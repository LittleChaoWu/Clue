package com.sfb.baselib.utils;

import java.text.DecimalFormat;

/**
 * 文件大小的转化工具
 */
public class StorageSizeConverter {
    private static final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};

    /**
     * 文件大小的转文字
     *
     * @param size 文件大小
     * @return 带单位的文件大小字符串
     */
    public static String convert(long size) {
        if (size <= 0) {
            return "0B";
        }
        int digitGroups = (int) (Math.log(size) / Math.log(1024));
        return new DecimalFormat("#.##").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }
}
