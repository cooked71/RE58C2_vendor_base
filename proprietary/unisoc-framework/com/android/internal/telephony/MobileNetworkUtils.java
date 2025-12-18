package com.android.internal.telephony;

/* loaded from: classes.dex */
public class MobileNetworkUtils {

    public static class RadioAccessFamily {
        public static final int CDMA = 72;
        public static final int EVDO = 10288;
        public static final int GSM = 32771;
        public static final int HS = 17280;
        public static final int LTE = 266240;
        public static final int NR = 524288;
        public static final int RAF_1xRTT = 64;
        public static final int RAF_EDGE = 2;
        public static final int RAF_EHRPD = 8192;
        public static final int RAF_EVDO_0 = 16;
        public static final int RAF_EVDO_A = 32;
        public static final int RAF_EVDO_B = 2048;
        public static final int RAF_GPRS = 1;
        public static final int RAF_GSM = 32768;
        public static final int RAF_HSDPA = 128;
        public static final int RAF_HSPA = 512;
        public static final int RAF_HSPAP = 16384;
        public static final int RAF_HSUPA = 256;
        public static final int RAF_IS95A = 8;
        public static final int RAF_IS95B = 8;
        public static final int RAF_LTE = 4096;
        public static final int RAF_LTE_CA = 262144;
        public static final int RAF_NR = 524288;
        public static final int RAF_TD_SCDMA = 65536;
        public static final int RAF_UMTS = 4;
        public static final int RAF_UNKNOWN = 0;
        public static final int WCDMA = 17284;
    }

    public static class TelephonyManagerConstants {
        public static final int NETWORK_MODE_CDMA_EVDO = 4;
        public static final int NETWORK_MODE_CDMA_NO_EVDO = 5;
        public static final int NETWORK_MODE_EVDO_NO_CDMA = 6;
        public static final int NETWORK_MODE_GLOBAL = 7;
        public static final int NETWORK_MODE_GSM_ONLY = 1;
        public static final int NETWORK_MODE_GSM_UMTS = 3;
        public static final int NETWORK_MODE_LTE_CDMA_EVDO = 8;
        public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 10;
        public static final int NETWORK_MODE_LTE_GSM_WCDMA = 9;
        public static final int NETWORK_MODE_LTE_ONLY = 11;
        public static final int NETWORK_MODE_LTE_TDSCDMA = 15;
        public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
        public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 17;
        public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 20;
        public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 19;
        public static final int NETWORK_MODE_LTE_WCDMA = 12;
        public static final int NETWORK_MODE_NR_LTE = 24;
        public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO = 25;
        public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
        public static final int NETWORK_MODE_NR_LTE_GSM_WCDMA = 26;
        public static final int NETWORK_MODE_NR_LTE_TDSCDMA = 29;
        public static final int NETWORK_MODE_NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 33;
        public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM = 30;
        public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM_WCDMA = 32;
        public static final int NETWORK_MODE_NR_LTE_TDSCDMA_WCDMA = 31;
        public static final int NETWORK_MODE_NR_LTE_WCDMA = 28;
        public static final int NETWORK_MODE_NR_ONLY = 23;
        public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 21;
        public static final int NETWORK_MODE_TDSCDMA_GSM = 16;
        public static final int NETWORK_MODE_TDSCDMA_GSM_WCDMA = 18;
        public static final int NETWORK_MODE_TDSCDMA_ONLY = 13;
        public static final int NETWORK_MODE_TDSCDMA_WCDMA = 14;
        public static final int NETWORK_MODE_UNKNOWN = -1;
        public static final int NETWORK_MODE_WCDMA_ONLY = 2;
        public static final int NETWORK_MODE_WCDMA_PREF = 0;
    }

    public static long getRafFromNetworkType(int type) {
        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_GSM_UMTS /* 3 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_LTE_GSM_WCDMA /* 9 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_LTE_ONLY /* 11 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_LTE_WCDMA /* 12 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_NR_ONLY /* 23 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_NR_LTE /* 24 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_NR_LTE_GSM_WCDMA /* 26 */:
                break;
            case TelephonyManagerConstants.NETWORK_MODE_NR_LTE_WCDMA /* 28 */:
                break;
        }
        return 50055L;
    }
}