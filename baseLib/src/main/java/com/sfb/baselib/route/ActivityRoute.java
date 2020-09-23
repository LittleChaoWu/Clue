package com.sfb.baselib.route;

import com.sfb.baselib.route.activityroute.BasicCheckActivityRoute;
import com.sfb.baselib.route.activityroute.CarActivityRoute;
import com.sfb.baselib.route.activityroute.ClueActivityRoute;
import com.sfb.baselib.route.activityroute.ClueDisposeActivityRoute;
import com.sfb.baselib.route.activityroute.CollectActivityRoute;
import com.sfb.baselib.route.activityroute.GasActivityRoute;
import com.sfb.baselib.route.activityroute.HomeActivityRoute;
import com.sfb.baselib.route.activityroute.HouseActivityRoute;
import com.sfb.baselib.route.activityroute.LegalCaseActivityRoute;
import com.sfb.baselib.route.activityroute.LoginActivityRoute;
import com.sfb.baselib.route.activityroute.MapActivityRoute;
import com.sfb.baselib.route.activityroute.MediaActivityRoute;
import com.sfb.baselib.route.activityroute.MineActivityRoute;
import com.sfb.baselib.route.activityroute.NoticeActivityRoute;
import com.sfb.baselib.route.activityroute.PatrolActivityRoute;
import com.sfb.baselib.route.activityroute.PersonActivityRoute;
import com.sfb.baselib.route.activityroute.RegisterCheckActivityRoute;
import com.sfb.baselib.route.activityroute.ScanCodeActivityRoute;
import com.sfb.baselib.route.activityroute.ShareActivityRoute;
import com.sfb.baselib.route.activityroute.SignActivityRoute;
import com.sfb.baselib.route.activityroute.SpecialIndustryActivityRoute;
import com.sfb.baselib.route.activityroute.SplashActivityRoute;
import com.sfb.baselib.route.activityroute.TaskActivityRoute;
import com.sfb.baselib.route.activityroute.TrainActivityRoute;
import com.sfb.baselib.route.activityroute.UpdateActivityRoute;
import com.sfb.baselib.route.activityroute.UploadActivityRoute;
import com.sfb.baselib.route.activityroute.UserManagerActivityRoute;
import com.sfb.baselib.route.activityroute.WebActivityRoute;

/**
 * Activity的路由
 */
public class ActivityRoute implements
        SplashActivityRoute,//启动页模块
        HomeActivityRoute,//首页模块
        LoginActivityRoute,//登陆模块
        UploadActivityRoute,//上传模块
        MediaActivityRoute,//媒体模块
        UpdateActivityRoute,//版本更新模块
        MapActivityRoute,//地图模块
        WebActivityRoute,//Web模块
        MineActivityRoute,//我的模块
        UserManagerActivityRoute,//用户管理模块
        RegisterCheckActivityRoute,//注册审核模块
        NoticeActivityRoute,//通知公共模块
        PatrolActivityRoute,//自主巡逻模块
        ScanCodeActivityRoute,//扫码/二维码模块
        CollectActivityRoute,//采集模块
        LegalCaseActivityRoute,//案件录入模块
        ClueActivityRoute,//线索举报模块
        ClueDisposeActivityRoute,//线索处置模块
        HouseActivityRoute,//出租屋模块
        CarActivityRoute,//车辆采集模块
        PersonActivityRoute,//人员采集模块
        TaskActivityRoute,//任务模块
        GasActivityRoute,//汽油模块
        TrainActivityRoute,//学习培训模块
        SpecialIndustryActivityRoute,//特行巡检模块
        BasicCheckActivityRoute,//基础排查模块
        SignActivityRoute,//签到打卡模块
        ShareActivityRoute//分享模块
{
}
