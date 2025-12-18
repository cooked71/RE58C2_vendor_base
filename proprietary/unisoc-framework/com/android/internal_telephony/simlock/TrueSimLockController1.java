package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;

/* loaded from: classes.dex */
public class TrueSimLockController1 extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final String TAG = "TrueSimLockController1";
    private static TrueSimLockController1 mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener[] mRadioInteractorCallbackListener;

    public TrueSimLockController1(Context context) {
        super(context);
        Log.d(TAG, "init");
        this.mContext = context;
        this.mRadioInteractorCallbackListener = new RadioInteractorListener[this.mPhoneCount];
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static TrueSimLockController1 create(Context context) {
        synchronized (TrueSimLockController1.class) {
            if (mInstance == null) {
                mInstance = new TrueSimLockController1(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static TrueSimLockController1 getInstance() {
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
        if ("LOADED".equals(simState) && phoneId == 1 && !isTrueOperatorCard(phoneId)) {
            showSimLockView(1, true);
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean simLockConfigMatch() {
        boolean locked = true;
        if (getSimLockStatus(2, 0) == 0) {
            locked = false;
            Log.d(TAG, "simLockConfigMatch: simlock is unlocked!");
        }
        boolean dummysMatch = dummysMatch();
        return locked && dummysMatch;
    }

    private boolean dummysMatch() {
        int dummy1 = getDummy1();
        int dummy3 = getDummy3();
        if (dummy1 == 1 || dummy3 == 24) {
            return true;
        }
        Log.d(TAG, "dummysMatch: wrong dummys: " + dummy1 + ", " + dummy3);
        return false;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onRiServiceConnected");
        if (this.mRadioInteractor != null) {
            this.mRadioInteractor = new RadioInteractor(this.mContext);
        }
        this.mSimLockConfigMatch = simLockConfigMatch();
        Log.d(TAG, "simlock config match: " + this.mSimLockConfigMatch);
        if (!this.mSimLockConfigMatch) {
            return;
        }
        for (int i = 0; i < this.mPhoneCount; i++) {
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onRiServiceDisconnected");
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isDataAllowedForSlot(int phoneId) {
        if (!this.mSimLockConfigMatch || phoneId == 0) {
            return true;
        }
        return isTrueOperatorCard(phoneId);
    }

    private boolean isTrueOperatorCard(int phoneId) {
        String simOperatorNumeric = this.mTelephonyManager.getSimOperatorNumericForPhone(phoneId);
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        int[] subIds = SubscriptionManager.getSubId(phoneId);
        if (subIds == null || subIds.length < 1) {
            return false;
        }
        String gid1 = this.mTelephonyManager.getGroupIdLevel1();
        Log.d(TAG, "isTrueOperatorCard: sim " + phoneId + " operator numeric: " + simOperatorNumeric + ", gid1: " + gid1);
        return "52004".equals(simOperatorNumeric) || ("52000".equals(simOperatorNumeric) && !TextUtils.isEmpty(gid1) && gid1.equals("01FF"));
    }

    private void showSimLockView(int status, boolean show) {
        if (!this.mSimLockConfigMatch) {
            return;
        }
        Log.d(TAG, "showSimLockView: simlock status = " + status + ", show simlock? " + show);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_UPDATE_SIMLOCK_VISIBILITY);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SHOW_SIMLOCK, show);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SIMLOCK_STATUS, status);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }
}