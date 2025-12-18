package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface INetworkAdapterService extends IInterface {
    public static final String DESCRIPTOR = "android.net.INetworkAdapterService";

    int addAllowedListUidForTraffic(int i, boolean z) throws RemoteException;

    boolean bindAppUidToNetwork(int i, Network network) throws RemoteException;

    boolean bindDstIpToNetwork(String str, Network network) throws RemoteException;

    void deleteExtraIPv6Addr(String str, String str2) throws RemoteException;

    int doPingForVowifi(int i, String str, String str2) throws RemoteException;

    int enableTrafficLimit(boolean z, String str) throws RemoteException;

    int getMptcpStatus() throws RemoteException;

    int initTrafficLimit() throws RemoteException;

    void sendCmdsToDaemon(String str) throws RemoteException;

    void setDnsFilterEnabled(int i) throws RemoteException;

    void setEthernetInterface(int i) throws RemoteException;

    void setIPv6Mtu(String str, int i) throws RemoteException;

    int setMptcpStatus(boolean z) throws RemoteException;

    int setTrafficLimit(boolean z, int i) throws RemoteException;

    public static class Default implements INetworkAdapterService {
        @Override // android.net.INetworkAdapterService
        public void setIPv6Mtu(String iface, int ipv6Mtu) throws RemoteException {
        }

        @Override // android.net.INetworkAdapterService
        public void sendCmdsToDaemon(String cmd) throws RemoteException {
        }

        @Override // android.net.INetworkAdapterService
        public void setDnsFilterEnabled(int enabled) throws RemoteException {
        }

        @Override // android.net.INetworkAdapterService
        public void setEthernetInterface(int enabled) throws RemoteException {
        }

        @Override // android.net.INetworkAdapterService
        public void deleteExtraIPv6Addr(String ipv6Addr, String interfaceName) throws RemoteException {
        }

        @Override // android.net.INetworkAdapterService
        public boolean bindAppUidToNetwork(int uid, Network network) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkAdapterService
        public boolean bindDstIpToNetwork(String dstAddr, Network network) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkAdapterService
        public int getMptcpStatus() throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int setMptcpStatus(boolean enable) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int doPingForVowifi(int ipv4Flag, String srcIP, String dstIP) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int initTrafficLimit() throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int enableTrafficLimit(boolean enabled, String interfaceName) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int setTrafficLimit(boolean enabled, int bw) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkAdapterService
        public int addAllowedListUidForTraffic(int uid, boolean add) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkAdapterService {
        static final int TRANSACTION_addAllowedListUidForTraffic = 14;
        static final int TRANSACTION_bindAppUidToNetwork = 6;
        static final int TRANSACTION_bindDstIpToNetwork = 7;
        static final int TRANSACTION_deleteExtraIPv6Addr = 5;
        static final int TRANSACTION_doPingForVowifi = 10;
        static final int TRANSACTION_enableTrafficLimit = 12;
        static final int TRANSACTION_getMptcpStatus = 8;
        static final int TRANSACTION_initTrafficLimit = 11;
        static final int TRANSACTION_sendCmdsToDaemon = 2;
        static final int TRANSACTION_setDnsFilterEnabled = 3;
        static final int TRANSACTION_setEthernetInterface = 4;
        static final int TRANSACTION_setIPv6Mtu = 1;
        static final int TRANSACTION_setMptcpStatus = 9;
        static final int TRANSACTION_setTrafficLimit = 13;

        public Stub() {
            attachInterface(this, INetworkAdapterService.DESCRIPTOR);
        }

        public static INetworkAdapterService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INetworkAdapterService.DESCRIPTOR);
            if (iin != null && (iin instanceof INetworkAdapterService)) {
                return (INetworkAdapterService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(INetworkAdapterService.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(INetworkAdapterService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            setIPv6Mtu(_arg0, _arg1);
                            reply.writeNoException();
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            data.enforceNoDataAvail();
                            sendCmdsToDaemon(_arg02);
                            reply.writeNoException();
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            setDnsFilterEnabled(_arg03);
                            reply.writeNoException();
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            setEthernetInterface(_arg04);
                            reply.writeNoException();
                            return true;
                        case 5:
                            String _arg05 = data.readString();
                            String _arg12 = data.readString();
                            data.enforceNoDataAvail();
                            deleteExtraIPv6Addr(_arg05, _arg12);
                            reply.writeNoException();
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            Network _arg13 = (Network) data.readTypedObject(Network.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result = bindAppUidToNetwork(_arg06, _arg13);
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 7:
                            String _arg07 = data.readString();
                            Network _arg14 = (Network) data.readTypedObject(Network.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result2 = bindDstIpToNetwork(_arg07, _arg14);
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 8:
                            int _result3 = getMptcpStatus();
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 9:
                            boolean _arg08 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result4 = setMptcpStatus(_arg08);
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 10:
                            int _arg09 = data.readInt();
                            String _arg15 = data.readString();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            int _result5 = doPingForVowifi(_arg09, _arg15, _arg2);
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 11:
                            int _result6 = initTrafficLimit();
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 12:
                            boolean _arg010 = data.readBoolean();
                            String _arg16 = data.readString();
                            data.enforceNoDataAvail();
                            int _result7 = enableTrafficLimit(_arg010, _arg16);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 13:
                            boolean _arg011 = data.readBoolean();
                            int _arg17 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result8 = setTrafficLimit(_arg011, _arg17);
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 14:
                            int _arg012 = data.readInt();
                            boolean _arg18 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result9 = addAllowedListUidForTraffic(_arg012, _arg18);
                            reply.writeNoException();
                            reply.writeInt(_result9);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements INetworkAdapterService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INetworkAdapterService.DESCRIPTOR;
            }

            @Override // android.net.INetworkAdapterService
            public void setIPv6Mtu(String iface, int ipv6Mtu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(ipv6Mtu);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public void sendCmdsToDaemon(String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeString(cmd);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public void setDnsFilterEnabled(int enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeInt(enabled);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public void setEthernetInterface(int enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeInt(enabled);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public void deleteExtraIPv6Addr(String ipv6Addr, String interfaceName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeString(ipv6Addr);
                    _data.writeString(interfaceName);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public boolean bindAppUidToNetwork(int uid, Network network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeTypedObject(network, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public boolean bindDstIpToNetwork(String dstAddr, Network network) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeString(dstAddr);
                    _data.writeTypedObject(network, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int getMptcpStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int setMptcpStatus(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int doPingForVowifi(int ipv4Flag, String srcIP, String dstIP) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeInt(ipv4Flag);
                    _data.writeString(srcIP);
                    _data.writeString(dstIP);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int initTrafficLimit() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int enableTrafficLimit(boolean enabled, String interfaceName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeString(interfaceName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int setTrafficLimit(boolean enabled, int bw) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeInt(bw);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkAdapterService
            public int addAllowedListUidForTraffic(int uid, boolean add) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(INetworkAdapterService.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(add);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}