package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class PoliceStationInfo extends BaseBean implements Parcelable{

    private String key;
    private String value;
    private List<PoliceStationInfo> children;

    protected PoliceStationInfo(Parcel in) {
        key = in.readString();
        value = in.readString();
        children = in.createTypedArrayList(PoliceStationInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PoliceStationInfo> CREATOR = new Creator<PoliceStationInfo>() {
        @Override
        public PoliceStationInfo createFromParcel(Parcel in) {
            return new PoliceStationInfo(in);
        }

        @Override
        public PoliceStationInfo[] newArray(int size) {
            return new PoliceStationInfo[size];
        }
    };

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setChildren(List<PoliceStationInfo> children) {
        this.children = children;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public List<PoliceStationInfo> getChildren() {
        return children;
    }
}
