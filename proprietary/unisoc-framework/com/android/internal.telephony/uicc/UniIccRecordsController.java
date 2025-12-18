package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

/* loaded from: classes.dex */
public class UniIccRecordsController extends Handler {
    private static final int EVENT_SIM_LOADED = 0;
    private static final int EVENT_SIM_UNAVAILABLE = 1;
    private static final String LOG_TAG = "UniIccRecordsController";
    private static UniIccRecordsController mInstance;
    private static int mPhoneCount = TelephonyManager.getDefault().getPhoneCount();
    private BroadcastReceiver mReceiver;
    private String[] mSimState;
    private UniIccRecords[] mUniIccRecords;

    private UniIccRecordsController(Context context) {
        int i = mPhoneCount;
        this.mUniIccRecords = new UniIccRecords[i];
        this.mSimState = new String[i];
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.uicc.UniIccRecordsController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    int phoneId = intent.getIntExtra("phone", Integer.MAX_VALUE);
                    String simState = intent.getStringExtra("ss");
                    Log.i(UniIccRecordsController.LOG_TAG, "receive broadcast ACTION_SIM_STATE_CHANGED " + phoneId);
                    Log.i(UniIccRecordsController.LOG_TAG, "simState = " + simState);
                    if (UniIccRecordsController.this.mSimState[phoneId].equals(simState)) {
                        Log.i(UniIccRecordsController.LOG_TAG, "Sim State not change return");
                        return;
                    }
                    UniIccRecordsController.this.mSimState[phoneId] = simState;
                    if ("LOADED".equals(simState)) {
                        UniIccRecordsController uniIccRecordsController = UniIccRecordsController.this;
                        uniIccRecordsController.sendMessage(uniIccRecordsController.obtainMessage(0, phoneId, -1));
                    } else if ("NOT_READY".equals(simState) || "ABSENT".equals(simState) || "CARD_IO_ERROR".equals(simState) || "UNKNOWN".equals(simState)) {
                        UniIccRecordsController uniIccRecordsController2 = UniIccRecordsController.this;
                        uniIccRecordsController2.sendMessage(uniIccRecordsController2.obtainMessage(1, phoneId, -1));
                    }
                }
            }
        };
        for (int i2 = 0; i2 < mPhoneCount; i2++) {
            this.mUniIccRecords[i2] = new UniIccRecords(context, i2);
            this.mSimState[i2] = "";
        }
        IntentFilter filter = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
        context.registerReceiver(this.mReceiver, filter);
    }

    public static UniIccRecordsController init(Context context) {
        UniIccRecordsController uniIccRecordsController;
        synchronized (UniIccRecordsController.class) {
            if (mInstance == null) {
                mInstance = new UniIccRecordsController(context);
            } else {
                Log.i(LOG_TAG, "init() called multiple times!  mInstance = " + mInstance);
            }
            uniIccRecordsController = mInstance;
        }
        return uniIccRecordsController;
    }

    public static UniIccRecordsController getInstance() {
        if (mInstance == null) {
            Log.i(LOG_TAG, "getInstance null");
        }
        return mInstance;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                this.mUniIccRecords[msg.arg1].fetchUniIccRecords();
                this.mUniIccRecords[msg.arg1].registerForCellLocationChanged();
                break;
            case 1:
                this.mUniIccRecords[msg.arg1].resetRecords();
                break;
        }
    }

    public UniIccRecords getUniIccRecordForPhone(int phoneId) {
        return this.mUniIccRecords[phoneId];
    }
}