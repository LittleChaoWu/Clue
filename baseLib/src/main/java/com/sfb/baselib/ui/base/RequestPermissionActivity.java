package com.sfb.baselib.ui.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;


import com.sfb.baselib.R;
import com.sfb.baselib.ui.mvp.BaseMVPActivity;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.widget.dialog.PromptDialog;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * 权限申请的Activity的基类
 */
public abstract class RequestPermissionActivity<V extends BaseView, P extends BasePresenter> extends BaseMVPActivity<V, P> {

    /**
     * 获取手机信息权限的code
     */
    private final int PERMISSION_REQUEST_PHONE = 0x1000;
    /**
     * 获取存储权限的code
     */
    private final int PERMISSION_REQUEST_IO = 0x1001;
    /**
     * 获取位置权限的code
     */
    private final int PERMISSION_REQUEST_LOCATION = 0x1002;
    /**
     * 获取相机权限的code
     */
    private final int PERMISSION_REQUEST_CAMERA = 0x1003;
    /**
     * 获取录音权限的code
     */
    private final int PERMISSION_REQUEST_AUDIO = 0x1004;
    /**
     * 获取拨号权限的code
     */
    private final int PERMISSION_REQUEST_CALL = 0x1005;

    public boolean isNeedCheckAgain = true;//是否需要重新检查权限

    /**
     * 请求手机权限
     *
     * @return 权限是否申请
     */
    protected boolean requestPhone() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_PHONE);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求手机权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestPhoneCallback(boolean isSuccess) {
    }

    /**
     * 请求存储权限
     *
     * @return 权限是否申请
     */
    protected boolean requestIO() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_IO);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求存储权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestIOCallback(boolean isSuccess) {
    }

    /**
     * 请求位置权限
     *
     * @return 权限是否申请
     */
    protected boolean requestLocation() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_LOCATION);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求位置权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestLocationCallback(boolean isSuccess) {
    }

    /**
     * 请求相机权限
     *
     * @return 权限是否申请
     */
    protected boolean requestCamera() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.CAMERA};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_CAMERA);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求相机权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestCameraCallback(boolean isSuccess) {
    }

    /**
     * 请求录音权限
     *
     * @return 权限是否申请
     */
    protected boolean requestAudio() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_AUDIO);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求录音权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestAudioCallback(boolean isSuccess) {
    }

    /**
     * 请求拨号权限
     *
     * @return 权限是否申请
     */
    protected boolean requestCall() {
        isNeedCheckAgain = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
            if (this.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_CALL);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求拨号权限回调
     *
     * @param isSuccess 是否申请成功
     */
    protected void requestCallCallback(boolean isSuccess) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isSuccess = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isSuccess = false;
                break;
            }
        }
        switch (requestCode) {
            //请求手机权限
            case PERMISSION_REQUEST_PHONE:
                requestPhoneCallback(isSuccess);
                break;
            case PERMISSION_REQUEST_IO:
                requestIOCallback(isSuccess);
                break;
            case PERMISSION_REQUEST_LOCATION:
                requestLocationCallback(isSuccess);
                break;
            case PERMISSION_REQUEST_CAMERA:
                requestCameraCallback(isSuccess);
                break;
            case PERMISSION_REQUEST_AUDIO:
                requestAudioCallback(isSuccess);
                break;
            case PERMISSION_REQUEST_CALL:
                requestCallCallback(isSuccess);
                break;
            default:
                break;
        }
    }

    /**
     * 显示权限被拒绝后的弹窗
     */
    public void showPermissionRefusedDialog(@StringRes int requestMessage, final boolean isFinish) {
        showPermissionRefusedDialog(requestMessage, new PromptDialog.OnNegativeClickListener() {
            @Override
            public void onClick() {
                if (isFinish) {
                    finish();
                }
            }
        });
    }

    /**
     * 显示权限被拒绝后的弹窗
     */
    public void showPermissionRefusedDialog(@StringRes int requestMessage, PromptDialog.OnNegativeClickListener listener) {
        new PromptDialog(this)
                .setTitle(getString(R.string.request_permission))
                .setMessage(getString(requestMessage))
                .messageAlign(Gravity.LEFT)
                .setNegativeButton(listener)
                .setPositiveButton(getString(R.string.go_to_set), new PromptDialog.OnPositiveClickListener() {
                    @Override
                    public void onClick() {
                        isNeedCheckAgain = true;//是否需要重新检查权限
                        openPermissionManager(RequestPermissionActivity.this);
                    }
                }).show();
    }

    /**
     * 跳转到权限设置界面
     */
    public void openPermissionManager(Context context) {
        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }
        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
        appIntent = context.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }

}
