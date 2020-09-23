package com.sfb.baselib.components.inject.model;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.pref.GuardPreference_;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.network.NetworkHelper;
import com.sfb.baselib.network.api.BaseApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    protected Application app;

    public BaseModule(Application application) {
        this.app = application;
    }

    @Provides
    public ARouter providerARouter() {
        return ARouter.getInstance();
    }

    @Provides
    public Context providerContext() {
        return app;
    }

    @Provides
    public BaseApiService providerBaseApiService() {
        return NetworkHelper.getInstance(app).getApi(BaseApiService.class);
    }

    @Provides
    public Gson providerGson() {
        return new Gson();
    }

    @Provides
    public Bus providerBus() {
        return Bus.getInstance();
    }

    @Provides
    public GuardPreference_ providerGuardPreference() {
        return GuardPreference_.getInstance(app);
    }

    @Provides
    public DaoHelper providerDaoHelper() {
        return DaoHelper.getInstance(app);
    }

}
