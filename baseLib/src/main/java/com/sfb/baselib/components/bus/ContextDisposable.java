package com.sfb.baselib.components.bus;

import io.reactivex.disposables.Disposable;

public class ContextDisposable {

    public String key;
    public Disposable disposable;

    public ContextDisposable(String key, Disposable disposable) {
        this.key = key;
        this.disposable = disposable;
    }
}
