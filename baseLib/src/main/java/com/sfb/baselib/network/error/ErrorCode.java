package com.sfb.baselib.network.error;

public class ErrorCode {

    //系统级异常
    public static final String APP_VERSION_LOW = "1006";//版本过低异常
    //用户相关异常
    public static final String SESSION_OVER = "603";//用户会话丢失
    public static final String SESSION_OVERDUE = "1001";//toke过期
    public static final String TOKEN_OVERDUE = "2000";//toke过期
    public static final String REMOTE_LOGIN = "2012";//账号在其他地方登录

}
