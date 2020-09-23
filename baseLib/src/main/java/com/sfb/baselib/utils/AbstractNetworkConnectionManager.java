package com.sfb.baselib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 网络状态管理类
 */
public abstract class AbstractNetworkConnectionManager {

    private ConnectivityBroadcastReceiver mReceiver;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NETWORK_TYPE_NOT_AVAILABLE, NETWORK_TYPE_WIFI, NETWORK_TYPE_MOBILE})
    @interface NetworkType {
    }


    /**
     * 无网络状态
     */
    private static final int NETWORK_TYPE_NOT_AVAILABLE = 0;
    /**
     * wifi网络状态
     */
    private static final int NETWORK_TYPE_WIFI = 1;
    /**
     * 移动网络状态
     */
    private static final int NETWORK_TYPE_MOBILE = 2;

    @NetworkType
    private int networkType = NETWORK_TYPE_NOT_AVAILABLE;

    /**
     * 初始化网络监听管理
     *
     * @param context 上下文
     */
    public void init(Context context) {
        try {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            mReceiver = new ConnectivityBroadcastReceiver();
            context.registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解除网络监听广播
     *
     * @param context 上下文
     */
    public void release(Context context) {
        try {
            if (mReceiver != null) {
                context.unregisterReceiver(mReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络发生变化时，更新一下目前的网络状态，并提供回调
     *
     * @param manager 连接管理器
     */
    private void updateConnectivityManager(ConnectivityManager manager) {
        if (manager == null) {
            networkType = NETWORK_TYPE_NOT_AVAILABLE;
            return;
        }
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            networkType = NETWORK_TYPE_NOT_AVAILABLE;
            onNetChanged(false, false);
            return;
        }

        int type = netInfo.getType();
        //判断是在wifi下还是数据网络下
        if (type == ConnectivityManager.TYPE_WIFI) {
            networkType = NETWORK_TYPE_WIFI;
            onNetChanged(false, true);
        } else {
            networkType = NETWORK_TYPE_MOBILE;
            onNetChanged(true, false);
        }
    }

    /**
     * 是否网络可用
     *
     * @return 是否网络可用
     */
    public boolean isNetworkAvailable() {
        return networkType != NETWORK_TYPE_NOT_AVAILABLE;
    }

    /**
     * wifi是否可用
     *
     * @return wifi是否可用
     */
    public boolean isWifiAvailable() {
        return networkType == NETWORK_TYPE_WIFI;
    }

    /**
     * 手机数据是否可用
     *
     * @return 手机数据是否可用
     */
    public boolean isMobileNetworkAvailable() {
        return networkType == NETWORK_TYPE_MOBILE;
    }

    /**
     * 当网络变化时回调
     *
     * @param isMobileAvailable 数据是否可用
     * @param isWifiAvailable   wifi是否可用
     */
    protected abstract void onNetChanged(boolean isMobileAvailable, boolean isWifiAvailable);

    public class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            try {
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    updateConnectivityManager(manager);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
