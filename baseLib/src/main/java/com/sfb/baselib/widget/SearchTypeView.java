package com.sfb.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchTypeView extends FrameLayout {

    private FrameLayout mLayoutSearchType;
    private TextView mTvSearchType;
    private View mLinear;

    private String mSearchText;
    private int mSearchIcon;
    private boolean showLinear;

    public SearchTypeView(@NonNull Context context, String mSearchText) {
        this(context, mSearchText, R.drawable.selector_up_down_arrow);
    }

    public SearchTypeView(@NonNull Context context, String mSearchText, int mSearchIcon) {
        super(context);
        this.mSearchText = mSearchText;
        this.mSearchIcon = mSearchIcon;
        //初始化视图
        initView(context);
    }

    public SearchTypeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchTypeView);
        this.mSearchText = typedArray.getString(R.styleable.SearchTypeView_search_type);
        this.mSearchIcon = typedArray.getResourceId(R.styleable.SearchTypeView_search_icon, R.drawable.selector_up_down_arrow);
        this.showLinear = typedArray.getBoolean(R.styleable.SearchTypeView_show_linear, false);
        typedArray.recycle();
        //初始化视图
        initView(context);
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        inflate(context, R.layout.layout_search_type, this);
        mLayoutSearchType = findViewById(R.id.layout_search_type);
        mTvSearchType = findViewById(R.id.tv_search_type);
        mLinear = findViewById(R.id.linear);
        setSearchText(mSearchText);
        setSearchIcon(mSearchIcon);
        showLinear(showLinear);
    }

    /**
     * 设置文本
     *
     * @param searchText String
     */
    public void setSearchText(String searchText) {
        if (mTvSearchType != null && searchText != null) {
            mTvSearchType.setText(searchText);
        }
    }

    /**
     * 设置CompoundDrawables
     *
     * @param mSearchIcon int
     */
    public void setSearchIcon(int mSearchIcon) {
        if (mTvSearchType != null && mSearchIcon != 0) {
            Drawable drawable = getResources().getDrawable(mSearchIcon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvSearchType.setCompoundDrawablePadding(CommonUtils.dp2px(getContext(), 5));
            mTvSearchType.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    /**
     * 设置间隔线显示
     *
     * @param isShow boolean
     */
    public void showLinear(boolean isShow) {
        if (isShow) {
            mLinear.setVisibility(VISIBLE);
        } else {
            mLinear.setVisibility(GONE);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (mTvSearchType != null) {
            mTvSearchType.setSelected(selected);
        }
    }
}
