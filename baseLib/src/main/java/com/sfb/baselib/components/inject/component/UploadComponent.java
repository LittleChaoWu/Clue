package com.sfb.baselib.components.inject.component;

import com.sfb.baselib.components.inject.model.UploadModule;
import com.sfb.baselib.dialog.UploadDialog;
import com.sfb.baselib.network.upload.FormUpload;
import com.sfb.baselib.network.upload.UploadManager;
import com.sfb.baselib.service.UploadService;
import com.sfb.baselib.ui.cache.CacheActivity;

import dagger.Component;

@Component(modules = UploadModule.class)
public interface UploadComponent {

    void inject(UploadManager uploadManager);

    void inject(FormUpload formUpload);

    void inject(UploadService uploadService);

    void inject(UploadDialog uploadDialog);

    void inject(CacheActivity cacheActivity);
}
