package com.sfb.baselib;

import android.app.Application;
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

public class SDKInit {

    public static void init(Application context) {
        //初始化网络环境
        NetworkConfig.init(context);
        //初始化日志系统，指定日志文件存储位置
        LogUtils.init("People", BuildConfig.DEBUG, StoragePathUtils.getRootPath("guard.log"));
        //初始化异常日志打印
        CustomExceptionHandler.getInstance(context).init(StoragePathUtils.getRootPath("dump.log"));
        //初始化数据库
        DataBaseManager.getInstance().init(context);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(context);
        //注册Activity生命周期回调
        context.registerActivityLifecycleCallbacks(ActivityLifecycleManager.getInstance());
        //初始化ARouter
        initARouter(context);
        //初始化Dagger
        BaseDagger.init(context);
        UploadDagger.init(context);
        ClueDagger.init(context);
    }

    /**
     * 初始化ARouter
     */
    private static void initARouter(Application context) {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(context);
    }
}
