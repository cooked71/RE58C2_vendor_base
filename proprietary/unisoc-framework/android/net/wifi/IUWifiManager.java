package android.net.wifi;

import android.net.wifi.IUWifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IUWifiManager extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.IUWifiManager";

    void addWifiTetherAllowedClient(WifiTetherWhiteClient wifiTetherWhiteClient) throws RemoteException;

    boolean canForgetWifiConfig(int i, String str) throws RemoteException;

    boolean canModifyWifiConfig(int i, String str) throws RemoteException;

    String getAddedScanResultSecureSummary(int i, int[] iArr) throws RemoteException;

    String getCurrentWifiCountry() throws RemoteException;

    Map<String, String[]> getEapMethodForSpecificSsid() throws RemoteException;

    List<WifiNetworkSuggestion> getNetworkSuggestions() throws RemoteException;

    int getWifiOnlyCountryCodeUpdatePolicy() throws RemoteException;

    List<WifiTetherWhiteClient> getWifiTetherAllowedClientList() throws RemoteException;

    int[] getWifiTetherAutoTurnOffIntervalSec() throws RemoteException;

    boolean isAutoReconnectEnabled() throws RemoteException;

    boolean isRemoveWifiTetherForWifiOnly() throws RemoteException;

    boolean isScanResultAddSecureSummarySupported() throws RemoteException;

    boolean isSettingsP2pGroupPersistent() throws RemoteException;

    boolean isSettingsShowHotspot2AutoJoin() throws RemoteException;

    boolean isSettingsShowNetworkSuggestions() throws RemoteException;

    boolean isShowNetworkCategoryLabel() throws RemoteException;

    boolean isShowNotificationEnabled() throws RemoteException;

    boolean isShowNotificationSupported() throws RemoteException;

    boolean isShowReconnectSwitch() throws RemoteException;

    boolean isWifiOnly() throws RemoteException;

    boolean isWifiTetherSettingAllowedClientListSupported() throws RemoteException;

    boolean isWifiTetherSettingShowBandSupported() throws RemoteException;

    boolean isWifiTetherSettingShowConnectedClientSupported() throws RemoteException;

    boolean isWifiTetherSettingShowFreqSupported() throws RemoteException;

    boolean isWifiTetherSettingShowHiddenSsidSupported() throws RemoteException;

    boolean isWifiTetherSettingShowMaxClientNumberSupported() throws RemoteException;

    boolean isWifiTetherSettingShowRandomMacSupported() throws RemoteException;

    boolean isWifiTetherUnavailableIfAirplaneOn() throws RemoteException;

    boolean isWlanPlusEnabled() throws RemoteException;

    boolean isWlanPlusSupported() throws RemoteException;

    void setAutoReconnectEnabled(boolean z) throws RemoteException;

    boolean setShowNotificationEnabled(boolean z) throws RemoteException;

    boolean setWlanPlusEnabled(boolean z) throws RemoteException;

    public static class Default implements IUWifiManager {
        @Override // android.net.wifi.IUWifiManager
        public boolean isWlanPlusSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWlanPlusEnabled() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean setWlanPlusEnabled(boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiOnly() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isRemoveWifiTetherForWifiOnly() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public int getWifiOnlyCountryCodeUpdatePolicy() throws RemoteException {
            return 0;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isScanResultAddSecureSummarySupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public String getAddedScanResultSecureSummary(int singleLine, int[] securityTypes) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isSettingsShowHotspot2AutoJoin() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isSettingsShowNetworkSuggestions() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public List<WifiNetworkSuggestion> getNetworkSuggestions() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean canModifyWifiConfig(int netId, String configKey) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean canForgetWifiConfig(int netId, String configKey) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isSettingsP2pGroupPersistent() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherUnavailableIfAirplaneOn() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public int[] getWifiTetherAutoTurnOffIntervalSec() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowBandSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowFreqSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowHiddenSsidSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowRandomMacSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowMaxClientNumberSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingShowConnectedClientSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isWifiTetherSettingAllowedClientListSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public void addWifiTetherAllowedClient(WifiTetherWhiteClient client) throws RemoteException {
        }

        @Override // android.net.wifi.IUWifiManager
        public List<WifiTetherWhiteClient> getWifiTetherAllowedClientList() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IUWifiManager
        public Map<String, String[]> getEapMethodForSpecificSsid() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isShowNetworkCategoryLabel() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isShowReconnectSwitch() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isAutoReconnectEnabled() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public void setAutoReconnectEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isShowNotificationSupported() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean isShowNotificationEnabled() throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public boolean setShowNotificationEnabled(boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.IUWifiManager
        public String getCurrentWifiCountry() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUWifiManager {
        static final int TRANSACTION_addWifiTetherAllowedClient = 24;
        static final int TRANSACTION_canForgetWifiConfig = 13;
        static final int TRANSACTION_canModifyWifiConfig = 12;
        static final int TRANSACTION_getAddedScanResultSecureSummary = 8;
        static final int TRANSACTION_getCurrentWifiCountry = 34;
        static final int TRANSACTION_getEapMethodForSpecificSsid = 26;
        static final int TRANSACTION_getNetworkSuggestions = 11;
        static final int TRANSACTION_getWifiOnlyCountryCodeUpdatePolicy = 6;
        static final int TRANSACTION_getWifiTetherAllowedClientList = 25;
        static final int TRANSACTION_getWifiTetherAutoTurnOffIntervalSec = 16;
        static final int TRANSACTION_isAutoReconnectEnabled = 29;
        static final int TRANSACTION_isRemoveWifiTetherForWifiOnly = 5;
        static final int TRANSACTION_isScanResultAddSecureSummarySupported = 7;
        static final int TRANSACTION_isSettingsP2pGroupPersistent = 14;
        static final int TRANSACTION_isSettingsShowHotspot2AutoJoin = 9;
        static final int TRANSACTION_isSettingsShowNetworkSuggestions = 10;
        static final int TRANSACTION_isShowNetworkCategoryLabel = 27;
        static final int TRANSACTION_isShowNotificationEnabled = 32;
        static final int TRANSACTION_isShowNotificationSupported = 31;
        static final int TRANSACTION_isShowReconnectSwitch = 28;
        static final int TRANSACTION_isWifiOnly = 4;
        static final int TRANSACTION_isWifiTetherSettingAllowedClientListSupported = 23;
        static final int TRANSACTION_isWifiTetherSettingShowBandSupported = 17;
        static final int TRANSACTION_isWifiTetherSettingShowConnectedClientSupported = 22;
        static final int TRANSACTION_isWifiTetherSettingShowFreqSupported = 18;
        static final int TRANSACTION_isWifiTetherSettingShowHiddenSsidSupported = 19;
        static final int TRANSACTION_isWifiTetherSettingShowMaxClientNumberSupported = 21;
        static final int TRANSACTION_isWifiTetherSettingShowRandomMacSupported = 20;
        static final int TRANSACTION_isWifiTetherUnavailableIfAirplaneOn = 15;
        static final int TRANSACTION_isWlanPlusEnabled = 2;
        static final int TRANSACTION_isWlanPlusSupported = 1;
        static final int TRANSACTION_setAutoReconnectEnabled = 30;
        static final int TRANSACTION_setShowNotificationEnabled = 33;
        static final int TRANSACTION_setWlanPlusEnabled = 3;

        public Stub() {
            attachInterface(this, IUWifiManager.DESCRIPTOR);
        }

        public static IUWifiManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUWifiManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IUWifiManager)) {
                return (IUWifiManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, final Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUWifiManager.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUWifiManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _result = isWlanPlusSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            boolean _result2 = isWlanPlusEnabled();
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 3:
                            boolean _arg0 = data.readBoolean();
                            data.enforceNoDataAvail();
                            boolean _result3 = setWlanPlusEnabled(_arg0);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            boolean _result4 = isWifiOnly();
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 5:
                            boolean _result5 = isRemoveWifiTetherForWifiOnly();
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 6:
                            int _result6 = getWifiOnlyCountryCodeUpdatePolicy();
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 7:
                            boolean _result7 = isScanResultAddSecureSummarySupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 8:
                            int _arg02 = data.readInt();
                            int[] _arg1 = data.createIntArray();
                            data.enforceNoDataAvail();
                            String _result8 = getAddedScanResultSecureSummary(_arg02, _arg1);
                            reply.writeNoException();
                            reply.writeString(_result8);
                            return true;
                        case 9:
                            boolean _result9 = isSettingsShowHotspot2AutoJoin();
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 10:
                            boolean _result10 = isSettingsShowNetworkSuggestions();
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 11:
                            List<WifiNetworkSuggestion> _result11 = getNetworkSuggestions();
                            reply.writeNoException();
                            reply.writeTypedList(_result11);
                            return true;
                        case 12:
                            int _arg03 = data.readInt();
                            String _arg12 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result12 = canModifyWifiConfig(_arg03, _arg12);
                            reply.writeNoException();
                            reply.writeBoolean(_result12);
                            return true;
                        case 13:
                            int _arg04 = data.readInt();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result13 = canForgetWifiConfig(_arg04, _arg13);
                            reply.writeNoException();
                            reply.writeBoolean(_result13);
                            return true;
                        case 14:
                            boolean _result14 = isSettingsP2pGroupPersistent();
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 15:
                            boolean _result15 = isWifiTetherUnavailableIfAirplaneOn();
                            reply.writeNoException();
                            reply.writeBoolean(_result15);
                            return true;
                        case 16:
                            int[] _result16 = getWifiTetherAutoTurnOffIntervalSec();
                            reply.writeNoException();
                            reply.writeIntArray(_result16);
                            return true;
                        case 17:
                            boolean _result17 = isWifiTetherSettingShowBandSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result17);
                            return true;
                        case 18:
                            boolean _result18 = isWifiTetherSettingShowFreqSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result18);
                            return true;
                        case 19:
                            boolean _result19 = isWifiTetherSettingShowHiddenSsidSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result19);
                            return true;
                        case 20:
                            boolean _result20 = isWifiTetherSettingShowRandomMacSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result20);
                            return true;
                        case 21:
                            boolean _result21 = isWifiTetherSettingShowMaxClientNumberSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result21);
                            return true;
                        case 22:
                            boolean _result22 = isWifiTetherSettingShowConnectedClientSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result22);
                            return true;
                        case 23:
                            boolean _result23 = isWifiTetherSettingAllowedClientListSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result23);
                            return true;
                        case 24:
                            WifiTetherWhiteClient _arg05 = (WifiTetherWhiteClient) data.readTypedObject(WifiTetherWhiteClient.CREATOR);
                            data.enforceNoDataAvail();
                            addWifiTetherAllowedClient(_arg05);
                            reply.writeNoException();
                            return true;
                        case 25:
                            List<WifiTetherWhiteClient> _result24 = getWifiTetherAllowedClientList();
                            reply.writeNoException();
                            reply.writeTypedList(_result24);
                            return true;
                        case 26:
                            Map<String, String[]> _result25 = getEapMethodForSpecificSsid();
                            reply.writeNoException();
                            if (_result25 == null) {
                                reply.writeInt(-1);
                            } else {
                                reply.writeInt(_result25.size());
                                _result25.forEach(new BiConsumer() { // from class: android.net.wifi.IUWifiManager$Stub$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        IUWifiManager.Stub.lambda$onTransact$0(reply, (String) obj, (String[]) obj2);
                                    }
                                });
                            }
                            return true;
                        case 27:
                            boolean _result26 = isShowNetworkCategoryLabel();
                            reply.writeNoException();
                            reply.writeBoolean(_result26);
                            return true;
                        case 28:
                            boolean _result27 = isShowReconnectSwitch();
                            reply.writeNoException();
                            reply.writeBoolean(_result27);
                            return true;
                        case 29:
                            boolean _result28 = isAutoReconnectEnabled();
                            reply.writeNoException();
                            reply.writeBoolean(_result28);
                            return true;
                        case 30:
                            boolean _arg06 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setAutoReconnectEnabled(_arg06);
                            reply.writeNoException();
                            return true;
                        case 31:
                            boolean _result29 = isShowNotificationSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result29);
                            return true;
                        case 32:
                            boolean _result30 = isShowNotificationEnabled();
                            reply.writeNoException();
                            reply.writeBoolean(_result30);
                            return true;
                        case 33:
                            boolean _arg07 = data.readBoolean();
                            data.enforceNoDataAvail();
                            boolean _result31 = setShowNotificationEnabled(_arg07);
                            reply.writeNoException();
                            reply.writeBoolean(_result31);
                            return true;
                        case TRANSACTION_getCurrentWifiCountry /* 34 */:
                            String _result32 = getCurrentWifiCountry();
                            reply.writeNoException();
                            reply.writeString(_result32);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel reply, String k, String[] v) {
            reply.writeString(k);
            reply.writeStringArray(v);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IUWifiManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUWifiManager.DESCRIPTOR;
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWlanPlusSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWlanPlusEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean setWlanPlusEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiOnly() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isRemoveWifiTetherForWifiOnly() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public int getWifiOnlyCountryCodeUpdatePolicy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isScanResultAddSecureSummarySupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public String getAddedScanResultSecureSummary(int singleLine, int[] securityTypes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeInt(singleLine);
                    _data.writeIntArray(securityTypes);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isSettingsShowHotspot2AutoJoin() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isSettingsShowNetworkSuggestions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public List<WifiNetworkSuggestion> getNetworkSuggestions() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    List<WifiNetworkSuggestion> _result = _reply.createTypedArrayList(WifiNetworkSuggestion.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean canModifyWifiConfig(int netId, String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(configKey);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean canForgetWifiConfig(int netId, String configKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeString(configKey);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isSettingsP2pGroupPersistent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherUnavailableIfAirplaneOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public int[] getWifiTetherAutoTurnOffIntervalSec() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowBandSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowFreqSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowHiddenSsidSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowRandomMacSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowMaxClientNumberSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingShowConnectedClientSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isWifiTetherSettingAllowedClientListSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public void addWifiTetherAllowedClient(WifiTetherWhiteClient client) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeTypedObject(client, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public List<WifiTetherWhiteClient> getWifiTetherAllowedClientList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    List<WifiTetherWhiteClient> _result = _reply.createTypedArrayList(WifiTetherWhiteClient.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public Map<String, String[]> getEapMethodForSpecificSsid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, String[]> _result = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.net.wifi.IUWifiManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IUWifiManager.Stub.Proxy.lambda$getEapMethodForSpecificSsid$0(_reply, _result, i);
                        }
                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getEapMethodForSpecificSsid$0(Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                String[] v = _reply.createStringArray();
                _result.put(k, v);
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isShowNetworkCategoryLabel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isShowReconnectSwitch() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isAutoReconnectEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public void setAutoReconnectEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isShowNotificationSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean isShowNotificationEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public boolean setShowNotificationEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.IUWifiManager
            public String getCurrentWifiCountry() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUWifiManager.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getCurrentWifiCountry, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}