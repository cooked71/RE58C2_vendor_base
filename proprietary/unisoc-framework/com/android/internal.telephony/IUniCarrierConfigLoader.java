package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUniCarrierConfigLoader extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IUniCarrierConfigLoader";

    PersistableBundle getConfigForSubId(int i, String str) throws RemoteException;

    PersistableBundle getConfigForSubIdWithFeature(int i, String str, String str2) throws RemoteException;

    void overrideConfig(int i, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    public static class Default implements IUniCarrierConfigLoader {
        @Override // com.android.internal.telephony.IUniCarrierConfigLoader
        public PersistableBundle getConfigForSubId(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IUniCarrierConfigLoader
        public PersistableBundle getConfigForSubIdWithFeature(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IUniCarrierConfigLoader
        public void overrideConfig(int subId, PersistableBundle overrides, boolean persistent) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUniCarrierConfigLoader {
        static final int TRANSACTION_getConfigForSubId = 1;
        static final int TRANSACTION_getConfigForSubIdWithFeature = 2;
        static final int TRANSACTION_overrideConfig = 3;

        public Stub() {
            attachInterface(this, IUniCarrierConfigLoader.DESCRIPTOR);
        }

        public static IUniCarrierConfigLoader asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUniCarrierConfigLoader.DESCRIPTOR);
            if (iin != null && (iin instanceof IUniCarrierConfigLoader)) {
                return (IUniCarrierConfigLoader) iin;
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
                data.enforceInterface(IUniCarrierConfigLoader.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUniCarrierConfigLoader.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            PersistableBundle _result = getConfigForSubId(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            String _arg12 = data.readString();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            PersistableBundle _result2 = getConfigForSubIdWithFeature(_arg02, _arg12, _arg2);
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            PersistableBundle _arg13 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                            boolean _arg22 = data.readBoolean();
                            data.enforceNoDataAvail();
                            overrideConfig(_arg03, _arg13, _arg22);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUniCarrierConfigLoader {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUniCarrierConfigLoader.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IUniCarrierConfigLoader
            public PersistableBundle getConfigForSubId(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniCarrierConfigLoader.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    PersistableBundle _result = (PersistableBundle) _reply.readTypedObject(PersistableBundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniCarrierConfigLoader
            public PersistableBundle getConfigForSubIdWithFeature(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniCarrierConfigLoader.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    PersistableBundle _result = (PersistableBundle) _reply.readTypedObject(PersistableBundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniCarrierConfigLoader
            public void overrideConfig(int subId, PersistableBundle overrides, boolean persistent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniCarrierConfigLoader.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeTypedObject(overrides, 0);
                    _data.writeBoolean(persistent);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}