package android.os.sprdpower;

import android.annotation.UnisocHiddenApi;
import android.content.Context;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.sprdpower.IPowerManagerEx;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class PowerManagerExImpl extends PowerManagerEx {
    private static final String POWER_CONTROLLER_ENABLE = "persist.sys.pwctl.enable";
    private static final String TAG = "PowerManagerExImpl";
    private static final boolean mPowerControllerEnabled;
    private final IPowerManagerEx mPowerMan;

    static {
        mPowerControllerEnabled = 1 == SystemProperties.getInt(POWER_CONTROLLER_ENABLE, 1);
    }

    public boolean isPowerControllerEnabled() {
        return mPowerControllerEnabled;
    }

    public PowerManagerExImpl(Context context) {
        super(context);
        this.mPowerMan = IPowerManagerEx.Stub.asInterface(ServiceManager.getService("power_ex"));
    }

    public void shutdownForAlarm() {
        try {
            this.mPowerMan.shutdownForAlarm(false, true);
        } catch (Exception e) {
            Log.i(TAG, "shutdownForAlarm exception:" + e);
        }
    }

    public void rebootAnimation() {
        try {
            this.mPowerMan.rebootAnimation();
        } catch (Exception e) {
            Log.i(TAG, "rebootAnimation excpetion:" + e);
        }
    }

    public void scheduleButtonLightTimeout(long now) {
        try {
            this.mPowerMan.scheduleButtonLightTimeout(now);
        } catch (Exception e) {
            Log.i(TAG, "scheduleButtonLightTimeout excpetion:" + e);
        }
    }

    public void setEventUserActivityNeeded(boolean bEventNeeded) {
        try {
            this.mPowerMan.setEventUserActivityNeeded(bEventNeeded);
        } catch (Exception e) {
            Log.i(TAG, "setEventUserActivityNeeded exception:" + e);
        }
    }

    public boolean forcePowerSaveMode(boolean mode) {
        try {
            return this.mPowerMan.forcePowerSaveMode(mode);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setUltraPowerSaveMode(boolean mode) {
        try {
            return this.mPowerMan.setUltraPowerSaveMode(mode);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUltraPowerSaveMode() {
        try {
            return this.mPowerMan.isUltraPowerSaveMode();
        } catch (Exception e) {
            return false;
        }
    }

    public int getPowerSaveMode() {
        try {
            return this.mPowerMan.getPowerSaveMode();
        } catch (Exception e) {
            return 1;
        }
    }

    public int getPrePowerSaveMode() {
        try {
            return this.mPowerMan.getPrePowerSaveMode();
        } catch (Exception e) {
            return 1;
        }
    }

    public boolean setPowerSaveMode(int mode) {
        try {
            return this.mPowerMan.setPowerSaveMode(mode);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getSmartSavingModeWhenCharging() {
        try {
            return this.mPowerMan.getSmartSavingModeWhenCharging();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setSmartSavingModeWhenCharging(boolean bExit) {
        try {
            return this.mPowerMan.setSmartSavingModeWhenCharging(bExit);
        } catch (Exception e) {
            return false;
        }
    }

    public AppPowerSaveConfig getAppPowerSaveConfig(String appName) {
        try {
            return this.mPowerMan.getAppPowerSaveConfig(appName);
        } catch (Exception e) {
            return new AppPowerSaveConfig();
        }
    }

    public int getAppPowerSaveConfigWithType(String appName, int type) {
        try {
            return this.mPowerMan.getAppPowerSaveConfigWithType(appName, type);
        } catch (Exception e) {
            return 2;
        }
    }

    public boolean setAppPowerSaveConfig(String appName, AppPowerSaveConfig config) {
        try {
            return this.mPowerMan.setAppPowerSaveConfig(appName, config);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAppPowerSaveConfigWithType(String appName, int type, int value) {
        try {
            return this.mPowerMan.setAppPowerSaveConfigWithType(appName, type, value);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setAppPowerSaveConfigListWithType(List<String> appList, int type, int value) {
        try {
            return this.mPowerMan.setAppPowerSaveConfigListWithType(appList, type, value);
        } catch (Exception e) {
            return false;
        }
    }

    public int getAppNumWithSpecificConfig(int type, int value) {
        try {
            return this.mPowerMan.getAppNumWithSpecificConfig(type, value);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean addAllowedAppInUltraSavingMode(String componentNameStr) {
        try {
            return this.mPowerMan.addAllowedAppInUltraSavingMode(componentNameStr);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delAllowedAppInUltraSavingMode(String componentNameStr) {
        try {
            return this.mPowerMan.delAllowedAppInUltraSavingMode(componentNameStr);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAllowedAppListInUltraSavingMode() {
        try {
            return this.mPowerMan.getAllowedAppListInUltraSavingMode();
        } catch (Exception e) {
            return new ArrayList();
        }
    }
}