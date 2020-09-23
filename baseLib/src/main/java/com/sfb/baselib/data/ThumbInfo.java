package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class ThumbInfo extends BaseBean implements Parcelable {

    private String path;//路径
    private int type;//类型 ：主要是image和video
    private String mimeType;//文件类型
    private String title;//文件标题
    private FileModel fileModel;

    public ThumbInfo() {
    }

    public ThumbInfo(String path) {
        this.path = path;
    }

    public ThumbInfo(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    public String getPath() {
        return path;
    }

    public int getType() {
        return type;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getTitle() {
        return title;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    protected ThumbInfo(Parcel in) {
        path = in.readString();
        type = in.readInt();
        mimeType = in.readString();
        title = in.readString();
        fileModel = in.readParcelable(FileModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(type);
        dest.writeString(mimeType);
        dest.writeString(title);
        dest.writeParcelable(fileModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ThumbInfo> CREATOR = new Creator<ThumbInfo>() {
        @Override
        public ThumbInfo createFromParcel(Parcel in) {
            return new ThumbInfo(in);
        }

        @Override
        public ThumbInfo[] newArray(int size) {
            return new ThumbInfo[size];
        }
    };
}
