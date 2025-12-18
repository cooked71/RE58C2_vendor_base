package com.android.internal.telephony.uicc;

import android.util.Log;

/* loaded from: classes.dex */
public final class UniIccServiceTable implements UniIccConstants {
    static final String LOG_TAG = "IccServiceTable";
    private int mAppType;
    private final byte[] mIccServiceTable;

    public UniIccServiceTable(byte[] table, int appType) {
        this.mIccServiceTable = table;
        this.mAppType = appType;
    }

    private boolean isAvailable(int service) {
        Log.d(LOG_TAG, "isAvailable for servic" + service + " for type " + this.mAppType);
        int offset = this.mAppType;
        if (offset == 2) {
            int offset2 = service / 8;
            byte[] bArr = this.mIccServiceTable;
            if (offset2 >= bArr.length) {
                Log.e(LOG_TAG, "isAvailable for service " + (service + 1) + " fails, max service is " + (this.mIccServiceTable.length * 8));
                return false;
            }
            int bit = service % 8;
            return (bArr[offset2] & (1 << bit)) != 0;
        }
        if (offset != 1) {
            return false;
        }
        int offset3 = service / 4;
        byte[] bArr2 = this.mIccServiceTable;
        if (offset3 >= bArr2.length) {
            Log.e(LOG_TAG, "isAvailable for service " + (service + 1) + " fails, max service is " + (this.mIccServiceTable.length * 4));
            return false;
        }
        int bit2 = service % 4;
        return (bArr2[offset3] & (3 << bit2)) != 0;
    }

    boolean pnnAvaliable() {
        boolean available = false;
        int i = this.mAppType;
        if (i == 2) {
            available = isAvailable(44);
        } else if (i == 1) {
            available = isAvailable(51);
        }
        Log.d(LOG_TAG, "pnnAvaliable return " + available);
        return available;
    }

    boolean oplAvaliable() {
        boolean available = false;
        int i = this.mAppType;
        if (i == 2) {
            available = isAvailable(45);
        } else if (i == 1) {
            available = isAvailable(52);
        }
        Log.d(LOG_TAG, "oplAvaliable return " + available);
        return available;
    }

    boolean oplNrAvaliable() {
        boolean available = false;
        if (this.mAppType == 2) {
            available = isAvailable(128);
        }
        Log.d(LOG_TAG, "oplAvaliable return " + available);
        return available;
    }

    boolean psDataOffAvaliable() {
        if (this.mAppType == 2) {
            return isAvailable(UniIccConstants.UST_PS_DATA_OFF);
        }
        return false;
    }

    boolean psDataOffInfoAvaliable() {
        if (this.mAppType == 2) {
            return isAvailable(UniIccConstants.UST_PS_DATA_OFF_INFO);
        }
        return false;
    }
}