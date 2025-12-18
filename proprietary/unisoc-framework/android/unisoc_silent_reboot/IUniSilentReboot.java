package android.unisoc.silent.reboot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUniSilentReboot extends IInterface {
    public static final String DESCRIPTOR = "android.unisoc.silent.reboot.IUniSilentReboot";

    boolean isDeviceMoving() throws RemoteException;

    boolean isPreparedForUnattendedReboot() throws RemoteException;

    void silentRebootForApply() throws RemoteException;

    public static class Default implements IUniSilentReboot {
        @Override // android.unisoc.silent.reboot.IUniSilentReboot
        public boolean isDeviceMoving() throws RemoteException {
            return false;
        }

        @Override // android.unisoc.silent.reboot.IUniSilentReboot
        public void silentRebootForApply() throws RemoteException {
        }

        @Override // android.unisoc.silent.reboot.IUniSilentReboot
        public boolean isPreparedForUnattendedReboot() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUniSilentReboot {
        static final int TRANSACTION_isDeviceMoving = 1;
        static final int TRANSACTION_isPreparedForUnattendedReboot = 3;
        static final int TRANSACTION_silentRebootForApply = 2;

        public Stub() {
            attachInterface(this, IUniSilentReboot.DESCRIPTOR);
        }

        public static IUniSilentReboot asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUniSilentReboot.DESCRIPTOR);
            if (iin != null && (iin instanceof IUniSilentReboot)) {
                return (IUniSilentReboot) iin;
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
                data.enforceInterface(IUniSilentReboot.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUniSilentReboot.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            boolean _result = isDeviceMoving();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            silentRebootForApply();
                            reply.writeNoException();
                            return true;
                        case 3:
                            boolean _result2 = isPreparedForUnattendedReboot();
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUniSilentReboot {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUniSilentReboot.DESCRIPTOR;
            }

            @Override // android.unisoc.silent.reboot.IUniSilentReboot
            public boolean isDeviceMoving() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniSilentReboot.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.unisoc.silent.reboot.IUniSilentReboot
            public void silentRebootForApply() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniSilentReboot.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.unisoc.silent.reboot.IUniSilentReboot
            public boolean isPreparedForUnattendedReboot() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniSilentReboot.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}