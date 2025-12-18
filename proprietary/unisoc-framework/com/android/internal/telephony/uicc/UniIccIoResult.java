package com.android.internal.telephony.uicc;

import com.android.internal.telephony.MobileNetworkUtils;
import com.android.internal.telephony.util.TelephonyUtils;

/* loaded from: classes.dex */
public class UniIccIoResult {
    private static final String UNKNOWN_ERROR = "unknown";
    public byte[] payload;
    public int sw1;
    public int sw2;

    private String getErrorString() {
        switch (this.sw1) {
            case 97:
                break;
            case 98:
                switch (this.sw2) {
                    case 0:
                        break;
                    case 129:
                        break;
                    case 130:
                        break;
                    case UniIccConstants.UST_PS_DATA_OFF_INFO /* 131 */:
                        break;
                    case 132:
                        break;
                    case 241:
                        break;
                    case 242:
                        break;
                    case 243:
                        break;
                }
            case 99:
                int retries = this.sw2;
                if ((retries >> 4) == 12) {
                    int retries2 = retries & 15;
                    break;
                } else {
                    switch (retries) {
                    }
                }
            case 100:
                switch (this.sw2) {
                }
            case 101:
                switch (this.sw2) {
                    case 0:
                        break;
                    case 129:
                        break;
                }
            case 103:
                switch (this.sw2) {
                }
            case 104:
                switch (this.sw2) {
                    case 0:
                        break;
                    case 129:
                        break;
                    case 130:
                        break;
                }
            case 105:
                switch (this.sw2) {
                    case 0:
                        break;
                    case 129:
                        break;
                    case 130:
                        break;
                    case UniIccConstants.UST_PS_DATA_OFF_INFO /* 131 */:
                        break;
                    case 132:
                        break;
                    case 133:
                        break;
                    case 134:
                        break;
                    case 137:
                        break;
                }
            case 106:
                switch (this.sw2) {
                }
            case 107:
                break;
            case 108:
                break;
            case 109:
                break;
            case 110:
                break;
            case 111:
                switch (this.sw2) {
                }
            case 144:
                break;
            case 145:
                break;
            case 146:
                int i = this.sw2;
                if ((i >> 4) != 0) {
                    switch (i) {
                    }
                }
                break;
            case 147:
                switch (this.sw2) {
                }
            case 148:
                switch (this.sw2) {
                    case 0:
                        break;
                    case 2:
                        break;
                    case 4:
                        break;
                    case 8:
                        break;
                }
            case 152:
                switch (this.sw2) {
                    case 2:
                        break;
                    case 4:
                        break;
                    case 8:
                        break;
                    case 16:
                        break;
                    case MobileNetworkUtils.RadioAccessFamily.RAF_1xRTT /* 64 */:
                        break;
                    case 80:
                        break;
                    case 98:
                        break;
                    case 100:
                        break;
                    case 101:
                        break;
                    case 102:
                        break;
                    case 103:
                        break;
                }
            case 158:
                break;
            case 159:
                break;
        }
        return null;
    }

    public UniIccIoResult(int sw1, int sw2, byte[] payload) {
        this.sw1 = sw1;
        this.sw2 = sw2;
        this.payload = payload;
    }

    public UniIccIoResult(int sw1, int sw2, String hexString) {
        this(sw1, sw2, IccUtils.hexStringToBytes(hexString));
    }

    public String toString() {
        return "IccIoResult sw1:0x" + Integer.toHexString(this.sw1) + " sw2:0x" + Integer.toHexString(this.sw2) + " Payload: " + (TelephonyUtils.IS_DEBUGGABLE ? IccUtils.bytesToHexString(this.payload) : "*******") + (!success() ? " Error: " + getErrorString() : "");
    }

    public boolean success() {
        int i = this.sw1;
        return i == 144 || i == 145 || i == 158 || i == 159;
    }

    public UniIccException getException() {
        if (success()) {
            return null;
        }
        switch (this.sw1) {
            case 148:
                if (this.sw2 == 8) {
                    return new UniIccFileTypeMismatch();
                }
                return new UniIccFileNotFound();
            default:
                return new UniIccException("sw1:" + this.sw1 + " sw2:" + this.sw2);
        }
    }
}