package android.net;

import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes.dex */
public final class LinkTurboThread extends HandlerThread {

    private static class Singleton {
        private static final LinkTurboThread INSTANCE = LinkTurboThread.createInstance();

        private Singleton() {
        }
    }

    private LinkTurboThread() {
        super("LinkTurboThread");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkTurboThread createInstance() {
        LinkTurboThread t = new LinkTurboThread();
        t.start();
        return t;
    }

    public static LinkTurboThread get() {
        return Singleton.INSTANCE;
    }

    public static Looper getInstanceLooper() {
        return Singleton.INSTANCE.getLooper();
    }
}