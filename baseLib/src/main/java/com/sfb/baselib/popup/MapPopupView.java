package com.sfb.baselib.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.sfb.baselib.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * 地图的Popup
 */
public class MapPopupView extends FrameLayout {

    public TextView mTvPopup;
    public ImageView mIvClose;

    public MapPopupView(@NonNull Context context) {
        this(context, null);
    }

    public MapPopupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_map_popup, this);
        mTvPopup = findViewById(R.id.tv_popup);
        mIvClose = findViewById(R.id.iv_close);
        mTvPopup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 设置数据
     *
     * @param text         String
     * @param isShowDelete boolean
     */
    public void setText(String text, boolean isShowDelete) {
        setText(text);
        if (isShowDelete) {
            mIvClose.setVisibility(VISIBLE);
        } else {
            mIvClose.setVisibility(GONE);
        }
    }

    /**
     * 设置数据
     *
     * @param resID int
     */
    public void setText(@StringRes int resID) {
        mTvPopup.setText(resID);
    }

    /**
     * 设置数据
     *
     * @param text String
     */
    public void setText(String text) {
        mTvPopup.setText(text);
    }

}
