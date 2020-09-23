package com.sfb.baselib.network.api;


import com.sfb.baselib.network.NetworkConfig;

public class H5Url {

    /**
     * 添加房屋合同的url
     */
    public static final String ADD_HOUSE_CONTRACT_URL = NetworkConfig.getH5RootUrl() + "/hireContract/add.html?bizId=%s";

    /**
     * 房屋合同详情的url
     */
    public static final String HOUSE_CONTRACT_DETAIL_URL = NetworkConfig.getH5RootUrl() + "/hireContract/contractDetail.html?id=%1$s&bizId=%2$s";

    /**
     * 考试详情的url
     */
    public static final String EXAMINATION_DETAIL_URL = NetworkConfig.getRootUrl() + "exam_detail_h5?id=%s";

    /**
     * 特行巡检的url
     */
    public static final String SPECIAL_INDUSTRY_CHECK = NetworkConfig.getH5RootUrl() + "/hotelContract/add.html";

    /**
     * 特行巡检合同的url
     */
    public static final String SPECIAL_INDUSTRY_CONTRACT_PREVIEW = NetworkConfig.getH5RootUrl() + "/hotelContract/signPreview.html";

}
