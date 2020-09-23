package com.sfb.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * 读写文件工具类
 */
public class RWFileUtils {
    /**
     * 将字节数组写入文件(覆盖旧数据)
     *
     * @param filePath 将写入的文件路径
     * @param buffer   内容的byte数组
     * @return 写入是否成功
     */
    public static boolean saveByte(@NonNull String filePath, byte[] buffer) {
        return saveByte(filePath, buffer, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath 将写入的文件路径
     * @param buffer   内容的byte数组
     * @param isAppend 是否是append模式
     * @return 写入是否成功
     */
    public static boolean saveByte(@NonNull String filePath, byte[] buffer, boolean isAppend) {
        if (FileUtils.isFileExist(filePath) || FileUtils.createFile(filePath)) {
            try {
                FileOutputStream fos = new FileOutputStream(filePath, isAppend);
                fos.write(buffer);
                fos.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将String写入文件(覆盖旧数据)
     *
     * @param filePath 将写入的文件路径
     * @param content  内容字符串
     * @return 写入是否成功
     */
    public static boolean saveString(String filePath, String content) {
        return !TextUtils.isEmpty(content) && saveByte(filePath, content.getBytes());
    }

    /**
     * 将String写入文件
     *
     * @param filePath 将写入的文件路径
     * @param content  内容字符串
     * @param isAppend 是否是append模式
     * @return 写入是否成功
     */
    public static boolean saveString(String filePath, String content, boolean isAppend) {
        return !TextUtils.isEmpty(content) && saveByte(filePath, content.getBytes(), isAppend);
    }

    /**
     * 将bitmap写入文件
     * @param filePath 将写入的文件路径
     * @param bitmap 要写入的bitmap
     * @param format 写入格式
     * @return 写入是否成功
     */
    public static boolean saveBitmap(String filePath, Bitmap bitmap, Bitmap.CompressFormat format) {
        if (bitmap == null) {
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return saveByte(filePath, baos.toByteArray());
    }

    /**
     * 将bitmap写入文件
     * @param filePath 将写入的文件路径
     * @param bitmap 要写入的bitmap
     * @return 写入是否成功
     */
    public static boolean saveBitmap(String filePath, Bitmap bitmap) {
        return saveBitmap(filePath, bitmap, Bitmap.CompressFormat.JPEG);
    }

    /**
     * 读取asset中的文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 文件中的字符串
     */
    public static String readAsset(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
