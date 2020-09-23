package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class NoticeInfo extends BaseBean implements Parcelable {

    private int id;
    private String title;//标题
    private int isNotices;//是否是通知公告
    private int createUserId;//创建者id
    private String infType;//公告类型
    private long sendTime;//发送事件
    private long createTime;//创建时间
    private String summary;//文字内容
    private String contentType;//内容类型
    private String webUrl;//链接地址
    private int read;//是否已读 0-未读 1-已读
    private FileModel fileModel;//首页展示图
    //以下是发布通知公告才有的字段
    private String username;//用户名
    private String receiverUsers;//接收对象
    private String filePaths;//本地的附件路径
    private String fileIds;//附加的文件ids
    private FileModel fileModelPic;//首页图片信息，用于首页的轮播图展示

    public NoticeInfo() {
    }

    protected NoticeInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        isNotices = in.readInt();
        createUserId = in.readInt();
        infType = in.readString();
        sendTime = in.readLong();
        createTime = in.readLong();
        summary = in.readString();
        contentType = in.readString();
        webUrl = in.readString();
        read = in.readInt();
        fileModel = in.readParcelable(FileModel.class.getClassLoader());
        username = in.readString();
        receiverUsers = in.readString();
        filePaths = in.readString();
        fileIds = in.readString();
        fileModelPic = in.readParcelable(FileModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(isNotices);
        dest.writeInt(createUserId);
        dest.writeString(infType);
        dest.writeLong(sendTime);
        dest.writeLong(createTime);
        dest.writeString(summary);
        dest.writeString(contentType);
        dest.writeString(webUrl);
        dest.writeInt(read);
        dest.writeParcelable(fileModel, flags);
        dest.writeString(username);
        dest.writeString(receiverUsers);
        dest.writeString(filePaths);
        dest.writeString(fileIds);
        dest.writeParcelable(fileModelPic, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoticeInfo> CREATOR = new Creator<NoticeInfo>() {
        @Override
        public NoticeInfo createFromParcel(Parcel in) {
            return new NoticeInfo(in);
        }

        @Override
        public NoticeInfo[] newArray(int size) {
            return new NoticeInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsNotices() {
        return isNotices;
    }

    public void setIsNotices(int isNotices) {
        this.isNotices = isNotices;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getInfType() {
        return infType;
    }

    public void setInfType(String infType) {
        this.infType = infType;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiverUsers() {
        return receiverUsers;
    }

    public void setReceiverUsers(String receiverUsers) {
        this.receiverUsers = receiverUsers;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public FileModel getFileModelPic() {
        return fileModelPic;
    }

    public void setFileModelPic(FileModel fileModelPic) {
        this.fileModelPic = fileModelPic;
    }
}
