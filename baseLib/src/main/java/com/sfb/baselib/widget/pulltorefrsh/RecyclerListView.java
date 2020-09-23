package com.sfb.baselib.widget.pulltorefrsh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.sfb.baselib.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerListView extends RecyclerView {

    /**
     * 线性布局管理器标识
     */
    public static final int LAYOUT_MANAGER_LINEAR = 1;
    /**
     * 网格布局管理器标识
     */
    public static final int LAYOUT_MANAGER_GRID = 2;

    private Context mContext;
    private int mSpanCount;
    private int mLayoutManagerType = LAYOUT_MANAGER_LINEAR;
    private boolean haveDivider;
    private SimpleDividerItemDecoration itemDecoration;

    public RecyclerListView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public RecyclerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    public RecyclerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(context, attrs);
    }

    /**
     * 控件初始化（初始化属性及视图）
     *
     * @param context 上下文
     * @param attrs   属性集合
     */
    private void init(Context context, AttributeSet attrs) {
        initAttrs(attrs);
        initView(context);
    }

    /**
     * 初始化属性
     *
     * @param attrs 属性集合
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RecyclerListView);
        mLayoutManagerType = typedArray.getInt(R.styleable.RecyclerListView_layout_manager, LAYOUT_MANAGER_LINEAR);
        mSpanCount = typedArray.getInt(R.styleable.RecyclerListView_span_count, 1);
        haveDivider = typedArray.getBoolean(R.styleable.RecyclerListView_has_divider, true);
        typedArray.recycle();
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        this.mContext = context;
        itemDecoration = new SimpleDividerItemDecoration(context);
        //设置滑动模式
        this.setOverScrollMode(RecyclerListView.OVER_SCROLL_NEVER);
        //设置列表间隔线
        setListDivider(haveDivider);
        //设置网格布局每行显示数量
        setSpanCount(mSpanCount);
        //设置布局类型
        setLayoutManagerType(mLayoutManagerType);
    }

    /**
     * 设置列表间隔线
     *
     * @param haveDivider boolean
     */
    public void setListDivider(boolean haveDivider) {
        if (haveDivider) {
            this.addItemDecoration(itemDecoration);
        } else {
            this.removeItemDecoration(itemDecoration);
        }
    }

    /**
     * 设置网格布局每行显示数量
     *
     * @param spanCount 网格布局每行显示数量
     */
    public void setSpanCount(int spanCount) {
        mSpanCount = spanCount;
    }

    /**
     * 设置布局类型
     *
     * @param layoutManagerType 布局类型
     *                          {@link #LAYOUT_MANAGER_GRID} 网格布局
     *                          {@link #LAYOUT_MANAGER_LINEAR} 线性布局
     */
    private void setLayoutManagerType(int layoutManagerType) {
        mLayoutManagerType = layoutManagerType;
        if (layoutManagerType == LAYOUT_MANAGER_GRID) {
            this.setLayoutManager(new GridLayoutManager(mContext, mSpanCount));
        } else {
            this.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }
}
