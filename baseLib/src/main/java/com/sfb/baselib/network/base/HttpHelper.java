package com.sfb.baselib.network.base;

import android.text.TextUtils;


import com.sfb.baselib.network.NetworkConfig;
import com.sfb.baselib.network.interceptor.LogInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求基础类（使用retrofit）
 */
public class HttpHelper {
    private static final String TAG = HttpHelper.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 30;
    private HashMap<String, Object> mServiceMap;
    private static HttpHelper mInstance;

    public static HttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }

    private HttpHelper() {
        //Map is used to store RetrofitService
        mServiceMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass) {
        String key = serviceClass.getSimpleName();
        if (mServiceMap.containsKey(key)) {
            return (S) mServiceMap.get(key);
        } else {
            Object obj = createApi(serviceClass);
            mServiceMap.put(key, obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass, OkHttpClient client) {
        String key = serviceClass.getSimpleName();
        if (!TextUtils.isEmpty(key)) {
            if (mServiceMap.containsKey(key)) {
                return (S) mServiceMap.get(key);
            } else {
                Object obj = createApi(serviceClass, client);
                mServiceMap.put(key, obj);
                return (S) obj;
            }
        }
        return null;
    }

    private <S> S createApi(Class<S> serviceClass) {
        //custom OkHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //time our
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        /*//cache
        File httpCacheDirectory = new File(mContext.getCacheDir(), "OkHttpCache");
        httpClient.cache(new Cache(httpCacheDirectory, 50 * 1024 * 1024));*/
        //Interceptor
        httpClient.addNetworkInterceptor(new LogInterceptor());
        //retry when fail
        httpClient.retryOnConnectionFailure(true);

        return createApi(serviceClass, httpClient.build());
    }

    private <S> S createApi(Class<S> serviceClass, OkHttpClient client) {
        String baseUrl = NetworkConfig.getRootUrl();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass, String baseUrl) {
        String key = serviceClass.getSimpleName();
        if (mServiceMap.containsKey(key)) {
            return (S) mServiceMap.get(key);
        } else {
            Object obj = createApi(serviceClass, baseUrl);
            mServiceMap.put(key, obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <S> S getApi(Class<S> serviceClass, String baseUrl, OkHttpClient client) {
        String key = serviceClass.getSimpleName();
        if (!TextUtils.isEmpty(key)) {
            if (mServiceMap.containsKey(key)) {
                return (S) mServiceMap.get(key);
            } else {
                Object obj = createApi(serviceClass, baseUrl, client);
                mServiceMap.put(key, obj);
                return (S) obj;
            }
        }
        return null;
    }

    private <S> S createApi(Class<S> serviceClass, String baseUrl) {
        //custom OkHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //time our
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        /*//cache
        File httpCacheDirectory = new File(mContext.getCacheDir(), "OkHttpCache");
        httpClient.cache(new Cache(httpCacheDirectory, 50 * 1024 * 1024));*/
        //Interceptor
        httpClient.addNetworkInterceptor(new LogInterceptor());
        //retry when fail
        httpClient.retryOnConnectionFailure(true);

        return createApi(serviceClass, baseUrl, httpClient.build());
    }

    private <S> S createApi(Class<S> serviceClass, String baseUrl, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    public void clear() {
        if (mServiceMap != null) {
            mServiceMap.clear();
            mServiceMap = null;
            mServiceMap = new HashMap<>();
        }
    }
}
