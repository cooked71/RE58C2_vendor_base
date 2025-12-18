package com.android.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.unisoc.telephony.RadioInteractor;

/* loaded from: classes.dex */
public class UniMmsUtils {
    private static final String ACTION_REPORT_ERRLOG = "com.sprd.intent.action.COMMLOG_REPORTED";
    public static final int DONOTHING_IN_THAILAND = 10003;
    public static final int IS_TRUE_SIM_IN_THAILAND = 10000;
    private static final int MMS_EXCEPTION_EVENT = 2;
    private static final String MODEMLOG_IT_PACKAGE = "com.unisoc.telephony.modemlogtests";
    private static final String MODEMLOG_PACKAGE = "com.sprd.commlog";
    public static final int NOT_IN_THAILAND = 10002;
    public static final int NOT_TRUE_SIM_IN_THAILAND = 10001;
    private static final String TAG = "UniMmsUtils";
    private static final UniMmsUtils sInstance = new UniMmsUtils();

    private UniMmsUtils() {
    }

    public static UniMmsUtils getInstance() {
        return sInstance;
    }

    public boolean carrierTureError(Context context, int subId) {
        if (!checktheOperatorIsThailand()) {
            return false;
        }
        int phoneId = SubscriptionManager.getPhoneId(subId);
        switch (judgeTrueSIM2(context, phoneId)) {
            case NOT_TRUE_SIM_IN_THAILAND /* 10001 */:
            case NOT_IN_THAILAND /* 10002 */:
                Log.d(TAG, "the number does not support sending mms");
                break;
        }
        return false;
    }

    private int judgeTrueSIM2(Context context, int phoneId) {
        if (phoneId == 0) {
            return DONOTHING_IN_THAILAND;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String mccMnc = telephonyManager.getSimOperatorNumericForPhone(1);
        if (mccMnc == null || "".equals(mccMnc)) {
            return DONOTHING_IN_THAILAND;
        }
        String mcc = mccMnc.substring(0, 3);
        String mnc = mccMnc.substring(3);
        Log.d(TAG, "PhoneId: " + phoneId + "; Mcc: " + mcc + "; Mnc: " + mnc);
        if (!"520".equals(mcc)) {
            return NOT_IN_THAILAND;
        }
        if ("00".equals(mnc) || "04".equals(mnc) || "99".equals(mnc)) {
            return IS_TRUE_SIM_IN_THAILAND;
        }
        return NOT_TRUE_SIM_IN_THAILAND;
    }

    private boolean checktheOperatorIsThailand() {
        String operator = SystemProperties.get("ro.Thailand.operator");
        if (TextUtils.isEmpty(operator)) {
            operator = SystemProperties.get("ro.operator");
        }
        if (operator != null && operator.toLowerCase().equals("thailand_true")) {
            return true;
        }
        return false;
    }

    public void sendOrDownloadMmsErrReport(Context context, int errorCode, int httpCode, int subId, int sceneId) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        String cpInfo = new RadioInteractor(context).getExceptionEvents(2, sceneId, 0, 0, phoneId);
        Log.d(TAG, "EventId: 2, SceneId: " + sceneId + ", cpInfo is not null:" + (cpInfo != null) + ", try send error report");
        String[] pkgs = {MODEMLOG_PACKAGE, MODEMLOG_IT_PACKAGE};
        for (String pkg : pkgs) {
            Log.d(TAG, "sendOrDownloadMmsErrReport pkg: " + pkg);
            Intent intent = new Intent(ACTION_REPORT_ERRLOG);
            intent.setPackage(pkg);
            intent.putExtra("FaultId", 2);
            intent.putExtra("SceneId", sceneId);
            intent.putExtra("ErrorCode", errorCode);
            intent.putExtra("HttpCode", httpCode);
            intent.putExtra("SimIndex", phoneId);
            intent.putExtra("CpInfo", cpInfo);
            context.sendBroadcast(intent);
        }
    }

    public boolean MmsMetered(Context context, int subId) throws Resources.NotFoundException {
        boolean mmsAllowed = SubscriptionManager.getResourcesForSubId(context, subId).getBoolean(134414377);
        Log.d(TAG, "mmsAllowed: " + mmsAllowed);
        if (mmsAllowed) {
            return false;
        }
        int defaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        TelephonyManager tm = telephonyManager.createForSubscriptionId(defaultDataSubId);
        if (!tm.isDataEnabled()) {
            Log.d(TAG, "Subscription with id: " + subId + " cannot send or download MMS, MMS is Metered.");
            return true;
        }
        if (!tm.isNetworkRoaming() || tm.isDataRoamingEnabled()) {
            return false;
        }
        Log.d(TAG, "Subscription with id: " + subId + " cannot send or download MMS, MMS is Metered when roaming.");
        return true;
    }
}