package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 活体检测结果
 */
public class LiveDetectResult extends BaseBean {

    private String accountUuid;
    private String authenResult;
    private String businessSerial;
    private String sessionID;

    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }

    public String getAuthenResult() {
        return authenResult;
    }

    public void setAuthenResult(String authenResult) {
        this.authenResult = authenResult;
    }

    public String getBusinessSerial() {
        return businessSerial;
    }

    public void setBusinessSerial(String businessSerial) {
        this.businessSerial = businessSerial;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
