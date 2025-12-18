package com.android.internal.telephony.uicc;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.SparseArray;
import com.android.unisoc.telephony.RadioInteractor;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/* loaded from: classes.dex */
public class UniIccFileHandler extends Handler implements UniIccConstants {
    protected static final int COMMAND_GET_RESPONSE = 192;
    protected static final int COMMAND_READ_BINARY = 176;
    protected static final int COMMAND_READ_RECORD = 178;
    protected static final int COMMAND_SEEK = 162;
    protected static final int COMMAND_UPDATE_BINARY = 214;
    protected static final int COMMAND_UPDATE_RECORD = 220;
    private static final boolean DBG = true;
    protected static final int EF_TYPE_CYCLIC = 3;
    protected static final int EF_TYPE_LINEAR_FIXED = 1;
    protected static final int EF_TYPE_TRANSPARENT = 0;
    protected static final int EVENT_GET_BINARY_SIZE_DONE = 1;
    protected static final int EVENT_GET_RECORD_SIZE_DONE = 3;
    protected static final int EVENT_READ_BINARY_DONE = 2;
    protected static final int EVENT_READ_RECORD_DONE = 4;
    protected static final int EVENT_UPDATE_BINARY_DONE = 5;
    protected static final int GET_RESPONSE_EF_IMG_SIZE_BYTES = 10;
    protected static final int GET_RESPONSE_EF_SIZE_BYTES = 15;
    private static final String LOG_TAG = "UniIccFileHandler";
    protected static final int READ_RECORD_MODE_ABSOLUTE = 4;
    protected static final int RESPONSE_DATA_FILE_ID_1 = 4;
    protected static final int RESPONSE_DATA_FILE_ID_2 = 5;
    protected static final int RESPONSE_DATA_FILE_SIZE_1 = 2;
    protected static final int RESPONSE_DATA_FILE_SIZE_2 = 3;
    protected static final int RESPONSE_DATA_FILE_TYPE = 6;
    protected static final int RESPONSE_DATA_RECORD_LENGTH = 14;
    protected static final int RESPONSE_DATA_STRUCTURE = 13;
    protected static final int TYPE_EF = 4;
    private Context mContext;
    private int mIccType;
    private int mPhoneId;
    private RadioInteractor mRadioInteractor;
    AtomicInteger sNextSerial = new AtomicInteger(0);
    SparseArray<Object> mRequestList = new SparseArray<>();
    Messenger mMessenger = new Messenger(this);

    public UniIccFileHandler(int type, Context context, int phoneId) {
        this.mIccType = type;
        this.mPhoneId = phoneId;
        this.mRadioInteractor = new RadioInteractor(context);
    }

    public void loadEFTransparent(int fileid, Message onLoaded) {
        String efPath = getEFPath(fileid);
        IccIoContext iccIoContext = new IccIoContext(1, fileid, efPath, onLoaded);
        int serial = getSerial(iccIoContext);
        Log.d(LOG_TAG, "loadEFTransparent iccIoContext = " + iccIoContext);
        this.mRadioInteractor.iccIOForApp(this.mMessenger, COMMAND_GET_RESPONSE, fileid, efPath, 0, 0, 15, (String) null, (String) null, (String) null, this.mPhoneId, serial);
    }

    public void loadEFLinearFixedAll(int fileid, Message onLoaded) {
        String efPath = getEFPath(fileid);
        IccIoContext iccIoContext = new IccIoContext(3, fileid, efPath, onLoaded);
        int serial = getSerial(iccIoContext);
        Log.d(LOG_TAG, "loadEFLinearFixedAll iccIoContext = " + iccIoContext);
        this.mRadioInteractor.iccIOForApp(this.mMessenger, COMMAND_GET_RESPONSE, fileid, efPath, 0, 0, 15, (String) null, (String) null, (String) null, this.mPhoneId, serial);
    }

    public void updateEFTransparent(int fileid, byte[] data, Message onComplete) {
        String efPath = getEFPath(fileid);
        IccIoContext iccIoContext = new IccIoContext(5, fileid, efPath, onComplete);
        int serial = getSerial(iccIoContext);
        Log.d(LOG_TAG, "updateEFTransparent iccIoContext = " + iccIoContext);
        this.mRadioInteractor.iccIOForApp(this.mMessenger, COMMAND_UPDATE_BINARY, fileid, efPath, 0, 0, data.length, IccUtils.bytesToHexString(data), (String) null, (String) null, this.mPhoneId, serial);
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 23, insn: 0x024e: MOVE (r9 I:??[OBJECT, ARRAY]) = (r23 I:??[OBJECT, ARRAY] A[D('path' java.lang.String)]), block:B:65:0x024e */
    @Override // android.os.Handler
    public void handleMessage(android.os.Message r33) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.uicc.UniIccFileHandler.handleMessage(android.os.Message):void");
    }

