package android.app;

import android.annotation.UnisocHiddenApi;
import android.util.Slog;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class AlarmManagerWrapper {
    static final int EARLY_START_TIME = 120000;
    static final String TAG = "AlarmManagerWrapper";

    public static void cancelAlarm(AlarmManager am, PendingIntent operation) {
        am.cancel(operation);
        am.setPowerOnToRtc(0L, operation);
    }

    public static void setPowerOffWakeup(AlarmManager am, long triggerAtMillis, PendingIntent operation) {
        Slog.d(TAG, "setPowerOffWakeup : triggerAtMillis = " + triggerAtMillis + " ,operation = " + operation);
        am.setExact(0, triggerAtMillis, operation);
    }

    public static void setPowerOnWakeup(AlarmManager am, long triggerAtMillis, PendingIntent operation) {
        Slog.d(TAG, "setPowerOnWakeup : triggerAtMillis = " + triggerAtMillis + " ,operation = " + operation);
        am.setPowerOnToRtc(triggerAtMillis, operation);
        am.setExact(0, triggerAtMillis, operation);
    }

    public static void setPowerOffAlarm(AlarmManager am, long triggerAtMillis, PendingIntent operation) {
        Slog.d(TAG, "setPowerOffAlarm : triggerAtMillis = " + triggerAtMillis + " ,operation = " + operation);
        am.setPowerOnToRtc(triggerAtMillis - 120000, operation);
        am.setExact(0, triggerAtMillis, operation);
    }
}