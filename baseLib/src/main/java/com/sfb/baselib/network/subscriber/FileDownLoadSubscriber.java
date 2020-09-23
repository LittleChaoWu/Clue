package com.sfb.baselib.network.subscriber;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;

public class FileDownLoadSubscriber<T> extends ResourceSubscriber<T> {

    private static int STOP = 0;
    private static int START = 1;

    private int status = START;
    /**
     * 文件总大小
     */
    private long total;

    public FileDownLoadSubscriber() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(T t) {
        if (t instanceof File) {
            File file = (File)t;
            if (file.length() != total) {
                onDownLoadFail(null);
                return;
            }
        }
        onDownLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownLoadFail(e);
    }

    /**
     * 下载成功的回调
     *
     * @param t 下载的文件
     */
    public void onDownLoadSuccess(T t) {
    }

    /**
     * 下载失败回调
     *
     * @param throwable 错误
     */
    public void onDownLoadFail(Throwable throwable) {
    }

    /**
     * 下载进度监听
     *
     * @param current 当前大小
     * @param total   总大小
     */
    public void onProgress(long current, long total) {
    }

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @param file_path    目标文件夹
     * @return 写入完成的文件
     */
    public File saveFile(ResponseBody responseBody, String file_path) {
        try {
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            RandomAccessFile fos = null;
            File file = null;
            try {
                is = responseBody.byteStream();
                total = responseBody.contentLength();
                long sum = 0;
                file = new File(file_path);
                if (!file.exists() || file.length() < total) {
                    file.delete();
                } else {
                    return file;
                }
                fos = new RandomAccessFile(file, "rw");
                while ((len = is.read(buf)) != -1 && status != STOP) {
                    fos.write(buf, 0, len);
                    sum += len;
                    final long finalSum = sum;
                    onProgress(finalSum, total);
                }
                return file;
            } finally {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (IOException e) {
            onDownLoadFail(e);
            return null;
        }
    }

    public void stopDownload() {
        status = STOP;
    }

}
