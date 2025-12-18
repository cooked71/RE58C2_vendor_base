package com.android.internal.telephony.simlock;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.SimStateTracker;
import com.android.unisoc.telephony.RadioInteractor;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OpSimLockController extends Handler implements SimStateTracker.OnSimStateChangedListener {
    public static final String ACTION_SHOW_OPERATOR_SIMLOCK = "unisoc.simlockintent.action.ACTION_SHOW_OPERATOR_SIMLOCK";
    public static final String ACTION_UPDATE_SIMLOCK_DISMISS_BUTTON = "unisoc.simlockintent.action.ACTION_UPDATE_SIMLOCK_DISMISS_BUTTON";
    public static final String ACTION_UPDATE_SIMLOCK_VISIBILITY = "unisoc.simlockintent.action.UPDATE_SIMLOCK_VISIBILITY";
    protected static final int DEFAULT_FORCED_DATA_PHONE_INDEX = 0;
    protected static final int DEFAULT_RESTRICTED_NETWORK_MODE = -1;
    public static final String INTENT_EXTRA_HIDE_DISMISS_BTN = "hide_dismiss_button";
    public static final String INTENT_EXTRA_HIDE_OPERATOR_DISMISS_BTN = "hide_operator_dismiss_button";
    public static final String INTENT_EXTRA_SHOW_OPERATOR_SIMLOCK = "show_operator_simlock";
    public static final String INTENT_EXTRA_SHOW_SIMLOCK = "show_simlock";
    public static final String INTENT_EXTRA_SIMLOCK_STATUS = "simlock_status";
    protected static final int PHONE_INDEX_0 = 0;
    protected static final int PHONE_INDEX_1 = 1;
    protected static final int SIMLOCK_DUMMY1_UNLOCK_COMB_LOCK_ONE_TIME = 1;
    protected static final int SIMLOCK_DUMMY2_ONE_SIMLOCK = 1;
    protected static final int SIMLOCK_DUMMY2_SLOT_DENDENCY_KIND_1 = 2;
    protected static final int SIMLOCK_DUMMY2_SLOT_DENDENCY_KIND_2 = 3;
    protected static final int SIMLOCK_DUMMY2_SLOT_DENDENCY_KIND_2_AND_EXPIRE_SIM = 7;
    protected static final int SIMLOCK_DUMMY3_SEPEARE_WHITELIST_CONFIG = 16;
    protected static final int SIMLOCK_DUMMY3_UNLOCK_RELATION_OR = 8;
    protected static final int SIMLOCK_STATUS_LOCKED = 1;
    protected static final int SIMLOCK_STATUS_UNLOCKED = 0;
    public static final boolean SUPPORT_V2 = false;
    public static final boolean SUPPORT_V4 = true;
    private static final String TAG = "OpSimLockController";
    protected static final int UNLOCK_NETWORK = 2;
    private static OpSimLockController mInstance;
    protected Context mContext;
    protected int mPhoneCount;
    protected RadioInteractor mRadioInteractor;
    protected SimStateTracker mSimStateTracker;
    protected SubscriptionManager mSubscriptionManager;
    protected TelephonyManager mTelephonyManager;
    protected ArrayList<String> mSimLockWhiteLists = new ArrayList<>();
    protected boolean mSimLockConfigMatch = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.simlock.OpSimLockController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                int phoneId = intent.getIntExtra("phone", -1);
                String simState = intent.getStringExtra("ss");
                OpSimLockController.this.notifySimStateChangedForSlot(phoneId, simState);
            }
        }
    };
    protected List<OnSimStateChangedListener> mSimStateChangedListeners = new ArrayList();
    protected List<OnRiServiceBindListener> mRiServiceBindListeners = new ArrayList();

    public interface OnRiServiceBindListener {
        void onRiServiceConnected(ComponentName componentName, IBinder iBinder);

        void onRiServiceDisconnected(ComponentName componentName);
    }

    public interface OnSimStateChangedListener {
        void onAllSimsDetectedEvent(boolean z);

        void onSimHotSwapedEvent(int i);

        void onSimStateChangedForSlotEvent(int i, String str);
    }

    public OpSimLockController(Context context) {
        this.mPhoneCount = 1;
        this.mContext = context;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mSubscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        this.mPhoneCount = this.mTelephonyManager.getActiveModemCount();
        SimStateTracker simStateTrackerInit = SimStateTracker.init(this.mContext);
        this.mSimStateTracker = simStateTrackerInit;
        simStateTrackerInit.addOnSimStateChangedListener(this);
        addRadioInteractorListener();
        IntentFilter filter = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    public static OpSimLockController init(Context context) {
        Log.d(TAG, "init");
        synchronized (OpSimLockController.class) {
            if (mInstance == null) {
                mInstance = createCustomizedController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static OpSimLockController getInstance() {
        return mInstance;
    }

    private static OpSimLockController createCustomizedController(Context context) {
        if (Resources.getSystem().getBoolean(134414383)) {
            return MtnSimLockController.create(context);
        }
        if (Resources.getSystem().getBoolean(134414384)) {
            return OrangeSimLockController.create(context);
        }
        if (Resources.getSystem().getBoolean(134414385)) {
            return TzTigoSimLockController.create(context);
        }
        if (Resources.getSystem().getBoolean(134414382)) {
            return AirtelSimLockController.create(context);
        }
        if (Resources.getSystem().getBoolean(134414386)) {
            return TrueSimLockController1.create(context);
        }
        if (Resources.getSystem().getBoolean(134414387)) {
            return TrueSimLockController2.create(context);
        }
        return new OpSimLockController(context);
    }

    @Override // com.android.internal.telephony.SimStateTracker.OnSimStateChangedListener
    public void onSimHotSwaped(int phoneId) {
        notifySimHotSwaped(phoneId);
    }

    @Override // com.android.internal.telephony.SimStateTracker.OnSimStateChangedListener
    public void onAllSimDetected(boolean isIccChanged) {
        notifyAllSimDetected(isIccChanged);
    }

    private void addRadioInteractorListener() {
        this.mContext.bindService(new Intent("com.android.unisoc.telephony.server.RADIOINTERACTOR_SERVICE").setPackage("com.android.unisoc.telephony.server"), new ServiceConnection() { // from class: com.android.internal.telephony.simlock.OpSimLockController.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (OpSimLockController.this.mRadioInteractor == null) {
                    OpSimLockController.this.mRadioInteractor = new RadioInteractor(OpSimLockController.this.mContext);
                }
                OpSimLockController.this.notifyRiServiceConnected(name, service);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                OpSimLockController.this.notifyRiServiceDisconnected(name);
            }
        }, 1);
    }

    public void addSimStateChangedListener(OnSimStateChangedListener listener) {
        if (this.mSimStateChangedListeners.contains(listener)) {
            return;
        }
        this.mSimStateChangedListeners.add(listener);
    }

    public void addRiServiceBindListener(OnRiServiceBindListener listener) {
        if (this.mRiServiceBindListeners.contains(listener)) {
            return;
        }
        this.mRiServiceBindListeners.add(listener);
    }

    private void notifySimHotSwaped(int phoneId) {
        Log.d(TAG, "notifySimHotSwaped: " + phoneId);
        for (OnSimStateChangedListener listener : this.mSimStateChangedListeners) {
            listener.onSimHotSwapedEvent(phoneId);
        }
    }

    private void notifyAllSimDetected(boolean isIccChanged) {
        Log.d(TAG, "notifyAllSimDetected: isIccChanged = " + isIccChanged);
        for (OnSimStateChangedListener listener : this.mSimStateChangedListeners) {
            listener.onAllSimsDetectedEvent(isIccChanged);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySimStateChangedForSlot(int phoneId, String simState) {
        Log.d(TAG, "notifySimStateChangedForSlot: " + phoneId + ", " + simState);
        for (OnSimStateChangedListener listener : this.mSimStateChangedListeners) {
            listener.onSimStateChangedForSlotEvent(phoneId, simState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRiServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "notifyRiServiceConnected");
        for (OnRiServiceBindListener listener : this.mRiServiceBindListeners) {
            listener.onRiServiceConnected(name, service);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRiServiceDisconnected(ComponentName name) {
        Log.d(TAG, "notifyRiServiceDisconnected");
        for (OnRiServiceBindListener listener : this.mRiServiceBindListeners) {
            listener.onRiServiceDisconnected(name);
        }
    }

    public boolean simLockConfigMatch() {
        return false;
    }

    public void addWhiteList(String simLockWhiteList) {
        String[] temp;
        if (!TextUtils.isEmpty(simLockWhiteList) && (temp = simLockWhiteList.split(",")) != null && temp.length > 2) {
            for (int i = 2; i < temp.length; i++) {
                Log.d(TAG, "add white list " + temp[i]);
                this.mSimLockWhiteLists.add(temp[i]);
            }
        }
    }

    public int getSimLockStatus(int unlockType, int phoneId) {
        RadioInteractor radioInteractor = this.mRadioInteractor;
        if (radioInteractor != null) {
            return radioInteractor.getSimLockStatus(unlockType, 0);
        }
        return 0;
    }

    public boolean isWhiteListCard(int phoneId) {
        return true;
    }

    public boolean isDefaultDataCardSwitchAllowed() {
        return true;
    }

    public int getOpPreferredPrimaryCard() {
        return -1;
    }

    public boolean isDataAllowedForSlot(int phoneId) {
        return true;
    }

    public int getRestrictedNetTypePhoneId() {
        return -1;
    }

    public boolean restrictedNetworkTypeNeeded(int phoneId) {
        return false;
    }

    public int getRestrictedNetworkType(int phoneId) {
        return -1;
    }

    public boolean isCallAllowedForSlot(int phoneId) {
        return true;
    }

    public boolean isSmsAllowedForSlot(int phoneId) {
        return true;
    }

    public int[] getSimlockDummys() {
        int[] dummys = new int[8];
        RadioInteractor radioInteractor = this.mRadioInteractor;
        if (radioInteractor != null) {
            int[] dummys2 = radioInteractor.getSimlockDummys(0);
            return dummys2;
        }
        return dummys;
    }

    public int getDummy1() {
        int[] dummys = getSimlockDummys();
        if (dummys.length <= 0) {
            return 0;
        }
        int dummy1 = dummys[0];
        return dummy1;
    }

    public int getDummy2() {
        int[] dummys = getSimlockDummys();
        if (dummys.length <= 1) {
            return 0;
        }
        int dummy2 = dummys[1];
        return dummy2;
    }

    public int getDummy3() {
        int[] dummys = getSimlockDummys();
        if (dummys.length <= 2) {
            return 0;
        }
        int dummy3 = dummys[2];
        return dummy3;
    }

    public boolean needShowOperatorSimLock() {
        return false;
    }

    public void showOperatorSimLock(boolean show) {
    }
}