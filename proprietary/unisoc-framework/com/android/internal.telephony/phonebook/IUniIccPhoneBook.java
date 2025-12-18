package com.android.internal.telephony.phonebook;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IUniIccPhoneBook extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.phonebook.IUniIccPhoneBook";

    List<String> getAasInEfForSubscriber(int i) throws RemoteException;

    List<UniAdnRecord> getAdnRecordsInEfForSubscriber(int i, int i2) throws RemoteException;

    int[] getAdnRecordsSizeForSubscriber(int i, int i2) throws RemoteException;

    int getAnrNum(int i) throws RemoteException;

    int[] getAnrRecordsSize(int i) throws RemoteException;

    int[] getAvalibleAnrCount(String str, String str2, String[] strArr, String str3, int[] iArr, int i) throws RemoteException;

    int[] getAvalibleEmailCount(String str, String str2, String[] strArr, String str3, int[] iArr, int i) throws RemoteException;

    int getEmailMaxLen(int i) throws RemoteException;

    int getEmailNum(int i) throws RemoteException;

    int[] getEmailRecordsSize(int i) throws RemoteException;

    List<String> getGasInEfForSubscriber(int i) throws RemoteException;

    int getGroupNum(int i) throws RemoteException;

    int getInsertIndex(int i) throws RemoteException;

    int getPhoneNumMaxLen(int i) throws RemoteException;

    int[] getSneLength(int i) throws RemoteException;

    int getSneSize(int i) throws RemoteException;

    int getUsimGroupNameMaxLen(int i) throws RemoteException;

    int[] getUsimGroupSize(int i) throws RemoteException;

    boolean isApplicationOnIcc(int i, int i2) throws RemoteException;

    int updateAdnRecordsInEfByIndexForSubscriber(int i, int i2, String str, String str2, String[] strArr, String str3, String str4, String str5, String str6, String str7, int i3, String str8) throws RemoteException;

    boolean updateAdnRecordsInEfBySearchForSubscriber(int i, int i2, String str, String str2, String str3, String str4, String str5) throws RemoteException;

    int updateAdnRecordsInEfBySearchForSubscriberEx(int i, int i2, String str, String str2, String[] strArr, String str3, String str4, String str5, String str6, String str7, String[] strArr2, String str8, String str9, String str10, String str11, String str12, String str13) throws RemoteException;

    int updateUsimAasByIndexForSubscriber(String str, int i, int i2) throws RemoteException;

    int updateUsimAasBySearchForSubscriber(String str, String str2, int i) throws RemoteException;

    int updateUsimGroupByIndexForSubscriber(int i, String str, int i2) throws RemoteException;

    int updateUsimGroupBySearchForSubscriber(int i, String str, String str2) throws RemoteException;

    public static class Default implements IUniIccPhoneBook {
        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public List<UniAdnRecord> getAdnRecordsInEfForSubscriber(int subId, int efid) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getAdnRecordsSizeForSubscriber(int subId, int efid) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public boolean updateAdnRecordsInEfBySearchForSubscriber(int subId, int efid, String oldTag, String oldPhoneNumber, String newTag, String newPhoneNumber, String pin2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateAdnRecordsInEfBySearchForSubscriberEx(int subId, int efid, String oldTag, String oldPhoneNumber, String[] oldEmailList, String oldAnr, String oldSne, String oldGrp, String newTag, String newPhoneNumber, String[] newEmailList, String newAnr, String newAas, String newSne, String newGrp, String newGas, String pin2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateAdnRecordsInEfByIndexForSubscriber(int subId, int efid, String newTag, String newPhoneNumber, String[] newEmailList, String newAnr, String newAas, String newSne, String newGrp, String newGas, int index, String pin2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateUsimGroupBySearchForSubscriber(int subId, String oldName, String newName) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateUsimGroupByIndexForSubscriber(int subId, String newName, int groupId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public List<String> getGasInEfForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public boolean isApplicationOnIcc(int appType, int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getEmailRecordsSize(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getAnrRecordsSize(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getAnrNum(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getEmailNum(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getGroupNum(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getInsertIndex(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getAvalibleEmailCount(String name, String number, String[] emails, String anr, int[] emailNums, int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getAvalibleAnrCount(String name, String number, String[] emails, String anr, int[] anrNums, int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getEmailMaxLen(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getPhoneNumMaxLen(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getUsimGroupNameMaxLen(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getUsimGroupSize(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public List<String> getAasInEfForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateUsimAasBySearchForSubscriber(String oldName, String newName, int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int updateUsimAasByIndexForSubscriber(String newName, int aasIndex, int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int getSneSize(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
        public int[] getSneLength(int subId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUniIccPhoneBook {
        static final int TRANSACTION_getAasInEfForSubscriber = 22;
        static final int TRANSACTION_getAdnRecordsInEfForSubscriber = 1;
        static final int TRANSACTION_getAdnRecordsSizeForSubscriber = 2;
        static final int TRANSACTION_getAnrNum = 12;
        static final int TRANSACTION_getAnrRecordsSize = 11;
        static final int TRANSACTION_getAvalibleAnrCount = 17;
        static final int TRANSACTION_getAvalibleEmailCount = 16;
        static final int TRANSACTION_getEmailMaxLen = 18;
        static final int TRANSACTION_getEmailNum = 13;
        static final int TRANSACTION_getEmailRecordsSize = 10;
        static final int TRANSACTION_getGasInEfForSubscriber = 8;
        static final int TRANSACTION_getGroupNum = 14;
        static final int TRANSACTION_getInsertIndex = 15;
        static final int TRANSACTION_getPhoneNumMaxLen = 19;
        static final int TRANSACTION_getSneLength = 26;
        static final int TRANSACTION_getSneSize = 25;
        static final int TRANSACTION_getUsimGroupNameMaxLen = 20;
        static final int TRANSACTION_getUsimGroupSize = 21;
        static final int TRANSACTION_isApplicationOnIcc = 9;
        static final int TRANSACTION_updateAdnRecordsInEfByIndexForSubscriber = 5;
        static final int TRANSACTION_updateAdnRecordsInEfBySearchForSubscriber = 3;
        static final int TRANSACTION_updateAdnRecordsInEfBySearchForSubscriberEx = 4;
        static final int TRANSACTION_updateUsimAasByIndexForSubscriber = 24;
        static final int TRANSACTION_updateUsimAasBySearchForSubscriber = 23;
        static final int TRANSACTION_updateUsimGroupByIndexForSubscriber = 7;
        static final int TRANSACTION_updateUsimGroupBySearchForSubscriber = 6;

        public Stub() {
            attachInterface(this, IUniIccPhoneBook.DESCRIPTOR);
        }

        public static IUniIccPhoneBook asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUniIccPhoneBook.DESCRIPTOR);
            if (iin != null && (iin instanceof IUniIccPhoneBook)) {
                return (IUniIccPhoneBook) iin;
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
                data.enforceInterface(IUniIccPhoneBook.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(IUniIccPhoneBook.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            List<UniAdnRecord> _result = getAdnRecordsInEfForSubscriber(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeTypedList(_result);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result2 = getAdnRecordsSizeForSubscriber(_arg02, _arg12);
                            reply.writeNoException();
                            reply.writeIntArray(_result2);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg13 = data.readInt();
                            String _arg2 = data.readString();
                            String _arg3 = data.readString();
                            String _arg4 = data.readString();
                            String _arg5 = data.readString();
                            String _arg6 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result3 = updateAdnRecordsInEfBySearchForSubscriber(_arg03, _arg13, _arg2, _arg3, _arg4, _arg5, _arg6);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            int _arg14 = data.readInt();
                            String _arg22 = data.readString();
                            String _arg32 = data.readString();
                            String[] _arg42 = data.createStringArray();
                            String _arg52 = data.readString();
                            String _arg62 = data.readString();
                            String _arg7 = data.readString();
                            String _arg8 = data.readString();
                            String _arg9 = data.readString();
                            String[] _arg10 = data.createStringArray();
                            String _arg11 = data.readString();
                            String _arg122 = data.readString();
                            String _arg132 = data.readString();
                            String _arg142 = data.readString();
                            String _arg15 = data.readString();
                            String _arg16 = data.readString();
                            data.enforceNoDataAvail();
                            int _result4 = updateAdnRecordsInEfBySearchForSubscriberEx(_arg04, _arg14, _arg22, _arg32, _arg42, _arg52, _arg62, _arg7, _arg8, _arg9, _arg10, _arg11, _arg122, _arg132, _arg142, _arg15, _arg16);
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            int _arg17 = data.readInt();
                            String _arg23 = data.readString();
                            String _arg33 = data.readString();
                            String[] _arg43 = data.createStringArray();
                            String _arg53 = data.readString();
                            String _arg63 = data.readString();
                            String _arg72 = data.readString();
                            String _arg82 = data.readString();
                            String _arg92 = data.readString();
                            int _arg102 = data.readInt();
                            String _arg112 = data.readString();
                            data.enforceNoDataAvail();
                            int _result5 = updateAdnRecordsInEfByIndexForSubscriber(_arg05, _arg17, _arg23, _arg33, _arg43, _arg53, _arg63, _arg72, _arg82, _arg92, _arg102, _arg112);
                            reply.writeNoException();
                            reply.writeInt(_result5);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            String _arg18 = data.readString();
                            String _arg24 = data.readString();
                            data.enforceNoDataAvail();
                            int _result6 = updateUsimGroupBySearchForSubscriber(_arg06, _arg18, _arg24);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 7:
                            int _arg07 = data.readInt();
                            String _arg19 = data.readString();
                            int _arg25 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result7 = updateUsimGroupByIndexForSubscriber(_arg07, _arg19, _arg25);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            List<String> _result8 = getGasInEfForSubscriber(_arg08);
                            reply.writeNoException();
                            reply.writeStringList(_result8);
                            return true;
                        case 9:
                            int _arg09 = data.readInt();
                            int _arg110 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result9 = isApplicationOnIcc(_arg09, _arg110);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 10:
                            int _arg010 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result10 = getEmailRecordsSize(_arg010);
                            reply.writeNoException();
                            reply.writeIntArray(_result10);
                            return true;
                        case 11:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result11 = getAnrRecordsSize(_arg011);
                            reply.writeNoException();
                            reply.writeIntArray(_result11);
                            return true;
                        case 12:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result12 = getAnrNum(_arg012);
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            return true;
                        case 13:
                            int _arg013 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result13 = getEmailNum(_arg013);
                            reply.writeNoException();
                            reply.writeInt(_result13);
                            return true;
                        case 14:
                            int _arg014 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result14 = getGroupNum(_arg014);
                            reply.writeNoException();
                            reply.writeInt(_result14);
                            return true;
                        case 15:
                            int _arg015 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result15 = getInsertIndex(_arg015);
                            reply.writeNoException();
                            reply.writeInt(_result15);
                            return true;
                        case 16:
                            String _arg016 = data.readString();
                            String _arg111 = data.readString();
                            String[] _arg26 = data.createStringArray();
                            String _arg34 = data.readString();
                            int[] _arg44 = data.createIntArray();
                            int _arg54 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result16 = getAvalibleEmailCount(_arg016, _arg111, _arg26, _arg34, _arg44, _arg54);
                            reply.writeNoException();
                            reply.writeIntArray(_result16);
                            return true;
                        case 17:
                            String _arg017 = data.readString();
                            String _arg113 = data.readString();
                            String[] _arg27 = data.createStringArray();
                            String _arg35 = data.readString();
                            int[] _arg45 = data.createIntArray();
                            int _arg55 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result17 = getAvalibleAnrCount(_arg017, _arg113, _arg27, _arg35, _arg45, _arg55);
                            reply.writeNoException();
                            reply.writeIntArray(_result17);
                            return true;
                        case 18:
                            int _arg018 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result18 = getEmailMaxLen(_arg018);
                            reply.writeNoException();
                            reply.writeInt(_result18);
                            return true;
                        case 19:
                            int _arg019 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result19 = getPhoneNumMaxLen(_arg019);
                            reply.writeNoException();
                            reply.writeInt(_result19);
                            return true;
                        case 20:
                            int _arg020 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result20 = getUsimGroupNameMaxLen(_arg020);
                            reply.writeNoException();
                            reply.writeInt(_result20);
                            return true;
                        case 21:
                            int _arg021 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result21 = getUsimGroupSize(_arg021);
                            reply.writeNoException();
                            reply.writeIntArray(_result21);
                            return true;
                        case 22:
                            int _arg022 = data.readInt();
                            data.enforceNoDataAvail();
                            List<String> _result22 = getAasInEfForSubscriber(_arg022);
                            reply.writeNoException();
                            reply.writeStringList(_result22);
                            return true;
                        case 23:
                            String _arg023 = data.readString();
                            String _arg114 = data.readString();
                            int _arg28 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result23 = updateUsimAasBySearchForSubscriber(_arg023, _arg114, _arg28);
                            reply.writeNoException();
                            reply.writeInt(_result23);
                            return true;
                        case 24:
                            String _arg024 = data.readString();
                            int _arg115 = data.readInt();
                            int _arg29 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result24 = updateUsimAasByIndexForSubscriber(_arg024, _arg115, _arg29);
                            reply.writeNoException();
                            reply.writeInt(_result24);
                            return true;
                        case 25:
                            int _arg025 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result25 = getSneSize(_arg025);
                            reply.writeNoException();
                            reply.writeInt(_result25);
                            return true;
                        case 26:
                            int _arg026 = data.readInt();
                            data.enforceNoDataAvail();
                            int[] _result26 = getSneLength(_arg026);
                            reply.writeNoException();
                            reply.writeIntArray(_result26);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUniIccPhoneBook {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUniIccPhoneBook.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public List<UniAdnRecord> getAdnRecordsInEfForSubscriber(int subId, int efid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(efid);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<UniAdnRecord> _result = _reply.createTypedArrayList(UniAdnRecord.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getAdnRecordsSizeForSubscriber(int subId, int efid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(efid);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public boolean updateAdnRecordsInEfBySearchForSubscriber(int subId, int efid, String oldTag, String oldPhoneNumber, String newTag, String newPhoneNumber, String pin2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(efid);
                    _data.writeString(oldTag);
                    _data.writeString(oldPhoneNumber);
                    _data.writeString(newTag);
                    _data.writeString(newPhoneNumber);
                    _data.writeString(pin2);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateAdnRecordsInEfBySearchForSubscriberEx(int subId, int efid, String oldTag, String oldPhoneNumber, String[] oldEmailList, String oldAnr, String oldSne, String oldGrp, String newTag, String newPhoneNumber, String[] newEmailList, String newAnr, String newAas, String newSne, String newGrp, String newGas, String pin2) throws Throwable {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(efid);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(oldTag);
                    try {
                        _data.writeString(oldPhoneNumber);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(oldEmailList);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(oldAnr);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(oldSne);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(oldGrp);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newTag);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newPhoneNumber);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStringArray(newEmailList);
                    try {
                        _data.writeString(newAnr);
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newAas);
                        _data.writeString(newSne);
                        _data.writeString(newGrp);
                        _data.writeString(newGas);
                        _data.writeString(pin2);
                        this.mRemote.transact(4, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateAdnRecordsInEfByIndexForSubscriber(int subId, int efid, String newTag, String newPhoneNumber, String[] newEmailList, String newAnr, String newAas, String newSne, String newGrp, String newGas, int index, String pin2) throws Throwable {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(efid);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(newTag);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(newPhoneNumber);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStringArray(newEmailList);
                    try {
                        _data.writeString(newAnr);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newAas);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newSne);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(newGrp);
                    try {
                        _data.writeString(newGas);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(index);
                        try {
                            _data.writeString(pin2);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(5, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateUsimGroupBySearchForSubscriber(int subId, String oldName, String newName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(oldName);
                    _data.writeString(newName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateUsimGroupByIndexForSubscriber(int subId, String newName, int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(newName);
                    _data.writeInt(groupId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public List<String> getGasInEfForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public boolean isApplicationOnIcc(int appType, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(appType);
                    _data.writeInt(subId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getEmailRecordsSize(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getAnrRecordsSize(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getAnrNum(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getEmailNum(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getGroupNum(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getInsertIndex(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getAvalibleEmailCount(String name, String number, String[] emails, String anr, int[] emailNums, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(number);
                    _data.writeStringArray(emails);
                    _data.writeString(anr);
                    _data.writeIntArray(emailNums);
                    _data.writeInt(subId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getAvalibleAnrCount(String name, String number, String[] emails, String anr, int[] anrNums, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(number);
                    _data.writeStringArray(emails);
                    _data.writeString(anr);
                    _data.writeIntArray(anrNums);
                    _data.writeInt(subId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getEmailMaxLen(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getPhoneNumMaxLen(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getUsimGroupNameMaxLen(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getUsimGroupSize(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public List<String> getAasInEfForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateUsimAasBySearchForSubscriber(String oldName, String newName, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeString(oldName);
                    _data.writeString(newName);
                    _data.writeInt(subId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int updateUsimAasByIndexForSubscriber(String newName, int aasIndex, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeString(newName);
                    _data.writeInt(aasIndex);
                    _data.writeInt(subId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int getSneSize(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.phonebook.IUniIccPhoneBook
            public int[] getSneLength(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUniIccPhoneBook.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}