package android.media;

import android.R;
import android.annotation.UnisocHiddenApi;
import android.content.Context;
import android.media.IAudioServiceEx;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

@UnisocHiddenApi
/* loaded from: classes.dex */
public class AudioManagerEx extends UnisocAudioManager {
    private static String AUDIO_SERVICEEX = "audio_ex";
    private static final boolean DEBUG = false;
    private static final String TAG = "AudioManagerEx";
    private static IAudioServiceEx sService;
    private Context mApplicationContext;
    private final IBinder mICallBack;
    private Context mOriginalContext;
    private final boolean mUseFixedVolume;
    private final boolean mUseVolumeKeySounds;
    private long mVolumeKeyUpTime;

    public AudioManagerEx() {
        this.mICallBack = new Binder();
        this.mUseVolumeKeySounds = true;
        this.mUseFixedVolume = false;
    }

    public AudioManagerEx(Context context) {
        this.mICallBack = new Binder();
        setContext(context);
        this.mUseVolumeKeySounds = getContext().getResources().getBoolean(R.bool.config_permissionsIndividuallyControlled);
        this.mUseFixedVolume = getContext().getResources().getBoolean(R.bool.config_omnipresentCommunalUser);
    }

    private Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        Context context = this.mApplicationContext;
        if (context != null) {
            return context;
        }
        return this.mOriginalContext;
    }

    private void setContext(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mApplicationContext = applicationContext;
        if (applicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
    }

    private static IAudioServiceEx getServiceEx() {
        IAudioServiceEx iAudioServiceEx = sService;
        if (iAudioServiceEx != null) {
            return iAudioServiceEx;
        }
        IBinder b = ServiceManager.getService(AUDIO_SERVICEEX);
        IAudioServiceEx iAudioServiceExAsInterface = IAudioServiceEx.Stub.asInterface(b);
        sService = iAudioServiceExAsInterface;
        return iAudioServiceExAsInterface;
    }

    @UnisocHiddenApi
    public void setFmSpeakerOn(boolean on) {
        IAudioServiceEx service = getServiceEx();
        try {
            service.setForceUseSpeaker(8, on, this.mICallBack, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setFmSpeakerOn ", e);
        }
    }

    @UnisocHiddenApi
    public void setMediaSpeakerOn(boolean on) {
        IAudioServiceEx service = getServiceEx();
        try {
            service.setForceUseSpeaker(1, on, this.mICallBack, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setMediaSpeakerOn ", e);
        }
    }

    @UnisocHiddenApi
    public void setDeviceConnectionStateForFM(int device, int state, String device_address, String device_name) {
        IAudioServiceEx service = getServiceEx();
        try {
            service.setDeviceConnectionStateForFM(device, state, device_address, device_name, this.mICallBack);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setDeviceConnectionStateForFM ", e);
        }
    }
}