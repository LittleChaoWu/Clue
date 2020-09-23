package com.sfb.baselib.widget.banner.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pref.GuardPreference_;
import com.sfb.baselib.R;
import com.sfb.baselib.data.NoticeInfo;
import com.sfb.baselib.imageloader.glide.GlideImageLoader;
import com.sfb.baselib.network.NetworkConfig;
import com.sfb.baselib.widget.banner.holder.ImageHolder;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class ImageAdapter extends BannerAdapter<NoticeInfo, ImageHolder> {
    private GuardPreference_ mPreference;
    private String baseUrl = NetworkConfig.getH5RootUrl();

    public ImageAdapter(Context context, List<NoticeInfo> datas) {
        super(context, datas);
        mPreference = GuardPreference_.getInstance(context);
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, NoticeInfo data, int position, int size) {
//        holder.imageView.setImageResource(data.imageRes);
        int fileId = data.getFileModelPic() == null ? -1 : data.getFileModelPic().getFileId();
        String imgUrl = baseUrl + "/file/load?fileId=" + fileId + "&token=" + mPreference.getToken();
        GlideImageLoader.with(getContext()).load(imgUrl).error(R.drawable.ic_banner_bk_error).into(holder.imageView);
    }

}
