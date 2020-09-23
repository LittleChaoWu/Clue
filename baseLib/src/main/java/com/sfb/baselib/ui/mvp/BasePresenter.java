package com.sfb.baselib.ui.mvp;

import android.content.Context;

import com.google.gson.Gson;
import com.pref.GuardPreference_;
import com.sfb.baselib.components.inject.BaseDagger;
import com.sfb.baselib.database.DaoHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.ResourceSubscriber;

public class BasePresenter {

    @Inject
    protected Context context;
    @Inject
    protected DaoHelper mDaoHelper;
    @Inject
    protected GuardPreference_ mPreference;
    @Inject
    protected Gson gson;

    protected List<ResourceSubscriber> subscribers = new ArrayList<>();

    public BaseView mView;

    public void attach(BaseView mView) {
        this.mView = mView;
    }

    public void detach() {
        mView = null;
        if (subscribers != null && subscribers.size() > 0) {
            for (ResourceSubscriber subscriber : subscribers) {
                if (subscriber != null && !subscriber.isDisposed()) {
                    subscriber.dispose();
                }
            }
        }
    }

    public boolean isViewAttach() {
        return mView != null;
    }

    public BasePresenter() {
        BaseDagger.getBaseDaggerComponent().inject((BasePresenter) this);
    }

    /**
     * 重置ApiService
     */
    public void resetApiService() {
    }

}
