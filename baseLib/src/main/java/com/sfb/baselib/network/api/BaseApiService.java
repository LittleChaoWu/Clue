package com.sfb.baselib.network.api;


import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.data.base.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface BaseApiService {

    /**
     * 下载文件
     *
     * @param url 文件下载地址
     */
    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String url);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    //20200916需求修改：线索举报需要提供模块SDK发布，涉及SDK单点登录以及代码管理问题，因此将该接口抽基础层
    @GET("login")
    Flowable<BaseResponse<UserInfo>> login(@Query("userName") String username, @Query("password") String password);

    /**
     * 适用于模块集成的单点登录
     * @param phone 手机号
     * @param password 密码
     * @param timestamp 时间戳
     * @return
     */
    @GET("loginByPhoneAndPwd")
    Flowable<BaseResponse<UserInfo>> loginSdk(@Query("phone") String phone, @Query("password") String password, @Query("timestamp") String timestamp);
}
