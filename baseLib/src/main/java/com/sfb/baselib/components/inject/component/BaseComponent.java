package com.sfb.baselib.components.inject.component;


import com.sfb.baselib.components.inject.model.BaseModule;
import com.sfb.baselib.components.service.BaseService;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.widget.gridimage.contract.GirdImageContract;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {

    //base
    void inject(BasePresenter basePresenter);

    void inject(BaseService baseService);

    //network
    void inject(ResponseSubscriber<Object> responseSubscriber);

    void inject(GirdImageContract.Presenter<GirdImageContract.View> viewPresenter);
}
