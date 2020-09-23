package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 群组信息
 */
public class UserGroup extends BaseBean implements Parcelable {

    private String code;//角色code
    private int status;//状态 0:未审核 1:审核通过 2:审核不通过
    private String groupName;//角色分组名
    private String remark;//审核不通过原因

    //义务交警队才有字段
    private  String traffic;//义务交警队
    private  String trafficCode;
    private   String email;
    private  String emergency;//紧急联系人电话
    //义务反扒队才有字段
    private   String busStation;
    private   String busRoute;
    //信息申报义务人
    private   String workUnitName;
    private  String workUnitAddr;
    private  int addressType;
    private String workUnitAddrCode;
    //汽油管理员才有字段
    private String gasStationCode;
    private String gasStationName;

    public UserGroup() {
    }

    protected UserGroup(Parcel in) {
        code = in.readString();
        status = in.readInt();
        groupName = in.readString();
        remark = in.readString();
        traffic = in.readString();
        trafficCode = in.readString();
        email = in.readString();
        emergency = in.readString();
        busStation = in.readString();
        busRoute = in.readString();
        workUnitName = in.readString();
        workUnitAddr = in.readString();
        addressType = in.readInt();
        workUnitAddrCode = in.readString();
        gasStationCode = in.readString();
        gasStationName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeInt(status);
        dest.writeString(groupName);
        dest.writeString(remark);
        dest.writeString(traffic);
        dest.writeString(trafficCode);
        dest.writeString(email);
        dest.writeString(emergency);
        dest.writeString(busStation);
        dest.writeString(busRoute);
        dest.writeString(workUnitName);
        dest.writeString(workUnitAddr);
        dest.writeInt(addressType);
        dest.writeString(workUnitAddrCode);
        dest.writeString(gasStationCode);
        dest.writeString(gasStationName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserGroup> CREATOR = new Creator<UserGroup>() {
        @Override
        public UserGroup createFromParcel(Parcel in) {
            return new UserGroup(in);
        }

        @Override
        public UserGroup[] newArray(int size) {
            return new UserGroup[size];
        }
    };

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public void setTrafficCode(String trafficCode) {
        this.trafficCode = trafficCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public void setBusStation(String busStation) {
        this.busStation = busStation;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public void setWorkUnitAddr(String workUnitAddr) {
        this.workUnitAddr = workUnitAddr;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public void setWorkUnitAddrCode(String workUnitAddrCode) {
        this.workUnitAddrCode = workUnitAddrCode;
    }

    public void setGasStationCode(String gasStationCode) {
        this.gasStationCode = gasStationCode;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getRemark() {
        return remark;
    }

    public String getTraffic() {
        return traffic;
    }

    public String getTrafficCode() {
        return trafficCode;
    }

    public String getEmail() {
        return email;
    }

    public String getEmergency() {
        return emergency;
    }

    public String getBusStation() {
        return busStation;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public String getWorkUnitAddr() {
        return workUnitAddr;
    }

    public int getAddressType() {
        return addressType;
    }

    public String getWorkUnitAddrCode() {
        return workUnitAddrCode;
    }

    public String getGasStationCode() {
        return gasStationCode;
    }

    public String getGasStationName() {
        return gasStationName;
    }
}
