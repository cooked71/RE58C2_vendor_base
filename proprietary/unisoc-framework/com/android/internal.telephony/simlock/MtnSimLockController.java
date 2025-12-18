package com.android.internal.telephony.simlock;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.IBinder;
import android.os.Messenger;
import android.telephony.PrimarySubManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.MobileNetworkUtils;
import com.android.internal.telephony.simlock.OpSimLockController;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;

/* loaded from: classes.dex */
public class MtnSimLockController extends OpSimLockController implements OpSimLockController.OnSimStateChangedListener, OpSimLockController.OnRiServiceBindListener {
    private static final String TAG = "MtnSimLockController";
    private static final int UNLOCK_NETWORK = 2;
    private static MtnSimLockController mInstance;
    private Context mContext;
    private String[] mPlmnWhiteList;
    private RadioInteractorListener[] mRadioInteractorCallbackListener;
    private boolean mSim1Expired;

    private MtnSimLockController(Context context) {
        super(context);
        this.mPlmnWhiteList = new String[]{"65510", "65512"};
        this.mSim1Expired = false;
        Log.d(TAG, "init");
        this.mContext = context;
        this.mRadioInteractorCallbackListener = new RadioInteractorListener[this.mPhoneCount];
        addSimStateChangedListener(this);
        addRiServiceBindListener(this);
    }

