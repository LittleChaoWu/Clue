package com.sfb.baselib.ui.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.pref.GuardPreference_;
import com.sfb.baselib.R;
import com.sfb.baselib.ui.mvp.BaseMVPFragment;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.widget.LoadingView;
import com.sfb.baselib.widget.form.LinearClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Fragment的基类
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter> extends BaseMVPFragment<V, P> implements View.OnClickListener, LinearClickListener {

    protected Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTvBack;
    private FrameLayout mToolbarLayout;
    private FrameLayout mContainer;
    private ViewStub mStuLoading;
    private LoadingView mLoadingView;
    private View parentView;
    private AppCompatActivity appCompatActivity;
    protected GuardPreference_ mPreference;
    protected ARouter mARouter;
    private boolean isCanBack = true;

    public BaseFragment() {
        mPreference = GuardPreference_.getInstance(getContext());
        mARouter = ARouter.getInstance();
    }

    /**
     * 是否有标题
     *
     * @return boolean
     */
    protected boolean hasToolbar() {
        return false;
    }

    /**
     * 创建内容视图
     *
     * @param inflater 解析器
     * @return 内容视图
     */
    protected abstract View onCreateContentView(LayoutInflater inflater);

    /**
     * 初始化视图
     *
     * @param view               view
     * @param savedInstanceState Bundle
     */
    protected abstract void onContentViewCreated(View view, @Nullable Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (hasToolbar()) {
            parentView = inflater.inflate(R.layout.layout_toolbar, container, false);
            initToolBar(parentView);
        } else {
            parentView = inflater.inflate(R.layout.layout_not_toolbar, container, false);
        }
        mStuLoading = parentView.findViewById(R.id.view_stub_loading);
        mLoadingView = mStuLoading.findViewById(R.id.mLoadingView);
        mContainer = parentView.findViewById(R.id.container_layout);
        mContainer.addView(onCreateContentView(inflater));
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onContentViewCreated(view, savedInstanceState);
    }

    /**
     * 初始化ToolBar
     *
     * @param parentView View
     */
    private void initToolBar(View parentView) {
        setHasOptionsMenu(true);//设置显示菜单项
        mToolbarLayout = parentView.findViewById(R.id.layout_tool);
        mTvTitle = parentView.findViewById(R.id.tv_title);
        mTvBack = parentView.findViewById(R.id.tv_back);
        mToolbar = parentView.findViewById(R.id.toolbar);
        appCompatActivity = (AppCompatActivity) this.getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        mTvBack.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanBack) {
                    back();
                }
            }
        });
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(null);
        }
        try {
            PackageManager pm = appCompatActivity.getPackageManager();
            ActivityInfo activityInfo = pm.getActivityInfo(appCompatActivity.getComponentName(), 0);
            mTvTitle.setText(activityInfo.loadLabel(pm).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回
     */
    protected void back() {

    }

    /**
     * 设置标题
     *
     * @param resId int
     */
    public void setToolBarTitle(int resId) {
        if (hasToolbar()) {
            String title = getString(resId);
            setToolBarTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param title String
     */
    public void setToolBarTitle(String title) {
        if (hasToolbar()) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 设置标题颜色
     *
     * @param resId int
     */
    public void setToolBarTitleColor(int resId) {
        if (hasToolbar()) {
            mTvTitle.setTextColor(getResources().getColor(resId));
        }
    }


    /**
     * 修改返回文本
     *
     * @param resId int
     */
    public void setBackText(int resId) {
        if (hasToolbar()) {
            String text = getString(resId);
            setBackText(text);
        }
    }

    /**
     * 修改返回文本
     *
     * @param text String
     */
    public void setBackText(String text) {
        if (hasToolbar()) {
            mTvBack.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_back) {
            if (isCanBack) {
                back();
            }
        }
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading(String... values) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.showLoading(values);
        } else {
            mStuLoading.setVisibility(View.VISIBLE);
            mLoadingView = parentView.findViewById(R.id.mLoadingView);
            if (values != null && values.length > 0) {
                mLoadingView.setLoadingText(values[0]);
            }
            isCanBack = false;
        }
    }

    /**
     * 隐藏loading
     */
    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideLoading();
        } else {
            mStuLoading.setVisibility(View.GONE);
            isCanBack = true;
        }
    }

    /////////////FormTextView点击事件//////////////

    @Override
    public void onLinearItemClick(int id) {
    }

    @Override
    public void onSuffixTextClick(int id) {
    }

    @Override
    public void onFirstSuffixImageClick(int id) {
    }

    @Override
    public void onSecondSuffixImageClick(int id) {
    }

}
