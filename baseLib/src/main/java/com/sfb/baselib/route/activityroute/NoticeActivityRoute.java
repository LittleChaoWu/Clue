package com.sfb.baselib.route.activityroute;

/**
 * 通知公共模块的Activity路由
 */
public interface NoticeActivityRoute {

    String NOTICE_PATH = "/notice/NoticeActivity";

    String ADD_NOTICE_PATH = "/notice/AddNoticeActivity";

    String NOTICE_DETAIL_PATH = "/notice/NoticeDetailActivity";

    String PICK_NOTICE_RECEIVER_PATH = "/notice/PickNoticeReceiverActivity";

    interface NoticeDetailActivityKey {
        String NOTICE_INFO_KEY = "noticeInfo";

        //通知公告类型
        String NOTICE_TYPE_NORMAL = "0";
        String NOTICE_TYPE_URL = "1";
    }

}
