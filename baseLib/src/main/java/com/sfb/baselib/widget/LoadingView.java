package com.sfb.baselib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sfb.baselib.R;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * 正在加载布局
 */
public class LoadingView extends LinearLayout {

    private Context mContext;
    private ProgressBar mPbLoading;
    private TextView mTvLoading;
    private String mLoadingText;

    public LoadingView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 控件初始化（初始化属性及视图）
     *
     * @param attrs 属性集合
     */
    private void init(@Nullable AttributeSet attrs) {
        initAttrs(attrs);
        initView();
    }

    /**
     * 初始化属性
     *
     * @param attrs 属性集合
     */
    private void initAttrs(@Nullable AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        mLoadingText = typedArray.getString(R.styleable.LoadingView_loading_text);
        typedArray.recycle();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        View loadingLayout = inflate(mContext, R.layout.layout_loading_view, this);
        mPbLoading = loadingLayout.findViewById(R.id.pb_loading);
        mTvLoading = loadingLayout.findViewById(R.id.tv_loading);
        findViewById(R.id.layout_load).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setLoadingText(mLoadingText);
    }

    /**
     * 设置正在加载布局显示的文字
     *
     * @param loadingTextRes 显示的文字资源id
     */
    public void setLoadingText(@StringRes int loadingTextRes) {
        setLoadingText(mContext.getString(loadingTextRes));
    }

    /**
     * 设置正在加载布局显示的文字
     *
     * @param loadingText 显示的文字
     */
    public void setLoadingText(String loadingText) {
        if (!TextUtils.isEmpty(loadingText)) {
            mTvLoading.setText(loadingText);
        }
    }
}
