package com.sfb.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author: Liu
 * @description: 单点击的TextView
 * @date: 2020/4/8
 */
public class SingleClickView extends TextView {

    private long clickTime = 0L;

    public SingleClickView(Context context) {
        super(context);
    }

    public SingleClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SingleClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean performClick() {
        long cTime = System.currentTimeMillis();
        if (cTime - clickTime > 1000) {
            clickTime = cTime;
            return super.performClick();
        } else {
            clickTime = cTime;
            return false;
        }
    }
}
