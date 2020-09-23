package com.sfb.clue.data;

import com.sfb.baselib.data.base.BaseBean;

public class ClueStatusInfo extends BaseBean {

    private int id;
    private int clueId;
    private int clueStatus;
    private int dealUserId;
    private String dealUserName;
    private long dealTime;
    private String dealTimeStr;
    private int receiveUserId;
    private String receiveUserName;
    private String dealContent;

    public void setId(int id) {
        this.id = id;
    }

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setClueStatus(int clueStatus) {
        this.clueStatus = clueStatus;
    }

    public void setDealUserId(int dealUserId) {
        this.dealUserId = dealUserId;
    }

    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public void setDealTimeStr(String dealTimeStr) {
        this.dealTimeStr = dealTimeStr;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
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

    public int getDealUserId() {
        return dealUserId;
    }

    public String getDealUserName() {
        return dealUserName;
    }

    public long getDealTime() {
        return dealTime;
    }

    public String getDealTimeStr() {
        return dealTimeStr;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public String getDealContent() {
        return dealContent;
    }
}
