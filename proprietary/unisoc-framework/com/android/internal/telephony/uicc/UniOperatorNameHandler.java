package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UniCarrierConfigManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.aidl.IOperatorNameHandler;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class UniOperatorNameHandler extends IOperatorNameHandler.Stub implements UniIccConstants {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "UniOperatorNameHandler";
    private static final String UNISOC_PLMN_COMMON_PERMISSION = "com.unisoc.permisson.PLMN_COMMON";
    private static UniOperatorNameHandler mInstance;
    private static int mPhoneCount = TelephonyManager.getDefault().getPhoneCount();
    private static HashMap<String, String> mTelcelPlmnMap = new HashMap<>();
    private Context mContext;
    private PersistableBundle mPersistableBundle;
    private RadioInteractor mRadioInteractor;
    private boolean mShowTelcelName;
    private TelephonyManager mTelephonyManager;
    private UniCarrierConfigManager mUniCarrierConfigManager;
    private Boolean mResetPlmn = false;
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.uicc.UniOperatorNameHandler.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED") || intent.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                Log.d(UniOperatorNameHandler.LOG_TAG, "onReceive ACTION_LOCALE_CHANGED or ACTION_CARRIER_CONFIG_CHANGED");
                UniOperatorNameHandler.this.mResetPlmn = true;
                UniOperatorNameHandler.this.updatePlmn();
            }
        }
    };

    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        int check = this.mContext.checkCallingOrSelfPermission(UNISOC_PLMN_COMMON_PERMISSION);
        if (check == -1) {
            Log.d(LOG_TAG, "IRadiointeractor-> permission denied !");
            return false;
        }
        return super.onTransact(code, data, reply, flags);
    }

    private UniOperatorNameHandler(Context context) throws Resources.NotFoundException {
        this.mContext = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCALE_CHANGED");
        filter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        context.registerReceiver(this.mIntentReceiver, filter);
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        String[] itemList = this.mContext.getResources().getStringArray(134283384);
        try {
            for (String item : itemList) {
                String[] parts = item.split(",");
                mTelcelPlmnMap.put(parts[0], parts[1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        this.mUniCarrierConfigManager = new UniCarrierConfigManager(this.mContext);
        publish();
    }

    public static UniOperatorNameHandler init(Context context) {
        UniOperatorNameHandler uniOperatorNameHandler;
        synchronized (UniOperatorNameHandler.class) {
            if (mInstance == null) {
                mInstance = new UniOperatorNameHandler(context);
            } else {
                Log.d(LOG_TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
            uniOperatorNameHandler = mInstance;
        }
        return uniOperatorNameHandler;
    }

    public static UniOperatorNameHandler getInstance() {
        if (mInstance == null) {
            Log.d(LOG_TAG, "getInstance null");
        }
        return mInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlmn() {
        if (this.mRadioInteractor == null) {
            this.mRadioInteractor = new RadioInteractor(this.mContext);
        }
        for (int phoneId = 0; phoneId < mPhoneCount; phoneId++) {
            String mccmnc = TelephonyManager.from(this.mContext).getNetworkOperatorForPhone(phoneId);
            if (!TextUtils.isEmpty(mccmnc)) {
                this.mRadioInteractor.updateOperatorName(mccmnc, phoneId);
            }
            if (this.mResetPlmn.booleanValue()) {
                this.mRadioInteractor.updateOperatorName((String) null, phoneId);
            }
        }
        this.mResetPlmn = false;
    }

    public String getHighPriorityPlmn(int phoneId, String mccmnc, int lac) throws Resources.NotFoundException {
        String telcelPlmn;
        Log.d(LOG_TAG, "getHighPriorityPlmn for phone " + phoneId + " mccmnc ****** and lac " + lac);
        String simMccMnc = TelephonyManager.getTelephonyProperty(phoneId, "gsm.sim.operator.numeric", "");
        if ("47002".equals(mccmnc) && "47007".equals(simMccMnc)) {
            return "Airtel";
        }
        UniCarrierConfigManager uniCarrierConfigManager = this.mUniCarrierConfigManager;
        if (uniCarrierConfigManager != null) {
            PersistableBundle configForSubId = uniCarrierConfigManager.getConfigForSubId(getSubId(phoneId));
            this.mPersistableBundle = configForSubId;
            if (configForSubId != null) {
                this.mShowTelcelName = configForSubId.getBoolean(UniCarrierConfigManager.KEY_ROAMING_PLMN_OVERRIDE_BOOL);
            }
        }
        TelephonyManager telephonyManagerCreateForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(getSubId(phoneId));
        this.mTelephonyManager = telephonyManagerCreateForSubscriptionId;
        boolean isRoaming = false;
        if (telephonyManagerCreateForSubscriptionId.getServiceState() != null) {
            isRoaming = this.mTelephonyManager.getServiceState().getRoaming();
        }
        if (this.mShowTelcelName && isRoaming && (telcelPlmn = mTelcelPlmnMap.get(mccmnc)) != null) {
            return telcelPlmn;
        }
        UniIccRecords iccRecords = UniIccRecordsController.getInstance().getUniIccRecordForPhone(phoneId);
        if (("334020".equals(mccmnc) || "33402".equals(mccmnc)) && isRoaming && !TextUtils.isEmpty(iccRecords.getSimOns())) {
            return iccRecords.getSimOns();
        }
        if (!SubscriptionManager.isValidPhoneId(phoneId) || TextUtils.isEmpty(mccmnc) || mccmnc.length() <= 3) {
            return mccmnc;
        }
        if (!TextUtils.isEmpty(mccmnc) && mccmnc.substring(0, 3).equals("716") && mccmnc.equals(TelephonyManager.getDefault().getSimOperatorNumericForPhone(phoneId))) {
            return "";
        }
        String homePlmn = TelephonyManager.getDefault().getSimOperatorNumericForPhone(phoneId);
        if (("26003".equals(mccmnc) || "26005".equals(mccmnc)) && ("26098".equals(homePlmn) || "26006".equals(homePlmn))) {
            return "PLAY(Orange)";
        }
        if ("22201".equals(mccmnc)) {
            return "TIM";
        }
        String orangePlmn = getOrangeOperatorPlmn(mccmnc, isRoaming);
        if (!TextUtils.isEmpty(orangePlmn)) {
            return orangePlmn;
        }
        if (lac == -1) {
            lac = iccRecords.getLac();
        }
        String highPriorityPlmn = getPnn(iccRecords, mccmnc, lac, phoneId);
        if (TextUtils.isEmpty(highPriorityPlmn) && !isIgnoreOns(phoneId) && TelephonyManager.from(this.mContext).getNetworkOperatorForPhone(phoneId).equals(mccmnc) && ArrayUtils.contains(iccRecords.getHomePlmns(), mccmnc)) {
            highPriorityPlmn = iccRecords.getSimOns();
            Log.d(LOG_TAG, "Didn't get pnn from sim, try ons next. ONS = " + highPriorityPlmn);
        }
        if (!TextUtils.isEmpty(highPriorityPlmn) && mccmnc.startsWith("466")) {
            Log.d(LOG_TAG, "Show highPriorityPlmn for TW OPs");
            return highPriorityPlmn;
        }
        if (lac != -1 && "52503".equals(mccmnc)) {
            return "SGP-M1";
        }
        String highPriorityPlmn2 = plmnIgnoreDisplayRule(phoneId, mccmnc, highPriorityPlmn);
        String localeName = getLocaleOperatorName(mccmnc);
        if (TextUtils.isEmpty(localeName) || ("46697".equals(mccmnc) && ("GT 4G R".equals(highPriorityPlmn2) || "GT R".equals(highPriorityPlmn2)))) {
            return highPriorityPlmn2;
        }
        return localeName;
    }

    public String getOrangeOperatorPlmn(String mccmnc, boolean isRoaming) {
        if (TextUtils.isEmpty(mccmnc)) {
            return "";
        }
        switch (mccmnc) {
            case "20610":
                return "Orange B";
            case "20801":
                if (!isRoaming) {
                    return "Orange F";
                }
                return "Orange FR";
            case "21403":
                if (!isRoaming) {
                    return "Orange";
                }
                return "Orange SP";
            case "22610":
                if (!isRoaming) {
                    return "Orange";
                }
                return "RO Orange";
            case "23101":
                if (!isRoaming) {
                    return "Orange";
                }
                return "Orange SK";
            default:
                return "";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String updateNetworkList(int r14, java.lang.String[] r15) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.uicc.UniOperatorNameHandler.updateNetworkList(int, java.lang.String[]):java.lang.String");
    }

    private String getPnn(UniIccRecords iccRecords, String regPlmn, int lac, int phoneId) {
        String pnn = "";
        UniIccServiceTable ist = iccRecords.getIccmServiceTable();
        if (ist == null) {
            Log.d(LOG_TAG, "ist is null, return empty pnn");
            return "";
        }
        if (ist.pnnAvaliable() && ist.oplNrAvaliable()) {
            pnn = getOperatorNameFromOplNrPnn(iccRecords, regPlmn, lac);
        } else if (ist.pnnAvaliable() && ist.oplAvaliable()) {
            pnn = getOperatorNameFromOplPnn(iccRecords, regPlmn, lac);
        } else if (TextUtils.isEmpty("") && ist.pnnAvaliable()) {
            pnn = getFirstPnn(iccRecords, regPlmn, phoneId);
        }
        Log.d(LOG_TAG, "getPnn return " + pnn);
        return pnn;
    }

    private String getOperatorNameFromOplPnn(UniIccRecords iccRecords, String regPlmn, int lac) {
        if (regPlmn == null) {
            log("regplmn is  null,doesn't  getOperatorNameFromOplPnn from sim.");
            return "";
        }
        if (iccRecords.getPnnRecords() == null || iccRecords.getPnnRecords().size() == 0) {
            log("mPnnRecords is null");
            return "";
        }
        int pnnRecordnum = getPnnRecordnum(iccRecords, regPlmn, lac);
        if (pnnRecordnum <= 0 || pnnRecordnum > iccRecords.getPnnRecords().size()) {
            log("invalid PnnRecordnum = " + pnnRecordnum);
            return "";
        }
        log("mPnnRecords.get(PnnRecordnum - 1).getLongName(): ********");
        String operatorFromOpnPnn = iccRecords.getPnnRecords().get(pnnRecordnum - 1).getLongName();
        return operatorFromOpnPnn;
    }

    private String getFirstPnn(UniIccRecords iccRecords, String regPlmn, int phoneId) {
        String firstPnn = "";
        if (regPlmn == null) {
            log("regplmn is  null, doesn't get pnn name from sim.");
            return "";
        }
        String homePlmn = TelephonyManager.getDefault().getSimOperatorNumericForPhone(phoneId);
        if (regPlmn.equals(homePlmn) && iccRecords.getPnnRecords() != null && iccRecords.getPnnRecords().size() > 0) {
            log("PNN first record name: " + iccRecords.getPnnRecords().get(0).getLongName());
            firstPnn = iccRecords.getPnnRecords().get(0).getLongName();
        }
        log("firstPnn return " + firstPnn);
        return firstPnn;
    }

    private String getOperatorNameFromOplNrPnn(UniIccRecords iccRecords, String regPlmn, int lac) {
        if (regPlmn == null) {
            log("regplmn is  null,doesn't  getOperatorNameFromOplNrPnn from sim.");
            return "";
        }
        if (iccRecords.getPnnRecords() == null || iccRecords.getPnnRecords().size() == 0) {
            log("mPnnRecords is null");
            return "";
        }
        int PnnRecordnumNr = getPnnRecordnum(iccRecords, regPlmn, lac);
        if (PnnRecordnumNr <= 0 || PnnRecordnumNr > iccRecords.getPnnRecords().size()) {
            log("invalid PnnRecordnumNr = " + PnnRecordnumNr);
            return "";
        }
        log("mPnnRecords.get(PnnRecordnumNr - 1).getLongName(): " + iccRecords.getPnnRecords().get(PnnRecordnumNr - 1).getLongName());
        String operatorFromOpnPnn = iccRecords.getPnnRecords().get(PnnRecordnumNr - 1).getLongName();
        return operatorFromOpnPnn;
    }

    private int getPnnRecordnum(UniIccRecords iccRecords, String regplmn, int lac) {
        int[] regplmnarray = {0, 0, 0, 0, 0, 0};
        int pnnRecordNum = -1;
        if (regplmn == null || regplmn.length() > regplmnarray.length) {
            log("regplmn is invalid, fail to get pnn name from sim.");
            return -1;
        }
        if (lac == -1) {
            log("invalid lac");
            return -1;
        }
        if (iccRecords.getOplNrRecords() == null && iccRecords.getOplRecords() == null) {
            log("OplRecord not exist");
            if (!ArrayUtils.contains(iccRecords.getHomePlmns(), regplmn)) {
                return -1;
            }
            log("OplRecord not exist, but registered in the HPLMN");
            return 1;
        }
        for (int i = 0; i < regplmn.length(); i++) {
            regplmnarray[i] = regplmn.charAt(i) - '0';
        }
        if (iccRecords.getOplNrRecords() != null) {
            Iterator<UniOplNrRecord> it = iccRecords.getOplNrRecords().iterator();
            while (it.hasNext()) {
                UniOplNrRecord record = it.next();
                if (matchOplplmn(record.mOplNrplmn, regplmnarray)) {
                    log("getPnnRecordnumNr  tac:" + lac + ", record.mOplNrtac1:" + record.mOplNrtac1 + ", record.mOplNrtac2:" + record.mOplNrtac2);
                    if (record.mOplNrtac1 <= lac && lac <= record.mOplNrtac2) {
                        log("record.getPnnRecordNumNr() = " + record.getPnnRecordNum());
                        pnnRecordNum = record.getPnnRecordNum();
                    }
                }
            }
        } else if (iccRecords.getOplRecords() != null) {
            Iterator<UniOplRecord> it2 = iccRecords.getOplRecords().iterator();
            while (it2.hasNext()) {
                UniOplRecord record2 = it2.next();
                if (matchOplplmn(record2.mOplplmn, regplmnarray)) {
                    log("getPnnRecordnum  lac:" + lac + ", record.mOpllac1:" + record2.mOpllac1 + ", record.mOpllac2:" + record2.mOpllac2);
                    if (record2.mOpllac1 <= lac && lac <= record2.mOpllac2) {
                        log("record.getPnnRecordNum() = " + record2.getPnnRecordNum());
                        pnnRecordNum = record2.getPnnRecordNum();
                    }
                }
            }
        }
        log("No invalid pnn record match");
        return pnnRecordNum;
    }

    private boolean matchOplplmn(int[] oplplmn, int[] regplmn) {
        boolean match = true;
        if (regplmn == null || oplplmn == null) {
            return false;
        }
        if (regplmn.length != oplplmn.length) {
            log("regplmn length is not equal oplmn length");
            return false;
        }
        for (int i = 0; i < regplmn.length; i++) {
            if (oplplmn[i] == 13) {
                oplplmn[i] = regplmn[i];
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= regplmn.length) {
                break;
            }
            if (oplplmn[i2] == regplmn[i2]) {
                i2++;
            } else {
                match = false;
                break;
            }
        }
        log("matchOplplmn match:" + match);
        return match;
    }

    private String plmnIgnoreDisplayRule(int phoneId, String mccmnc, String highPriorityPlmn) throws Resources.NotFoundException {
        int subId = getSubId(phoneId);
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            Resources res = SubscriptionManager.getResourcesForSubId(this.mContext, subId);
            String specialPlmn = res.getString(135004246);
            if (!TextUtils.isEmpty(specialPlmn)) {
                String[] specialPlmnInfo = specialPlmn.split("@");
                if (specialPlmnInfo.length == 2 && mccmnc.equals(specialPlmnInfo[0])) {
                    return specialPlmnInfo[1];
                }
            }
        }
        return TextUtils.isEmpty(highPriorityPlmn) ? "" : highPriorityPlmn;
    }

    private int getSubId(int phoneId) {
        int[] subIds = SubscriptionManager.getSubId(phoneId);
        if (subIds == null || subIds.length <= 0) {
            return -1;
        }
        int subId = subIds[0];
        return subId;
    }

    private Resources getResourcesForMccMnc(Context context, String mccmnc) {
        Configuration config = context.getResources().getConfiguration();
        Configuration newConfig = new Configuration();
        newConfig.setTo(config);
        try {
            newConfig.mcc = Integer.parseInt(mccmnc.substring(0, 3));
            newConfig.mnc = Integer.parseInt(mccmnc.substring(3));
            if (newConfig.mnc == 0) {
                newConfig.mnc = 65535;
            }
        } catch (NumberFormatException e) {
            log("getResourcesForMccMnc " + e);
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        DisplayMetrics newMetrics = new DisplayMetrics();
        newMetrics.setTo(metrics);
        return new Resources(context.getResources().getAssets(), newMetrics, newConfig);
    }

    private String getLocaleOperatorName(String mccmnc) throws Resources.NotFoundException {
        if (mccmnc.matches("^[0-9]{5,6}$")) {
            Resources res = getResourcesForMccMnc(this.mContext, mccmnc);
            boolean preferLocaleName = res.getBoolean(134414352);
            String localeNameRes = res.getString(135004180);
            Log.d(LOG_TAG, "preferLocaleName = " + preferLocaleName);
            Log.d(LOG_TAG, "localeName = ***");
            if (preferLocaleName && !TextUtils.isEmpty(localeNameRes)) {
                return localeNameRes;
            }
        }
        return "";
    }

    private boolean isIgnoreOns(int phoneId) {
        Resources res;
        int subId = getSubId(phoneId);
        if (SubscriptionManager.isValidSubscriptionId(subId) && (res = SubscriptionManager.getResourcesForSubId(this.mContext, subId)) != null) {
            return res.getBoolean(134414347);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void publish() {
        Log.d(LOG_TAG, "publish: " + this);
        ServiceManager.addService("ions_ex", this);
    }

    private void log(String s) {
        Log.d(LOG_TAG, s);
    }
}