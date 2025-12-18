package com.android.internal.telephony;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.unisoc.telephony.RadioInteractor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class SimStateTracker {
    public static final String ACTION_APN_SETTINGS = "android.settings.APN_SETTINGS";
    public static final String ACTION_CONNECTIVITY_CHANGE = "android.dmyk.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_SIM_STATE_CHANGED = "com.dmyk.android.telephony.action.SIM_STATE_CHANGED";
    public static final int DEFAULT_NOTIFICATION_ID_SIM = 1000;
    public static final String EXTRA_SHOW_FRAGMENT_AS_SUBSETTING = ":settings:show_fragment_as_subsetting";
    public static final String EXTRA_SIM_PHONEID = "com.dmyk.android.telephony.extra.SIM_PHONEID";
    public static final String EXTRA_SIM_STATE = "com.dmyk.android.telephony.extra.SIM_STATE";
    public static final String ICC_ID = "icc_id";
    public static final String ICC_ID_PREFS_NAME = "msms.info.iccid";
    public static final String PACKAGE_NAME = "com.sprd.opm";
    private static final int SIM_STATE_NULL = -1;
    private static final int SIM_STATE_UICC_DIABLED = 18;
    public static final String SUB_ID = "sub_id";
    private static SimStateTracker mInstance;
    private Context mContext;
    private NotificationManager mNotificationManager;
    private int mNotifyPhoneId;
    private int mNotifySubId;
    private int mPhoneCount;
    private PrimarySubConfig mPrimarySubConfig;
    private RadioInteractor mRadioInteractor;
    private boolean mShuttingDown;
    private int[] mSimState;
    private int mSimStateAbsentFlag;
    private int[] mSimStateLoadedFlag;
    private TelephonyManager mTeleMgr;
    private static boolean DBG = true;
    private static String TAG = "SimStateTracker";
    static final Uri PREFERAPN_NO_UPDATE_URI_USING_SUBID = Uri.parse("content://telephony/carriers/preferapn_no_update/subId/");
    static final Uri URL_RESTOREAPN_USING_SUBID = Uri.parse("content://telephony/carriers/restore/subId/");
    private List<OnSimStateChangedListener> mOnSimStateChangedListeners = new ArrayList();
    private boolean mIsNotify = true;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.SimStateTracker.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws Resources.NotFoundException {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if ("android.telephony.action.SIM_CARD_STATE_CHANGED".equals(action) || "android.telephony.action.SIM_APPLICATION_STATE_CHANGED".equals(action) || "android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                SimStateTracker.this.onSimStateChanged(intent);
                return;
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                SimStateTracker.this.mShuttingDown = true;
                return;
            }
            if ("android.telephony.action.MULTI_SIM_CONFIG_CHANGED".equals(action)) {
                SimStateTracker.this.logd("MULTI_SIM_CONFIG_CHANGED");
                SimStateTracker simStateTracker = SimStateTracker.this;
                simStateTracker.mPhoneCount = simStateTracker.mTeleMgr.getSupportedModemCount();
                Arrays.fill(SimStateTracker.this.mSimState, -1);
                return;
            }
            if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                SimStateTracker.this.logd("ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                SubscriptionManager subManager = SubscriptionManager.from(SimStateTracker.this.mContext);
                SimStateTracker.this.mNotifySubId = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", SubscriptionManager.getDefaultDataSubscriptionId());
                if (SubscriptionManager.isValidSubscriptionId(SimStateTracker.this.mNotifySubId)) {
                    SimStateTracker.this.mNotifyPhoneId = intent.getIntExtra("phone", subManager.getDefaultDataPhoneId());
                    boolean isPayStateSupport = SubscriptionManager.getResourcesForSubId(SimStateTracker.this.mContext, SimStateTracker.this.mNotifySubId).getBoolean(134414340);
                    SimStateTracker.this.logd("isPayStateSupport=" + isPayStateSupport + ",subId=" + SimStateTracker.this.mNotifySubId + ",phoneId=" + SimStateTracker.this.mNotifyPhoneId + ",mIsNotify=" + SimStateTracker.this.mIsNotify);
                    if (isPayStateSupport && SimStateTracker.this.mIsNotify) {
                        SimStateTracker.this.mIsNotify = false;
                        SimStateTracker simStateTracker2 = SimStateTracker.this;
                        simStateTracker2.updateNotification(simStateTracker2.mNotifySubId);
                    }
                }
            }
        }
    };
    private final Handler mHandler = new Handler();
    private PreferApnChangeObserver mPreferApnObserver = new PreferApnChangeObserver();
    private RestoreApnChangeObserver mRestoreApnObserver = new RestoreApnChangeObserver();

    public interface OnSimStateChangedListener {
        void onAllSimDetected(boolean z);

        void onSimHotSwaped(int i);
    }

    private SimStateTracker(Context context) {
        this.mPrimarySubConfig = PrimarySubConfig.init(context);
        this.mContext = context;
        this.mTeleMgr = (TelephonyManager) context.getSystemService("phone");
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        this.mContext.getContentResolver().registerContentObserver(Telephony.Carriers.CONTENT_URI, true, this.mPreferApnObserver);
        this.mContext.getContentResolver().registerContentObserver(Telephony.Carriers.CONTENT_URI, true, this.mRestoreApnObserver);
        int supportedModemCount = this.mTeleMgr.getSupportedModemCount();
        this.mPhoneCount = supportedModemCount;
        int[] iArr = new int[supportedModemCount];
        this.mSimState = iArr;
        Arrays.fill(iArr, -1);
        int[] iArr2 = new int[this.mPhoneCount];
        this.mSimStateLoadedFlag = iArr2;
        Arrays.fill(iArr2, -1);
        this.mSimStateAbsentFlag = 0;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addAction("android.telephony.action.SIM_CARD_STATE_CHANGED");
        filter.addAction("android.telephony.action.SIM_APPLICATION_STATE_CHANGED");
        filter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
        filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        filter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        context.registerReceiver(this.mReceiver, filter);
        this.mRadioInteractor = new RadioInteractor(context);
    }

    public static SimStateTracker init(Context context) {
        Rlog.d(TAG, "-- init --");
        synchronized (SimStateTracker.class) {
            if (mInstance == null) {
                mInstance = new SimStateTracker(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static SimStateTracker getInstance() {
        return mInstance;
    }

    public void dispose() {
        this.mSimState = null;
        this.mPhoneCount = 0;
    }

    public void reset() {
        int supportedModemCount = this.mTeleMgr.getSupportedModemCount();
        this.mPhoneCount = supportedModemCount;
        this.mSimState = new int[supportedModemCount];
    }

    public void addOnSimStateChangedListener(OnSimStateChangedListener listener) {
        if (!this.mOnSimStateChangedListeners.contains(listener)) {
            this.mOnSimStateChangedListeners.add(listener);
        }
    }

    public void removeOnSimStateChangedListener(OnSimStateChangedListener listener) {
        if (this.mOnSimStateChangedListeners.contains(listener)) {
            this.mOnSimStateChangedListeners.remove(listener);
        }
    }

    private void notifySimHotSwaped(int phoneId) {
        logd("notifySimHotSwaped: " + phoneId);
        this.mPrimarySubConfig.update();
        for (OnSimStateChangedListener listener : this.mOnSimStateChangedListeners) {
            listener.onSimHotSwaped(phoneId);
        }
    }

    private void notifyAllSimDetected(boolean isIccChanged) {
        logd("notifyAllSimDetected: " + isIccChanged);
        this.mPrimarySubConfig.update();
        for (OnSimStateChangedListener listener : this.mOnSimStateChangedListeners) {
            listener.onAllSimDetected(isIccChanged);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSimStateChanged(Intent intent) {
        NotificationManager notificationManager;
        int phoneId = intent.getIntExtra("phone", -1);
        int state = 0;
        if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
            String simState = intent.getStringExtra("ss");
            logd("handleSimStateChanged: " + phoneId + " " + simState);
            if ("NOT_READY".equals(simState)) {
                state = 6;
            } else if ("READY".equals(simState)) {
                state = 5;
            } else if ("ABSENT".equals(simState)) {
                logd("simstate = ABSENT, Cancel the notification" + phoneId);
                int i = this.mNotifyPhoneId;
                if (phoneId == i && (notificationManager = this.mNotificationManager) != null) {
                    notificationManager.cancel(i + DEFAULT_NOTIFICATION_ID_SIM);
                }
                Intent simLockAbsentIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://"));
                simLockAbsentIntent.addFlags(268435456);
                simLockAbsentIntent.putExtra("sim_status_change", "ABSENT");
                this.mContext.sendBroadcast(simLockAbsentIntent);
            } else if ("LOADED".equals(simState)) {
                int[] subIds = SubscriptionManager.getSubId(phoneId);
                SubscriptionManager mSubscriptionManager = SubscriptionManager.from(this.mContext);
                if (subIds != null && subIds.length > 0) {
                    int subId = subIds[0];
                    logd("phoneId = " + phoneId + "subId = " + subId + "oldsubId = " + subIds[subIds.length - 1]);
                    if (mSubscriptionManager.isActiveSubscriptionId(subId)) {
                        logd("simstate = INTENT_VALUE_ICC_LOADED");
                        state = 10;
                        Intent simLockLoadedIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://"));
                        simLockLoadedIntent.addFlags(268435456);
                        simLockLoadedIntent.putExtra("sim_status_change", "LOADED");
                        this.mContext.sendBroadcast(simLockLoadedIntent);
                    }
                }
            } else if ("LOCKED".equals(simState)) {
                logd("simstate = INTENT_VALUE_ICC_LOCKED");
                state = 1;
                Intent simLockLockedIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://"));
                simLockLockedIntent.addFlags(268435456);
                simLockLockedIntent.putExtra("sim_status_change", "LOCKED");
                this.mContext.sendBroadcast(simLockLockedIntent);
            }
        } else {
            state = intent.getIntExtra("android.telephony.extra.SIM_STATE", 0);
        }
        if (state != 0) {
            logd("handleSimStateChanged: " + phoneId + " " + state);
            if (this.mSimState == null) {
                this.mSimState = new int[this.mPhoneCount];
            }
            if (!SubscriptionManager.isValidPhoneId(phoneId)) {
                return;
            }
            if (state == 6 && !isUiccEnable(phoneId)) {
                logd("uicc is not enable");
                state = 18;
            }
            int[] iArr = this.mSimState;
            int oldState = iArr[phoneId];
            if (oldState == state) {
                logd("SIM state isn't changed actually.");
                return;
            }
            iArr[phoneId] = state;
            if (isAllSimDetected()) {
                boolean isIccChanged = handleIccCardChanged();
                logd("All SIM detected, ICC changed: " + isIccChanged);
                if (oldState != -1 && oldState != state && (1 == oldState || 1 == state)) {
                    logd("SIM hot swaped: " + phoneId);
                    notifySimHotSwaped(phoneId);
                    if (this.mPhoneCount == 2) {
                        int[] iArr2 = this.mSimStateLoadedFlag;
                        if (iArr2[phoneId] == -1 && 10 == this.mSimState[phoneId]) {
                            iArr2[phoneId] = phoneId;
                        }
                        broadcastSimStateChanged(phoneId);
                    }
                } else {
                    notifyAllSimDetected(isIccChanged);
                }
            }
            if (1 == this.mSimState[phoneId]) {
                this.mSimStateAbsentFlag |= 1 << phoneId;
            }
            logd("mSimStateAbsentFlag: " + this.mSimStateAbsentFlag);
            if (this.mSimStateAbsentFlag == (1 << this.mPhoneCount) - 1) {
                broadcastSimStateChanged(phoneId);
                this.mSimStateAbsentFlag = 0;
            }
            if (this.mPhoneCount == 2) {
                logd("mSimStateLoadedFlag :" + this.mSimStateLoadedFlag[phoneId]);
                int[] iArr3 = this.mSimStateLoadedFlag;
                if (iArr3[phoneId] == -1 && 10 == this.mSimState[phoneId]) {
                    iArr3[phoneId] = phoneId;
                    broadcastSimStateChanged(phoneId);
                }
            }
        }
    }

    private boolean isUiccEnable(int phoneId) {
        String iccid = this.mRadioInteractor.getIccIdFromIccStatus(phoneId);
        if (this.mTeleMgr.getSimState(phoneId) == 6 && !TextUtils.isEmpty(iccid)) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNotification(int subId) throws Resources.NotFoundException {
        Intent intent = new Intent(ACTION_APN_SETTINGS);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_AS_SUBSETTING, true);
        intent.putExtra(SUB_ID, subId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.mContext, subId, intent, 201326592);
        String channelid = String.valueOf(subId);
        Log.d(TAG, "channelid = " + channelid);
        NotificationChannel channel = new NotificationChannel(channelid, channelid, 5);
        this.mNotificationManager.createNotificationChannel(channel);
        String notice = SubscriptionManager.getResourcesForSubId(this.mContext, subId).getString(135004251);
        String content = SubscriptionManager.getResourcesForSubId(this.mContext, subId).getString(135004250);
        Notification.Builder builder = new Notification.Builder(this.mContext, channelid).setSmallIcon(R.drawable.stat_sys_warning).setWhen(0L).setContentTitle(notice).setContentText(content).setContentIntent(pendingIntent).setPriority(2).setVisibility(1);
        Notification notification = builder.build();
        notification.flags = 2 | notification.flags;
        if (SubscriptionManager.isValidPhoneId(this.mNotifyPhoneId)) {
            this.mNotificationManager.notify(this.mNotifyPhoneId + DEFAULT_NOTIFICATION_ID_SIM, notification);
        }
    }

    private boolean handleIccCardChanged() {
        boolean isIccChanged = false;
        if (!this.mShuttingDown) {
            SharedPreferences preferences = this.mContext.getSharedPreferences(ICC_ID_PREFS_NAME, 0);
            for (int i = 0; i < this.mPhoneCount; i++) {
                String lastIccId = preferences.getString(ICC_ID + i, null);
                String newIccId = getIccId(i);
                logd("[handleIccCardChanged] lastIccId = " + lastIccId + " newIccId = " + newIccId);
                if (!TextUtils.equals(lastIccId, newIccId)) {
                    if (!isSimLocked(i)) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(ICC_ID + i, newIccId);
                        editor.commit();
                        logd("[handleIccCardChanged] SIM " + i + " changed, save new iccid: " + newIccId);
                    }
                    isIccChanged = true;
                }
            }
        }
        return isIccChanged;
    }

    private String getIccId(int phoneId) {
        SubscriptionManager subManager = SubscriptionManager.from(this.mContext);
        SubscriptionInfo subInfo = subManager.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
        if (subInfo != null) {
            return subInfo.getIccId();
        }
        return null;
    }

    public boolean isAllSimDetected() {
        for (int phoneId = 0; phoneId < this.mPhoneCount; phoneId++) {
            if (!isSimDetected(phoneId)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllSimLoaded() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (10 != this.mSimState[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isSimLocked(int phoneId) {
        int i = this.mSimState[phoneId];
        return i == 2 || i == 3 || i == 4;
    }

    public boolean hasSimLocked() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            int i2 = this.mSimState[i];
            if (i2 == 2 || i2 == 3 || i2 == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllSimAbsent() {
        for (int phoneId = 0; phoneId < this.mPhoneCount; phoneId++) {
            if (hasIccCard(phoneId)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasIccCard(int phoneId) {
        int i = this.mSimState[phoneId];
        return (i == -1 || i == 1) ? false : true;
    }

    private boolean isSimDetected(int phoneId) {
        int i = this.mSimState[phoneId];
        return i == 1 || i == 10 || i == 0 || i == 2 || i == 3 || i == 4 || i == 7 || i == 18;
    }

    public int getSimState(int phoneId) {
        if (SubscriptionManager.isValidPhoneId(phoneId)) {
            return this.mSimState[phoneId];
        }
        return -1;
    }

    public boolean isAnySimNetworkLocked() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (this.mSimState[i] == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllSimNetworkLocked() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (this.mSimState[i] != 4) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logd(String msg) {
        if (DBG) {
            Rlog.d(TAG, msg);
        }
    }

    private void broadcastSimStateChanged(int phoneId) {
        int currentSimState = this.mTeleMgr.getSimState(phoneId);
        Intent dmyIntent = new Intent(ACTION_SIM_STATE_CHANGED);
        dmyIntent.setPackage(PACKAGE_NAME);
        dmyIntent.putExtra(EXTRA_SIM_PHONEID, phoneId);
        dmyIntent.putExtra(EXTRA_SIM_STATE, currentSimState);
        this.mContext.sendBroadcast(dmyIntent);
    }

    private class PreferApnChangeObserver extends ContentObserver {
        public PreferApnChangeObserver() {
            super(SimStateTracker.this.mHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            if (SubscriptionManager.isValidSubscriptionId(SimStateTracker.this.mNotifySubId)) {
                Uri uri = Uri.withAppendedPath(SimStateTracker.PREFERAPN_NO_UPDATE_URI_USING_SUBID, String.valueOf(SimStateTracker.this.mNotifySubId));
                Cursor cursor = SimStateTracker.this.mContext.getContentResolver().query(uri, new String[]{"_id", "name", "apn"}, null, null, "name ASC");
                if (cursor != null) {
                    try {
                        try {
                            if (cursor.getCount() > 0 && SimStateTracker.this.mNotificationManager != null) {
                                SimStateTracker.this.mNotificationManager.cancel(SimStateTracker.this.mNotifyPhoneId + SimStateTracker.DEFAULT_NOTIFICATION_ID_SIM);
                            }
                        } catch (Exception e) {
                            SimStateTracker.this.logd("exception on query: " + e);
                        }
                    } finally {
                        cursor.close();
                    }
                }
            }
        }
    }

    private class RestoreApnChangeObserver extends ContentObserver {
        public RestoreApnChangeObserver() {
            super(SimStateTracker.this.mHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            SimStateTracker.this.logd("Apn Restore Callback");
            if (SimStateTracker.this.mNotificationManager != null) {
                SimStateTracker.this.mNotificationManager.cancel(SimStateTracker.this.mNotifyPhoneId + SimStateTracker.DEFAULT_NOTIFICATION_ID_SIM);
            }
        }
    }
}