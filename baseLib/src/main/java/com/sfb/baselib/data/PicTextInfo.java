package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * 图文信息
 */
public class PicTextInfo extends BaseBean {
    private int type;
    @DrawableRes private int imgRes;
    private String imgPath;
    private String text;
    @StringRes private int textRes;
    private String url;

    public PicTextInfo() {
    }

    public PicTextInfo(int type, int imgRes, String text, String url) {
        this.type = type;
        this.imgRes = imgRes;
        this.text = text;
        this.url = url;
    }

    public PicTextInfo(int type, int imgRes, String text) {
        this.type = type;
        this.imgRes = imgRes;
        this.text = text;
    }

    public PicTextInfo(int type, int imgRes, int textRes) {
        this.type = type;
        this.imgRes = imgRes;
        this.textRes = textRes;
    }

    public PicTextInfo(int type, String imgPath, String text) {
        this.type = type;
        this.imgPath = imgPath;
        this.text = text;
    }

    public PicTextInfo(int type, String imgPath, int textRes) {
        this.type = type;
        this.imgPath = imgPath;
        this.textRes = textRes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextRes() {
        return textRes;
    }

    public void setTextRes(int textRes) {
        this.textRes = textRes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
