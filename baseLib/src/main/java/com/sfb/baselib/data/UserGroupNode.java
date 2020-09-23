package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class UserGroupNode extends BaseBean implements Parcelable {

    private String id;
    private int userId;//用户id
    private String name;//分组名称
    private long createTime;//创建时间
    private int type;//类型
    private int num;//分组人数
    private String ownerShipUserIds;
    private int onlineCount;//在线人数
    private List<UserNode> children;//子节点的数据

    public UserGroupNode() {
    }

    protected UserGroupNode(Parcel in) {
        id = in.readString();
        userId = in.readInt();
        name = in.readString();
        createTime = in.readLong();
        type = in.readInt();
        num = in.readInt();
        ownerShipUserIds = in.readString();
        onlineCount = in.readInt();
        children = in.createTypedArrayList(UserNode.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeLong(createTime);
        dest.writeInt(type);
        dest.writeInt(num);
        dest.writeString(ownerShipUserIds);
        dest.writeInt(onlineCount);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserGroupNode> CREATOR = new Creator<UserGroupNode>() {
        @Override
        public UserGroupNode createFromParcel(Parcel in) {
            return new UserGroupNode(in);
        }

        @Override
        public UserGroupNode[] newArray(int size) {
            return new UserGroupNode[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setOwnerShipUserIds(String ownerShipUserIds) {
        this.ownerShipUserIds = ownerShipUserIds;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public void setChildren(List<UserNode> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getType() {
        return type;
    }

    public int getNum() {
        return num;
    }

    public String getOwnerShipUserIds() {
        return ownerShipUserIds;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public List<UserNode> getChildren() {
        return children;
    }
}
