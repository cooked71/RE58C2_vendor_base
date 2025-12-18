package com.android.internal.telephony;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UniSettings;
import android.text.TextUtils;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.internal.util.XmlUtils;
import com.android.unisoc.telephony.RadioInteractor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class PrimarySubConfig {
    private static final boolean DBG = true;
    private static final String TAG = "PrimarySubConfig";
    private static PrimarySubConfig mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private int mPhoneCount = TelephonyManager.getDefault().getSupportedModemCount();
    private List<PrimarySubConfigInfo> mAllConfigInfos = new ArrayList();
    private PrimarySubConfigInfo[] mIccConfigs = new PrimarySubConfigInfo[this.mPhoneCount];

    private PrimarySubConfig(Context context) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        this.mContext = context;
        this.mRadioInteractor = new RadioInteractor(this.mContext);
        loadPrimarySubConfigInfo();
    }

    public static PrimarySubConfig init(Context context) {
        if (mInstance == null) {
            mInstance = new PrimarySubConfig(context);
        }
        return mInstance;
    }

    public static PrimarySubConfig getInstance() {
        return mInstance;
    }

    private void loadPrimarySubConfigInfo() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        try {
            Context context = this.mContext.createPackageContext("com.unisoc.phone", 2);
            if (context != null) {
                Resources resource = context.getResources();
                int resId = resource.getIdentifier("primary_sub_conf", "xml", context.getPackageName());
                XmlPullParser parser = resource.getXml(resId);
                XmlUtils.beginDocument(parser, "iccConfigs");
                while (true) {
                    XmlUtils.nextElement(parser);
                    String name = parser.getName();
                    if (!"iccConfig".equals(name)) {
                        break;
                    } else {
                        this.mAllConfigInfos.add(new PrimarySubConfigInfo(parser));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            logw("Exception in primary_sub_conf parser " + e);
        } catch (IOException e2) {
            logw("Exception in primary_sub_conf parser " + e2);
        } catch (XmlPullParserException e3) {
            logw("Exception in primary_sub_conf parser " + e3);
        }
        logd("loadPrimarySubConfigInfo done: " + this.mAllConfigInfos);
    }

    public void update() {
        PrimarySubConfigInfo primarySubConfigInfo;
        SubscriptionManager subManager = SubscriptionManager.from(this.mContext);
        this.mIccConfigs = new PrimarySubConfigInfo[this.mPhoneCount];
        for (int i = 0; i < this.mPhoneCount; i++) {
            SubscriptionInfo subInfo = subManager.getActiveSubscriptionInfoForSimSlotIndex(i);
            if (subInfo != null) {
                for (PrimarySubConfigInfo config : this.mAllConfigInfos) {
                    PrimarySubConfigInfo newConfig = new PrimarySubConfigInfo(config);
                    if (newConfig.match(subInfo) && ((primarySubConfigInfo = this.mIccConfigs[i]) == null || primarySubConfigInfo.mMatchedScore < newConfig.mMatchedScore)) {
                        this.mIccConfigs[i] = newConfig;
                    }
                }
            }
        }
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            if (this.mIccConfigs[i2] != null) {
                setNetworkTypeRestrictEnable(false, i2);
                this.mIccConfigs[i2].updateRestrictedNetwork();
            }
            logd("Update matched config[" + i2 + "]: " + this.mIccConfigs[i2]);
        }
    }

    public int getPreferredPrimaryCard() throws Resources.NotFoundException {
        int preferredPrimaryCard;
        int maxPriorityPhoneId;
        Resources.getSystem().getBoolean(134414383);
        boolean airtelSimLockConfig = Resources.getSystem().getBoolean(134414382);
        if (0 != 0 || airtelSimLockConfig) {
            logd("getPreferredPrimaryCard for operator");
            OpSimLockController simLockCtrl = OpSimLockController.getInstance();
            int opPrimaryCardId = simLockCtrl.getOpPreferredPrimaryCard();
            if (!SubscriptionManager.isValidPhoneId(opPrimaryCardId)) {
                preferredPrimaryCard = getPreferredPrimaryCard(true);
            } else {
                preferredPrimaryCard = opPrimaryCardId;
            }
            maxPriorityPhoneId = preferredPrimaryCard;
        } else {
            maxPriorityPhoneId = getPreferredPrimaryCard(true);
        }
        return isFixedSlot() ? getFixedSlot() : maxPriorityPhoneId;
    }

    private int getPreferredPrimaryCard(boolean accordingPriority) {
        PrimarySubConfigInfo primarySubConfigInfo;
        int maxPriorityPhoneId = 0;
        SubscriptionManager subManager = SubscriptionManager.from(this.mContext);
        for (int i = 0; i < this.mPhoneCount; i++) {
            PrimarySubConfigInfo[] primarySubConfigInfoArr = this.mIccConfigs;
            if (primarySubConfigInfoArr[i] != null && ((primarySubConfigInfo = primarySubConfigInfoArr[maxPriorityPhoneId]) == null || primarySubConfigInfo.mPriority < this.mIccConfigs[i].mPriority)) {
                maxPriorityPhoneId = i;
            }
            SubscriptionInfo subInfo = subManager.getActiveSubscriptionInfoForSimSlotIndex(maxPriorityPhoneId);
            if (subInfo != null && ((!subManager.isSubscriptionEnabled(subInfo.getSubscriptionId()) || subInfo.isOpportunistic()) && i != maxPriorityPhoneId)) {
                logd("the subinfo is disabled or opportunistic");
                maxPriorityPhoneId = i;
            }
        }
        return maxPriorityPhoneId;
    }

    private static boolean isFixedSlot() {
        return "true".equals(SystemProperties.get("ro.vendor.radio.fixed_slot", "false"));
    }

    private int getFixedSlot() throws Resources.NotFoundException {
        int primaryPhoneId = Resources.getSystem().getInteger(134807639);
        return primaryPhoneId;
    }

    public boolean isNeedPopupPrimaryCardSettingPrompt() throws Resources.NotFoundException {
        Resources.getSystem().getBoolean(134414383);
        int maxPriorityPhoneId = getPreferredPrimaryCard();
        if (this.mIccConfigs[maxPriorityPhoneId] == null) {
            return false;
        }
        int maxPriorityCount = 0;
        boolean allowUserPrompt = false;
        for (int i = 0; i < this.mPhoneCount; i++) {
            PrimarySubConfigInfo primarySubConfigInfo = this.mIccConfigs[i];
            if (primarySubConfigInfo != null && primarySubConfigInfo.mPriority == this.mIccConfigs[maxPriorityPhoneId].mPriority) {
                maxPriorityCount++;
                allowUserPrompt |= this.mIccConfigs[i].mUserPrompt;
            }
        }
        return maxPriorityCount >= 2 && allowUserPrompt;
    }

    public long getNetworkTypeBitMask(int phoneId) {
        PrimarySubConfigInfo primarySubConfigInfo = this.mIccConfigs[phoneId];
        if (primarySubConfigInfo != null) {
            int network = primarySubConfigInfo.mNetwork;
            return MobileNetworkUtils.getRafFromNetworkType(network);
        }
        return -1L;
    }

    public long getRestrictedNetworkTypeBitMask(int phoneId) throws Resources.NotFoundException {
        Resources.getSystem().getBoolean(134414383);
        boolean orangeSimLockConfig = Resources.getSystem().getBoolean(134414384);
        boolean tigoSimLockConfig = Resources.getSystem().getBoolean(134414385);
        if (0 != 0 || orangeSimLockConfig || tigoSimLockConfig) {
            OpSimLockController simLockCtrl = OpSimLockController.getInstance();
            boolean restrictedNetModNeeded = simLockCtrl.restrictedNetworkTypeNeeded(phoneId);
            if (restrictedNetModNeeded) {
                int restrictedNetworkType = simLockCtrl.getRestrictedNetworkType(phoneId);
                logd("get simlock restricted networktype " + restrictedNetworkType + " for sim " + (phoneId + 1));
                return MobileNetworkUtils.getRafFromNetworkType(restrictedNetworkType);
            }
        }
        PrimarySubConfigInfo primarySubConfigInfo = this.mIccConfigs[phoneId];
        if (primarySubConfigInfo != null && primarySubConfigInfo.mIsNetworkRestricted) {
            int restrictNetwork = this.mIccConfigs[phoneId].mRestrictedNetwork;
            return MobileNetworkUtils.getRafFromNetworkType(restrictNetwork);
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRestrictedPhoneId(int phoneId) throws Resources.NotFoundException {
        int restrictedPhoneId = Resources.getSystem().getInteger(134807643);
        return SubscriptionManager.isValidPhoneId(restrictedPhoneId) ? restrictedPhoneId : phoneId;
    }

    void setNetworkTypeRestrictEnable(boolean z, int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), UniSettings.UniGlobal.RESTRICT_NETWORK_TYPE + i, z ? 1 : 0);
    }

    public String getTag(int phoneId) {
        PrimarySubConfigInfo primarySubConfigInfo = this.mIccConfigs[phoneId];
        if (primarySubConfigInfo == null) {
            return "";
        }
        String tag = primarySubConfigInfo.mTag;
        return tag;
    }

    class PrimarySubConfigInfo {
        private static final int ANY_MATCHED_FIALED = -1;
        private static final int MATCHED_APP_TYPE = 16;
        private static final int MATCHED_PARTTERN = 256;
        private static final int MATCHED_ROAMING_STATE = 4;
        private String mAppType;
        private boolean mForceRestricted;
        private String mGid;
        private boolean mIsNetworkRestricted;
        private int mMatchedScore;
        private SubscriptionInfo mMatchedSubInfo;
        private int mNetwork;
        private String mPattern;
        private String mPatternType;
        private int mPriority;
        private String mRestrictedBy;
        private int mRestrictedNetwork;
        private String mRoaming;
        private String mTag;
        private boolean mUserPrompt;

        PrimarySubConfigInfo(XmlPullParser parser) {
            this.mPriority = -1;
            this.mNetwork = -1;
            this.mRestrictedNetwork = -1;
            this.mTag = parser.getAttributeValue(null, "tag");
            this.mPatternType = parser.getAttributeValue(null, "pattern_type");
            this.mPattern = parser.getAttributeValue(null, "pattern");
            this.mAppType = parser.getAttributeValue(null, "app_type");
            this.mRoaming = parser.getAttributeValue(null, "roaming");
            this.mPriority = Integer.parseInt(parser.getAttributeValue(null, "priority"));
            String netwrokValue = parser.getAttributeValue(null, "network");
            if (!TextUtils.isEmpty(netwrokValue)) {
                this.mNetwork = Integer.parseInt(netwrokValue);
            }
            String restrictedNetwrokValue = parser.getAttributeValue(null, "restricted_network");
            if (!TextUtils.isEmpty(restrictedNetwrokValue)) {
                this.mRestrictedNetwork = Integer.parseInt(restrictedNetwrokValue);
            }
            this.mRestrictedBy = parser.getAttributeValue(null, "restricted_by");
            this.mUserPrompt = Boolean.valueOf(parser.getAttributeValue(null, "user_prompt")).booleanValue();
            this.mGid = parser.getAttributeValue(null, "gid");
            this.mForceRestricted = Boolean.valueOf(parser.getAttributeValue(null, "force_restricted")).booleanValue();
        }

        PrimarySubConfigInfo(PrimarySubConfigInfo config) {
            this.mPriority = -1;
            this.mNetwork = -1;
            this.mRestrictedNetwork = -1;
            copyFrom(config);
        }

        boolean match(SubscriptionInfo subInfo) {
            this.mMatchedScore = 0;
            if (subInfo != null && SubscriptionManager.isValidSubscriptionId(subInfo.getSubscriptionId())) {
                int phoneId = subInfo.getSimSlotIndex();
                int subId = subInfo.getSubscriptionId();
                if (!TextUtils.isEmpty(this.mPattern)) {
                    Pattern p = Pattern.compile(this.mPattern);
                    if ("mccmnc".equalsIgnoreCase(this.mPatternType)) {
                        String mccMnc = TelephonyManager.from(PrimarySubConfig.this.mContext).getSimOperatorNumeric(subId);
                        if (!TextUtils.isEmpty(mccMnc) && p.matcher(mccMnc).find()) {
                            if (!TextUtils.isEmpty(this.mGid)) {
                                Pattern p1 = Pattern.compile(this.mGid);
                                String gid1 = TelephonyManager.from(PrimarySubConfig.this.mContext).getGroupIdLevel1(subId);
                                if (!TextUtils.isEmpty(gid1) && p1.matcher(gid1).find()) {
                                    this.mMatchedScore |= 256;
                                }
                            } else {
                                this.mMatchedScore |= 256;
                            }
                        }
                    } else {
                        String iccId = PrimarySubConfig.this.mRadioInteractor.getIccIdFromIccStatus(phoneId);
                        if (!TextUtils.isEmpty(iccId) && p.matcher(iccId).find()) {
                            this.mMatchedScore |= 256;
                        }
                    }
                    if ((this.mMatchedScore & 256) != 256) {
                        this.mMatchedScore = -1;
                        return false;
                    }
                }
                if (!TextUtils.isEmpty(this.mAppType)) {
                    int type = PrimarySubConfig.this.mRadioInteractor.getIccAppType(phoneId);
                    if (this.mAppType.equals(getAppType(type))) {
                        this.mMatchedScore |= 16;
                    }
                    if ((this.mMatchedScore & 16) != 16) {
                        this.mMatchedScore = -1;
                        return false;
                    }
                }
                if (!TextUtils.isEmpty(this.mRoaming)) {
                    TelephonyManager tm = TelephonyManager.from(PrimarySubConfig.this.mContext);
                    ServiceState ss = tm.getServiceStateForSubscriber(subId);
                    boolean isRoaming = ss != null ? ss.getRoaming() : false;
                    if (String.valueOf(isRoaming).equals(this.mRoaming)) {
                        this.mMatchedScore |= 4;
                    }
                    if ((this.mMatchedScore & 4) != 4) {
                        this.mMatchedScore = -1;
                        return false;
                    }
                }
            }
            int phoneId2 = this.mMatchedScore;
            if (phoneId2 > 0) {
                this.mMatchedSubInfo = subInfo;
            }
            return phoneId2 > 0;
        }

        private String getAppType(int type) {
            switch (type) {
                case 1:
                    return "APPTYPE_SIM";
                case 2:
                    return "APPTYPE_USIM";
                default:
                    return "APPTYPE_UNKNOWN";
            }
        }

        void updateRestrictedNetwork() {
            if (this.mMatchedSubInfo != null) {
                if (TextUtils.isEmpty(this.mRestrictedBy)) {
                    this.mIsNetworkRestricted = false;
                    return;
                }
                for (PrimarySubConfigInfo config : PrimarySubConfig.this.mIccConfigs) {
                    if (((config != this && config != null && !TextUtils.isEmpty(config.mTag) && this.mRestrictedBy.matches(config.mTag)) || (config != null && config.mForceRestricted)) && downgradeNetworkCapability(this.mMatchedSubInfo.getSimSlotIndex())) {
                        this.mIsNetworkRestricted = true;
                        return;
                    }
                }
            }
            this.mIsNetworkRestricted = false;
        }

        private boolean downgradeNetworkCapability(int phoneId) {
            Resources res = Resources.getSystem();
            try {
                if (res.getBoolean(134414378)) {
                    int restrictedPhoneId = PrimarySubConfig.getInstance().getRestrictedPhoneId(phoneId);
                    PrimarySubConfig.this.logd("downgradeNetworkCapability restrictedPhoneId=" + restrictedPhoneId + ",mRestrictedBy =" + this.mRestrictedBy + ",mTag=" + this.mTag);
                    if (!this.mRestrictedBy.equalsIgnoreCase(this.mTag) && phoneId == restrictedPhoneId) {
                        PrimarySubConfig.getInstance().setNetworkTypeRestrictEnable(true, restrictedPhoneId);
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                PrimarySubConfig.this.logd("occur exception");
                return false;
            }
        }

        private void copyFrom(PrimarySubConfigInfo config) {
            this.mTag = config.mTag;
            this.mPatternType = config.mPatternType;
            this.mPattern = config.mPattern;
            this.mAppType = config.mAppType;
            this.mRoaming = config.mRoaming;
            this.mPriority = config.mPriority;
            this.mNetwork = config.mNetwork;
            this.mRestrictedNetwork = config.mRestrictedNetwork;
            this.mRestrictedBy = config.mRestrictedBy;
            this.mUserPrompt = config.mUserPrompt;
            this.mGid = config.mGid;
            this.mForceRestricted = config.mForceRestricted;
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append("PrimarySubConfigInfo:  tag= ").append(this.mTag).append(" patternType= ").append(this.mPatternType).append(" parttern= ").append(this.mPattern).append(" appType= ").append(this.mAppType).append(" priority= ").append(this.mPriority).append(" network= ").append(this.mNetwork).append(" restrictedNetwork= ").append(this.mRestrictedNetwork).append(" restrictedBy= ").append(this.mRestrictedBy).append(" userPrompt= ").append(this.mUserPrompt).append(" isNetworkRestricted= ").append(this.mIsNetworkRestricted).append(" roaming= ").append(this.mRoaming).append(" gid= ").append(this.mGid).append(" forceRestricted=").append(this.mForceRestricted).append(" matchedScore= ").append(this.mMatchedScore).append(" sub= ");
            SubscriptionInfo subscriptionInfo = this.mMatchedSubInfo;
            return sbAppend.append(subscriptionInfo == null ? "null" : Integer.valueOf(subscriptionInfo.getSubscriptionId())).append("\n").toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logd(String msg) {
        Rlog.d(TAG, msg);
    }

    private void logw(String msg) {
        Rlog.w(TAG, msg);
    }
}