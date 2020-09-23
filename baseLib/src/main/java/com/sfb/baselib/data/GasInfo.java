package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

public class GasInfo extends BaseBean implements Parcelable {

    private int id;//采集汽油id
    private int checkStatus;//审核状态 0-待审核 1-审核通过 2-审核不通过
    private String remark;//说明核查不通过的原因
    private String name;//采集人名字
    private String card;//身份证号
    private int sex;//性别
    private String company;//购买单位
    private float litre;//购买量
    private long collTime;//采集时间
    private String policeStation;//派出所id
    private String gasStation;//加油站名称
    private String checker;//审核人
    private long check_time;//审核时间
    private String userFilePath;//手持身份证照片路径
    private String cardFilePath;//身份证照片路径
    private String letterFilePath;//介绍信照片路径
    private String userFileId;//申请人照片id
    private String cardFileId;//身份证照片id
    private String letterFileId;//介绍信照片id
    private FileModel user_file;//申请人照片id(获取详情时的数据解析)
    private FileModel card_file;//身份证照片id(获取详情时的数据解析)
    private FileModel letter_file;//介绍信照片id(获取详情时的数据解析)

    public GasInfo() {
    }

    protected GasInfo(Parcel in) {
        id = in.readInt();
        checkStatus = in.readInt();
        remark = in.readString();
        name = in.readString();
        card = in.readString();
        sex = in.readInt();
        company = in.readString();
        litre = in.readFloat();
        collTime = in.readLong();
        policeStation = in.readString();
        gasStation = in.readString();
        checker = in.readString();
        check_time = in.readLong();
        userFilePath = in.readString();
        cardFilePath = in.readString();
        letterFilePath = in.readString();
        userFileId = in.readString();
        cardFileId = in.readString();
        letterFileId = in.readString();
        user_file = in.readParcelable(FileModel.class.getClassLoader());
        card_file = in.readParcelable(FileModel.class.getClassLoader());
        letter_file = in.readParcelable(FileModel.class.getClassLoader());
    }

    public static final Creator<GasInfo> CREATOR = new Creator<GasInfo>() {
        @Override
        public GasInfo createFromParcel(Parcel in) {
            return new GasInfo(in);
        }

        @Override
        public GasInfo[] newArray(int size) {
            return new GasInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(checkStatus);
        dest.writeString(remark);
        dest.writeString(name);
        dest.writeString(card);
        dest.writeInt(sex);
        dest.writeString(company);
        dest.writeFloat(litre);
        dest.writeLong(collTime);
        dest.writeString(policeStation);
        dest.writeString(gasStation);
        dest.writeString(checker);
        dest.writeLong(check_time);
        dest.writeString(userFilePath);
        dest.writeString(cardFilePath);
        dest.writeString(letterFilePath);
        dest.writeString(userFileId);
        dest.writeString(cardFileId);
        dest.writeString(letterFileId);
        dest.writeParcelable(user_file, flags);
        dest.writeParcelable(card_file, flags);
        dest.writeParcelable(letter_file, flags);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLitre(float litre) {
        this.litre = litre;
    }

    public void setCollTime(long collTime) {
        this.collTime = collTime;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public void setCheck_time(long check_time) {
        this.check_time = check_time;
    }

    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    public void setCardFilePath(String cardFilePath) {
        this.cardFilePath = cardFilePath;
    }

    public void setLetterFilePath(String letterFilePath) {
        this.letterFilePath = letterFilePath;
    }

    public void setUserFileId(String userFileId) {
        this.userFileId = userFileId;
    }

    public void setCardFileId(String cardFileId) {
        this.cardFileId = cardFileId;
    }

    public void setLetterFileId(String letterFileId) {
        this.letterFileId = letterFileId;
    }

    public void setUser_file(FileModel user_file) {
        this.user_file = user_file;
    }

    public void setCard_file(FileModel card_file) {
        this.card_file = card_file;
    }

    public void setLetter_file(FileModel letter_file) {
        this.letter_file = letter_file;
    }

    public int getId() {
        return id;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public String getName() {
        return name;
    }

    public String getCard() {
        return card;
    }

    public int getSex() {
        return sex;
    }

    public String getCompany() {
        return company;
    }

    public float getLitre() {
        return litre;
    }

    public long getCollTime() {
        return collTime;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public String getGasStation() {
        return gasStation;
    }

    public String getChecker() {
        return checker;
    }

    public long getCheck_time() {
        return check_time;
    }

    public String getUserFilePath() {
        return userFilePath;
    }

    public String getCardFilePath() {
        return cardFilePath;
    }

    public String getLetterFilePath() {
        return letterFilePath;
    }

    public String getUserFileId() {
        return userFileId;
    }

    public String getCardFileId() {
        return cardFileId;
    }

    public String getLetterFileId() {
        return letterFileId;
    }

    public FileModel getUser_file() {
        return user_file;
    }

    public FileModel getCard_file() {
        return card_file;
    }

    public FileModel getLetter_file() {
        return letter_file;
    }
}
