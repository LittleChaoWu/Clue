package com.sfb.baselib.ui.media.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;


import com.sfb.baselib.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageCameraHelper {

    //后置相机
    public static final int CAMERA_FACING_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;
    //前置相机
    public static final int CAMERA_FACING_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;

    //相机旋转的角度
    private static final int CAMERA_ROTATION = 90;

    //SurfaceTexture
    private SurfaceTexture mSurface;
    private int width;
    private int height;
    //相机
    private Camera camera;
    //相机方向
    private int mCameraFace = CAMERA_FACING_BACK;
    //上下文
    private Context context;
    //对焦回调
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Camera.Parameters params = camera.getParameters();
            params.setFocusMode(params.getFlashMode());
            camera.setParameters(params);
        }
    };

    public ImageCameraHelper(Context context) {
        this.context = context;
    }

    /**
     * 设置摄像头方向
     *
     * @param mCameraFace int
     */
    public void setCameraFace(int mCameraFace) {
        this.mCameraFace = mCameraFace;
    }

    /**
     * 启动相机
     *
     * @param surface SurfaceTexture
     */
    public void openCamera(SurfaceTexture surface, int width, int height) {
        mSurface = surface;
        this.width = width;
        this.height = height;
        try {
            camera = Camera.open(mCameraFace);
        } catch (Exception e) {
            if ("Fail to connect to camera service".equalsIgnoreCase(e.getMessage())) {
//                ToastUtils.showLong(context, context.getString(R.string.camera_permission_deny));
                return;
            }
        }
        try {
            //旋转90，保证垂直拍摄
            camera.setDisplayOrientation(CAMERA_ROTATION);
            //初始化相机参数
            initCameraParams(mCameraFace, width, height);
            //设置预览界面
            camera.setPreviewTexture(surface);
            //开始预览
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }
    }

    /**
     * 初始化相机参数
     */
    private void initCameraParams(int camareFace, int width, int height) {
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (camareFace == CAMERA_FACING_BACK) {
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes != null) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        }
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        if (previewSizes != null && previewSizes.size() > 0) {
            Camera.Size optimalPreSize = getOptimalSize(previewSizes, width, height);
            parameters.setPreviewSize(optimalPreSize.width, optimalPreSize.height);
        }
        if (pictureSizes != null && pictureSizes.size() > 0) {
            Camera.Size optimalPicSize = getOptimalSize(pictureSizes, width, height);
            parameters.setPictureSize(optimalPicSize.width, optimalPicSize.height);
        }
        camera.setParameters(parameters);
    }

    /**
     * 获取最佳预览相机Size参数
     *
     * @return
     */
    private Camera.Size getOptimalSize(List<Camera.Size> sizes, int w, int h) {
        Camera.Size optimalSize = null;
        float targetRadio = h / (float) w;
        //最匹配的比例
        float optimalDif = Float.MAX_VALUE;
        //最优的最大值差距
        int optimalMaxDif = Integer.MAX_VALUE;
        for (Camera.Size size : sizes) {
            float newOptimal = size.width / (float) size.height;
            float newDiff = Math.abs(newOptimal - targetRadio);
            //更好的尺寸
            if (newDiff < optimalDif) {
                optimalDif = newDiff;
                optimalSize = size;
                optimalMaxDif = Math.abs(h - size.width);
            } else if (newDiff == optimalDif) {
                //若比例一致，则选择与屏幕宽高最相近的那个
                int newOptimalMaxDif = Math.abs(h - size.width);
                if (newOptimalMaxDif < optimalMaxDif) {
                    optimalDif = newDiff;
                    optimalSize = size;
                    optimalMaxDif = newOptimalMaxDif;
                }
            }
        }
        return optimalSize;
    }

    /**
     * 释放摄像头
     */
    public void closeCamera() {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.release();
                camera = null;
            } catch (Exception e) {
                if (camera != null) {
                    camera.release();
                    camera = null;
                }
            }
        }
    }

    /**
     * 判断相机是后置还是前置
     *
     * @return
     */
    private boolean isFaceBack() {
        return mCameraFace == CAMERA_FACING_BACK;
    }

    /**
     * 摄像机是否支持前置拍照
     *
     * @return
     */
    private boolean isSupportFrontCamera() {
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更换前后置摄像
     */
    public void changeCameraFacing() {
        if (isSupportFrontCamera()) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            //得到摄像头的个数
            int cameraCount = Camera.getNumberOfCameras();
            for (int i = 0; i < cameraCount; i++) {
                //得到每一个摄像头的信息
                Camera.getCameraInfo(i, cameraInfo);
                //现在是后置，变更为前置
                if (mCameraFace == CAMERA_FACING_FRONT) {
                    //代表摄像头的方位为前置
                    if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                        closeCamera();
                        mCameraFace = CAMERA_FACING_BACK;
                        openCamera(mSurface, width, height);
                        break;
                    }

                } else {
                    //现在是前置，变更为后置
                    //代表摄像头的方位
                    if (cameraInfo.facing == CAMERA_FACING_BACK) {
                        closeCamera();
                        mCameraFace = CAMERA_FACING_FRONT;
                        openCamera(mSurface, width, height);
                        break;
                    }
                }
            }
        } else {
            //不支持摄像机
            ToastUtils.showShort(context, "您的手机不支持前置摄像");
        }
    }

    /**
     * 缩放
     *
     * @param zoomIn
     */
    public void setZoom(int zoomIn) {
        if (camera == null) {
            return;
        }
        Camera.Parameters params = camera.getParameters();
        if (params == null) {
            return;
        }
        if (params.isZoomSupported()) {
            int maxZoom = params.getMaxZoom();
            int zoom = params.getZoom() + zoomIn;
            if (zoom > maxZoom) {
                zoom = maxZoom;
            } else if (zoom < 0) {
                zoom = 0;
            }
            params.setZoom(zoom);
            camera.setParameters(params);
        }
    }

    /**
     * 自动对焦
     */
    public void autoFocus() {
        camera.autoFocus(mAutoFocusCallback);
    }


    /**
     * 对焦
     *
     * @param x
     * @param y
     */
    public void onFocus(float x, float y) {
        Camera.Parameters params = camera.getParameters();
        Camera.Size previewSize = params.getPreviewSize();
        Rect focusRect = calculateTapArea(x, y, 1f, previewSize);
        Rect meteringRect = calculateTapArea(x, y, 1.5f, previewSize);
        camera.cancelAutoFocus();
        if (params.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<>();
            focusAreas.add(new Camera.Area(focusRect, 1000));
            params.setFocusAreas(focusAreas);
        }
        if (params.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));
            params.setMeteringAreas(meteringAreas);
        }
        final String currentFocusMode = params.getFocusMode();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        try {
            camera.setParameters(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(currentFocusMode);
                camera.setParameters(params);
            }
        });
    }

    /**
     * 计算点击区域
     *
     * @param x
     * @param y
     * @param coefficient
     * @param previewSize
     * @return
     */
    private Rect calculateTapArea(float x, float y, float coefficient, Camera.Size previewSize) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) (x / previewSize.width - 1000);
        int centerY = (int) (y / previewSize.height - 1000);
        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    /**
     * 获取范围内的值
     *
     * @param x
     * @param min
     * @param max
     * @return
     */
    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    /**
     * 拍照
     */
    public void takePick(TakePicCallback takePicCallback) {
        this.takePicCallback = takePicCallback;
        if (camera != null) {
            camera.takePicture(null, null, mPictureCallback);
        } else {
            if (this.takePicCallback != null) {
                this.takePicCallback.onTakePic(null);
            }
        }
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (null == bitmap) {
                // 使拍照结束后重新预览
                closeCamera();
                openCamera(mSurface, width, height);
                if (takePicCallback != null) {
                    takePicCallback.onTakePic(null);
                }
                return;
            }
            Log.i("message", "bitmap width===" + bitmap.getWidth() + " bitmap height===" + bitmap.getHeight());
            //对于前置摄像头和后置摄像头采用不同的旋转角度  前置摄像头还需要做镜像水平翻转
            System.gc();
            Matrix matrix = new Matrix();
            matrix.setRotate(getRotation());
            if (mCameraFace == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                matrix.postScale(-1, 1);   //镜像水平翻转
            }
            Bitmap bmp;
            bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            if (bmp != bitmap) {
                bitmap.recycle();
            }
            if (takePicCallback != null) {
                takePicCallback.onTakePic(bmp);
            }
            // 使拍照结束后重新预览
            closeCamera();
            openCamera(mSurface, width, height);
        }
    };

    /**
     * 获取照片旋转角度
     *
     * @return int
     */
    public int getRotation() {
        if (mCameraFace == CAMERA_FACING_FRONT) {
            return 360 - CAMERA_ROTATION;
        } else {
            return CAMERA_ROTATION;
        }
    }

    private TakePicCallback takePicCallback;

    /**
     * 拍照回调
     */
    public interface TakePicCallback {
        void onTakePic(Bitmap bitmap);
    }
}
