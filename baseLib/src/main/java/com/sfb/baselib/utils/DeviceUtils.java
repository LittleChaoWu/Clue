package com.sfb.baselib.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * 设备的相关信息工具类
 */
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备android版本号
     *
     * @return
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取当前版本号
     */
    public static String getReleaseVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取生产商品牌
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取IMEI
     */
    public static String getPhoneDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取IMSI
     */
    public static String getSubscriberId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * IMSI是否合法
     */
    public static boolean isIMSIValid(String imsi) {
        boolean isValid = false;
        if (!TextUtils.isEmpty(imsi)) {
            if (imsi.startsWith("4600") && imsi.length() == 15) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * 获取当前wifi的mac地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取网络运营商名称
     */
    public static String getSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String strSimType = "";
        int simState = tm.getSimState();
        if (TelephonyManager.SIM_STATE_READY == simState) {
            strSimType = tm.getSimOperatorName();
        }

        return strSimType;
    }

    /**
     * 获取网络运营商
     */
    public static String getNetworkOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperator();

        return networkOperator;
    }

    /**
     * 获取手机运营商
     */
    public static String getSimOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simOperator = "";
        int simState = tm.getSimState();
        if (TelephonyManager.SIM_STATE_READY == simState) {
            simOperator = tm.getSimOperator();
        }

        return simOperator;
    }

    /**
     * sim卡是否归属中国电信
     */
    public static boolean isChinaTelecomSim(Context context) {
        boolean isChinaTelecomSim = false;
        String networkOperator = getNetworkOperator(context);
        if ("46003".equals(networkOperator)) {
            isChinaTelecomSim = true;
        }

        return isChinaTelecomSim;
    }

    /**
     * 是否是模拟器
     */
    public static boolean isEmulator(Context context) {
        boolean isEmulator = false;
        String deviceId = getPhoneDeviceId(context);
        if (null == deviceId || "000000000000000".equals(deviceId)) {
            String model = getPhoneModel();
            String manufacturer = getManufacturer();
            if (("sdk".equals(model)) && ("unknown".equals(manufacturer))) {
                isEmulator = true;
            }
        }

        return isEmulator;
    }

    /**
     * 获取本机手机号
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 获取基带版本
     */
    public static String getBaseBandVersion() {
        String strBaseBandVersion = null;

        try {
            @SuppressWarnings("rawtypes") Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "unknown"});
            strBaseBandVersion = (String) result;
        } catch (Exception e) {
        }

        return strBaseBandVersion;
    }

    /**
     * 获取内核版本
     */
    public static String getKernelVersion() {
        String kernerVersion = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {

        }
        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);
        String result = "";
        String line;
        // get the whole standard output string
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {

        }

        if (!TextUtils.isEmpty(result)) {
            String Keyword = "version ";
            int index = result.indexOf(Keyword);
            line = result.substring(index + Keyword.length());
            kernerVersion = line;
        }

        return kernerVersion;
    }

    /**
     * 获取源码控制版本号
     */
    public static String getInternalVersion() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * 当前设备是否为android5.0以上机型
     */
    public static boolean isLollipop() {
        return getSdkVersion() >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 手机是否 root
     */
    public static boolean isRoot() {
        int kSystemRootStateUnknow = -1;
        int kSystemRootStateDisable = 0;
        int kSystemRootStateEnable = 1;
        int systemRootState = kSystemRootStateUnknow;
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemRootState = kSystemRootStateDisable;
        if (systemRootState == kSystemRootStateEnable) {
            return true;
        } else if (systemRootState == kSystemRootStateDisable) {
            return false;
        }
        return false;
    }

    /**
     * 获取安卓id（设备初始化时随机生成，不需权限可以获取，恢复出厂设置后会重置）
     *
     * @return android id
     */
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取开机时间
     *
     * @return long
     */
    public static long getBootTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return SystemClock.elapsedRealtimeNanos() / 1000000;
        }
        return 0;
    }

}