    private void sendResult(Message response, Object result, Throwable ex) {
        if (response == null) {
            return;
        }
        AsyncResult.forMessage(response, result, ex);
        response.sendToTarget();
    }

    private int getSerial(Object result) {
        int serial = this.sNextSerial.getAndUpdate(new IntUnaryOperator() { // from class: com.android.internal.telephony.uicc.UniIccFileHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return UniIccFileHandler.lambda$getSerial$0(i);
            }
        });
        synchronized (this.mRequestList) {
            this.mRequestList.append(serial, result);
        }
        logd("getSerial = " + serial);
        return serial;
    }

    static /* synthetic */ int lambda$getSerial$0(int n) {
        return (n + 1) % Integer.MAX_VALUE;
    }

    private Object findAndRemoveMessageFromList(int serial) {
        Object userObj;
        synchronized (this.mRequestList) {
            userObj = this.mRequestList.get(serial);
            if (userObj != null) {
                this.mRequestList.remove(serial);
            }
        }
        return userObj;
    }

    private boolean processException(Message response, UniIccIoResult uniIccIoResult) {
        if (uniIccIoResult == null) {
            sendResult(response, null, new UniIccException("length wrong"));
            return true;
        }
        UniIccException iccException = uniIccIoResult.getException();
        if (iccException == null) {
            return false;
        }
        sendResult(response, null, iccException);
        return true;
    }

    private UniIccIoResult getUniIccIoResult(byte[] data) {
        if (data == null || data.length < 2) {
            return null;
        }
        int sw1 = data[data.length - 2] & 255;
        int sw2 = data[data.length - 1] & 255;
        byte[] payload = new byte[data.length - 2];
        System.arraycopy(data, 0, payload, 0, data.length - 2);
        UniIccIoResult result = new UniIccIoResult(sw1, sw2, payload);
        return result;
    }

    protected String getEFPath(int efid) {
        String path = null;
        switch (efid) {
            case UniIccConstants.EF_OPLNR /* 20232 */:
                return "3F007FFF5FC0";
            case UniIccConstants.EF_SPN_CPHS /* 28436 */:
            case UniIccConstants.EF_SPN_SHORT_CPHS /* 28440 */:
                return "3F007F20";
            case UniIccConstants.EF_PLMN_SEL /* 28464 */:
            case UniIccConstants.EF_SST /* 28472 */:
            case UniIccConstants.EF_PLMN_ACT /* 28512 */:
            case UniIccConstants.EF_AD /* 28589 */:
            case UniIccConstants.EF_ECC /* 28599 */:
            case UniIccConstants.EF_PNN /* 28613 */:
            case UniIccConstants.EF_OPL /* 28614 */:
            case UniIccConstants.EF_SPDI /* 28621 */:
            case UniIccConstants.EF_EHPLMN /* 28633 */:
            case UniIccConstants.EF_PS_DATA_OFF /* 28665 */:
                int i = this.mIccType;
                if (2 == i) {
                    path = "3F007FFF";
                    break;
                } else if (1 == i) {
                    path = "3F007F20";
                    break;
                }
                break;
        }
        logd(efid + " getEFPath, return " + path);
        return path;
    }

    static class IccIoContext {
        int mCountRecords;
        int mEfid;
        Message mOnLoaded;
        String mPath;
        int mRecordNum = 1;
        int mRecordSize;
        int mWhat;
        ArrayList<byte[]> results;

        IccIoContext(int what, int efid, String path, Message onLoaded) {
            this.mWhat = what;
            this.mEfid = efid;
            this.mPath = path;
            this.mOnLoaded = onLoaded;
        }

        public String toString() {
            return "IccIoContext:  mWhat" + this.mWhat + " mEfid" + this.mEfid + " mRecordNum=" + this.mRecordNum + " mOnLoaded=" + this.mOnLoaded + " mPath=" + this.mPath;
        }
    }

    private void logd(String s) {
        Log.d(LOG_TAG, "[UniIccFileHandler" + this.mPhoneId + "] " + s);
    }

    private void loge(String s) {
        Log.e(LOG_TAG, "[UniIccFileHandler" + this.mPhoneId + "] " + s);
    }
}