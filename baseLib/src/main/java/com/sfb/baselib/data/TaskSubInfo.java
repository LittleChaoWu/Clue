package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

/**
 * Created by wuwc on 2020/8/5.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 * 群防任务附属任务，PC端用任务id和用户id查询属于自己的任务信息。用于判断是否可以再领取任务/已经领取任务。
 */
public class TaskSubInfo extends BaseBean implements Parcelable {
    private int id;
    private int taskId;
    private String taskLocationIds;
    private int userId;
    private String execStatus;
    private long createdTime;
    private long updatedTime;
    private int status;
    private int mileage;
    private String execStatusName;

    protected TaskSubInfo(Parcel in) {
        id = in.readInt();
        taskId = in.readInt();
        taskLocationIds = in.readString();
        userId = in.readInt();
        execStatus = in.readString();
        createdTime = in.readLong();
        updatedTime = in.readLong();
        status = in.readInt();
        mileage = in.readInt();
        execStatusName = in.readString();
    }

    public static final Creator<TaskSubInfo> CREATOR = new Creator<TaskSubInfo>() {
        @Override
        public TaskSubInfo createFromParcel(Parcel in) {
            return new TaskSubInfo(in);
        }

        @Override
        public TaskSubInfo[] newArray(int size) {
            return new TaskSubInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskLocationIds() {
        return taskLocationIds;
    }

    public void setTaskLocationIds(String taskLocationIds) {
        this.taskLocationIds = taskLocationIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getExecStatusName() {
        return execStatusName;
    }

    public void setExecStatusName(String execStatusName) {
        this.execStatusName = execStatusName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(taskId);
        dest.writeString(taskLocationIds);
        dest.writeInt(userId);
        dest.writeString(execStatus);
        dest.writeLong(createdTime);
        dest.writeLong(updatedTime);
        dest.writeInt(status);
        dest.writeInt(mileage);
        dest.writeString(execStatusName);
    }
}
