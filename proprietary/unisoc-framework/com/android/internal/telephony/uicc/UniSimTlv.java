package com.android.internal.telephony.uicc;

/* loaded from: classes.dex */
public class UniSimTlv {
    int mCurDataLength;
    int mCurDataOffset;
    int mCurOffset;
    boolean mHasValidTlvObject = parseCurrentTlvObject();
    byte[] mRecord;
    int mTlvLength;
    int mTlvOffset;

    public UniSimTlv(byte[] record, int offset, int length) {
        this.mRecord = record;
        this.mTlvOffset = offset;
        this.mTlvLength = length;
        this.mCurOffset = offset;
    }

    public boolean nextObject() {
        if (!this.mHasValidTlvObject) {
            return false;
        }
        this.mCurOffset = this.mCurDataOffset + this.mCurDataLength;
        boolean currentTlvObject = parseCurrentTlvObject();
        this.mHasValidTlvObject = currentTlvObject;
        return currentTlvObject;
    }

    public boolean isValidObject() {
        return this.mHasValidTlvObject;
    }

    public int getTag() {
        if (this.mHasValidTlvObject) {
            return this.mRecord[this.mCurOffset] & 255;
        }
        return 0;
    }

    public byte[] getData() {
        if (!this.mHasValidTlvObject) {
            return null;
        }
        int i = this.mCurDataLength;
        byte[] ret = new byte[i];
        System.arraycopy(this.mRecord, this.mCurDataOffset, ret, 0, i);
        return ret;
    }

    private boolean parseCurrentTlvObject() {
        try {
            byte[] bArr = this.mRecord;
            int i = this.mCurOffset;
            byte b = bArr[i];
            if (b != 0 && (b & 255) != 255) {
                if ((bArr[i + 1] & 255) < 128) {
                    this.mCurDataLength = bArr[i + 1] & 255;
                    this.mCurDataOffset = i + 2;
                } else {
                    if ((bArr[i + 1] & 255) != 129) {
                        return false;
                    }
                    this.mCurDataLength = bArr[i + 2] & 255;
                    this.mCurDataOffset = i + 3;
                }
                if (this.mCurDataLength + this.mCurDataOffset > this.mTlvOffset + this.mTlvLength) {
                    return false;
                }
                return true;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}