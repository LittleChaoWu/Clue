package com.sfb.baselib.network.upload;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.DataBaseManager;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.service.UploadService;
import com.pref.GuardPreference_;

import javax.inject.Inject;

/**
 * 文件上传管理器
 */
public class UploadManager {

    @Inject
    public Context context;
    @Inject
    public GuardPreference_ preference;
    @Inject
    public DaoHelper mDaoHelper;
    @Inject
    public Bus mBus;

    private static UploadManager uploadManager;

    private UploadManager() {
        UploadDagger.getDaggerComponent().inject(this);
        mBus.register(this);
    }

    public static UploadManager getInstance() {
        if (uploadManager == null) {
            synchronized (DataBaseManager.class) {
                if (uploadManager == null) {
                    uploadManager = new UploadManager();
                }
            }
        }
        return uploadManager;
    }

    /**
     * 通知上传
     *
     * @param bean CollectRecordBean
     */
    public void notifyUpload(CollectRecordBean bean) {
        if (bean != null) {
            //判断是否是缓存传来的
            if (bean.getRecordRole() == CollectRecordBean.RECORD_ROLE.CACHE.ordinal()) {
                bean.setRecordRole(CollectRecordBean.RECORD_ROLE.CACHE_TRANSMIT.ordinal());
            } else {
                bean.setRecordRole(CollectRecordBean.RECORD_ROLE.TRANSMIT.ordinal());
            }
            bean.setIsCache(CollectRecordBean.TRANSMIT_RECORD);
            mDaoHelper.saveCollectRecord(bean);
            if (!TextUtils.isEmpty(bean.getFilepaths())) {
                if (bean.getUploadReportState() == CollectRecordBean.STATE_OF_SUBMIT_FAIL) {
                    FormUpload.getInstance().uploadForm(bean);
                } else {
                    notifyUpload();
                }
            } else {
                FormUpload.getInstance().uploadForm(bean);
            }
        }
    }

    /**
     * 通知上传
     */
    public void notifyUpload() {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(UploadService.ACTION_UPLOAD);
        context.startService(intent);
    }

    /**
     * 通知暂停
     */
    public void notifyPause(CollectRecordBean bean) {
        if (bean != null) {
            if (bean.getUploadReportState() == CollectRecordBean.STATE_OF_UPLOADING) {
                notifyPause();
            } else {
                bean.setUploadReportState(CollectRecordBean.STATE_OF_PAUSE);
                mDaoHelper.saveCollectRecord(bean);
            }
        }
    }

    /**
     * 通知暂停
     */
    public void notifyPause() {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(UploadService.ACTION_PAUSE);
        context.startService(intent);
    }
}
