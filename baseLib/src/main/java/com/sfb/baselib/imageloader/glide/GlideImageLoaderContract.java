package com.sfb.baselib.imageloader.glide;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * Created by huangqh on 2017/6/22.
 */

public interface GlideImageLoaderContract {
    /**
     * 设置居中裁切
     */
    GlideImageLoaderContract centerCrop();

    /**
     * 设置居中自适应
     */
    GlideImageLoaderContract fitCenter();

    /**
     * 设置居中不裁剪
     */
    GlideImageLoaderContract centerInside();

    /**
     * 使用默认渐变效果
     */
    GlideImageLoaderContract crossFade();

    /**
     * 是否进行内存缓存
     *
     * @param isSaveToMemoryCache 是否进行内存缓存
     */
    GlideImageLoaderContract saveToMemoryCache(boolean isSaveToMemoryCache);

    /**
     * 设置硬盘缓存策略，具体策略类型详见{@link GlideBuilderBean.DiskCacheStrategy}
     *
     * @param diskCacheStrategy 硬盘缓存策略
     */
    GlideImageLoaderContract setDiskCacheStrategy(@GlideBuilderBean.DiskCacheStrategy int diskCacheStrategy);

    /**
     * 设置动画资源id
     *
     * @param animResId 动画资源id
     */
    GlideImageLoaderContract setAnimResId(@AnimRes int animResId);

    /**
     * 设置图片加载监听器
     *
     * @param requestListener 图片加载监听器
     */
    GlideImageLoaderContract listener(RequestListener requestListener);

    /**
     * 装载图片
     */
    void into(ImageView imageView);

    /**
     * 将图片转为bitmap并装载
     */
    void asBitmapInto(ImageView imageView);

    /**
     * 将图片转为bitmap并装载在SimpleTarget里面
     */
    void asBitmapInto(SimpleTarget<Bitmap> target);

    /**
     * 将图片转为gif并装载
     */
    void asGifInto(ImageView imageView);

    /**
     * 将图片转为gif并装载
     */
    void asGifInto(SimpleTarget<GifDrawable> target);

    /**
     * 设置加载路径
     *
     * @param object 加载路径（String/Uri/File/Integer/byte[]）
     */
    GlideImageLoaderContract load(Object object);

    /**
     * 设置加载路径（限需要cookie的url）
     *
     * @param url        图片的加载url
     * @param headerName cookie对应的header名称
     * @param cookie     cookie
     */
    GlideImageLoaderContract loadWithCookie(String url, String headerName, String cookie);

    /**
     * 设置加载图
     *
     * @param placeholderResId 加载图的资源文件
     */
    GlideImageLoaderContract placeholder(@DrawableRes @ColorRes int placeholderResId);

    /**
     * 设置加载失败图
     *
     * @param errorResId 加载失败图的资源文件
     */
    GlideImageLoaderContract error(@DrawableRes @ColorRes int errorResId);

    /**
     * 设置图片宽高
     *
     * @param width  宽（单位px）
     * @param height 高（单位px）
     */
    GlideImageLoaderContract setImageSize(int width, int height);

    /**
     * The transformations to apply in order
     *
     * @param transformations BitmapTransformation
     */
    GlideImageLoaderContract transform(BitmapTransformation... transformations);
}
