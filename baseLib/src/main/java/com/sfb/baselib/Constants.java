package com.sfb.baselib;

public class Constants {

    //首页Item项------------------------------------------------------------------------------------
    public static final String NOTICE = "notice";//通知公告
    public static final String TASK = "task";//群防任务(发布)
    public static final String TASK_EXECUTE = "task_execute";//群防任务(执行)
    public static final String CLUE = "clue";//线索举报
    public static final String CLUE_DISPOSE = "clue_dispose";//线索处置
    public static final String TRAIN = "train";//学习培训
    public static final String PATROL = "patrol";//我要巡逻
    public static final String CASES = "cases";//案例录入
    public static final String COLLECT = "collect";//我要采集
    public static final String COLLECT_CAR = "collect_car";//车辆采集
    public static final String COLLECT_PERSON = "collect_person";//人员采集
    public static final String GAS_MANAGER = "gas_manager";//汽油管理
    public static final String RENTAL_MANAGER = "rental_manager";//房屋出租管理
    public static final String SPECIAL_INDUSTRY_MANAGER = "special_industry_manager";//特殊行业管理
    public static final String USER_MANAGER = "user_manager";//人员管理
    public static final String REGISTER_USER_MANAGER = "register_user_manager";//注册信息审核
    public static final String ONLINE_SHOW = "online_show";//在线力量
    public static final String SPECIAL_CHECK = "special_check";//特殊行业巡检
    public static final String TIME_OFF_CHECK = "time_off_check";//请假审批
    public static final String SIGN = "sign";//打卡签到
    public static final String CALL_POLICE = "call_police";//警情报警
    public static final String CONVENIENCE_SERVICE = "convenience_services";//便民服务
    public static final String ONE_BUTTON_ALARM_110 = "one_button_service_110";//一键报警-110
    public static final String ONE_BUTTON_ALARM_122 = "one_button_service_122";//一键报警-120
    public static final String EMPTY = "empty";//空
    //----------------------------------------------------------------------------------------------

    public static final int SIGN_START_DISTANCE = 500;//执行任务范围限制（单位：米）

    //系统常量列表关键字
    public static final String MASS_SOCIETY_LIST = "mass_society";//群防社会人员
    public static final String MASS_HELP_LIST = "mass_help";//群防协助人员
    public static final String NOTICE_INFO_TYPE = "notice_info_type";//发布资讯的类型
    public static final String AREA_LIST = "area_code";//所属地区
    public static final String POLICE_STATION_LIST = "police_station";//派出所列表
    public static final String COLL_HIRE_TYPE = "COLL_HIRE_TYPE";//出租屋类型
    public static final String CERTIFICATE_TYPE = "CERTIFICATE_TYPE";//证件类型
    public static final String NATIONALITY = "nationality";//民族
    public static final String POLITICS_LIST = "politics";//政治面貌列表
    public static final String CULTURE_LIST = "culture";//文化程度列表
    public static final String JOB_LIST = "job";//职业列表
    public static final String TRAFFIC_POLICE_LIST = "traffic_police";//意向交警队列表

    //APP用户角色
    public static final int ROLE_MANAGER = 0;//管理员
    public static final int ROLE_GUARDER = 1;//群众
    public static final int ROLE_OTHER = 2;//其他角色

    //审核通过状态
    public static final int APPLY_UNDO = 0;//待审核
    public static final int APPLY_SUCCESS = 1;//审核通过
    public static final int APPLY_FAIL = 2;//审核不通过

