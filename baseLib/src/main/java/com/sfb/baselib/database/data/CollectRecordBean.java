package com.sfb.baselib.database.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sfb.baselib.data.base.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

@Entity(nameInDb = "collect_report_record")
public class CollectRecordBean extends BaseBean implements Parcelable {

    //记录类型
    public static final int CAR_COLLECT = 0;//车辆采集
    public static final int RENTAL_HOUSE_COLLECT = 1;//出租房采集
    public static final int CLUE_COLLECT = 2;//线索采集
    public static final int TASK_REPORT = 3;//任务报告
    public static final int PUBLISH_NOTICE = 4;//发布通知
    public static final int PUBLISH_TASK = 5;//发布任务
    //public static final int OTHER = 6;//其他 [注意：不要此项，将此项对应的类型拆分开]
    public static final int PERSONNEL_COLLECT = 7;//人员采集
    public static final int ILLEGAL_PARK_COLLECT = 8;//非法停车采集
    public static final int CHECK_BACKGROUND = 9;//检查背景
    public static final int CASE_INPUT = 10;//案件导入
    public static final int SIGN = 11;
    public static final int DISPOSE_CLUE = 12;//线索处理
    public static final int CLUE_FEEDBACK = 13;//线索反馈
    public static final int CLUE_ASSIGN = 14;//线索分配
    public static final int ADD_RENTAL_PERSON = 15;//新增出租人
    //以下是原Other对应的各种类型
    public static final int REGISTER = 16;//注册
    public static final int REGISTER_AGAIN = 17;//重新注册
    public static final int RESET_INFO = 18;//补全信息/重置信息
    public static final int AVATAR = 19;//头像
    public static final int GAS_COLLECT = 20;//汽油管理
    public static final int DUMP_LOG = 21;//错误日志

    public enum RECORD_ROLE {
        NONE,//无型
        CACHE,//缓存类型
        TRANSMIT,//传输类型
        CACHE_TRANSMIT//缓存列表的传输类型
    }

    public static final int TRANSMIT_RECORD = 0;//传输记录
    public static final int CACHE_RECORD = 1;//缓存记录

    public static final int STATE_OF_PAUSE = 0;//暂停
    public static final int STATE_OF_UPLOADING = 1;//文件上传中
    public static final int STATE_OF_UPLOAD_SUCCESS = 2;//文件上传完成
    public static final int STATE_OF_UPLOAD_FAIL = 3;//文件上传失败
    public static final int STATE_OF_SUBMIT_SUCCESS = 4;//提交成功
    public static final int STATE_OF_SUBMIT_FAIL = 5;//提交失败
    public static final int STATE_OF_WAITING = 6;//等待上传

    @Id
    @Property(nameInDb = "_id")
    private Long id;
    @Property(nameInDb = "collect_type")
    private int collectType;//记录类型 0--车辆采集 1--出租屋采集 2--线索采集
    @Property(nameInDb = "taskCategory")
    private String taskCategory;//任务主类型
    @Property(nameInDb = "taskSubCategory")
    private String taskSubCategory;//任务子类型
    @Property(nameInDb = "subject")
    private String subject;//主题
    @Property(nameInDb = "car_color")
    private String carColor;//车牌颜色
    @Property(nameInDb = "content")
    private String content;//采集内容
    @Property(nameInDb = "gps")
    private String gps;//采集位置GPS
    @Property(nameInDb = "gps_address")
    private String gpsAddress;//采集位置地址
    @Property(nameInDb = "user")
    private String user;//当前用户名
    @Property(nameInDb = "filepaths")
    private String filepaths;//采集时附件文件路径集
    @Property(nameInDb = "save_time")
    private long saveTime;
    @Property(nameInDb = "file_upload_infos")
    private String fileUploadInfos;
    @Property(nameInDb = "car_num")
    private String carNum;//车牌号
    @Property(nameInDb = "idcard_num")
    private String idCardNum;//身份证号
    @Property(nameInDb = "rental_person")
    private String rentalPerson;//出租人名字
    @Property(nameInDb = "rental_phone")
    private String rentalPhone;//出租人电话
    @Property(nameInDb = "current_size")
    private long currentSize;
    @Property(nameInDb = "report_main_category_key")
    private String reportMainCategoryKey;
    @Property(nameInDb = "report_sub_category_key")
    private String reportSubCategoryKey;
    @Property(nameInDb = "report_main_category_value")
    private String reportMainCategoryValue;
    @Property(nameInDb = "report_sub_category_value")
    private String reportSubCategoryValue;
    @Property(nameInDb = "rental_person_json")
    private String rentalPersonJson;
    @Property(nameInDb = "total_size")
    private long totalSize;
    @Property(nameInDb = "current_index")
    private int currentIndex;
    @Property(nameInDb = "total_index")
    private int totalIndex;
    @Property(nameInDb = "upload_report_state")
    private int uploadReportState;//采集提交上传状态 0--未举报，1--举报文件上传中 2--举报文件上传完成 3--举报文件上传失败 4--举报成功 5--举报失败
    @Property(nameInDb = "rental_idcard_path")
    private String rentalIdCardPath;//出租屋子身份证照路径
    @Property(nameInDb = "rental_doornum_path")
    private String rentalDoornumPath;//出租屋门牌照路径
    @Property(nameInDb = "rental_house_path")
    private String rentalHousePath;//出租屋照路径
    @Property(nameInDb = "attach_data")
    private String attachData;//附带数据
    @Property(nameInDb = "task_data")
    private String taskData;//任务相关数据
    @Property(nameInDb = "task_id")
    private String taskId;//任务ID
    @Property(nameInDb = "action_constant")
    private int actionConstant;
    @Property(nameInDb = "record_role")
    private int recordRole;
    @Property(nameInDb = "is_cache")
    private int isCache;
    @Property(nameInDb = "url")
    private String url;//正常请求地址,zhangy add 20161010

