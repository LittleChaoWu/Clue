package com.sfb.baselib.components.bus.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.Map;

/**
 * Created by wuwc on 2020/9/17.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 */

public class SdkInitEvent extends BaseBean implements Parcelable {
    private Map<String, String> bundle;
    private int resultCode = 1;//1:正常,0:失败
    private String errorMsg;
    private long response_time;

    public SdkInitEvent(Map<String, String> bundle, long response_time) {
        this.bundle = bundle;
        this.response_time = response_time;
    }

    public SdkInitEvent(int resultCode, String errorMsg) {
        this.resultCode = resultCode;
        this.errorMsg = errorMsg;
    }

    protected SdkInitEvent(Parcel in) {
        resultCode = in.readInt();
        errorMsg = in.readString();
        response_time = in.readLong();
    }

    public static final Creator<SdkInitEvent> CREATOR = new Creator<SdkInitEvent>() {
        @Override
        public SdkInitEvent createFromParcel(Parcel in) {
            return new SdkInitEvent(in);
        }

        @Override
        public SdkInitEvent[] newArray(int size) {
            return new SdkInitEvent[size];
        }
    };

    public Map<String, String> getBundle() {
        return bundle;
    }

    public void setBundle(Map<String, String> bundle) {
        this.bundle = bundle;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultCode);
        dest.writeString(errorMsg);
        dest.writeLong(response_time);
    }
}
