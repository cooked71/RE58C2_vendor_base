package android.telephony;

import android.content.Context;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.telephony.IUniTelephony;

/* loaded from: classes.dex */
public class PrimarySubManager {
    private static final String TAG = "PrimarySubManager";
    private static PrimarySubManager sInstance;
    private Context mContext;

    public PrimarySubManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    public PrimarySubManager(Context context, int subId) {
        this.mContext = context;
    }

    public static PrimarySubManager from(Context context) {
        if (sInstance == null) {
            sInstance = new PrimarySubManager(context);
        }
        return sInstance;
    }

    public int getPreferredPrimaryCard() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getPreferredPrimaryCard();
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return -1;
        }
    }

    public long getRestrictedNetworkTypeBitMask(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1L;
            }
            return telephonyService.getRestrictedNetworkTypeBitMask(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return -1L;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return -1L;
        }
    }

    public boolean isNeedPopupPrimaryCardSettingPrompt() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isNeedPopupPrimaryCardSettingPrompt();
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return false;
        }
    }

    public boolean isRestrictPreference(int carrierlistType) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isRestrictPreference(carrierlistType);
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return false;
        }
    }

    public int getRestrictPreferencePhoneId(int carrierlistType) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getRestrictPreferencePhoneId(carrierlistType);
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return -1;
        }
    }

    public int getHomeExceptService(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getHomeExceptService(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return -1;
        }
    }

    public int getRomingExceptService(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getRomingExceptService(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException ex =" + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException ex =" + ex2);
            return -1;
        }
    }

    public boolean isWhiteListCard(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isWhiteListCard(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isWhiteListCard ex = " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isWhiteListCard ex = " + ex2);
            return true;
        }
    }

    public boolean isDefaultDataCardSwitchAllowed() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isDefaultDataCardSwitchAllowed();
        } catch (RemoteException ex) {
            log("RemoteException calling isDefaultDataCardSwitchAllowed ex= " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isDefaultDataCardSwitchAllowed ex = " + ex2);
            return true;
        }
    }

    public boolean isSubscriptionPersoEnabled(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isSubscriptionPersoEnabled(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isSubscriptionPersoEnabled ex = " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isSubscriptionPersoEnabled ex = " + ex2);
            return true;
        }
    }

    public boolean restrictedNetworkTypeNeeded(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.restrictedNetworkTypeNeeded(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling restrictedNetworkTypeNeeded ex = " + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling restrictedNetworkTypeNeeded ex = " + ex2);
            return false;
        }
    }

    public int getRestrictedNetTypePhoneId() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getRestrictedNetTypePhoneId();
        } catch (RemoteException ex) {
            log("RemoteException calling getRestrictedNetTypePhoneId ex = " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling getRestrictedNetTypePhoneId ex = " + ex2);
            return -1;
        }
    }

    public int getOpPreferredPrimaryCard() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return -1;
            }
            return telephonyService.getOpPreferredPrimaryCard();
        } catch (RemoteException ex) {
            log("RemoteException calling getOpPreferredPrimaryCard ex = " + ex);
            return -1;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling getOpPreferredPrimaryCard ex = " + ex2);
            return -1;
        }
    }

    public boolean isDataAllowedForSlot(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isDataAllowedForSlot(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isDataAllowedForSlot ex = " + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isDataAllowedForSlot ex = " + ex2);
            return false;
        }
    }

    public boolean isSmsAllowedForSlot(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isSmsAllowedForSlot(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isSmsAllowedForSlot ex = " + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isSmsAllowedForSlot ex = " + ex2);
            return false;
        }
    }

    public boolean isCallAllowedForSlot(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isCallAllowedForSlot(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isCallAllowedForSlot ex = " + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isCallAllowedForSlot + ex = " + ex2);
            return false;
        }
    }

    public boolean isDataRestrictBySimLock(int phoneId) throws Resources.NotFoundException {
        boolean mtnSimLockConfig = Resources.getSystem().getBoolean(134414383) && !supportSimLockV4();
        boolean airtelSimLockConfig = Resources.getSystem().getBoolean(134414382);
        if (mtnSimLockConfig || airtelSimLockConfig) {
            int primaryCardId = getOpPreferredPrimaryCard();
            if (SubscriptionManager.isValidPhoneId(primaryCardId) && primaryCardId != phoneId) {
                log("data restricted by simlock. operator primary card: " + primaryCardId);
                return true;
            }
        }
        boolean true1SimLockConfig = Resources.getSystem().getBoolean(134414386);
        boolean true2SimLockConfig = Resources.getSystem().getBoolean(134414387);
        if ((!true1SimLockConfig && !true2SimLockConfig) || isDataAllowedForSlot(phoneId)) {
            return false;
        }
        log("data restricted by TRUE simlock.");
        return true;
    }

    public boolean supportSimLockV4() {
        return true;
    }

    public boolean isDataSwitchAllowedForSubsidy() {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isDataSwitchAllowedForSubsidy();
        } catch (RemoteException ex) {
            log("RemoteException calling isDataSwitchAllowedForSubsidy ex= " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isDataSwitchAllowedForSubsidy ex = " + ex2);
            return true;
        }
    }

    public boolean isDisableSimAllowedForSubsidy(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isDisableSimAllowedForSubsidy(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isDisableSimAllowedForSubsidy ex = " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isDisableSimAllowedForSubsidy ex = " + ex2);
            return true;
        }
    }

    public boolean isDisableSimAllowedByIccId(String iccId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isDisableSimAllowedByIccId(iccId);
        } catch (RemoteException ex) {
            log("RemoteException calling isDisableSimAllowedByIccIdex ex = " + ex);
            return true;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isDisableSimAllowedByIccId ex = " + ex2);
            return true;
        }
    }

    public boolean isOperatorCardForSubsidy(int phoneId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return false;
            }
            return telephonyService.isOperatorCardForSubsidy(phoneId);
        } catch (RemoteException ex) {
            log("RemoteException calling isOperatorCardForSubsidy ex = " + ex);
            return false;
        } catch (NullPointerException ex2) {
            log("NullPointerException calling isOperatorCardForSubsidy ex = " + ex2);
            return false;
        }
    }

    public void setUserDataEnabledForSubsidy(int subId, boolean enabled) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return;
            }
            telephonyService.setUserDataEnabledForSubsidy(subId, enabled);
        } catch (RemoteException ex) {
            log("RemoteException calling setUserDataEnabledForSubsidy ex = " + ex);
        } catch (NullPointerException ex2) {
            log("NullPointerException calling setUserDataEnabledForSubsidy ex = " + ex2);
        }
    }

    public void popupDataEnabledForSubsidy(int subId) {
        try {
            IUniTelephony telephonyService = getIUniTelephony();
            if (telephonyService == null) {
                return;
            }
            telephonyService.popupDataEnabledForSubsidy(subId);
        } catch (RemoteException ex) {
            log("RemoteException calling popupDataEnabledForSubsidy ex = " + ex);
        } catch (NullPointerException ex2) {
            log("NullPointerException calling popupDataEnabledForSubsidy ex = " + ex2);
        }
    }

    private IUniTelephony getIUniTelephony() {
        return IUniTelephony.Stub.asInterface(ServiceManager.getService("uni_telephony"));
    }

    private void log(String message) {
        Rlog.d(TAG, message);
    }
}