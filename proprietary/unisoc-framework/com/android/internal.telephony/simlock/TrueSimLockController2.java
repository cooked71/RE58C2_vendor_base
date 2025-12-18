package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.telephony.SubscriptionInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;

/* loaded from: classes.dex */
public class TrueSimLockController2 extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final int SIMLOCK_STATUS_UNLOCKED = 0;
    private static final String TAG = "TrueSimLockController2";
    private static TrueSimLockController2 mInstance;
    private Context mContext;
    private RadioInteractor mRadioInteractor;
    private boolean mSim2EmergencyOnly;
    private static String[] TRUE_MCCMNC_CODES = {"52000", "52004", "52099"};
    private static String THAILAND_ICCID_PREFIX = "8966";
    private static String[] TRUE_ICCID_CODES = {"896604", "896600", "896605"};

    public TrueSimLockController2(Context context) {
        super(context);
        Log.d(TAG, "init");
        this.mContext = context;
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static TrueSimLockController2 create(Context context) {
        synchronized (TrueSimLockController2.class) {
            if (mInstance == null) {
                mInstance = new TrueSimLockController2(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static TrueSimLockController2 getInstance() {
        return mInstance;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimHotSwapedEvent(int phoneId) {
        Log.d(TAG, "onSimHotSwapedEvent: " + phoneId);
        setEccModForSim2();
        showSimLockView(shouldShowLockView());
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onAllSimsDetectedEvent(boolean isIccChanged) {
        Log.d(TAG, "onAllSimsDetectedEvent: isIccChanged = " + isIccChanged);
        setEccModForSim2();
        showSimLockView(shouldShowLockView());
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
        int dummy3 = getDummy3();
        if (dummy3 != 16) {
            dummysMatch = false;
        }
        return locked && dummysMatch;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isDataAllowedForSlot(int phoneId) {
        if (!this.mSimLockConfigMatch || phoneId != 1) {
            return true;
        }
        boolean sim2Ready = this.mTelephonyManager.getSimState(1) == 5;
        return (sim2Ready && (isThaOtherSimByIccId(1) || (isThaOtherSimByIccId(0) && isThaTrueSim(1)))) ? false : true;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isCallAllowedForSlot(int phoneId) {
        if (this.mSimLockConfigMatch && phoneId == 1) {
            return isDataAllowedForSlot(1);
        }
        return true;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isSmsAllowedForSlot(int phoneId) {
        if (this.mSimLockConfigMatch && phoneId == 1) {
            return isDataAllowedForSlot(1);
        }
        return true;
    }

    private boolean isThaTrueSim(int phoneId) {
        String simOperatorNumeric = this.mTelephonyManager.getSimOperatorNumericForPhone(phoneId);
        Log.d(TAG, "isThaTrueSim: sim " + phoneId + " operator numeric: " + simOperatorNumeric);
        if (TextUtils.isEmpty(simOperatorNumeric)) {
            return false;
        }
        for (String mccmnc : TRUE_MCCMNC_CODES) {
            if (mccmnc.equals(simOperatorNumeric)) {
                return true;
            }
        }
        return false;
    }

    private boolean isThaOtherSimByIccId(int phoneId) {
        SubscriptionInfo subInfo = this.mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
        if (subInfo == null) {
            return false;
        }
        int count = 0;
        String iccId = subInfo.getIccId();
        for (String trueIccId : TRUE_ICCID_CODES) {
            if (!TextUtils.isEmpty(iccId) && iccId.startsWith(THAILAND_ICCID_PREFIX) && !iccId.startsWith(trueIccId)) {
                count++;
            }
        }
        boolean isThaOtherSim = count == TRUE_ICCID_CODES.length;
        Log.d(TAG, "isThaOtherSimByIccId: sim " + phoneId + " is Tha other sim? " + isThaOtherSim);
        return isThaOtherSim;
    }

    private void setEccModForSim2() {
        if (this.mRadioInteractor == null) {
            return;
        }
        boolean bothThaOtherSims = isThaOtherSimByIccId(0) && isThaOtherSimByIccId(1);
        if (bothThaOtherSims && !this.mSim2EmergencyOnly) {
            Log.d(TAG, "set ecc mode for sim2");
            this.mRadioInteractor.setEmergencyOnly(true, (Messenger) null, -1, 1);
            this.mSim2EmergencyOnly = true;
        } else if (!bothThaOtherSims && this.mSim2EmergencyOnly) {
            Log.d(TAG, "cancel ecc mode for sim2");
            this.mRadioInteractor.setEmergencyOnly(false, (Messenger) null, -1, 1);
            this.mSim2EmergencyOnly = false;
        }
    }

    private boolean shouldShowLockView() {
        if (this.mTelephonyManager.getSimState(0) != 4) {
            return false;
        }
        boolean isThaOtherSim = isThaOtherSimByIccId(0);
        return isThaOtherSim;
    }

    private void showSimLockView(boolean show) {
        Log.d(TAG, "showSimLockView: " + show);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_UPDATE_SIMLOCK_VISIBILITY);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SHOW_SIMLOCK, show);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }
}