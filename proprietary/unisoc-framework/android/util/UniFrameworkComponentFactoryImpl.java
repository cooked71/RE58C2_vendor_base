package android.util;

import android.content.Context;
import android.event.UniEventManager;
import android.event.UniEventService;
import android.net.LinkTurboManager;
import android.net.LinkTurboManagerImpl;
import android.net.NetworkAdapterManager;
import android.net.NetworkAdapterManagerImpl;
import android.net.wifi.UWifiManager;
import android.net.wifi.UniWifiManager;
import android.os.DefaultBinderTracker;
import android.os.UnisocBinderTracker;

/* loaded from: classes.dex */
public class UniFrameworkComponentFactoryImpl extends UniFrameworkComponentFactory {
    public FooBar makeFooBar() {
        return new UniFooBar();
    }

    public NetworkAdapterManager makeNetworkAdapterManager() {
        return new NetworkAdapterManagerImpl();
    }

    public LinkTurboManager makeLinkTurboManager(Context context) {
        return new LinkTurboManagerImpl(context);
    }

    public UniWifiManager makeUniWifiManager() {
        return new UWifiManager();
    }

    public UniEventManager makeUniEventManager() {
        return new UniEventService();
    }

    public DefaultBinderTracker makeUnisocBinderTracker() {
        return UnisocBinderTracker.getInstance();
    }
}