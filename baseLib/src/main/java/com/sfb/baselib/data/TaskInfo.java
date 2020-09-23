package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class TaskInfo extends BaseBean implements Parcelable {

    //任务状态
    public static final String CREATED = "created";//已创建
    public static final String WAITING = "waiting";//待领取
    public static final String PUBLISHED = "published";//已发布
    public static final String STARTED = "started";//已开始
    public static final String ENDED = "ended";//已结束
    public static final String ABORTED = "aborted";//已终止

    @Expose
    private Integer id;
    @Expose
    private String subject;//任务主题
    @Expose
    private String category;//任务类型
    @Expose
    private String categoryName;//任务类型名字
    @Expose
    private String subCategory;//子任务类型
    @Expose
    private String subCategoryName;//任务子类型名字
    @Expose
    private String type;//任务类型，通用，定向，临时
    @Expose
    private String description;//任务描述
    @Expose
    private long beginTime;//开始时间
    @Expose
    private long endTime;//结束时间
    @Expose
    private long receiveBeginTime;//开始领取时间
    @Expose
    private long receiveEndTime;//截止领取时间
    @Expose
    private String leaders;//领队人员，以"|"分隔,可空
    @Expose
    private String userScope;//表示接收人范围，不填时默认为all（全部人接收）,字段为：u1|u2|oMASS_ROLE_1
    @Expose
    private int maxScore;//积分
    @Expose
    private int numOfPeople;//指定的人数
    @Expose
    private long createdTime;//创建时间
    @Expose
    private String routes;//巡逻路径点集
    @Expose
    private String locations;//所有标记点的经纬度字符串
    @Expose
    private String addresses;//所有标记点的地址字符串
    @Expose
    private int delivery_method;//分配方式
    @Expose
    private String fileIdsStr; //本地上传时使用
    //针对模板类型的信息
    @Expose
    private String tplName;//模板名称
    @Expose
    private int status;
    @Expose
    private int ttype;//0：公共模板，1：个性模板

    private long publishTime;//发布时间
    private int completedCount;//完成人数
    private String publisherRealName;//发布人
    private String publisherOrgName;//发布人所属单位名称
    private String abortedReason;//任务中断原因
    private String taskStatus;//任务状态
    private List<LocationInfo> locationList;
    private List<LocationInfo> locationList2;
    private LocationInfo locationAddr;//此字段只针对要从抢单页面进入执行页面时带给执行页面的用户选择唯一一个任务目标地址。
    private String selectCaptionIds;//领队
    private List<TaskLeaderInfo> leaderList;//领队人的集合
    private String selectUserIds;//接收对象
    private List<TaskReceiverInfo> userScopeList;//接收对象的集合
    private String duiCount;//领队人数
    private String personRangeCount;//接收对象的人数
    private String needPersonCount;//所需人数
    private String nowPersonCount;//所需人数（巡逻）
    private String filePaths;  //本地用
    private List<FileModel> fileUploads;
    private TaskSubInfo subTask;

    protected TaskInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        subject = in.readString();
        category = in.readString();
        categoryName = in.readString();
        subCategory = in.readString();
        subCategoryName = in.readString();
        type = in.readString();
        description = in.readString();
        beginTime = in.readLong();
        endTime = in.readLong();
        receiveBeginTime = in.readLong();
        receiveEndTime = in.readLong();
        leaders = in.readString();
        userScope = in.readString();
        maxScore = in.readInt();
        numOfPeople = in.readInt();
        createdTime = in.readLong();
        routes = in.readString();
        locations = in.readString();
        addresses = in.readString();
        delivery_method = in.readInt();
        fileIdsStr = in.readString();
        tplName = in.readString();
        status = in.readInt();
        ttype = in.readInt();
        publishTime = in.readLong();
        completedCount = in.readInt();
        publisherRealName = in.readString();
        publisherOrgName = in.readString();
        abortedReason = in.readString();
        taskStatus = in.readString();
        locationList = in.createTypedArrayList(LocationInfo.CREATOR);
        locationList2 = in.createTypedArrayList(LocationInfo.CREATOR);
        locationAddr = in.readParcelable(LocationInfo.class.getClassLoader());
        selectCaptionIds = in.readString();
        selectUserIds = in.readString();
        userScopeList = in.createTypedArrayList(TaskReceiverInfo.CREATOR);
        duiCount = in.readString();
        personRangeCount = in.readString();
        needPersonCount = in.readString();
        nowPersonCount = in.readString();
        filePaths = in.readString();
        fileUploads = in.createTypedArrayList(FileModel.CREATOR);
        subTask = in.readParcelable(TaskSubInfo.class.getClassLoader());
    }

    public static final Creator<TaskInfo> CREATOR = new Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel in) {
            return new TaskInfo(in);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };

    public List<LocationInfo> getLocationInfoList() {
        if (locationList2 != null) {
            return locationList2;
        } else {
            return locationList;
        }
    }


    public TaskInfo() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getReceiveBeginTime() {
        return receiveBeginTime;
    }

    public void setReceiveBeginTime(long receiveBeginTime) {
        this.receiveBeginTime = receiveBeginTime;
    }

    public long getReceiveEndTime() {
        return receiveEndTime;
    }

    public void setReceiveEndTime(long receiveEndTime) {
        this.receiveEndTime = receiveEndTime;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public String getUserScope() {
        return userScope;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public int getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(int delivery_method) {
        this.delivery_method = delivery_method;
    }

    public String getFileIdsStr() {
        return fileIdsStr;
    }

    public void setFileIdsStr(String fileIdsStr) {
        this.fileIdsStr = fileIdsStr;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTtype() {
        return ttype;
    }

    public void setTtype(int ttype) {
        this.ttype = ttype;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public String getPublisherRealName() {
        return publisherRealName;
    }

    public void setPublisherRealName(String publisherRealName) {
        this.publisherRealName = publisherRealName;
    }

    public String getPublisherOrgName() {
        return publisherOrgName;
    }

    public void setPublisherOrgName(String publisherOrgName) {
        this.publisherOrgName = publisherOrgName;
    }

    public String getAbortedReason() {
        return abortedReason;
    }

    public void setAbortedReason(String abortedReason) {
        this.abortedReason = abortedReason;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<LocationInfo> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationInfo> locationList) {
        this.locationList = locationList;
    }

    public List<LocationInfo> getLocationList2() {
        return locationList2;
    }

    public void setLocationList2(List<LocationInfo> locationList2) {
        this.locationList2 = locationList2;
    }

    public LocationInfo getLocationAddr() {
        return locationAddr;
    }

    public void setLocationAddr(LocationInfo locationAddr) {
        this.locationAddr = locationAddr;
    }

    public String getSelectCaptionIds() {
        return selectCaptionIds;
    }

    public void setSelectCaptionIds(String selectCaptionIds) {
        this.selectCaptionIds = selectCaptionIds;
    }

    public List<TaskLeaderInfo> getLeaderList() {
        return leaderList;
    }

    public void setLeaderList(List<TaskLeaderInfo> leaderList) {
        this.leaderList = leaderList;
    }

    public String getSelectUserIds() {
        return selectUserIds;
    }

    public void setSelectUserIds(String selectUserIds) {
        this.selectUserIds = selectUserIds;
    }

    public List<TaskReceiverInfo> getUserScopeList() {
        return userScopeList;
    }

    public void setUserScopeList(List<TaskReceiverInfo> userScopeList) {
        this.userScopeList = userScopeList;
    }

    public String getDuiCount() {
        return duiCount;
    }

    public void setDuiCount(String duiCount) {
        this.duiCount = duiCount;
    }

    public String getPersonRangeCount() {
        return personRangeCount;
    }

    public void setPersonRangeCount(String personRangeCount) {
        this.personRangeCount = personRangeCount;
    }

    public String getNeedPersonCount() {
        return needPersonCount;
    }

    public void setNeedPersonCount(String needPersonCount) {
        this.needPersonCount = needPersonCount;
    }

    public String getNowPersonCount() {
        return nowPersonCount;
    }

    public void setNowPersonCount(String nowPersonCount) {
        this.nowPersonCount = nowPersonCount;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public List<FileModel> getFileUploads() {
        return fileUploads;
    }

    public void setFileUploads(List<FileModel> fileUploads) {
        this.fileUploads = fileUploads;
    }

    public TaskSubInfo getSubTask() {
        return subTask;
    }

    public void setSubTask(TaskSubInfo subTask) {
        this.subTask = subTask;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(subject);
        dest.writeString(category);
        dest.writeString(categoryName);
        dest.writeString(subCategory);
        dest.writeString(subCategoryName);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeLong(beginTime);
        dest.writeLong(endTime);
        dest.writeLong(receiveBeginTime);
        dest.writeLong(receiveEndTime);
        dest.writeString(leaders);
        dest.writeString(userScope);
        dest.writeInt(maxScore);
        dest.writeInt(numOfPeople);
        dest.writeLong(createdTime);
        dest.writeString(routes);
        dest.writeString(locations);
        dest.writeString(addresses);
        dest.writeInt(delivery_method);
        dest.writeString(fileIdsStr);
        dest.writeString(tplName);
        dest.writeInt(status);
        dest.writeInt(ttype);
        dest.writeLong(publishTime);
        dest.writeInt(completedCount);
        dest.writeString(publisherRealName);
        dest.writeString(publisherOrgName);
        dest.writeString(abortedReason);
        dest.writeString(taskStatus);
        dest.writeTypedList(locationList);
        dest.writeTypedList(locationList2);
        dest.writeParcelable(locationAddr, flags);
        dest.writeString(selectCaptionIds);
        dest.writeString(selectUserIds);
        dest.writeTypedList(userScopeList);
        dest.writeString(duiCount);
        dest.writeString(personRangeCount);
        dest.writeString(needPersonCount);
        dest.writeString(nowPersonCount);
        dest.writeString(filePaths);
        dest.writeTypedList(fileUploads);
        dest.writeParcelable(subTask, flags);
    }
}
