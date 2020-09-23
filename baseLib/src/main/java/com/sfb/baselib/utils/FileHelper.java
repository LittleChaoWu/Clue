package com.sfb.baselib.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.sfb.baselib.BuildConfig;
import com.sfb.baselib.R;
import com.sfb.baselib.network.NetworkConfig;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.core.content.FileProvider;

/**
 * 文件
 */
public class FileHelper {

    private static final String FILE_URL_PATH = "file/download_file_item";
    private static final String FILE_URL_NOT_SESSION_PATH = "file/download_open_file";

    public static final int IMAGE_TYPE = 0;//图片类型
    public static final int VIDEO_TYPE = 1;//视频类型
    public static final int AUDIO_TYPE = 2;//音频类型
    public static final int TEXT_TYPE = 3;//文本类型

    /**
     * 是否需要添加session
     *
     * @param url String
     * @return boolean
     */
    public static boolean isNeedSession(String url) {
        String root = NetworkConfig.getRootUrl() + FILE_URL_PATH;
        return url.startsWith(root);
    }

    /**
     * 拼接图片的url
     *
     * @param fileId 文件id
     * @return String
     */
    public static String getDownloadUrl(String fileId) {
        Map<String, String> map = new HashMap<>();
        map.put("file_id", fileId);
        map.put("start_pos", String.valueOf(0));
        map.put("width", String.valueOf(0));
        map.put("height", String.valueOf(0));
        map.put("video_img", String.valueOf(false));
        map.put("shuiyin", String.valueOf(false));
        return getFileUrl(map, true);
    }

    /**
     * 拼接图片的url
     *
     * @param fileId     文件id
     * @param startPos   第几项
     * @param width      宽
     * @param height     高
     * @param isVideoImg 是否是视频图片 如果不传要么是图片，要么是视频下载地址，尼玛慎重
     * @param addShuiyin 是否添加水印
     * @return String
     */
    public static String getFileUrl(String fileId, int startPos, int width, int height, boolean isVideoImg, boolean addShuiyin) {
        Map<String, String> map = new HashMap<>();
        map.put("file_id", fileId);
        map.put("start_pos", String.valueOf(startPos));
        map.put("width", width != 0 ? String.valueOf(width) : null);
        map.put("height", height != 0 ? String.valueOf(height) : null);
        map.put("video_img", String.valueOf(isVideoImg));
        map.put("shuiyin", String.valueOf(addShuiyin));
        return getFileUrl(map, true);
    }

    /**
     * 拼接图片的url
     *
     * @param fileId     文件id
     * @param startPos   第几项
     * @param width      宽
     * @param height     高
     * @param isVideoImg 是否是视频图片 如果不传要么是图片，要么是视频下载地址，尼玛慎重
     * @param addShuiyin 是否添加水印
     * @return String
     */
    public static String getFileUrlWithoutSession(String fileId, int startPos, int width, int height, boolean isVideoImg, boolean addShuiyin) {
        Map<String, String> map = new HashMap<>();
        map.put("file_id", fileId);
        map.put("start_pos", String.valueOf(startPos));
        map.put("width", width != 0 ? String.valueOf(width) : null);
        map.put("height", height != 0 ? String.valueOf(height) : null);
        map.put("video_img", String.valueOf(isVideoImg));
        map.put("shuiyin", String.valueOf(addShuiyin));
        return getFileUrl(map, false);
    }

    /**
     * 拼接图片的url
     *
     * @param map 参数集合
     * @return String
     */
    private static String getFileUrl(Map<String, String> map, boolean hasSession) {
        String url = "";
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (TextUtils.isEmpty(url)) {
                if (hasSession) {
                    url = NetworkConfig.getRootUrl() + FILE_URL_PATH + "?" + entry.getKey() + "=" + entry.getValue();
                } else {
                    url = NetworkConfig.getRootUrl() + FILE_URL_NOT_SESSION_PATH + "?" + entry.getKey() + "=" + entry.getValue();
                }
            } else {
                url = url + "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    /**
     * 计算路径下所有文件总大小，string中的文件以,分隔
     *
     * @param paths
     */
    public static long calculateTotalSize(String paths) {
        long total = 0;
        try {
            if (!TextUtils.isEmpty(paths)) {
                List<String> list = Arrays.asList(paths.split(","));
                for (int i = 0; i < list.size(); i++) {
                    try {
                        File file = new File(list.get(i));
                        if (file.isFile())
                            total += file.length();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取文件类型
     *
     * @param filePath String
     * @return String
     */
    public static String getMIMEType(String filePath) {
        URL url;
        try {
            filePath = "file://" + filePath;
            url = new URL(filePath);
            URLConnection ul = url.openConnection();
            String mime = ul.getContentType();
            return mime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测文件类型
     *
     * @param mine String
     * @param type int
     * @return boolean
     */
    public static boolean checkFileType(String mine, int type) {
        if (!TextUtils.isEmpty(mine)) {
            String preffix = mine.substring(0, mine.lastIndexOf("/"));
            if (!TextUtils.isEmpty(preffix)) {
                switch (type) {
                    case IMAGE_TYPE:
                        return preffix.equalsIgnoreCase("image");
                    case VIDEO_TYPE:
                        return preffix.equalsIgnoreCase("video");
                    case AUDIO_TYPE:
                        return preffix.equalsIgnoreCase("audio");
                    case TEXT_TYPE:
                        return preffix.equalsIgnoreCase("text");
                    default:
                        break;
                }
            }
        }
        return false;
    }

    /**
     * 打开媒体文件
     *
     * @param path String
     */
    public static void openMedia(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(path);
            String type = getMIMEType(path);
            Uri uri;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, type);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.no_application_found), Toast.LENGTH_SHORT).show();
        }
    }

}
