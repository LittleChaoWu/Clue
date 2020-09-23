package com.sfb.baselib.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Toast统一管理类
 */
public class ToastUtils {

    private static final int LONG_DELAY = 3000;
    private static final int SHORT_DELAY = 2000;
    private static Toast mToast;
    private static Runnable r = new Runnable() {
        public void run() {
            if (mToast != null) {
                mToast.cancel();
            }
        }
    };

    /**
     * 显示Toast
     *
     * @param context  上下文
     * @param message  文字
     * @param duration 时间长度
     */
    private static void show(final Context context, final CharSequence message, final int duration) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }

        UiHandler.removeCallbacks(r);
        UiHandler.postDelayed(r, duration == Toast.LENGTH_LONG ? LONG_DELAY : SHORT_DELAY);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            //主线程直接显示
            actionShow(context, message, duration);
        } else {
            //非主线程post到主线程显示
            UiHandler.post(new Runnable() {
                @Override
                public void run() {
                    actionShow(context, message, duration);
                }
            });
        }
    }

    private static void actionShow(final Context context, final CharSequence message, final int duration) {
        if (mToast != null) {
            mToast.setText(message);
        } else {
            mToast = Toast.makeText(context.getApplicationContext(), message, duration);
        }
        int yOffset = 0;
        mToast.setGravity(Gravity.CENTER, 0, yOffset);
        mToast.show();
    }

    /**
     * 显示短时间的Toast
     *
     * @param context 上下文
     * @param str     文字
     */
    public static void showShort(final Context context, final CharSequence str) {
        show(context, str, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时间的Toast
     *
     * @param context  上下文
     * @param strResId 文字资源id
     */
    public static void showShort(final Context context, @StringRes final int strResId) {
        if (context == null) {
            return;
        }
        show(context, context.getString(strResId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的Toast
     *
     * @param context 上下文
     * @param str     文字
     */
    public static void showLong(final Context context, final CharSequence str) {
        show(context, str, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的Toast
     *
     * @param context  上下文
     * @param strResId 文字资源id
     */
    public static void showLong(final Context context, @StringRes final int strResId) {
        if (context == null) {
            return;
        }
        show(context, context.getString(strResId), Toast.LENGTH_LONG);
    }
}
