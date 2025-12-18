package android.net.wifi;

import android.net.wifi.IUWifiManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class UWifiManager extends UniWifiManager {
    private static final String TAG = "UniWifiManager";
    IUWifiManager mService = IUWifiManager.Stub.asInterface(ServiceManager.getService("uni_wifi"));

    public boolean isWlanPlusSupported() {
        try {
            return this.mService.isWlanPlusSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWlanPlusSupported : " + e);
            return false;
        }
    }

    public boolean isWlanPlusEnabled() {
        try {
            return this.mService.isWlanPlusEnabled();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWlanPlusEnabled : " + e);
            return false;
        }
    }

    public boolean setWlanPlusEnabled(boolean enabled) {
        try {
            return this.mService.setWlanPlusEnabled(enabled);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on setWlanPlusEnabled : " + e);
            return false;
        }
    }

    public boolean isWifiOnly() {
        try {
            return this.mService.isWifiOnly();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiOnly : " + e);
            return false;
        }
    }

    public boolean isRemoveWifiTetherForWifiOnly() {
        try {
            return this.mService.isRemoveWifiTetherForWifiOnly();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isRemoveWifiTetherForWifiOnly : " + e);
            return false;
        }
    }

    public int getWifiOnlyCountryCodeUpdatePolicy() {
        try {
            return this.mService.getWifiOnlyCountryCodeUpdatePolicy();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getWifiOnlyCountryCodeUpdatePolicy : " + e);
            return -1;
        }
    }

    public boolean isScanResultAddSecureSummarySupported() {
        try {
            return this.mService.isScanResultAddSecureSummarySupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isScanResultAddSecureSummarySupported : " + e);
            return false;
        }
    }

    public String getAddedScanResultSecureSummary(int singleLine, int[] securityTypes) {
        try {
            return this.mService.getAddedScanResultSecureSummary(singleLine, securityTypes);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getAddedScanResultSecureSummary : " + e);
            return "";
        }
    }

    public boolean isSettingsShowHotspot2AutoJoin() {
        try {
            return this.mService.isSettingsShowHotspot2AutoJoin();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isSettingsShowHotspot2AutoJoin : " + e);
            return false;
        }
    }

    public boolean isSettingsShowNetworkSuggestions() {
        try {
            return this.mService.isSettingsShowNetworkSuggestions();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isSettingsShowNetworkSuggestions : " + e);
            return false;
        }
    }

    public List<WifiNetworkSuggestion> getNetworkSuggestions() {
        try {
            return this.mService.getNetworkSuggestions();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getNetworkSuggestions : " + e);
            return Collections.emptyList();
        }
    }

    public boolean canModifyWifiConfig(int netId, String configKey) {
        try {
            return this.mService.canModifyWifiConfig(netId, configKey);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on canModifyWifiConfig : " + e);
            return true;
        }
    }

    public boolean canForgetWifiConfig(int netId, String configKey) {
        try {
            return this.mService.canForgetWifiConfig(netId, configKey);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on canForgetWifiConfig : " + e);
            return true;
        }
    }

    public boolean isSettingsP2pGroupPersistent() {
        try {
            return this.mService.isSettingsP2pGroupPersistent();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isSettingsP2pGroupPersistent : " + e);
            return true;
        }
    }

    public boolean isWifiTetherUnavailableIfAirplaneOn() {
        try {
            return this.mService.isWifiTetherUnavailableIfAirplaneOn();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherUnavailableIfAirplaneOn : " + e);
            return false;
        }
    }

    public int[] getWifiTetherAutoTurnOffIntervalSec() {
        try {
            return this.mService.getWifiTetherAutoTurnOffIntervalSec();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getWifiTetherAutoTurnOffIntervalSec : " + e);
            return null;
        }
    }

    public boolean isWifiTetherSettingShowBandSupported() {
        try {
            return this.mService.isWifiTetherSettingShowBandSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowBandSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingShowFreqSupported() {
        try {
            return this.mService.isWifiTetherSettingShowFreqSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowFreqSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingShowHiddenSsidSupported() {
        try {
            return this.mService.isWifiTetherSettingShowHiddenSsidSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowHiddenSsidSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingShowRandomMacSupported() {
        try {
            return this.mService.isWifiTetherSettingShowRandomMacSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowRandomMacSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingShowMaxClientNumberSupported() {
        try {
            return this.mService.isWifiTetherSettingShowMaxClientNumberSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowMaxClientNumberSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingShowConnectedClientSupported() {
        try {
            return this.mService.isWifiTetherSettingShowConnectedClientSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingShowConnectedClientSupported : " + e);
            return false;
        }
    }

    public boolean isWifiTetherSettingAllowedClientListSupported() {
        try {
            return this.mService.isWifiTetherSettingAllowedClientListSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isWifiTetherSettingAllowedClientListSupported : " + e);
            return false;
        }
    }

    public void addWifiTetherAllowedClient(WifiTetherWhiteClient client) {
        try {
            this.mService.addWifiTetherAllowedClient(client);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on addWifiTetherAllowedClient : " + e);
        }
    }

    public List<WifiTetherWhiteClient> getWifiTetherAllowedClientList() {
        try {
            return this.mService.getWifiTetherAllowedClientList();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getWifiTetherAllowedClientList : " + e);
            return Collections.emptyList();
        }
    }

    public Map<String, String[]> getEapMethodForSpecificSsid() {
        try {
            return this.mService.getEapMethodForSpecificSsid();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on getEapMethodForSpecificSsid : " + e);
            return Collections.emptyMap();
        }
    }

    public boolean isShowNetworkCategoryLabel() {
        try {
            return this.mService.isShowNetworkCategoryLabel();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isShowNetworkCategoryLabel : " + e);
            return false;
        }
    }

    public boolean isShowReconnectSwitch() {
        try {
            return this.mService.isShowReconnectSwitch();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isShowReconnectSwitch : " + e);
            return false;
        }
    }

    public boolean isAutoReconnectEnabled() {
        try {
            return this.mService.isAutoReconnectEnabled();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isAutoReconnectEnabled : " + e);
            return false;
        }
    }

    public void setAutoReconnectEnabled(boolean enabled) {
        try {
            this.mService.setAutoReconnectEnabled(enabled);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on setAutoReconnectEnabled : " + e);
        }
    }

    public boolean isShowNotificationSupported() {
        try {
            return this.mService.isShowNotificationSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isShowNotificationSupported : " + e);
            return false;
        }
    }

    public boolean isShowNotificationEnabled() {
        try {
            return this.mService.isShowNotificationEnabled();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on isShowNotificationEnabled : " + e);
            return false;
        }
    }

    public boolean setShowNotificationEnabled(boolean enabled) {
        try {
            return this.mService.setShowNotificationEnabled(enabled);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on setShowNotificationEnabled : " + e);
            return false;
        }
    }

    public String getCurrentWifiCountry() {
        try {
            return this.mService.getCurrentWifiCountry();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed on setShowNotificationEnabled : " + e);
            return "";
        }
    }
}