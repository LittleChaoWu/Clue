package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

/**
 * 系统常量信息
 */
public class ConstantInfo extends BaseBean {

    private String cfgValue;
    private String cfgText;
    private String remark;
    private boolean isSelect;

    public ConstantInfo() {
    }

    public ConstantInfo(String cfgValue, String cfgText) {
        this.cfgValue = cfgValue;
        this.cfgText = cfgText;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    public String getCfgText() {
        return cfgText;
    }

    public void setCfgText(String cfgText) {
        this.cfgText = cfgText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
