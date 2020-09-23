package com.sfb.baselib.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;
import com.sfb.baselib.R;

public class ClueInfo extends BaseBean implements Parcelable {

    //线索处理状态
    public static final int CLUE_STATUS_UN_HANDLE = 0;//未处理
    public static final int CLUE_STATUS_HANDLING = 1;//处理中
    public static final int CLUE_STATUS_HANDLED = 2;//已处置
    public static final int CLUE_STATUS_UN_PASSED = 3;//不处置
    public static final int CLUE_STATUS_CHECKED_NO_CONFIRM = 5; //待确认处置
    public static final int CLUE_STATUS_ALREADY_FEEDBACK = 6; //已反馈

    private int id;
    private String title;
    private int areaCode;//所属区编号
    private String areaName;//所在地址
    private String clueBroadType;//线索大类
    private String clueBroadTypeName;//线索大类名称
    private String clueType;//线索小类
    private String clueTypeName;//线索小类名称
    private String clueMiniType;//线索细项
    private String clueMiniTypeName;//线索细项名称
    private int createUserId;//举报人id
    private String createUserName;//举报人姓名
    private String summary;//描述
    private String filePaths;  //本地用
    private String fileIds;//文件id，多个以“|”号分离,本地上传时使用
    private String createTimeStr;//格式化的 创建时间
    private long createdTime;//创建时间
    private String gps;//格式 ： 经度，纬度
    private int clueStatus;//状态 : 0-未处理、1-处理中、2-已处置、3-不处置、 5-待确认处置、 6-已反馈
    private String filepaths;
    private int sourceType;//采集来源（0--普通采集，1--来自任务）
    private int subTaskId;//子任务id

    private String clueCode;
    private long updatedTime;
    private int status;
    private String resultNote;//评价内容
    private int clueScore;//评分
    private boolean isHideInfo;
    private String realname;
    private String telephone;
    private String remark;
    private String addressHide;//地址，一个隐藏的地址，自动获取，后台需要

    public ClueInfo() {
    }

    protected ClueInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        areaCode = in.readInt();
        areaName = in.readString();
        clueType = in.readString();
        clueBroadType = in.readString();
        clueMiniType = in.readString();
        clueTypeName = in.readString();
        clueBroadTypeName = in.readString();
        clueMiniTypeName = in.readString();
        createUserId = in.readInt();
        createUserName = in.readString();
        summary = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
        createTimeStr = in.readString();
        createdTime = in.readLong();
        gps = in.readString();
        clueStatus = in.readInt();
        filepaths = in.readString();
        sourceType = in.readInt();
        subTaskId = in.readInt();
        clueCode = in.readString();
        updatedTime = in.readLong();
        status = in.readInt();
        resultNote = in.readString();
        clueScore = in.readInt();
        clueMiniType = in.readString();
        clueMiniTypeName = in.readString();
        isHideInfo = in.readByte() != 0;
        realname = in.readString();
        telephone = in.readString();
        remark = in.readString();
        addressHide = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(areaCode);
        dest.writeString(areaName);
        dest.writeString(clueType);
        dest.writeString(clueBroadType);
        dest.writeString(clueMiniType);
        dest.writeString(clueTypeName);
        dest.writeString(clueBroadTypeName);
        dest.writeString(clueMiniTypeName);
        dest.writeInt(createUserId);
        dest.writeString(createUserName);
        dest.writeString(summary);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
        dest.writeString(createTimeStr);
        dest.writeLong(createdTime);
        dest.writeString(gps);
        dest.writeInt(clueStatus);
        dest.writeString(filepaths);
        dest.writeInt(sourceType);
        dest.writeInt(subTaskId);
        dest.writeString(clueCode);
        dest.writeLong(updatedTime);
        dest.writeInt(status);
        dest.writeString(resultNote);
        dest.writeInt(clueScore);
        dest.writeString(clueMiniType);
        dest.writeString(clueMiniTypeName);
        dest.writeByte((byte) (isHideInfo ? 1 : 0));
        dest.writeString(realname);
        dest.writeString(telephone);
        dest.writeString(remark);
        dest.writeString(addressHide);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClueInfo> CREATOR = new Creator<ClueInfo>() {
        @Override
        public ClueInfo createFromParcel(Parcel in) {
            return new ClueInfo(in);
        }

        @Override
        public ClueInfo[] newArray(int size) {
            return new ClueInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setClueType(String clueType) {
        this.clueType = clueType;
    }

    public void setClueBroadType(String clueBroadType) {
        this.clueBroadType = clueBroadType;
    }

    public void setClueMiniType(String clueMiniType) {
        this.clueMiniType = clueMiniType;
    }

    public void setClueTypeName(String clueTypeName) {
        this.clueTypeName = clueTypeName;
    }

    public void setClueBroadTypeName(String clueBroadTypeName) {
        this.clueBroadTypeName = clueBroadTypeName;
    }

    public void setClueMiniTypeName(String clueMiniTypeName) {
        this.clueMiniTypeName = clueMiniTypeName;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public void setClueStatus(int clueStatus) {
        this.clueStatus = clueStatus;
    }

    public void setFilepaths(String filepaths) {
        this.filepaths = filepaths;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setClueCode(String clueCode) {
        this.clueCode = clueCode;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public void setClueScore(int clueScore) {
        this.clueScore = clueScore;
    }

    public void setHideInfo(boolean hideInfo) {
        isHideInfo = hideInfo;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getClueType() {
        return clueType;
    }

    public String getClueBroadType() {
        return clueBroadType;
    }

    public String getClueMiniType() {
        return clueMiniType;
    }

    public String getClueTypeName() {
        return clueTypeName;
    }

    public String getClueBroadTypeName() {
        return clueBroadTypeName;
    }

    public String getClueMiniTypeName() {
        return clueMiniTypeName;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public String getSummary() {
        return summary;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getGps() {
        return gps;
    }

    public int getClueStatus() {
        return clueStatus;
    }

    public String getFilepaths() {
        return filepaths;
    }

    public int getSourceType() {
        return sourceType;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public String getClueCode() {
        return clueCode;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public int getStatus() {
        return status;
    }

    public String getResultNote() {
        return resultNote;
    }

    public int getClueScore() {
        return clueScore;
    }

    public boolean isHideInfo() {
        return isHideInfo;
    }

    public String getRealname() {
        return realname;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getRemark() {
        return remark;
    }

    public String getAddressHide() {
        return addressHide;
    }

    public void setAddressHide(String addressHide) {
        this.addressHide = addressHide;
    }

    public String getStatusInReport(Context context) {
        String status;
        switch (clueStatus) {
            case CLUE_STATUS_UN_HANDLE:
                status = context.getString(R.string.no_deal);
                break;
            case CLUE_STATUS_HANDLING:
            case CLUE_STATUS_CHECKED_NO_CONFIRM:
                status = context.getString(R.string.dealing);
                break;
            case CLUE_STATUS_HANDLED:
                status = context.getString(R.string.deal);
                break;
            case CLUE_STATUS_UN_PASSED:
                status = context.getString(R.string.pass_deal);
                break;
            case CLUE_STATUS_ALREADY_FEEDBACK:
                status = context.getString(R.string.already_feedback);
                break;
            default:
                status = "UNKNOW(value=" + clueStatus + ")";
                break;
        }
        return status;
    }

    public String getStatusInDispose(Context context) {
        String status;
        switch (clueStatus) {
            case 1:
                status = context.getString(R.string.no_deal);
                break;
            default:
                status = context.getString(R.string.deal);
                break;
        }
        return status;
    }

}
