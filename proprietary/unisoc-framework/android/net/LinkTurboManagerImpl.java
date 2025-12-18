package android.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ILinkTurboManager;
import android.net.LinkTurboManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.telephony.MobileNetworkUtils;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class LinkTurboManagerImpl extends LinkTurboManager {
    private static final int ALREADY_UNREGISTERED = -1;
    private static final String TAG = "SmartLink2.0:LinkTurboManagerImpl";
    private static CallbackHandler sCallbackHandler;
    private volatile boolean isMpHttpRegistered = false;
    private ServiceConnection mConn;
    private final Context mContext;
    private volatile ILinkTurboManager mService;
    private static LinkTurboManager.LinkTurboCallback mLinkTurboCallback = null;
    private static String mPackageName = null;
    private static final HashMap<Integer, LinkTurboManager.LinkTurboCallback> sCallbacks = new HashMap<>();

    private static class CallbackHandler extends Handler {
        private static final boolean DBG = false;
        private static final String TAG = "LinkTurboManager.CallbackHandler";

        CallbackHandler(Looper looper) {
            super(looper);
        }

        CallbackHandler(Handler handler) {
            this(((Handler) Preconditions.checkNotNull(handler, "Handler cannot be null.")).getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(TAG, "handleMessage " + LinkTurboManager.getCallbackName(message.what));
            int requestId = message.getData().getInt("requestId");
            Log.i(TAG, "handleMessage:the requestId from linkturboservice is :" + requestId);
            LinkTurboManager.LinkTurboCallback callback = null;
            LinkTurboManager.MpHttpCallback mpHttpCallback = null;
            int msgWhat = message.what;
            if (msgWhat == 1 || msgWhat == 3 || msgWhat == 2) {
                synchronized (LinkTurboManagerImpl.sCallbacks) {
                    callback = (LinkTurboManager.LinkTurboCallback) LinkTurboManagerImpl.sCallbacks.get(Integer.valueOf(requestId));
                }
                if (callback == null) {
                    Log.w(TAG, "callback not found for " + LinkTurboManager.getCallbackName(message.what) + " message");
                    return;
                }
            }
            switch (msgWhat) {
                case 1:
                    if (callback != null) {
                        callback.onBindProcessToNetwork((Network) message.obj);
                        return;
                    }
                    return;
                case 2:
                    if (callback != null) {
                        callback.onClearBindingRequest();
                        return;
                    }
                    return;
                case MobileNetworkUtils.TelephonyManagerConstants.NETWORK_MODE_GSM_UMTS /* 3 */:
                    if (callback != null) {
                        callback.onBindProcessToNetwork((Network) message.obj);
                        return;
                    }
                    return;
                case 4:
                    if (0 != 0) {
                        mpHttpCallback.notifyNetworkAvailable(message.getData().getString("InterfaceName"));
                        return;
                    }
                    return;
                case MobileNetworkUtils.TelephonyManagerConstants.NETWORK_MODE_CDMA_NO_EVDO /* 5 */:
                    if (0 != 0) {
                        mpHttpCallback.notifyNetworkLost(message.getData().getString("InterfaceName"));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public LinkTurboManagerImpl(Context context) {
        this.mConn = null;
        Log.i(TAG, "LinkTurboManagerImpl one args");
        this.mContext = context;
        this.mConn = new ServiceConnection() { // from class: android.net.LinkTurboManagerImpl.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i(LinkTurboManagerImpl.TAG, "Linkturbo service is disconnected already");
                LinkTurboManagerImpl.this.mService = null;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LinkTurboManagerImpl.TAG, "Linkturbo service is connected already, bind service name: " + name.getClassName());
                LinkTurboManagerImpl.this.mService = ILinkTurboManager.Stub.asInterface(service);
                if (LinkTurboManagerImpl.this.mService != null && LinkTurboManagerImpl.mLinkTurboCallback != null && LinkTurboManagerImpl.mPackageName != null) {
                    Log.i(LinkTurboManagerImpl.TAG, "start register mLinkTurboCallback & mPackageName");
                    LinkTurboManagerImpl.this.registerNetworkCallback(LinkTurboManagerImpl.mLinkTurboCallback, LinkTurboManagerImpl.this.getDefaultHandler(), LinkTurboManagerImpl.mPackageName);
                } else {
                    Log.e(LinkTurboManagerImpl.TAG, "mService or mLinkTurboCallback or mPackageName is null");
                }
            }
        };
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("com.sprd.linkturbo.ACTION_AIDL_SERVICE");
        serviceIntent.setPackage("com.sprd.linkturbo");
        Log.i(TAG, "bindService Linkturbo service --com.sprd.linkturbo");
        context.bindService(serviceIntent, this.mConn, 1);
    }

    public void destroy() {
        if (this.mConn != null && this.mContext != null) {
            Log.i(TAG, "unbind to SmartLinkService now");
            this.mContext.unbindService(this.mConn);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CallbackHandler getDefaultHandler() {
        CallbackHandler callbackHandler;
        synchronized (sCallbacks) {
            if (sCallbackHandler == null) {
                sCallbackHandler = new CallbackHandler(LinkTurboThread.getInstanceLooper());
            }
            callbackHandler = sCallbackHandler;
        }
        return callbackHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0043 A[Catch: all -> 0x0070, TryCatch #0 {, blocks: (B:6:0x0010, B:8:0x002e, B:9:0x0035, B:11:0x0043, B:13:0x0064, B:14:0x006b, B:15:0x006d), top: B:23:0x0010, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int sendRequestForNetwork(android.net.LinkTurboManager.LinkTurboCallback r8, android.net.LinkTurboManagerImpl.CallbackHandler r9, java.lang.String r10) {
        /*
            r7 = this;
            java.lang.String r0 = "SmartLink2.0:LinkTurboManagerImpl"
            java.lang.String r1 = "sendRequestForNetwork "
            android.util.Log.i(r0, r1)
            checkCallbackNotNull(r8)
            r0 = 0
            java.util.HashMap<java.lang.Integer, android.net.LinkTurboManager$LinkTurboCallback> r1 = android.net.LinkTurboManagerImpl.sCallbacks     // Catch: android.os.RemoteException -> L73
            monitor-enter(r1)     // Catch: android.os.RemoteException -> L73
            if (r8 == 0) goto L35
            java.lang.String r2 = "SmartLink2.0:LinkTurboManagerImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L70
            r3.<init>()     // Catch: java.lang.Throwable -> L70
            java.lang.String r4 = "sendRequestForNetwork callback.networkRequestId  = "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L70
            int r4 = r8.networkRequestId     // Catch: java.lang.Throwable -> L70
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L70
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L70
            android.util.Log.i(r2, r3)     // Catch: java.lang.Throwable -> L70
            int r2 = r8.networkRequestId     // Catch: java.lang.Throwable -> L70
            if (r2 <= 0) goto L35
            java.lang.String r2 = "SmartLink2.0:LinkTurboManagerImpl"
            java.lang.String r3 = "LinkTurboCallback was already registered"
            android.util.Log.e(r2, r3)     // Catch: java.lang.Throwable -> L70
        L35:
            android.os.Messenger r2 = new android.os.Messenger     // Catch: java.lang.Throwable -> L70
            r2.<init>(r9)     // Catch: java.lang.Throwable -> L70
            android.os.Binder r3 = new android.os.Binder     // Catch: java.lang.Throwable -> L70
            r3.<init>()     // Catch: java.lang.Throwable -> L70
            android.net.ILinkTurboManager r4 = r7.mService     // Catch: java.lang.Throwable -> L70
            if (r4 == 0) goto L6d
            android.net.ILinkTurboManager r4 = r7.mService     // Catch: java.lang.Throwable -> L70
            int r4 = r4.registerLinkTurboCallback(r2, r3, r10)     // Catch: java.lang.Throwable -> L70
            r0 = r4
            java.lang.String r4 = "SmartLink2.0:LinkTurboManagerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L70
            r5.<init>()     // Catch: java.lang.Throwable -> L70
            java.lang.String r6 = "mService.registerLinkTurboCallback : requestId  = "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> L70
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch: java.lang.Throwable -> L70
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L70
            android.util.Log.i(r4, r5)     // Catch: java.lang.Throwable -> L70
            if (r0 <= 0) goto L6b
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L70
            r1.put(r4, r8)     // Catch: java.lang.Throwable -> L70
        L6b:
            r8.networkRequestId = r0     // Catch: java.lang.Throwable -> L70
        L6d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L70
            return r0
        L70:
            r2 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L70
            throw r2     // Catch: android.os.RemoteException -> L73
        L73:
            r1 = move-exception
            java.lang.RuntimeException r2 = r1.rethrowFromSystemServer()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.LinkTurboManagerImpl.sendRequestForNetwork(android.net.LinkTurboManager$LinkTurboCallback, android.net.LinkTurboManagerImpl$CallbackHandler, java.lang.String):int");
    }

    private static void checkCallbackNotNull(LinkTurboManager.LinkTurboCallback callback) {
        Preconditions.checkNotNull(callback, "null NetworkCallback");
    }

    public void registerMpHttpNetworkCallback(LinkTurboManager.MpHttpCallback callback) throws InterruptedException {
        Log.i(TAG, "registerMpHttpNetworkCallback ");
        if (this.mService == null) {
            for (int tryNum = 0; tryNum < 3; tryNum++) {
                try {
                    if (this.mService != null) {
                        Log.i(TAG, "tryNum:" + tryNum + ", mService is not null!!!");
                        registerMpHttpNetworkCallback(callback, getDefaultHandler());
                        return;
                    } else {
                        Thread.sleep(1000L);
                        Log.i(TAG, "after sleep 1 seconds for tryNum:" + tryNum + ", mService is still null!!!");
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "error occurred:" + e);
                    return;
                }
            }
            return;
        }
        registerMpHttpNetworkCallback(callback, getDefaultHandler());
    }

    public void registerMpHttpNetworkCallback(LinkTurboManager.MpHttpCallback callback, Handler handler) {
        new CallbackHandler(handler);
    }

    public void unregisterMpHttpNetworkCallback(LinkTurboManager.MpHttpCallback callback) {
        if (callback == null) {
            Log.e(TAG, "unregisterMpHttpNetworkCallback MpHttpCallback is null");
        } else {
            new ArrayList();
        }
    }

    public Network getNetwork(String interfaceName) throws InterruptedException {
        try {
            if (this.mService == null) {
                if (this.mService != null) {
                    Log.i(TAG, "getNetwork mService is not null!!!");
                    Network network = this.mService.getNetwork(interfaceName);
                    return network;
                }
                Thread.sleep(1000L);
                Log.i(TAG, "getNetwork after sleep 1 seconds, mService is still null!!!");
                return null;
            }
            Network network2 = this.mService.getNetwork(interfaceName);
            return network2;
        } catch (Exception e) {
            Log.e(TAG, "some error occurred: " + e);
            return null;
        }
    }

    public void registerNetworkCallback(LinkTurboManager.LinkTurboCallback linkTurboCallback, String packageName) {
        Log.i(TAG, "registerNetworkCallback ");
        if (this.mService == null) {
            Log.i(TAG, "Due to mService is null, storing mLinkTurboCallback & mPackageName ");
            mLinkTurboCallback = linkTurboCallback;
            mPackageName = packageName;
        } else {
            Log.i(TAG, "Due to mService is not null, calling registerNetworkCallback function");
            registerNetworkCallback(linkTurboCallback, getDefaultHandler(), packageName);
        }
    }

    public void registerNetworkCallback(LinkTurboManager.LinkTurboCallback linkTurboCallback, Handler handler, String packageName) {
        CallbackHandler cbHandler = new CallbackHandler(handler);
        sendRequestForNetwork(linkTurboCallback, cbHandler, packageName);
    }

    public void unregisterNetworkCallback(LinkTurboManager.LinkTurboCallback linkTurboCallback) {
        checkCallbackNotNull(linkTurboCallback);
        List<Integer> reqIds = new ArrayList<>();
        HashMap<Integer, LinkTurboManager.LinkTurboCallback> map = sCallbacks;
        synchronized (map) {
            if (linkTurboCallback.networkRequestId != 0 && linkTurboCallback.networkRequestId != -1) {
                if (this.mService == null) {
                    Log.i(TAG, "mService is null, can not unregisterNetworkCallback");
                    return;
                }
                for (Map.Entry<Integer, LinkTurboManager.LinkTurboCallback> e : map.entrySet()) {
                    if (e.getValue() == linkTurboCallback) {
                        reqIds.add(e.getKey());
                    }
                }
                for (Integer r : reqIds) {
                    try {
                        this.mService.unregisterLinkTurboCallback(r.intValue());
                        sCallbacks.remove(r);
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                }
                linkTurboCallback.networkRequestId = -1;
                return;
            }
            Log.i(TAG, "linkTurboCallback was not registered");
        }
    }

    public void onBindedNetworkFinished(int netId, int pid) {
        if (this.mService != null) {
            try {
                this.mService.onBindedNetworkFinished(netId, pid);
                Log.i(TAG, "mService.onBindedNetworkChanged : netId  = " + netId + " pid = " + pid);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}