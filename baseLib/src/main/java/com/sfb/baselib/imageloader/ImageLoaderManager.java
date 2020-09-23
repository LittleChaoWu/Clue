package com.sfb.baselib.imageloader;

import android.content.Context;
import android.os.AsyncTask;

import com.sfb.baselib.R;
import com.sfb.baselib.utils.ToastUtils;


/**
 * Created by huangqh on 2017/5/22.
 */

public class ImageLoaderManager {

    private static ImageLoaderManager mInstance;

    public static ImageLoaderManager get() {
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 暂停加载
     */
    public void pauseLoad(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    /**
     * 恢复加载
     */
    public void resumeLoad(Context context) {
        GlideApp.with(context).resumeRequests();
    }

    /**
     * 是否暂停加载
     */
    public boolean isPaused(Context context) {
        return GlideApp.with(context).isPaused();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCaches(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除内存缓存（包括手动GC内存）
     */
    public void clearMemoryCachesWithGC(Context context) {
        GlideApp.get(context).clearMemory();
        System.gc();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCaches(Context context) {
        new ClearCacheTask(context).execute();
    }

    /**
     * 清除所有缓存（内存+磁盘）
     */
    public void clearCaches(Context context) {
        GlideApp.get(context).clearMemory();
        System.gc();
        new ClearCacheTask(context).execute();
    }

    class ClearCacheTask extends AsyncTask<Void, Void, Void> {
        Context context;

        public ClearCacheTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            GlideApp.get(context).clearDiskCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastUtils.showShort(context, R.string.clear_success);
        }
    }
}
