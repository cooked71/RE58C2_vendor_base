package android.os;

import android.annotation.UnisocHiddenApi;
import android.util.Log;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class UnisocBinderTracker extends DefaultBinderTracker {
    private static final UnisocBinderTracker INSTANCE = new UnisocBinderTracker();
    private static final String TAG = "UnisocBinderTracker";
    private boolean mMainThreadOnly;
    private final int mMyUid;
    private long mSlowThresholdMs;
    private boolean mTrackerEnabled;

    private UnisocBinderTracker() {
        int iMyUid = Process.myUid();
        this.mMyUid = iMyUid;
        this.mTrackerEnabled = false;
        this.mMainThreadOnly = true;
        this.mSlowThresholdMs = 50L;
        this.mTrackerEnabled = SystemProperties.getBoolean("debug.unibinder.tracker.enabled." + iMyUid, this.mTrackerEnabled);
        this.mMainThreadOnly = SystemProperties.getBoolean("debug.unibinder.tracker.main." + iMyUid, this.mMainThreadOnly);
        this.mSlowThresholdMs = SystemProperties.getLong("debug.unibinder.tracker.slow." + iMyUid, this.mSlowThresholdMs);
    }

    public static DefaultBinderTracker getInstance() {
        return INSTANCE;
    }

    public boolean isUniBinderTrackerEnabled() {
        return this.mTrackerEnabled;
    }

    public Object onTransactStarted(IBinder binder, int transactionCode) {
        return new Session(binder, transactionCode);
    }

    public void onTransactEnded(Object session) {
        if (session == null) {
            Log.i(TAG, "Session is null");
            return;
        }
        Session uniTrackerSession = (Session) session;
        uniTrackerSession.calculateTimeSpent();
        uniTrackerSession.warnIfNeeded();
    }

    class Session {
        IBinder mBinder;
        long mStartTime = SystemClock.uptimeMillis();
        long mTimeSpent;
        int mTransactionCode;

        public Session(IBinder binder, int transactionCode) {
            this.mBinder = binder;
            this.mTransactionCode = transactionCode;
        }

        void calculateTimeSpent() {
            long now = SystemClock.uptimeMillis();
            this.mTimeSpent = now - this.mStartTime;
        }

        void warnIfNeeded() {
            if (UnisocBinderTracker.this.mTrackerEnabled) {
                if (!UnisocBinderTracker.this.mMainThreadOnly) {
                    if (this.mTimeSpent > UnisocBinderTracker.this.mSlowThresholdMs) {
                        Log.d(UnisocBinderTracker.TAG, "This binder Transaction has spent " + this.mTimeSpent + " ms", new Throwable());
                    }
                } else if (Looper.myLooper() == Looper.getMainLooper() && this.mTimeSpent > UnisocBinderTracker.this.mSlowThresholdMs) {
                    Log.d(UnisocBinderTracker.TAG, "Main thread binder transaction has spent " + this.mTimeSpent + " ms", new Throwable());
                }
            }
        }
    }
}