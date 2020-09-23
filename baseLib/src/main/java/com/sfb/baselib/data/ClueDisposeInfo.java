package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class ClueDisposeInfo extends BaseBean implements Parcelable {

    private int clueId;
    private String result;
    private String clueBroadType;
    private String clueType;
    private String clueChildType;
    private String remark;
    private String filePaths;
    private String fileIds;

    public ClueDisposeInfo() {
    }

    protected ClueDisposeInfo(Parcel in) {
        clueId = in.readInt();
        result = in.readString();
        clueBroadType = in.readString();
        clueType = in.readString();
        clueChildType = in.readString();
        remark = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clueId);
        dest.writeString(result);
        dest.writeString(clueBroadType);
        dest.writeString(clueType);
        dest.writeString(clueChildType);
        dest.writeString(remark);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClueDisposeInfo> CREATOR = new Creator<ClueDisposeInfo>() {
        @Override
        public ClueDisposeInfo createFromParcel(Parcel in) {
            return new ClueDisposeInfo(in);
        }

        @Override
        public ClueDisposeInfo[] newArray(int size) {
            return new ClueDisposeInfo[size];
        }
    };

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setClueBroadType(String clueBroadType) {
        this.clueBroadType = clueBroadType;
    }

    public void setClueType(String clueType) {
        this.clueType = clueType;
    }

    public void setClueChildType(String clueChildType) {
        this.clueChildType = clueChildType;
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

    public String getResult() {
        return result;
    }

    public String getClueBroadType() {
        return clueBroadType;
    }

    public String getClueType() {
        return clueType;
    }

    public String getClueChildType() {
        return clueChildType;
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
