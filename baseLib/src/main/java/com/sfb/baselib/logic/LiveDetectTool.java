package com.sfb.baselib.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.sfb.baselib.R;
import com.sfb.baselib.data.LiveDetectResult;
import com.sfb.baselib.utils.ToastUtils;


public class LiveDetectTool {

    public static final String LIVE_DETECT_APPKEY = /*"87acd7bc9a7d11e6a2470050569a506a"*/"3e4f34c25829435ab39789d1566723fb";
    public static final String LIVE_DETECT_MODE_SERIAL = "8";
    public static final String DES_KEY = "e7003840e096afde9592d623735e87f2";

    private Context context;

    public LiveDetectTool(Context context) {
        this.context = context;
    }

    /**
     * 解析活体检测界面采集回来的照片结果
     *
     * @param intent
     * @return
     */
    public byte[] parsePluginCollectResult(Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle result = intent.getBundleExtra("result");
        boolean check_pass = result.getBoolean("check_pass");
        String mBadReason = result.getString("mBadReason");
        if (check_pass) {
            byte[] pic_thumbnail = result.getByteArray("pic_thumbnail");
            byte[] pic = result.getByteArray("encryption");
            if (pic_thumbnail != null && pic != null) {
                return pic;
            } else {
                ToastUtils.showLong(context, "抱歉！您的动作不符合");
            }
        }
        return null;
    }

    /**
     * 显示活体检测结果
     *
     * @param liveDetectResult
     */
    public boolean showLiveResult(LiveDetectResult liveDetectResult) {
        if (liveDetectResult == null) {
            ToastUtils.showShort(context, R.string.live_detect_no_pass);
            return false;
        }

        if (TextUtils.isEmpty(liveDetectResult.getAuthenResult())) {
            ToastUtils.showShort(context, R.string.live_detect_no_pass);
            return false;
        }

        String maskString = liveDetectResult.getAuthenResult();
        maskString = maskString.substring(0, 2);
        if (maskString.equals("00")) {
            return true;
        } else {
            String first = maskString.substring(0, 1);
            String second = maskString.substring(1, 2);
            StringBuilder sb = new StringBuilder();
            if (first.equals("1")) {
                sb.append("保留数据匹配失败,");
            } else if (first.equalsIgnoreCase("2")) {
                sb.append("该序列已失效");
            } else if (first.equalsIgnoreCase("3")) {
                sb.append("查找不到该认证");
            } else if (first.equalsIgnoreCase("4")) {
                sb.append("该序列已失效");
            } else if (first.equalsIgnoreCase("5")) {
                sb.append("保留数据匹配失败");
            } else if (first.equalsIgnoreCase("6")) {
                sb.append("保留数据匹配失败");
            } else if (first.equalsIgnoreCase("7")) {
                sb.append("系统错误");
            } else if (first.equalsIgnoreCase("X")) {
                sb.append("程序未执行");
            }
            if (second.equalsIgnoreCase("1")) {
                sb.append("人像匹配失败,");
            } else if (second.equalsIgnoreCase("2")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("3")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("4")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("5")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("A")) {
                sb.append("对比数据操作异常");
            } else if (second.equalsIgnoreCase("B")) {
                sb.append("对比系统异常");
            } else if (second.equalsIgnoreCase("C")) {
                sb.append("业务流水号出错");
            } else if (second.equalsIgnoreCase("D")) {
                sb.append("找不到流水号");
            } else if (second.equalsIgnoreCase("E")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("F")) {
                sb.append("人像匹配失败");
            } else if (second.equalsIgnoreCase("G")) {
                sb.append("对比系统异常");
            }
            String message = !TextUtils.isEmpty(sb.toString()) ? sb.toString() : context.getString(R.string.live_detect_no_pass);
            ToastUtils.showShort(context, message);
        }
        return false;
    }

}
