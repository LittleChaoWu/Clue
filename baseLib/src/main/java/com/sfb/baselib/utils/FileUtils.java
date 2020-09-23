package com.sfb.baselib.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.channels.FileChannel;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

/**
 * 文件操作工具类
 */
public class FileUtils {

    public static final int DIR_CATEGORY_IMAGE = 0;
    public static final int DIR_CATEGORY_TEMP = 1;
    public static final int DIR_CATEGORY_THUMBNAIL = 2;
    public static final int DIR_CATEGORY_LOG = 3;
    public static final int DIR_CATEGORY_AUDIO = 4;
    public static final int DIR_CATEGORY_CACHE = 5;
    public static final int DIR_CATEGORY_VIDEO = 6;
    public static final int DIR_CATEGORY_RECOGNIZE = 7;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DIR_CATEGORY_IMAGE, DIR_CATEGORY_TEMP, DIR_CATEGORY_THUMBNAIL, DIR_CATEGORY_LOG, DIR_CATEGORY_AUDIO,
            DIR_CATEGORY_CACHE, DIR_CATEGORY_VIDEO, DIR_CATEGORY_RECOGNIZE})
    public @interface DirCategory {
    }

    /**
     * 根据路径生成一个File对象
     *
     * @param filePath 文件路径
     */
    private static File String2File(@NonNull String filePath) {
        try {
            return new File(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     */
    public static boolean isFileExist(@NonNull String filePath) {
        return isFileExist(String2File(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 目标文件
     */
    public static boolean isFileExist(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断文件夹是否存在
     *
     * @param folderPath 文件夹路径
     */
    public static boolean isFolderExist(@NonNull String folderPath) {
        return isFolderExist(String2File(folderPath));
    }

    /**
     * 判断文件夹是否存在
     *
     * @param file 目标文件夹
     */
    public static boolean isFolderExist(File file) {
        return file != null && file.isDirectory() && file.exists();
    }

    /**
     * 创建文件夹
     *
     * @param folderPath 文件夹路径
     */
    public static boolean createFolder(@NonNull String folderPath) {
        File folder = String2File(folderPath);
        return folder != null && !folder.exists() && folder.mkdirs();
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     */
    public static boolean createFile(@NonNull String filePath) {
        try {
            File file = String2File(filePath);
            return file != null && !file.exists() && file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制文件到指定路径
     *
     * @param oldPath 源文件路径
     * @param newPath 指定复制路径
     */
    public static boolean copyFile(@NonNull String oldPath, @NonNull String newPath) {
        try {
            File fromFile = String2File(oldPath);
            File toFile = String2File(newPath);
            if (fromFile == null || toFile == null) {
                return false;
            }
            FileInputStream inStream = new FileInputStream(fromFile);
            FileOutputStream outStream = new FileOutputStream(toFile);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
            inChannel.close();
            outChannel.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件后缀
     *
     * @param filePath 文件名/文件路径
     * @return 后缀名
     */
    public static String getFileSuffix(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int index = filePath.lastIndexOf(".");
        if (index <= 0) {
            return null;
        }
        return filePath.substring(index);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param context 上下文
     * @param uri     选择文件后返回的uri
     * @return 文件真实路径
     */
    @SuppressLint("NewApi")
    public static String getPathByUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getFilePath(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getFilePath(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getFilePath(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * 获取一个文件或文件夹大小
     *
     * @param filePath 文件路径
     * @return 文件或文件夹大小
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] paths = file.listFiles();
                for (int i = 0; i < paths.length; i++) {
                    if (paths[i].isDirectory()) {
                        size = size + getFileSize(paths[i].getPath());
                    } else {
                        size = size + paths[i].length();
                    }
                }
            } else {
                size = size + file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return size;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] paths = file.listFiles();
                for (int i = 0; i < paths.length; i++) {
                    if (paths[i].isDirectory()) {
                        deleteFile(paths[i].getPath());
                    } else {
                        paths[i].delete();
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 4.4版本以下通过uri获取文件路径
     *
     * @param context       上下文
     * @param uri           选择文件后返回的uri
     * @param selection     ContentResolver查找的selection
     * @param selectionArgs ContentResolver查找的selectionArgs
     * @return 文件真实路径
     */
    private static String getFilePath(Context context, Uri uri, String selection, String[] selectionArgs) {
        if (uri == null)
            return null;

        String path = null;
        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                path = cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return path;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
