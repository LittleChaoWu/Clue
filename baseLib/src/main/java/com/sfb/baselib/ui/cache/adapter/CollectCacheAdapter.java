package com.sfb.baselib.ui.cache.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.imageloader.ImageLoader;
import com.sfb.baselib.utils.DateUtils;
import com.sfb.baselib.R;
import com.sfb.baselib.components.event.CacheSelectEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectCacheAdapter extends BaseQuickAdapter<CollectRecordBean, BaseViewHolder> {

    private Context context;
    private boolean isSelect;

    public CollectCacheAdapter(Context context) {
        super(R.layout.layout_collect_cache_item);
        this.context = context;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).setSelect(isSelect);
        }
        notifyDataSetChanged();
    }

    public List<CollectRecordBean> getSelectList() {
        List<CollectRecordBean> list = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            CollectRecordBean bean = getData().get(i);
            if (bean.isSelect()) {
                list.add(bean);
            }
        }
        return list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CollectRecordBean item) {
        ImageView mIvImage = helper.getView(R.id.iv_image);
        ImageView mIvSelect = helper.getView(R.id.iv_select);
        helper.setText(R.id.tv_title, item.getSubject());
        helper.setText(R.id.tv_time, String.format(context.getString(R.string.time_format), DateUtils.formatTime(item.getSaveTime())));
        if (!TextUtils.isEmpty(item.getFilepaths())) {
            List<String> paths = Arrays.asList(item.getFilepaths().split(","));
            String path = paths.size() > 0 ? paths.get(0) : "";
            String lowerCasePath = path.toLowerCase();
            if (lowerCasePath.contains("wma") || lowerCasePath.contains("wmv") || lowerCasePath.contains("m4a") ||
                    lowerCasePath.contains("amr") || lowerCasePath.contains("mp3") || lowerCasePath.contains("aac")) {
                ImageLoader.get(context).load(R.drawable.ic_collect_record).into(mIvImage);
            } else {
                ImageLoader.get(context).load(path).into(mIvImage);
            }
        } else {
            ImageLoader.get(context).load(R.drawable.ic_collect_record).into(mIvImage);
        }
        if (isSelect) {
            mIvSelect.setVisibility(View.VISIBLE);
        } else {
            mIvSelect.setVisibility(View.GONE);
        }
        mIvSelect.setSelected(item.isSelect());
        mIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelect(!item.isSelect());
                notifyItemChanged(helper.getAdapterPosition());
                Bus.getInstance().post(new CacheSelectEvent(item.isSelect()));
            }
        });
    }
}
