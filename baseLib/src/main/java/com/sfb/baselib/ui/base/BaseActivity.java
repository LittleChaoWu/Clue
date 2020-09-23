package com.sfb.baselib.ui.base;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.pref.GuardPreference_;
import com.sfb.baselib.R;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.utils.ActivityLifecycleManager;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.eyes.Eyes;
import com.sfb.baselib.widget.LoadingView;
import com.sfb.baselib.widget.dialog.PromptDialog;
import com.sfb.baselib.widget.form.LinearClickListener;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;

/**
 * Activity的基类
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends RequestPermissionActivity<V, P> implements View.OnClickListener, LinearClickListener {

    protected Toolbar mToolbar;
    private FrameLayout mToolbarLayout;
    private TextView mTvTitle;
    protected TextView mTvBack;
    private FrameLayout mContainer;
    private ViewStub mStuLoading;
    private LoadingView mLoadingView;
    private boolean isCanBack = true;

    public ARouter mARouter;
    public GuardPreference_ mPreference;
    public Gson gson;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoading();
        CommonUtils.hideKeyBoard(mContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckAgain) {
            if (requestLocation()) {
                isNeedCheckAgain = true;
            }
        }
    }

    @Override
    protected void requestLocationCallback(boolean isSuccess) {
        super.requestLocationCallback(isSuccess);
        if (!isSuccess) {
            showPermissionRefusedDialog(R.string.location_permission_request_message, new PromptDialog.OnNegativeClickListener() {
                @Override
                public void onClick() {
                    ActivityLifecycleManager.getInstance().finishActivities();
                }
            });
        } else {
            isNeedCheckAgain = true;
        }
    }

    /**
     * 是否有标题
     *
     * @return boolean
     */
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mARouter = ARouter.getInstance();
        mPreference = GuardPreference_.getInstance(this);
        gson = new Gson();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (hasToolbar()) {
            getDelegate().setContentView(R.layout.layout_toolbar);
            initToolBar();
        } else {
            getDelegate().setContentView(R.layout.layout_not_toolbar);
        }
        setStatusBarColor(Color.WHITE, true);
        setChildContentView(layoutResID);
    }

    private void setChildContentView(int layoutResID) {
        mStuLoading = this.findViewById(R.id.view_stub_loading);
        mContainer = this.findViewById(R.id.container_layout);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mContainer.addView(view);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolbarLayout = this.findViewById(R.id.layout_tool);
        mToolbar = this.findViewById(R.id.toolbar);
        mTvTitle = this.findViewById(R.id.tv_title);
        mTvBack = this.findViewById(R.id.tv_back);
        mTvBack.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle(null);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });
        }
        try {
            PackageManager pm = getPackageManager();
            ActivityInfo activityInfo = pm.getActivityInfo(getComponentName(), 0);
            mTvTitle.setText(activityInfo.loadLabel(pm).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * 设置标题栏颜色
     */
    public void setStatusBarColor(int color, boolean isLight) {
        if (isLight) {
            Eyes.setStatusBarLightMode(this, color);
        } else {
            Eyes.setStatusBarDarkMode(this, color);
        }
        setTitleMode(color, isLight);
    }

    public void setTitleMode(int color, boolean isLight) {
        if (hasToolbar()) {
            if (isLight) {
                mTvTitle.setTextColor(getResources().getColor(R.color.text_black));
                mToolbar.setNavigationIcon(R.drawable.ic_back_black);
            } else {
                mTvTitle.setTextColor(Color.WHITE);
                mToolbar.setNavigationIcon(R.drawable.ic_back);
            }
            mToolbarLayout.setBackgroundColor(color);
        }
    }

    /**
     * 隐藏标题
     */
    public void hideTitle() {
        if (hasToolbar()) {
            mTvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 显示标题
     */
    public void showTitle() {
        if (hasToolbar()) {
            mTvTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题
     *
     * @param resId int
     */
    public void setToolBarTitle(@StringRes int resId) {
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

    /**
     * 返回
     */
    protected void back() {
        if (isCanBack) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_back) {
            back();
        }
    }


    /**
     * 显示loading
     */
    @Override
    public void showLoading(String... values) {
        mStuLoading.setVisibility(View.VISIBLE);
        mLoadingView = mStuLoading.findViewById(R.id.mLoadingView);
        if (values != null && values.length > 0) {
            mLoadingView.setLoadingText(values[0]);
        }
        isCanBack = false;
    }

    /**
     * 隐藏loading
     */
    @Override
    public void hideLoading() {
        if (mStuLoading != null) {
            mStuLoading.setVisibility(View.GONE);
        }
        isCanBack = true;
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
