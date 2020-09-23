package com.sfb.baselib.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.sfb.baselib.R;
import com.sfb.baselib.ui.adapter.FGPagerAdapter;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.widget.SearchTypeView;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * 具有Viewpager的Activity的基类
 */
public abstract class BasePagerActivity<V extends BaseView, P extends BasePresenter> extends BaseActivity<V, P> {

    protected static final int BELOW_TOP = 0;
    protected static final int BELOW_TAB_LAYOUT = 1;
    protected static final int BELOW_SEARCH_TYPE = 2;

    protected LinearLayout mLayoutContent;
    protected TabLayout mTabLayout;
    protected TabLayout.Tab mTabOne;
    protected TabLayout.Tab mTabTwo;
    protected ViewPager mViewPager;
    protected LinearLayout mLayoutSearchType;
    protected SearchView mSearchView;
    protected SearchView.SearchAutoComplete mSearchText;

    protected FGPagerAdapter mAdapter;

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

    /**
     * 设置搜索框的hint
     *
     * @param hint String
     */
    protected void setSearchHint(String hint) {
        mSearchText.setHint(hint);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_pager);
        //初始化PagerView
        initPagerView();
        //初始化SearchType
        if (hasSearchType()) {
            initSearchType();
        }
        //视图创建后回调
        afterCreated();
        //加载数据
        loadData(mAdapter);
        //根据数据更新UI
        updateUIByData();
    }

    /**
     * 设置头部拓展View
     *
     * @param layoutResID int
     */
    protected void setExpandHeaderView(int layoutResID) {
        setExpandHeaderView(layoutResID, BELOW_TOP);
    }

    /**
     * 设置头部拓展View
     *
     * @param layoutResID int
     */
    protected void setExpandHeaderView(int layoutResID, int position) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mLayoutContent.addView(view, position);
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
     * 加载数据
     *
     * @param adapter FGPagerAdapter
     */
    protected abstract void loadData(FGPagerAdapter adapter);

    /**
     * 根据数据更新UI
     */
    private void updateUIByData() {
        if (mAdapter.getCount() < 2) {
            mTabLayout.setVisibility(View.GONE);
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化PagerView
     */
    private void initPagerView() {
        mLayoutContent = findViewById(R.id.layout_content);
        mTabLayout = findViewById(R.id.layout_tab);
        mViewPager = findViewById(R.id.mViewPager);
        //设置分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_divider)); //设置分割线的样式
        linearLayout.setDividerPadding(CommonUtils.dp2px(this, 10)); //设置分割线间隔

        mAdapter = new FGPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabOne = mTabLayout.newTab();
        mTabTwo = mTabLayout.newTab();
        mTabLayout.addTab(mTabOne);
        mTabLayout.addTab(mTabTwo);
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
    public SearchTypeView addSearchType(int position, String text, final SearchTypeClickListener listener) {
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
    public SearchTypeView addSearchType(int position, String text, int resId, final SearchTypeClickListener listener) {
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
