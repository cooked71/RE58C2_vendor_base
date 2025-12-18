package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.telephony.Rlog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class RoamingBroker {
    private static final String EMPTY_STRING = "";
    private static final String LOG_TAG = "RoamingBroker";
    private static final String ROAMINGBROKER_LSIT_PATH = "etc/roamingbroker_file.xml";
    private static volatile RoamingBroker mInstance = null;
    private String[][] mIccIds;
    private String[][] mMccMncs;
    private int mPhoneCount;
    private TelephonyManager mTelephonyManager;
    private HashMap<String, String> mRoamingBrokerMap = new HashMap<>();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.telephony.uicc.RoamingBroker.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra("ss");
            int phoneId = intent.getIntExtra("phone", -1);
            RoamingBroker.this.log("PhoneId= " + phoneId + ", state equals " + state);
            if (SubscriptionManager.isValidPhoneId(phoneId) && "ABSENT".equals(state)) {
                RoamingBroker.this.resetValuesofPhoneId(phoneId);
            }
        }
    };

    private RoamingBroker(Context context) throws XmlPullParserException {
        this.mPhoneCount = 0;
        IntentFilter intentFilter = new IntentFilter();
        context.registerReceiver(this.mReceiver, intentFilter);
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        TelephonyManager telephonyManagerFrom = TelephonyManager.from(context);
        this.mTelephonyManager = telephonyManagerFrom;
        this.mPhoneCount = telephonyManagerFrom.getSupportedModemCount();
        resetAllValues();
        loadRoamingBrokerList();
    }

    public static RoamingBroker initRoamingBroker(Context context) {
        synchronized (RoamingBroker.class) {
            if (mInstance == null) {
                mInstance = new RoamingBroker(context);
            } else {
                Log.wtf(LOG_TAG, "initRoamingBroker() called multiple times!  mInstance = " + mInstance);
            }
        }
        return mInstance;
    }

    public static RoamingBroker getInstance() {
        return mInstance;
    }

    public void updateIccIds(int phoneId, String iccId) {
        if (TextUtils.isEmpty(iccId)) {
            return;
        }
        if (TextUtils.isEmpty(this.mIccIds[phoneId][0])) {
            this.mIccIds[phoneId][0] = iccId;
        } else {
            this.mIccIds[phoneId][1] = iccId;
        }
    }

    public void updateMccMncs(int phoneId, String mccmnc) {
        if (TextUtils.isEmpty(mccmnc)) {
            return;
        }
        if (TextUtils.isEmpty(this.mMccMncs[phoneId][0])) {
            this.mMccMncs[phoneId][0] = mccmnc;
        } else {
            this.mMccMncs[phoneId][1] = mccmnc;
        }
    }

    public void setMccMncs(int phoneId) {
        String[] strArr = this.mMccMncs[phoneId];
        strArr[0] = strArr[1];
    }

    public boolean isReadytoSetMccMnc(String[][] mIccIds, String[][] mMccMncs, int phoneId, int subId) {
        if (mIccIds == null || mMccMncs == null || TextUtils.isEmpty(mIccIds[phoneId][0]) || TextUtils.isEmpty(mIccIds[phoneId][1]) || TextUtils.isEmpty(mMccMncs[phoneId][0]) || TextUtils.isEmpty(mMccMncs[phoneId][1])) {
            return false;
        }
        log("[isReadytoSetMccMnc] subId = " + subId);
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            return false;
        }
        log("[isReadytoSetMccMnc] MccMncMapping is " + isMccMncMapping(this.mRoamingBrokerMap, mMccMncs[phoneId][0], mMccMncs[phoneId][1]));
        HashMap map = this.mRoamingBrokerMap;
        if (map != null && !isMccMncMapping(map, mMccMncs[phoneId][0], mMccMncs[phoneId][1])) {
            return false;
        }
        ServiceState ss = this.mTelephonyManager.getServiceStateForSubscriber(subId);
        if (ss == null) {
            return false;
        }
        log("[isReadytoSetMccMnc] mIccIds[0] = " + mIccIds[phoneId][0] + ", mIccIds[1] = " + mIccIds[phoneId][1] + ", mMccMncs[0] = " + mMccMncs[phoneId][0] + ", mMccMncs[1] = " + mMccMncs[phoneId][1]);
        return mIccIds[phoneId][1].equals(mIccIds[phoneId][0]) && !mMccMncs[phoneId][1].equals(mMccMncs[phoneId][0]);
    }

    public boolean getStateforBroker(int phoneId) {
        int[] subIds;
        if (!SubscriptionManager.isValidPhoneId(phoneId) || (subIds = SubscriptionManager.getSubId(phoneId)) == null || subIds.length <= 0) {
            return false;
        }
        int subId = subIds[subIds.length - 1];
        log("[getStateforBroker]subId = " + subId);
        return isReadytoSetMccMnc(this.mIccIds, this.mMccMncs, phoneId, subId);
    }

    public String getOriginalValue(int phoneId) {
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            return EMPTY_STRING;
        }
        return this.mMccMncs[phoneId][0];
    }

    public HashMap<String, String> getRoamingBrokerMap() {
        return this.mRoamingBrokerMap;
    }

    private boolean isMccMncMapping(HashMap map, String oriValue, String newValue) {
        log("oriValue is " + oriValue + ", newValue is " + newValue);
        if (map == null || TextUtils.isEmpty(oriValue) || TextUtils.isEmpty(newValue)) {
            return false;
        }
        HashMap<String, String> newMap = new HashMap<>();
        newMap.put(oriValue, newValue);
        Set entries = map.entrySet();
        Map.Entry<String, String> newEntry = newMap.entrySet().iterator().next();
        if (entries != null) {
            for (Map.Entry<String, String> entry : entries) {
                if (newEntry != null && newEntry.equals(entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidSimCount(int simcount) {
        return simcount > 0 && simcount <= Integer.MAX_VALUE;
    }

    private void resetAllValues() {
        if (!isValidSimCount(this.mPhoneCount)) {
            this.mIccIds = (String[][]) Array.newInstance((Class<?>) String.class, 2, 2);
            this.mMccMncs = (String[][]) Array.newInstance((Class<?>) String.class, 2, 2);
        } else {
            this.mIccIds = (String[][]) Array.newInstance((Class<?>) String.class, this.mPhoneCount, 2);
            this.mMccMncs = (String[][]) Array.newInstance((Class<?>) String.class, this.mPhoneCount, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetValuesofPhoneId(int phoneId) {
        this.mIccIds[phoneId] = new String[2];
        this.mMccMncs[phoneId] = new String[2];
    }

    private void loadRoamingBrokerList() throws XmlPullParserException {
        File roamingBrokerListFile = new File(Environment.getProductDirectory(), ROAMINGBROKER_LSIT_PATH);
        try {
            FileReader roamingBrokerListReader = new FileReader(roamingBrokerListFile);
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(roamingBrokerListReader);
                XmlUtils.beginDocument(parser, "roamingBrokerList");
                while (true) {
                    XmlUtils.nextElement(parser);
                    String name = parser.getName();
                    if ("allowRoamingBroker".equals(name)) {
                        String originalMccMnc = parser.getAttributeValue(null, "original_mccmnc");
                        String roamingMccMnc = parser.getAttributeValue(null, "roaming_mccmnc");
                        this.mRoamingBrokerMap.put(originalMccMnc, roamingMccMnc);
                    } else {
                        log("mRoamingBrokerMap= " + this.mRoamingBrokerMap);
                        roamingBrokerListReader.close();
                        return;
                    }
                }
            } catch (IOException e) {
                log("Exception in spn-conf parser " + e);
            } catch (XmlPullParserException e2) {
                log("Exception in spn-conf parser " + e2);
            }
        } catch (FileNotFoundException e3) {
            log("Can not open " + roamingBrokerListFile.getAbsolutePath());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void log(String s) {
        Rlog.d(LOG_TAG, s);
    }
}