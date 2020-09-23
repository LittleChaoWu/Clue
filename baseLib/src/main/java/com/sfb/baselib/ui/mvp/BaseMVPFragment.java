package com.sfb.baselib.ui.mvp;

import android.content.Context;
import android.os.Bundle;


import com.sfb.baselib.utils.ToastUtils;
import com.sfb.baselib.widget.dialog.LoadingDialog;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public abstract class BaseMVPFragment<V extends BaseView, P extends BasePresenter> extends Fragment implements BaseView {

    protected P mPresenter;

    protected static final String TAG = "BaseMVPFragment";
    private LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String showText) {
        ToastUtils.showShort(getActivity(), showText);
    }

    @Override
    public void showProgressDialog(@StringRes int resId) {
        showProgressDialog(getString(resId));
    }

    @Override
    public void showProgressDialog(String contentText) {
        mLoadingDialog = new LoadingDialog(getActivity(), LoadingDialog.PROGRESS_BAR_STYLE_ENDLESS);
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
        return getActivity();
    }

    public abstract P createPresenter();
}
