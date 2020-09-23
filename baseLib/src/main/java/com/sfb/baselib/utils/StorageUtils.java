package com.sfb.baselib.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 存储/SD卡相关工具类
 */
public class StorageUtils {
    public static boolean isSDCardExisted() {
        boolean isSDCardExisted = false;
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            isSDCardExisted = true;
        }
        return isSDCardExisted;
    }

    public static long getSDCardFreeSize() {
        long freeSize = 0;

        if (isSDCardExisted()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blockSize = statfs.getBlockSize();
            long availableBlocks = statfs.getAvailableBlocks();
            freeSize = availableBlocks * blockSize;
        }

        return freeSize;
    }
}
