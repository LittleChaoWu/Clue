package com.sfb.baselib.widget.gridimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sfb.baselib.R;
import com.sfb.baselib.data.ThumbInfo;
import com.sfb.baselib.widget.gridimage.adapter.GridImageAdapter;
import com.sfb.baselib.widget.pulltorefrsh.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 网格图片View
 */
public class GridImageView extends FrameLayout {

    public static final int MODE_ADD = 1;
    public static final int MODE_DETAIL = 2;

    private TextView mTvCatalog;
    private TextView mTvTitle;
    private RecyclerListView mRecyclerListView;

    private GridImageAdapter mAdapter;

    private List<ThumbInfo> list = new ArrayList<>();

    private String catalog;
    private String title;
    private int mode = MODE_DETAIL;

    public GridImageView(@NonNull Context context) {
        super(context);
        //初始化视图
        initView(context);
        //初始化数据
        initData();
    }

    public GridImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridImageView);
        catalog = typedArray.getString(R.styleable.GridImageView_catalog);
        title = typedArray.getString(R.styleable.GridImageView_title);
        mode = typedArray.getInt(R.styleable.GridImageView_mode, MODE_DETAIL);
        typedArray.recycle();
        //初始化视图
        initView(context);
        //初始化数据
        initData();
    }

    /**
     * 设置目录
     *
     * @param resId int
     */
    public void setCatalog(int resId) {
        mTvCatalog.setText(resId);
    }

    /**
     * 设置目录
     *
     * @param title String
     */
    public void setCatalog(String title) {
        mTvCatalog.setText(title);
        mTvCatalog.setVisibility(VISIBLE);
    }

    /**
     * 清除目录
     */
    public void removeCatalog() {
        mTvCatalog.setVisibility(GONE);
    }

    /**
     * 设置标题
     *
     * @param resId int
     */
    public void setTitle(int resId) {
        String title = getContext().getString(resId);
        setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param title String
     */
    public void setTitle(String title) {
        int index = title.indexOf("(");
        if (index < 0) {
            index = title.indexOf("（");
        }
        if (index > 0) {
            SpannableString sp = new SpannableString(title);
            //设置颜色
            sp.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.yellow)), index, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置字体大小，true表示前面的字体大小20单位为dip
            sp.setSpan(new AbsoluteSizeSpan(12, true), index, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvTitle.setText(sp);
            mTvTitle.setVisibility(VISIBLE);
        } else {
            mTvTitle.setText(title);
            mTvTitle.setVisibility(VISIBLE);
        }
    }

    /**
     * 清除标题
     */
    public void removeTitle() {
        mTvTitle.setVisibility(GONE);
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        inflate(context, R.layout.layout_grid_image, this);
        mTvCatalog = findViewById(R.id.tv_catalog);
        mTvTitle = findViewById(R.id.tv_title);
        mRecyclerListView = findViewById(R.id.mRecyclerListView);
        mAdapter = new GridImageAdapter(context, this);
        mAdapter.setCanDelete(mode == MODE_ADD);
        mAdapter.setNewData(list);
        mRecyclerListView.setListDivider(false);
        mRecyclerListView.setAdapter(mAdapter);
        if (!TextUtils.isEmpty(catalog)) {
            setCatalog(catalog);
        } else {
            removeCatalog();
        }
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        } else {
            removeTitle();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mode == MODE_ADD) {
            list.add(new ThumbInfo());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加数据
     *
     * @param info ThumbInfo
     */
    public void addData(ThumbInfo info) {
        if (mode == MODE_ADD) {
            list.add(list.size() - 1, info);
        } else {
            list.add(info);
        }
        mAdapter.notifyDataSetChanged();
        //重新计算高度，防止出现滚动的情况
        reMeasure();
    }

    /**
     * 添加数据
     *
     * @param list List<ThumbInfo>
     */
    public void addData(List<ThumbInfo> list) {
        if (mode == MODE_ADD) {
            this.list.addAll(this.list.size() - 1, list);
        } else {
            this.list.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
        //重新计算高度，防止出现滚动的情况
        reMeasure();
    }

    /**
     * 重新计算高度，防止出现滚动的情况
     */
    public void reMeasure() {
        if (getLayoutParams() != null) {
            measure(0, 0);
            getLayoutParams().height = getMeasuredHeight();
        }
    }

    /**
     * 清空数据
     */
    public void clearData() {
        list.clear();
        if (mode == MODE_ADD) {
            list.add(new ThumbInfo());
        }
        mAdapter.notifyDataSetChanged();
        reMeasure();
    }

    /**
     * 获取数量
     *
     * @return int
     */
    public int getCount() {
        return mAdapter.getItemCount();
    }

    /**
     * 获取添加的文件路径
     *
     * @return List<String>
     */
    public String getFilePaths() {
        StringBuilder buffer = new StringBuilder();
        int count = list.size();
        for (int i = 0; i < count; i++) {
            String path = list.get(i).getPath();
            if (!TextUtils.isEmpty(path)) {
                buffer.append(path).append(",");
            }
        }
        if (buffer.lastIndexOf(",") > 0) {
            buffer = buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString().trim();
    }

    public void setOnAddItemClickListener(ImageItemView.OnAddItemClickListener onAddItemClickListener) {
        mAdapter.setOnAddItemClickListener(onAddItemClickListener);
    }
}
