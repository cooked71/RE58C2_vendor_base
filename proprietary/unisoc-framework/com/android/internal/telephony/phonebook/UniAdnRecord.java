package com.android.internal.telephony.phonebook;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.UniGsmAlphabet;
import com.android.internal.telephony.UniIccUtils;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class UniAdnRecord implements Parcelable {
    static final int ADN_BCD_NUMBER_LENGTH = 0;
    static final int ADN_CAPABILITY_ID = 12;
    static final int ADN_DIALING_NUMBER_END = 11;
    static final int ADN_DIALING_NUMBER_START = 2;
    static final int ADN_EXTENSION_ID = 13;
    static final int ADN_REC_ID = 1;
    static final int ADN_SFI = 0;
    static final int ADN_TON_AND_NPI = 1;
    public static final String ANR_SPLIT_FLG = ";";
    public static final Parcelable.Creator<UniAdnRecord> CREATOR = new Parcelable.Creator<UniAdnRecord>() { // from class: com.android.internal.telephony.phonebook.UniAdnRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UniAdnRecord createFromParcel(Parcel source) {
            String[] emails;
            int efid = source.readInt();
            int recordNumber = source.readInt();
            String alphaTag = source.readString();
            String number = source.readString();
            int len = source.readInt();
            if (len > 0) {
                String[] emails2 = new String[len];
                source.readStringArray(emails2);
                emails = emails2;
            } else {
                emails = null;
            }
            String anr = source.readString();
            String aas = source.readString();
            String sne = source.readString();
            String grp = source.readString();
            String gas = source.readString();
            return new UniAdnRecord(efid, recordNumber, alphaTag, number, emails, anr, aas, sne, grp, gas);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UniAdnRecord[] newArray(int size) {
            return new UniAdnRecord[size];
        }
    };
    static final int EXT_RECORD_LENGTH_BYTES = 13;
    static final int EXT_RECORD_TYPE_ADDITIONAL_DATA = 2;
    static final int EXT_RECORD_TYPE_MASK = 3;
    static final int FOOTER_SIZE_BYTES = 14;
    static final int MAX_EXT_CALLED_PARTY_LENGTH = 10;
    public static final int MAX_LENTH_ADN = 20;
    public static final int MAX_LENTH_NUMBER = 40;
    static final int MAX_NUMBER_SIZE_BYTES = 11;
    private static final String TAG = "UniAdnRecord";
    static final int TYPE1_DATA_LENGTH = 15;
    public String mAas;
    public String mAlphaTag;
    public String mAnr;
    public int mEfid;
    public String[] mEmails;
    public int mExtRecord;
    public String mGas;
    public String mGrp;
    int mIndex;
    public int mIsSupport5c601Feature;
    public String mNumber;
    public int mRecordNumber;
    public String mSne;

    public UniAdnRecord(byte[] record) {
        this(0, 0, record);
    }

    public UniAdnRecord(int efid, int recordNumber, byte[] record) {
        this.mAlphaTag = null;
        this.mNumber = null;
        this.mExtRecord = 255;
        this.mAnr = null;
        this.mAas = null;
        this.mSne = null;
        this.mGrp = null;
        this.mGas = null;
        this.mIndex = -1;
        this.mIsSupport5c601Feature = SystemProperties.getInt("ro.vendor.feature.adn.5c601.support", 0);
        this.mEfid = efid;
        this.mRecordNumber = recordNumber;
        parseRecord(record);
    }

    public UniAdnRecord(String alphaTag, String number) {
        this(0, 0, alphaTag, number);
    }

    public UniAdnRecord(String alphaTag, String number, String[] emails) {
        this(0, 0, alphaTag, number, emails);
    }

    public UniAdnRecord(int efid, int recordNumber, String alphaTag, String number, String[] emails) {
        this.mAlphaTag = null;
        this.mNumber = null;
        this.mExtRecord = 255;
        this.mAnr = null;
        this.mAas = null;
        this.mSne = null;
        this.mGrp = null;
        this.mGas = null;
        this.mIndex = -1;
        this.mIsSupport5c601Feature = SystemProperties.getInt("ro.vendor.feature.adn.5c601.support", 0);
        this.mEfid = efid;
        this.mRecordNumber = recordNumber;
        this.mAlphaTag = alphaTag;
        this.mNumber = number;
        this.mEmails = emails;
    }

    public UniAdnRecord(int efid, int recordNumber, String alphaTag, String number) {
        this.mAlphaTag = null;
        this.mNumber = null;
        this.mExtRecord = 255;
        this.mAnr = null;
        this.mAas = null;
        this.mSne = null;
        this.mGrp = null;
        this.mGas = null;
        this.mIndex = -1;
        this.mIsSupport5c601Feature = SystemProperties.getInt("ro.vendor.feature.adn.5c601.support", 0);
        this.mEfid = efid;
        this.mRecordNumber = recordNumber;
        this.mAlphaTag = alphaTag;
        this.mNumber = number;
        this.mEmails = null;
    }

    public UniAdnRecord(String mAlphaTag, String mNumber, String[] mEmails, String anr, String aas, String sne, String grp, String gas) {
        this(0, 0, mAlphaTag, mNumber, mEmails, anr, aas, sne, grp, gas);
    }

    public UniAdnRecord(int efid, int recordNumber, String mAlphaTag, String mNumber, String[] mEmails, String anr, String aas, String sne, String grp, String gas) {
        this(efid, recordNumber, mAlphaTag, mNumber, mEmails);
        this.mAnr = anr;
        this.mAas = aas;
        this.mGrp = grp;
        this.mGas = gas;
        this.mSne = sne;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEfid);
        dest.writeInt(this.mRecordNumber);
        dest.writeString(this.mAlphaTag);
        dest.writeString(this.mNumber);
        dest.writeStringArray(this.mEmails);
        dest.writeString(this.mAnr);
        dest.writeString(this.mAas);
        dest.writeString(this.mSne);
        dest.writeString(this.mGrp);
        dest.writeString(this.mGas);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ADN Record '" + this.mAlphaTag + "' '" + this.mNumber + " " + this.mEmails + "'";
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.mAlphaTag) && TextUtils.isEmpty(this.mNumber) && this.mEmails == null && isEmptyAnr(this.mAnr);
    }

    public boolean hasExtendedRecord() {
        int i = this.mExtRecord;
        return (i == 0 || i == 255) ? false : true;
    }

    public boolean isEqual(UniAdnRecord adn) {
        return stringCompareNullEqualsEmpty(this.mAlphaTag, adn.mAlphaTag) && stringCompareNullEqualsEmpty(this.mNumber, adn.mNumber) && stringCompareEmails(this.mEmails, adn.mEmails) && stringCompareAnr(this.mAnr, adn.mAnr);
    }

    public boolean isEmptyAnr(String s) {
        if (TextUtils.isEmpty(s)) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ';') {
                return false;
            }
        }
        return true;
    }

    public String getAlphaTag() {
        return this.mAlphaTag;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    public String[] getEmails() {
        return this.mEmails;
    }

    public void setEmails(String[] emails) {
        this.mEmails = emails;
    }

    public String getAnr() {
        return this.mAnr;
    }

    public void setAnr(String anr) {
        this.mAnr = anr;
    }

    public String getAas() {
        return this.mAas;
    }

    public void setAas(String aas) {
        this.mAas = aas;
    }

    public String getSne() {
        return this.mSne;
    }

    public void setSne(String sne) {
        this.mSne = sne;
    }

    public String getGrp() {
        return this.mGrp;
    }

    public void setGrp(String grp) {
        this.mGrp = grp;
    }

    public String getGas() {
        return this.mGas;
    }

    public void setGas(String gas) {
        this.mGas = gas;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public void setRecordNumber(int sim_index) {
        this.mRecordNumber = sim_index;
    }

    public int getRecId() {
        return this.mRecordNumber;
    }

    public int getEfid() {
        return this.mEfid;
    }

    public int getExtRecord() {
        return this.mExtRecord;
    }

    public byte[] buildExtString() {
        String extNumber = "";
        if (!TextUtils.isEmpty(this.mNumber) && this.mNumber.length() > 20) {
            extNumber = this.mNumber.charAt(0) == '+' ? this.mNumber.substring(21) : this.mNumber.substring(20);
        }
        logd("extNumber = " + extNumber);
        byte[] extBcdNumber = PhoneNumberUtils.numberToCalledPartyBCD(extNumber, 1);
        byte[] extString = new byte[13];
        for (int i = 0; i < 13; i++) {
            extString[i] = -1;
        }
        if (!extNumber.isEmpty()) {
            extString[0] = 2;
            extBcdNumber[0] = (byte) (extBcdNumber.length - 1);
            if (extBcdNumber.length <= 11) {
                System.arraycopy(extBcdNumber, 0, extString, 1, extBcdNumber.length);
            }
            extString[12] = -1;
        }
        return extString;
    }

    public boolean extRecordIsNeeded() {
        String numberNoPlus = this.mNumber;
        if (!TextUtils.isEmpty(this.mNumber) && this.mNumber.charAt(0) == '+') {
            numberNoPlus = numberNoPlus.substring(1);
        }
        return !TextUtils.isEmpty(numberNoPlus) && numberNoPlus.length() <= 40 && numberNoPlus.length() > 20;
    }

    public boolean extRecord4DisplayIsNeeded() {
        String numberNoPlus = this.mNumber;
        if (!TextUtils.isEmpty(this.mNumber) && this.mNumber.charAt(0) == '+') {
            numberNoPlus = numberNoPlus.substring(1);
        }
        return !TextUtils.isEmpty(numberNoPlus) && numberNoPlus.length() == 20;
    }

    public void appendExtRecord(byte[] extRecord) {
        try {
            if (extRecord.length == 13 && (extRecord[0] & 3) == 2 && (extRecord[1] & 255) <= 10) {
                this.mNumber += PhoneNumberUtils.calledPartyBCDFragmentToString(extRecord, 2, extRecord[1] & 255, 1);
            }
        } catch (RuntimeException ex) {
            logw("Error parsing UniAdnRecord ext record:" + ex);
        }
    }

    public boolean stringCompareEmails(String[] e1, String[] e2) {
        if (e1 == null) {
            e1 = new String[]{""};
        }
        if (e2 == null) {
            e2 = new String[]{""};
        }
        return stringCompareNullEqualsEmpty(e1[0], e2[0]);
    }

    public boolean stringCompareAnr(String s1, String s2) {
        if (TextUtils.isEmpty(s1) || isEmptyAnr(s1)) {
            s1 = "";
        }
        if (TextUtils.isEmpty(s2) || isEmptyAnr(s2)) {
            s2 = "";
        }
        return stringCompareNullEqualsEmpty(s1, s2);
    }

    public byte[] buildAdnString(int recordSize) throws UnsupportedEncodingException {
        String adnNumber;
        int footerOffset = recordSize - 14;
        byte[] byteTag = null;
        byte[] adnString = new byte[recordSize];
        for (int i = 0; i < recordSize; i++) {
            adnString[i] = -1;
        }
        String numberNoPlus = this.mNumber;
        if (!TextUtils.isEmpty(this.mNumber) && this.mNumber.charAt(0) == '+') {
            numberNoPlus = numberNoPlus.substring(1);
        }
        logd("buildAdnString mNumber : " + this.mNumber + ", mAlphaTag : " + this.mAlphaTag + ", numberNoplus = " + numberNoPlus);
        if (TextUtils.isEmpty(this.mNumber) && TextUtils.isEmpty(this.mAlphaTag)) {
            String[] strArr = this.mEmails;
            if (strArr != null && strArr.length != 0) {
                throw new UniIccPBForOperationException(-1, "number and alphaTag is null but emails is not null ");
            }
            String str = this.mGrp;
            if (str != null && str.length() != 0) {
                throw new UniIccPBForOperationException(-1, "number and alphaTag is null but grp is not null ");
            }
            String str2 = this.mAnr;
            if (str2 != null && str2.length() != 0) {
                throw new UniIccPBForOperationException(-1, "number and alphaTag is null but anr is not null ");
            }
            String str3 = this.mSne;
            if (str3 != null && str3.length() != 0) {
                throw new UniIccPBForOperationException(-1, "number and alphaTag is null but sne is not null ");
            }
        }
        if (TextUtils.isEmpty(this.mNumber) && TextUtils.isEmpty(this.mAlphaTag)) {
            logw("buildAdnString Empty dialing mNumber");
            return adnString;
        }
        if (!TextUtils.isEmpty(numberNoPlus) && numberNoPlus.length() > 40) {
            logw("[buildAdnString] Max length of dialing mNumber is: 40");
            throw new UniIccPBForOperationException(-5, "Max length of dialing mNumber is 40");
        }
        String str4 = this.mAlphaTag;
        if (str4 != null && str4.length() > footerOffset) {
            logw("[buildAdnString] Max length of tag is " + footerOffset);
            throw new UniIccPBForOperationException(-4, "Max length of name is " + footerOffset);
        }
        if (!TextUtils.isEmpty(numberNoPlus) && numberNoPlus.length() <= 20) {
            logd("mNumber.length is: " + numberNoPlus.length());
            byte[] bcdNumber = PhoneNumberUtils.numberToCalledPartyBCD(this.mNumber, 1);
            System.arraycopy(bcdNumber, 0, adnString, footerOffset + 1, bcdNumber.length);
            adnString[footerOffset + 0] = (byte) bcdNumber.length;
            adnString[footerOffset + 12] = -1;
            adnString[footerOffset + 13] = -1;
        } else if (!TextUtils.isEmpty(numberNoPlus) && numberNoPlus.length() <= 40) {
            if (this.mNumber.charAt(0) == '+') {
                adnNumber = this.mNumber.substring(0, 21);
            } else {
                String adnNumber2 = this.mNumber;
                adnNumber = adnNumber2.substring(0, 20);
            }
            logd("adnNumber = " + adnNumber);
            if (!TextUtils.isEmpty(adnNumber)) {
                byte[] adnBcdNumber = PhoneNumberUtils.numberToCalledPartyBCD(adnNumber, 1);
                System.arraycopy(adnBcdNumber, 0, adnString, footerOffset + 1, adnBcdNumber.length);
                adnString[footerOffset + 0] = (byte) adnBcdNumber.length;
            }
            adnString[footerOffset + 12] = -1;
            adnString[footerOffset + 13] = (byte) this.mExtRecord;
            logd("mNumber.length >20 , mExtRecord = " + this.mExtRecord);
        }
        if (!TextUtils.isEmpty(this.mAlphaTag)) {
            try {
                byteTag = UniGsmAlphabet.stringToGsmAlphaSS(this.mAlphaTag);
                System.arraycopy(byteTag, 0, adnString, 0, byteTag.length);
            } catch (EncodeException e) {
                try {
                    byteTag = this.mAlphaTag.getBytes("utf-16be");
                    if (byteTag != null && byteTag.length >= footerOffset) {
                        logw("[buildAdnString] Max length of tag is:" + footerOffset);
                        throw new UniIccPBForOperationException(-4, "Max length of name is " + footerOffset);
                    }
                    if (byteTag != null && byteTag.length < adnString.length) {
                        System.arraycopy(byteTag, 0, adnString, 1, byteTag.length);
                    }
                    adnString[0] = -128;
                } catch (UnsupportedEncodingException e2) {
                    loge("[AdnRecord]mAlphaTag convert byte excepiton");
                }
            }
            logw("mAlphaTag length = " + (byteTag != null ? Integer.valueOf(byteTag.length) : "null,") + " footoffset = " + footerOffset);
            if (byteTag != null && byteTag.length > footerOffset) {
                logw("[buildAdnString] Max length of tag is " + footerOffset);
                throw new UniIccPBForOperationException(-4, "Max length of name is " + footerOffset);
            }
        }
        return adnString;
    }

    public byte[] buildIapString(int recordSize, int recNum) {
        byte[] iapString = new byte[recordSize];
        for (int i = 0; i < recordSize; i++) {
            iapString[i] = -1;
        }
        iapString[0] = (byte) recNum;
        return iapString;
    }

    public byte[] buildEmailString(int recordSize, int recordSeq, int efid, int adnNum) throws UnsupportedEncodingException {
        int footerOffset = recordSize - 2;
        byte[] emailString = new byte[recordSize];
        for (int i = 0; i < recordSize; i++) {
            emailString[i] = -1;
        }
        int i2 = footerOffset + 0;
        emailString[i2] = (byte) efid;
        emailString[footerOffset + 1] = (byte) adnNum;
        String[] strArr = this.mEmails;
        if (strArr == null || strArr[recordSeq] == null) {
            return emailString;
        }
        String emailRecord = strArr[recordSeq];
        if (!TextUtils.isEmpty(emailRecord)) {
            try {
                byte[] byteTag = UniGsmAlphabet.isAsciiStringToGsm8BitUnpackedField(emailRecord);
                if (byteTag.length > footerOffset) {
                    loge("emailRecord is overlength");
                    return null;
                }
                System.arraycopy(byteTag, 0, emailString, 0, byteTag.length);
            } catch (EncodeException e) {
                try {
                    byte[] byteTag2 = emailRecord.getBytes("utf-16be");
                    if (byteTag2.length > footerOffset - 1) {
                        loge("emailRecord is overlength");
                        return null;
                    }
                    System.arraycopy(byteTag2, 0, emailString, 1, byteTag2.length);
                    emailString[0] = -128;
                } catch (UnsupportedEncodingException e2) {
                    loge("[AdnRecord]emailRecord convert byte exception");
                }
            }
        }
        loge("emailRecord for adn[" + adnNum + "]==" + emailRecord);
        return emailString;
    }

    public byte[] buildAnrString(int recordSize, int anrCount, int efid, int adnNum, int aasIndex) {
        logd("buildAnrString");
        byte[] anrString = new byte[recordSize];
        for (int i = 0; i < recordSize; i++) {
            anrString[i] = -1;
        }
        if (TextUtils.isEmpty(this.mAnr) || this.mAnr.equals(ANR_SPLIT_FLG) || this.mAnr.equals(";;")) {
            loge("[buildAnrString] anr mNumber is empty. ");
            return anrString;
        }
        loge("anr = " + this.mAnr);
        String[] ret = null;
        if (!TextUtils.isEmpty(this.mAnr)) {
            ret = (this.mAnr + "1").split(ANR_SPLIT_FLG);
            ret[ret.length - 1] = ret[ret.length - 1].substring(0, ret[ret.length - 1].length() - 1);
        }
        if (anrCount >= ret.length) {
            return anrString;
        }
        String anrRecord = ret[anrCount];
        loge("anrRecord = " + anrRecord);
        if (TextUtils.isEmpty(anrRecord)) {
            loge("[buildAnrString] anrRecord is empty. ");
        } else {
            if (anrRecord.length() > 20) {
                loge("[buildAnrString] Max length of dailingmNumber is 20,throw exception");
                throw new UniIccPBForOperationException(-5, "Max length of dialing mNumber is 20");
            }
            if (isSupportOrange()) {
                anrString[0] = (byte) aasIndex;
            } else {
                anrString[0] = 1;
            }
            byte[] anrNumber = PhoneNumberUtils.numberToCalledPartyBCD(anrRecord, 1);
            if (anrNumber == null) {
                return anrString;
            }
            anrString[1] = (byte) anrNumber.length;
            System.arraycopy(anrNumber, 0, anrString, 2, anrNumber.length);
            if (recordSize > 15) {
                anrString[recordSize - 4] = -1;
                anrString[recordSize - 3] = -1;
                anrString[recordSize - 2] = (byte) efid;
                anrString[recordSize - 1] = (byte) adnNum;
            } else {
                anrString[recordSize - 2] = -1;
                anrString[recordSize - 1] = -1;
            }
        }
        return anrString;
    }

    public byte[] buildSneString(int recordSize, int recordSeq, int efid, int adnNum) throws UnsupportedEncodingException {
        byte[] sneString = new byte[recordSize];
        int footerOffset = recordSize - 2;
        for (int i = 0; i < recordSize; i++) {
            sneString[i] = -1;
        }
        int i2 = footerOffset + 0;
        sneString[i2] = (byte) efid;
        sneString[footerOffset + 1] = (byte) adnNum;
        if (this.mSne == null) {
            return sneString;
        }
        String sneRecord = this.mSne;
        if (!TextUtils.isEmpty(sneRecord)) {
            try {
                byte[] byteTag = UniGsmAlphabet.isAsciiStringToGsm8BitUnpackedField(sneRecord);
                if (byteTag.length > footerOffset) {
                    loge("sneRecord is overlength");
                    return null;
                }
                System.arraycopy(byteTag, 0, sneString, 0, byteTag.length);
            } catch (ArrayIndexOutOfBoundsException e) {
                loge("over the length of aas");
                return null;
            } catch (EncodeException e2) {
                try {
                    byte[] byteTag2 = sneRecord.getBytes("utf-16be");
                    if (byteTag2.length > footerOffset - 1) {
                        loge("sneRecord is overlength");
                        return null;
                    }
                    System.arraycopy(byteTag2, 0, sneString, 1, byteTag2.length);
                    sneString[0] = -128;
                } catch (UnsupportedEncodingException e3) {
                    loge("[AdnRecord]sneRecord convert byte exception");
                    return null;
                } catch (ArrayIndexOutOfBoundsException e4) {
                    loge("over the length of aas");
                    return null;
                }
            }
        }
        return sneString;
    }

    private static boolean stringCompareNullEqualsEmpty(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }
        return s1.trim().equals(s2.trim());
    }

    private void parseRecord(byte[] record) {
        try {
            if (this.mIsSupport5c601Feature == 1 && record.length - 14 >= 2) {
                this.mAlphaTag = UniIccUtils.adnStringFieldToString(record, 0, record.length - 14);
            } else {
                this.mAlphaTag = IccUtils.adnStringFieldToString(record, 0, record.length - 14);
            }
            int footerOffset = record.length - 14;
            int numberLength = record[footerOffset] & 255;
            if (numberLength > 11) {
                this.mNumber = "";
                return;
            }
            this.mNumber = PhoneNumberUtils.calledPartyBCDToString(record, footerOffset + 1, numberLength, 1);
            this.mExtRecord = record[record.length - 1] & 255;
            this.mEmails = null;
        } catch (RuntimeException ex) {
            logw("Error parsing UniAdnRecord:" + ex);
            this.mNumber = "";
            this.mAlphaTag = "";
            this.mEmails = null;
        }
    }

    private boolean isSupportOrange() throws Resources.NotFoundException {
        boolean isSupportOrange = Resources.getSystem().getBoolean(134414363);
        return isSupportOrange;
    }

    private void logd(String msg) {
        UniPhoneBookLog.d(TAG, msg);
    }

    private void logw(String msg) {
        UniPhoneBookLog.w(TAG, msg);
    }

    private void loge(String msg) {
        UniPhoneBookLog.e(TAG, msg);
    }
}