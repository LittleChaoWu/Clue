package com.sfb.clue.data;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class ClueTypeInfo extends BaseBean {

    private String value;
    private String key;
    private List<ClueTypeInfo> children;

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setChildren(List<ClueTypeInfo> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public List<ClueTypeInfo> getChildren() {
        return children;
    }
}
