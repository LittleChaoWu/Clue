package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

public class GasStationInfo extends BaseBean {

    private int id;// 加油站id
    private String name;// 加油站名称
    private String address;// 加油站地址
    private String code;// 加油站编码
    private int status;// 0-新建 1-启用 2-停用 3-逻辑删除

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
