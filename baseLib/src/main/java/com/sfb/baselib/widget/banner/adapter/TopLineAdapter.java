package com.sfb.baselib.widget.banner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sfb.baselib.R;
import com.sfb.baselib.data.NoticeInfo;
import com.sfb.baselib.widget.banner.utils.BannerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义布局，实现类似1号店、淘宝头条的滚动效果
 */
public class TopLineAdapter extends BannerAdapter<NoticeInfo, TopLineAdapter.TopLineHolder> {

    public TopLineAdapter(Context context, List<NoticeInfo> datas) {
        super(context, datas);
    }

    @Override
    public TopLineHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new TopLineHolder(BannerUtils.getView(parent, R.layout.item_hot_notice));
    }

    @Override
    public void onBindView(TopLineHolder holder, NoticeInfo data, int position, int size) {
        holder.message.setText(data.getTitle());
    }

    static class TopLineHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView source;

        public TopLineHolder(@NonNull View view) {
            super(view);
            message = view.findViewById(R.id.item_hot_notice_content);
        }
    }
}
