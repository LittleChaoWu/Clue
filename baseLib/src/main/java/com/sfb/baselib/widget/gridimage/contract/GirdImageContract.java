package com.sfb.baselib.widget.gridimage.contract;

import android.content.Context;

import com.sfb.baselib.components.inject.BaseDagger;
import com.sfb.baselib.network.api.BaseApiService;

import javax.inject.Inject;

public interface GirdImageContract {

    interface View {
        /**
         * 下载成功
         */
        void onDownLoadSuccess(String filePath);

        /**
         * 下载失败
         *
         * @param throwable
         */
        void onDownLoadFail(Throwable throwable);

        /**
         * 下载进度
         *
         * @param current 进度
         * @param total   总进度
         */
        void onProgress(long current, long total);
    }

    abstract class Presenter<V extends View> {

        @Inject
        protected Context context;
        @Inject
        protected BaseApiService mApiService;

        public V mView;

        public void attach(V mView) {
            this.mView = mView;
        }

        public void detach() {
            mView = null;
        }

        public boolean isViewAttach() {
            return mView != null;
        }

        public Presenter() {
            BaseDagger.getBaseDaggerComponent().inject((Presenter<View>) this);
        }

        /**
         * 下载apk
         *
         * @param url 下载地址
         */
        public abstract void downloadFile(String path, String url);
    }

}
