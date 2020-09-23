package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class UserNode extends BaseBean implements Parcelable {

    private String id;
    private int userId;//用户id
    private int userCustomGroupId;//自定义分组的id
    private long createTime;//创建时间
    private String realName;//真实姓名
    private String userGroupText;//所在组群名称
    private String policeCode;//警号
    private String telephone;//手机号
    private String orgName;//所在派出所
    private boolean hasSharedGroup;//是否分享过分组
    private boolean isSelect;//是否选中

    public UserNode() {
    }

    protected UserNode(Parcel in) {
        id = in.readString();
        userId = in.readInt();
        userCustomGroupId = in.readInt();
        createTime = in.readLong();
        realName = in.readString();
        userGroupText = in.readString();
        policeCode = in.readString();
        telephone = in.readString();
        orgName = in.readString();
        hasSharedGroup = in.readByte() != 0;
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(userId);
        dest.writeInt(userCustomGroupId);
        dest.writeLong(createTime);
        dest.writeString(realName);
        dest.writeString(userGroupText);
        dest.writeString(policeCode);
        dest.writeString(telephone);
        dest.writeString(orgName);
        dest.writeByte((byte) (hasSharedGroup ? 1 : 0));
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserNode> CREATOR = new Creator<UserNode>() {
        @Override
        public UserNode createFromParcel(Parcel in) {
            return new UserNode(in);
        }

        @Override
        public UserNode[] newArray(int size) {
            return new UserNode[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserCustomGroupId(int userCustomGroupId) {
        this.userCustomGroupId = userCustomGroupId;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUserGroupText(String userGroupText) {
        this.userGroupText = userGroupText;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setHasSharedGroup(boolean hasSharedGroup) {
        this.hasSharedGroup = hasSharedGroup;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserCustomGroupId() {
        return userCustomGroupId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getRealName() {
        return realName;
    }

    public String getUserGroupText() {
        return userGroupText;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getOrgName() {
        return orgName;
    }

    public boolean isHasSharedGroup() {
        return hasSharedGroup;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
