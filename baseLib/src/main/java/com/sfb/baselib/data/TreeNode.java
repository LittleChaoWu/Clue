package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class TreeNode extends BaseBean implements Parcelable {

    private String id;//id
    private String userId;//userId
    private String name;//名称
    private int num;//分组人数，当num=0，不表示分组
    private boolean isSelect;//是否选中
    private boolean isOpen;//判断父级是否展开
    private boolean isParent;//判断是否是父级
    private String levels;//表示数据层级 Eg：1,2,1.....
    private List<TreeNode> children = new ArrayList<>();//子节点的数据

    public TreeNode() {
    }

    protected TreeNode(Parcel in) {
        id = in.readString();
        userId = in.readString();
        name = in.readString();
        num = in.readInt();
        isSelect = in.readByte() != 0;
        isOpen = in.readByte() != 0;
        levels = in.readString();
        children = in.createTypedArrayList(TreeNode.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeInt(num);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeByte((byte) (isOpen ? 1 : 0));
        dest.writeString(levels);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TreeNode> CREATOR = new Creator<TreeNode>() {
        @Override
        public TreeNode createFromParcel(Parcel in) {
            return new TreeNode(in);
        }

        @Override
        public TreeNode[] newArray(int size) {
            return new TreeNode[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isParent() {
        return isParent;
    }

    public String getLevels() {
        return levels;
    }

    public List<TreeNode> getChildren() {
        return children;
    }
}
