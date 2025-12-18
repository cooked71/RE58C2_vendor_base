package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;

/* loaded from: classes.dex */
public class TzTigoSimLockController extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final String TAG = "TzTigoimLockController";
    private static TzTigoSimLockController mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener[] mRadioInteractorCallbackListener;

    public TzTigoSimLockController(Context context) {
        super(context);
        Log.d(TAG, "init");
        this.mContext = context;
        this.mRadioInteractorCallbackListener = new RadioInteractorListener[this.mPhoneCount];
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static TzTigoSimLockController create(Context context) {
        synchronized (TzTigoSimLockController.class) {
            if (mInstance == null) {
                mInstance = new TzTigoSimLockController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static TzTigoSimLockController getInstance() {
        return mInstance;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimHotSwapedEvent(int phoneId) {
        Log.d(TAG, "onSimHotSwapedEvent: " + phoneId);
        boolean show = needShowOperatorSimLock();
        showOperatorSimLock(show);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onAllSimsDetectedEvent(boolean isIccChanged) {
        Log.d(TAG, "onAllSimsDetectedEvent: isIccChanged = " + isIccChanged);
        boolean show = needShowOperatorSimLock();
        showOperatorSimLock(show);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimStateChangedForSlotEvent(int phoneId, String simState) {
        Log.d(TAG, "onSimStateChangedForSlotEvent: phoneId = " + phoneId + ", simState = " + simState);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onRiServiceConnected");
        if (this.mRadioInteractor == null) {
            this.mRadioInteractor = new RadioInteractor(this.mContext);
        }
        String simLockWhiteList = this.mRadioInteractor.getSimlockWhitelist(2, 0);
        addWhiteList(simLockWhiteList);
        this.mSimLockConfigMatch = simLockConfigMatch();
        Log.d(TAG, "simlock config match: " + this.mSimLockConfigMatch);
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
    public void addWhiteList(String simLockWhiteList) {
        super.addWhiteList(simLockWhiteList);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isWhiteListCard(int phoneId) {
        Log.d(TAG, "isWhiteListCard for phoneId " + phoneId);
        boolean locked = this.mRadioInteractor != null && getSimLockStatus(2, 0) == 1;
        if (!locked) {
            Log.d(TAG, "not simlock state, return true");
            return true;
        }
        String simOperatorNumeric = this.mTelephonyManager.getSimOperatorNumericForPhone(phoneId);
        return this.mSimLockWhiteLists != null && this.mSimLockWhiteLists.contains(simOperatorNumeric);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean restrictedNetworkTypeNeeded(int phoneId) {
        return this.mSimLockConfigMatch && getSimLockStatus(2, 0) != 0 && phoneId == 1 && this.mSimStateTracker.isAllSimLoaded();
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public int getRestrictedNetworkType(int phoneId) {
        if (!this.mSimLockConfigMatch) {
            return -1;
        }
        return 1;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public void showOperatorSimLock(boolean showSimLock) {
        Log.d(TAG, "showOperatorSimLock: " + showSimLock);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_SHOW_OPERATOR_SIMLOCK);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_HIDE_OPERATOR_DISMISS_BTN, false);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean needShowOperatorSimLock() {
        return (this.mTelephonyManager.hasIccCard(0) && isWhiteListCard(0)) ? false : true;
    }
}