package com.sfb.baselib.route;

import com.sfb.baselib.route.fragmentroute.BasicCheckFragmentRoute;
import com.sfb.baselib.route.fragmentroute.ClueDisposeFragmentRoute;
import com.sfb.baselib.route.fragmentroute.ClueFragmentRoute;
import com.sfb.baselib.route.fragmentroute.HomeFragmentRoute;
import com.sfb.baselib.route.fragmentroute.HouseFragmentRoute;
import com.sfb.baselib.route.fragmentroute.LegalCaseFragmentRoute;
import com.sfb.baselib.route.fragmentroute.MineFragmentRoute;
import com.sfb.baselib.route.fragmentroute.NoticeFragmentRoute;
import com.sfb.baselib.route.fragmentroute.TaskFragmentRoute;
import com.sfb.baselib.route.fragmentroute.TrainFragmentRoute;
import com.sfb.baselib.route.fragmentroute.UserManagerFragmentRoute;

/**
 * Fragment的路由
 */
public class FragmentRoute implements
        HomeFragmentRoute,//首页模块
        MineFragmentRoute,//我的模块
        UserManagerFragmentRoute,//用户管理模块
        NoticeFragmentRoute,//通知公告模块
        LegalCaseFragmentRoute,//案件录入模块
        ClueFragmentRoute,//线索举报模块
        ClueDisposeFragmentRoute,//线索处置模块
        HouseFragmentRoute,//出租屋模块
        TaskFragmentRoute,//任务模块
        TrainFragmentRoute,//学习培训模块
        BasicCheckFragmentRoute//基础排查模块
{
}
