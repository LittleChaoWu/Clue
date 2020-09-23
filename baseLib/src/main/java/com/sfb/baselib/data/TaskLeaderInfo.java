package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class TaskLeaderInfo extends BaseBean {

    private String realName;
    private String telephone;

    public String getRealName() {
        return realName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
