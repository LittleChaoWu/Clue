package com.sfb.clue;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sfb.baselib.R;
import com.sfb.baselib.builder.CollectRecordBuilder;
import com.sfb.baselib.components.bus.annotation.BusSubscribe;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.components.bus.event.UploadErrorEvent;
import com.sfb.baselib.data.ClueFeedbackInfo;
import com.sfb.baselib.data.ThumbInfo;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.storage.StoragePathUtils;
import com.sfb.baselib.ui.base.BaseUploadActivity;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.utils.MediaUtils;
import com.sfb.baselib.widget.dialog.ActionSheetDialog;
import com.sfb.baselib.widget.dialog.DialogMenuItem;
import com.sfb.baselib.widget.form.LinearView;
import com.sfb.baselib.widget.gridimage.GridImageView;
import com.sfb.baselib.widget.gridimage.ImageItemView;

import java.io.File;
import java.util.ArrayList;

/**
 * 线索反馈界面
 */
@Route(path = ActivityRoute.CLUE_FEEDBACK_PATH)
public class ClueFeedbackActivity extends BaseUploadActivity {

    public static final String CLUE_ID_KEY = "clueId";

    private final int TAKE_IMAGE_REQUEST_CODE = 0x110;
    private final int TAKE_VIDEO_REQUEST_CODE = 0x111;

    private LinearView mLinearFeedback;
    private GridImageView mGridImageView;

    @Autowired
    public int clueId;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_feedback);
        mARouter.inject(this);
        //初始化视图
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mLinearFeedback = findViewById(R.id.linear_feedback);
        mGridImageView = findViewById(R.id.mGridImageView);
        findViewById(R.id.tv_submit).setOnClickListener(this);
        mGridImageView.setOnAddItemClickListener(new ImageItemView.OnAddItemClickListener() {
            @Override
            public void onAddItemClick() {
                if (mGridImageView.getCount() - 1 < 5) {
                    showMediaDialog();
                } else {
                    showToast(R.string.clue_dispose_file_count_over_tip);
                }
            }
        });
    }

    /**
     * 显示媒体获取路径对话框
     */
    private void showMediaDialog() {
        ArrayList<DialogMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new DialogMenuItem(0, getString(com.sfb.baselib.R.string.scene_photo)));
        menuItems.add(new DialogMenuItem(1, getString(com.sfb.baselib.R.string.live_video)));
        final ActionSheetDialog dialog = new ActionSheetDialog(this);
        dialog.setMenuDatas(menuItems);
        dialog.setOnItemClickListener(new ActionSheetDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        String fileName = "add_clue_" + System.currentTimeMillis() + ".jpg";
                        String path = StoragePathUtils.getImagePath(fileName);
                        mARouter.build(ActivityRoute.IMAGE_TAKE_PATH)
                                .withString("filePath", path)
                                .navigation(ClueFeedbackActivity.this, TAKE_IMAGE_REQUEST_CODE);
                        break;
                    case 1:
                        if (requestCamera()) {
                            if (requestIO()) {
                                MediaUtils.takeVideo(ClueFeedbackActivity.this, TAKE_VIDEO_REQUEST_CODE);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void requestCameraCallback(boolean isSuccess) {
        if (isSuccess) {
            requestIO();
        } else {
            showPermissionRefusedDialog(com.sfb.baselib.R.string.camera_permission_request_message, false);
        }
    }

    @Override
    protected void requestIOCallback(boolean isSuccess) {
        if (!isSuccess) {
            showPermissionRefusedDialog(com.sfb.baselib.R.string.io_permission_request_message, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case TAKE_IMAGE_REQUEST_CODE:
                    if (data.getData() != null) {
                        addThumbInfo(data.getData().getPath());
                    }
                    break;
                case TAKE_VIDEO_REQUEST_CODE:
                    if (data.getData() != null) {
                        saveVideoFile(data.getData());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 保存视频文件
     *
     * @param uri Uri
     */
    private void saveVideoFile(Uri uri) {
        if (uri != null) {
            String path = null;
            if (uri.toString().startsWith("content://")) {
                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    }
                    cursor.close();
                }
            } else {
                path = uri.getPath();
            }
            if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                addThumbInfo(path);
            }
        }
    }

    /**
     * 添加数据额
     *
     * @param path String
     */
    private void addThumbInfo(String path) {
        ThumbInfo info = new ThumbInfo(path);
        mGridImageView.addData(info);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_submit) {
            //提交
            submit();
        }
    }

    /**
     * 提交
     */
    private void submit() {
        ClueFeedbackInfo info = checkEnable();
        if (info == null) {
            return;
        }
        CollectRecordBean bean = new CollectRecordBuilder(this).build(info);
        //上传检测
        uploadCheck(bean);
    }

    /**
     * 检测是否可以提交
     */
    private ClueFeedbackInfo checkEnable() {
        String feedback = mLinearFeedback.getText();
        if (TextUtils.isEmpty(feedback)) {
            showLoading(getString(R.string.clue_feedback_tip));
            return null;
        }
        ClueFeedbackInfo info = new ClueFeedbackInfo();
        info.setClueId(clueId);
        info.setRemark(feedback);
        info.setFilePaths(mGridImageView.getFilePaths());
        return info;
    }

    @Override
    public void runInBackCallback() {

    }

    /**
     * 记录上传成功回调
     *
     * @param event CollectRecordEvent
     */
    @BusSubscribe
    public void onEvent(CollectRecordEvent event) {
        if (isUploadSuccess(event)) {
            finish();
        }
    }

    @BusSubscribe
    public void onEvent(UploadErrorEvent event) {
        uploadError(event);
    }

}
