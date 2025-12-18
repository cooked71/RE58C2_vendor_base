package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAudioServiceEx extends IInterface {
    public static final String DESCRIPTOR = "android.media.IAudioServiceEx";

    void setDeviceConnectionStateForFM(int i, int i2, String str, String str2, IBinder iBinder) throws RemoteException;

    void setForceUseSpeaker(int i, boolean z, IBinder iBinder, String str) throws RemoteException;

    public static class Default implements IAudioServiceEx {
        @Override // android.media.IAudioServiceEx
        public void setForceUseSpeaker(int usage, boolean on, IBinder cb, String callingPkg) throws RemoteException {
        }

        @Override // android.media.IAudioServiceEx
        public void setDeviceConnectionStateForFM(int device, int state, String device_address, String device_name, IBinder cb) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAudioServiceEx {
        static final int TRANSACTION_setDeviceConnectionStateForFM = 2;
        static final int TRANSACTION_setForceUseSpeaker = 1;

        public Stub() {
            attachInterface(this, IAudioServiceEx.DESCRIPTOR);
        }

        public static IAudioServiceEx asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAudioServiceEx.DESCRIPTOR);
            if (iin != null && (iin instanceof IAudioServiceEx)) {
                return (IAudioServiceEx) iin;
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
                data.enforceInterface(IAudioServiceEx.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IAudioServiceEx.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            boolean _arg1 = data.readBoolean();
                            IBinder _arg2 = data.readStrongBinder();
                            String _arg3 = data.readString();
                            data.enforceNoDataAvail();
                            setForceUseSpeaker(_arg0, _arg1, _arg2, _arg3);
                            reply.writeNoException();
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            String _arg22 = data.readString();
                            String _arg32 = data.readString();
                            IBinder _arg4 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            setDeviceConnectionStateForFM(_arg02, _arg12, _arg22, _arg32, _arg4);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IAudioServiceEx {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioServiceEx.DESCRIPTOR;
            }

            @Override // android.media.IAudioServiceEx
            public void setForceUseSpeaker(int usage, boolean on, IBinder cb, String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAudioServiceEx.DESCRIPTOR);
                    _data.writeInt(usage);
                    _data.writeBoolean(on);
                    _data.writeStrongBinder(cb);
                    _data.writeString(callingPkg);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioServiceEx
            public void setDeviceConnectionStateForFM(int device, int state, String device_address, String device_name, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAudioServiceEx.DESCRIPTOR);
                    _data.writeInt(device);
                    _data.writeInt(state);
                    _data.writeString(device_address);
                    _data.writeString(device_name);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}