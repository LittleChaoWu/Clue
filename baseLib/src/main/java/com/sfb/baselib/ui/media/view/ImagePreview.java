package com.sfb.baselib.ui.media.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;


public class ImagePreview extends TextureView implements TextureView.SurfaceTextureListener {

    //SurfaceTexture
    private SurfaceTexture mSurface;
    //ImageCameraHelper
    ImageCameraHelper mImageCameraHelper;

    private ImageView mFocusView;
    private Animation mFocusAnimation;
    private float mPointSpace;
    private boolean isZoom;

    public ImagePreview(Context context) {
        this(context, null);
    }

    public ImagePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSurfaceTextureListener(this);
        mImageCameraHelper = new ImageCameraHelper(context);
        mFocusAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.custom_camera_focusview_show);
        mFocusAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFocusView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 刷新相机
     */
    public void refreshCamera() {
        mImageCameraHelper.openCamera(mSurface, getWidth(), getHeight());
    }

    /**
     * 设置摄像头方向
     *
     * @param mCameraFace int
     */
    public void setCameraFace(int mCameraFace) {
        mImageCameraHelper.setCameraFace(mCameraFace);
    }

    /**
     * 创建 FouceView
     *
     * @param context Context
     * @return ImageView
     */
    private ImageView createFocusView(Context context) {
        ImageView image = new ImageView(context);
        int width = CommonUtils.dp2px(context, 75f);
        int height = width;
        image.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        image.setImageResource(R.drawable.ic_camera_focus);
        ViewGroup parent = (ViewGroup) getParent();
        parent.addView(image);
        return image;
    }

    /**
     * 显示 FouceView
     *
     * @param x
     * @param y
     */
    private void showFocusView(int x, int y) {
        if (mFocusView == null) {
            mFocusView = createFocusView(getContext());
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFocusView.getLayoutParams();
        params.leftMargin = x - mFocusView.getMeasuredWidth() / 2;
        params.topMargin = y - mFocusView.getMeasuredHeight() / 2;
        mFocusView.setLayoutParams(params);
        mFocusView.setVisibility(View.VISIBLE);
        mFocusView.startAnimation(mFocusAnimation);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = surface;
        mImageCameraHelper.openCamera(surface, width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        mImageCameraHelper.autoFocus();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mImageCameraHelper.closeCamera();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                mPointSpace = getPointsSpace(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    isZoom = true;
                    float lastSpace = getPointsSpace(event);
                    int zoom = (int) ((lastSpace / mPointSpace));
                    zoom = zoom == 0 ? -1 : zoom;
                    mImageCameraHelper.setZoom(zoom);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isZoom && event.getPointerCount() == 1) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    mImageCameraHelper.onFocus(x, y);
                    showFocusView(x, y);
                }
                isZoom = false;
                mPointSpace = 0;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = CommonUtils.getScreenWidth(getContext());
        int height = CommonUtils.getScreenHeight(getContext());
        if (height * 1.0f / width > (16 / 9)) {
            height = width * 16 / 9;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 获取两个手指头之间的距离
     *
     * @param event MotionEvent
     */
    private float getPointsSpace(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        float x1 = event.getX(1);
        float y1 = event.getY(1);
        float dx = Math.abs(x - x1);
        float dy = Math.abs(y - y1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 获取SurfaceTexture
     *
     * @return SurfaceTexture
     */
    @Override
    public SurfaceTexture getSurfaceTexture() {
        return mSurface;
    }

    /**
     * 更换前后置摄像
     */
    public void changeCameraFacing() {
        mImageCameraHelper.changeCameraFacing();
    }

    /**
     * 拍照
     */
    public void takePic(ImageCameraHelper.TakePicCallback takePicCallback) {
        if (mSurface != null) {
            mImageCameraHelper.takePick(takePicCallback);
        }
    }

}
