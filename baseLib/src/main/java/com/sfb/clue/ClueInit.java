package com.sfb.clue;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.pref.GuardPreference_;
import com.sfb.baselib.components.CommonConstant;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.annotation.BusSubscribe;
import com.sfb.baselib.components.bus.event.SdkInitEvent;
import com.sfb.baselib.components.service.BaseService;
import com.sfb.baselib.data.AccountInfo;
import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.utils.AppTool;
import com.sfb.baselib.utils.DeviceUtils;
import com.sfb.baselib.utils.ToastUtils;

/**
 * Created by wuwc on 2020/9/17.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 * 线索模块SDK入口
 */

public class ClueInit {
    private Activity activity;

    private Bundle bundle;

    public Bus mBus;

    private GuardPreference_ mPreference;

    public ClueInit(ClueInit.Builder builder) {
        this.activity = builder.activity;
        this.bundle = builder.bundle;
        mBus = Bus.getInstance();
        mBus.register(this);
        mPreference = GuardPreference_.getInstance(this.activity);
        checkPermissions();
    }

    private void checkPermissions() {
        AppTool.checkPermission(activity, "app将无法使用", new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, this::createToIntent, this::checkPermissions);
    }

    private void createToIntent() {
        showLoading();
        Intent intent = new Intent(activity, BaseService.class);
        intent.putExtras(bundle);
        intent.setAction(BaseService.ACTION_SFB_SDK_INIT);
        activity.startService(intent);
    }

    private void showLoading() {
        if (!MProgressDialog.isShowing()) {
            MDialogConfig mDialogConfig = new MDialogConfig.Builder()
                    .isCancelable(true).build();
            MProgressDialog.showProgress(activity, mDialogConfig);
        }
    }

    private void hideLoading() {
        if (!activity.isFinishing()) {
            MProgressDialog.dismissProgress();
        }
    }

    private void destroy() {
        mBus.unregister(this);
        activity = null;
        bundle = null;
    }

    @BusSubscribe
    public void onEvent(SdkInitEvent event) {
        hideLoading();
        if (event.getResultCode() == -1) {
            ToastUtils.showLong(activity, event.getErrorMsg());
            return;
        }
        String userInfoStr = event.getBundle().get(CommonConstant.KEY_OBJ);
        UserInfo userInfo = new Gson().fromJson(userInfoStr, UserInfo.class);
        if (null != userInfo) {
            mPreference.putUserInfo(userInfo);
            //保存token
            mPreference.putToken(userInfo.getToken());
            //保存角色类型
            mPreference.putRole(userInfo.getType());
            //保存账户信息
            mPreference.putAccountInfo(new AccountInfo(userInfo.getTelephone()));
            //保存服务端时间
            mPreference.putServerTime(event.getResponse_time());
            //保存登录时的开机时长
            mPreference.putBootTime(DeviceUtils.getBootTime());
            ARouter mARouter = ARouter.getInstance();
            mARouter.build(ActivityRoute.CLUE_REPORT_PATH).navigation();
        }
        destroy();
    }

    public static class Builder {

        private Activity activity;

        private Bundle bundle;

        public Builder(Activity activity, Bundle bundle) {
            this.activity = activity;
            this.bundle = bundle;
        }

        public ClueInit create() {
            return new ClueInit(this);
        }
    }
}
