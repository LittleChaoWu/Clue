package com.sfb.baselib.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 注册时传递的参数
 *
 * @author zhangy
 */
public class RegisterInfo extends BaseBean implements Parcelable {

    private String realName;
    private String username;
    private String card;//身份证号码
    private String sex;//0--男 1--女
    private String cardBack;//反面照id
    private String cardPhoto;//脸部照id
    private String cardFront;//正面照id
    private String birthday;//生日,yyyy-MM-dd
    private String nation;//民族
    private String residencet;//籍贯
    private String degree;//文化程度
    private String major;//专业
    private String league;//政治面貌
    private String occupation;//职业
    private String workUnit;//单位及职务
    private String referrer;//推荐人手机号
    private String specialty;//特长
    private String resume;//主要简历
    private String area;//所在地区
    private String police;//派出所
    private String gasStationCode;//加油站
    private String domicile;//经常居住地

    private String telephone;
    private String pwd;
    private String code;
    private String userGroups;//群防群治力量类型，多个以逗号分隔

    private String busStation;//公交站点
    private String busRoute;//公交线路

    private String traffic;//义务交警队
    private String email;//邮箱
    private String emergency;//紧急联系电话

    private String workUnitName;  //单位名称
    private String workUnitAddress;  //单位地址
    private String workUnitAddrCode; //单位地址编码
    private int addressType;//地址类型

    private String idcards;//身份证照片路径集
    private String frontIccardPath;//正面照
    private String backIccardPath;//反面照
    private String faceIccardPath;//脸部照

    private boolean registerAgain;//是否重新提交注册
    private int authType;//认证类型 0---正常二代身份证检测，1--在线人像检测
    private int authResult;//活体检测通过与否的结果 0--成功 1--失败
    private String departmentOrgKey;//科室KEY

    public RegisterInfo() {
    }