    public static MtnSimLockController create(Context context) {
        synchronized (MtnSimLockController.class) {
            if (mInstance == null) {
                mInstance = new MtnSimLockController(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static MtnSimLockController getInstance() {
        return mInstance;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimHotSwapedEvent(int phoneId) {
        Log.d(TAG, "onSimHotSwapedEvent: " + phoneId);
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onAllSimsDetectedEvent(boolean isIccChanged) {
        Log.d(TAG, "onAllSimsDetectedEvent: isIccChanged" + isIccChanged);
        if (this.mSimLockConfigMatch && this.mSimStateTracker.isAnySimNetworkLocked()) {
            int cardCount = getPresentCardCount();
            boolean hideDismissBtn = cardCount > 1 ? this.mSimStateTracker.isAllSimNetworkLocked() : true;
            Log.d(TAG, "cardCount = " + cardCount + ", hideDismissBtn = " + hideDismissBtn);
            notifyDismissBtnUpdate(hideDismissBtn);
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnSimStateChangedListener
    public void onSimStateChangedForSlotEvent(int phoneId, String simState) {
        Log.d(TAG, "onSimStateChangedForSlotEvent: phoneId = " + phoneId + ", simState = " + simState);
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            return;
        }
        if ("IMSI".equals(simState) && phoneId == 1) {
            setEccModForExpireSim(1);
            return;
        }
        if ("ABSENT".equals(simState)) {
            if (phoneId == 0) {
                this.mSim1Expired = false;
            }
            if (this.mTelephonyManager.hasIccCard(1) && this.mRadioInteractor != null) {
                this.mRadioInteractor.setEmergencyOnly(false, (Messenger) null, 0, 1);
            }
        }
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
        if (!this.mSimLockConfigMatch) {
            return;
        }
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mRadioInteractorCallbackListener[i] = getRadioInteractorListener(i);
            this.mRadioInteractor.listen(this.mRadioInteractorCallbackListener[i], MobileNetworkUtils.RadioAccessFamily.RAF_GSM, false);
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController.OnRiServiceBindListener
    public void onRiServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onRiServiceDisconnected");
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mRadioInteractor.listen(this.mRadioInteractorCallbackListener[i], 0);
        }
    }

    private RadioInteractorListener getRadioInteractorListener(final int phoneId) {
        return new RadioInteractorListener(phoneId) { // from class: com.android.internal.telephony.simlock.MtnSimLockController.1
            public void onExpireSimEvent(Object object) {
                Log.d(MtnSimLockController.TAG, "onExpireSimEvent phoneId= " + phoneId);
                AsyncResult ar = (AsyncResult) object;
                if (ar.exception == null && ar.result != null) {
                    Integer phoneId2 = (Integer) ar.result;
                    Log.d(MtnSimLockController.TAG, "expire sim = " + phoneId2);
                    if (phoneId2.intValue() == 0) {
                        MtnSimLockController.this.mSim1Expired = true;
                        MtnSimLockController.this.setEccModForExpireSim(1);
                    }
                }
            }
        };
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public void addWhiteList(String simLockWhiteList) {
        super.addWhiteList(simLockWhiteList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEccModForExpireSim(int phoneId) {
        if (this.mSim1Expired) {
            boolean whiteListCard = isWhiteListCard(phoneId);
            if (!whiteListCard) {
                Log.d(TAG, "setEccModForExpireSim for sim" + phoneId);
                this.mRadioInteractor.setEmergencyOnly(true, (Messenger) null, 0, phoneId);
                showSimLockView(true);
            }
        }
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isWhiteListCard(int phoneId) {
        Log.d(TAG, "isWhiteListCard for phoneId " + phoneId);
        boolean z = false;
        if (this.mRadioInteractor != null && getSimLockStatus(2, 0) == 1) {
            z = true;
        }
        boolean isNetworkLock = z;
        if (!isNetworkLock) {
            Log.d(TAG, "isWhiteListCard: not simlock state, return true");
            return true;
        }
        return isWhiteListCard(phoneId, isNetworkLock);
    }

    private boolean isWhiteListCard(int phoneId, boolean networkLocked) {
        if (this.mSimLockWhiteLists == null || this.mSimLockWhiteLists.size() == 0) {
            Log.d(TAG, "isWhiteListCard: no white list");
            return true;
        }
        PrimarySubManager primarySubMgr = PrimarySubManager.from(this.mContext);
        if ((primarySubMgr != null && primarySubMgr.isSubscriptionPersoEnabled(phoneId)) || this.mTelephonyManager.getSimState(phoneId) == 4) {
            return false;
        }
        String simOperatorNumeric = this.mTelephonyManager.getSimOperatorNumericForPhone(phoneId);
        Log.d(TAG, "isWhiteListCard: simOperatorNumeric = " + simOperatorNumeric);
        if (!TextUtils.isEmpty(simOperatorNumeric)) {
            return this.mSimLockWhiteLists.contains(simOperatorNumeric);
        }
        return isWhiteListCardByImsi(phoneId);
    }

    private boolean isWhiteListCardByImsi(int phoneId) {
        Log.d(TAG, "isWhiteListCardByImsi:  " + phoneId);
        int subId = getSubId(phoneId);
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            String imsi = this.mTelephonyManager.getSubscriberId(subId);
            Log.d(TAG, "isWhiteListCardByImsi:  imsi = " + imsi);
            if (!TextUtils.isEmpty(imsi) && imsi.length() >= 5) {
                return this.mSimLockWhiteLists.contains(imsi.substring(0, 5)) || this.mSimLockWhiteLists.contains(imsi.substring(0, 6));
            }
        }
        return false;
    }

    private int getSubId(int slotIndex) {
        int[] subIds = SubscriptionManager.getSubId(slotIndex);
        if (subIds != null && subIds.length > 0) {
            return subIds[0];
        }
        return -1;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean isDefaultDataCardSwitchAllowed() {
        boolean z = this.mSimLockConfigMatch;
        return true;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public int getOpPreferredPrimaryCard() {
        if (!this.mSimLockConfigMatch) {
            return -1;
        }
        int maxPriorityPhoneId = 0;
        int currentDataCard = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        Log.d(TAG, "getOpPreferredPrimaryCard: current primary card " + currentDataCard);
        if (SubscriptionManager.isValidPhoneId(currentDataCard) && isWhiteListCard(currentDataCard)) {
            maxPriorityPhoneId = currentDataCard;
        } else {
            int i = 0;
            while (true) {
                if (i >= this.mPhoneCount) {
                    break;
                }
                if (!isWhiteListCard(i)) {
                    i++;
                } else {
                    maxPriorityPhoneId = i;
                    break;
                }
            }
        }
        Log.d(TAG, "getOpPreferredPrimaryCard: return " + maxPriorityPhoneId);
        return maxPriorityPhoneId;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean restrictedNetworkTypeNeeded(int phoneId) {
        if (phoneId == 1) {
            boolean z = this.mSimLockConfigMatch;
            return false;
        }
        return false;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public int getRestrictedNetworkType(int phoneId) {
        return -1;
    }

    @Override // com.android.internal.telephony.simlock.OpSimLockController
    public boolean simLockConfigMatch() {
        boolean plmnMatch = true;
        String[] strArr = this.mPlmnWhiteList;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String plmn = strArr[i];
            if (this.mSimLockWhiteLists.contains(plmn)) {
                i++;
            } else {
                plmnMatch = false;
                Log.d(TAG, "simLockConfigMatch: whitelist plmn not match!");
                break;
            }
        }
        boolean locked = true;
        if (getSimLockStatus(2, 0) == 0) {
            locked = false;
            Log.d(TAG, "simLockConfigMatch: simlock is unlocked!");
        }
        boolean dummysMatch = true;
        int dummy2 = getDummy2();
        if (dummy2 != 7 && dummy2 != 3) {
            dummysMatch = false;
            Log.d(TAG, "simLockConfigMatch: wrong dummy2 " + dummy2 + " !");
        }
        return plmnMatch && locked && dummysMatch;
    }

    private int getPresentCardCount() {
        int simCount = 0;
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (this.mTelephonyManager.hasIccCard(i)) {
                simCount++;
            }
        }
        return simCount;
    }

    private void notifyDismissBtnUpdate(boolean hideDismissBtn) {
        Log.d(TAG, "notifyDismissBtnUpdate: DISMISS button invisible " + hideDismissBtn);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_UPDATE_SIMLOCK_DISMISS_BUTTON);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_HIDE_DISMISS_BTN, hideDismissBtn);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }

    private void showSimLockView(boolean show) {
        Log.d(TAG, "showSimLockView: " + show);
        Intent intent = new Intent();
        intent.setAction(OpSimLockController.ACTION_UPDATE_SIMLOCK_VISIBILITY);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SHOW_SIMLOCK, show);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_HIDE_DISMISS_BTN, true);
        intent.putExtra(OpSimLockController.INTENT_EXTRA_SIMLOCK_STATUS, show ? 1 : 0);
        this.mContext.sendBroadcast(intent, "unisoc.permission.SIMLOCK_UPDATE");
    }
}