package com.android.internal.telephony.phonebook;

import android.os.SystemProperties;
import com.android.telephony.Rlog;

/* loaded from: classes.dex */
public abstract class UniPhoneBookLog {
    private static final boolean DEBUG = SystemProperties.get("ro.build.type").equalsIgnoreCase("userdebug");
    private static final String TAG = "UniPhoneBookLog";

    public static void d(Object caller, String msg) {
        if (!DEBUG) {
            return;
        }
        String className = caller.getClass().getName();
        Rlog.d(TAG, className.substring(className.lastIndexOf(46) + 1) + ": " + msg);
    }

    public static void d(String caller, String msg) {
        Rlog.d(TAG, caller + ": " + msg);
    }

    public static void e(Object caller, String msg) {
        if (!DEBUG) {
            return;
        }
        String className = caller.getClass().getName();
        Rlog.e(TAG, className.substring(className.lastIndexOf(46) + 1) + ": " + msg);
    }

    public static void e(String caller, String msg) {
        Rlog.e(TAG, caller + ": " + msg);
    }

    public static void w(Object caller, String msg) {
        if (!DEBUG) {
            return;
        }
        String className = caller.getClass().getName();
        Rlog.w(TAG, className.substring(className.lastIndexOf(46) + 1) + ": " + msg);
    }

    public static void w(String caller, String msg) {
        Rlog.w(TAG, caller + ": " + msg);
    }

    public static void i(Object caller, String msg) {
        if (!DEBUG) {
            return;
        }
        String className = caller.getClass().getName();
        Rlog.i(TAG, className.substring(className.lastIndexOf(46) + 1) + ": " + msg);
    }

    public static void i(String caller, String msg) {
        Rlog.i(TAG, caller + ": " + msg);
    }
}