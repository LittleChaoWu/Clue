
package com.sfb.baselib.network.interceptor;

import android.text.TextUtils;


import com.sfb.baselib.utils.LogUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 基于Okhttp的日志拦截器
 * Created by huangqh on 2017/10/12.
 */

public class LogInterceptor implements Interceptor {
    private static final String TAG = "RETROFIT_LOG";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String method = request.method();
        String requestParams = null;
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + "\n");
                }
                sb.delete(sb.length() - 1, sb.length());
                requestParams = sb.toString();
            }
        }

        long t1 = System.nanoTime();
        LogUtils.i(TAG, String.format("Sending request %s on %s%n%s%n%s",
            request.url(), chain.connection(), request.headers(), requestParams));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        ResponseBody body = response.body();
        MediaType mediaType = null;
        String content = null;
        if (body != null) {
            Headers headers = response.headers();
            mediaType = body.contentType();
            String contentType;
            if (mediaType == null || TextUtils.isEmpty(mediaType.toString())) {
                contentType = headers.get("Content-Type");
                if (TextUtils.isEmpty(contentType)) {
                    contentType = headers.get("content-type");
                }
            } else {
                contentType = mediaType.toString();
            }
            //若不是json字符串的返回类型就不打日志
            if (TextUtils.isEmpty(contentType) || !contentType.contains("application/json")) {
                return response;
            }
            content = body.string();
        }

        LogUtils.i(TAG, String.format("Received response for %s in %.1fms%nCode:%s%n%s%n%s",
            response.request().url(), (t2 - t1) / 1e6d, response.code(), response.headers(), content));

        if (response.body() != null) {
            if (mediaType == null || TextUtils.isEmpty(mediaType.toString())) {
                mediaType = MediaType.parse("application/json");
            }
            ResponseBody newBody = ResponseBody.create(mediaType, content);
            return response.newBuilder().body(newBody).build();
        } else {
            return response;
        }
    }
}
