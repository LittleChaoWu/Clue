package com.sfb.baselib.widget.pulltorefrsh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class CustomPtrFrameLayout extends PtrClassicFrameLayout {

    public CustomPtrFrameLayout(Context context) {
        super(context);
        initConfig(context);
    }

    public CustomPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig(context);
    }

    public CustomPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initConfig(context);
    }

    // 初始化参数
    private void initConfig(Context context) {
        // the following are default settings
        this.setResistance(1.7f);
        this.setRatioOfHeaderHeightToRefresh(1.2f);
        this.setDurationToClose(200);
        this.setDurationToCloseHeader(1000);
        // default is false
        this.setPullToRefresh(true);
        // default is true
        this.setKeepHeaderWhenRefresh(true);
    }

}
