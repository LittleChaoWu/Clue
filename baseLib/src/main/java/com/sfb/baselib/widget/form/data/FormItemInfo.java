package com.sfb.baselib.widget.form.data;

/**
 * 表单配置的数据结构
 */
public class FormItemInfo {

    private String key;//key
    private String catalog;//目录
    private String title;//标题
    private int type;//表单类型
    private boolean isRequired;//是否必选
    private boolean isInputable;//是否可输入
    private boolean isItemClickable;//item是否需要添加点击事件
    private String contentHint;//内容hint
    private String suffix;//后缀文本
    private String suffixColor;//后缀文本颜色
    private String[] suffixImages;//后缀图片

    public void setKey(String key) {
        this.key = key;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public void setInputable(boolean inputable) {
        isInputable = inputable;
    }

    public void setItemClickable(boolean itemClickable) {
        isItemClickable = itemClickable;
    }

    public void setContentHint(String contentHint) {
        this.contentHint = contentHint;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setSuffixColor(String suffixColor) {
        this.suffixColor = suffixColor;
    }

    public void setSuffixImages(String[] suffixImages) {
        this.suffixImages = suffixImages;
    }

    public String getKey() {
        return key;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public boolean isInputable() {
        return isInputable;
    }

    public boolean isItemClickable() {
        return isItemClickable;
    }

    public String getContentHint() {
        return contentHint;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getSuffixColor() {
        return suffixColor;
    }

    public String[] getSuffixImages() {
        return suffixImages;
    }
}
