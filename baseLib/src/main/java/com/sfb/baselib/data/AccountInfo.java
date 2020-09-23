package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

public class AccountInfo extends BaseBean {

    private String username;
    private String password;
    private String phone;

    public AccountInfo() {
    }

    public AccountInfo(String phone) {
        this.phone = phone;
    }

    public AccountInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
