package com.unisoc.internal.app;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IPowerManager;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/* loaded from: classes.dex */
public class ShutdownCountdownActivity extends Activity {
    private static final int SHUTDOWN_FOR_OVERHEAT_FLAG = 1;
    private static final String TAG = "ShutdownCountdownActivity";
    static boolean sSingleInstance = false;
    private AlertDialog mDialog;
    private IPowerManager mPm;
    private TelephonyManager mTelephonyManager;
    private PowerManager.WakeLock mWakeLock;
    private int mSeconds = 15;
    private int mSecondsForOverheat = 15;
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.1
        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int state, String ignored) {
            Log.i(ShutdownCountdownActivity.TAG, "state = " + state + ",ignored = " + ignored);
            if (1 == state) {
                ShutdownCountdownActivity.this.mHandler.removeCallbacks(ShutdownCountdownActivity.this.mShutdownAction);
                ShutdownCountdownActivity.this.mDialog.cancel();
                ShutdownCountdownActivity.this.finish();
            }
        }
    };
    private Handler mHandler = new Handler();
    private Runnable mShutdownAction = new Runnable() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.2
        @Override // java.lang.Runnable
        public void run() {
            ShutdownCountdownActivity shutdownCountdownActivity = ShutdownCountdownActivity.this;
            shutdownCountdownActivity.mSeconds--;
            if (ShutdownCountdownActivity.this.mDialog != null) {
                ShutdownCountdownActivity shutdownCountdownActivity2 = ShutdownCountdownActivity.this;
                shutdownCountdownActivity2.setSecondsForDialog(shutdownCountdownActivity2.mSeconds, 134938625);
            }
            if (ShutdownCountdownActivity.this.mSeconds > 0) {
                ShutdownCountdownActivity.this.mHandler.postDelayed(ShutdownCountdownActivity.this.mShutdownAction, 1000L);
            } else {
                ShutdownCountdownActivity.this.mHandler.post(new Runnable() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.i(ShutdownCountdownActivity.TAG, "ShutdownThread->shutdown");
                        try {
                            if (ShutdownCountdownActivity.this.mDialog != null) {
                                ShutdownCountdownActivity.this.mDialog.dismiss();
                            }
                            ShutdownCountdownActivity.this.mPm.shutdown(false, "timer", false);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };
    private Runnable mShutdownForOverheat = new Runnable() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.3
        @Override // java.lang.Runnable
        public void run() {
            ShutdownCountdownActivity shutdownCountdownActivity = ShutdownCountdownActivity.this;
            shutdownCountdownActivity.mSecondsForOverheat--;
            if (ShutdownCountdownActivity.this.mDialog != null) {
                ShutdownCountdownActivity shutdownCountdownActivity2 = ShutdownCountdownActivity.this;
                shutdownCountdownActivity2.setSecondsForDialog(shutdownCountdownActivity2.mSecondsForOverheat, 134938624);
            }
            if (ShutdownCountdownActivity.this.mSecondsForOverheat > 0) {
                ShutdownCountdownActivity.this.mHandler.postDelayed(ShutdownCountdownActivity.this.mShutdownForOverheat, 1000L);
            } else {
                ShutdownCountdownActivity.this.mHandler.post(new Runnable() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.i(ShutdownCountdownActivity.TAG, "ShutdownThread->shutdown");
                        try {
                            if (ShutdownCountdownActivity.this.mDialog != null) {
                                ShutdownCountdownActivity.this.mDialog.dismiss();
                            }
                            ShutdownCountdownActivity.this.mPm.shutdown(false, "timer", false);
                        } catch (RemoteException e) {
                        }
                    }
                });
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler;
        super.onCreate(savedInstanceState);
        if (sSingleInstance && (handler = this.mHandler) != null && !handler.hasCallbacks(this.mShutdownAction) && !this.mHandler.hasCallbacks(this.mShutdownForOverheat)) {
            Log.i(TAG, "already has ShutdownCountdownActivity, exit onCreate!");
            return;
        }
        sSingleInstance = true;
        Intent intent = getIntent();
        String action = intent.getAction();
        Log.i(TAG, "onCreate(): Action=" + action);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        telephonyManager.listen(this.mPhoneStateListener, 32);
        this.mPm = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
        getWindow().addFlags(6815872);
        getWindow().getDecorView().setAlpha(0.0f);
        PowerManager pm = (PowerManager) getSystemService("power");
        this.mWakeLock = pm.newWakeLock(1, "ShutdownActivity");
        Log.i(TAG, "Countdown dialog need power wake lock to show countdown normally ,acquire mWakeLock");
        this.mWakeLock.acquire();
        if ((getResources().getConfiguration().uiMode & 48) == 16) {
            this.mDialog = new AlertDialog.Builder(this, R.style.Theme.Material.Light.CompactMenu).create();
        } else {
            this.mDialog = new AlertDialog.Builder(this, R.style.Theme.Material.BaseDialog).create();
        }
        this.mDialog.setTitle(R.string.permlab_requestDeletePackages);
        if (intent.getIntExtra("ShutdownDueToOverheat", 0) == 1) {
            setSecondsForDialog(this.mSecondsForOverheat, 134938624);
            this.mDialog.setButton(-3, getText(R.string.permlab_requestDeletePackages), new DialogInterface.OnClickListener() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (ShutdownCountdownActivity.this.mDialog != null) {
                            ShutdownCountdownActivity.this.mDialog.dismiss();
                        }
                        ShutdownCountdownActivity.this.mPm.shutdown(false, "timer", false);
                    } catch (RemoteException e) {
                    }
                }
            });
            this.mDialog.setCancelable(false);
            this.mDialog.getWindow().getAttributes().setTitle("ShutdownTiming");
            this.mDialog.getWindow().setType(2009);
            this.mDialog.show();
            this.mHandler.postDelayed(this.mShutdownForOverheat, 1000L);
            return;
        }
        setSecondsForDialog(this.mSeconds, 134938625);
        this.mDialog.setButton(-3, getText(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.unisoc.internal.app.ShutdownCountdownActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ShutdownCountdownActivity.this.mHandler.removeCallbacks(ShutdownCountdownActivity.this.mShutdownAction);
                dialog.cancel();
                ShutdownCountdownActivity.this.finish();
            }
        });
        this.mDialog.setCancelable(false);
        this.mDialog.getWindow().getAttributes().setTitle("ShutdownTiming");
        this.mDialog.getWindow().setType(2009);
        this.mDialog.show();
        this.mHandler.postDelayed(this.mShutdownAction, 1000L);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        Handler handler;
        super.onDestroy();
        if (sSingleInstance && (handler = this.mHandler) != null && (handler.hasCallbacks(this.mShutdownAction) || this.mHandler.hasCallbacks(this.mShutdownForOverheat))) {
            Log.i(TAG, "already has ShutdownCountdownActivity, exit onDestroy!");
            return;
        }
        sSingleInstance = false;
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDialog = null;
        }
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        if (this.mWakeLock != null) {
            Log.i(TAG, "Countdown dialog dismiss - release mWakeLock.");
            this.mWakeLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSecondsForDialog(int seconds, int resouceName) {
        if (seconds > 0) {
            this.mDialog.setMessage(getResources().getQuantityString(resouceName, seconds, Integer.valueOf(seconds)));
        } else {
            this.mDialog.setMessage(getString(R.string.resolver_turn_on_work_apps));
        }
    }
}