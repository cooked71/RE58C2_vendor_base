package com.android.internal.telephony.uicc;

/* loaded from: classes.dex */
public class UniOplNrRecord extends UniOplRecord {
    public int PNNrecordnum;
    public int[] mOplNrplmn;
    public int mOplNrtac1;
    public int mOplNrtac2;

    public UniOplNrRecord(byte[] record) {
        super(record);
        this.mOplNrplmn = new int[]{0, 0, 0, 0, 0, 0};
        OplNrplmn(record);
        this.mOplNrtac1 = ((record[3] & 255) << 16) | ((record[4] & 255) << 8) | (record[5] & 255);
        this.mOplNrtac2 = ((record[6] & 255) << 16) | ((record[7] & 255) << 8) | (record[8] & 255);
        this.PNNrecordnum = (short) (record[9] & 255);
    }

    public void OplNrplmn(byte[] record) {
        int[] iArr = this.mOplNrplmn;
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

    @Override // com.android.internal.telephony.uicc.UniOplRecord
    public int getPnnRecordNum() {
        return this.PNNrecordnum;
    }

    @Override // com.android.internal.telephony.uicc.UniOplRecord
    public String toString() {
        return "OPL Record mOplNrplmn = " + Integer.toHexString(this.mOplNrplmn[0]) + Integer.toHexString(this.mOplNrplmn[1]) + Integer.toHexString(this.mOplNrplmn[2]) + Integer.toHexString(this.mOplNrplmn[3]) + Integer.toHexString(this.mOplNrplmn[4]) + Integer.toHexString(this.mOplNrplmn[5]) + ", mOplNrtac1 =" + this.mOplNrtac1 + ", mOplNrtac2 =" + this.mOplNrtac2 + " ,PNNrecordnum = " + this.PNNrecordnum;
    }
}