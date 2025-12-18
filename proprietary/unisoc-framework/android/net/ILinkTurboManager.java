package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ILinkTurboManager extends IInterface {
    public static final String DESCRIPTOR = "android.net.ILinkTurboManager";

    Network getNetwork(String str) throws RemoteException;

    void onBindedNetworkFinished(int i, int i2) throws RemoteException;

    int registerLinkTurboCallback(Messenger messenger, IBinder iBinder, String str) throws RemoteException;

    int registerMpHttpNetworkCallback(Messenger messenger, IBinder iBinder) throws RemoteException;

    void unregisterLinkTurboCallback(int i) throws RemoteException;

    void unregisterMpHttpNetworkCallback(int i) throws RemoteException;

    public static class Default implements ILinkTurboManager {
        @Override // android.net.ILinkTurboManager
        public int registerLinkTurboCallback(Messenger messenger, IBinder binder, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.net.ILinkTurboManager
        public void unregisterLinkTurboCallback(int networkRequestId) throws RemoteException {
        }

        @Override // android.net.ILinkTurboManager
        public void onBindedNetworkFinished(int netId, int pid) throws RemoteException {
        }

        @Override // android.net.ILinkTurboManager
        public int registerMpHttpNetworkCallback(Messenger messenger, IBinder binder) throws RemoteException {
            return 0;
        }

        @Override // android.net.ILinkTurboManager
        public void unregisterMpHttpNetworkCallback(int networkRequestId) throws RemoteException {
        }

        @Override // android.net.ILinkTurboManager
        public Network getNetwork(String interfaceName) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILinkTurboManager {
        static final int TRANSACTION_getNetwork = 6;
        static final int TRANSACTION_onBindedNetworkFinished = 3;
        static final int TRANSACTION_registerLinkTurboCallback = 1;
        static final int TRANSACTION_registerMpHttpNetworkCallback = 4;
        static final int TRANSACTION_unregisterLinkTurboCallback = 2;
        static final int TRANSACTION_unregisterMpHttpNetworkCallback = 5;

        public Stub() {
            attachInterface(this, ILinkTurboManager.DESCRIPTOR);
        }

        public static ILinkTurboManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILinkTurboManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ILinkTurboManager)) {
                return (ILinkTurboManager) iin;
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
                data.enforceInterface(ILinkTurboManager.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(ILinkTurboManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            Messenger _arg0 = (Messenger) data.readTypedObject(Messenger.CREATOR);
                            IBinder _arg1 = data.readStrongBinder();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            int _result = registerLinkTurboCallback(_arg0, _arg1, _arg2);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterLinkTurboCallback(_arg02);
                            reply.writeNoException();
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onBindedNetworkFinished(_arg03, _arg12);
                            reply.writeNoException();
                            return true;
                        case 4:
                            Messenger _arg04 = (Messenger) data.readTypedObject(Messenger.CREATOR);
                            IBinder _arg13 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            int _result2 = registerMpHttpNetworkCallback(_arg04, _arg13);
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterMpHttpNetworkCallback(_arg05);
                            reply.writeNoException();
                            return true;
                        case 6:
                            String _arg06 = data.readString();
                            data.enforceNoDataAvail();
                            Network _result3 = getNetwork(_arg06);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ILinkTurboManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILinkTurboManager.DESCRIPTOR;
            }

            @Override // android.net.ILinkTurboManager
            public int registerLinkTurboCallback(Messenger messenger, IBinder binder, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeTypedObject(messenger, 0);
                    _data.writeStrongBinder(binder);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.ILinkTurboManager
            public void unregisterLinkTurboCallback(int networkRequestId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeInt(networkRequestId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.ILinkTurboManager
            public void onBindedNetworkFinished(int netId, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeInt(pid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.ILinkTurboManager
            public int registerMpHttpNetworkCallback(Messenger messenger, IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeTypedObject(messenger, 0);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.ILinkTurboManager
            public void unregisterMpHttpNetworkCallback(int networkRequestId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeInt(networkRequestId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.ILinkTurboManager
            public Network getNetwork(String interfaceName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILinkTurboManager.DESCRIPTOR);
                    _data.writeString(interfaceName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    Network _result = (Network) _reply.readTypedObject(Network.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}