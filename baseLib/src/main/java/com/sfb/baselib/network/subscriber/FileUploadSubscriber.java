package com.sfb.baselib.network.subscriber;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 上传订阅者
 */
public abstract class FileUploadSubscriber<T> extends ResourceSubscriber<T> {

    public FileUploadSubscriber() {
        super();
    }

    @Override
    public void onNext(T t) {
        uploadComplete(t);
    }

    @Override
    public void onError(Throwable t) {
        uploadFail(t);
    }

    @Override
    public void onComplete() {
        uploadSuccess();
    }

    /**
     * 上传成功回调
     *
     * @param t T
     */
    public abstract void uploadComplete(T t);

    /**
     * 上传失败回调
     *
     * @param e 错误
     */
    public abstract void uploadFail(Throwable e);

    /**
     * 上传完成回调
     */
    public abstract void uploadSuccess();
}
