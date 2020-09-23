package com.sfb.baselib.utils;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    /**
     * 观察者在主线程，被观察者在io线程的Transformer
     */
    public static <T> FlowableTransformer<T, T> io2main() {
        return new FlowableTransformer<T, T>() {
            @Override public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 观察者在io线程，被观察者在io线程的Transformer
     */
    public static <T> FlowableTransformer<T, T> io2io() {
        return new FlowableTransformer<T, T>() {
            @Override public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            }
        };
    }
}

