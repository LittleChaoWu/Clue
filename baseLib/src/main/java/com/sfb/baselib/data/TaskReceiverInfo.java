package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class TaskReceiverInfo extends BaseBean implements Parcelable {

    private int id;
    private int taskId;
    private int orgId;
    private int userId;
    private String userRealName;
    private String orgName;
    private String telephone;
    private long createdTime;
    private boolean isUser;
    private boolean isOrg;
    private boolean isAll;

    public TaskReceiverInfo() {
    }

    protected TaskReceiverInfo(Parcel in) {
        id = in.readInt();
        taskId = in.readInt();
        orgId = in.readInt();
        userId = in.readInt();
        userRealName = in.readString();
        orgName = in.readString();
        telephone = in.readString();
        createdTime = in.readLong();
        isUser = in.readByte() != 0;
        isOrg = in.readByte() != 0;
        isAll = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(taskId);
        dest.writeInt(orgId);
        dest.writeInt(userId);
        dest.writeString(userRealName);
        dest.writeString(orgName);
        dest.writeString(telephone);
        dest.writeLong(createdTime);
        dest.writeByte((byte) (isUser ? 1 : 0));
        dest.writeByte((byte) (isOrg ? 1 : 0));
        dest.writeByte((byte) (isAll ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskReceiverInfo> CREATOR = new Creator<TaskReceiverInfo>() {
        @Override
        public TaskReceiverInfo createFromParcel(Parcel in) {
            return new TaskReceiverInfo(in);
        }

        @Override
        public TaskReceiverInfo[] newArray(int size) {
            return new TaskReceiverInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public void setOrg(boolean org) {
        isOrg = org;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getOrgId() {
        return orgId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getTelephone() {
        return telephone;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public boolean isUser() {
        return isUser;
    }

    public boolean isOrg() {
        return isOrg;
    }

    public boolean isAll() {
        return isAll;
    }
}
