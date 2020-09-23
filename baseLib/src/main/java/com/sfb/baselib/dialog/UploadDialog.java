package com.sfb.baselib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.utils.NetworkUtils;
import com.sfb.baselib.utils.ToastUtils;
import com.sfb.baselib.R;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.network.upload.UploadManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class UploadDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private TextView mTvTitle;
    private TextView mTvPercent;
    private ProgressBar mProgressBar;

    private Thread thread;
    private boolean isRunning = true;//表示线程是否继续执行

    private CollectRecordBean recordBean;

    @Inject
    public DaoHelper mDaoHelper;

    public UploadDialog(@NonNull Context context, CollectRecordBean bean) {
        super(context, R.style.PromptDialog);
        UploadDagger.getDaggerComponent().inject(this);
        setCancelable(false);
        setContentView(R.layout.layout_upload_dialog);
        activity = (Activity) context;
        mTvTitle = findViewById(R.id.tv_title);
        mTvPercent = findViewById(R.id.tv_percent);
        mProgressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.tv_run_back).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        //获取数据库中的记录
        recordBean = mDaoHelper.getCollectRecord(bean.getFilepaths());
//        Gson gson = new Gson();
//        boolean isHideRunBack = false;
//        switch (recordBean.getCollectType()) {
//            case TASK_REPORT:
//            case DISPOSE_CLUE:
//            case CLUE_FEEDBACK:
//            case CLUE_ASSIGN:
//            case REGISTER:
//            case REGISTER_AGAIN:
//            case RESET_INFO:
//            case AVATAR:
//                isHideRunBack = true;
//                break;
//            case CAR_COLLECT:
//                CarInfo carInfo = gson.fromJson(bean.getAttachData(), CarInfo.class);
//                if (carInfo.getSourceType() == 1) {
//                    isHideRunBack = true;
//                }
//                break;
//            case CLUE_COLLECT:
//                ClueInfo clueInfo = gson.fromJson(bean.getAttachData(), ClueInfo.class);
//                if (clueInfo.getSourceType() == 1) {
//                    isHideRunBack = true;
//                }
//                break;
//            case PERSONNEL_COLLECT:
//                PersonCollectInfo personCollectInfo = gson.fromJson(bean.getAttachData(), PersonCollectInfo.class);
//                if (personCollectInfo.getSourceType() == 1) {
//                    isHideRunBack = true;
//                }
//                break;
//            default:
//                break;
//        }
//        findViewById(R.id.tv_run_back).setVisibility(isHideRunBack ? View.GONE : View.VISIBLE);
        findViewById(R.id.tv_run_back).setVisibility(View.GONE);
        //运行进度
        runProgress(recordBean);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isRunning = false;
    }

    /**
     * 运行进度
     *
     * @param collectRecordBean CollectRecordBean
     */
    private void runProgress(final CollectRecordBean collectRecordBean) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (collectRecordBean != null) {
                        long currentSize = 0;
                        long totalSize = 0;
                        CollectRecordBean bean = collectRecordBean;
                        while (isRunning && bean != null && bean.getUploadReportState() != CollectRecordBean.STATE_OF_SUBMIT_SUCCESS) {
                            //判断网络条件
                            if (NetworkUtils.isNetworkAvailable(activity)) {
                                currentSize = bean.getCurrentSize();
                                totalSize = bean.getTotalSize();
                                final int progress = (int) (currentSize * 100 / totalSize);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvPercent.setText(progress + "%");
                                        mProgressBar.setProgress(progress);
                                    }
                                });
                                bean = mDaoHelper.getCollectRecord(collectRecordBean.getFilepaths());
                            } else {
                                UploadManager.getInstance().notifyPause(collectRecordBean);
                                ToastUtils.showShort(activity, R.string.network_error);
                                break;
                            }
                            Thread.sleep(100);
                        }
                        isRunning = false;
                        dismiss();
                    } else {
                        isRunning = false;
                        dismiss();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isRunning = false;
                    dismiss();
                }
            }
        });
        thread.start();
    }

    /**
     * 设置negative的点击
     *
     * @param listener OnNegativeClickListener
     * @return UploadDialog
     */
    public UploadDialog setNegativeButton(OnNegativeClickListener listener) {
        onNegativeClickListener = listener;
        return this;
    }

    /**
     * 设置positive的点击
     *
     * @param listener OnPositiveClickListener
     * @return UploadDialog
     */
    public UploadDialog setPositiveButton(OnPositiveClickListener listener) {
        onPositiveClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_run_back) {
            dismiss();
            if (onPositiveClickListener != null) {
                onPositiveClickListener.onClick();
            }
        } else if (i == R.id.tv_cancel) {
            UploadManager.getInstance().notifyPause(recordBean);
            dismiss();
            if (onNegativeClickListener != null) {
                onNegativeClickListener.onClick();
            }
        }
    }

    private OnPositiveClickListener onPositiveClickListener;

    public interface OnPositiveClickListener {
        void onClick();
    }

    private OnNegativeClickListener onNegativeClickListener;

    public interface OnNegativeClickListener {
        void onClick();
    }

}
