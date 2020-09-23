package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class PersonInfo extends BaseBean implements Parcelable {

    public static final String SEPARATOR = "_";

    public static final String INTEGRAL_SCORE = "1";    //个人总可用积分
    public static final String AVG_STAR = "2";  //平均星级
    public static final String NO_READ_MSG_COUNT = "3"; //未读消息数
    public static final String MILEAGE_COUNT = "4"; //巡逻里程数
    public static final String UNCHECKED_USER_COUNT = "5"; //未审核用户数量

    private int integral_score;//可用积分
    private double avg_star;//平均星级
    private int no_read_msg_count;//未读消息数
    private int mileage_count;//巡逻里程数
    private int unchecked_user_count;//未审核用户数量

    public int getIntegralScore() {
        return integral_score;
    }

    public double getAvgStar() {
        return avg_star;
    }

    public int getNoReadMsgCount() {
        return no_read_msg_count;
    }

    public int getMileageCount() {
        return mileage_count;
    }

    public int getUncheckedUserCount() {
        return unchecked_user_count;
    }

    public void setIntegralScore(int integral_score) {
        this.integral_score = integral_score;
    }

    public void setAvgStar(double avg_star) {
        this.avg_star = avg_star;
    }

    public void setNoReadMsgCount(int no_read_msg_count) {
        this.no_read_msg_count = no_read_msg_count;
    }

    public void setMileageCount(int mileage_count) {
        this.mileage_count = mileage_count;
    }

    public void setUncheckedUserCount(int unchecked_user_count) {
        this.unchecked_user_count = unchecked_user_count;
    }

    public PersonInfo() {
    }

    protected PersonInfo(Parcel in) {
        integral_score = in.readInt();
        avg_star = in.readDouble();
        no_read_msg_count = in.readInt();
        mileage_count = in.readInt();
        unchecked_user_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(integral_score);
        dest.writeDouble(avg_star);
        dest.writeInt(no_read_msg_count);
        dest.writeInt(mileage_count);
        dest.writeInt(unchecked_user_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonInfo> CREATOR = new Creator<PersonInfo>() {
        @Override
        public PersonInfo createFromParcel(Parcel in) {
            return new PersonInfo(in);
        }

        @Override
        public PersonInfo[] newArray(int size) {
            return new PersonInfo[size];
        }
    };
}
