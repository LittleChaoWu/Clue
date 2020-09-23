package com.sfb.baselib.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sfb.baselib.R;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.widget.SearchTypeView;
import com.sfb.baselib.widget.pulltorefrsh.PtrRecycleView;

import java.util.List;

import androidx.appcompat.widget.SearchView;

/**
 * 列表Activity的基类
 */
public abstract class BaseListActivity<V extends BaseView, P extends BasePresenter, E> extends BaseActivity<V, P> {

    protected LinearLayout mLayoutSearchType;
    protected SearchView mSearchView;
    protected SearchView.SearchAutoComplete mSearchText;
    protected LinearLayout mLayoutContent;
    protected PtrRecycleView mPtrRecycleView;

    protected BaseQuickAdapter<E, BaseViewHolder> mAdapter;

    private boolean isFirst = true;

    /**
     * 是否有搜索框
     *
     * @return boolean
     */
    protected boolean hasSearch() {
        return false;
    }

    /**
     * 是否有搜索类型
     *
     * @return boolean
     */
    protected boolean hasSearchType() {
        return false;
    }

    /**
     * 返回 搜索框的hint
     *
     * @return String
     */
    protected String searchHint() {
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_list);
        mLayoutContent = findViewById(R.id.layout_content);
        mPtrRecycleView = findViewById(R.id.mPtrRecycleView);
        //初始化SearchType
        if (hasSearchType()) {
            initSearchType();
        }
        //视图创建后回调
        afterCreated();
        //加载数据
        mPtrRecycleView.refresh();
    }

    /**
     * 设置顶部拓展View
     *
     * @param layoutResID int
     */
    protected void setHeaderExpandView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mLayoutContent.addView(view, 0);
    }

    /**
     * 设置底部拓展View
     *
     * @param layoutResID int
     */
    protected void setExpandView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mLayoutContent.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (hasSearch()) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            initSearchView(menu.findItem(R.id.menu_search));
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 视图创建后回调
     */
    public abstract void afterCreated();

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

    @Override
    protected void onResume() {
        super.onResume();
        autoRefresh();
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        if (!isFirst) {
            mPtrRecycleView.refresh();
        } else {
            isFirst = false;
        }
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
                    BaseListActivity.this.onItemClick(adapter, position);
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

    /**
     * 初始化Search
     *
     * @param item MenuItem
     */
    private void initSearchView(final MenuItem item) {
        //通过 MenuItem 获取 actionview
        mSearchView = (SearchView) item.getActionView();
        //改变默认的搜索图标
        ((ImageView) mSearchView.findViewById(R.id.search_button)).setImageResource(R.drawable.ic_search_white);
        ((ImageView) mSearchView.findViewById(R.id.search_close_btn)).setImageResource(R.drawable.ic_close);
        //根据id-search_src_text获取TextView
        mSearchText = mSearchView.findViewById(R.id.search_src_text);
        //改变背景
        mSearchText.setBackgroundResource(R.drawable.shape_white_round);
        //改变输入文字的颜色
        mSearchText.setTextColor(getResources().getColor(R.color.text_color));
        //改变输入文字的大小
        mSearchText.setTextSize(14);
        //设置搜索hint
        mSearchText.setHint(searchHint());
        //搜索监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                //在输入法按下搜索或者回车时，会调用次方法
                onSearch(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //此处只有在文本为空的时候调用，为了弥补onQueryTextSubmit()的keyword为空不调用的问题
                if (TextUtils.isEmpty(newText)) {
                    onSearch(newText);
                }
                return false;
            }
        });
        //searchview 的开启监听
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTitle();
            }
        });
        //searchview 的关闭监听
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showTitle();
                if (!TextUtils.isEmpty(mSearchText.getText().toString())) {
                    onSearch("");
                }
                return false;
            }
        });
    }

    /**
     * 搜索
     *
     * @param keyword String
     */
    protected void onSearch(String keyword) {
    }

    /**
     * 初始化SearchType
     */
    private void initSearchType() {
        mLayoutSearchType = findViewById(R.id.layout_search_type);
        mLayoutSearchType.setVisibility(View.VISIBLE);
    }

    /**
     * 删除SearchType
     */
    public void removeAddSearchType() {
        mLayoutSearchType.removeAllViews();
    }

    /**
     * 添加SearchType
     *
     * @param text String
     */
    public SearchTypeView addSearchType(int position, String text, final BasePagerActivity.SearchTypeClickListener listener) {
        if (hasSearchType()) {
            SearchTypeView searchTypeView = new SearchTypeView(this, text);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, CommonUtils.dp2px(this, 46));
            params.weight = 1;
            searchTypeView.setLayoutParams(params);
            searchTypeView.showLinear(position != 0);
            searchTypeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideAllSearchType();
                    v.setSelected(!v.isSelected());
                    if (listener != null) {
                        listener.onClick((SearchTypeView) v);
                    }
                }
            });
            mLayoutSearchType.addView(searchTypeView);
            return searchTypeView;
        } else {
            return null;
        }
    }

    /**
     * 添加SearchType
     *
     * @param text  String
     * @param resId int
     */
    public SearchTypeView addSearchType(int position, String text, int resId, final BasePagerActivity.SearchTypeClickListener listener) {
        if (hasSearchType()) {
            SearchTypeView searchTypeView = new SearchTypeView(this, text, resId);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, CommonUtils.dp2px(this, 46));
            params.weight = 1;
            searchTypeView.setLayoutParams(params);
            searchTypeView.showLinear(position != 0);
            searchTypeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideAllSearchType();
                    v.setSelected(!v.isSelected());
                    if (listener != null) {
                        listener.onClick((SearchTypeView) v);
                    }
                }
            });
            mLayoutSearchType.addView(searchTypeView);
            return searchTypeView;
        } else {
            return null;
        }
    }

    /**
     * 设置所有SearchType没有被选中
     */
    public void hideAllSearchType() {
        if (mLayoutSearchType.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < mLayoutSearchType.getChildCount(); i++) {
                mLayoutSearchType.getChildAt(i).setSelected(false);
            }
        }
    }

    /**
     * SearchType 点击回调
     */
    public interface SearchTypeClickListener {
        void onClick(SearchTypeView view);
    }

}
