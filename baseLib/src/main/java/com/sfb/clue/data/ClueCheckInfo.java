package com.sfb.clue.data;

import com.sfb.baselib.data.base.BaseBean;

public class ClueCheckInfo extends BaseBean {

    private int id;
    private int clueId;
    private int clueStatus;
    private String dealUserCode;
    private String dealUserType;
    private long dealTime;
    private String dealContent;
    private long createdTime;
    private String creator;
    private String remark;
    private String clueBigType;
    private String clueBigTypeName;
    private String clueSmallType;
    private String clueSmallTypeName;
    private String clueMiniType;
    private String clueMiniTypeName;

    public void setId(int id) {
        this.id = id;
    }

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setClueStatus(int clueStatus) {
        this.clueStatus = clueStatus;
    }

    public void setDealUserCode(String dealUserCode) {
        this.dealUserCode = dealUserCode;
    }

    public void setDealUserType(String dealUserType) {
        this.dealUserType = dealUserType;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setClueBigType(String clueBigType) {
        this.clueBigType = clueBigType;
    }

    public void setClueBigTypeName(String clueBigTypeName) {
        this.clueBigTypeName = clueBigTypeName;
    }

    public void setClueSmallType(String clueSmallType) {
        this.clueSmallType = clueSmallType;
    }

    public void setClueSmallTypeName(String clueSmallTypeName) {
        this.clueSmallTypeName = clueSmallTypeName;
    }

    public void setClueMiniType(String clueMiniType) {
        this.clueMiniType = clueMiniType;
    }

    public void setClueMiniTypeName(String clueMiniTypeName) {
        this.clueMiniTypeName = clueMiniTypeName;
    }

    public int getId() {

        return id;
    }

    public int getClueId() {
        return clueId;
    }

    public int getClueStatus() {
        return clueStatus;
    }

    public String getDealUserCode() {
        return dealUserCode;
    }

    public String getDealUserType() {
        return dealUserType;
    }

    public long getDealTime() {
        return dealTime;
    }

    public String getDealContent() {
        return dealContent;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getCreator() {
        return creator;
    }

    public String getRemark() {
        return remark;
    }

    public String getClueBigType() {
        return clueBigType;
    }

    public String getClueBigTypeName() {
        return clueBigTypeName;
    }

    public String getClueSmallType() {
        return clueSmallType;
    }

    public String getClueSmallTypeName() {
        return clueSmallTypeName;
    }

    public String getClueMiniType() {
        return clueMiniType;
    }

    public String getClueMiniTypeName() {
        return clueMiniTypeName;
    }
}