    //厦门才有的辅助力量角色Code
    public static final String YWJJ_CODE = "UG_VTP";//义务交警角色CODE
    public static final String YWFP_CODE = "UG_OFT";//义务反扒角色CODE
    public static final String YW_110_CODE = "UG_110";//110角色CODE
    public static final String XXSBYWR_CODE = "UG_XXCJZRR";//信息申报义务人CODE
    public static final String WYGLRY_CODE = "UG_WYGLRY";//物业管理人员CODE
    public static final String CZWFD_CODE = "UG_CZFFD";//出租屋房东角色CODE
    //府谷才有的辅助力量角色Code
    public static final String QYGLY_CODE = "UG_QYGLY";//加油站汽油管理人员角色CODE
    public static final String DWFZR_CODE = "UG_DWFZR";//单位负责人角色CODE
    //厦门才有的专职力量
    public static final String GAWZ_CODE = "UG_GAWZ";//公安文职CODE
    public static final String FJ_CODE = "UG_FJ";//警辅角色CODE
    public static final String HCD_CODE = "UG_HCD";//护村队角色CODE
    public static final String PZS_CODE = "UG_PZS";//派驻所角色CODE
    public static final String WGY_CODE = "UG_COURIER";//网格员角色CODE
    public static final String TQXJ_CODE = "UG_TQXJ";//特勤协警角色CODE
    public static final String XJ_CODE = "UG_XJ";//学警角色CODE
    //临沂才有的力量类型
    public static final String UG_WYBA = "WYGSBA";//物业公司保安角色CODE
    public static final String UG_BABA = "BAGSBA";//保安公司保安角色CODE
    public static final String UG_ZZBA = "ZZBA";//自招保安角色CODE
    public static final String VISITOR = "VISITOR";//游客角色CODE


    //辅助力量角色Name
    public static final String YWFPD_NAME = "义务反扒队员";
    public static final String XXSBYWR_NAME = "信息采集责任人";
    public static final String YWJJD_NAME = "义务交警队员";
    public static final String SSPPK_NAME = "110随手拍拍客";
    public static final String FJ_NAME = "警辅（治安协警、交通协警）";
    public static final String WGY_NAME = "社区网格员";
    public static final String HCD_NAME = "护村队";
    public static final String PZS_NAME = "派驻所";

    //上传限制
    public static final long LIMIT_UNIT = 1024 * 1024;
    public static final long LIMIT_RANGE_OUT = 50;
    public static final long LIMIT_MAX_VIDEO = 100;

    //任务类型--1,通用任务，2-定向任务 3--临时任务
    public static final String TYPE_GENERAL = "general";//通用任务
    public static final String TYPE_DIRECTIONAL = "directional";//定向任务
    public static final String TYPE_TEMP = "temp";//临时任务

    //采集任务的类型
    public static final String CAR_COLLECT_TASK_REPORT = "car-info-collection";//车辆
    public static final String PEOPLE_COLLECT_TASK_REPORT = "people-info-collection";//人员
    public static final String CLUE_TASK_REPORT = "clue";//线索
    public static final String HOUSE_COLLECT_TASK_REPORT = "house-info-collection";//房屋

    //自主巡逻类型
    public static final String SELF_PATROL_CATEGORY_TEMP = "self-category";
    public static final String SELF_PATROL_SUBCATEGORY_TEMP = "self-subcategory";

    //主任务类别
    public static final String GENERAL_PUBLIC = "general-public";//群防任务
    public static final String DUTY_TRAFFIC_POLICE = "duty-traffic-police";//义务交警任务
    public static final String DUTY_ANTI_PICKPOCKET = "duty-anti-pickpocket";//义务反扒任务
    public static final String INFO_COLLECTION_TASK = "info-collection";//信息采集

    //子任务类别
    //群防任务子类别
    public static final String PEACE_PATROL = "peace-patrol";//治安巡逻
    public static final String SALVATION = "salvation";//求助孤寡
    public static final String GENERA_PUBLIC_PROPAGANDA = "genera-public-propaganda";//群防宣传
    public static final String CLUE_REPORT = "clue-report";//线索征集举报
    public static final String ZHAN_GAN = "zhan-gang";//站岗

    //义务交警任务子类
    public static final String TRAINING_ONE = "trainning-one";//入队培训科目一
    public static final String TRAINING_TWO = "trainning-two";//入队培训科目二
    public static final String TRAINING_THREE = "trainning-three";//入队培训科目三
    public static final String TE_QIN = "te-qin";//特勤
    public static final String NEI_QIN = "nei-qin";//内勤
    public static final String WAI_QIN = "wai-qin";//外勤
    public static final String TRAINING = "trainning";//培训

