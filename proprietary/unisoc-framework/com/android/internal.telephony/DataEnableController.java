package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.List;

/* loaded from: classes.dex */
public class DataEnableController extends ContextWrapper {
    private static final String DATA_ENABLED_STATE_PROP = "gsm.data.setenabled";
    private static final String OVERLIMITFLAG = "persist.sys.overlimit.flag";
    static final String TAG = "DataEnableController";
    static DataEnableController mInstance;
    private Context mContext;
    private int mDefaultDataSubId;
    private boolean mNeedUpdateDataEnable;
    private BroadcastReceiver mReceiver;
    private ContentObserver mSetupWizardCompleteObserver;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyManager mTelephonyManager;

    public static DataEnableController getInstance() {
        return mInstance;
    }

    public static DataEnableController init(Context context) {
        DataEnableController dataEnableController;
        synchronized (DataEnableController.class) {
            if (mInstance == null) {
                mInstance = new DataEnableController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
            dataEnableController = mInstance;
        }
        return dataEnableController;
    }

    private DataEnableController(Context context) {
        super(context);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.DataEnableController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                    if (DataEnableController.this.isDeviceProvisioned()) {
                        DataEnableController.this.selectDataCardUpdate();
                    } else {
                        DataEnableController.this.mNeedUpdateDataEnable = true;
                    }
                }
            }
        };
        this.mSetupWizardCompleteObserver = new ContentObserver(new Handler()) { // from class: com.android.internal.telephony.DataEnableController.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d(DataEnableController.TAG, "mSetupWizardCompleteObserver : isDeviceProvisioned = " + DataEnableController.this.isDeviceProvisioned());
                if (DataEnableController.this.isDeviceProvisioned() && DataEnableController.this.mNeedUpdateDataEnable) {
                    DataEnableController.this.mNeedUpdateDataEnable = false;
                    DataEnableController.this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
                    int newSubId = SubscriptionManager.getDefaultDataSubscriptionId();
                    boolean isDataEnable = DataEnableController.this.getDataEnable();
                    if (isDataEnable != DataEnableController.this.mTelephonyManager.getDataEnabled(newSubId)) {
                        Log.d(DataEnableController.TAG, "setDataEnabled:" + isDataEnable);
                    }
                    DataEnableController.this.disableDataForOtherSubscriptions(newSubId);
                }
            }
        };
        mInstance = this;
        this.mContext = context;
        this.mSubscriptionManager = SubscriptionManager.from(context);
        this.mTelephonyManager = TelephonyManager.from(this.mContext);
        this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        IntentFilter filter = new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, filter);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mSetupWizardCompleteObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceProvisioned() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getDataEnable() {
        try {
            Log.d(TAG, "MOBILE_DATA=" + (Settings.Global.getInt(this.mContext.getContentResolver(), "mobile_data2147483645") != 0));
            return Settings.Global.getInt(this.mContext.getContentResolver(), "mobile_data2147483645") != 0;
        } catch (Settings.SettingNotFoundException e) {
            int defaultVal = "true".equalsIgnoreCase(SystemProperties.get("ro.com.android.mobiledata", "true")) ? 1 : 0;
            Settings.Global.putInt(this.mContext.getContentResolver(), "mobile_data2147483645", defaultVal);
            return defaultVal == 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectDataCardUpdate() {
        int newSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        int newPhoneId = SubscriptionManager.getPhoneId(newSubId);
        boolean isDataEnable = getDataEnable();
        Log.d(TAG, "selectDataCardUpdate: newPhoneId = " + newPhoneId + ",newSubId = " + newSubId);
        if (SubscriptionManager.isValidPhoneId(newPhoneId) && newSubId != this.mDefaultDataSubId) {
            if (SubscriptionManager.isValidSubscriptionId(newSubId)) {
                this.mDefaultDataSubId = newSubId;
            }
            boolean isOverLimit = SystemProperties.getBoolean(OVERLIMITFLAG + newSubId, false);
            Log.d(TAG, "isOverLimit:" + isOverLimit);
            if (isDataEnable != this.mTelephonyManager.getDataEnabled(newSubId) && !isOverLimit) {
                Log.d(TAG, "setDataEnabled:" + isDataEnable);
                this.mTelephonyManager.setDataEnabled(newSubId, isDataEnable);
            }
            disableDataForOtherSubscriptions(newSubId);
            SystemProperties.set(DATA_ENABLED_STATE_PROP, "true");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableDataForOtherSubscriptions(int subId) {
        SubscriptionManager subManager = SubscriptionManager.from(this.mContext);
        List<SubscriptionInfo> subInfoList = subManager.getAvailableSubscriptionInfoList();
        if (subInfoList != null) {
            for (SubscriptionInfo subInfo : subInfoList) {
                boolean needDataDisabled = "true".equals(SystemProperties.get(DATA_ENABLED_STATE_PROP, "true"));
                Log.d(TAG, "needDataDisabled is " + needDataDisabled);
                if (subInfo.getSubscriptionId() != subId && this.mTelephonyManager.getDataEnabled(subInfo.getSubscriptionId()) && needDataDisabled) {
                    Log.d(TAG, "disableDataForOtherSubscriptions =" + subInfo.getSubscriptionId());
                    Settings.Global.putInt(this.mContext.getContentResolver(), "mobile_data" + subInfo.getSubscriptionId(), 0);
                }
            }
        }
    }
}