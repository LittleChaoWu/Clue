package com.sfb.baselib.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.event.UploadErrorEvent;
import com.sfb.baselib.components.bus.event.UploadEvent;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.database.data.UploadInfo;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.FileHelper;
import com.sfb.baselib.utils.NetworkUtils;
import com.sfb.baselib.utils.RxUtils;
import com.sfb.baselib.utils.ToastUtils;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.data.FileResult;
import com.sfb.baselib.network.api.UploadApiService;
import com.sfb.baselib.network.body.FileRequestBody;
import com.sfb.baselib.network.upload.FormUpload;
import com.pref.GuardPreference_;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.ResourceSubscriber;

@SuppressLint("CheckResult")
public class UploadService extends Service implements FileRequestBody.UploadProgressListener {

    public static final String ACTION_UPLOAD = "start_upload";
    public static final String ACTION_PAUSE = "pause_upload";
    public static final String ACTION_NOTIFY_UI = "notify_ui";

    private ResourceSubscriber<FileResult> subscriber;

    @Inject
    public Context context;
    @Inject
    public DaoHelper mDaoHelper;
    @Inject
    public Bus mBus;
    @Inject
    public GuardPreference_ mPreference;
    @Inject
    public UploadApiService mApiService;
    @Inject
    public Gson mGson;
    public FormUpload mFormUpload;

    private String fileId;//当前上传的文件id
    private String paths;//记录的文件集合
    private boolean isPause = false;
    private boolean isUploading = false;

