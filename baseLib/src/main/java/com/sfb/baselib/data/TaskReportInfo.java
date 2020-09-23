package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class TaskReportInfo extends BaseBean implements Parcelable{

    private int subTaskId;
    private double lat;
    private double lon;
    private String address;
    private TaskReportContentInfo achievement;
    private String filepaths;
    //zhangy addd 20160710 for patrol distance
    private long mileage;
    //true--自动结束任务时，直接提交汇报 false---任务结束时间到时未提交或主任务终止任务时，间接提交
    private boolean directSubmit = true;

    public TaskReportInfo() {
    }

    protected TaskReportInfo(Parcel in) {
        subTaskId = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        address = in.readString();
        filepaths = in.readString();
        mileage = in.readLong();
        directSubmit = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subTaskId);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(address);
        dest.writeString(filepaths);
        dest.writeLong(mileage);
        dest.writeByte((byte) (directSubmit ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskReportInfo> CREATOR = new Creator<TaskReportInfo>() {
        @Override
        public TaskReportInfo createFromParcel(Parcel in) {
            return new TaskReportInfo(in);
        }

        @Override
        public TaskReportInfo[] newArray(int size) {
            return new TaskReportInfo[size];
        }
    };

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
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

    public void setAchievement(TaskReportContentInfo achievement) {
        this.achievement = achievement;
    }

    public void setFilepaths(String filepaths) {
        this.filepaths = filepaths;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void setDirectSubmit(boolean directSubmit) {
        this.directSubmit = directSubmit;
    }

    public int getSubTaskId() {
        return subTaskId;
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

    public TaskReportContentInfo getAchievement() {
        return achievement;
    }

    public String getFilepaths() {
        return filepaths;
    }

    public long getMileage() {
        return mileage;
    }

    public boolean isDirectSubmit() {
        return directSubmit;
    }
}
