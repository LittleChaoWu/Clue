package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class HomeItemInfo extends BaseBean {

    private String type;
    private String name;
    private int icon;
    private int shortcutIcon;//快捷入口的图标
    private int backGround;//背景图
    private int messageNum;

    public HomeItemInfo(String type, String name, int icon, int shortcutIcon) {
        this.type = type;
        this.name = name;
        this.icon = icon;
        this.shortcutIcon = shortcutIcon;
    }

    public HomeItemInfo(String type, String name, int icon, int shortcutIcon, int backGround) {
        this.type = type;
        this.name = name;
        this.icon = icon;
        this.shortcutIcon = shortcutIcon;
        this.backGround = backGround;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setShortcutIcon(int shortcutIcon) {
        this.shortcutIcon = shortcutIcon;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public int getShortcutIcon() {
        return shortcutIcon;
    }

    public int getBackGround() {
        return backGround;
    }

    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }
}
