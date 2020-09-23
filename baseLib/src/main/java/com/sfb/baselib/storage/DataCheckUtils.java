package com.sfb.baselib.storage;

import android.content.Context;

import com.pref.GuardPreference_;
import com.sfb.baselib.Constants;
import com.sfb.baselib.data.UserGroup;
import com.sfb.baselib.data.UserInfo;

import java.util.List;

/**
 * Created by wuwc on 2020/7/31.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 */

public class DataCheckUtils {
    /**
     * 民警角色
     *
     * @param context
     * @return
     */
    public static boolean isPolice(Context context) {
        GuardPreference_ preference = GuardPreference_.getInstance(context);
        return preference.getRole() == Constants.ROLE_MANAGER && preference.getUserInfo().getSource() != 5;
    }

    /**
     * 保安管理员角色
     * 20200731需求修改：用于识别角色为“保安管理员”：userInfo.getSource() == 5&&type==0
     *
     * @param context
     * @return
     */
    public static boolean isBaoAnAdmin(Context context) {
        GuardPreference_ preference = GuardPreference_.getInstance(context);
        return preference.getRole() == Constants.ROLE_MANAGER && preference.getUserInfo().getSource() == 5;
    }

    /**
     * 普通游客
     *
     * @param context
     * @return
     */
    public static boolean isYouKe(Context context) {
        GuardPreference_ preference = GuardPreference_.getInstance(context);
        UserInfo userInfo = preference.getUserInfo();
        if (userInfo != null) {
            List<UserGroup> userGroups = preference.getUserInfo().getUserGroups();
            for (int i = 0; i < userGroups.size(); i++) {
                UserGroup group = userGroups.get(i);
                if (group.getCode().equals(Constants.VISITOR)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 群防角色
     *
     * @param context
     * @return
     */
    public static boolean isMonitoringRole(Context context) {
        GuardPreference_ preference = GuardPreference_.getInstance(context);
        UserInfo userInfo = preference.getUserInfo();
        if (userInfo != null) {
            List<UserGroup> userGroups = preference.getUserInfo().getUserGroups();
            for (int i = 0; i < userGroups.size(); i++) {
                UserGroup group = userGroups.get(i);
                if (group.getCode().equals(Constants.UG_BABA) ||
                        group.getCode().equals(Constants.UG_WYBA) ||
                        group.getCode().equals(Constants.UG_ZZBA) ||
                        group.getCode().equals(Constants.VISITOR)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 普通保安
     *
     * @param context
     * @return
     */
    public static boolean isBaoAn(Context context) {
        GuardPreference_ preference = GuardPreference_.getInstance(context);
        UserInfo userInfo = preference.getUserInfo();
        if (userInfo != null) {
            List<UserGroup> userGroups = preference.getUserInfo().getUserGroups();
            for (int i = 0; i < userGroups.size(); i++) {
                UserGroup group = userGroups.get(i);
                if (group.getCode().equals(Constants.UG_BABA) ||
                        group.getCode().equals(Constants.UG_WYBA) ||
                        group.getCode().equals(Constants.UG_ZZBA)) {
                    return true;
                }
            }
        }
        return false;
    }
}