    protected RegisterInfo(Parcel in) {
        realName = in.readString();
        username = in.readString();
        card = in.readString();
        sex = in.readString();
        cardBack = in.readString();
        cardPhoto = in.readString();
        cardFront = in.readString();
        birthday = in.readString();
        nation = in.readString();
        residencet = in.readString();
        degree = in.readString();
        major = in.readString();
        league = in.readString();
        occupation = in.readString();
        workUnit = in.readString();
        referrer = in.readString();
        specialty = in.readString();
        resume = in.readString();
        area = in.readString();
        police = in.readString();
        gasStationCode = in.readString();
        domicile = in.readString();
        telephone = in.readString();
        pwd = in.readString();
        code = in.readString();
        userGroups = in.readString();
        busStation = in.readString();
        busRoute = in.readString();
        traffic = in.readString();
        email = in.readString();
        emergency = in.readString();
        workUnitName = in.readString();
        workUnitAddress = in.readString();
        workUnitAddrCode = in.readString();
        addressType = in.readInt();
        idcards = in.readString();
        frontIccardPath = in.readString();
        backIccardPath = in.readString();
        faceIccardPath = in.readString();
        registerAgain = in.readByte() != 0;
        authType = in.readInt();
        authResult = in.readInt();
        departmentOrgKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(realName);
        dest.writeString(username);
        dest.writeString(card);
        dest.writeString(sex);
        dest.writeString(cardBack);
        dest.writeString(cardPhoto);
        dest.writeString(cardFront);
        dest.writeString(birthday);
        dest.writeString(nation);
        dest.writeString(residencet);
        dest.writeString(degree);
        dest.writeString(major);
        dest.writeString(league);
        dest.writeString(occupation);
        dest.writeString(workUnit);
        dest.writeString(referrer);
        dest.writeString(specialty);
        dest.writeString(resume);
        dest.writeString(area);
        dest.writeString(police);
        dest.writeString(gasStationCode);
        dest.writeString(domicile);
        dest.writeString(telephone);
        dest.writeString(pwd);
        dest.writeString(code);
        dest.writeString(userGroups);
        dest.writeString(busStation);
        dest.writeString(busRoute);
        dest.writeString(traffic);
        dest.writeString(email);
        dest.writeString(emergency);
        dest.writeString(workUnitName);
        dest.writeString(workUnitAddress);
        dest.writeString(workUnitAddrCode);
        dest.writeInt(addressType);
        dest.writeString(idcards);
        dest.writeString(frontIccardPath);
        dest.writeString(backIccardPath);
        dest.writeString(faceIccardPath);
        dest.writeByte((byte) (registerAgain ? 1 : 0));
        dest.writeInt(authType);
        dest.writeInt(authResult);
        dest.writeString(departmentOrgKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterInfo> CREATOR = new Creator<RegisterInfo>() {
        @Override
        public RegisterInfo createFromParcel(Parcel in) {
            return new RegisterInfo(in);
        }

        @Override
        public RegisterInfo[] newArray(int size) {
            return new RegisterInfo[size];
        }
    };

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setResidencet(String residencet) {
        this.residencet = residencet;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public void setGasStationCode(String gasStationCode) {
        this.gasStationCode = gasStationCode;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserGroups(String userGroups) {
        this.userGroups = userGroups;
    }

    public void setBusStation(String busStation) {
        this.busStation = busStation;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public void setWorkUnitAddress(String workUnitAddress) {
        this.workUnitAddress = workUnitAddress;
    }

    public void setWorkUnitAddrCode(String workUnitAddrCode) {
        this.workUnitAddrCode = workUnitAddrCode;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public void setIdcards(String idcards) {
        this.idcards = idcards;
    }

    public void setFrontIccardPath(String frontIccardPath) {
        this.frontIccardPath = frontIccardPath;
    }

    public void setBackIccardPath(String backIccardPath) {
        this.backIccardPath = backIccardPath;
    }

    public void setFaceIccardPath(String faceIccardPath) {
        this.faceIccardPath = faceIccardPath;
    }

    public void setRegisterAgain(boolean registerAgain) {
        this.registerAgain = registerAgain;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public void setAuthResult(int authResult) {
        this.authResult = authResult;
    }

    public void setDepartmentOrgKey(String departmentOrgKey) {
        this.departmentOrgKey = departmentOrgKey;
    }

    public String getRealName() {
        return realName;
    }

    public String getUsername() {
        return username;
    }

    public String getCard() {
        return card;
    }

    public String getSex() {
        return sex;
    }

    public String getCardBack() {
        return cardBack;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public String getCardFront() {
        return cardFront;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNation() {
        return nation;
    }

    public String getResidencet() {
        return residencet;
    }

    public String getDegree() {
        return degree;
    }

    public String getMajor() {
        return major;
    }

    public String getLeague() {
        return league;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getResume() {
        return resume;
    }

    public String getArea() {
        return area;
    }

    public String getPolice() {
        return police;
    }

    public String getGasStationCode() {
        return gasStationCode;
    }

    public String getDomicile() {
        return domicile;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPwd() {
        return pwd;
    }

    public String getCode() {
        return code;
    }

    public String getUserGroups() {
        return userGroups;
    }

    public String getBusStation() {
        return busStation;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public String getTraffic() {
        return traffic;
    }

    public String getEmail() {
        return email;
    }

    public String getEmergency() {
        return emergency;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public String getWorkUnitAddress() {
        return workUnitAddress;
    }

    public String getWorkUnitAddrCode() {
        return workUnitAddrCode;
    }

    public int getAddressType() {
        return addressType;
    }

    public String getIdcards() {
        return idcards;
    }

    public String getFrontIccardPath() {
        return frontIccardPath;
    }

    public String getBackIccardPath() {
        return backIccardPath;
    }

    public String getFaceIccardPath() {
        return faceIccardPath;
    }

    public boolean isRegisterAgain() {
        return registerAgain;
    }

    public int getAuthType() {
        return authType;
    }

    public int getAuthResult() {
        return authResult;
    }

    public String getDepartmentOrgKey() {
        return departmentOrgKey;
    }
}
