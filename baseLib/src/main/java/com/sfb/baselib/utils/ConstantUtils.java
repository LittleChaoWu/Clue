package com.sfb.baselib.utils;

public class ConstantUtils {

    //证件类型
    public static final String CERTIFICATE_TYPE_IDCARD = "1";  //身份证
    public static final String CERTIFICATE_TYPE_DRIVER_LICENSE = "2";    //驾驶证
    public static final String CERTIFICATE_TYPE_PASSPORT = "3";    //护照
    public static final String CERTIFICATE_TYPE_RESIDENCE_PERMIT = "4";    //居住证

    /**
     * 获取证件类型
     *
     * @param code String
     * @return String
     */
    public static String getCertificateType(String code) {
        if (CERTIFICATE_TYPE_IDCARD.equals(code)) {
            return "二代身份证";
        } else if (CERTIFICATE_TYPE_DRIVER_LICENSE.equals(code)) {
            return "驾驶证";
        } else if (CERTIFICATE_TYPE_PASSPORT.equals(code)) {
            return "护照";
        } else if (CERTIFICATE_TYPE_RESIDENCE_PERMIT.equals(code)) {
            return "居住证";
        }
        return "二代身份证";
    }


}
