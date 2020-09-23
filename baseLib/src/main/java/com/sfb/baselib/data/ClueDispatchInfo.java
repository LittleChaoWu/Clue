package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class ClueDispatchInfo extends BaseBean implements Parcelable {

    private int clueId;
    private String remark;
    private String orgCodes;
    private String filePaths;
    private String fileIds;

    public ClueDispatchInfo() {
    }

    protected ClueDispatchInfo(Parcel in) {
        clueId = in.readInt();
        remark = in.readString();
        orgCodes = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clueId);
        dest.writeString(remark);
        dest.writeString(orgCodes);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClueDispatchInfo> CREATOR = new Creator<ClueDispatchInfo>() {
        @Override
        public ClueDispatchInfo createFromParcel(Parcel in) {
            return new ClueDispatchInfo(in);
        }

        @Override
        public ClueDispatchInfo[] newArray(int size) {
            return new ClueDispatchInfo[size];
        }
    };

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOrgCodes(String orgCodes) {
        this.orgCodes = orgCodes;
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

    public String getOrgCodes() {
        return orgCodes;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }
}
