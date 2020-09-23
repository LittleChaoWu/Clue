package com.sfb.baselib.ui.mvp;

import android.content.Context;

import androidx.annotation.StringRes;

public interface BaseView {
    void showToast(@StringRes int resId);

    void showToast(String showText);

    void showProgressDialog(@StringRes int resId);

    void showProgressDialog(String contentText);

    void dismissProgressDialog();

    void showLoading(String... values);

    void hideLoading();

    Context getContext();
}
