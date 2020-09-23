package com.sfb.baselib.ui.mvp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;


import com.sfb.baselib.utils.ToastUtils;
import com.sfb.baselib.widget.dialog.LoadingDialog;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseMVPActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseView {
    public P mPresenter;

    protected static final String TAG = "BaseMVPActivity";
    private LoadingDialog mLoadingDialog;

    public abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showToast(@StringRes int resId) {
        if (resId != 0) {
            showToast(getString(resId));
        }
    }

    @Override
    public void showToast(String showText) {
        if (!TextUtils.isEmpty(showText)) {
            ToastUtils.showShort(this, showText);
        }
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        showProgressDialog(getString(resId));
    }

    @Override
    public void showProgressDialog(String contentText) {
        mLoadingDialog = new LoadingDialog(this, LoadingDialog.PROGRESS_BAR_STYLE_ENDLESS);
        mLoadingDialog.isTitleShow(false)
                .content(contentText)
                .show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
