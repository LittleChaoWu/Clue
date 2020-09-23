package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

public class FunctionCode extends BaseBean {
    private int id;
    private String code;

    public FunctionCode(String code) {
        this.code = code;
    }

    public FunctionCode(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
