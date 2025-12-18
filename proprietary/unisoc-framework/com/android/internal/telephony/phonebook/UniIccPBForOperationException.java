package com.android.internal.telephony.phonebook;

/* loaded from: classes.dex */
public class UniIccPBForOperationException extends RuntimeException {
    public static final int AAS_CAPACITY_FULL = -12;
    public static final int ADN_CAPACITY_FULL = -3;
    public static final int ANR_CAPACITY_FULL = -9;
    public static final int EMAIL_CAPACITY_FULL = -2;
    public static final int GROUP_CAPACITY_FULL = -8;
    public static final int GRP_RECORD_MAX_LENGTH = -10;
    public static final int LOAD_ADN_FAIL = -6;
    public static final int OVER_AAS_MAX_LENGTH = -11;
    public static final int OVER_ANR_MAX_LENGTH = -13;
    public static final int OVER_GROUP_NAME_MAX_LENGTH = -7;
    public static final int OVER_NAME_MAX_LENGTH = -4;
    public static final int OVER_NUMBER_MAX_LENGTH = -5;
    public static final int WRITE_OPREATION_FAILED = -1;
    public int mErrorCode;

    public UniIccPBForOperationException() {
        this.mErrorCode = -1;
    }

    public UniIccPBForOperationException(String detailMessage) {
        super(detailMessage);
        this.mErrorCode = -1;
    }

    public UniIccPBForOperationException(Throwable throwable) {
        super(throwable);
        this.mErrorCode = -1;
    }

    public UniIccPBForOperationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.mErrorCode = -1;
    }

    public UniIccPBForOperationException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.mErrorCode = -1;
        this.mErrorCode = errorCode;
    }

    public UniIccPBForOperationException(int errorCode, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.mErrorCode = -1;
        this.mErrorCode = errorCode;
    }
}