    @Transient
    private boolean isSelect;//是否在列表中选中

    protected CollectRecordBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        collectType = in.readInt();
        taskCategory = in.readString();
        taskSubCategory = in.readString();
        subject = in.readString();
        carColor = in.readString();
        content = in.readString();
        gps = in.readString();
        gpsAddress = in.readString();
        user = in.readString();
        filepaths = in.readString();
        saveTime = in.readLong();
        fileUploadInfos = in.readString();
        carNum = in.readString();
        idCardNum = in.readString();
        rentalPerson = in.readString();
        rentalPhone = in.readString();
        currentSize = in.readLong();
        reportMainCategoryKey = in.readString();
        reportSubCategoryKey = in.readString();
        reportMainCategoryValue = in.readString();
        reportSubCategoryValue = in.readString();
        rentalPersonJson = in.readString();
        totalSize = in.readLong();
        currentIndex = in.readInt();
        totalIndex = in.readInt();
        uploadReportState = in.readInt();
        rentalIdCardPath = in.readString();
        rentalDoornumPath = in.readString();
        rentalHousePath = in.readString();
        attachData = in.readString();
        taskData = in.readString();
        taskId = in.readString();
        actionConstant = in.readInt();
        recordRole = in.readInt();
        isCache = in.readInt();
        url = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Generated(hash = 681905703)
    public CollectRecordBean(Long id, int collectType, String taskCategory, String taskSubCategory,
                             String subject, String carColor, String content, String gps, String gpsAddress, String user,
                             String filepaths, long saveTime, String fileUploadInfos, String carNum, String idCardNum,
                             String rentalPerson, String rentalPhone, long currentSize, String reportMainCategoryKey,
                             String reportSubCategoryKey, String reportMainCategoryValue, String reportSubCategoryValue,
                             String rentalPersonJson, long totalSize, int currentIndex, int totalIndex,
                             int uploadReportState, String rentalIdCardPath, String rentalDoornumPath,
                             String rentalHousePath, String attachData, String taskData, String taskId,
                             int actionConstant, int recordRole, int isCache, String url) {
        this.id = id;
        this.collectType = collectType;
        this.taskCategory = taskCategory;
        this.taskSubCategory = taskSubCategory;
        this.subject = subject;
        this.carColor = carColor;
        this.content = content;
        this.gps = gps;
        this.gpsAddress = gpsAddress;
        this.user = user;
        this.filepaths = filepaths;
        this.saveTime = saveTime;
        this.fileUploadInfos = fileUploadInfos;
        this.carNum = carNum;
        this.idCardNum = idCardNum;
        this.rentalPerson = rentalPerson;
        this.rentalPhone = rentalPhone;
        this.currentSize = currentSize;
        this.reportMainCategoryKey = reportMainCategoryKey;
        this.reportSubCategoryKey = reportSubCategoryKey;
        this.reportMainCategoryValue = reportMainCategoryValue;
        this.reportSubCategoryValue = reportSubCategoryValue;
        this.rentalPersonJson = rentalPersonJson;
        this.totalSize = totalSize;
        this.currentIndex = currentIndex;
        this.totalIndex = totalIndex;
        this.uploadReportState = uploadReportState;
        this.rentalIdCardPath = rentalIdCardPath;
        this.rentalDoornumPath = rentalDoornumPath;
        this.rentalHousePath = rentalHousePath;
        this.attachData = attachData;
        this.taskData = taskData;
        this.taskId = taskId;
        this.actionConstant = actionConstant;
        this.recordRole = recordRole;
        this.isCache = isCache;
        this.url = url;
    }