    //义务反扒任务
    public static final String KEEP_WATCH_PROPAGANDA = "keep-watch-propaganda";//防范宣传
    public static final String KEEP_STATION = "keep-station";//维持站点秩序
    public static final String CAR_ANTI_PICKPOCKET = "car-anti-pickpocket";//跟车反扒
    public static final String LARGE_ACTIVITY = "large-activity";//全市大型活动

    //信息采集任务小类
    public static final String CAR_INFO_COLLECTION = "car-info-collection";//车辆采集任务类型小类
    public static final String HOUSE_INFO_COLLECTION = "house-info-collection";//出租屋采集任务类型小类
    public static final String PEOPLE_INFO_COLLECTION = "people-info-collection";//人员采集任务类型小类
    public static final String ILLEGAL_PARK_COLLECTION = "illegal-park-collection";//违章采集任务类型小类

    //子任务状态
    public static final String WAIT_EXECUTED = "not-executed";//待执行
    public static final String EXECUTING = "executing";//执行中
    public static final String NOT_SUBMIT = "not-submit";//未提交
    public static final String NOT_COMPLETED = "not-completed";//未完成
    public static final String COMPLETED = "completed";//已完成
    public static final String CONFIRMED = "confirmed";//已确认（积分已生效）
    public static final String CANCELED = "cancelled";//已取消
    public static final String COMMENTED = "commented";//已评价

    public static final Float UNIT_STEP = 0.5f;//0.5m/步
    public static final Float UNIT_KA = 0.064f;//0.064大卡/米

    //便民服务模块及链接地址
    public static final String ROAD_CONDITIONS_URL = "https://map.baidu.com/mobile/webapp/index/index/foo=bar/vt=map&traffic=on";//查线路
    public static final String EXPRESS_DELIVERY_URL = "https://m.kuaidi100.com/";//查快递
    public static final String TRAIN_URL = "https://m.ctrip.com/webapp/train";//查高铁
    public static final String FLIGHT_URL = "https://m.ctrip.com/html5/flight/swift/index";//查航班
    public static final String CAR_URL = "https://m.ctrip.com/webapp/bus";//查车票
    public static final String BUS_URL = "http://m.linyi.bendibao.com/bus/";//查公交
    public static final String WEATHER_URL = "http://m.linyi.bendibao.com/tianqi/";//查天气

    public static final String VIOLATION_URL = "https://sd.122.gov.cn/views/inquiry.html";//违章
    public static final String MOCK_EXAMINATION_URL = "http://m.jxedt.com/mnks/";//"https://mnks.jxedt.com/";//模拟考
    public static final String ROAD_TRAFFIC_URL = "http://h5.changxingshandong.com/road?source=lyga";//查路况
    public static final String DRIVING_LICENSE_SCORE_URL = "http://h5.changxingshandong.com/license/illegal?source=lyga";//驾驶证记分
    public static final String NOTICE_MOVE_CAR_URL = "http://h5.changxingshandong.com/movecar?source=lyga";//通知挪车
    public static final String ANNUAL_REVIEW_URL = "http://h5.changxingshandong.com/order";//年审预约

    public static final int TYPE_ROAD_CONDITIONS = 0;//查线路
    public static final int TYPE_EXPRESS_DELIVERY = 1;//查快递
    public static final int TYPE_TRAIN = 2;//查高铁
    public static final int TYPE_FLIGHT = 3;//查航班
    public static final int TYPE_CAR = 4;//查车票
    public static final int TYPE_BUS = 5;//查公交
    public static final int TYPE_WEATHER = 6;//查天气

    public static final int TYPE_VIOLATION = 7;//违章
    public static final int TYPE_MOCK_EXAMINATION = 8;//模拟考
    public static final int TYPE_ROAD_TRAFFIC = 9;//查路况
    public static final int TYPE_DRIVING_LICENSE_SCORE = 10;//驾驶证记分
    public static final int TYPE_NOTICE_MOVE_CAR = 11;//通知挪车
    public static final int TYPE_ANNUAL_REVIEW = 12;//年审预约

}
