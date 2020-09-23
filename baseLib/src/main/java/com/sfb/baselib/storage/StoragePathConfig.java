package com.sfb.baselib.storage;

import android.os.Environment;

import com.sfb.baselib.BuildConfig;


/**
 * 配置本地存储路径
 *
 * @author Roadley Huang
 */
class StoragePathConfig {

    /**
     * 存储文件夹根目录
     */
    static final String ROOT = Environment.getExternalStorageDirectory() + "/" + BuildConfig.STORAGE_ROOT + "/";
    /**
     * 存储各类数据文件夹目录
     */
    static final String DATA_DIR = ROOT + "data/";
    /**
     * 存储图片文件的文件夹
     */
    static final String IMAGE_DIR = DATA_DIR + "Image/";
    /**
     * 存储音频文件的文件夹
     */
    static final String AUDIO_DIR = DATA_DIR + "Audio/";
    /**
     * 存储视频文件的文件夹
     */
    static final String VIDEO_DIR = DATA_DIR + "Video/";
    /**
     * 存储缩略图的文件夹
     */
    static final String THUMBNAIL_DIR = DATA_DIR + "THumbnail/";
    /**
     * 存储缓存的文件夹
     */
    static final String CACHE_DIR = DATA_DIR + "Cache/";
    /**
     * 存储Temp的文件夹
     */
    static final String TEMP_DIR = DATA_DIR + "Temp/";
    /**
     * 存储Log的文件夹
     */
    static final String LOG_DIR = DATA_DIR + "Log/";
    /**
     * 存储Json的文件夹
     */
    static final String JSON_DIR = DATA_DIR + "Json/";
    /**
     * 存储拍照识别图的文件夹
     */
    static final String RECOGNIZE_DIR = DATA_DIR + "Recognize/";
}
