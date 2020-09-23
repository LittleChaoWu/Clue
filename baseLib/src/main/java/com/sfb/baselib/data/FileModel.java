package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;


/**
 * 文件类型
 */
public class FileModel extends BaseBean implements Parcelable {

    private int fileId;
    private String fileType;
    private String fileContentType;
    private String fileName;
    private long fileSize;
    private long fileTimeLength;
    private long createdTime;

    public FileModel() {
    }

    protected FileModel(Parcel in) {
        fileId = in.readInt();
        fileType = in.readString();
        fileContentType = in.readString();
        fileName = in.readString();
        fileSize = in.readLong();
        fileTimeLength = in.readLong();
        createdTime = in.readLong();
    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fileId);
        dest.writeString(fileType);
        dest.writeString(fileContentType);
        dest.writeString(fileName);
        dest.writeLong(fileSize);
        dest.writeLong(fileTimeLength);
        dest.writeLong(createdTime);
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getFileTimeLength() {
        return fileTimeLength;
    }

    public void setFileTimeLength(long fileTimeLength) {
        this.fileTimeLength = fileTimeLength;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "fileId=" + fileId +
                ", fileType='" + fileType + '\'' +
                ", fileContentType='" + fileContentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileTimeLength=" + fileTimeLength +
                ", createdTime=" + createdTime +
                '}';
    }

}
