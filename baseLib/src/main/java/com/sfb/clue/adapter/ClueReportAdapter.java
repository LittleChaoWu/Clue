package com.sfb.clue.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.R;

public class ClueReportAdapter extends BaseQuickAdapter<ClueInfo, BaseViewHolder> {

    private Context context;

    public ClueReportAdapter(Context context) {
        super(R.layout.layout_clue_report_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClueInfo item) {
        String type = "";
        if (!TextUtils.isEmpty(item.getClueBroadTypeName())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(item.getClueBroadTypeName());
            if (!TextUtils.isEmpty(item.getClueTypeName())) {
                buffer.append("-").append(item.getClueTypeName());
            }
            type = buffer.toString();
        } else {
            type = context.getString(R.string.empty_type);
        }
        helper.setText(R.id.tv_type, String.format(context.getString(R.string.type_format), type));
        helper.setText(R.id.tv_time, String.format(context.getString(R.string.time_format), item.getCreateTimeStr()));
        //20200806需求修改：不需要给外部处理，先隐藏。
        helper.setVisible(R.id.tv_status, false);
        helper.setText(R.id.tv_status, item.getStatusInReport(context));
    }

}
