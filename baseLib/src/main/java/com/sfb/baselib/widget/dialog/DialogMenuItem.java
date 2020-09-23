package com.sfb.baselib.widget.dialog;

public class DialogMenuItem {
    public int key;
    public String operName;
    public int resId;

    public DialogMenuItem(String operName, int resId) {
        this.operName = operName;
        this.resId = resId;
    }

    public DialogMenuItem(int key, String operName) {
        this.key = key;
        this.operName = operName;
    }

    public DialogMenuItem(int key, String operName, int resId) {
        this.key = key;
        this.operName = operName;
        this.resId = resId;
    }
}