    @Override
    public void onCreate() {
        super.onCreate();
        UploadDagger.getDaggerComponent().inject(this);
        mFormUpload = FormUpload.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            Log.i("lbs log","=== UploadService onStartCommand : " + action);
            switch (action) {
                case ACTION_UPLOAD:
                    upload();
                    break;
                case ACTION_PAUSE:
                    pause();
                    break;
                case ACTION_NOTIFY_UI:
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 每次上传前重置下相关状态
     */
    private void reset() {
        fileId = "";
        paths = "";
        isPause = false;
        isUploading = true;
    }

    /**
     * 暂停
     */
    private void pause() {
        if (subscriber != null && !subscriber.isDisposed()) {
            subscriber.dispose();
        }
        isPause = false;
        isUploading = false;
    }

    /**
     * 上传
     */
    private void upload() {
        if (isUploading) {
            return;
        }
        final CollectRecordBean bean = mDaoHelper.getPrepareRecord();
        if (bean == null || !NetworkUtils.isNetworkAvailable(this)) {
            if (subscriber != null && subscriber.isDisposed()) {
                subscriber.dispose();
            }
            stopSelf();
            return;
        }
        //更改记录上传状态为正在上传
        bean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOADING);
        mDaoHelper.saveCollectRecord(bean);
        //重置传输状态
        reset();
        subscriber = Flowable.just(bean).compose(RxUtils.<CollectRecordBean>io2io())
                .flatMap(new Function<CollectRecordBean, Publisher<UploadInfo>>() {
                    @Override
                    public Publisher<UploadInfo> apply(CollectRecordBean bean) throws Exception {
                        int currentIndex = bean.getCurrentIndex();
                        List<UploadInfo> uploadInfos = bean.getUploadInfos();
                        UploadInfo uploadInfo = null;
                        if (uploadInfos != null && uploadInfos.size() > 0) {
                            uploadInfo = uploadInfos.get(currentIndex);
                            //判断是否有上传过
                            UploadInfo info = mDaoHelper.getUploadInfoByPath(uploadInfo.getFilePath());
                            if (info != null) {
                                uploadInfo = info;
                            }
                        }
                        return Flowable.just(uploadInfo);
                    }
                }).flatMap(new Function<UploadInfo, Publisher<FileResult>>() {
                    @Override
                    public Publisher<FileResult> apply(UploadInfo uploadInfo) throws Exception {
                        String fileName = uploadInfo.getFileName();
                        String fileId = uploadInfo.getRowKey();
                        if (TextUtils.isEmpty(fileId)) {
                            return Flowable.just(null);
                        } else {
                            if (fileId.contains(fileName)) {
                                return createFile(uploadInfo);
                            } else {
                                FileResult fileResult = new FileResult();
                                fileResult.setFileId(fileId);
                                return Flowable.just(fileResult);
                            }
                        }
                    }
                }).flatMap(new Function<FileResult, Publisher<FileResult>>() {
                    @Override
                    public Publisher<FileResult> apply(FileResult fileResult) throws Exception {
                        return getUploadLength(fileResult.getFileId());
                    }
                }).flatMap(new Function<FileResult, Publisher<FileResult>>() {
                    @Override
                    public Publisher<FileResult> apply(FileResult fileResult) throws Exception {
                        //更新CollectRecordBean的上传状态
                        bean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOADING);
                        mDaoHelper.saveCollectRecord(bean);
                        //为全局变量赋值
                        fileId = fileResult.getFileId();
                        paths = bean.getFilepaths();
                        return uploadFile(fileResult);
                    }
                }).subscribeWith(new ResourceSubscriber<FileResult>() {
                    @Override
                    public void onNext(FileResult fileResult) {
                        if (fileResult != null) {
                            CollectRecordBean bean = mDaoHelper.getCollectRecord(paths);
                            int currentIndex = bean.getCurrentIndex();
                            int totalIndex = bean.getTotalIndex();
                            if (currentIndex >= totalIndex) {
                                long currentSize = bean.getCurrentSize();
                                long totalSize = bean.getTotalSize();
                                if (currentSize == totalSize) {
                                    //更新CollectRecordBean的上传状态
                                    if (bean.getUploadReportState() != CollectRecordBean.STATE_OF_SUBMIT_SUCCESS) {
                                        bean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOAD_SUCCESS);
                                        mDaoHelper.saveCollectRecord(bean);
                                        //提交表单
                                        mFormUpload.uploadForm(bean);
                                    }
                                } else {
                                    bean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOAD_FAIL);
                                    mDaoHelper.saveCollectRecord(bean);
                                }
                            } else {
                                //更新CollectRecordBean的currentIndex
                                currentIndex++;
                                bean.setCurrentIndex(currentIndex);
                                mDaoHelper.saveCollectRecord(bean);
                            }
                            //通知更新UI
                            notifyUI(paths);
                        } else {
                            //提交表单(无附件的情况)
                            mFormUpload.uploadForm(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (isPause) {
                            bean.setUploadReportState(CollectRecordBean.STATE_OF_PAUSE);
                            mDaoHelper.saveCollectRecord(bean);
                        } else {
                            bean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOAD_FAIL);
                            mDaoHelper.saveCollectRecord(bean);
                        }
                        //通知上传错误
                        notifyError(paths);
                        //开始下一个任务
                        isUploading = false;
                        upload();
                    }

                    @Override
                    public void onComplete() {
                        //开始下一个任务
                        isUploading = false;
                        upload();
                    }
                });
    }


    /**
     * 通知更新UI
     *
     * @param paths 文件路径
     */
    private void notifyUI(String paths) {
        mBus.post(new UploadEvent(paths));
    }

    /**
     * 通知上传错误
     *
     * @param paths 文件路径
     */
    private void notifyError(String paths) {
        mBus.post(new UploadErrorEvent(paths));
    }

    /**
     * 创建文件
     */
    private Flowable<FileResult> createFile(final UploadInfo uploadInfo) {
        String path = uploadInfo.getFilePath();
        Map<String, Object> map = new HashMap<>();
        map.put("file_name", uploadInfo.getFileName());
        map.put("file_size", uploadInfo.getTotalSize());
        map.put("file_content_type", FileHelper.getMIMEType(path));
        map.put("file_type", path.substring(path.lastIndexOf(".") + 1, path.length()));
        map.put("time_length", uploadInfo.getTimeLength());
        Log.i("lbs log","====: " + map );
        Flowable<FileResult> flowAble = mApiService.createFile(map)
                .doOnNext(new Consumer<BaseResponse<FileResult>>() {
                    @Override
                    public void accept(BaseResponse<FileResult> response) throws Exception {
                        String fileId = response.getData().getFileId();
                        //uploadInfo的fileName、filePath、totalSize已有值
                        uploadInfo.setRowKey(fileId);
                        if (mPreference.getAccountInfo() != null) {
                            uploadInfo.setUser(mPreference.getAccountInfo().getUsername());
                        }
                        uploadInfo.setSaveTime(CommonUtils.getServerTime(getApplicationContext()));
                        mDaoHelper.saveUploadInfo(uploadInfo);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(context, throwable.getLocalizedMessage());
                        Log.i("liudingyi", throwable.getLocalizedMessage());
                    }
                }).flatMap(new Function<BaseResponse<FileResult>, Publisher<FileResult>>() {
                    @Override
                    public Publisher<FileResult> apply(BaseResponse<FileResult> response) throws Exception {
                        return Flowable.just(response.getData());
                    }
                });
        return flowAble;
    }

    /**
     * 获取上传进度
     */
    private Flowable<FileResult> getUploadLength(final String fileId) {
        Flowable<FileResult> flowAble = mApiService.getUploadLength(fileId)
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(context, throwable.getLocalizedMessage());
                        Log.i("liudingyi", throwable.getLocalizedMessage());
                    }
                }).flatMap(new Function<BaseResponse<FileResult>, Publisher<FileResult>>() {
                    @Override
                    public Publisher<FileResult> apply(BaseResponse<FileResult> response) throws Exception {
                        FileResult fileResult = response.getData();
                        fileResult.setFileId(fileId);
                        return Flowable.just(fileResult);
                    }
                });
        return flowAble;
    }

    /**
     * 上传
     */
    private Flowable<FileResult> uploadFile(final FileResult fileResult) {
        Flowable<FileResult> flowAble = null;
        long currentSize = fileResult.getTempLength();
        UploadInfo uploadInfo = mDaoHelper.getUploadInfoById(fileResult.getFileId());
        try {
            if (uploadInfo != null && !TextUtils.isEmpty(uploadInfo.getFilePath())) {
                File file = new File(uploadInfo.getFilePath());
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    if (currentSize > 0) {
                        fis.skip(currentSize);
                    } else if (currentSize < 0) {
                        currentSize = 0;
                    }
                    FileRequestBody fileRequestBody = new FileRequestBody(fis, file.length(), currentSize, this);
                    flowAble = mApiService.uploadFile(fileResult.getFileId(), fileResult.getTempLength(), fileRequestBody)
                            .doOnError(new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    ToastUtils.showShort(context, throwable.getLocalizedMessage());
                                    Log.i("liudingyi", throwable.getLocalizedMessage());
                                }
                            }).flatMap(new Function<BaseResponse, Publisher<FileResult>>() {
                                @Override
                                public Publisher<FileResult> apply(BaseResponse response) throws Exception {
                                    return Flowable.just(fileResult);
                                }
                            });
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flowAble;
    }

    @Override
    public void transferred(long uploadSize) {
        if (!isPause) {
            //保存当前UploadInfo的上传进度
            if (!TextUtils.isEmpty(fileId)) {
                UploadInfo info = mDaoHelper.getUploadInfoById(fileId);
                info.setCurrentSize(uploadSize);
                mDaoHelper.saveUploadInfo(info);
            }
            //保存当前CollectRecordBean的上传进度
            if (!TextUtils.isEmpty(paths)) {
                CollectRecordBean bean = mDaoHelper.getCollectRecord(paths);
                if (bean != null) {
                    int currentSize = 0;
                    String[] list = paths.split(",");
                    for (String path : list) {
                        UploadInfo info = mDaoHelper.getUploadInfoByPath(path);
                        if (info != null) {
                            currentSize += info.getCurrentSize();
                        }
                    }
                    bean.setCurrentSize(currentSize);
                    mDaoHelper.saveCollectRecord(bean);
                    //通知更新UI
                    notifyUI(paths);
                }
            }
        }
    }

    @Override
    public boolean isPause() {
        return isPause;
    }
}
