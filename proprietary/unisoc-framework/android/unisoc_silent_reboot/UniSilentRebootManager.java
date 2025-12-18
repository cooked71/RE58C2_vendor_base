package android.unisoc.silent.reboot;

import android.annotation.UnisocHiddenApi;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.unisoc.silent.reboot.IUniSilentReboot;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class UniSilentRebootManager {
    public static final String REBOOT_REASON = "quiescent";
    public static final String UNISOC_SILENT_REBOOT_SERVICE = "unisoc_silent_reboot";
    public static final String UPDATE_TOKEN = "unisilentreboot";
    static UniSilentRebootManager sUniSilentRebootManager = null;
    static IUniSilentReboot mService = null;

    public static UniSilentRebootManager getInstance() {
        if (sUniSilentRebootManager == null) {
            mService = IUniSilentReboot.Stub.asInterface(ServiceManager.getService(UNISOC_SILENT_REBOOT_SERVICE));
            sUniSilentRebootManager = new UniSilentRebootManager();
        }
        return sUniSilentRebootManager;
    }

    public boolean isDeviceMoving() {
        IUniSilentReboot iUniSilentReboot = mService;
        if (iUniSilentReboot != null) {
            try {
                return iUniSilentReboot.isDeviceMoving();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void silentRebootForApply() {
        IUniSilentReboot iUniSilentReboot = mService;
        if (iUniSilentReboot != null) {
            try {
                iUniSilentReboot.silentRebootForApply();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isPreparedForUnattendedReboot() {
        IUniSilentReboot iUniSilentReboot = mService;
        if (iUniSilentReboot != null) {
            try {
                iUniSilentReboot.isPreparedForUnattendedReboot();
                return true;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }
}