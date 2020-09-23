package com.sfb.baselib.network.subscriber;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sfb.baselib.BuildConfig;
import com.sfb.baselib.components.inject.BaseDagger;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.ui.mvp.BasePresenter;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import io.reactivex.subscribers.ResourceSubscriber;

public abstract class ResponseSubscriber<T> extends ResourceSubscriber<T> {

    @Inject
    protected Context context;
    @Inject
    protected ARouter mARouter;

    private BasePresenter mPresenter;

    public ResponseSubscriber() {
        this(null);
    }

    public ResponseSubscriber(BasePresenter mPresenter) {
        BaseDagger.getBaseDaggerComponent().inject((ResponseSubscriber<Object>) this);
        this.mPresenter = mPresenter;
    }

    /**
     * 成功回调
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param errorMessage   错误信息
     * @param isEmptyMessage 错误信息是否为空
     */
    public abstract void onFail(String errorMessage, boolean isEmptyMessage);

    @Override
    public void onNext(T t) {
        if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            if (response.isSuccess()) {
                onSuccess(t);
            } else {
                String errorMessage = response.getMsg();
                if (mPresenter != null && mPresenter.isViewAttach()) {
                    mPresenter.mView.showToast(errorMessage);
                }
                onFail(errorMessage, TextUtils.isEmpty(errorMessage));
                errorHandle(response.getCode(), errorMessage);
            }
        } else {
            onSuccess(t);
        }
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        String errorMessage = e.getLocalizedMessage();
        if (mPresenter != null && mPresenter.isViewAttach() && BuildConfig.DEBUG) {
            mPresenter.mView.showToast(errorMessage);
        }
        onFail(errorMessage, TextUtils.isEmpty(errorMessage));
    }

    @Override
    public void onComplete() {

    }

    /**
     * 特殊错误码处理
     *
     * @param code String
     */
    private void errorHandle(String code, String errorMessage) {
        if (mPresenter != null && mPresenter.isViewAttach()) {
            mPresenter.mView.showToast(errorMessage);
        }
    }

}
