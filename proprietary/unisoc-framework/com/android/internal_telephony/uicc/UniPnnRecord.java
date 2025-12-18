package com.android.internal.telephony.uicc;

/* loaded from: classes.dex */
public class UniPnnRecord {
    static final int TAG_FULL_NETWORK_NAME = 67;
    static final int TAG_SHORT_NETWORK_NAME = 69;
    private String mPnnLongName;
    private String mPnnShortName;

    public UniPnnRecord(byte[] data) {
        UniSimTlv tlv = new UniSimTlv(data, 0, data.length);
        while (tlv.isValidObject()) {
            if (tlv.getTag() == TAG_FULL_NETWORK_NAME) {
                this.mPnnLongName = IccUtils.networkNameToString(tlv.getData(), 0, tlv.getData().length);
            }
            if (tlv.getTag() != TAG_SHORT_NETWORK_NAME) {
                tlv.nextObject();
            } else {
                this.mPnnShortName = IccUtils.networkNameToString(tlv.getData(), 0, tlv.getData().length);
                return;
            }
        }
    }

    public String getLongName() {
        return this.mPnnLongName;
    }

    public String getShortName() {
        return this.mPnnShortName;
    }

    public String toString() {
        return "PnnLongName = " + this.mPnnLongName + ", PnnShortName = " + this.mPnnShortName;
    }
}