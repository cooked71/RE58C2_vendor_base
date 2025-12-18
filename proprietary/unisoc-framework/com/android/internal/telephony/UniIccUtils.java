package com.android.internal.telephony;

import android.R;
import android.content.res.Resources;
import android.os.SystemProperties;
import com.android.telephony.Rlog;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class UniIccUtils {
    private static final String LOG_TAG = "UniIccUtils";
    private static int KSC_BEGAINSECOND = 161;
    private static int KSC_ENDSECOND = 254;
    private static int KSC_BEGAINFIRST = 192;
    private static int KSC_ENDFIRST = 200;

    public static String adnStringFieldToString(byte[] data, int offset, int length) throws Resources.NotFoundException {
        if (length == 0 || data == null) {
            return "";
        }
        if (length >= 1 && data[offset] == -128) {
            int ucslen = (length - 1) / 2;
            String ret = null;
            try {
                ret = new String(data, offset + 1, ucslen * 2, "utf-16be");
            } catch (UnsupportedEncodingException ex) {
                Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", ex);
            }
            if (ret != null) {
                int ucslen2 = ret.length();
                while (ucslen2 > 0 && ret.charAt(ucslen2 - 1) == 65535) {
                    ucslen2--;
                }
                return ret.substring(0, ucslen2);
            }
        }
        if (SystemProperties.getInt("ro.vendor.feature.adn.5c601.support", 0) == 1 && length >= 2 && (((data[offset] & 240) == 176 || ((data[offset] & 255) >= KSC_BEGAINFIRST && (data[offset] & 255) <= KSC_ENDFIRST)) && (data[offset + 1] & 255) >= KSC_BEGAINSECOND && (data[offset + 1] & 255) <= KSC_ENDSECOND)) {
            int ucslen3 = length;
            while (ucslen3 > 0 && data[ucslen3 - 1] == -1) {
                ucslen3--;
            }
            try {
                return new String(data, 0, ucslen3, "KSC5601");
            } catch (UnsupportedEncodingException ex2) {
                Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException KSC5601", ex2);
            }
        }
        boolean isucs2 = false;
        char base = 0;
        int len = 0;
        if (length >= 3 && data[offset] == -127) {
            len = data[offset + 1] & 255;
            if (len > length - 3) {
                len = length - 3;
            }
            base = (char) ((data[offset + 2] & 255) << 7);
            offset += 3;
            isucs2 = true;
        } else if (length >= 4 && data[offset] == -126) {
            len = data[offset + 1] & 255;
            if (len > length - 4) {
                len = length - 4;
            }
            base = (char) (((data[offset + 2] & 255) << 8) | (data[offset + 3] & 255));
            offset += 4;
            isucs2 = true;
        }
        if (isucs2) {
            StringBuilder ret2 = new StringBuilder();
            while (len > 0) {
                if (data[offset] < 0) {
                    ret2.append((char) ((data[offset] & 127) + base));
                    offset++;
                    len--;
                }
                int count = 0;
                while (count < len && data[offset + count] >= 0) {
                    count++;
                }
                ret2.append(GsmAlphabet.gsm8BitUnpackedToString(data, offset, count));
                offset += count;
                len -= count;
            }
            return ret2.toString();
        }
        Resources resource = Resources.getSystem();
        String defaultCharset = "";
        try {
            defaultCharset = "";
        } catch (Resources.NotFoundException e) {
        }
        return GsmAlphabet.gsm8BitUnpackedToString(data, offset, length, defaultCharset.trim());
    }
}