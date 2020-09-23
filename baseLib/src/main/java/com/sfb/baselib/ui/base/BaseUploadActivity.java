package com.sfb.baselib.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;

import com.sfb.baselib.Constants;
import com.sfb.baselib.R;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.components.bus.event.UploadErrorEvent;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.dialog.UploadDialog;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.utils.FileHelper;
import com.sfb.baselib.utils.NetworkUtils;
import com.sfb.baselib.widget.dialog.PromptDialog;
import com.sfb.baselib.builder.CollectRecordBuilder;
import com.sfb.baselib.network.upload.UploadManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 有文件上传的界面继承此Activity
 * 主要步骤：
 * 1.构建上传类型，如：GasInfo，LegalInfo。
 * 2.在{@link CollectRecordBuilder}中实现对应类型的build(info)方法。
 * 3.在{@link com.sfb.baselib.network.upload.FormUpload}中实现对应的提交方法。
 * 4.实现@BusSubscribe注解的CollectRecordEvent事件的Bus回调，实现上传后的处理，并调
 * 用isUploadSuccess()方法，判断是否上传提交成功。
 * 5.实现@BusSubscribe注解的UploadErrorEvent事件的Bus回调，实现上传过程中出错的处理，
 * 其中必须要执行uploadError()方法。
 */
public abstract class BaseUploadActivity<V extends BaseView, P extends BasePresenter> extends BaseActivity<V, P> {

    public static final String COLLECT_RECORD_BEAN_KEY = "mCollectRecordBean";

    protected Bus mBus;
    protected DaoHelper mDaoHelper;

    protected CollectRecordBean mCollectRecordBean;
    protected UploadDialog uploadDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        Log.i("liu", this.getClass() + "");
        if (uploadDialog != null && uploadDialog.isShowing()) {
            uploadDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus = Bus.getInstance();
        mDaoHelper = DaoHelper.getInstance(this);
        mBus.register(this);
        Log.i("liu", this.getClass() + "");
    }

    /**
     * 上传检测
     *
     * @param bean CollectRecordBean
     */
    protected void uploadCheck(CollectRecordBean bean) {
        if (bean != null) {
            String paths = bean.getFilepaths();
            if (!TextUtils.isEmpty(paths)) {
                CollectRecordBean sameBean = mDaoHelper.getCollectRecord(paths);
                if (sameBean != null) {
                    sameBean.setAttachData(bean.getAttachData());
                    sameBean.setUploadReportState(CollectRecordBean.STATE_OF_UPLOADING);
                    bean = sameBean;
                    upload(sameBean);
                } else {
                    long totalSize = FileHelper.calculateTotalSize(bean.getFilepaths());
                    if (!NetworkUtils.isWifiAvailable(this) && totalSize > Constants.LIMIT_RANGE_OUT * Constants.LIMIT_UNIT) {
                        showUploadTipDialog(bean);
                    } else {
                        upload(bean);
                    }
                }
            } else {
                upload(bean);
            }
            mCollectRecordBean = bean;
        }
    }

    /**
     * 显示没开wifi且文件超过5M的提示
     */
    private void showUploadTipDialog(final CollectRecordBean bean) {
        PromptDialog dialog = new PromptDialog(this);
        String message = String.format(getString(R.string.upload_without_wifi_tip), String.valueOf(Constants.LIMIT_RANGE_OUT));
        dialog.setMessage(message);
        dialog.setNegativeButton(getString(R.string.open_wifi), new PromptDialog.OnNegativeClickListener() {
            @Override
            public void onClick() {
                Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(settingsIntent);
            }
        });
        dialog.setPositiveButton(getString(R.string.continue_uploading), new PromptDialog.OnPositiveClickListener() {
            @Override
            public void onClick() {
                upload(bean);
            }
        });
        dialog.show();
    }

