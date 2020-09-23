package com.sfb.baselib.widget.form;

public interface LinearClickListener {

    /**
     * 表单item的点击事件
     */
    void onLinearItemClick(int id);

    /**
     * 后缀文本的点击事件
     */
    void onSuffixTextClick(int id);

    /**
     * 第一个后缀图片的点击事件
     */
    void onFirstSuffixImageClick(int id);

    /**
     * 第二个后缀图片的点击事件
     */
    void onSecondSuffixImageClick(int id);

}
