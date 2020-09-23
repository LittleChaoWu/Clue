package com.sfb.baselib.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.R;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.widget.pulltorefrsh.PtrRecycleView;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 列表Fragment的基类
 */
public abstract class BaseListFragment<V extends BaseView, P extends BasePresenter, E> extends BaseFragment<V, P> {

    protected LinearLayout mLayoutContent;
    protected PtrRecycleView mPtrRecycleView;
    protected BaseQuickAdapter<E, BaseViewHolder> mAdapter;

    protected boolean isViewInitiated = false;//视图是否初始化
    protected boolean isDataInitiated = false;//数据是否初始化

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            //懒加载
            lazyLoad();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        autoRefresh();
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        if (mPtrRecycleView != null && isDataInitiated) {
            mPtrRecycleView.refresh();
        }
    }

    /**
     * 懒加载时调用
     */
    private void lazyLoad() {
        isDataInitiated = true;
        if (mPtrRecycleView != null) {
            mPtrRecycleView.refresh();
        }
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_base_list, null);
    }

    @Override
    protected void onContentViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLayoutContent = view.findViewById(R.id.layout_content);
        mPtrRecycleView = view.findViewById(R.id.mPtrRecycleView);
        isViewInitiated = true;
        //视图创建后回调
        afterViewCreated(view);
        //加载数据
        if (getUserVisibleHint() && !isDataInitiated) {
            isDataInitiated = true;
            mPtrRecycleView.refresh();
        }
    }

    /**
     * 设置底部拓展View
     *
     * @param layoutResID int
     */
    protected void setExpandView(int layoutResID) {
        View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        mLayoutContent.addView(view);
    }

    /**
     * 视图创建后回调
     */
    public abstract void afterViewCreated(View view);

    /**
     * 加载数据额
     *
     * @param pageNo   int
     * @param pageSize int
     */
    public abstract void loadData(int pageNo, int pageSize);

    /**
     * 列表Item点击回调
     *
     * @param adapter  BaseQuickAdapter
     * @param position int
     */
    protected void onItemClick(BaseQuickAdapter adapter, int position) {

    }

    /**
     * 设置Adapter
     *
     * @param adapter BaseQuickAdapter
     */
    public void setAdapter(BaseQuickAdapter<E, BaseViewHolder> adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mPtrRecycleView.setAdapter(mAdapter);
            mPtrRecycleView.setRecycleListener(new PtrRecycleView.RecycleListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, int position) {
                    BaseListFragment.this.onItemClick(adapter, position);
                }

                @Override
                public void onRefresh(int pageNo, int pageSize) {
                    loadData(pageNo, pageSize);
                }

                @Override
                public void onLoadMore(int pageNo, int pageSize) {
                    loadData(pageNo, pageSize);
                }

                @Override
                public void onRefreshComplete(List list) {
                    mAdapter.setNewData(list);
                }

                @Override
                public void onLoadMoreComplete(List list) {
                    mAdapter.addData(list);
                }
            });
        }
    }

}
