package com.sfb.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pref.GuardPreference_;
import com.sfb.baselib.data.PicTextInfo;
import com.sfb.baselib.data.UserInfo;

import java.util.List;

/**
 * Created by wuwc on 2020/8/10.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 */

public class ConvenienceServicePreference {
    private static ConvenienceServicePreference instance;

    private static SharedPreferences preference;

    private static SharedPreferences.Editor editor;

    private GuardPreference_ guardPreference;


    private ConvenienceServicePreference(Context context) {
        preference = context.getSharedPreferences("convenience_service_pref", 0);
        editor = preference.edit();
        guardPreference = GuardPreference_.getInstance(context);
    }

    public static ConvenienceServicePreference getInstance(Context context) {
        if (instance == null) {
            instance = new ConvenienceServicePreference(context);
        }
        return instance;
    }

    /**
     * 根据用户账号id来区分，获取便民服务。用于存储用户编辑过的便民服务项
     *
     * @return
     */
    public List<PicTextInfo> getConvenienceService() {
        synchronized (ConvenienceServicePreference.class) {
            UserInfo userInfo = guardPreference.getUserInfo();
            if (preference != null) {
                String userId = userInfo == null ? "" : userInfo.getId();
                String json = preference.getString("convenience_service_" + userId, "");
                if (TextUtils.isEmpty(json)) {
                    return null;
                }
                return new Gson().fromJson(json, new TypeToken<List<PicTextInfo>>() {
                }.getType());
            }
            return null;
        }
    }

    /**
     * 根据用户账号id来区分，存放便民服务。用于存储用户编辑过的便民服务项
     *
     * @return
     */
    public void putConvenienceService(List<PicTextInfo> convenienceService) {
        synchronized (ConvenienceServicePreference.class) {
            UserInfo userInfo = guardPreference.getUserInfo();
            if (editor != null) {
                String userId = userInfo == null ? "" : userInfo.getId();
                String json = "";
                if (convenienceService != null) {
                    json = new Gson().toJson(convenienceService);
                }
                editor.putString("convenience_service_" + userId, json).apply();
            }
        }
    }

    public void putAllConvenienceService(List<PicTextInfo> convenienceService) {
        synchronized (ConvenienceServicePreference.class) {
            if (editor != null) {
                String json = "";
                if (convenienceService != null) {
                    json = new Gson().toJson(convenienceService);
                }
                editor.putString("all_convenience_service", json).apply();
            }
        }
    }

    public List<PicTextInfo> getAllConvenienceService() {
        synchronized (ConvenienceServicePreference.class) {
            if (preference != null) {
                String json = preference.getString("all_convenience_service", "");
                if (TextUtils.isEmpty(json)) {
                    return null;
                }
                return new Gson().fromJson(json, new TypeToken<List<PicTextInfo>>() {
                }.getType());
            }
            return null;
        }
    }

}
