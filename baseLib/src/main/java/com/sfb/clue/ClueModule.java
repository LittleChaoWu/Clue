package com.sfb.clue;

import android.app.Application;

import com.sfb.baselib.components.inject.model.BaseModule;
import com.sfb.baselib.network.NetworkHelper;
import com.sfb.clue.api.ClueApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class ClueModule extends BaseModule {

    public ClueModule(Application application) {
        super(application);
    }

    @Provides
    public ClueApiService providerClueApiService() {
        return NetworkHelper.getInstance(app).getApi(ClueApiService.class);
    }
}
