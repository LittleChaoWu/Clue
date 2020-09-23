package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class LegalCaseInfo extends BaseBean implements Parcelable {

    private int id;//id
    private String title;//标题
    private String description;//描述
    private int createUserId;//创建人id
    private long createdTime;//创建时间
    private long updatedTime;//更新时间
    private int status;//状态 0:新建:1:启用:2:停用3:逻辑删除
    private String userRealName;//真实姓名
    private String userTelephone;//电话
    private String userPoliceCode;//警号
    private String orgText;//上传单位
    private List<FileModel> fileModels;
    private String filePaths;  //本地用
    private String fileIds;  //本地上传时使用

    public LegalCaseInfo() {
    }

    protected LegalCaseInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        createUserId = in.readInt();
        createdTime = in.readLong();
        updatedTime = in.readLong();
        status = in.readInt();
        userRealName = in.readString();
        userTelephone = in.readString();
        userPoliceCode = in.readString();
        orgText = in.readString();
        fileModels = in.createTypedArrayList(FileModel.CREATOR);
        filePaths = in.readString();
        fileIds = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(createUserId);
        dest.writeLong(createdTime);
        dest.writeLong(updatedTime);
        dest.writeInt(status);
        dest.writeString(userRealName);
        dest.writeString(userTelephone);
        dest.writeString(userPoliceCode);
        dest.writeString(orgText);
        dest.writeTypedList(fileModels);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LegalCaseInfo> CREATOR = new Creator<LegalCaseInfo>() {
        @Override
        public LegalCaseInfo createFromParcel(Parcel in) {
            return new LegalCaseInfo(in);
        }

        @Override
        public LegalCaseInfo[] newArray(int size) {
            return new LegalCaseInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public void setUserPoliceCode(String userPoliceCode) {
        this.userPoliceCode = userPoliceCode;
    }

    public void setOrgText(String orgText) {
        this.orgText = orgText;
    }

    public void setFileModels(List<FileModel> fileModels) {
        this.fileModels = fileModels;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public int getStatus() {
        return status;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public String getUserPoliceCode() {
        return userPoliceCode;
    }

    public String getOrgText() {
        return orgText;
    }

    public List<FileModel> getFileModels() {
        return fileModels;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }
}
