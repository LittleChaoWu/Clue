package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 子任务执行时的后台轨迹数据结构
 */
public class SubLocationInfo extends BaseBean implements Parcelable {

    private int id;
    private int subTaskId;
    private double lat;
    private double lon;
    private long recordTime;
    private long createdTime;
    private String address;

    public SubLocationInfo(double lat, double lon, String address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    protected SubLocationInfo(Parcel in) {
        id = in.readInt();
        subTaskId = in.readInt();
        lon = in.readDouble();
        lat = in.readDouble();
        recordTime = in.readLong();
        createdTime = in.readLong();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(subTaskId);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeLong(recordTime);
        dest.writeLong(createdTime);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubLocationInfo> CREATOR = new Creator<SubLocationInfo>() {
        @Override
        public SubLocationInfo createFromParcel(Parcel in) {
            return new SubLocationInfo(in);
        }

        @Override
        public SubLocationInfo[] newArray(int size) {
            return new SubLocationInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getAddress() {
        return address;
    }
}
