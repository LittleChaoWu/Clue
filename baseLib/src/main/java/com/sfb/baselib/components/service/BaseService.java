package com.sfb.baselib.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.sfb.baselib.components.CommonConstant;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.event.SdkInitEvent;
import com.sfb.baselib.components.inject.BaseDagger;
import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.network.api.BaseApiService;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.utils.DesUtils;
import com.sfb.baselib.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.Nullable;


/**
 * Created by wuwc on 2020/9/17.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 */

public class BaseService extends Service {
    public static final String ACTION_SFB_SDK_INIT = "action_sfb_sdk_init";
    private ResponseSubscriber subscriber;
    @Inject
    public BaseApiService mBaseApiService;
    @Inject
    public Bus mBus;

    public BaseService() {
        BaseDagger.getBaseDaggerComponent().inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriber != null && !subscriber.isDisposed()) {
            subscriber.dispose();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_SFB_SDK_INIT:
                        String phone = intent.getStringExtra(CommonConstant.KEY_PHONE);
                        initSDK(phone);
                        break;
                    default:
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSDK(String phone) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String pwd = CommonConstant.DEFAULT_INIT_ACCOUNT_PWD;
        String md5Pwd = DesUtils.md5Encode(pwd + timestamp);
        subscriber = mBaseApiService.loginSdk(phone, md5Pwd, timestamp)
                .compose(RxUtils.<BaseResponse<UserInfo>>io2main())
                .subscribeWith(new ResponseSubscriber<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> objectBaseResponse) {
                        Map<String, String> params = new HashMap<>();
                        params.put(CommonConstant.KEY_OBJ, new Gson().toJson(objectBaseResponse.getData()));
                        mBus.post(new SdkInitEvent(params, objectBaseResponse.getResponse_time()));
                    }

                    @Override
                    public void onFail(String errorMessage, boolean isEmptyMessage) {
                        mBus.post(new SdkInitEvent(-1, errorMessage));
                    }
                });

    }
}
