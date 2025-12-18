package com.android.internal.telephony.subsidy;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioSystemEx;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.PrimarySubManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.ims.ImsManager;
import com.android.internal.telephony.MobileNetworkUtils;
import com.android.internal.telephony.PrimarySubConfig;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;
import java.util.List;

/* loaded from: classes.dex */
public class SubsidyLockController {
    private static final String ACTION_DATA_ENABLED_AUTOMATICALLY = "com.sprd.action.AUTO_EANBLE_DATA";
    private static final String ACTION_SUBSIDYLOCK_STATE = "com.slc.action.ACTION_SUBSIDYLOCK_STATE";
    private static final String ACTION_SUBSIDYLOCK_STATE_EXTEND = "com.sprd.action.ACTION_SUBSIDYLOCK_STATE";
    private static final String INTENT_KEY_LOCK_SCREEN = "INTENT_KEY_LOCK_SCREEN";
    private static final String INTENT_KEY_SWITCH_SIM_SCREEN = "INTENT_KEY_SWITCH_SIM_SCREEN";
    private static final String INTENT_KEY_UNLOCK_PERMANENTLY = "INTENT_KEY_UNLOCK_PERMANENTLY";
    private static final String INTENT_KEY_UNLOCK_SCREEN = "INTENT_KEY_UNLOCK_SCREEN";
    private static String[] JIO_ICCID = {"8991868", "8991866", "8991865", "8991869", "8991867", "8991871", "8991874", "8991864", "8991873", "8991872", "8991870", "8991840", "8991857", "8991858", "8991859", "8991860", "8991856", "8991855", "8991862", "8991861", "8991863", "8991854", "898600", "898602"};
    private static final String KEY_SUB = "sub";
    private static final int SEND_BROADCAST_DELAY = 5000;
    private static final String SUBSIDYLOCK_ICCID_PREFS_NAME = "subsidylock.info.iccid";
    private static final int SUBSIDY_LOCK_SCREEN_MODE_LOCK = 1;
    private static final int SUBSIDY_LOCK_SCREEN_MODE_SWITCH_SIM = 2;
    private static final int SUBSIDY_LOCK_SCREEN_MODE_UNLOCK = 3;
    private static final int SUBSIDY_LOCK_SCREEN_MODE_UNLOCK_PERMANENTLY = 4;
    private static final int SUBSIDY_STATUS_LOCKED = 1;
    private static final int SUBSIDY_STATUS_UNLOCKED = 0;
    private static final String TAG = "SubsidyLockController";
    private static SubsidyLockController mInstance;
    private Context mContext;
    private boolean[] mEmergencyOnly;
    private String[] mIccId;
    private int mPhoneCount;
    private PrimarySubConfig mPrimarySubConfig;
    private PrimarySubManager mPrimarySubMgr;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener[] mRadioInteractorListener;
    private String[] mSimStates;
    private SubscriptionManager mSubscriptionManager;
    private boolean mSubsidyLock;
    private TelephonyManager mTelephonyManager;
    private boolean mFirstReceive = true;
    private boolean mHasReceiveBroadcastFromSlc = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(SubsidyLockController.TAG, "receive broadcast : " + action + " mFirstReceive : " + SubsidyLockController.this.mFirstReceive);
            if (action.equals(SubsidyLockController.ACTION_SUBSIDYLOCK_STATE)) {
                SubsidyLockController.this.mHasReceiveBroadcastFromSlc = true;
            } else if (SubsidyLockController.this.mHasReceiveBroadcastFromSlc && SubsidyLockController.ACTION_SUBSIDYLOCK_STATE_EXTEND.equals(action)) {
                Log.d(SubsidyLockController.TAG, "Has receive Broadcast From Slc, no need to handle ACTION_SUBSIDYLOCK_STATE_EXTEND");
                return;
            }
            if (action.equals(SubsidyLockController.ACTION_SUBSIDYLOCK_STATE) || action.equals(SubsidyLockController.ACTION_SUBSIDYLOCK_STATE_EXTEND)) {
                if (action.equals(SubsidyLockController.ACTION_SUBSIDYLOCK_STATE)) {
                    if (intent.getBooleanExtra(SubsidyLockController.INTENT_KEY_LOCK_SCREEN, false)) {
                        Log.d(SubsidyLockController.TAG, "---- Receive broadcast: INTENT_KEY_LOCK_SCREEN");
                        SystemProperties.set("gsm.subsidy.lock.state", "true");
                        SystemProperties.set("gsm.subsidylock.currentstate", "1");
                    } else if (intent.getBooleanExtra(SubsidyLockController.INTENT_KEY_SWITCH_SIM_SCREEN, false)) {
                        Log.d(SubsidyLockController.TAG, "---- Receive broadcast: INTENT_KEY_SWITCH_SIM_SCREEN");
                        SystemProperties.set("gsm.subsidy.lock.state", "true");
                        SystemProperties.set("gsm.subsidylock.currentstate", "2");
                    } else if (intent.getBooleanExtra(SubsidyLockController.INTENT_KEY_UNLOCK_SCREEN, false)) {
                        Log.d(SubsidyLockController.TAG, "---- Receive broadcast: INTENT_KEY_UNLOCK_SCREEN");
                        SystemProperties.set("gsm.subsidy.lock.state", "false");
                        SystemProperties.set("gsm.subsidylock.currentstate", "3");
                    } else if (intent.getBooleanExtra(SubsidyLockController.INTENT_KEY_UNLOCK_PERMANENTLY, false)) {
                        Log.d(SubsidyLockController.TAG, "---- Receive broadcast: INTENT_KEY_UNLOCK_PERMANENTLY");
                        SystemProperties.set("gsm.subsidy.lock.state", "false");
                        SystemProperties.set("gsm.subsidylock.currentstate", "4");
                    } else {
                        Log.d(SubsidyLockController.TAG, "No Valid Extra For SubsidyLock");
                    }
                }
                if (SubsidyLockController.this.mFirstReceive) {
                    Log.d(SubsidyLockController.TAG, "First Receive Broadcast, send the state to SystemUI later");
                    new Handler().postDelayed(new Runnable() { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SubsidyLockController.this.sendBroadcastForSystemUI();
                        }
                    }, 5000L);
                    SubsidyLockController.this.mFirstReceive = false;
                }
                for (int i = 0; i < SubsidyLockController.this.mPhoneCount; i++) {
                    SubsidyLockController.this.setEmergencyOnly(i);
                }
                SubsidyLockController.this.enableLte();
                return;
            }
            if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                int phoneId = intent.getIntExtra("phone", -1);
                String simState = intent.getStringExtra("ss");
                Log.d(SubsidyLockController.TAG, "SIM_STATE_CHANGED: simState[" + phoneId + "] = " + simState);
                if (!SubscriptionManager.isValidPhoneId(phoneId)) {
                    return;
                }
                if ("ABSENT".equals(simState) || "NOT_READY".equals(simState) || "READY".equals(simState) || "IMSI".equals(simState) || "LOADED".equals(simState) || "LOCKED".equals(simState) || "UNKNOWN".equals(simState)) {
                    if (simState != null && simState.equals(SubsidyLockController.this.mSimStates[phoneId])) {
                        return;
                    }
                    SubsidyLockController.this.mSimStates[phoneId] = simState;
                    SubsidyLockController.this.mPrimarySubConfig.update();
                    if ("IMSI".equals(SubsidyLockController.this.mSimStates[phoneId]) && SubsidyLockController.this.mRadioInteractor != null && SubsidyLockController.this.mRadioInteractor.getRealSimSatus(phoneId) != 0) {
                        Log.d(SubsidyLockController.TAG, "simLoaded setEmergencyOnly mode according to lock state");
                        SubsidyLockController.this.setEmergencyOnly(phoneId);
                    }
                    if ("ABSENT".equals(simState)) {
                        SubsidyLockController.this.mIccId[phoneId] = "";
                    }
                }
            }
        }
    };
    private SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.5
        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() throws Resources.NotFoundException {
            int defaultDataPhoneId = SubsidyLockController.this.mSubscriptionManager.getDefaultDataPhoneId();
            if (SubscriptionManager.isValidPhoneId(defaultDataPhoneId) && SubsidyLockController.this.isLowPriSim(defaultDataPhoneId) && SubsidyLockController.this.mPrimarySubConfig != null && defaultDataPhoneId != SubsidyLockController.this.mPrimarySubConfig.getPreferredPrimaryCard()) {
                Log.d(SubsidyLockController.TAG, "current data sub is not desired");
                SubsidyLockController.this.resetPrimaryCardIfNeed();
            }
        }
    };
    private boolean mSupportSubsidyLock = Resources.getSystem().getBoolean(134414357);

    public static SubsidyLockController init(Context context) {
        Log.d(TAG, "init");
        synchronized (SubsidyLockController.class) {
            if (mInstance == null) {
                mInstance = new SubsidyLockController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    private SubsidyLockController(Context context) {
        this.mContext = context;
        TelephonyManager telephonyManagerFrom = TelephonyManager.from(this.mContext);
        this.mTelephonyManager = telephonyManagerFrom;
        this.mPhoneCount = telephonyManagerFrom.getActiveModemCount();
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(this.mContext);
        this.mSubscriptionManager = subscriptionManagerFrom;
        subscriptionManagerFrom.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        this.mPrimarySubMgr = PrimarySubManager.from(this.mContext);
        this.mPrimarySubConfig = PrimarySubConfig.init(context);
        this.mRadioInteractorListener = new RadioInteractorListener[this.mPhoneCount];
        addRadioInteractorListener();
        int i = this.mPhoneCount;
        this.mSimStates = new String[i];
        this.mEmergencyOnly = new boolean[i];
        this.mIccId = new String[i];
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            this.mEmergencyOnly[i2] = false;
            this.mIccId[i2] = "";
        }
        IntentFilter filter = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
        filter.addAction(ACTION_SUBSIDYLOCK_STATE);
        filter.addAction(ACTION_SUBSIDYLOCK_STATE_EXTEND);
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    private void addRadioInteractorListener() {
        this.mContext.bindService(new Intent("com.android.unisoc.telephony.server.RADIOINTERACTOR_SERVICE").setPackage("com.android.unisoc.telephony.server"), new ServiceConnection() { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(SubsidyLockController.TAG, "on radioInteractor service connected");
                if (SubsidyLockController.this.mRadioInteractor == null) {
                    SubsidyLockController.this.mRadioInteractor = new RadioInteractor(SubsidyLockController.this.mContext);
                }
                for (int i = 0; i < SubsidyLockController.this.mPhoneCount; i++) {
                    SubsidyLockController.this.mRadioInteractorListener[i] = SubsidyLockController.this.getRadioInteractorListener(i);
                    boolean z = false;
                    SubsidyLockController.this.mRadioInteractor.listen(SubsidyLockController.this.mRadioInteractorListener[i], MobileNetworkUtils.RadioAccessFamily.RAF_HSPAP, false);
                    SubsidyLockController.this.mRadioInteractor.listen(SubsidyLockController.this.mRadioInteractorListener[i], AudioSystemEx.DEVICE_OUT_FM_HEADSET, false);
                    SubsidyLockController subsidyLockController = SubsidyLockController.this;
                    if (subsidyLockController.mRadioInteractor.getSubsidyLockStatus(0) == 1) {
                        z = true;
                    }
                    subsidyLockController.mSubsidyLock = z;
                    Log.d(SubsidyLockController.TAG, "When radioInteractor service connected, SubsidyLock state is " + SubsidyLockController.this.mSubsidyLock);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                for (int i = 0; i < SubsidyLockController.this.mPhoneCount; i++) {
                    SubsidyLockController.this.mRadioInteractor.listen(SubsidyLockController.this.mRadioInteractorListener[i], 0);
                }
            }
        }, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RadioInteractorListener getRadioInteractorListener(final int phoneId) {
        return new RadioInteractorListener(phoneId) { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.3
            public void onRealSimStateChangedEvent() {
                Log.d(SubsidyLockController.TAG, "onRealSimStateChangedEvent phoneId= " + phoneId);
                if (SubsidyLockController.this.mRadioInteractor.getRealSimSatus(phoneId) == 0) {
                    SubsidyLockController.this.mIccId[phoneId] = "";
                } else {
                    SubsidyLockController.this.setSimEnabledForOperator(phoneId);
                }
            }

            public void onSubsidyLockEvent(Object object) throws Resources.NotFoundException {
                Log.d(SubsidyLockController.TAG, "onSubsidyLockEvent phoneId = " + phoneId);
                if (phoneId != 0) {
                    return;
                }
                AsyncResult ar = (AsyncResult) object;
                if (ar.exception == null && ar.result != null) {
                    int subsidyLock = ((Integer) ar.result).intValue();
                    SubsidyLockController.this.mSubsidyLock = subsidyLock == 1;
                }
                Log.d(SubsidyLockController.TAG, "SubsidyLock change to " + SubsidyLockController.this.mSubsidyLock);
                if (SubsidyLockController.this.mSubsidyLock) {
                    for (int i = 0; i < SubsidyLockController.this.mPhoneCount; i++) {
                        SubsidyLockController.this.setSimEnabledForOperator(i);
                        ImsManager imsManager = ImsManager.getInstance(SubsidyLockController.this.mContext, i);
                        if (!SubsidyLockController.this.isLowPriSim(i) && !imsManager.isEnhanced4gLteModeSettingEnabledByUser()) {
                            imsManager.setEnhanced4gLteModeSetting(true);
                        }
                    }
                    SubsidyLockController.this.resetPrimaryCardIfNeed();
                    SubsidyLockController.this.enableLte();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSimEnabledForOperator(int phoneId) {
        if (this.mRadioInteractor.getRealSimSatus(phoneId) != 0 && this.mSubsidyLock) {
            Log.d(TAG, "setSimEnabledForOperator: phoneId = " + phoneId);
            SubscriptionInfo subInfo = this.mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
            if (subInfo == null) {
                return;
            }
            String iccId = subInfo.getIccId();
            int subId = subInfo.getSubscriptionId();
            boolean subscriptionEnabled = subInfo.areUiccApplicationsEnabled();
            for (String jioIccId : JIO_ICCID) {
                if (iccId.startsWith(jioIccId) && !subscriptionEnabled) {
                    Log.d(TAG, "enable jio sim in subsidylock state");
                    this.mSubscriptionManager.setSubscriptionEnabled(subId, true);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmergencyOnly(int phoneId) {
        RadioInteractor radioInteractor;
        Log.d(TAG, "setEmergencyOnly for phone " + phoneId);
        if (this.mEmergencyOnly[phoneId] && "IMSI".equals(this.mSimStates[phoneId]) && !isLowPriSim(phoneId)) {
            Log.d(TAG, "disable emergency only for phoneId " + phoneId);
            this.mRadioInteractor.setEmergencyOnly(false, (Messenger) null, -1, phoneId);
            this.mEmergencyOnly[phoneId] = false;
            ImsManager.getInstance(this.mContext, phoneId).setWfcSetting(true);
            return;
        }
        if (SystemProperties.getBoolean("gsm.subsidy.lock.state", false)) {
            Log.d(TAG, "Lock State");
            if (!this.mEmergencyOnly[phoneId] && "IMSI".equals(this.mSimStates[phoneId]) && (radioInteractor = this.mRadioInteractor) != null && radioInteractor.getRealSimSatus(phoneId) != 0 && isLowPriSim(phoneId)) {
                Log.d(TAG, "set emergency only because AP Lock for phoneId " + phoneId);
                this.mRadioInteractor.setEmergencyOnly(true, (Messenger) null, -1, phoneId);
                this.mEmergencyOnly[phoneId] = true;
                ImsManager.getInstance(this.mContext, phoneId).setWfcSetting(false);
                return;
            }
            return;
        }
        Log.d(TAG, "Not Lock State");
        if (this.mEmergencyOnly[phoneId]) {
            Log.d(TAG, "disable emergency only for phoneId " + phoneId);
            this.mRadioInteractor.setEmergencyOnly(false, (Messenger) null, -1, phoneId);
            this.mEmergencyOnly[phoneId] = false;
            ImsManager.getInstance(this.mContext, phoneId).setWfcSetting(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableLte() {
        if (this.mRadioInteractor != null && this.mSubsidyLock) {
            new Thread(new Runnable() { // from class: com.android.internal.telephony.subsidy.SubsidyLockController.4
                @Override // java.lang.Runnable
                public void run() {
                    int defaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
                    SubsidyLockController.this.mTelephonyManager.setPreferredNetworkType(defaultDataSubId, 9);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPrimaryCardIfNeed() throws Resources.NotFoundException {
        if (!isDataSwitchAllowedForSubsidy()) {
            int dataPhoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            int primaryPhoneId = this.mPrimarySubConfig.getPreferredPrimaryCard();
            Log.d(TAG, "resetPrimaryCardIfNeed: current= " + dataPhoneId + " target= " + primaryPhoneId);
            int primarySubId = Integer.MAX_VALUE;
            int[] subIds = SubscriptionManager.getSubId(primaryPhoneId);
            boolean z = false;
            if (subIds != null && subIds.length > 0) {
                primarySubId = subIds[0];
            }
            Log.d(TAG, "resetPrimaryCardIfNeed: primarySubId= " + primarySubId);
            if (this.mSubscriptionManager.isActiveSubscriptionId(primarySubId) && this.mSubscriptionManager.isSubscriptionEnabled(primarySubId)) {
                z = true;
            }
            boolean isPrimarySubActive = z;
            if (isPrimarySubActive && dataPhoneId != primaryPhoneId) {
                if (!SubscriptionManager.isValidPhoneId(dataPhoneId) || isLowPriSim(dataPhoneId)) {
                    this.mSubscriptionManager.setDefaultDataSubId(primarySubId);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLowPriSim(int phoneId) {
        Log.d(TAG, "isLowPriSim: phoneId= " + phoneId);
        if (SubscriptionManager.isValidPhoneId(phoneId) && this.mTelephonyManager.hasIccCard(phoneId)) {
            return !isOperatorCardForSubsidy(phoneId);
        }
        return true;
    }

    public boolean isDataSwitchAllowedForSubsidy() {
        boolean isDataSwitchAllowed = true;
        if (this.mSupportSubsidyLock && this.mSubsidyLock) {
            Log.d(TAG, "subsidylock state, Not allowed to switch default data card if Jio SIM card and non-Jio SIM");
            int highPriSimCount = 0;
            List<SubscriptionInfo> subInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
            if (subInfoList != null && subInfoList.size() > 0) {
                for (int i = 0; i < subInfoList.size(); i++) {
                    if (!isLowPriSim(i)) {
                        highPriSimCount++;
                    }
                }
            }
            if (highPriSimCount > 0 && highPriSimCount < subInfoList.size()) {
                isDataSwitchAllowed = false;
            }
        }
        Log.d(TAG, "isDataSwitchAllowed = " + isDataSwitchAllowed);
        return isDataSwitchAllowed;
    }

    public boolean isDisableSimAllowedForSubsidy(int phoneId) {
        if (!this.mSupportSubsidyLock || !this.mSubsidyLock) {
            Log.d(TAG, "isDisableSimAllowed = true");
            return true;
        }
        Log.d(TAG, "subsidylock state, Not allowed to Disable jio Sim");
        return isLowPriSim(phoneId);
    }

    public boolean isDisableSimAllowedByIccId(String iccId) {
        if (!this.mSupportSubsidyLock || !this.mSubsidyLock) {
            return true;
        }
        for (String jioIccId : JIO_ICCID) {
            if (!TextUtils.isEmpty(iccId) && iccId.startsWith(jioIccId)) {
                return false;
            }
        }
        return true;
    }

    public boolean isOperatorCardForSubsidy(int phoneId) {
        Log.d(TAG, "isOperatorCardForSubsidy: phoneId = " + phoneId);
        int i = 0;
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            return false;
        }
        boolean isOperatorCard = false;
        long token = Binder.clearCallingIdentity();
        try {
            SubscriptionInfo subInfo = this.mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
            Log.d(TAG, "isOperatorCardForSubsidy: subInfo = " + subInfo);
            if (subInfo != null) {
                String iccId = subInfo.getIccId();
                String[] strArr = JIO_ICCID;
                int length = strArr.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String jioIccId = strArr[i];
                    if (TextUtils.isEmpty(iccId) || !iccId.startsWith(jioIccId)) {
                        i++;
                    } else {
                        isOperatorCard = true;
                        break;
                    }
                }
            }
            Binder.restoreCallingIdentity(token);
            Log.d(TAG, "isOperatorCardForSubsidy: isOperatorCard = " + isOperatorCard);
            return isOperatorCard;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(token);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastForSystemUI() {
        Intent intent = new Intent(ACTION_SUBSIDYLOCK_STATE_EXTEND);
        intent.setPackage("com.android.systemui");
        int currentState = SystemProperties.getInt("gsm.subsidylock.currentstate", -1);
        String currentStateKey = null;
        switch (currentState) {
            case 1:
                currentStateKey = INTENT_KEY_LOCK_SCREEN;
                break;
            case 2:
                currentStateKey = INTENT_KEY_SWITCH_SIM_SCREEN;
                break;
            case 3:
                currentStateKey = INTENT_KEY_UNLOCK_SCREEN;
                break;
            case 4:
                currentStateKey = INTENT_KEY_UNLOCK_PERMANENTLY;
                break;
            default:
                Log.d(TAG, "Unknown currentState:" + currentState);
                break;
        }
        if (currentStateKey != null) {
            Log.d(TAG, "sendBroadcast: " + currentStateKey);
            intent.putExtra(currentStateKey, true);
            intent.addFlags(536870912);
            this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
        }
    }

    public void popupDataEnabledForSubsidy(int subId) {
        if (!this.mSupportSubsidyLock || !this.mSubsidyLock) {
            return;
        }
        SharedPreferences preferences = this.mContext.getSharedPreferences(SUBSIDYLOCK_ICCID_PREFS_NAME, 0);
        String currentIccId = "";
        SubscriptionInfo subInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(subId);
        if (subInfo != null) {
            currentIccId = subInfo.getIccId();
        }
        boolean isCurrentSubJio = isOperatorCardForSubsidy(SubscriptionManager.getPhoneId(subId));
        Log.d(TAG, "current data sub " + subId + ", iccid = " + currentIccId + ", is jio? " + isCurrentSubJio);
        int preSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        String preIccId = preferences.getString(KEY_SUB, null);
        Log.d(TAG, "pre data sub " + preSubId + ", iccid = " + preIccId);
        boolean isSameSubJio = currentIccId.equals(preIccId);
        Log.d(TAG, "is same jio sub? " + isSameSubJio);
        if (isCurrentSubJio && !isSameSubJio) {
            Log.d(TAG, "save " + subId + "-" + currentIccId);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_SUB, currentIccId);
            editor.commit();
        }
        boolean dataEnabled = true;
        try {
            dataEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), new StringBuilder().append("mobile_data").append(subId).toString()) != 0;
        } catch (Settings.SettingNotFoundException e) {
        }
        Log.d(TAG, "data enabled? " + dataEnabled);
        if (subId != preSubId && isCurrentSubJio && !dataEnabled && !isSameSubJio) {
            Log.d(TAG, "enable data for JIO card");
            this.mPrimarySubMgr.setUserDataEnabledForSubsidy(subId, true);
            popupDataEnabledScreen();
        }
    }

    private void popupDataEnabledScreen() {
        Log.d(TAG, "popupDataEnabledScreen");
        Intent intent = new Intent().setComponent(new ComponentName("com.unisoc.phone", "com.unisoc.phone.subsidy.AutoEnableDataActivity")).addFlags(268435456);
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Unable to find data enabled prompt for restart activity: " + e);
        }
    }

    public static SubsidyLockController getInstance() {
        return mInstance;
    }
}