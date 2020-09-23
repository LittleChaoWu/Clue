package com.sfb.baselib.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.R;
import com.sfb.baselib.data.base.BaseBean;

public class GoodsInfo extends BaseBean implements Parcelable {

    private int type;
    private String pro_value;
    private String pro_name;
    private String remark;

    public GoodsInfo() {
    }

    /**
     * 获取物品类型文本
     *
     * @param context Context
     * @return String
     */
    public String getTypeText(Context context) {
        String typeText;
        switch (type) {
            case 1:
                typeText = context.getString(R.string.mobile_phone);
                break;
            case 2:
                typeText = context.getString(R.string.bank_card);
                break;
            case 3:
                typeText = context.getString(R.string.accompanying_bag);
                break;
            case 4:
                typeText = context.getString(R.string.computer);
                break;
            default:
                typeText = context.getString(R.string.mobile_phone);
                break;
        }
        return typeText;
    }

    protected GoodsInfo(Parcel in) {
        type = in.readInt();
        pro_value = in.readString();
        pro_name = in.readString();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(pro_value);
        dest.writeString(pro_name);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel in) {
            return new GoodsInfo(in);
        }

        @Override
        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };

    public void setType(int type) {
        this.type = type;
    }

    public void setPro_value(String pro_value) {
        this.pro_value = pro_value;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public String getPro_value() {
        return pro_value;
    }

    public String getPro_name() {
        return pro_name;
    }

    public String getRemark() {
        return remark;
    }
}
