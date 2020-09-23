package com.sfb.baselib.network;

import android.content.Context;

import com.pref.GuardPreference_;
import com.sfb.baselib.BuildConfig;

public class NetworkConfig {

    public static final String[] ENVS = {"正式环境", "测试环境", "外网测试环境", "怡隆", "瑞福", "沂骏", "自定义环境"};

    public static final int OFFICIAL_ENV = 0;//正式环境
    public static final int DEV_ENV = 1;//测试环境
    public static final int LOCAL_ONE_ENV = 2;//本地环境
    public static final int LOCAL_TWO_ENV = 3;//本地环境
    public static final int LOCAL_THREE_ENV = 4;//本地环境
    public static final int OUT_DEV_ENV = 5;//外网测试环境

    private static int currentEvn = DEV_ENV; //默认正式环境

    private static String HOST = "";    //地址

    private static String ROOT_URL = null;//根地址
    private static String H5_ROOT_URL = null;//h5根地址

    /**
     * 初始化网络环境
     */
    public static void init(Context context) {
        currentEvn = GuardPreference_.getInstance(context).getEnvironmentValue();
        switch (currentEvn) {
            case OFFICIAL_ENV:
                HOST = BuildConfig.OFFICIAL_ENV;
                break;
            case DEV_ENV:
                HOST = BuildConfig.DEV_ENV;
                break;
            case LOCAL_ONE_ENV:
                HOST = BuildConfig.LOCAL_ONE_ENV;
                break;
            case LOCAL_TWO_ENV:
                HOST = BuildConfig.LOCAL_TWO_ENV;
                break;
            case LOCAL_THREE_ENV:
                HOST = BuildConfig.LOCAL_THREE_ENV;
                break;
            case OUT_DEV_ENV:
                HOST = BuildConfig.OUT_DEV_ENV;
                break;
            default:
                break;
        }
        ROOT_URL = HOST + BuildConfig.MASS + "/s/";
        H5_ROOT_URL = HOST + BuildConfig.MASS;
    }

    public static void customIpServiceEnv(String host) {
        if (null == host) {
            host = "";
        }
        ROOT_URL = "http://" + host + BuildConfig.MASS + "/s/";
        H5_ROOT_URL = "http://" + host + BuildConfig.MASS;
    }

    public static int getCurrentEnv() {
        return currentEvn;
    }

    /**
     * 获取域名
     *
     * @return HOST
     */
    public static String getHOST() {
        return HOST;
    }

    /**
     * 获取H5链接的域名
     *
     * @return H5_ROOT_URL
     */
    public static String getH5RootUrl() {
        return H5_ROOT_URL;
    }

    /**
     * 获取根地址
     *
     * @return ROOT_URL
     */
    public static String getRootUrl() {
        return ROOT_URL;
    }

}
