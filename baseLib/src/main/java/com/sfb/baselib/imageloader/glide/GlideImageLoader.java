package com.sfb.baselib.imageloader.glide;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.ObjectKey;
import com.sfb.baselib.imageloader.GlideApp;
import com.sfb.baselib.imageloader.GlideRequest;
import com.sfb.baselib.imageloader.GlideRequests;

import java.io.File;
import java.util.UUID;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by huangqh on 2017/6/16.
 */

public class GlideImageLoader implements GlideImageLoaderContract {

    /**
     * 构造对象实体
     */
    private GlideBuilderBean mGlideBuilderBean;
    /**
     * 请求管理对象
     */
    private GlideRequests mRequestManager;

    /**
     * 创建Glide加载库
     */
    public static GlideImageLoader with(Context context) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (context != null) {
            imageLoader.mGlideBuilderBean = new GlideBuilderBean();
            imageLoader.mRequestManager = GlideApp.with(context);
        }
        return imageLoader;
    }

    /**
     * 创建Glide加载库
     */
    public static GlideImageLoader with(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(activity);
            }
        } else {
            if (activity != null && !activity.isFinishing()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(activity);
            }
        }
        return imageLoader;
    }

    /**
     * 创建Glide加载库
     */
    public static GlideImageLoader with(FragmentActivity fragmentActivity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (fragmentActivity != null && !fragmentActivity.isFinishing() && !fragmentActivity.isDestroyed()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(fragmentActivity);
            }
        } else {
            if (fragmentActivity != null && !fragmentActivity.isFinishing()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(fragmentActivity);
            }
        }
        return imageLoader;
    }

    /**
     * 创建Glide加载库
     */
    public static GlideImageLoader with(Fragment fragment) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (fragment != null && fragment.getActivity() != null && !fragment.getActivity().isFinishing() && !fragment.getActivity().isDestroyed()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(fragment);
            }
        } else {
            if (fragment != null && fragment.getActivity() != null && !fragment.getActivity().isFinishing()) {
                imageLoader.mGlideBuilderBean = new GlideBuilderBean();
                imageLoader.mRequestManager = GlideApp.with(fragment);
            }
        }
        return imageLoader;
    }

    @Override
    public GlideImageLoaderContract load(Object object) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        if (object instanceof String || object instanceof Uri || object instanceof File || object instanceof Integer || object instanceof byte[]) {
            mGlideBuilderBean.path = object;
            return this;
        }
        throw new RuntimeException("Glide不支持String/Uri/File/Integer/byte[]以外的图片路径参数");
    }

    @Override
    public GlideImageLoaderContract loadWithCookie(String url, String headerName, String cookie) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.path = new GlideUrl(url, new LazyHeaders.Builder().addHeader(headerName, cookie).build());
        return this;
    }

    @Override
    public GlideImageLoaderContract placeholder(@DrawableRes @ColorRes int placeholderResId) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.placeholderResId = placeholderResId;
        return this;
    }

    @Override
    public GlideImageLoaderContract error(@DrawableRes @ColorRes int errorResId) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.errorResId = errorResId;
        return this;
    }

    @Override
    public GlideImageLoaderContract setImageSize(int width, int height) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.width = width;
        mGlideBuilderBean.height = height;
        return this;
    }

    @Override
    public GlideImageLoaderContract transform(BitmapTransformation... transformations) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.transformations = transformations;
        return this;
    }

    @Override
    public GlideImageLoaderContract saveToMemoryCache(boolean isSaveToMemoryCache) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.saveToMemoryCache = isSaveToMemoryCache;
        return this;
    }

    @Override
    public GlideImageLoaderContract setDiskCacheStrategy(@GlideBuilderBean.DiskCacheStrategy int diskCacheStrategy) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    @Override
    public GlideImageLoaderContract centerCrop() {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.centerCrop = true;
        return this;
    }

    @Override
    public GlideImageLoaderContract fitCenter() {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.fitCenter = true;
        return this;
    }

    @Override
    public GlideImageLoaderContract centerInside() {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.centerInside = true;
        return this;
    }

    @Override
    public GlideImageLoaderContract crossFade() {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.dontAnimate = false;
        mGlideBuilderBean.crossFade = true;
        return this;
    }

    @Override
    public GlideImageLoaderContract setAnimResId(@AnimRes int animResId) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.dontAnimate = false;
        mGlideBuilderBean.animResId = animResId;
        return this;
    }

    @Override
    public GlideImageLoaderContract listener(RequestListener requestListener) {
        if (mGlideBuilderBean == null) {
            return this;
        }
        mGlideBuilderBean.requestListener = requestListener;
        return this;
    }

    @Override
    public void into(ImageView imageView) {
        if (mRequestManager == null) {
            return;
        }
        GlideRequest<Drawable> builder = getDrawableTypeRequest(mRequestManager);
        builder.into(imageView);
    }

    @Override
    public void asBitmapInto(ImageView imageView) {
        if (mRequestManager == null) {
            return;
        }
        GlideRequest<Bitmap> request = getBitmapTypeRequest(mRequestManager);
        request.into(imageView);
    }

    @Override
    public void asBitmapInto(SimpleTarget<Bitmap> target) {
        if (mRequestManager == null) {
            return;
        }
        GlideRequest<Bitmap> request = getBitmapTypeRequest(mRequestManager);
        request.into(target);
    }

    @Override
    public void asGifInto(ImageView imageView) {
        if (mRequestManager == null) {
            return;
        }
        GlideRequest<GifDrawable> request = getGifTypeRequest(mRequestManager);
        request.into(imageView);
    }

    @Override
    public void asGifInto(SimpleTarget<GifDrawable> target) {
        if (mRequestManager == null) {
            return;
        }
        GlideRequest<GifDrawable> request = getGifTypeRequest(mRequestManager);
        request.into(target);
    }

    /**
     * 组装GlideRequest<Bitmap>
     */
    private GlideRequest<Bitmap> getBitmapTypeRequest(GlideRequests manager) {
        GlideRequest<Bitmap> request = manager.asBitmap().load(mGlideBuilderBean.path);
        return configBitmapTypeAnimate(request);
    }

    /**
     * 组装GlideRequest<GifDrawable>
     */
    private GlideRequest<GifDrawable> getGifTypeRequest(GlideRequests manager) {
        GlideRequest<GifDrawable> request = manager.asGif().load(mGlideBuilderBean.path);
        return configGifTypeAnimate(request);
    }

    /**
     * 组装GlideRequest<Drawable>
     */
    private GlideRequest<Drawable> getDrawableTypeRequest(GlideRequests manager) {
        GlideRequest<Drawable> request = manager.load(mGlideBuilderBean.path);
        return configDrawableTypeAnimate(request);
    }

    /**
     * 配置请求选项（包括占位符、转换、缓存策略等）
     *
     * @return 配置完成的请求选项
     */
    private RequestOptions configRequestOptions() {
        RequestOptions requestOptions = new RequestOptions()
                //设置加载图
                .placeholder(mGlideBuilderBean.placeholderResId)
                //设置加载失败图
                .error(mGlideBuilderBean.errorResId)
                //设置跳过内存缓存
                .skipMemoryCache(!mGlideBuilderBean.saveToMemoryCache);
        //设置居中裁剪
        if (mGlideBuilderBean.centerCrop) {
            requestOptions = requestOptions.centerCrop();
        }
        //设置居中自适应
        if (mGlideBuilderBean.fitCenter) {
            requestOptions = requestOptions.fitCenter();
        }
        //设置居中不裁剪
        if (mGlideBuilderBean.centerInside) {
            requestOptions = requestOptions.centerInside();
        }
        //对数组类型特殊处理
        if (mGlideBuilderBean.path instanceof byte[]) {
            requestOptions = requestOptions.signature(new ObjectKey(UUID.randomUUID().toString())).skipMemoryCache(true);
        }
        //配置硬盘缓存策略
        requestOptions = configDiskCacheStrategy(requestOptions);
        //设置图像宽高
        if (mGlideBuilderBean.width > 0 || mGlideBuilderBean.height > 0) {
            requestOptions = requestOptions.override(mGlideBuilderBean.width, mGlideBuilderBean.height);
        }
        //设置不使用动画
        if (mGlideBuilderBean.dontAnimate) {
            requestOptions = requestOptions.dontAnimate();
        }
        //设置图像转换
        if (mGlideBuilderBean.transformations != null && mGlideBuilderBean.transformations.length > 0) {
            requestOptions = requestOptions.transforms(mGlideBuilderBean.transformations);
        }

        return requestOptions;
    }

    /**
     * 配置glide request
     */
    @NonNull
    private <T> GlideRequest<T> configGlideRequest(GlideRequest<T> request) {
        //配置请求选项
        request = request.apply(configRequestOptions());
        //设置加载监听
        if (mGlideBuilderBean.requestListener != null) {
            request = request.listener(mGlideBuilderBean.requestListener);
        }
        return request;
    }

    /**
     * 配置Drawable的动画效果
     */
    private GlideRequest<Drawable> configDrawableTypeAnimate(GlideRequest<Drawable> request) {
        if (mGlideBuilderBean.crossFade) {
            request = request.transition(DrawableTransitionOptions.withCrossFade());
        }
        if (mGlideBuilderBean.animResId != -1) {
            request = request.transition(GenericTransitionOptions.<Drawable>with(mGlideBuilderBean.animResId));
        }
        if (mGlideBuilderBean.animator != null) {
            request = request.transition(new DrawableTransitionOptions().transition(mGlideBuilderBean.animator));
        }
        return configGlideRequest(request);
    }

    /**
     * 配置Bitmap的动画效果
     */
    private GlideRequest<Bitmap> configBitmapTypeAnimate(GlideRequest<Bitmap> request) {
        if (mGlideBuilderBean.crossFade) {
            request = request.transition(BitmapTransitionOptions.withCrossFade());
        }
        if (mGlideBuilderBean.animResId != -1) {
            request = request.transition(GenericTransitionOptions.<Bitmap>with(mGlideBuilderBean.animResId));
        }
        if (mGlideBuilderBean.animator != null) {
            request = request.transition(new BitmapTransitionOptions().transition(mGlideBuilderBean.animator));
        }
        return configGlideRequest(request);
    }

    /**
     * 配置GifDrawable的动画效果
     */
    private GlideRequest<GifDrawable> configGifTypeAnimate(GlideRequest<GifDrawable> request) {
        if (mGlideBuilderBean.animResId != -1) {
            request = request.transition(GenericTransitionOptions.<GifDrawable>with(mGlideBuilderBean.animResId));
        }
        if (mGlideBuilderBean.animator != null) {
            request = request.transition(new GenericTransitionOptions<>().transition(mGlideBuilderBean.animator));
        }
        return configGlideRequest(request);
    }

    /**
     * 配置硬盘缓存策略<br/>
     * 基本策略为：<br/>
     * 1.本地加载的不缓存(包括raw/asset/res/本地路径中的文件)<br/>
     * 2.加载视频时，需要设置为NONE或RESOURCE<br/>
     * 3.加载GIF时，需要设置为DATA<br/>
     * 4.默认策略为ALL
     *
     * @param requestOptions 配置硬盘缓存策略后的请求选项
     */
    private RequestOptions configDiskCacheStrategy(RequestOptions requestOptions) {
        if (mGlideBuilderBean == null) {
            return requestOptions;
        }
        if (mGlideBuilderBean.path instanceof File || mGlideBuilderBean.path instanceof Integer || mGlideBuilderBean.path instanceof byte[]) {
            //本地文件、资源文件、byte数组不做硬盘缓存
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (mGlideBuilderBean.path instanceof String && ((String) mGlideBuilderBean.path).startsWith("/")) {
            //String类型以“/”开头的为本地文件路径，如本地文件一样，不做硬盘缓存
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (mGlideBuilderBean.path instanceof Uri && isLocalUri(((Uri) mGlideBuilderBean.path).getScheme())) {
            //Uri判断是否是本地文件的Uri，若是，则为本地文件，不做硬盘缓存
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else {
            //其他类型则以传入的硬盘缓存策略为准，默认为保存原图及结果图
            switch (mGlideBuilderBean.diskCacheStrategy) {
                case GlideBuilderBean.DISK_CACHE_STRATEGY_NONE:
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    break;
                case GlideBuilderBean.DISK_CACHE_STRATEGY_RESOURCE:
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                    break;
                case GlideBuilderBean.DISK_CACHE_STRATEGY_DATA:
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
                    break;
                case GlideBuilderBean.DISK_CACHE_STRATEGY_ALL:
                default:
                    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    break;
            }
        }
        return requestOptions;
    }

    /**
     * 判断是否是本地的URI
     *
     * @param scheme
     * @return 是否本地uri
     */
    private boolean isLocalUri(String scheme) {
        return ContentResolver.SCHEME_FILE.equals(scheme)
                || ContentResolver.SCHEME_CONTENT.equals(scheme)
                || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme);
    }
}
