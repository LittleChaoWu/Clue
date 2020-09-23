package com.sfb.baselib.ui.media;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sfb.baselib.R;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.ui.base.BaseActivity;
import com.sfb.baselib.ui.media.view.ImageCameraHelper;
import com.sfb.baselib.ui.media.view.ImagePreview;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.RWFileUtils;

import java.io.File;

/**
 * 自定义相机
 */
@Route(path = ActivityRoute.IMAGE_TAKE_PATH)
public class ImageTakeActivity extends BaseActivity {

    private ImagePreview mCameraView;

    @Autowired
    public String filePath;
    @Autowired
    public boolean isFront;

    private static final String MEDIA_DEFAULT_PATH = Environment.getDownloadCacheDirectory() + "/DCIM/Camera/";
    private static final String IMAGE_SUFFIX = ".jpg";
    private boolean hasTakePic;

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mARouter.inject(this);
        setContentView(R.layout.activity_image_take);
        //初始化视图
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckAgain) {
            //请求相机权限
            if (requestCamera()) {
                if (mCameraView.isAvailable()) {
                    mCameraView.refreshCamera();
                }
                requestIO();
            }
        }
    }

    @Override
    protected void requestCameraCallback(boolean isSuccess) {
        if (isSuccess) {
            mCameraView.refreshCamera();
            requestIO();
        } else {
            showPermissionRefusedDialog(R.string.camera_permission_request_message, true);
        }
    }

    @Override
    protected void requestIOCallback(boolean isSuccess) {
        if (!isSuccess) {
            showPermissionRefusedDialog(R.string.io_permission_request_message, true);
        }
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mCameraView = findViewById(R.id.camera_view);
        if (isFront) {
            mCameraView.setCameraFace(ImageCameraHelper.CAMERA_FACING_FRONT);
        }
        View btnCapture = findViewById(R.id.capture_ib);
        View btnChangeFacing = findViewById(R.id.iv_change_facing);
        int navigationBarHeight = CommonUtils.getNavigationBarHeight(this);
        if (navigationBarHeight > 0) {
            RelativeLayout.LayoutParams captureParams = (RelativeLayout.LayoutParams) btnCapture.getLayoutParams();
            captureParams.setMargins(captureParams.leftMargin, captureParams.topMargin, captureParams.rightMargin,
                    captureParams.bottomMargin + navigationBarHeight);
            btnCapture.setLayoutParams(captureParams);
            RelativeLayout.LayoutParams btnChangeFacingLayoutParams = (RelativeLayout.LayoutParams) btnChangeFacing.getLayoutParams();
            btnChangeFacingLayoutParams
                    .setMargins(btnChangeFacingLayoutParams.leftMargin, btnChangeFacingLayoutParams.topMargin, btnChangeFacingLayoutParams.rightMargin,
                            btnChangeFacingLayoutParams.bottomMargin + navigationBarHeight);
            btnChangeFacing.setLayoutParams(btnChangeFacingLayoutParams);
        }
        btnCapture.setOnClickListener(this);
        btnChangeFacing.setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.capture_ib) {
            if (hasTakePic) {
                return;
            }
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                hasTakePic = true;
                mCameraView.takePic(new ImageCameraHelper.TakePicCallback() {
                    @Override
                    public void onTakePic(Bitmap bitmap) {
                        if (bitmap != null) {
                            RWFileUtils.saveBitmap(getSavePath(), bitmap);
                            Intent intent = new Intent();
                            intent.setData(Uri.fromFile(new File(filePath)));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            } else {
                showToast("该设备暂无摄像头");
            }
        } else if (v.getId() == R.id.iv_change_facing) {
            mCameraView.changeCameraFacing();
        } else if (v.getId() == R.id.iv_close) {
            finish();
        }
    }

    private String getSavePath() {
        if (!TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(MEDIA_DEFAULT_PATH).append(System.currentTimeMillis()).append(IMAGE_SUFFIX);
        filePath = sb.toString();
        return filePath;
    }

}
