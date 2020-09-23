package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class AvatarInfo extends BaseBean implements Parcelable {

    private String fileId;
    private String filePath;

    public AvatarInfo(String filePath) {
        this.filePath = filePath;
    }

    protected AvatarInfo(Parcel in) {
        fileId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AvatarInfo> CREATOR = new Creator<AvatarInfo>() {
        @Override
        public AvatarInfo createFromParcel(Parcel in) {
            return new AvatarInfo(in);
        }

        @Override
        public AvatarInfo[] newArray(int size) {
            return new AvatarInfo[size];
        }
    };

    public String getFilePath() {
        return filePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
