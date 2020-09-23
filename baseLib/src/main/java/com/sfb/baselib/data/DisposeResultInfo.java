package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class DisposeResultInfo extends BaseBean {

    private int type; //人员处理结果类型 1-放行 2-抓获
    private String transfer_to; //嫌犯转移地址
    private String person_info; //嫌犯情况描述
    private String remark; //人员处理备注

    public DisposeResultInfo() {
    }

    public DisposeResultInfo(int type, String transfer_to, String person_info, String remark) {
        this.type = type;
        this.transfer_to = transfer_to;
        this.person_info = person_info;
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTransfer_to() {
        return transfer_to;
    }

    public void setTransfer_to(String transfer_to) {
        this.transfer_to = transfer_to;
    }

    public String getPerson_info() {
        return person_info;
    }

    public void setPerson_info(String person_info) {
        this.person_info = person_info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DisposeResult{" +
                "type='" + type + '\'' +
                ", transfer_to='" + transfer_to + '\'' +
                ", person_info='" + person_info + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
