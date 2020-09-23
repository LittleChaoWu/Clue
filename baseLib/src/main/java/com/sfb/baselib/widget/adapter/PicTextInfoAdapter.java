package com.sfb.baselib.widget.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.R;
import com.sfb.baselib.data.PicTextInfo;

import java.util.List;

import androidx.annotation.Nullable;

public class PicTextInfoAdapter extends BaseQuickAdapter<PicTextInfo, BaseViewHolder> {

    public PicTextInfoAdapter(int layoutResId, @Nullable List<PicTextInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicTextInfo item) {
        helper.setText(R.id.text, item.getText());
        helper.setImageResource(R.id.image, item.getImgRes());
    }
}