    @Generated(hash = 2046816929)
    public CollectRecordBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeInt(collectType);
        dest.writeString(taskCategory);
        dest.writeString(taskSubCategory);
        dest.writeString(subject);
        dest.writeString(carColor);
        dest.writeString(content);
        dest.writeString(gps);
        dest.writeString(gpsAddress);
        dest.writeString(user);
        dest.writeString(filepaths);
        dest.writeLong(saveTime);
        dest.writeString(fileUploadInfos);
        dest.writeString(carNum);
        dest.writeString(idCardNum);
        dest.writeString(rentalPerson);
        dest.writeString(rentalPhone);
        dest.writeLong(currentSize);
        dest.writeString(reportMainCategoryKey);
        dest.writeString(reportSubCategoryKey);
        dest.writeString(reportMainCategoryValue);
        dest.writeString(reportSubCategoryValue);
        dest.writeString(rentalPersonJson);
        dest.writeLong(totalSize);
        dest.writeInt(currentIndex);
        dest.writeInt(totalIndex);
        dest.writeInt(uploadReportState);
        dest.writeString(rentalIdCardPath);
        dest.writeString(rentalDoornumPath);
        dest.writeString(rentalHousePath);
        dest.writeString(attachData);
        dest.writeString(taskData);
        dest.writeString(taskId);
        dest.writeInt(actionConstant);
        dest.writeInt(recordRole);
        dest.writeInt(isCache);
        dest.writeString(url);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CollectRecordBean> CREATOR = new Creator<CollectRecordBean>() {
        @Override
        public CollectRecordBean createFromParcel(Parcel in) {
            return new CollectRecordBean(in);
        }

        @Override
        public CollectRecordBean[] newArray(int size) {
            return new CollectRecordBean[size];
        }
    };

    public List<UploadInfo> getUploadInfos() {
        if (!TextUtils.isEmpty(fileUploadInfos)) {
            return new Gson().fromJson(fileUploadInfos, new TypeToken<List<UploadInfo>>() {
            }.getType());
        }
        return null;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCollectType() {
        return this.collectType;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }

    public String getTaskCategory() {
        return this.taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskSubCategory() {
        return this.taskSubCategory;
    }

    public void setTaskSubCategory(String taskSubCategory) {
        this.taskSubCategory = taskSubCategory;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCarColor() {
        return this.carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGps() {
        return this.gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getGpsAddress() {
        return this.gpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        this.gpsAddress = gpsAddress;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFilepaths() {
        return this.filepaths;
    }

    public void setFilepaths(String filepaths) {
        this.filepaths = filepaths;
    }

    public long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public String getFileUploadInfos() {
        return this.fileUploadInfos;
    }

    public void setFileUploadInfos(String fileUploadInfos) {
        this.fileUploadInfos = fileUploadInfos;
    }

    public String getCarNum() {
        return this.carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getIdCardNum() {
        return this.idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getRentalPerson() {
        return this.rentalPerson;
    }

    public void setRentalPerson(String rentalPerson) {
        this.rentalPerson = rentalPerson;
    }

    public String getRentalPhone() {
        return this.rentalPhone;
    }

    public void setRentalPhone(String rentalPhone) {
        this.rentalPhone = rentalPhone;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public String getReportMainCategoryKey() {
        return this.reportMainCategoryKey;
    }

    public void setReportMainCategoryKey(String reportMainCategoryKey) {
        this.reportMainCategoryKey = reportMainCategoryKey;
    }

    public String getReportSubCategoryKey() {
        return this.reportSubCategoryKey;
    }

    public void setReportSubCategoryKey(String reportSubCategoryKey) {
        this.reportSubCategoryKey = reportSubCategoryKey;
    }

    public String getReportMainCategoryValue() {
        return this.reportMainCategoryValue;
    }

    public void setReportMainCategoryValue(String reportMainCategoryValue) {
        this.reportMainCategoryValue = reportMainCategoryValue;
    }

    public String getReportSubCategoryValue() {
        return this.reportSubCategoryValue;
    }

    public void setReportSubCategoryValue(String reportSubCategoryValue) {
        this.reportSubCategoryValue = reportSubCategoryValue;
    }

    public String getRentalPersonJson() {
        return this.rentalPersonJson;
    }

    public void setRentalPersonJson(String rentalPersonJson) {
        this.rentalPersonJson = rentalPersonJson;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTotalIndex() {
        return this.totalIndex;
    }

    public void setTotalIndex(int totalIndex) {
        this.totalIndex = totalIndex;
    }

    public int getUploadReportState() {
        return this.uploadReportState;
    }

    public void setUploadReportState(int uploadReportState) {
        this.uploadReportState = uploadReportState;
    }

    public String getRentalIdCardPath() {
        return this.rentalIdCardPath;
    }

    public void setRentalIdCardPath(String rentalIdCardPath) {
        this.rentalIdCardPath = rentalIdCardPath;
    }

    public String getRentalDoornumPath() {
        return this.rentalDoornumPath;
    }

    public void setRentalDoornumPath(String rentalDoornumPath) {
        this.rentalDoornumPath = rentalDoornumPath;
    }

    public String getRentalHousePath() {
        return this.rentalHousePath;
    }

    public void setRentalHousePath(String rentalHousePath) {
        this.rentalHousePath = rentalHousePath;
    }

    public String getAttachData() {
        return this.attachData;
    }

    public void setAttachData(String attachData) {
        this.attachData = attachData;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getActionConstant() {
        return this.actionConstant;
    }

    public void setActionConstant(int actionConstant) {
        this.actionConstant = actionConstant;
    }

    public int getRecordRole() {
        return this.recordRole;
    }

    public void setRecordRole(int recordRole) {
        this.recordRole = recordRole;
    }

    public int getIsCache() {
        return this.isCache;
    }

    public void setIsCache(int isCache) {
        this.isCache = isCache;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
