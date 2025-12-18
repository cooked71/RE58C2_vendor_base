package android.location;

import android.annotation.UnisocHiddenApi;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class UnisocLocationFeaturesUtils extends LocationFeaturesUtils {
    private static final String TAG = UnisocLocationFeaturesUtils.class.getSimpleName();
    private boolean mAgpsSettingEnabled;
    private boolean mGnssDisabled;
    private boolean mGpsTimeEnabled;
    private boolean mLocationDisabled;

    public UnisocLocationFeaturesUtils(Context context) {
        try {
            this.mGpsTimeEnabled = context.getResources().getBoolean(134414374);
            this.mAgpsSettingEnabled = context.getResources().getBoolean(134414336);
            this.mLocationDisabled = context.getResources().getBoolean(134414375);
            this.mGnssDisabled = context.getResources().getBoolean(134414373);
            Log.d(TAG, "mGpsTimeEnabled = " + this.mGpsTimeEnabled + ",mAgpsSettingEnabled = " + this.mAgpsSettingEnabled + ",mLocationDisabled = " + this.mLocationDisabled + ",mGnssDisabled = " + this.mGnssDisabled);
        } catch (Resources.NotFoundException e) {
            Log.i(TAG, "failed to read location features.");
        }
    }

    public boolean isSupportGpsTime() {
        return this.mGpsTimeEnabled;
    }

    public boolean isSupportAgpsSettings() {
        return this.mAgpsSettingEnabled;
    }

    public boolean isLocationDisabled() {
        return this.mLocationDisabled;
    }

    public boolean isGnssDisabled() {
        return this.mGnssDisabled;
    }
}