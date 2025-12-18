package com.android.ims.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ImsCallForwardInfoEx implements Parcelable {
    public static final Parcelable.Creator<ImsCallForwardInfoEx> CREATOR = new Parcelable.Creator<ImsCallForwardInfoEx>() { // from class: com.android.ims.internal.ImsCallForwardInfoEx.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallForwardInfoEx createFromParcel(Parcel in) {
            return new ImsCallForwardInfoEx(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallForwardInfoEx[] newArray(int size) {
            return new ImsCallForwardInfoEx[size];
        }
    };
    public int mCondition;
    public String mNumber;
    public int mNumberType;
    public String mRuleset;
    public int mServiceClass;
    public int mStatus;
    public int mTimeSeconds;
    public int mToA;

    public ImsCallForwardInfoEx() {
    }

    public ImsCallForwardInfoEx(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mCondition);
        out.writeInt(this.mStatus);
        out.writeInt(this.mToA);
        out.writeString(this.mNumber);
        out.writeInt(this.mTimeSeconds);
        out.writeString(this.mRuleset);
        out.writeInt(this.mNumberType);
        out.writeInt(this.mServiceClass);
    }

    public String toString() {
        return super.toString() + ", Condition: " + this.mCondition + ", Status: " + (this.mStatus == 0 ? "disabled" : "enabled") + ", ToA: " + this.mToA + ", Number=" + this.mNumber + ", Time (seconds): " + this.mTimeSeconds + ", mRuleset:" + this.mRuleset + ", mNumberType:" + this.mNumberType + ", mServiceClass:" + this.mServiceClass;
    }

    private void readFromParcel(Parcel in) {
        this.mCondition = in.readInt();
        this.mStatus = in.readInt();
        this.mToA = in.readInt();
        this.mNumber = in.readString();
        this.mTimeSeconds = in.readInt();
        this.mRuleset = in.readString();
        this.mNumberType = in.readInt();
        this.mServiceClass = in.readInt();
    }
}