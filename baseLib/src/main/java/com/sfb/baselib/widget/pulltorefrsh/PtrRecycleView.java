package com.sfb.baselib.widget.pulltorefrsh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.NetworkUtils;
import com.sfb.baselib.utils.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrRecycleView extends FrameLayout {

    private static final long INTERVAL_TIME = 500;

    private CustomPtrFrameLayout mPtrFrameLayout;
    private RecyclerListView mRecyclerView;
    private EmptyView mEmptyView;

    private Context context;
    private int mPageNo = 1;
    private int mPageSize = 20;
    private boolean isRefreshAble = true;
    private boolean isLoadMoreAble = true;
    private long lastClickTime;//最后点击时间

    private BaseQuickAdapter mAdapter;

    public PtrRecycleView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PtrRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_prt_recycleview, this);
        mPtrFrameLayout = findViewById(R.id.mPtrFrameLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mEmptyView = new EmptyView(getContext());
        mEmptyView.setRefreshCallback(new EmptyView.RefreshCallback() {
            @Override
            public void refresh() {
                PtrRecycleView.this.refresh();
            }
        });
    }

    /**
     * 设置适配器
     *
     * @param adapter BaseQuickAdapter
     */
    public void setAdapter(BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
        //配饰适配器
        setupAdapter();
    }

    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 配饰适配器
     */
    private void setupAdapter() {
        if (mAdapter == null) {
            return;
        }
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime > INTERVAL_TIME) {
                    if (recycleListener != null) {
                        recycleListener.onItemClick(adapter, position);
                    }
                }
                lastClickTime = currentTime;
            }
        });
        if (isLoadMoreAble) {
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    //检查网络
                    if (checkNetworkAvailable()) {
                        //本地列表且页数大于一页，加载更多
                        if (mAdapter.getData().size() >= mPageSize) {
                            mPageNo++;
                            //加载数据
                            if (recycleListener != null) {
                                recycleListener.onLoadMore(mPageNo, mPageSize);
                            }
                        } else {
                            mAdapter.loadMoreComplete();
                            mAdapter.loadMoreEnd(true);
                        }
                    }
                }
            }, mRecyclerView);
        }
        if (isRefreshAble) {
            mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    //检查网络
                    if (checkNetworkAvailable()) {
                        //加载数据
                        if (recycleListener != null) {
                            mPageNo = 1;
                            recycleListener.onRefresh(mPageNo, mPageSize);
                        }
                    }
                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        //检查网络
        if (checkNetworkAvailable()) {
            //加载数据
            if (recycleListener != null) {
                mPageNo = 1;
                recycleListener.onRefresh(mPageNo, mPageSize);
            }
        }
    }

    /**
     * 加载完成
     */
    public void loadComplete(List list) {
        if (mPageNo == 1) {
            mPtrFrameLayout.refreshComplete();
            //刷新数据完成
            if (recycleListener != null) {
                recycleListener.onRefreshComplete(list);
            }
            if (list == null || list.size() == 0) {
                mEmptyView.showEmpty();
            }
        } else {
            mAdapter.loadMoreComplete();
            //加载更多完成
            if (recycleListener != null && list != null) {
                recycleListener.onLoadMoreComplete(list);
                //本地列表且加载不到数据，就当作全部加载完成了
                if (list.size() == 0) {
                    mAdapter.loadMoreEnd(true);
                }
            } else {
                mAdapter.loadMoreEnd(true);
            }
        }
    }

    /**
     * 设置不能下拉刷新
     */
    public void setNoRefresh() {
        this.isRefreshAble = false;
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.setPtrHandler(null);
        }
    }

    public void setListScroll(boolean ifScroll) {
        if (null != mRecyclerView) {
            mRecyclerView.setNestedScrollingEnabled(ifScroll);
        }
    }

    /**
     * 设置不能上拉加载更多
     */
    public void setNoLoadMore() {
        this.isLoadMoreAble = false;
        if (mAdapter != null) {
            mAdapter.setOnLoadMoreListener(null, mRecyclerView);
        }
    }

    /**
     * 检查网络
     */
    public boolean checkNetworkAvailable() {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            ToastUtils.showShort(context, R.string.network_error);
            mEmptyView.showNetworkError();
            if (mPtrFrameLayout.isRefreshing()) {
                mPtrFrameLayout.refreshComplete();
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取RecycleView
     *
     * @return RecyclerListView
     */
    public RecyclerListView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 获取PagerSize
     *
     * @return int
     */
    public int getPageSize() {
        return mPageSize;
    }

    /**
     * 获取PageNo
     *
     * @return int
     */
    public int getPageNo() {
        return mPageNo;
    }

    /**
     * 滚动到某一项
     *
     * @param position int
     */
    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    /**
     * 设置EmptyView高度
     *
     * @param height int
     */
    public void setEmptyViewHeight(int height) {
        if (mEmptyView != null) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dp2px(context, height));
            mEmptyView.setLayoutParams(params);
        }
    }

    public EmptyView getEmptyView() {
        return mEmptyView;
    }

    private RecycleListener recycleListener;

    public void setRecycleListener(RecycleListener recycleListener) {
        this.recycleListener = recycleListener;
    }

    public interface RecycleListener {

        /**
         * Item点击
         *
         * @param adapter  适配器
         * @param position 第几项
         */
        void onItemClick(BaseQuickAdapter adapter, int position);

        /**
         * 下拉刷新
         */
        void onRefresh(int pageNo, int pageSize);

        /**
         * 上拉加载
         */
        void onLoadMore(int pageNo, int pageSize);

        /**
         * 下拉刷新完成
         */
        void onRefreshComplete(List list);

        /**
         * 上拉加载完成
         */
        void onLoadMoreComplete(List list);
    }

}