    /**
     * 显示上传的对话框
     *
     * @param bean CollectRecordBean
     */
    private void showUploadDialog(final CollectRecordBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getFilepaths())) {
            if (uploadDialog != null) {
                uploadDialog.dismiss();
                uploadDialog = null;
            }
            uploadDialog = new UploadDialog(this, bean);
            uploadDialog.setPositiveButton(new UploadDialog.OnPositiveClickListener() {
                @Override
                public void onClick() {
                    runInBackCallback();
                }
            });
            uploadDialog.show();
        }
    }

    /**
     * 点击后台运行回调【预留】
     * 特定情况不会被调用，详情见{@link com.sfb.baselib.widget.dialog.UploadDialog}
     */
    @Deprecated
    public abstract void runInBackCallback();

    /**
     * 上传
     */
    private void upload(CollectRecordBean bean) {
        Log.i("lbs log","上传: upload: " + bean);
        //通知上传
        UploadManager.getInstance().notifyUpload(bean);
        //显示上传的对话框[注意：此处必须放在通知上传之后，以保证记录存于数据库，使上传进度可以正常获取]
        showUploadDialog(bean);
    }

    /**
     * 删除缓存记录
     *
     * @param bean CollectRecordBean
     */
    protected void deleteCache(final CollectRecordBean bean) {
        if (bean != null) {
            //只有在附件不为空，且记录类型为CACHE时才显示“是否删除本地文件”的选项
            if (!TextUtils.isEmpty(bean.getFilepaths()) && bean.getRecordRole() == CollectRecordBean.RECORD_ROLE.CACHE.ordinal()) {
                final PromptDialog dialog = new PromptDialog(this, PromptDialog.CHECK);
                dialog.setMessage(getString(R.string.delete_collect_record_tip));
                dialog.setCheckLabel(getString(R.string.delete_local_file_tip));
                dialog.messageAlign(Gravity.LEFT);
                dialog.setPositiveButton(new PromptDialog.OnPositiveClickListener() {
                    @Override
                    public void onClick() {
                        if (dialog.isCheck()) {
                            List<String> paths = Arrays.asList(bean.getFilepaths().split(","));
                            for (int i = 0; i < paths.size(); i++) {
                                File file = new File(paths.get(i));
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                        mDaoHelper.deleteCollectRecord(bean);
                        //删除记录通知
                        mBus.post(new CollectRecordEvent(bean));
                        finish();
                    }
                });
                dialog.show();
            } else {
                PromptDialog dialog = new PromptDialog(this);
                dialog.setMessage(getString(R.string.delete_collect_record_tip));
                dialog.setPositiveButton(new PromptDialog.OnPositiveClickListener() {
                    @Override
                    public void onClick() {
                        mDaoHelper.deleteCollectRecord(bean);
                        //删除记录通知
                        mBus.post(new CollectRecordEvent(bean));
                        finish();
                    }
                });
                dialog.show();
            }
        }
    }

    /**
     * 判断是否上传成功
     *
     * @param event CollectRecordEvent
     */
    protected boolean isUploadSuccess(CollectRecordEvent event) {
        if (uploadDialog != null) {
            uploadDialog.dismiss();
            uploadDialog = null;
        }
        CollectRecordBean recordBean = event.getCollectRecordBean();
        if (recordBean != null && mCollectRecordBean != null && event.isSuccess()) {
            if (recordBean.getId().equals(mCollectRecordBean.getId())) {
                mDaoHelper.deleteCollectRecord(mCollectRecordBean);
                return true;
            }
        }
        return false;
    }

    /**
     * 上传失败
     *
     * @param event
     */
    protected void uploadError(UploadErrorEvent event) {
        if (!TextUtils.isEmpty(event.getPaths())) {
            CollectRecordBean recordBean = mDaoHelper.getCollectRecord(event.getPaths());
            if (recordBean != null) {
                if (uploadDialog != null) {
                    uploadDialog.dismiss();
                    uploadDialog = null;
                }
            }
        }
    }

}
