package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

/**
 * 用户信息
 */
public class UserInfo extends BaseBean implements Parcelable {

    private String id;//user id
    private String token;//token
    private String username;//用户名
    private String telephone;//电话
    private String pcsCode;//所属派出所Code
    private String policeCode;//警号
    private int type;//用户身份，0--物建人， 1-群防人员
    private String card;
    private String realName;
    private String sex;

    private String referrer; //推荐人电话
    private String birthday; //生日 yyyy-MM-dd
    private String area; //所在地区
    private String areaCode; //所在地区
    private String police;//所属派出所
    private String policeId;//所属派出所id
    private String workUnit;//单位及职务
    private int noReads;//未读消息
    private List<UserGroup> userGroups;
    private String totalIntegral;//总积分
    private long createdTime;
    private int uncheckedCount;//未审核注册人数
    private long mileageCount;//里程数
    private boolean resetPwd;//是否需要修改密码
    private boolean integrity;//资料完整性
    private boolean telStatus; //民警是否需要确认手机号码
    private boolean firstLogin;//是否第一次显示注册结果反馈界面
    private int logo;

    private String cardBack;  //身份证背面
    private String cardFront;//身份证正面
    private String cardPhoto;//个人近照

    private String cardBackOpenKey;
    private String cardFrontOpenKey;
    private String cardPhotoOpenKey;

    private int authType;//认证类型 0---正常二代身份证检测，1--在线人像检测
    private int authResult;//活体检测通过与否的结果 0--成功 1--失败

    private int source;//用户身份，用于区分民警还是保安管理员，5:-保安管理员，！5--民警

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        id = in.readString();
        token = in.readString();
        username = in.readString();
        telephone = in.readString();
        pcsCode = in.readString();
        policeCode = in.readString();
        type = in.readInt();
        card = in.readString();
        realName = in.readString();
        sex = in.readString();
        referrer = in.readString();
        birthday = in.readString();
        area = in.readString();
        areaCode = in.readString();
        police = in.readString();
        policeId = in.readString();
        workUnit = in.readString();
        noReads = in.readInt();
        userGroups = in.createTypedArrayList(UserGroup.CREATOR);
        totalIntegral = in.readString();
        createdTime = in.readLong();
        uncheckedCount = in.readInt();
        mileageCount = in.readLong();
        resetPwd = in.readByte() != 0;
        integrity = in.readByte() != 0;
        telStatus = in.readByte() != 0;
        firstLogin = in.readByte() != 0;
        logo = in.readInt();
        cardBack = in.readString();
        cardFront = in.readString();
        cardPhoto = in.readString();
        cardBackOpenKey = in.readString();
        cardFrontOpenKey = in.readString();
        cardPhotoOpenKey = in.readString();
        authType = in.readInt();
        authResult = in.readInt();
        source = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(token);
        dest.writeString(username);
        dest.writeString(telephone);
        dest.writeString(pcsCode);
        dest.writeString(policeCode);
        dest.writeInt(type);
        dest.writeString(card);
        dest.writeString(realName);
        dest.writeString(sex);
        dest.writeString(referrer);
        dest.writeString(birthday);
        dest.writeString(area);
        dest.writeString(areaCode);
        dest.writeString(police);
        dest.writeString(policeId);
        dest.writeString(workUnit);
        dest.writeInt(noReads);
        dest.writeTypedList(userGroups);
        dest.writeString(totalIntegral);
        dest.writeLong(createdTime);
        dest.writeInt(uncheckedCount);
        dest.writeLong(mileageCount);
        dest.writeByte((byte) (resetPwd ? 1 : 0));
        dest.writeByte((byte) (integrity ? 1 : 0));
        dest.writeByte((byte) (telStatus ? 1 : 0));
        dest.writeByte((byte) (firstLogin ? 1 : 0));
        dest.writeInt(logo);
        dest.writeString(cardBack);
        dest.writeString(cardFront);
        dest.writeString(cardPhoto);
        dest.writeString(cardBackOpenKey);
        dest.writeString(cardFrontOpenKey);
        dest.writeString(cardPhotoOpenKey);
        dest.writeInt(authType);
        dest.writeInt(authResult);
        dest.writeInt(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPcsCode() {
        return pcsCode;
    }

    public void setPcsCode(String pcsCode) {
        this.pcsCode = pcsCode;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public int getNoReads() {
        return noReads;
    }

    public void setNoReads(int noReads) {
        this.noReads = noReads;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public String getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(String totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getUncheckedCount() {
        return uncheckedCount;
    }

    public void setUncheckedCount(int uncheckedCount) {
        this.uncheckedCount = uncheckedCount;
    }

    public long getMileageCount() {
        return mileageCount;
    }

    public void setMileageCount(long mileageCount) {
        this.mileageCount = mileageCount;
    }

    public boolean isResetPwd() {
        return resetPwd;
    }

    public void setResetPwd(boolean resetPwd) {
        this.resetPwd = resetPwd;
    }

    public boolean isIntegrity() {
        return integrity;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }

    public boolean isTelStatus() {
        return telStatus;
    }

    public void setTelStatus(boolean telStatus) {
        this.telStatus = telStatus;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getCardBackOpenKey() {
        return cardBackOpenKey;
    }

    public void setCardBackOpenKey(String cardBackOpenKey) {
        this.cardBackOpenKey = cardBackOpenKey;
    }

    public String getCardFrontOpenKey() {
        return cardFrontOpenKey;
    }

    public void setCardFrontOpenKey(String cardFrontOpenKey) {
        this.cardFrontOpenKey = cardFrontOpenKey;
    }

    public String getCardPhotoOpenKey() {
        return cardPhotoOpenKey;
    }

    public void setCardPhotoOpenKey(String cardPhotoOpenKey) {
        this.cardPhotoOpenKey = cardPhotoOpenKey;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getAuthResult() {
        return authResult;
    }

    public void setAuthResult(int authResult) {
        this.authResult = authResult;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
