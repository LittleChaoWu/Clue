package com.sfb.baselib.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;
import com.sfb.baselib.R;

import java.util.List;

public class CarInfo extends BaseBean implements Parcelable{

    private int id;
    private int type; //车辆类型
    private String plate_num;//车牌号
    private String color;//车辆颜色
    private String coll_address;//采集地址的json
    private String remark;//内容描述
    private long created_time;  //采集时间（private long）
    private int sourceType;//采集来源（0--普通采集，1--来自任务）
    private int subTaskId;//子任务id
    private boolean isUnLimitTime;//车辆采集参数，是否不限制2小时内采集一辆车，是任务的话传入true，否则false
    private String personListJson;//随行人员json
    private List<DriverDetailInfo> person_list;//随行人员集合
    private String carPhotoPath; //车辆照片文件路径（本地用）
    private String carPhotoId; //车辆照片文件路径（本地上传时使用）
    private String filePaths;  //本地用
    private String fileIds;  //本地上传时使用
    private FileModel car_file;//车辆照片的FileModel
    private List<FileModel> other_file_list;//记录文件的FileModel

    public CarInfo() {
    }

    protected CarInfo(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        plate_num = in.readString();
        color = in.readString();
        coll_address = in.readString();
        remark = in.readString();
        created_time = in.readLong();
        sourceType = in.readInt();
        subTaskId = in.readInt();
        isUnLimitTime = in.readByte() != 0;
        personListJson = in.readString();
        person_list = in.createTypedArrayList(DriverDetailInfo.CREATOR);
        carPhotoPath = in.readString();
        carPhotoId = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
        car_file = in.readParcelable(FileModel.class.getClassLoader());
        other_file_list = in.createTypedArrayList(FileModel.CREATOR);
    }

    public static final Creator<CarInfo> CREATOR = new Creator<CarInfo>() {
        @Override
        public CarInfo createFromParcel(Parcel in) {
            return new CarInfo(in);
        }

        @Override
        public CarInfo[] newArray(int size) {
            return new CarInfo[size];
        }
    };

    /**
     * 获取车辆类型的字符串
     *
     * @param context Context
     * @return String
     */
    public String getCarTypeText(Context context) {
        String carTypeText;
        switch (type) {
            case 1:
                carTypeText = context.getString(R.string.automobile);
                break;
            case 2:
                carTypeText = context.getString(R.string.truck);
                break;
            case 3:
                carTypeText = context.getString(R.string.man_motorcycle);
                break;
            case 4:
                carTypeText = context.getString(R.string.woman_motorcycle);
                break;
            default:
                carTypeText = context.getString(R.string.automobile);
                break;
        }
        return carTypeText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPlate_num(String plate_num) {
        this.plate_num = plate_num;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setColl_address(String coll_address) {
        this.coll_address = coll_address;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setUnLimitTime(boolean unLimitTime) {
        isUnLimitTime = unLimitTime;
    }

    public void setPersonListJson(String personListJson) {
        this.personListJson = personListJson;
    }

    public void setPerson_list(List<DriverDetailInfo> person_list) {
        this.person_list = person_list;
    }

    public void setCarPhotoPath(String carPhotoPath) {
        this.carPhotoPath = carPhotoPath;
    }

    public void setCarPhotoId(String carPhotoId) {
        this.carPhotoId = carPhotoId;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public void setCar_file(FileModel car_file) {
        this.car_file = car_file;
    }

    public void setOther_file_list(List<FileModel> other_file_list) {
        this.other_file_list = other_file_list;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getPlate_num() {
        return plate_num;
    }

    public String getColor() {
        return color;
    }

    public String getColl_address() {
        return coll_address;
    }

    public String getRemark() {
        return remark;
    }

    public long getCreated_time() {
        return created_time;
    }

    public int getSourceType() {
        return sourceType;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public boolean isUnLimitTime() {
        return isUnLimitTime;
    }

    public String getPersonListJson() {
        return personListJson;
    }

    public List<DriverDetailInfo> getPerson_list() {
        return person_list;
    }

    public String getCarPhotoPath() {
        return carPhotoPath;
    }

    public String getCarPhotoId() {
        return carPhotoId;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }

    public FileModel getCar_file() {
        return car_file;
    }

    public List<FileModel> getOther_file_list() {
        return other_file_list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(plate_num);
        dest.writeString(color);
        dest.writeString(coll_address);
        dest.writeString(remark);
        dest.writeLong(created_time);
        dest.writeInt(sourceType);
        dest.writeInt(subTaskId);
        dest.writeByte((byte) (isUnLimitTime ? 1 : 0));
        dest.writeString(personListJson);
        dest.writeTypedList(person_list);
        dest.writeString(carPhotoPath);
        dest.writeString(carPhotoId);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
        dest.writeParcelable(car_file, flags);
        dest.writeTypedList(other_file_list);
    }
}
