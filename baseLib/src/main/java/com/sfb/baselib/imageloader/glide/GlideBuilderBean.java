package com.sfb.baselib.imageloader.glide;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;

/**
 * Created by huangqh on 2017/6/16.
 */

class GlideBuilderBean {

    GlideBuilderBean() {
    }

    static final int DISK_CACHE_STRATEGY_NONE = 1;
    static final int DISK_CACHE_STRATEGY_DATA = 2;
    static final int DISK_CACHE_STRATEGY_RESOURCE = 3;
    static final int DISK_CACHE_STRATEGY_ALL = 4;

    /**
     * {@link #DISK_CACHE_STRATEGY_NONE} 不进行硬盘缓存<br/>
     * {@link #DISK_CACHE_STRATEGY_DATA}  硬盘缓存原图大小尺寸<br/>
     * {@link #DISK_CACHE_STRATEGY_RESOURCE}  硬盘缓存target尺寸大小图片<br/>
     * {@link #DISK_CACHE_STRATEGY_ALL}  硬盘缓存原图及target尺寸大小图片
     */
    @IntDef({DISK_CACHE_STRATEGY_NONE, DISK_CACHE_STRATEGY_DATA, DISK_CACHE_STRATEGY_RESOURCE, DISK_CACHE_STRATEGY_ALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface DiskCacheStrategy {
    }

    /**
     * 默认加载图
     */
    private final static int DEFAULT_PLACEHOLDER = android.R.color.white;
    /**
     * 默认加载失败图
     */
    private final static int DEFAULT_ERROR = android.R.color.white;

    //加载路径
    Object path;
    //默认加载图
    @DrawableRes
    @ColorRes
    int placeholderResId = DEFAULT_PLACEHOLDER;
    //错误默认图
    @DrawableRes
    @ColorRes
    int errorResId = DEFAULT_ERROR;
    //保存到内存
    boolean saveToMemoryCache = true;
    //保存到硬盘缓存
    @DiskCacheStrategy
    int diskCacheStrategy = DISK_CACHE_STRATEGY_ALL;
    //需要重置的图片宽度
    int width = 0;
    //需要重置的图片高度
    int height = 0;
    //是否居中裁切
    boolean centerCrop = false;
    //是否居中自适应
    boolean fitCenter = false;
    //是否居中不裁剪
    boolean centerInside = false;
    //显示图片不使用动画
    boolean dontAnimate = true;
    //渐变显示
    boolean crossFade = false;
    //动画资源id
    @AnimRes
    int animResId = -1;
    //动画
    ViewPropertyTransition.Animator animator;
    //图片加载监听器
    RequestListener requestListener;
    BitmapTransformation[] transformations;
}
