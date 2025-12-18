package android.os.sprdpower;

import android.annotation.UnisocHiddenApi;
import android.content.Context;
import android.os.SystemProperties;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class UniPowerManagerExFactoryImpl extends UniPowerManagerExFactory {
    private static final String POWER_CONTROLLER_ENABLE = "persist.sys.pwctl.enable";
    private static final String TAG = "UniPowerManagerExFactoryImpl";
    private static final boolean mPowerControllerEnabled;
    private static UniPowerManagerExFactory sInstance;
    private PowerManagerExImpl mPowerManagerEx;

    static {
        mPowerControllerEnabled = 1 == SystemProperties.getInt(POWER_CONTROLLER_ENABLE, 1);
    }

    public PowerManagerEx getPowerManagerEx(Context context) {
        if (this.mPowerManagerEx == null) {
            this.mPowerManagerEx = new PowerManagerExImpl(context);
        }
        return this.mPowerManagerEx;
    }

    public boolean isPowerControllerEnabled() {
        return mPowerControllerEnabled;
    }
}