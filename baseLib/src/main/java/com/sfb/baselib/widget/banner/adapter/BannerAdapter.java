package com.sfb.baselib.widget.banner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.sfb.baselib.data.base.BaseBean;
import com.sfb.baselib.widget.banner.holder.IViewHolder;
import com.sfb.baselib.widget.banner.listener.OnBannerListener;
import com.sfb.baselib.widget.banner.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BannerAdapter<T extends BaseBean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IViewHolder<T, VH> {
    protected List<T> mDatas = new ArrayList<>();
    private OnBannerListener mOnBannerListener;
    private VH mViewHolder;
    private int increaseCount = 2;
    private Context mContext;

    public BannerAdapter(Context context, List<T> datas) {
        this.mContext = context;
        setDatas(datas);
    }

    public Context getContext() {
        return mContext;
    }

    public void setDatas(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        mViewHolder = holder;
        final int real = getRealPosition(position);
        onBindView(holder, mDatas.get(real), real, getRealCount());
        if (mOnBannerListener != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBannerListener.OnBannerClick(mDatas.get(real), real);
                }
            });
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return getRealCount() > 1 ? getRealCount() + increaseCount : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getRealPosition(int position) {
        return BannerUtils.getRealPosition(increaseCount == 2, position, getRealCount());
    }

    public void setOnBannerListener(OnBannerListener listener) {
        this.mOnBannerListener = listener;
    }

    public VH getViewHolder() {
        return mViewHolder;
    }

    public void setIncreaseCount(int increaseCount) {
        this.increaseCount = increaseCount;
    }
}