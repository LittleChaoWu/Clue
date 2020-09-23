package com.sfb.baselib;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.sfb.baselib.components.inject.BaseDagger;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.database.DataBaseManager;
import com.sfb.baselib.network.NetworkConfig;
import com.sfb.baselib.storage.StoragePathUtils;
import com.sfb.baselib.utils.ActivityLifecycleManager;
import com.sfb.baselib.utils.CustomExceptionHandler;
import com.sfb.baselib.utils.LogUtils;
import com.sfb.clue.ClueDagger;

import androidx.multidex.MultiDexApplication;

public class BaseApp extends MultiDexApplication {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SDKInit.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //释放资源
        ARouter.getInstance().destroy();
    }


}
