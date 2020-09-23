package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class ClueFeedbackInfo extends BaseBean implements Parcelable {

    private int clueId;
    private String remark;
    private String filePaths;
    private String fileIds;

    public ClueFeedbackInfo() {
    }

    protected ClueFeedbackInfo(Parcel in) {
        clueId = in.readInt();
        remark = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clueId);
        dest.writeString(remark);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClueFeedbackInfo> CREATOR = new Creator<ClueFeedbackInfo>() {
        @Override
        public ClueFeedbackInfo createFromParcel(Parcel in) {
            return new ClueFeedbackInfo(in);
        }

        @Override
        public ClueFeedbackInfo[] newArray(int size) {
            return new ClueFeedbackInfo[size];
        }
    };

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public int getClueId() {
        return clueId;
    }

    public String getRemark() {
        return remark;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }
}
