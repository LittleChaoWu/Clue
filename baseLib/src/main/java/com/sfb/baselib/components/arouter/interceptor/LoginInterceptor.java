package com.sfb.baselib.components.arouter.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.pref.GuardPreference_;
import com.sfb.baselib.route.ActivityRoute;

@Interceptor(name = "login", priority = 6)
public class LoginInterceptor implements IInterceptor {

    private GuardPreference_ mPreference;

    @Override
    public void init(Context context) {
        mPreference = GuardPreference_.getInstance(context);
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getPath().equals(ActivityRoute.LOGIN_PATH)) {
            //清除账户信息
//            mPreference.removeAccountInfo();//这个不清除，第二次登陆使用
            //清除用户信息
            mPreference.removeUserInfo();
            //清除角色信息
            mPreference.removeRole();
            //清除token
            mPreference.removeToken();
            //清除手势密码
            mPreference.removeGesturePassword();
        }
        callback.onContinue(postcard);
    }

}
