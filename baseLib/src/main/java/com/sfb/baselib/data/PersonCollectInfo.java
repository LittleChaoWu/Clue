package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class PersonCollectInfo extends BaseBean implements Parcelable {

    private int id;//id
    private String certificate_type;//证件类型
    private String certificate_num;//被采集人员身份证号
    private String real_name;//被采集人员姓名
    private int sex;//性别
    private String native_place;//被采集人员籍贯地址
    private String telephone; //被采集人员手机号
    private String resident;//现居住地
    private String remark;//内容描述
    private String coll_address;//采集地址 json  （lon lat address）
    private long created_time;//采集时间（long）
    private String content;//内容描述
    private String disposeResultJson;//人员处理结果json
    private DisposeResultInfo dispose_result;//人员处理
    private String goodsListJson;//随身物品json
    private List<GoodsInfo> goods_list;//随身物品集合
    private String cardPhotoPath;//车辆照片文件路径（本地用）
    private String cardPhotoId;//车辆照片文件路径（本地上传时使用）
    private String filePaths;//本地用
    private String fileIds;//本地上传时使用
    private int sourceType;//采集来源（0--普通采集，1--来自任务）
    private int subTaskId;//子任务id
    private List<FileModel> other_file_list;//采集记录文件的FileModel
    private FileModel certificate_file;//证件照片的FileModel

    public PersonCollectInfo() {
    }

    protected PersonCollectInfo(Parcel in) {
        id = in.readInt();
        certificate_type = in.readString();
        certificate_num = in.readString();
        real_name = in.readString();
        sex = in.readInt();
        native_place = in.readString();
        telephone = in.readString();
        resident = in.readString();
        remark = in.readString();
        coll_address = in.readString();
        created_time = in.readLong();
        content = in.readString();
        disposeResultJson = in.readString();
        goodsListJson = in.readString();
        goods_list = in.createTypedArrayList(GoodsInfo.CREATOR);
        cardPhotoPath = in.readString();
        cardPhotoId = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
        sourceType = in.readInt();
        subTaskId = in.readInt();
        other_file_list = in.createTypedArrayList(FileModel.CREATOR);
        certificate_file = in.readParcelable(FileModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(certificate_type);
        dest.writeString(certificate_num);
        dest.writeString(real_name);
        dest.writeInt(sex);
        dest.writeString(native_place);
        dest.writeString(telephone);
        dest.writeString(resident);
        dest.writeString(remark);
        dest.writeString(coll_address);
        dest.writeLong(created_time);
        dest.writeString(content);
        dest.writeString(disposeResultJson);
        dest.writeString(goodsListJson);
        dest.writeTypedList(goods_list);
        dest.writeString(cardPhotoPath);
        dest.writeString(cardPhotoId);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
        dest.writeInt(sourceType);
        dest.writeInt(subTaskId);
        dest.writeTypedList(other_file_list);
        dest.writeParcelable(certificate_file, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonCollectInfo> CREATOR = new Creator<PersonCollectInfo>() {
        @Override
        public PersonCollectInfo createFromParcel(Parcel in) {
            return new PersonCollectInfo(in);
        }

        @Override
        public PersonCollectInfo[] newArray(int size) {
            return new PersonCollectInfo[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public void setCertificate_num(String certificate_num) {
        this.certificate_num = certificate_num;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setColl_address(String coll_address) {
        this.coll_address = coll_address;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDisposeResultJson(String disposeResultJson) {
        this.disposeResultJson = disposeResultJson;
    }

    public void setDispose_result(DisposeResultInfo dispose_result) {
        this.dispose_result = dispose_result;
    }

    public void setGoodsListJson(String goodsListJson) {
        this.goodsListJson = goodsListJson;
    }

    public void setGoods_list(List<GoodsInfo> goods_list) {
        this.goods_list = goods_list;
    }

    public void setCardPhotoPath(String cardPhotoPath) {
        this.cardPhotoPath = cardPhotoPath;
    }

    public void setCardPhotoId(String cardPhotoId) {
        this.cardPhotoId = cardPhotoId;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setOther_file_list(List<FileModel> other_file_list) {
        this.other_file_list = other_file_list;
    }

    public void setCertificate_file(FileModel certificate_file) {
        this.certificate_file = certificate_file;
    }

    public int getId() {
        return id;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public String getCertificate_num() {
        return certificate_num;
    }

    public String getReal_name() {
        return real_name;
    }

    public int getSex() {
        return sex;
    }

    public String getNative_place() {
        return native_place;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getResident() {
        return resident;
    }

    public String getRemark() {
        return remark;
    }

    public String getColl_address() {
        return coll_address;
    }

    public long getCreated_time() {
        return created_time;
    }

    public String getContent() {
        return content;
    }

    public String getDisposeResultJson() {
        return disposeResultJson;
    }

    public DisposeResultInfo getDispose_result() {
        return dispose_result;
    }

    public String getGoodsListJson() {
        return goodsListJson;
    }

    public List<GoodsInfo> getGoods_list() {
        return goods_list;
    }

    public String getCardPhotoPath() {
        return cardPhotoPath;
    }

    public String getCardPhotoId() {
        return cardPhotoId;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }

    public int getSourceType() {
        return sourceType;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public List<FileModel> getOther_file_list() {
        return other_file_list;
    }

    public FileModel getCertificate_file() {
        return certificate_file;
    }
}
