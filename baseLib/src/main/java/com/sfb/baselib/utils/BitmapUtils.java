package com.sfb.baselib.utils;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap工具类
 */
public class BitmapUtils {

    /**
     * 压缩图片大小
     *
     * @param context       Context
     * @param filePath      文件路径
     * @param compressRadio 压缩到多少 如 80
     * @return boolean
     */
    public static boolean compressPic(Context context, String filePath, int compressRadio) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        float width = mWindowManager.getDefaultDisplay().getWidth();
        float height = mWindowManager.getDefaultDisplay().getHeight();
        float[] wh = new float[2];
        wh[0] = width;
        wh[1] = height;

        File file = new File(filePath);
        if (compressRadio == 0) {
            compressRadio = 100;
            long fileSize = file.length();
            if (200 * 1024 < fileSize && fileSize <= 1024 * 1024)
                compressRadio = 85;
            else if (1024 * 1024 < fileSize)
                compressRadio = 70;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = false;
        BitmapFactory.decodeFile(filePath, options);
        int bmpheight = options.outHeight;
        int bmpWidth = options.outWidth;
        float inSampleSize = bmpheight / wh[1] > bmpWidth / wh[0] ? bmpheight / wh[1] : bmpWidth / wh[0];
        if (bmpheight / wh[1] <= bmpWidth / wh[0]) {
            if (DeviceUtils.getPhoneModel().equalsIgnoreCase("SM-N9006")) {
                inSampleSize = inSampleSize * 2;
            } else if (DeviceUtils.getPhoneModel().equalsIgnoreCase("V360")
                    || DeviceUtils.getPhoneModel().equalsIgnoreCase("YuLong-Coolpad8750")
                    || DeviceUtils.getPhoneModel().equalsIgnoreCase("Coolpad8750")
                    || DeviceUtils.getPhoneModel().equalsIgnoreCase("MX4 Pro")
                    || DeviceUtils.getPhoneModel().equalsIgnoreCase("ME863")) {
                inSampleSize = inSampleSize * 2;
            }
        }

        if (inSampleSize <= 1) {
            inSampleSize = 2;
        } else if (inSampleSize > 1 && inSampleSize <= 2) {
            inSampleSize = 2;
        } else if (inSampleSize > 2 && inSampleSize <= 3) {
            inSampleSize = 4;
        } else if (inSampleSize > 3 && inSampleSize <= 4) {
            inSampleSize = 4;
        } else {
            inSampleSize = 4;
        }
        options.inSampleSize = (int) inSampleSize;
        options.inJustDecodeBounds = false;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return false;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {
            System.gc();
            bitmap = null;
        }
        if (compressRadio == 100)
            return true;
        File file2 = new File(filePath);
        try {
            FileOutputStream bitmapWtriter = new FileOutputStream(file2);
            if (bitmap != null && bitmapWtriter != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressRadio, bitmapWtriter);
                bitmap.recycle();
                bitmap = null;
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存Bitmap到本地
     *
     * @param bitmap   Bitmap
     * @param filePath String
     * @return boolean
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath) {
        boolean save = false;
        File f = new File(filePath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            save = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return save;
    }

    /**
     * 生成水印接口,耗时操作，建议在异步线程中执行.
     *
     * @param filePath   需要生成水印的图片文件路径
     * @param gps        经纬度
     * @param gpsAddress 位置
     * @param textSize   水印文字大小
     * @return 是否成功
     */
    public static boolean generateWatermark(Context mContext, String filePath, String gps, String gpsAddress, double textSize) {
        return generateWatermark(mContext, filePath, gps, gpsAddress, "美亚柏科", textSize, false, CommonUtils.getServerTime(mContext));
    }

    /**
     * 生成水印接口,耗时操作，建议在异步线程中执行.
     *
     * @param filePath   需要生成水印的图片文件路径
     * @param gps        经纬度
     * @param gpsAddress 位置
     * @param logoText   公司水印标识
     * @param textSize   水印文字大小
     * @return 是否成功
     */
    public static boolean generateWatermark(Context mContext, String filePath, String
            gps, String gpsAddress, String logoText, double textSize, boolean for110, long serverTime) {
        if (TextUtils.isEmpty(filePath)) {
            LogUtils.log2File("createwatermark filepath empty");
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            LogUtils.log2File("createwatermark file unexist");
            return false;
        }

        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Service.WINDOW_SERVICE);
        float width = mWindowManager.getDefaultDisplay().getWidth();
        float height = mWindowManager.getDefaultDisplay().getHeight();
        float[] wh = new float[2];
        wh[0] = width;
        wh[1] = height;

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        if (isImageFile(fileName)) {
            try {
                String timeString = DateUtils.formatTime(serverTime);
                String imei = DeviceUtils.getPhoneDeviceId(mContext);
                boolean result = createWatermark(logoText, gps, gpsAddress, timeString, imei, filePath, wh, textSize, for110);
                if (!result) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.log2File("createwatermark exception");
            }
        }
        return false;
    }

    /**
     * 添加水印 处理
     *
     * @param logoText   公司水印标识
     * @param gps        GPS坐标
     * @param gpsAddress GPS地址
     * @param time       时间
     * @param path       路径
     * @param wh         宽高
     * @param radio      水印文字比例 例：double 1/35.0;
     * @return boolean
     */
    public static boolean createWatermark(String logoText, String gps, String
            gpsAddress, String time, String imei, String path, float[] wh, double radio, boolean for110) {
        try {
            Bitmap bitmap = createNewBitmapAndCompressByFile(path, wh);
            if (bitmap != null) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                Bitmap bmpTemp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmpTemp);
                Paint p = new Paint();
                p.setFilterBitmap(true);
                p.setColor(Color.GREEN);
                // int textSize =0;
                double textSize = 0;
                if (radio == 0) {
                    // if(wh[0]>800){
                    // textSize = w/DEFAULT_LARGE_SIZE;
                    // }else{
                    // textSize = w/DEFAULT_SIZE;
                    // }
                    // if(textSize>30){
                    // textSize = 30;
                    // }
                    textSize = w * (1 / 35.0);// 默认比例1/35.0
                } else {
                    textSize = w * radio;
                }
                LogUtils.log2File("the textSize when create water mark = " + textSize);
                int LINE_PADDING = (int) (textSize / 10);

                p.setTextSize((int) textSize);
                p.setAntiAlias(true);
                canvas.drawBitmap(bitmap, 0, 0, p);

                float pos_x = (float) (textSize * 0.5);
                //                if(!TextUtils.isEmpty(logoText)){
                //                canvas.drawText(logoText, pos_x,
                //                (float) (h-(textSize*4.5)-LINE_PADDING), p);
                //                }
                if (for110) {
                    if (!TextUtils.isEmpty("经纬度：" + gps)) {
                        canvas.drawText(gps, pos_x, (float) (h - (textSize * 3.5) - LINE_PADDING), p);
                    }
                }
                //可能是主干道
                if (!TextUtils.isEmpty(gpsAddress)) {
                    canvas.drawText("地址：" + gpsAddress, pos_x, (float) (h - (textSize * 2.5) - LINE_PADDING), p);
                    if (!TextUtils.isEmpty(time)) {
                        canvas.drawText("时间：" + time, pos_x, (float) (h - (textSize * 1.5) - LINE_PADDING), p);
                    }
                    if (for110) {
                        if (!TextUtils.isEmpty(imei)) {
                            canvas.drawText("设备号：" + imei, pos_x, (float) (h - textSize * 0.5), p);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(time)) {
                        canvas.drawText("时间：" + time, pos_x, (float) (h - (textSize * 2.5) - LINE_PADDING), p);
                    }
                    if (for110) {
                        if (!TextUtils.isEmpty(imei)) {
                            canvas.drawText("设备号：" + imei, pos_x, (float) (h - textSize * 1.5), p);
                        }
                    }
                }

                canvas.save();
                canvas.restore();
                File file = new File(path);
                try {
                    FileOutputStream bitmapWtriter = new FileOutputStream(file);
                    bmpTemp.compress(Bitmap.CompressFormat.JPEG, 60, bitmapWtriter);
                    if (bmpTemp != null) {
                        bmpTemp.recycle();
                    }
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 压缩图片大小
     *
     * @param filePath 文件路径
     * @param wh       宽高
     * @return Bitmap
     */
    private static Bitmap createNewBitmapAndCompressByFile(String filePath, float wh[]) {
        int offset = 100;
        File file = new File(filePath);
        long fileSize = file.length();
        if (200 * 1024 < fileSize && fileSize <= 1024 * 1024)
            offset = 85;
        else if (1024 * 1024 < fileSize)
            offset = 80;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = false;
        BitmapFactory.decodeFile(filePath, options);
        int bmpheight = options.outHeight;
        int bmpWidth = options.outWidth;
        float inSampleSize = bmpheight / wh[1] > bmpWidth / wh[0] ? bmpheight / wh[1] : bmpWidth / wh[0];
        String phoneModle = DeviceUtils.getPhoneModel();
        if (bmpheight / wh[1] <= bmpWidth / wh[0]) {
            if (phoneModle.equalsIgnoreCase("SM-N9006")) {
                inSampleSize = inSampleSize * 2;
            } else if (phoneModle.equalsIgnoreCase("V360") || phoneModle.equalsIgnoreCase("YuLong-Coolpad8750")
                    || phoneModle.equalsIgnoreCase("Coolpad8750") || phoneModle.equalsIgnoreCase("MX4 Pro") || (DeviceUtils
                    .getPhoneModel().equalsIgnoreCase("ME863") || (DeviceUtils
                    .getManufacturer().equalsIgnoreCase("HUAWEI")) || phoneModle.equalsIgnoreCase("G750-T01"))) {
                inSampleSize = inSampleSize * 2;
            }
        }

        if (inSampleSize <= 1) {
            inSampleSize = 2;
        } else if (inSampleSize > 1 && inSampleSize <= 2) {
            inSampleSize = 2;
        } else if (inSampleSize > 2 && inSampleSize <= 3) {
            inSampleSize = 4;
        } else if (inSampleSize > 3 && inSampleSize <= 4) {
            inSampleSize = 4;
        } else {
            inSampleSize = 4;
        }
        options.inSampleSize = (int) inSampleSize;
        options.inJustDecodeBounds = false;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {
            System.gc();
            bitmap = null;
        }
        if (offset == 100)
            return bitmap;
        File file2 = new File(filePath);
        try {
            FileOutputStream bitmapWtriter = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, offset, bitmapWtriter);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isImageFile(String fileName) {
        boolean isImageFile = false;
        if (fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".png")
                || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp")) {
            isImageFile = true;
        }
        return isImageFile;
    }

    public static byte[] bmp2ByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
