package com.sfb.baselib.storage;


import com.sfb.baselib.utils.FileUtils;

public class StoragePathUtils {

    /**
     * 获取一个根目录的文件路径
     *
     * @param fileName 根目录下文件的文件名
     * @return 根目录的文件路径
     */
    public static String getRootPath(String fileName) {
        if (makeSureRootExist()) {
            return StoragePathConfig.ROOT.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Data目录的文件路径
     *
     * @param fileName data目录下文件的文件名
     * @return data目录的文件路径
     */
    public static String getDataPath(String fileName) {
        if (makeSureDataDirExist()) {
            return StoragePathConfig.DATA_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Image目录的文件路径
     *
     * @param fileName Image目录下文件的文件名
     * @return Image目录的文件路径
     */
    public static String getImagePath(String fileName) {
        if (makeSureImageDirExist()) {
            return StoragePathConfig.IMAGE_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Audio目录的文件路径
     *
     * @param fileName Audio目录下文件的文件名
     * @return Audio目录的文件路径
     */
    public static String getAudioPath(String fileName) {
        if (makeSureAudioDirExist()) {
            return StoragePathConfig.AUDIO_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Video目录的文件路径
     *
     * @param fileName Video目录下文件的文件名
     * @return Audio目录的文件路径
     */
    public static String getVideoPath(String fileName) {
        if (makeSureVideoDirExist()) {
            return StoragePathConfig.VIDEO_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Thumbnail目录的文件路径
     *
     * @param fileName Thumbnail目录下文件的文件名
     * @return Thumbnail目录的文件路径
     */
    public static String getThumbnailPath(String fileName) {
        if (makeSureThumbnailDirExist()) {
            return StoragePathConfig.THUMBNAIL_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Cache目录的文件路径
     *
     * @param fileName Cache目录下文件的文件名
     * @return Cache目录的文件路径
     */
    public static String getCachePath(String fileName) {
        if (makeSureCacheDirExist()) {
            return StoragePathConfig.CACHE_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Temp目录的文件路径
     *
     * @param fileName Temp目录下文件的文件名
     * @return Temp目录的文件路径
     */
    public static String getTempPath(String fileName) {
        if (makeSureTempDirExist()) {
            return StoragePathConfig.TEMP_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Log目录的文件路径
     *
     * @param fileName Log目录下文件的文件名
     * @return Log目录的文件路径
     */
    public static String getLogPath(String fileName) {
        if (makeSureLogDirExist()) {
            return StoragePathConfig.LOG_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Json目录的文件路径
     *
     * @param fileName Json目录下文件的文件名
     * @return Json目录的文件路径
     */
    public static String getJsonPath(String fileName) {
        if (makeSureJsonDirExist()) {
            return StoragePathConfig.JSON_DIR.concat(fileName);
        }
        return null;
    }

    /**
     * 获取一个Recognize目录的文件路径
     *
     * @param fileName Recognize目录下文件的文件名
     * @return Recognize目录的文件路径
     */
    public static String getRecognizePath(String fileName) {
        if (makeSureRecognizeDirExist()) {
            return StoragePathConfig.RECOGNIZE_DIR.concat(fileName);
        }
        return null;
    }


    /**
     * 确认根目录是否存在（不存在就创建）
     *
     * @return 是否存在根目录
     */
    private static boolean makeSureRootExist() {
        return FileUtils.isFolderExist(StoragePathConfig.ROOT) || FileUtils.createFolder(StoragePathConfig.ROOT);
    }

    /**
     * 确认data目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在data目录
     */
    private static boolean makeSureDataDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.DATA_DIR) || FileUtils.createFolder(StoragePathConfig.DATA_DIR);
    }

    /**
     * 确认Image目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Image目录
     */
    private static boolean makeSureImageDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.IMAGE_DIR) || FileUtils.createFolder(StoragePathConfig.IMAGE_DIR);
    }

    /**
     * 确认Audio目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Audio目录
     */
    private static boolean makeSureAudioDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.AUDIO_DIR) || FileUtils.createFolder(StoragePathConfig.AUDIO_DIR);
    }

    /**
     * 确认Video目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Video目录
     */
    private static boolean makeSureVideoDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.VIDEO_DIR) || FileUtils.createFolder(StoragePathConfig.VIDEO_DIR);
    }

    /**
     * 确认Thumbnail目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Thumbnail目录
     */
    private static boolean makeSureThumbnailDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.THUMBNAIL_DIR) || FileUtils.createFolder(StoragePathConfig.THUMBNAIL_DIR);
    }

    /**
     * 确认Cache目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Cache目录
     */
    private static boolean makeSureCacheDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.CACHE_DIR) || FileUtils.createFolder(StoragePathConfig.CACHE_DIR);
    }

    /**
     * 确认Temp目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Temp目录
     */
    private static boolean makeSureTempDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.TEMP_DIR) || FileUtils.createFolder(StoragePathConfig.TEMP_DIR);
    }

    /**
     * 确认Log目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Log目录
     */
    private static boolean makeSureLogDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.LOG_DIR) || FileUtils.createFolder(StoragePathConfig.LOG_DIR);
    }

    /**
     * 确认Json目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Json目录
     */
    private static boolean makeSureJsonDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.JSON_DIR) || FileUtils.createFolder(StoragePathConfig.JSON_DIR);
    }

    /**
     * 确认Recognize目录是否存在（不存在就一层层创建）
     *
     * @return 是否存在Recognize目录
     */
    private static boolean makeSureRecognizeDirExist() {
        return FileUtils.isFolderExist(StoragePathConfig.RECOGNIZE_DIR) || FileUtils.createFolder(StoragePathConfig.RECOGNIZE_DIR);
    }


}
