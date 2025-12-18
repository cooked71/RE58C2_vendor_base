package com.android.internal.telephony.uicc;

/* loaded from: classes.dex */
public class UniIccFileNotFound extends UniIccException {
    UniIccFileNotFound() {
    }

    UniIccFileNotFound(String s) {
        super(s);
    }

    UniIccFileNotFound(int ef) {
        super("ICC EF Not Found 0x" + Integer.toHexString(ef));
    }
}