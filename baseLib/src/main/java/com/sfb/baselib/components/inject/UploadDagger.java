package com.sfb.baselib.components.inject;

import android.app.Application;

import com.sfb.baselib.components.inject.component.DaggerUploadComponent;
import com.sfb.baselib.components.inject.component.UploadComponent;
import com.sfb.baselib.components.inject.model.UploadModule;

/**
 * 上传模块的Dagger
 */
public class UploadDagger {

    private static UploadComponent uploadComponent;

    public static void init(Application app) {
        uploadComponent = DaggerUploadComponent.builder().uploadModule(new UploadModule(app)).build();
    }

    public static UploadComponent getDaggerComponent() {
        return uploadComponent;
    }

}
