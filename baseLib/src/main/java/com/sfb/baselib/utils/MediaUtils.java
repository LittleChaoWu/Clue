package com.sfb.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.sfb.baselib.R;

import java.io.File;

/**
 * 媒体工具类
 */
public class MediaUtils {
    /**
     * 调用系统相机包名
     */
    private static final String CAMERA_PACKAGE_NAME = "com.android.camera";
    /**
     * 调用系统相机拍照类名
     */
    private static final String CAMERA_CLASSNAME = "com.android.camera.Camera";
    /**
     * 调用系统相机录像类名
     */
    private static final String VIDEO_CAMERA_CLASSNAME = "com.android.camera.VideoCamera";

    /**
     * 默认拍照文件后缀为jpg
     */
    public static final String IMAGE_EXT_NAME = ".jpg";
    /**
     * 判断容量大小单位
     */
    public static final int LIMIT_UNIT = 1024 * 1024;
    /**
     * 限制视频最大上传大小（M）
     */
    public static final int LIMIT_MAX_VIDEO = 100;

    /**
     * 从相册选取媒体文件（照片/视频）
     *
     * @param context     上下文
     * @param requestCode startActivityForResult的requestCode
     */
    public static void selectMediaFile(Context context, int requestCode) {
        if (!(context instanceof Activity)) {
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*,video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 从相册选取媒体文件（照片）
     *
     * @param context     上下文
     * @param requestCode startActivityForResult的requestCode
     */
    public static void selectImageFile(Context context, int requestCode) {
        if (!(context instanceof Activity)) {
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 从相册选取媒体文件（视频）
     *
     * @param context     上下文
     * @param requestCode startActivityForResult的requestCode
     */
    public static void selectVideoFile(Context context, int requestCode) {
        if (!(context instanceof Activity)) {
            return;
        }
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 调起系统拍照程序进行拍照
     *
     * @param context  上下文
     * @param filePath 图片保存路径
     * @param reqCode  startActivityForResult的requestCode
     */
    public static void takePicture(Context context, String filePath, int reqCode) {

        if (!(context instanceof Activity)) {
            return;
        }

        if (!StorageUtils.isSDCardExisted()) {
            ToastUtils.showShort(context, R.string.sdcard_invalid);
            return;
        }
        File out = new File(filePath);
        Uri uri = Uri.fromFile(out);
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setClassName(CAMERA_PACKAGE_NAME, CAMERA_CLASSNAME);
            ((Activity) context).startActivityForResult(intent, reqCode);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                ((Activity) context).startActivityForResult(intent, reqCode);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 调起相机程序进行拍摄视频
     *
     * @param context 上下文
     * @param req     startActivityForResult的requestCode
     */
    public static void takeVideo(Context context, int req) {
        if (!(context instanceof Activity)) {
            return;
        }

        if (!StorageUtils.isSDCardExisted()) {
            ToastUtils.showShort(context, R.string.sdcard_invalid);
            return;
        }

        long freeSize = StorageUtils.getSDCardFreeSize();
        if (freeSize < 100 * 1024 * 1024) {
            ToastUtils.showShort(context, R.string.sdcard_space_limit);
        } else {
            try {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.setClassName(CAMERA_PACKAGE_NAME, VIDEO_CAMERA_CLASSNAME);
                ((Activity) context).startActivityForResult(intent, req);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    ((Activity) context).startActivityForResult(intent, req);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 调起系统程序进行录音
     *
     * @param context 上下文
     * @param req     startActivityForResult的requestCode
     */
    public static void takeAudio(Context context, int req) {
        if (!(context instanceof Activity)) {
            return;
        }

        if (!StorageUtils.isSDCardExisted()) {
            ToastUtils.showShort(context, R.string.sdcard_invalid);
            return;
        }

        long freeSize = StorageUtils.getSDCardFreeSize();
        if (freeSize < 100 * 1024 * 1024) {
            ToastUtils.showShort(context, R.string.sdcard_space_limit);
        } else {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            ((Activity) context).startActivityForResult(intent, req);
        }
    }

    public static void selectAllTypeFile(Context context, int requestCode) {
        if (!(context instanceof Activity)) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((Activity) context).startActivityForResult(Intent.createChooser(intent,
                    context.getString(R.string.please_choose_file)), requestCode);
        } catch (android.content.ActivityNotFoundException ex) {
            ToastUtils.showShort(context, R.string.please_install_file_manager);
        }
    }

}
