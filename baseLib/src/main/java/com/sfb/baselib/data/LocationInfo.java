package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 主任务执行时的后台轨迹数据结构
 */
public class LocationInfo extends BaseBean implements Parcelable{

    private int id;
    private double lat;
    private double lon;
    private String address;
    private int taskId;
    private long createdTime;
    private boolean path;
    private boolean taskLocation;
    private String options;

    protected LocationInfo(Parcel in) {
        id = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        address = in.readString();
        taskId = in.readInt();
        createdTime = in.readLong();
        path = in.readByte() != 0;
        taskLocation = in.readByte() != 0;
        options = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(address);
        dest.writeInt(taskId);
        dest.writeLong(createdTime);
        dest.writeByte((byte) (path ? 1 : 0));
        dest.writeByte((byte) (taskLocation ? 1 : 0));
        dest.writeString(options);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel in) {
            return new LocationInfo(in);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public void setTaskLocation(boolean taskLocation) {
        this.taskLocation = taskLocation;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }

    public int getTaskId() {
        return taskId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public boolean isPath() {
        return path;
    }

    public boolean isTaskLocation() {
        return taskLocation;
    }

    public String getOptions() {
        return options;
    }
}
