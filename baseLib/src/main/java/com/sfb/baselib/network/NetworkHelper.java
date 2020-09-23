package com.sfb.baselib.network;

import android.content.Context;

import com.pref.GuardPreference_;
import com.sfb.baselib.BuildConfig;
import com.sfb.baselib.network.base.HttpHelper;
import com.sfb.baselib.network.interceptor.LogInterceptor;
import com.sfb.baselib.utils.CommonUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkHelper {

    private static NetworkHelper mInstance;
    private static Context context;

    private OkHttpClient httpClient;

    public static NetworkHelper getInstance(Context context) {
        NetworkHelper.context = context.getApplicationContext();
        if (mInstance == null) {
            synchronized (NetworkHelper.class) {
                if (mInstance == null) {
                    mInstance = new NetworkHelper();
                }
            }
        }
        return mInstance;
    }

    public <S> S getApi(Class<S> serviceClass) {
        return HttpHelper.getInstance().getApi(serviceClass, provideOkHttpClient());
    }

    public <S> S getApi(Class<S> serviceClass, String baseUrl) {
        return HttpHelper.getInstance().getApi(serviceClass, baseUrl, provideOkHttpClient());
    }

    public void clearApi() {
        HttpHelper.getInstance().clear();
    }

    private OkHttpClient provideOkHttpClient() {
        if (httpClient == null) {
            httpClient = trustAllHost(new OkHttpClient.Builder())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String token = GuardPreference_.getInstance(context).getToken();
                            long time = CommonUtils.getServerTime(context);
                            Request.Builder builder = chain.request().newBuilder()
                                    .addHeader("Accept-Charset", "UTF-8")
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Connection", "Keep-Alive")
                                    .addHeader("Accept-Language", "UTF-8")
                                    .addHeader("X-Requested-With", "true")
                                    .addHeader("request_time", String.valueOf(time))
                                    .addHeader("token", token);
                            return chain.proceed(builder.build());
                        }
                    })
                    .addNetworkInterceptor(new LogInterceptor())
                    .retryOnConnectionFailure(true)
                    .build();
        }
        return httpClient;
    }

    private OkHttpClient.Builder trustAllHost(OkHttpClient.Builder builder) {
        if (!BuildConfig.DEBUG) {
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[0];
                    return x509Certificates;
                }
            };

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HostnameVerifier customVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            return builder.sslSocketFactory(sslContext.getSocketFactory(), xtm).hostnameVerifier(customVerifier);
        } else {
            return builder;
        }
    }
}
