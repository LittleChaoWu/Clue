package com.sfb.baselib.storage;

import com.pref.annotations.DefaultBoolean;
import com.pref.annotations.DefaultInt;
import com.pref.annotations.DefaultString;
import com.pref.annotations.ObjectType;
import com.pref.annotations.PrefKey;
import com.pref.annotations.SharePref;
import com.sfb.baselib.Constants;
import com.sfb.baselib.data.AccountInfo;
import com.sfb.baselib.data.HomeItemInfo;
import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.network.NetworkConfig;

import java.util.List;

@SharePref(name = "guard_cloud_pref")
public class GuardPreference {

    @PrefKey(key = "token")
    private String token;//token

    @PrefKey(key = "environment_value")
    @DefaultInt(NetworkConfig.OFFICIAL_ENV)
    private int environmentValue;//网络环境

    @PrefKey(key = "server_time")
    private long serverTime;//登录时记录的服务端时间

    @PrefKey(key = "boot_time")
    private long bootTime;//登录时记录的开机时间

    @PrefKey(key = "role")
    @DefaultInt(Constants.ROLE_GUARDER)
    private int role;//用户角色

    @PrefKey(key = "user_info")
    @ObjectType
    private UserInfo userInfo;//用户信息

    @PrefKey(key = "account_info")
    @ObjectType
    private AccountInfo accountInfo;//账号信息

    @PrefKey(key = "is_first")
    @DefaultBoolean(true)
    private boolean isFirst;//是否首次启动

    @PrefKey(key = "city")
    private String city;//所在城市

    @PrefKey(key = "longitude")
    @DefaultString("0")
    private String longitude;//经度

    @PrefKey(key = "latitude")
    @DefaultString("0")
    private String latitude;//维度

    @PrefKey(key = "address")
    private String address;//地址

    @PrefKey(key = "radius")
    private float radius;//经度

    @PrefKey(key = "is_show_task_execute_tip")
    @DefaultBoolean(true)
    private boolean isShowTaskExecuteTip;//是否显示任务执行提示

    @PrefKey(key = "gesture_password")
    private String gesturePassword;//手势密码

    @PrefKey(key = "is_first_set_gesture")
    @DefaultBoolean(true)
    private boolean isFirstSetGesture;//是否第一次设置手势

    @PrefKey(key = "is_sdk_login")
    @DefaultBoolean()
    private boolean isSdkLogin;//是否是通过sdk单点登录

    @PrefKey(key = "common_service")
    @ObjectType
    private List<HomeItemInfo> commonService;

    @PrefKey(key = "manager_service")
    @ObjectType
    private List<HomeItemInfo> managerService;

    @PrefKey(key = "jm_service")
    @ObjectType
    private List<HomeItemInfo> jmService;//警民联防

    @PrefKey(key = "jb_service")
    @ObjectType
    private List<HomeItemInfo> jbService;//警保联控

    @PrefKey(key = "user_list")
    @ObjectType
    private List<AccountInfo> userList;//只用于debug测试

}
