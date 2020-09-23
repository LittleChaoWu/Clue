package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class DriverDetailInfo extends BaseBean implements Parcelable {

    private int type;//1-驾驶员 2-随行人员
    private String real_name;
    private int sex;
    private String certificate_type;
    private String certificate_num;
    private String telephone;
    private String remark;
    private List<GoodsInfo> goods_list; //人员随行物品json

    public DriverDetailInfo() {
    }

    protected DriverDetailInfo(Parcel in) {
        type = in.readInt();
        real_name = in.readString();
        sex = in.readInt();
        certificate_type = in.readString();
        certificate_num = in.readString();
        telephone = in.readString();
        remark = in.readString();
        goods_list = in.createTypedArrayList(GoodsInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(real_name);
        dest.writeInt(sex);
        dest.writeString(certificate_type);
        dest.writeString(certificate_num);
        dest.writeString(telephone);
        dest.writeString(remark);
        dest.writeTypedList(goods_list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DriverDetailInfo> CREATOR = new Creator<DriverDetailInfo>() {
        @Override
        public DriverDetailInfo createFromParcel(Parcel in) {
            return new DriverDetailInfo(in);
        }

        @Override
        public DriverDetailInfo[] newArray(int size) {
            return new DriverDetailInfo[size];
        }
    };

    public void setType(int type) {
        this.type = type;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public void setCertificate_num(String certificate_num) {
        this.certificate_num = certificate_num;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setGoods_list(List<GoodsInfo> goods_list) {
        this.goods_list = goods_list;
    }

    public int getType() {
        return type;
    }

    public String getReal_name() {
        return real_name;
    }

    public int getSex() {
        return sex;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public String getCertificate_num() {
        return certificate_num;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getRemark() {
        return remark;
    }

    public List<GoodsInfo> getGoods_list() {
        return goods_list;
    }
}
