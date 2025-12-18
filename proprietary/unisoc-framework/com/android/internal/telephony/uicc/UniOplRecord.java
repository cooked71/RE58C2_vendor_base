package com.android.internal.telephony.uicc;

/* loaded from: classes.dex */
public class UniOplRecord {
    public int PNNrecordnum;
    public int mOpllac1;
    public int mOpllac2;
    public int[] mOplplmn = {0, 0, 0, 0, 0, 0};

    public UniOplRecord(byte[] record) {
        Oplplmn(record);
        this.mOpllac1 = ((record[3] & 255) << 8) | (record[4] & 255);
        this.mOpllac2 = (record[6] & 255) | ((record[5] & 255) << 8);
        this.PNNrecordnum = (short) (record[7] & 255);
    }

    public void Oplplmn(byte[] record) {
        int[] iArr = this.mOplplmn;
        iArr[0] = record[0] & 15;
        iArr[1] = (record[0] >> 4) & 15;
        iArr[2] = record[1] & 15;
        iArr[3] = record[2] & 15;
        iArr[4] = (record[2] >> 4) & 15;
        int i = (record[1] >> 4) & 15;
        iArr[5] = i;
        if (15 == i) {
            iArr[5] = 0;
        }
    }

    public int getPnnRecordNum() {
        return this.PNNrecordnum;
    }

    public String toString() {
        return "OPL Record mOplplmn = " + Integer.toHexString(this.mOplplmn[0]) + Integer.toHexString(this.mOplplmn[1]) + Integer.toHexString(this.mOplplmn[2]) + Integer.toHexString(this.mOplplmn[3]) + Integer.toHexString(this.mOplplmn[4]) + Integer.toHexString(this.mOplplmn[5]) + ", mOpllac1 =" + this.mOpllac1 + ", mOpllac2 =" + this.mOpllac2 + " ,PNNrecordnum = " + this.PNNrecordnum;
    }
}