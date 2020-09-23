package com.sfb.baselib.widget.gridimage;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sfb.baselib.R;
import com.sfb.baselib.data.FileModel;
import com.sfb.baselib.imageloader.ImageLoader;
import com.sfb.baselib.storage.StoragePathUtils;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.FileHelper;
import com.sfb.baselib.widget.ProgressView;
import com.sfb.baselib.widget.gridimage.contract.GirdImageContract;
import com.sfb.baselib.widget.gridimage.presenter.GirdImagePresenter;
import com.pref.GuardPreference_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageItemView extends FrameLayout implements GirdImageContract.View {

    private Context context;
    private GirdImagePresenter mPresenter;
    private FileModel model;

    private FrameLayout mLayoutItem;
    private ImageView mIvImage;
    private ImageView mIvPlayer;
    private ImageView mIvDelete;
    private ProgressView mProgressView;

    private boolean isDowning;//是否正在下载
    private boolean isCanDelete;//是否可删除

    /**
     * Item点击删除回调
     */
    public interface ImageDeleteCallback {
        void onDeleteCallback();
    }

    private ImageDeleteCallback imageDeleteCallback;

    public void setImageDeleteCallback(ImageDeleteCallback imageDeleteCallback) {
        this.imageDeleteCallback = imageDeleteCallback;
    }

    /**
     * Item点击回调
     */
    public interface OnAddItemClickListener {
        void onAddItemClick();
    }

    private OnAddItemClickListener onAddItemClickListener;

    public void setOnAddItemClickListener(OnAddItemClickListener onAddItemClickListener) {
        this.onAddItemClickListener = onAddItemClickListener;
    }

    public ImageItemView(@NonNull Context context) {
        super(context);
        //初始化
        init(context);
    }

    public ImageItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化
        init(context);
    }

    /**
     * 初始化
     *
     * @param context Context
     */
    private void init(Context context) {
        inflate(context, R.layout.layout_image_item, this);
        this.context = context;
        this.mPresenter = new GirdImagePresenter();
        this.mPresenter.attach(this);
        mLayoutItem = findViewById(R.id.layout_item);
        mIvImage = findViewById(R.id.iv_image);
        mIvPlayer = findViewById(R.id.iv_player);
        mIvDelete = findViewById(R.id.iv_delete);
        mProgressView = findViewById(R.id.mProgressView);
        mIvImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(model);
            }
        });
        mIvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageDeleteCallback != null) {
                    imageDeleteCallback.onDeleteCallback();
                }
            }
        });
    }

    /**
     * 设置是否可删除
     *
     * @param canDelete boolean
     */
    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    /**
     * 加载数据
     *
     * @param path String
     */
    public void load(String path) {
        load(null, path);
    }

    /**
     * 加载数据
     *
     * @param model FileModel
     */
    public void load(FileModel model) {
        load(model, null);
    }

    /**
     * 加载数据
     *
     * @param model FileModel
     */
    public void load(FileModel model, String path) {
        this.model = model;
        if (model != null) {
            //根据FileModel初始化Item
            initItemByModel(mIvImage, mIvPlayer, mProgressView, model);
        } else if (!TextUtils.isEmpty(path)) {
            //根据Path初始化Item
            initItemByPath(path, mIvImage, mIvPlayer);
        } else {
            isCanDelete = false;
            mIvPlayer.setVisibility(GONE);
            mIvDelete.setVisibility(GONE);
            mProgressView.setVisibility(GONE);
            ImageLoader.get(context).load(R.drawable.ic_add_pic).into(mIvImage);
            mIvImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取焦点，防止在ScrollView滑动
                    ImageItemView.this.setFocusable(true);
                    ImageItemView.this.setFocusableInTouchMode(true);
                    ImageItemView.this.requestFocus();
                    ImageItemView.this.requestFocusFromTouch();
                    if (onAddItemClickListener != null) {
                        onAddItemClickListener.onAddItemClick();
                    }
                }
            });
        }
        mIvDelete.setVisibility(isCanDelete ? VISIBLE : GONE);
        int padding = CommonUtils.dp2px(context, 6);
        mLayoutItem.setPadding(0, padding, padding, 0);
    }

    /**
     * 根据FileModel初始化Item
     *
     * @param mIvImage      ImageView
     * @param mIvPlayer     ImageView
     * @param mProgressView ProgressView
     * @param model         FileModel
     */
    private void initItemByModel(ImageView mIvImage, final ImageView mIvPlayer, final ProgressView mProgressView, final FileModel model) {
        boolean isVideo = model.getFileContentType().contains("video");
        boolean isAudio = model.getFileContentType().contains("amr");//音频没图形文件，加载不出来
        String path = FileHelper.getFileUrl(String.valueOf(model.getFileId()), 0, 100, 100, isVideo, true);
        String header = "token";
        String cookie = GuardPreference_.getInstance(context).getToken();
        ImageLoader.get(context).saveToMemoryCache(true)
                .loadWithCookie(path, header, cookie)
                .error(isAudio ? R.drawable.ic_placeholder_player : R.drawable.ic_placeholder_picture)
                .into(mIvImage);
        mIvPlayer.setVisibility(isVideo ? View.VISIBLE : View.GONE);
        mIvImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(model);
            }
        });
    }

    /**
     * 根据Path初始化Item
     *
     * @param path,     String
     * @param mIvImage  ImageView
     * @param mIvPlayer ImageView
     */
    private void initItemByPath(final String path, ImageView mIvImage, ImageView mIvPlayer) {
        boolean isVideo = FileHelper.checkFileType(FileHelper.getMIMEType(path), FileHelper.VIDEO_TYPE);
        boolean isAudio = FileHelper.checkFileType(FileHelper.getMIMEType(path), FileHelper.AUDIO_TYPE);
        if (isAudio) {
            ImageLoader.get(context).load(R.drawable.ic_audio_file).into(mIvImage);
        } else {
            mIvPlayer.setVisibility(isVideo ? View.VISIBLE : View.GONE);
            ImageLoader.get(context).saveToMemoryCache(true)
                    .load(path)
                    .error(R.drawable.ic_placeholder_picture)
                    .into(mIvImage);
        }
        mIvImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FileHelper.openMedia(v.getContext(), path);
            }
        });
    }

    /**
     * 下载文件
     *
     * @param model FileModel
     */
    public void downloadFile(FileModel model) {
        if (isDowning) {
            return;
        }
        boolean isVideo = FileHelper.checkFileType(model.getFileContentType(), FileHelper.VIDEO_TYPE);
        boolean isAudio = FileHelper.checkFileType(model.getFileContentType(), FileHelper.AUDIO_TYPE);
        isDowning = true;
        String path;
        if (isVideo) {
            path = StoragePathUtils.getVideoPath(model.getFileName());
        } else if (isAudio) {
            path = StoragePathUtils.getAudioPath(model.getFileName());
        } else {
            path = StoragePathUtils.getImagePath(model.getFileName());
        }
        String url = FileHelper.getDownloadUrl(String.valueOf(model.getFileId()));
        mPresenter.downloadFile(path, url);
    }

    @Override
    public void onDownLoadSuccess(final String filePath) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!((Activity) context).isFinishing()) {
                    isDowning = false;
                    mProgressView.setVisibility(View.GONE);
                    FileHelper.openMedia(context, filePath);
                }
            }
        });
    }

    @Override
    public void onDownLoadFail(Throwable throwable) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isDowning = false;
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onProgress(final long current, final long total) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressView.setVisibility(View.VISIBLE);
                int progress = (int) (current * 100 / total);
                mProgressView.setProgress(progress);
            }
        });
    }

}
