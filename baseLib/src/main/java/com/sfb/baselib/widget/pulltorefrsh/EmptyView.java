package com.sfb.baselib.widget.pulltorefrsh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfb.baselib.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmptyView extends FrameLayout implements View.OnClickListener {

    private TextView mTvEmpty;
    private ImageView mIvEmpty;
    private TextView mTvRefresh;

    public EmptyView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    // 初始化视图
    private void initView(Context context) {
        View view = inflate(context, R.layout.layout_empty, this);
        mTvEmpty = view.findViewById(R.id.tv_empty);
        mIvEmpty = view.findViewById(R.id.iv_empty);
        mTvRefresh = findViewById(R.id.tv_refresh);
        mTvRefresh.setOnClickListener(this);
        showEmpty();
    }

    // 显示空数据情况
    public void showEmpty() {
        mTvEmpty.setText(getContext().getString(R.string.empty_data));
    }

    // 显示网络异常情况
    public void showNetworkError() {
        mTvEmpty.setText(getContext().getString(R.string.network_error));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_refresh) {
            if (refreshCallback != null) {
                refreshCallback.refresh();
            }
        }
    }

    //刷新的回调
    private RefreshCallback refreshCallback;

    public void setRefreshCallback(RefreshCallback refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    public interface RefreshCallback {
        void refresh();
    }
}
