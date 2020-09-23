package com.sfb.baselib.widget.gridimage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.R;
import com.sfb.baselib.data.ThumbInfo;
import com.sfb.baselib.widget.gridimage.GridImageView;
import com.sfb.baselib.widget.gridimage.ImageItemView;
import com.sfb.baselib.widget.gridimage.ImageItemView.ImageDeleteCallback;
import com.sfb.baselib.widget.gridimage.ImageItemView.OnAddItemClickListener;

public class GridImageAdapter extends BaseQuickAdapter<ThumbInfo, BaseViewHolder> {

    private Context context;
    private GridImageView gridImageView;

    private boolean isCanDelete;//是否可删除

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    private OnAddItemClickListener onAddItemClickListener;

    public void setOnAddItemClickListener(OnAddItemClickListener onAddItemClickListener) {
        this.onAddItemClickListener = onAddItemClickListener;
    }

    public GridImageAdapter(Context context, GridImageView gridImageView) {
        super(R.layout.layout_grid_image_item);
        this.context = context;
        this.gridImageView = gridImageView;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ThumbInfo item) {
        TextView mTvTitle = helper.getView(R.id.tv_title);
        int titleVisible = TextUtils.isEmpty(item.getTitle()) ? View.GONE : View.VISIBLE;
        mTvTitle.setVisibility(titleVisible);
        mTvTitle.setText(item.getTitle());

        ImageItemView mImageItemView = helper.getView(R.id.mImageItemView);
        if (mImageItemView != null) {
            mImageItemView.setOnAddItemClickListener(onAddItemClickListener);
            if (isCanDelete) {
                mImageItemView.setCanDelete(true);
                mImageItemView.setImageDeleteCallback(new ImageDeleteCallback() {
                    @Override
                    public void onDeleteCallback() {
                        GridImageAdapter.this.remove(helper.getAdapterPosition());
                        gridImageView.reMeasure();
                    }
                });
            }
            mImageItemView.load(item.getFileModel(), item.getPath());
        }
    }

}
