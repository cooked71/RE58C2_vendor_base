package com.android.internal.telephony.uicc;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.UniTelephonyIntents;
import com.android.internal.util.ArrayUtils;
import com.android.unisoc.telephony.RadioInteractor;
import com.android.unisoc.telephony.RadioInteractorListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class UniIccRecords extends Handler implements UniIccConstants {
    private static final int EVENT_GET_AD_DONE = 7;
    private static final int EVENT_GET_ALL_OPLNR_LOAD_DONE = 11;
    private static final int EVENT_GET_ALL_OPL_DONE = 2;
    private static final int EVENT_GET_ALL_PNN_DONE = 3;
    private static final int EVENT_GET_CPHS_SPN_DONE = 0;
    private static final int EVENT_GET_EHPLMN_DONE = 4;
    private static final int EVENT_GET_PS_DATA_OFF_DONE = 8;
    private static final int EVENT_GET_SPDI_DONE = 5;
    private static final int EVENT_GET_SST_DONE = 1;
    private static final int EVENT_SIM_REFRESH = 6;
    private static final String LOG_TAG = "UniIccRecords";
    private static final int TAG_SPDI = 163;
    private static final int TAG_SPDI_PLMN_LIST = 128;
    private int mAppType;
    private Context mContext;
    private String[] mEhplmns;
    private UniIccFileHandler mIccFileHandler;
    private UniIccServiceTable mIccServiceTable;
    private int mLac;
    private GetCPHSSpnFsmState mOnsState;
    private int mPhoneId;
    private PhoneStateListener mPhoneStateListener;
    private RadioInteractor mRadioInteractor;
    private RadioInteractorListener mRadioInteractorListener;
    private String[] mSpdi;
    private TelephonyManager mTelephonyManager;
    private ArrayList<UniOplRecord> mOplRecords = null;
    private ArrayList<UniPnnRecord> mPnnRecords = null;
    private String mOns = null;
    private RegistrantList mONSRecordsLoadedRegistrants = new RegistrantList();
    private RegistrantList mEccRecordsLoadedRegistrants = new RegistrantList();
    private int mONSRecordsToLoad = 0;
    private int mEccRecordsToLoad = 0;
    private String mEfAd = null;
    private int mPsDataOffStateValue = -1;
    private int mHomeExceptService = 0;
    private int mRomingExceptService = 0;
    private ArrayList<UniOplNrRecord> mOplNrRecords = null;
    private boolean mSimOplNrPnnEnabled = false;

    private enum GetCPHSSpnFsmState {
        IDLE,
        INIT,
        READ_SPN_CPHS,
        READ_SPN_SHORT_CPHS
    }

    public UniIccRecords(Context c, int phoneId) {
        this.mPhoneId = -1;
        log("Create UniIccRecords");
        this.mContext = c;
        this.mPhoneId = phoneId;
        this.mRadioInteractorListener = getRadioInteractorListener(phoneId);
        this.mTelephonyManager = TelephonyManager.from(this.mContext);
    }

    public void registerForCellLocationChanged() {
        int subId = Integer.MAX_VALUE;
        int[] subIds = SubscriptionManager.getSubId(this.mPhoneId);
        if (subIds != null && subIds.length > 0) {
            subId = subIds[0];
        }
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            log("LISTEN_CELL_LOCATION");
            TelephonyManager telephonyManagerCreateForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(subId);
            this.mTelephonyManager = telephonyManagerCreateForSubscriptionId;
            telephonyManagerCreateForSubscriptionId.listen(getPhoneStateListener(), 16);
        }
    }

    private PhoneStateListener getPhoneStateListener() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() { // from class: com.android.internal.telephony.uicc.UniIccRecords.1
            @Override // android.telephony.PhoneStateListener
            public void onCellLocationChanged(CellLocation location) {
                if (location != null && (location instanceof GsmCellLocation)) {
                    UniIccRecords.this.mLac = ((GsmCellLocation) location).getLac();
                }
                String mccmnc = TelephonyManager.from(UniIccRecords.this.mContext).getNetworkOperatorForPhone(UniIccRecords.this.mPhoneId);
                UniIccRecords.this.log("onCellLocationChanged: " + UniIccRecords.this.mLac + "mccmnc: ******");
                if (!TextUtils.isEmpty(mccmnc)) {
                    if (UniIccRecords.this.mRadioInteractor == null) {
                        UniIccRecords.this.mRadioInteractor = new RadioInteractor(UniIccRecords.this.mContext);
                    }
                    UniIccRecords.this.mRadioInteractor.updateOperatorName((String) null, UniIccRecords.this.mPhoneId);
                    UniIccRecords.this.mRadioInteractor.updateOperatorName(mccmnc, UniIccRecords.this.mPhoneId);
                }
            }
        };
        this.mPhoneStateListener = phoneStateListener;
        return phoneStateListener;
    }

    public void fetchUniIccRecords() {
        log("fetchUniIccRecords");
        if (this.mRadioInteractor == null) {
            this.mRadioInteractor = new RadioInteractor(this.mContext);
        }
        this.mAppType = this.mRadioInteractor.getIccAppType(this.mPhoneId);
        log("mAppType = " + this.mAppType);
        this.mRadioInteractor.listen(this.mRadioInteractorListener, 4194304, false);
        if (this.mIccFileHandler == null) {
            this.mIccFileHandler = new UniIccFileHandler(this.mAppType, this.mContext, this.mPhoneId);
        }
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null && telephonyManager.getSimState(this.mPhoneId) == 5) {
            loadOperatorNameFiles();
            loadEfAdFiles();
        }
    }

    private void loadOperatorNameFiles() {
        log("loadOperatorNameFiles");
        this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_SST, obtainMessage(1));
        this.mONSRecordsToLoad++;
        this.mIccFileHandler.loadEFLinearFixedAll(UniIccConstants.EF_OPL, obtainMessage(2));
        this.mONSRecordsToLoad++;
        this.mIccFileHandler.loadEFLinearFixedAll(UniIccConstants.EF_PNN, obtainMessage(3));
        this.mONSRecordsToLoad++;
        this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_EHPLMN, obtainMessage(4));
        this.mONSRecordsToLoad++;
        this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_SPDI, obtainMessage(5));
        this.mONSRecordsToLoad++;
        getCPHSSpnFsm(true, null);
    }

    private void loadEfAdFiles() {
        this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_AD, obtainMessage(7));
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        boolean isOnsRecordLoadResponse = false;
        log("handleMessage " + msg);
        try {
            try {
                switch (msg.what) {
                    case 0:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar = (AsyncResult) msg.obj;
                        Log.d(LOG_TAG, "Load ons false");
                        getCPHSSpnFsm(false, ar);
                        break;
                    case 1:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar2 = (AsyncResult) msg.obj;
                        byte[] data = (byte[]) ar2.result;
                        if (ar2.exception == null) {
                            log("EF_SST data = " + IccUtils.bytesToHexString(data));
                            this.mIccServiceTable = new UniIccServiceTable(data, this.mAppType);
                            updatePlmn();
                            handleSstData(data);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar3 = (AsyncResult) msg.obj;
                        if (ar3.exception == null) {
                            handleGetOplResponse(ar3);
                            updatePlmn();
                            break;
                        } else {
                            loge("Exception in fetching OPL Records " + ar3.exception);
                            if (1 != 0) {
                                onONSRecordLoaded();
                                return;
                            } else {
                                if (0 != 0) {
                                    onEccRecordLoaded();
                                    return;
                                }
                                return;
                            }
                        }
                    case 3:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar4 = (AsyncResult) msg.obj;
                        handleGetPnnResponse(ar4);
                        updatePlmn();
                        break;
                    case 4:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar5 = (AsyncResult) msg.obj;
                        byte[] data2 = (byte[]) ar5.result;
                        if (ar5.exception == null && data2 != null) {
                            this.mEhplmns = parseBcdPlmnList(data2, "Equivalent Home");
                            updatePlmn();
                            break;
                        } else {
                            loge("Failed getting Equivalent Home PLMNs: " + ar5.exception);
                            break;
                        }
                    case 5:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar6 = (AsyncResult) msg.obj;
                        byte[] data3 = (byte[]) ar6.result;
                        if (ar6.exception == null) {
                            parseEfSpdi(data3);
                            updatePlmn();
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        AsyncResult ar7 = (AsyncResult) msg.obj;
                        if (ar7 != null) {
                            byte[] data4 = (byte[]) ar7.result;
                            if (ar7.exception != null) {
                                log("EVENT_GET_AD_DONE fail");
                                break;
                            } else {
                                this.mEfAd = IccUtils.bytesToHexString(data4);
                                log("EF_AD: " + this.mEfAd);
                                break;
                            }
                        } else {
                            log("EVENT_GET_AD_DONE ar is null");
                            break;
                        }
                    case 8:
                        AsyncResult ar8 = (AsyncResult) msg.obj;
                        byte[] data5 = (byte[]) ar8.result;
                        if (ar8.exception == null) {
                            handleGetPsDataOffResponse(data5);
                            break;
                        } else {
                            loge("Failed getting ps data off: " + ar8.exception);
                            if (0 != 0) {
                                onONSRecordLoaded();
                                return;
                            } else {
                                if (0 != 0) {
                                    onEccRecordLoaded();
                                    return;
                                }
                                return;
                            }
                        }
                    case 11:
                        isOnsRecordLoadResponse = true;
                        AsyncResult ar9 = (AsyncResult) msg.obj;
                        if (ar9.exception == null) {
                            handleGetOplNrResponse(ar9);
                            updatePlmn();
                            break;
                        } else {
                            loge("Exception in fetching OPLNR Records " + ar9.exception);
                            if (1 != 0) {
                                onONSRecordLoaded();
                                return;
                            } else {
                                if (0 != 0) {
                                    onEccRecordLoaded();
                                    return;
                                }
                                return;
                            }
                        }
                }
            } catch (RuntimeException exc) {
                logw("Exception parsing SIM record ex", exc);
                if (0 == 0) {
                    if (0 == 0) {
                        return;
                    }
                }
            }
            if (!isOnsRecordLoadResponse) {
                if (0 == 0) {
                    return;
                }
                onEccRecordLoaded();
                return;
            }
            onONSRecordLoaded();
        } catch (Throwable th) {
            if (0 != 0) {
                onONSRecordLoaded();
            } else if (0 != 0) {
                onEccRecordLoaded();
            }
            throw th;
        }
    }

    public String[] getEhplmns() {
        return this.mEhplmns;
    }

    private String[] getServiceProviderDisplayInformation() {
        return this.mSpdi;
    }

    private void parseEfSpdi(byte[] data) {
        UniSimTlv tlv = new UniSimTlv(data, 0, data.length);
        byte[] plmnEntries = null;
        while (true) {
            if (!tlv.isValidObject()) {
                break;
            }
            if (tlv.getTag() == TAG_SPDI) {
                tlv = new UniSimTlv(tlv.getData(), 0, tlv.getData().length);
            }
            if (tlv.getTag() != 128) {
                tlv.nextObject();
            } else {
                plmnEntries = tlv.getData();
                break;
            }
        }
        if (plmnEntries == null) {
            return;
        }
        List<String> tmpSpdi = new ArrayList<>(plmnEntries.length / 3);
        for (int i = 0; i + 2 < plmnEntries.length; i += 3) {
            String plmnCode = IccUtils.bcdPlmnToString(plmnEntries, i);
            if (!TextUtils.isEmpty(plmnCode)) {
                log("EF_SPDI PLMN: " + plmnCode);
                tmpSpdi.add(plmnCode);
            }
        }
        int i2 = tmpSpdi.size();
        this.mSpdi = (String[]) tmpSpdi.toArray(new String[i2]);
    }

    public String[] getHomePlmns() {
        String hplmn = this.mTelephonyManager.getSimOperatorNumeric();
        String[] hplmns = getEhplmns();
        String[] spdi = getServiceProviderDisplayInformation();
        if (ArrayUtils.isEmpty(hplmns)) {
            hplmns = new String[]{hplmn};
        }
        if (!ArrayUtils.isEmpty(spdi)) {
            hplmns = (String[]) ArrayUtils.concatElements(String.class, new String[][]{hplmns, spdi});
        }
        String[] hplmns2 = (String[]) ArrayUtils.appendElement(String.class, hplmns, hplmn);
        log("getHomePlmns: ******");
        return hplmns2;
    }

    RadioInteractorListener getRadioInteractorListener(final int phoneId) {
        return new RadioInteractorListener(phoneId) { // from class: com.android.internal.telephony.uicc.UniIccRecords.2
            public void onSimRefreshEvent() {
                UniIccRecords.this.log("onSimRefreshEvent phoneId= " + phoneId);
                UniIccRecords.this.handleSimRefresh();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSimRefresh() {
        fetchUniIccRecords();
        Intent intent = new Intent(UniTelephonyIntents.ACTION_SIM_REFRESH_FILEUPDATE);
        intent.putExtra("phone_id", this.mPhoneId);
        intent.addFlags(16777216);
        this.mContext.sendBroadcast(intent);
    }

    private void onONSRecordLoaded() {
        log("mONSRecordsToLoad " + this.mONSRecordsToLoad);
        int i = this.mONSRecordsToLoad - 1;
        this.mONSRecordsToLoad = i;
        if (i == 0) {
            this.mONSRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult((Object) null, Integer.valueOf(this.mPhoneId), (Throwable) null));
        } else if (i < 0) {
            loge("mONSRecordsToLoad < 0, programmer error suspected");
            this.mONSRecordsToLoad = 0;
        }
    }

    private void onEccRecordLoaded() {
        log("mEccRecordsToLoad " + this.mEccRecordsToLoad);
        int i = this.mEccRecordsToLoad - 1;
        this.mEccRecordsToLoad = i;
        if (i == 0) {
            this.mEccRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult((Object) null, Integer.valueOf(this.mPhoneId), (Throwable) null));
        } else if (i < 0) {
            loge("mEccRecordsToLoad < 0, programmer error suspected");
            this.mEccRecordsToLoad = 0;
        }
    }

    private void handleGetOplResponse(AsyncResult ar) {
        ArrayList<byte[]> dataOpl = (ArrayList) ar.result;
        this.mOplRecords = new ArrayList<>(dataOpl.size());
        int s = dataOpl.size();
        for (int i = 0; i < s; i++) {
            UniOplRecord opl = new UniOplRecord(dataOpl.get(i));
            this.mOplRecords.add(opl);
            log("OPL" + i + ": " + opl);
        }
    }

    private void handleGetOplNrResponse(AsyncResult ar) {
        if (ar.exception != null) {
            loge("Exception in fetching OPLNR Records " + ar.exception);
            return;
        }
        ArrayList<byte[]> dataOplNr = (ArrayList) ar.result;
        this.mOplNrRecords = new ArrayList<>(dataOplNr.size());
        int s = dataOplNr.size();
        for (int i = 0; i < s; i++) {
            UniOplNrRecord oplNr = new UniOplNrRecord(dataOplNr.get(i));
            this.mOplNrRecords.add(oplNr);
            log("OPLNr" + i + ": " + oplNr);
        }
    }

    private void handleGetPnnResponse(AsyncResult ar) {
        if (ar.exception != null) {
            loge("Exception in fetching PNN Records " + ar.exception);
            return;
        }
        ArrayList<byte[]> dataPnn = (ArrayList) ar.result;
        this.mPnnRecords = new ArrayList<>(dataPnn.size());
        int s = dataPnn.size();
        for (int i = 0; i < s; i++) {
            UniPnnRecord pnn = new UniPnnRecord(dataPnn.get(i));
            this.mPnnRecords.add(pnn);
            log("PNN" + i + ": " + pnn);
        }
    }

    private void updatePlmn() {
        String mccmnc = TelephonyManager.from(this.mContext).getNetworkOperatorForPhone(this.mPhoneId);
        if (!TextUtils.isEmpty(mccmnc)) {
            if (this.mRadioInteractor == null) {
                this.mRadioInteractor = new RadioInteractor(this.mContext);
            }
            this.mRadioInteractor.updateOperatorName(mccmnc, this.mPhoneId);
        }
    }

    private void getCPHSSpnFsm(boolean start, AsyncResult ar) {
        if (this.mIccFileHandler == null) {
            this.mOnsState = GetCPHSSpnFsmState.IDLE;
        }
        if (start) {
            this.mOnsState = GetCPHSSpnFsmState.INIT;
        }
        switch (AnonymousClass3.$SwitchMap$com$android$internal$telephony$uicc$UniIccRecords$GetCPHSSpnFsmState[this.mOnsState.ordinal()]) {
            case 1:
                this.mOns = null;
                this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_SPN_CPHS, obtainMessage(0));
                this.mONSRecordsToLoad++;
                this.mOnsState = GetCPHSSpnFsmState.READ_SPN_CPHS;
                break;
            case 2:
                if (ar == null || ar.exception != null) {
                    this.mOnsState = GetCPHSSpnFsmState.READ_SPN_SHORT_CPHS;
                } else {
                    byte[] data = (byte[]) ar.result;
                    String strAdnStringFieldToString = IccUtils.adnStringFieldToString(data, 0, data.length);
                    this.mOns = strAdnStringFieldToString;
                    if (isInvalidONS(strAdnStringFieldToString)) {
                        Log.d(LOG_TAG, "Drop invalid SPN_CPHS: " + this.mOns);
                        this.mOns = null;
                        this.mOnsState = GetCPHSSpnFsmState.READ_SPN_CPHS;
                    }
                    log("Load EF_SPN_CPHS: " + this.mOns);
                    this.mOnsState = GetCPHSSpnFsmState.IDLE;
                }
                if (this.mOnsState == GetCPHSSpnFsmState.READ_SPN_SHORT_CPHS) {
                    this.mIccFileHandler.loadEFTransparent(UniIccConstants.EF_SPN_SHORT_CPHS, obtainMessage(0));
                    this.mONSRecordsToLoad++;
                    break;
                } else {
                    updatePlmn();
                    break;
                }
            case 3:
                if (ar != null && ar.exception == null) {
                    byte[] data2 = (byte[]) ar.result;
                    String strAdnStringFieldToString2 = IccUtils.adnStringFieldToString(data2, 0, data2.length);
                    this.mOns = strAdnStringFieldToString2;
                    if (isInvalidONS(strAdnStringFieldToString2)) {
                        Log.d(LOG_TAG, "Drop invalid SPN_SHORT_CPHS: " + this.mOns);
                        this.mOns = null;
                    } else {
                        updatePlmn();
                    }
                    log("Load EF_SPN_SHORT_CPHS: " + this.mOns);
                } else {
                    log("Load EF_SPN_SHORT_CPHS Failure");
                }
                this.mOnsState = GetCPHSSpnFsmState.IDLE;
                break;
            default:
                this.mOnsState = GetCPHSSpnFsmState.IDLE;
                break;
        }
    }

    /* renamed from: com.android.internal.telephony.uicc.UniIccRecords$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$uicc$UniIccRecords$GetCPHSSpnFsmState;

        static {
            int[] iArr = new int[GetCPHSSpnFsmState.values().length];
            $SwitchMap$com$android$internal$telephony$uicc$UniIccRecords$GetCPHSSpnFsmState = iArr;
            try {
                iArr[GetCPHSSpnFsmState.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$uicc$UniIccRecords$GetCPHSSpnFsmState[GetCPHSSpnFsmState.READ_SPN_CPHS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$uicc$UniIccRecords$GetCPHSSpnFsmState[GetCPHSSpnFsmState.READ_SPN_SHORT_CPHS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private String[] parseBcdPlmnList(byte[] data, String description) {
        log("Received " + description + " PLMNs, raw=" + IccUtils.bytesToHexString(data));
        if (data.length == 0 || data.length % 3 != 0) {
            loge("Received invalid " + description + " PLMN list");
            return null;
        }
        int numPlmns = data.length / 3;
        int numValidPlmns = 0;
        String[] parsed = new String[numPlmns];
        for (int i = 0; i < numPlmns; i++) {
            parsed[numValidPlmns] = IccUtils.bcdPlmnToString(data, i * 3);
            if (!TextUtils.isEmpty(parsed[numValidPlmns])) {
                numValidPlmns++;
            }
        }
        String[] ret = (String[]) Arrays.copyOf(parsed, numValidPlmns);
        log(description + " PLMNs: " + Arrays.toString(ret));
        return ret;
    }

    private void handleSstData(byte[] bArr) {
        UniIccFileHandler uniIccFileHandler;
        if (bArr != null && this.mAppType == 2) {
            UniIccServiceTable uniIccServiceTable = this.mIccServiceTable;
            if (uniIccServiceTable != null && uniIccServiceTable.oplNrAvaliable()) {
                this.mSimOplNrPnnEnabled = true;
            }
            if (this.mSimOplNrPnnEnabled && (uniIccFileHandler = this.mIccFileHandler) != null) {
                uniIccFileHandler.loadEFLinearFixedAll(UniIccConstants.EF_OPLNR, obtainMessage(11));
                this.mONSRecordsToLoad++;
                log("read from OPLNR");
            }
            UniIccServiceTable uniIccServiceTable2 = this.mIccServiceTable;
            if (uniIccServiceTable2 != null && uniIccServiceTable2.psDataOffAvaliable()) {
                this.mPsDataOffStateValue = this.mIccServiceTable.psDataOffInfoAvaliable() ? 1 : 0;
                UniIccFileHandler uniIccFileHandler2 = this.mIccFileHandler;
                if (uniIccFileHandler2 != null) {
                    uniIccFileHandler2.loadEFTransparent(UniIccConstants.EF_PS_DATA_OFF, obtainMessage(8));
                }
            }
        }
    }

    private void handleGetPsDataOffResponse(byte[] data) {
        log("handleGetPsDataOffResponse ");
        if (data == null) {
            return;
        }
        int i = this.mPsDataOffStateValue;
        if (i == 1) {
            if (data.length > 2) {
                this.mHomeExceptService = (data[1] >> 5) & 255;
                this.mRomingExceptService = (data[2] >> 5) & 255;
                log(" mHomeExceptService: " + this.mHomeExceptService + " mRomingExceptService: " + this.mRomingExceptService);
                return;
            }
            return;
        }
        if (i == 0 && data.length > 1) {
            int i2 = (data[1] >> 5) & 255;
            this.mHomeExceptService = i2;
            this.mRomingExceptService = i2;
            log(" mHomeExceptService: " + this.mHomeExceptService + " mRomingExceptService: " + this.mRomingExceptService);
        }
    }

    public int getHomeExceptService() {
        return this.mHomeExceptService;
    }

    public int getRomingExceptService() {
        return this.mRomingExceptService;
    }

    public UniIccServiceTable getIccmServiceTable() {
        return this.mIccServiceTable;
    }

    public ArrayList<UniOplRecord> getOplRecords() {
        return this.mOplRecords;
    }

    public ArrayList<UniOplNrRecord> getOplNrRecords() {
        return this.mOplNrRecords;
    }

    public ArrayList<UniPnnRecord> getPnnRecords() {
        return this.mPnnRecords;
    }

    public String getSimOns() {
        return this.mOns;
    }

    public int getLac() {
        return this.mLac;
    }

    public String getEfAd() {
        return this.mEfAd;
    }

    void resetRecords() {
        log("reset records");
        this.mOplRecords = null;
        this.mPnnRecords = null;
        this.mOns = null;
        this.mAppType = 0;
        this.mEhplmns = null;
        this.mSpdi = null;
        this.mIccFileHandler = null;
        log("listen none and set radiointeractor to null");
        this.mTelephonyManager.listen(getPhoneStateListener(), 0);
        RadioInteractor radioInteractor = this.mRadioInteractor;
        if (radioInteractor != null) {
            radioInteractor.listen(this.mRadioInteractorListener, 0, false);
            this.mRadioInteractor = null;
        }
        this.mOplNrRecords = null;
    }

    private boolean isInvalidONS(String ons) {
        return "@".equals(ons);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String s) {
        Log.d(LOG_TAG, "[UniIccRecords" + this.mPhoneId + "] " + s);
    }

    private void loge(String s) {
        Log.e(LOG_TAG, "[UniIccRecords" + this.mPhoneId + "] " + s);
    }

    private void logw(String s, Throwable tr) {
        Log.w(LOG_TAG, "[UniIccRecords" + this.mPhoneId + "] " + s, tr);
    }
}