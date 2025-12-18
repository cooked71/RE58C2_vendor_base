package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;

/* loaded from: classes.dex */
public class OrangeSimLockController extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final String TAG = "OrangeSimLockController";
    private static final int UNLOCK_NETWORK = 2;
    private static OrangeSimLockController mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener[] mRadioInteractorCallbackListener;

    private OrangeSimLockController(Context context) {
        super(context);
        Log.d(TAG, "init");
        this.mContext = context;
        this.mRadioInteractorCallbackListener = new RadioInteractorListener[this.mPhoneCount];
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static OrangeSimLockController create(Context context) {
        synchronized (OrangeSimLockController.class) {
            if (mInstance == null) {
                mInstance = new OrangeSimLockController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static OrangeSimLockController getInstance() {
        return mInstance;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimHotSwapedEvent(int phoneId) {
        Log.d(TAG, "onSimHotSwapedEvent: " + phoneId);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onAllSimsDetectedEvent(boolean isIccChanged) {
        Log.d(TAG, "onAllSimsDetectedEvent: isIccChanged = " + isIccChanged);
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
        if (dummy2 != 1) {
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
    public int getRestrictedNetTypePhoneId() {
        if (!this.mSimLockConfigMatch) {
            return -1;
        }
        boolean z = false;
        if (this.mTelephonyManager.getSimState(0) == 5 && this.mTelephonyManager.getSimState(1) == 5) {
            z = true;
        }
        boolean bothSimsReady = z;
        if (!bothSimsReady || this.mRadioInteractor == null) {
            return -1;
        }
        int nonWhitelistSimCount = 0;
        int restrictedNetPhoneId = -1;
        int i = 0;
        while (true) {
            if (i >= this.mPhoneCount) {
                break;
            }
            if (isWhiteListCard(i)) {
                i++;
            } else {
                restrictedNetPhoneId = i;
                nonWhitelistSimCount = 0 + 1;
                break;
            }
        }
        if (nonWhitelistSimCount != 1) {
            return -1;
        }
        Log.d(TAG, "getRestrictedNetTypePhoneId: " + restrictedNetPhoneId);
        return restrictedNetPhoneId;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean restrictedNetworkTypeNeeded(int phoneId) {
        return this.mSimLockConfigMatch && SubscriptionManager.isValidPhoneId(phoneId) && phoneId == getRestrictedNetTypePhoneId();
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isWhiteListCard(int phoneId) {
        Log.d(TAG, "isWhiteListCard for phoneId " + phoneId);
        boolean locked = getSimLockStatus(2, 0) == 1;
        if (!locked) {
            Log.d(TAG, "isWhiteListCard: not simlock state");
            return true;
        }
        String simOperatorNumeric = this.mTelephonyManager.getSimOperatorNumericForPhone(phoneId);
        return this.mSimLockWhiteLists != null && this.mSimLockWhiteLists.contains(simOperatorNumeric);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public int getRestrictedNetworkType(int phoneId) {
        return this.mSimLockConfigMatch ? 1 : -1;
    }
}