package android.net.wifi;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.AudioSystemEx;
import android.util.Log;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class UniWifiContext extends ContextWrapper {
    private static final String ACTION_RESOURCES_APK = "com.android.server.wifi.intent.action.SERVICE_UNIWIFI_RESOURCES_APK";
    private static final String ACTION_WIFI_DIALOG_APK = "com.android.server.wifi.intent.action.UNIWIFI_DIALOG_APK";
    private static final String SERVICE_UNIWIFI_PACKAGE_NAME = "android";
    private static final String TAG = "UniWifiContext";
    private String mUniWifiDialogApkPkgName;
    private String mUniWifiOverlayApkPkgName;
    private Resources mUniWifiResourcesFromApk;

    public UniWifiContext(Context contextBase) {
        super(contextBase);
    }

    public String getUniWifiOverlayApkPkgName() {
        String str = this.mUniWifiOverlayApkPkgName;
        if (str != null) {
            return str;
        }
        String apkPkgNameForAction = getApkPkgNameForAction(ACTION_RESOURCES_APK);
        this.mUniWifiOverlayApkPkgName = apkPkgNameForAction;
        if (apkPkgNameForAction == null) {
            Log.e(TAG, "Attempted to fetch resources before UniWifi Resources APK is loaded!", new IllegalStateException());
            return null;
        }
        Log.i(TAG, "Found UniWifi Resources APK at : " + this.mUniWifiOverlayApkPkgName);
        return this.mUniWifiOverlayApkPkgName;
    }

    public String getUniWifiDialogApkPkgName() {
        String str = this.mUniWifiDialogApkPkgName;
        if (str != null) {
            return str;
        }
        String apkPkgNameForAction = getApkPkgNameForAction(ACTION_WIFI_DIALOG_APK);
        this.mUniWifiDialogApkPkgName = apkPkgNameForAction;
        if (apkPkgNameForAction == null) {
            Log.e(TAG, "Attempted to fetch UniWifiDialog apk before it is loaded!", new IllegalStateException());
            return null;
        }
        Log.i(TAG, "Found UniWifi Dialog APK at : " + this.mUniWifiDialogApkPkgName);
        return this.mUniWifiDialogApkPkgName;
    }

    private String getApkPkgNameForAction(String action) {
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(new Intent(action), AudioSystemEx.DEVICE_OUT_FM_HEADSET);
        Log.i(TAG, "Got resolveInfos for " + action + ": " + resolveInfos);
        resolveInfos.removeIf(new Predicate() { // from class: android.net.wifi.UniWifiContext$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UniWifiContext.lambda$getApkPkgNameForAction$0((ResolveInfo) obj);
            }
        });
        if (resolveInfos.isEmpty()) {
            return null;
        }
        if (resolveInfos.size() > 1) {
            Log.w(TAG, "Found > 1 APK that can resolve " + action + ": " + ((String) resolveInfos.stream().map(new Function() { // from class: android.net.wifi.UniWifiContext$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ResolveInfo) obj).activityInfo.applicationInfo.packageName;
                }
            }).collect(Collectors.joining(", "))));
        }
        ResolveInfo info = resolveInfos.get(0);
        return info.activityInfo.applicationInfo.packageName;
    }

    static /* synthetic */ boolean lambda$getApkPkgNameForAction$0(ResolveInfo info) {
        return !info.activityInfo.applicationInfo.sourceDir.startsWith("/system_ext/");
    }

    private Context getResourcesApkContext() {
        try {
            return createPackageContext(getUniWifiOverlayApkPkgName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf(TAG, "Failed to load resources: " + e);
            return null;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Context resourcesApkContext;
        if (this.mUniWifiResourcesFromApk == null && (resourcesApkContext = getResourcesApkContext()) != null) {
            this.mUniWifiResourcesFromApk = resourcesApkContext.getResources();
        }
        return this.mUniWifiResourcesFromApk;
    }

    public String getServiceUniWifiPackageName() {
        return SERVICE_UNIWIFI_PACKAGE_NAME;
    }
}