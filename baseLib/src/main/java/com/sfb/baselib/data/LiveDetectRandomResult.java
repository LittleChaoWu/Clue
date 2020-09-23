package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

/**
 * 活体检测所需的参数
 */
public class LiveDetectRandomResult extends BaseBean {

    private String businessSerial;
    private String randomNumber;
    private String sessionID;

    public String getBusinessSerial() {
        return businessSerial;
    }

    public void setBusinessSerial(String businessSerial) {
        this.businessSerial = businessSerial;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
