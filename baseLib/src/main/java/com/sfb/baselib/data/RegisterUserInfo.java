package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class RegisterUserInfo extends BaseBean {

    private int id;
    private String username;
    private int type;//用户身份，0--物建人， 1-群防人员
    private String card;
    private String realName;//真实姓名
    private int sex;
    private long createdTime;//创建时间
    private long updatedTime;
    private int status;
    private String residencet;//籍贯
    private String domicile;//经常居住地
    private String degree;//文化程度
    private String league;//政治面貌
    private String cardBack;  //身份证背面
    private String cardFront;//身份证正面
    private String cardPhoto;//个人近照
    private String workUnit;//工作单位
    private String telephone;//手机
    private String orgText;//注册地派出所
    private String sexText;//性别
    private String userGroupId;//角色id
    private String userGroupText;//角色名称
    private String checkStatus;//审核状态 0-待审核 1-审核通过 2-审核不通过
    private String checker;//审核人
    private long checkTime;//审核时间
    private String checkRemark;//不通过原因
    private String checkStatusText;//审核状态（文字）
    private String occupation;//职业
    private String referrer; //推荐人电话
    private String birthday; //生日 yyyy-MM-dd
    private String nation;//民族
    private String major; //专业
    private String specialty; //特长
    private String area; //所在地区
    private String resume; //主要简历
    private String activateStatusText; //激活状态
    private String leagueText;//政治面貌(文字)
    private String degreeText;//学历(文字)
    //义务交警队
    private String traffic;
    private String email;
    private String emergency;
    private String trafficType;
    private String subTraffic;
    //义务反扒队
    private String busStation;
    private String busRoute;
    private String steal;
    private String stealType;
    //背审信息
    private int ztStatus; //是否在逃，1:是  0:否
    private int wfStatus; //是否有前科，1:是  0:否
    private int idCheckStatus; //是否人证一致，1:是  0:否
    private String idCheckPhoto; //人口库base64照片
    private int idCheck; //人口库照片id
    private Integer pairResult;//是否同一人 0:是 !0:不同
    private String pairSimilarity;//相似度  x%的格式

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResidencet(String residencet) {
        this.residencet = residencet;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setOrgText(String orgText) {
        this.orgText = orgText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setUserGroupText(String userGroupText) {
        this.userGroupText = userGroupText;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public void setCheckStatusText(String checkStatusText) {
        this.checkStatusText = checkStatusText;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setActivateStatusText(String activateStatusText) {
        this.activateStatusText = activateStatusText;
    }

    public void setLeagueText(String leagueText) {
        this.leagueText = leagueText;
    }

    public void setDegreeText(String degreeText) {
        this.degreeText = degreeText;
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

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public void setSubTraffic(String subTraffic) {
        this.subTraffic = subTraffic;
    }

    public void setBusStation(String busStation) {
        this.busStation = busStation;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public void setSteal(String steal) {
        this.steal = steal;
    }

    public void setStealType(String stealType) {
        this.stealType = stealType;
    }

    public void setZtStatus(int ztStatus) {
        this.ztStatus = ztStatus;
    }

    public void setWfStatus(int wfStatus) {
        this.wfStatus = wfStatus;
    }

    public void setIdCheckStatus(int idCheckStatus) {
        this.idCheckStatus = idCheckStatus;
    }

    public void setIdCheckPhoto(String idCheckPhoto) {
        this.idCheckPhoto = idCheckPhoto;
    }

    public void setIdCheck(int idCheck) {
        this.idCheck = idCheck;
    }

    public void setPairResult(Integer pairResult) {
        this.pairResult = pairResult;
    }

    public void setPairSimilarity(String pairSimilarity) {
        this.pairSimilarity = pairSimilarity;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getType() {
        return type;
    }

    public String getCard() {
        return card;
    }

    public String getRealName() {
        return realName;
    }

    public int getSex() {
        return sex;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public int getStatus() {
        return status;
    }

    public String getResidencet() {
        return residencet;
    }

    public String getDomicile() {
        return domicile;
    }

    public String getDegree() {
        return degree;
    }

    public String getLeague() {
        return league;
    }

    public String getCardBack() {
        return cardBack;
    }

    public String getCardFront() {
        return cardFront;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getOrgText() {
        return orgText;
    }

    public String getSexText() {
        return sexText;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getUserGroupText() {
        return userGroupText;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public String getChecker() {
        return checker;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public String getCheckStatusText() {
        return checkStatusText;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNation() {
        return nation;
    }

    public String getMajor() {
        return major;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getArea() {
        return area;
    }

    public String getResume() {
        return resume;
    }

    public String getActivateStatusText() {
        return activateStatusText;
    }

    public String getLeagueText() {
        return leagueText;
    }

    public String getDegreeText() {
        return degreeText;
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

    public String getTrafficType() {
        return trafficType;
    }

    public String getSubTraffic() {
        return subTraffic;
    }

    public String getBusStation() {
        return busStation;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public String getSteal() {
        return steal;
    }

    public String getStealType() {
        return stealType;
    }

    public int getZtStatus() {
        return ztStatus;
    }

    public int getWfStatus() {
        return wfStatus;
    }

    public int getIdCheckStatus() {
        return idCheckStatus;
    }

    public String getIdCheckPhoto() {
        return idCheckPhoto;
    }

    public int getIdCheck() {
        return idCheck;
    }

    public Integer getPairResult() {
        return pairResult;
    }

    public String getPairSimilarity() {
        return pairSimilarity;
    }
}
