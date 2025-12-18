package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUniTelephony extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IUniTelephony";

    int getHomeExceptService(int i) throws RemoteException;

    int getOpPreferredPrimaryCard() throws RemoteException;

    int getPreferredPrimaryCard() throws RemoteException;

    int getRestrictPreferencePhoneId(int i) throws RemoteException;

    int getRestrictedNetTypePhoneId() throws RemoteException;

    long getRestrictedNetworkTypeBitMask(int i) throws RemoteException;

    int getRomingExceptService(int i) throws RemoteException;

    boolean isCallAllowedForSlot(int i) throws RemoteException;

    boolean isDataAllowedForSlot(int i) throws RemoteException;

    boolean isDataSwitchAllowedForSubsidy() throws RemoteException;

    boolean isDefaultDataCardSwitchAllowed() throws RemoteException;

    boolean isDisableSimAllowedByIccId(String str) throws RemoteException;

    boolean isDisableSimAllowedForSubsidy(int i) throws RemoteException;

    boolean isNeedPopupPrimaryCardSettingPrompt() throws RemoteException;

    boolean isOperatorCardForSubsidy(int i) throws RemoteException;

    boolean isRestrictPreference(int i) throws RemoteException;

    boolean isSmsAllowedForSlot(int i) throws RemoteException;

    boolean isSubscriptionPersoEnabled(int i) throws RemoteException;

    boolean isTestUsim(int i) throws RemoteException;

    boolean isWhiteListCard(int i) throws RemoteException;

    void popupDataEnabledForSubsidy(int i) throws RemoteException;

    boolean restrictedNetworkTypeNeeded(int i) throws RemoteException;

    void setUserDataEnabledForSubsidy(int i, boolean z) throws RemoteException;

    public static class Default implements IUniTelephony {
        @Override // com.android.internal.telephony.IUniTelephony
        public int getPreferredPrimaryCard() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public long getRestrictedNetworkTypeBitMask(int phoneId) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isNeedPopupPrimaryCardSettingPrompt() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isRestrictPreference(int carrierlistType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public int getRestrictPreferencePhoneId(int carrierlistType) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public int getHomeExceptService(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public int getRomingExceptService(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isWhiteListCard(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isDefaultDataCardSwitchAllowed() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isSubscriptionPersoEnabled(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean restrictedNetworkTypeNeeded(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public int getRestrictedNetTypePhoneId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public int getOpPreferredPrimaryCard() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isDataAllowedForSlot(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isSmsAllowedForSlot(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isCallAllowedForSlot(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isTestUsim(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isDataSwitchAllowedForSubsidy() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isDisableSimAllowedForSubsidy(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isDisableSimAllowedByIccId(String iccId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public boolean isOperatorCardForSubsidy(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public void setUserDataEnabledForSubsidy(int subId, boolean enabled) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IUniTelephony
        public void popupDataEnabledForSubsidy(int subId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUniTelephony {
        static final int TRANSACTION_getHomeExceptService = 6;
        static final int TRANSACTION_getOpPreferredPrimaryCard = 13;
        static final int TRANSACTION_getPreferredPrimaryCard = 1;
        static final int TRANSACTION_getRestrictPreferencePhoneId = 5;
        static final int TRANSACTION_getRestrictedNetTypePhoneId = 12;
        static final int TRANSACTION_getRestrictedNetworkTypeBitMask = 2;
        static final int TRANSACTION_getRomingExceptService = 7;
        static final int TRANSACTION_isCallAllowedForSlot = 16;
        static final int TRANSACTION_isDataAllowedForSlot = 14;
        static final int TRANSACTION_isDataSwitchAllowedForSubsidy = 18;
        static final int TRANSACTION_isDefaultDataCardSwitchAllowed = 9;
        static final int TRANSACTION_isDisableSimAllowedByIccId = 20;
        static final int TRANSACTION_isDisableSimAllowedForSubsidy = 19;
        static final int TRANSACTION_isNeedPopupPrimaryCardSettingPrompt = 3;
        static final int TRANSACTION_isOperatorCardForSubsidy = 21;
        static final int TRANSACTION_isRestrictPreference = 4;
        static final int TRANSACTION_isSmsAllowedForSlot = 15;
        static final int TRANSACTION_isSubscriptionPersoEnabled = 10;
        static final int TRANSACTION_isTestUsim = 17;
        static final int TRANSACTION_isWhiteListCard = 8;
        static final int TRANSACTION_popupDataEnabledForSubsidy = 23;
        static final int TRANSACTION_restrictedNetworkTypeNeeded = 11;
        static final int TRANSACTION_setUserDataEnabledForSubsidy = 22;

        public Stub() {
            attachInterface(this, IUniTelephony.DESCRIPTOR);
        }

        public static IUniTelephony asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUniTelephony.DESCRIPTOR);
            if (iin != null && (iin instanceof IUniTelephony)) {
                return (IUniTelephony) iin;
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
                data.enforceInterface(IUniTelephony.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUniTelephony.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _result = getPreferredPrimaryCard();
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            long _result2 = getRestrictedNetworkTypeBitMask(_arg0);
                            reply.writeNoException();
                            reply.writeLong(_result2);
                            return true;
                        case 3:
                            boolean _result3 = isNeedPopupPrimaryCardSettingPrompt();
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result4 = isRestrictPreference(_arg02);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 5:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result5 = getRestrictPreferencePhoneId(_arg03);
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 6:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result6 = getHomeExceptService(_arg04);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 7:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result7 = getRomingExceptService(_arg05);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 8:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result8 = isWhiteListCard(_arg06);
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        case 9:
                            boolean _result9 = isDefaultDataCardSwitchAllowed();
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 10:
                            int _arg07 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result10 = isSubscriptionPersoEnabled(_arg07);
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 11:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result11 = restrictedNetworkTypeNeeded(_arg08);
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 12:
                            int _result12 = getRestrictedNetTypePhoneId();
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            return true;
                        case 13:
                            int _result13 = getOpPreferredPrimaryCard();
                            reply.writeNoException();
                            reply.writeInt(_result13);
                            return true;
                        case 14:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result14 = isDataAllowedForSlot(_arg09);
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 15:
                            int _arg010 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result15 = isSmsAllowedForSlot(_arg010);
                            reply.writeNoException();
                            reply.writeBoolean(_result15);
                            return true;
                        case 16:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result16 = isCallAllowedForSlot(_arg011);
                            reply.writeNoException();
                            reply.writeBoolean(_result16);
                            return true;
                        case 17:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result17 = isTestUsim(_arg012);
                            reply.writeNoException();
                            reply.writeBoolean(_result17);
                            return true;
                        case 18:
                            boolean _result18 = isDataSwitchAllowedForSubsidy();
                            reply.writeNoException();
                            reply.writeBoolean(_result18);
                            return true;
                        case 19:
                            int _arg013 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result19 = isDisableSimAllowedForSubsidy(_arg013);
                            reply.writeNoException();
                            reply.writeBoolean(_result19);
                            return true;
                        case 20:
                            String _arg014 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result20 = isDisableSimAllowedByIccId(_arg014);
                            reply.writeNoException();
                            reply.writeBoolean(_result20);
                            return true;
                        case 21:
                            int _arg015 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result21 = isOperatorCardForSubsidy(_arg015);
                            reply.writeNoException();
                            reply.writeBoolean(_result21);
                            return true;
                        case 22:
                            int _arg016 = data.readInt();
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setUserDataEnabledForSubsidy(_arg016, _arg1);
                            reply.writeNoException();
                            return true;
                        case 23:
                            int _arg017 = data.readInt();
                            data.enforceNoDataAvail();
                            popupDataEnabledForSubsidy(_arg017);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUniTelephony {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUniTelephony.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getPreferredPrimaryCard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public long getRestrictedNetworkTypeBitMask(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isNeedPopupPrimaryCardSettingPrompt() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isRestrictPreference(int carrierlistType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(carrierlistType);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getRestrictPreferencePhoneId(int carrierlistType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(carrierlistType);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getHomeExceptService(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getRomingExceptService(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isWhiteListCard(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isDefaultDataCardSwitchAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isSubscriptionPersoEnabled(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean restrictedNetworkTypeNeeded(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getRestrictedNetTypePhoneId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public int getOpPreferredPrimaryCard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isDataAllowedForSlot(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isSmsAllowedForSlot(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isCallAllowedForSlot(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isTestUsim(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isDataSwitchAllowedForSubsidy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isDisableSimAllowedForSubsidy(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isDisableSimAllowedByIccId(String iccId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeString(iccId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public boolean isOperatorCardForSubsidy(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public void setUserDataEnabledForSubsidy(int subId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IUniTelephony
            public void popupDataEnabledForSubsidy(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}