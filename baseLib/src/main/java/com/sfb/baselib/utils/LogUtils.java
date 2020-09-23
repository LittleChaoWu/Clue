package com.sfb.baselib.utils;

import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 日志工具类
 */
public class LogUtils {

    private static boolean mLoggable = true;
    private static String mLogFilePath;
    private static final String DEFAULT_TAG = "MY_PROJECT";

    /**
     * /**
     * 初始化Log组件
     *
     * @param TAG      设置全局TAG，默认为MY_PROJECT
     * @param loggable 是否打印日志，默认为true
     */
    public static void init(String TAG, boolean loggable) {
        mLoggable = loggable;
        if (TextUtils.isEmpty(TAG)) {
            TAG = DEFAULT_TAG;
        }
        FormatStrategy formatStrategy =
            PrettyFormatStrategy.newBuilder().showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(TAG)               // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return mLoggable;
            }
        });
    }

    /**
     * 初始化Log组件
     *
     * @param TAG         设置全局TAG，默认为MY_PROJECT
     * @param loggable    是否打印日志，默认为true
     * @param logFilePath 将日志写到文件的文件路径
     */
    public static void init(String TAG, boolean loggable, String logFilePath) {
        mLogFilePath = logFilePath;
        init(TAG, loggable);
    }

    /**
     * 单独设置是否打印日志
     */
    public static void setLoggable(boolean Loggable) {
        mLoggable = Loggable;
    }

    /**
     * 打印v级日志
     */
    public static void v(String msg) {
        if (mLoggable) {
            Logger.v(msg);
        }
    }

    /**
     * 打印v级日志
     */
    public static void v(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).v(msg);
        }
    }

    /**
     * 打印d级日志
     */
    public static void d(String msg) {
        if (mLoggable) {
            Logger.d(msg);
        }
    }

    /**
     * 打印d级日志
     */
    public static void d(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).d(msg);
        }
    }

    /**
     * 打印i级日志
     */
    public static void i(String msg) {
        if (mLoggable) {
            Logger.i(msg);
        }
    }

    /**
     * 打印i级日志
     */
    public static void i(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).i(msg);
        }
    }

    /**
     * 打印w级日志
     */
    public static void w(String msg) {
        if (mLoggable) {
            Logger.w(msg);
        }
    }

    /**
     * 打印w级日志
     */
    public static void w(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).w(msg);
        }
    }

    /**
     * 打印e级日志
     */
    public static void e(String msg) {
        if (mLoggable) {
            Logger.e(msg);
        }
    }

    /**
     * 打印e级日志
     */
    public static void e(String msg, Throwable throwable) {
        if (mLoggable) {
            Logger.e(throwable, msg);
        }
    }

    /**
     * 打印e级日志
     */
    public static void e(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).e(msg);
        }
    }

    /**
     * 打印e级日志
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (mLoggable) {
            Logger.t(tag).e(throwable, msg);
        }
    }

    /**
     * 格式化json数据并打印（d级）
     */
    public static void json(String json) {
        if (mLoggable) {
            Logger.json(json);
        }
    }

    /**
     * 格式化json数据并打印（d级）
     */
    public static void json(String tag, String json) {
        if (mLoggable) {
            Logger.t(tag).json(json);
        }
    }

    /**
     * 格式化xml数据并打印（d级）
     */
    public static void xml(String xml) {
        if (mLoggable) {
            Logger.xml(xml);
        }
    }

    /**
     * 格式化xml数据并打印（d级）
     */
    public static void xml(String tag, String xml) {
        if (mLoggable) {
            Logger.t(tag).xml(xml);
        }
    }

    /**
     * 打印d级日志，并写入文件中（文件路径初始化时传入）<br/>
     * 打印日志与loggable参数有关，写入文件无关
     */
    public static void log2File(String msg) {
        log2File(DEFAULT_TAG, msg);
    }

    /**
     * 打印d级日志，并写入文件中（文件路径初始化时传入）<br/>
     * 打印日志与loggable参数有关，写入文件无关
     */
    public static void log2File(String tag, String msg) {
        if (mLoggable) {
            Logger.t(tag).d(msg);
        }
        if (TextUtils.isEmpty(mLogFilePath)) {
            return;
        }
        write2File(tag, msg);
    }

    /**
     * 打印e级日志，并写入文件中（文件路径初始化时传入）<br/>
     * 打印日志与loggable参数有关，写入文件无关
     */
    public static void logException2File(String msg, Throwable throwable) {
        logException2File(DEFAULT_TAG, msg, throwable);
    }

    /**
     * 打印e级日志，并写入文件中（文件路径初始化时传入）<br/>
     * 打印日志与loggable参数有关，写入文件无关
     */
    public static void logException2File(String tag, String msg, Throwable throwable) {
        if (mLoggable) {
            Logger.t(tag).e(throwable, msg);
        }
        if (TextUtils.isEmpty(mLogFilePath)) {
            return;
        }
        write2File(tag, new StringBuilder(msg).append("      ").append(throwableStackTrace(throwable)).toString());
    }

    /**
     * 输出异常信息
     * @param throwable 异常
     */
    private static String throwableStackTrace(Throwable throwable) {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\n").append("=========================================Throwable Stack Trace Start================================================").append("\r\n\t");
        if (throwable != null) {
            sb.append("LocalizedMessage = ").append(throwable.getLocalizedMessage()).append("\r\n\t")
                .append("Message = ").append(throwable.getMessage()).append("\r\n\t")
                .append("Cause = ").append(throwable.getCause()).append("\r\n")
                .append("throwable toString = ").append(throwable);
            for (StackTraceElement element : throwable.getStackTrace()) {
                sb.append("\r\n\t").append(element);
            }
        }
        sb.append("\r\n").append("===========================================Throwable Stack Trace End==============================================");
        return sb.length() == 0 ? null : sb.toString();
    }

    private static void write2File(String tag, String msg) {
        long time = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(DateUtils.formatTime(time)).append("     ").append(tag).append("     ").append(msg).append("\r\n");
        RWFileUtils.saveString(mLogFilePath, sb.toString(), true);
    }
}
