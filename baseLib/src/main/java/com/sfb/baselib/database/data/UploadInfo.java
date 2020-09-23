package com.sfb.baselib.database.data;

import android.os.Parcel;
import android.os.Parcelable;


import com.sfb.baselib.data.base.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

@Entity(nameInDb = "upload")
public class UploadInfo extends BaseBean implements Parcelable {

    public static final int UPLOAD_UNCOMPELETE = 0;//上传未完成
    public static final int UPLOAD_COMPELETE = 1;//上传完成
    public static final int UPLOAD_FAIL = 2;//上传失败
    public static final int UPLOADING = 3;//正在上传
    public static final int UPLOAD_STOP = 4;//停止上传
    public static final int UNUPLOAD = 5;//未上传
    @Id
    @Property(nameInDb = "_id")
    private Long id;
    @Property(nameInDb = "rowKey")
    private String rowKey;// 下载器网络标识
    @Property(nameInDb = "current_size")
    private long currentSize;// 完成度00
    @Property(nameInDb = "total_size")
    private long totalSize;//文件总大小
    @Property(nameInDb = "file_name")
    private String fileName;//文件名
    @Property(nameInDb = "file_path")
    private String filePath;//文件路径
    @Property(nameInDb = "save_time")
    private long saveTime;//保存时间
    @Property(nameInDb = "user")
    private String user;//上传用户名
    @Property(nameInDb = "file_type")
    private String fileType;//文件类型（暂无使用）
    @Property(nameInDb = "isUpload")
    private int isUpload;//0--未上传完成 1--上传完成 2--上传失败 3--正在上传中 4--暂停中
    @Property(nameInDb = "column1")
    private String column1;
    @Property(nameInDb = "column2")
    private int column2;
    @Property(nameInDb = "time_length")
    private long timeLength;//文件时长
    @Transient
    private String fromFolder;

    protected UploadInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        rowKey = in.readString();
        currentSize = in.readLong();
        totalSize = in.readLong();
        fileName = in.readString();
        filePath = in.readString();
        saveTime = in.readLong();
        user = in.readString();
        fileType = in.readString();
        isUpload = in.readInt();
        column1 = in.readString();
        column2 = in.readInt();
        timeLength = in.readLong();
        fromFolder = in.readString();
    }

    @Generated(hash = 1699621552)
    public UploadInfo(Long id, String rowKey, long currentSize, long totalSize,
            String fileName, String filePath, long saveTime, String user,
            String fileType, int isUpload, String column1, int column2,
            long timeLength) {
        this.id = id;
        this.rowKey = rowKey;
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.fileName = fileName;
        this.filePath = filePath;
        this.saveTime = saveTime;
        this.user = user;
        this.fileType = fileType;
        this.isUpload = isUpload;
        this.column1 = column1;
        this.column2 = column2;
        this.timeLength = timeLength;
    }

    @Generated(hash = 837649493)
    public UploadInfo() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(rowKey);
        dest.writeLong(currentSize);
        dest.writeLong(totalSize);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeLong(saveTime);
        dest.writeString(user);
        dest.writeString(fileType);
        dest.writeInt(isUpload);
        dest.writeString(column1);
        dest.writeInt(column2);
        dest.writeLong(timeLength);
        dest.writeString(fromFolder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowKey() {
        return this.rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    public String getColumn1() {
        return this.column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public int getColumn2() {
        return this.column2;
    }

    public void setColumn2(int column2) {
        this.column2 = column2;
    }

    public long getTimeLength() {
        return this.timeLength;
    }

    public void setTimeLength(long timeLength) {
        this.timeLength = timeLength;
    }

    public String getFromFolder() {
        return this.fromFolder;
    }

    public void setFromFolder(String fromFolder) {
        this.fromFolder = fromFolder;
    }

    public static final Creator<UploadInfo> CREATOR = new Creator<UploadInfo>() {
        @Override
        public UploadInfo createFromParcel(Parcel in) {
            return new UploadInfo(in);
        }

        @Override
        public UploadInfo[] newArray(int size) {
            return new UploadInfo[size];
        }
    };
}
