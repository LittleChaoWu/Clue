package com.sfb.baselib.components.inject.model;

import android.app.Application;

import com.sfb.baselib.network.NetworkHelper;
import com.sfb.baselib.network.api.UploadApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class UploadModule extends BaseModule {

    public UploadModule(Application application) {
        super(application);
    }

    @Provides
    public UploadApiService providerUploadApiService() {
        return NetworkHelper.getInstance(app).getApi(UploadApiService.class);
    }
}
