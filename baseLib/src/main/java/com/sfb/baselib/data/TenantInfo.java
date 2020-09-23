package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class TenantInfo extends BaseBean implements Parcelable{

    private int tenantry_id;//承租人id
    private int hire_unit_id;//房屋房间ID
    private String certificate_type;//证件类型
    private String certificate_num;//证件号码
    private String real_name;//姓名
    private int sex;//性别
    private String native_place;//籍贯
    private String telephone;//手机号码
    private String work_unit;//工作单位
    private String remark;//备注
    private long check_in_time;//入住时间
    private int childNum;//小孩数量
    private String tenantryPath;//承租人照片路径
    private String cardPath;//身份证照片路径
    //上传的文件id
    private String tenantryFileId;//承租人照片文件id
    private String cardFileId;//身份证照片文件id
    //获取详情才会有
    private FileModel tenantry_file;//承租人照片ID
    private FileModel card_file;//身份证照片ID

    public void setTenantry_id(int tenantry_id) {
        this.tenantry_id = tenantry_id;
    }

    public void setHire_unit_id(int hire_unit_id) {
        this.hire_unit_id = hire_unit_id;
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

    public void setWork_unit(String work_unit) {
        this.work_unit = work_unit;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCheck_in_time(long check_in_time) {
        this.check_in_time = check_in_time;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public void setTenantryPath(String tenantryPath) {
        this.tenantryPath = tenantryPath;
    }

    public void setCardPath(String cardPath) {
        this.cardPath = cardPath;
    }

    public void setTenantryFileId(String tenantryFileId) {
        this.tenantryFileId = tenantryFileId;
    }

    public void setCardFileId(String cardFileId) {
        this.cardFileId = cardFileId;
    }

    public void setTenantry_file(FileModel tenantry_file) {
        this.tenantry_file = tenantry_file;
    }

    public void setCard_file(FileModel card_file) {
        this.card_file = card_file;
    }

    public int getTenantry_id() {

        return tenantry_id;
    }

    public int getHire_unit_id() {
        return hire_unit_id;
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

    public String getWork_unit() {
        return work_unit;
    }

    public String getRemark() {
        return remark;
    }

    public long getCheck_in_time() {
        return check_in_time;
    }

    public int getChildNum() {
        return childNum;
    }

    public String getTenantryPath() {
        return tenantryPath;
    }

    public String getCardPath() {
        return cardPath;
    }

    public String getTenantryFileId() {
        return tenantryFileId;
    }

    public String getCardFileId() {
        return cardFileId;
    }

    public FileModel getTenantry_file() {
        return tenantry_file;
    }

    public FileModel getCard_file() {
        return card_file;
    }

    public TenantInfo() {
    }

    protected TenantInfo(Parcel in) {
        tenantry_id = in.readInt();
        hire_unit_id = in.readInt();
        certificate_type = in.readString();
        certificate_num = in.readString();
        real_name = in.readString();
        sex = in.readInt();
        native_place = in.readString();
        telephone = in.readString();
        work_unit = in.readString();
        remark = in.readString();
        check_in_time = in.readLong();
        childNum = in.readInt();
        tenantryPath = in.readString();
        cardPath = in.readString();
        tenantryFileId = in.readString();
        cardFileId = in.readString();
        tenantry_file = in.readParcelable(FileModel.class.getClassLoader());
        card_file = in.readParcelable(FileModel.class.getClassLoader());
    }

    public static final Creator<TenantInfo> CREATOR = new Creator<TenantInfo>() {
        @Override
        public TenantInfo createFromParcel(Parcel in) {
            return new TenantInfo(in);
        }

        @Override
        public TenantInfo[] newArray(int size) {
            return new TenantInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tenantry_id);
        dest.writeInt(hire_unit_id);
        dest.writeString(certificate_type);
        dest.writeString(certificate_num);
        dest.writeString(real_name);
        dest.writeInt(sex);
        dest.writeString(native_place);
        dest.writeString(telephone);
        dest.writeString(work_unit);
        dest.writeString(remark);
        dest.writeLong(check_in_time);
        dest.writeInt(childNum);
        dest.writeString(tenantryPath);
        dest.writeString(cardPath);
        dest.writeString(tenantryFileId);
        dest.writeString(cardFileId);
        dest.writeParcelable(tenantry_file, flags);
        dest.writeParcelable(card_file, flags);
    }
}
