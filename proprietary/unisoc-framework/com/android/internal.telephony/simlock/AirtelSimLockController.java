package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;

/* loaded from: classes.dex */
public class AirtelSimLockController extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final String TAG = "AirtelSimLockController";
    private static AirtelSimLockController mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener[] mRadioInteractorCallbackListener;

    public AirtelSimLockController(Context context) {
        super(context);
        Log.d(TAG, "init");
        this.mContext = context;
        this.mRadioInteractorCallbackListener = new RadioInteractorListener[this.mPhoneCount];
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static AirtelSimLockController create(Context context) {
        synchronized (AirtelSimLockController.class) {
            if (mInstance == null) {
                mInstance = new AirtelSimLockController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static AirtelSimLockController getInstance() {
        return mInstance;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimHotSwapedEvent(int phoneId) {
        Log.d(TAG, "onSimHotSwapedEvent: " + phoneId);
        showOperatorSimLock(needShowOperatorSimLock());
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onAllSimsDetectedEvent(boolean isIccChanged) {
        Log.d(TAG, "onAllSimsDetectedEvent: isIccChanged = " + isIccChanged);
        showOperatorSimLock(needShowOperatorSimLock());
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimStateChangedForSlotEvent(int phoneId, String simState) {
        Log.d(TAG, "onSimStateChangedForSlotEvent: phoneId = " + phoneId + ", simState = " + simState);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onRiServiceConnected");
        if (this.mRadioInteractor != null) {
            this.mRadioInteractor = new RadioInteractor(this.mContext);
        }
        this.mSimLockConfigMatch = simLockConfigMatch();
        if (!this.mSimLockConfigMatch) {
            Log.d(TAG, "simlock config not match");
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onRiServiceDisconnected");
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean simLockConfigMatch() {
        boolean locked = true;
        if (getSimLockStatus(2, 0) == 0) {
            locked = false;
            Log.d(TAG, "simLockConfigMatch: simlock is unlocked!");
        }
        boolean dummysMatch = true;
        int dummy2 = getDummy2();
        if (dummy2 != 2) {
            dummysMatch = false;
            Log.d(TAG, "simLockConfigMatch: wrong dummy2 " + dummy2 + "!");
        }
        return locked && dummysMatch;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isDefaultDataCardSwitchAllowed() {
        Log.d(TAG, "isDefaultDataCardSwitchAllowed");
        if (!this.mSimLockConfigMatch) {
            Log.d(TAG, "isDefaultDataCardSwitchAllowed: simlock config not match!");
            return true;
        }
        int primaryCardId = getOpPreferredPrimaryCard();
        if (SubscriptionManager.isValidPhoneId(primaryCardId)) {
            Log.d(TAG, "isDefaultDataCardSwitchAllowed：false. primaryCardId " + primaryCardId);
            return false;
        }
        Log.d(TAG, "isDefaultDataCardSwitchAllowed: true");
        return true;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public int getOpPreferredPrimaryCard() {
        Log.d(TAG, "getOpPreferredPrimaryCard");
        if (!this.mSimLockConfigMatch) {
            Log.d(TAG, "getOpPreferredPrimaryCard: simlock config not match!");
            return -1;
        }
        boolean bothSimsReady = this.mTelephonyManager.getSimState(0) == 5 && this.mTelephonyManager.getSimState(1) == 5;
        if (!bothSimsReady) {
            Log.d(TAG, "getOpPreferredPrimaryCard: invalid. bothSimsReady = false ");
            return -1;
        }
        Log.d(TAG, "getOpPreferredPrimaryCard: 0");
        return 0;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public void showOperatorSimLock(boolean showSimLock) {
        Log.d(TAG, "showOperatorSimLock: " + showSimLock);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_SHOW_OPERATOR_SIMLOCK);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SHOW_OPERATOR_SIMLOCK, showSimLock);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_HIDE_OPERATOR_DISMISS_BTN, true);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean needShowOperatorSimLock() {
        return (this.mTelephonyManager.hasIccCard(0) && isWhiteListCard(0)) ? false : true;
    }
}