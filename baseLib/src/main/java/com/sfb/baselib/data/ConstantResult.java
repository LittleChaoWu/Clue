package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class ConstantResult extends BaseBean {

    private int id;
    private String cfgItem;//类型
    private List<ConstantInfo> constants;//常量列表

    public void setId(int id) {
        this.id = id;
    }

    public void setCfgItem(String cfgItem) {
        this.cfgItem = cfgItem;
    }

    public void setConstants(List<ConstantInfo> constants) {
        this.constants = constants;
    }

    public int getId() {
        return id;
    }

    public String getCfgItem() {
        return cfgItem;
    }

    public List<ConstantInfo> getConstants() {
        return constants;
    }
}
