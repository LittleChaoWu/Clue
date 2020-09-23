package com.sfb.baselib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CustomExceptionHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CustomExceptionHandler";

    private boolean isSaveDumpFile;
    private String dumpFilePath;

    private boolean isSaveUserName;
    private String userName;

    private UncaughtExceptionHandler mDefaultHandler;
    private static CustomExceptionHandler mInstance = null;
    private Context mContext;
    private Map<String, String> infos = new HashMap<String, String>();

    private CustomExceptionHandler(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static CustomExceptionHandler getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CustomExceptionHandler.class) {
                if (mInstance == null) {
                    mInstance = new CustomExceptionHandler(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void init(String dumpFilePath) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setSavePath(dumpFilePath);
    }

    /**
     * 设置dump日志保存的文件路径
     *
     * @param dumpFilePath dump文件路径
     */
    public void setSavePath(String dumpFilePath) {
        if (!TextUtils.isEmpty(dumpFilePath)) {
            this.isSaveDumpFile = true;
            this.dumpFilePath = dumpFilePath;
        }
    }

    /**
     * 设置dump文件中的USER_NAME字段
     *
     * @param currentUserName 当前用户名
     */
    public void setUserName(String currentUserName) {
        if (!TextUtils.isEmpty(currentUserName)) {
            this.isSaveUserName = true;
            this.userName = currentUserName;
        }
    }

    @Override public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectDeviceInfo(mContext);
        saveCrashInfo2File(ex);
        return false;
    }

    /**
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("VERSION_NAME", versionName);
                infos.put("VERSION_CODE", versionCode);
            }
        } catch (NameNotFoundException e) {
            LogUtils.e("an error occured when collect package info  " + e.toString());
        }
        if (isSaveUserName) {
            infos.put("USER_NAME", userName);
        }
        infos.put("ANDROID_VERSION", DeviceUtils.getReleaseVersion());
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogUtils.e("an error occured when collect crash info  " + e.toString());
            }
        }
    }

    private boolean saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            if (StorageUtils.isSDCardExisted()) {
                if (!FileUtils.isFileExist(dumpFilePath)) {
                    FileUtils.createFile(dumpFilePath);
                }
                RWFileUtils.saveString(dumpFilePath, sb.toString());
            }
            return true;
        } catch (Exception e) {
            LogUtils.e("an error occured while writing file...  " + e.toString());
        }
        return false;
    }
}
