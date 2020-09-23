package com.sfb.baselib.widget.gridimage.presenter;

import com.sfb.baselib.network.subscriber.FileDownLoadSubscriber;
import com.sfb.baselib.widget.gridimage.contract.GirdImageContract;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class GirdImagePresenter extends GirdImageContract.Presenter {

    @Override
    public void downloadFile(final String path, String url) {
        final FileDownLoadSubscriber<File> fileDownLoadObserver = new FileDownLoadSubscriber<File>() {
            @Override
            public void onDownLoadSuccess(File file) {
                if (isViewAttach()) {
                    mView.onDownLoadSuccess(file.getPath());
                }
            }

            @Override
            public void onDownLoadFail(Throwable throwable) {
                if (isViewAttach()) {
                    mView.onDownLoadFail(throwable);
                }
            }

            @Override
            public void onProgress(long current, long total) {
                if (isViewAttach()) {
                    mView.onProgress(current, total);
                }
            }
        };
        mApiService.downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        return fileDownLoadObserver.saveFile(responseBody, path);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileDownLoadObserver);
    }

}
