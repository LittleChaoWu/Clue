package com.sfb.baselib.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sfb.baselib.utils.DeviceUtils;
import com.sfb.baselib.widget.wheel.ProgressWheel;

import androidx.annotation.StringRes;

abstract class BaseAlertDialog<T extends BaseAlertDialog<T>> extends BaseDialog {
    /**
     * container
     */
    protected LinearLayout ll_container;
    //title
    /**
     * title
     */
    protected TextView tv_title;
    /**
     * title content(标题)
     */
    protected String title;
    protected int titleRes;
    /**
     * title textcolor(标题颜色)
     */
    protected int titleTextColor;
    /**
     * title textsize(标题字体大小,单位sp)
     */
    protected float titleTextSize_SP;
    /**
     * enable title show(是否显示标题)
     */
    protected boolean isTitleShow = true;

    //content
    /** content */
    /**
     * scrollview
     */
    protected ScrollView scrollView;
    protected TextView tv_content;
    /**
     * content text
     */
    protected String content;
    protected int contentRes;
    /**
     * show gravity of content(正文内容显示位置)
     */
    protected int contentGravity = Gravity.CENTER_VERTICAL;
    /**
     * content textcolor(正文字体颜色)
     */
    protected int contentTextColor;
    /**
     * content textsize(正文字体大小)
     */
    protected float contentTextSize_SP;
    /**
     * enable title show(是否显示正文)
     */
    protected boolean isContentShow = true;
    protected boolean needScroll = true;
    protected boolean isToastShow;

    //ProgressBar
    protected TextView progressText;//进度文字
    protected ProgressBar progressBar;
    protected ProgressWheel progressWheel;

    //btns
    /**
     * btn container
     */
    protected LinearLayout ll_btns;
    /**
     * btns
     */
    protected TextView tv_btn_left;
    protected TextView tv_btn_right;
    protected TextView tv_btn_middle;
    /**
     * btn text(按钮内容)
     */
    protected int btnLeftId = -1;
    protected int btnRightId = -1;
    protected int btnMiddleId = -1;
    /**
     * btn text(按钮内容)
     */
    protected String btnLeftText;
    protected String btnRightText;
    protected String btnMiddleText;
    /**
     * btn textcolor(按钮字体颜色)
     */
    protected int leftBtnTextColor;
    protected int rightBtnTextColor;
    protected int middleBtnTextColor;
    /**
     * btn textsize(按钮字体大小)
     */
    private float leftBtnTextSize_SP = 15f;
    private float rightBtnTextSize_SP = 15f;
    private float middleBtnTextSize_SP = 15f;
    /**
     * btn press color(按钮点击颜色)
     */
    protected int btnPressColor = Color.parseColor("#E3E3E3");// #85D3EF,#ffcccccc,#E3E3E3
    /**
     * left btn click listener(左按钮接口)
     */
    protected View.OnClickListener onBtnLeftClickL;
    /**
     * right btn click listener(右按钮接口)
     */
    protected View.OnClickListener onBtnRightClickL;
    /**
     * middle btn click listener(右按钮接口)
     */
    protected View.OnClickListener onBtnMiddleClickL;

    /**
     * corner radius,dp(圆角程度,单位dp)
     */
    protected float cornerRadius_DP = 3;
    /**
     * background color(背景颜色)
     */
    protected int bgColor = Color.parseColor("#ffffff");

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public BaseAlertDialog(Context context) {
        super(context);
        widthScale(0.88f);

        ll_container = new LinearLayout(context);
        ll_container.setOrientation(LinearLayout.VERTICAL);

        /** title */
        tv_title = new TextView(context);

        /** content */
        tv_content = new TextView(context);
        scrollView = new ScrollView(context);

        /** progress bar */

        progressBar = new ProgressBar(context);
        progressText = new TextView(context);

        /**btns*/
        ll_btns = new LinearLayout(context);
        ll_btns.setOrientation(LinearLayout.HORIZONTAL);

        tv_btn_left = new TextView(context);
        tv_btn_left.setGravity(Gravity.CENTER);

        tv_btn_middle = new TextView(context);
        tv_btn_middle.setGravity(Gravity.CENTER);

        tv_btn_right = new TextView(context);
        tv_btn_right.setGravity(Gravity.CENTER);
    }


    @Override public void setUiBeforShow() {
        /** title */
        tv_title.setVisibility(isTitleShow ? View.VISIBLE : View.GONE);

        if (titleRes != 0) {
            tv_title.setText(titleRes);
        } else if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        } else {
            tv_title.setText("温馨提示");
        }
        tv_title.setTextColor(titleTextColor);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize_SP);

        /** content */
        tv_content.setVisibility(isContentShow ? View.VISIBLE : View.GONE);
        tv_content.setGravity(contentGravity);
        if (contentRes != 0) {
            tv_content.setText(contentRes);
        } else if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }
        tv_content.setTextColor(contentTextColor);
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTextSize_SP);
        tv_content.setLineSpacing(0, 1.3f);

        scrollView.setVisibility(needScroll ? View.VISIBLE : View.GONE);
        scrollView.setVerticalScrollBarEnabled(false);

        /**btns*/
        if (btnLeftId != -1) {
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText(btnLeftId);
        }
        if (btnRightId != -1) {
            tv_btn_right.setVisibility(View.VISIBLE);
            tv_btn_right.setText(btnRightId);
        }
        if (btnMiddleId != -1) {
            tv_btn_middle.setVisibility(View.VISIBLE);
            tv_btn_middle.setText(btnMiddleId);
        }

        if (!TextUtils.isEmpty(btnLeftText)) {
            tv_btn_left.setVisibility(View.VISIBLE);
            tv_btn_left.setText(btnLeftText);
        }
        if (!TextUtils.isEmpty(btnRightText)) {
            tv_btn_right.setVisibility(View.VISIBLE);
            tv_btn_right.setText(btnRightText);
        }
        if (!TextUtils.isEmpty(btnMiddleText)) {
            tv_btn_middle.setVisibility(View.VISIBLE);
            tv_btn_middle.setText(btnMiddleText);
        }

        tv_btn_left.setTextColor(leftBtnTextColor);
        tv_btn_right.setTextColor(rightBtnTextColor);
        tv_btn_middle.setTextColor(middleBtnTextColor);

        tv_btn_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftBtnTextSize_SP);
        tv_btn_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightBtnTextSize_SP);
        tv_btn_middle.setTextSize(TypedValue.COMPLEX_UNIT_SP, middleBtnTextSize_SP);

        if (TextUtils.isEmpty(btnLeftText) && btnLeftId == -1) {
            tv_btn_left.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(btnRightText) && btnRightId == -1) {
            tv_btn_right.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(btnMiddleText) && btnMiddleId == -1) {
            tv_btn_middle.setVisibility(View.GONE);
        }

        tv_btn_left.setOnClickListener(onBtnLeftClickL == null ? new View.OnClickListener() {
            @Override public void onClick(View v) {
                dismiss();
            }
        } : onBtnLeftClickL);
        tv_btn_right.setOnClickListener(onBtnRightClickL == null ? new View.OnClickListener() {
            @Override public void onClick(View v) {
                dismiss();
            }
        } : onBtnRightClickL);
        tv_btn_middle.setOnClickListener(onBtnMiddleClickL == null ? new View.OnClickListener() {
            @Override public void onClick(View v) {
                dismiss();
            }
        } : onBtnMiddleClickL);

        if (DeviceUtils.isLollipop()) {
            tv_btn_left.setElevation(0);
            tv_btn_middle.setElevation(0);
            tv_btn_right.setElevation(0);
        }
    }

    /**
     * set title text(设置标题内容) @return MaterialDialog
     */
    public T title(@StringRes int titleRes) {
        this.titleRes = titleRes;
        return (T) this;
    }

    /**
     * set title text(设置标题内容) @return MaterialDialog
     */
    public T title(String title) {
        this.title = title;
        return (T) this;
    }

    /**
     * set title textcolor(设置标题字体颜色)
     */
    public T titleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return (T) this;
    }

    /**
     * set title textsize(设置标题字体大小)
     */
    public T titleTextSize(float titleTextSize_SP) {
        this.titleTextSize_SP = titleTextSize_SP;
        return (T) this;
    }

    /**
     * enable title show(设置标题是否显示)
     */
    public T isTitleShow(boolean isTitleShow) {
        this.isTitleShow = isTitleShow;
        return (T) this;
    }

    /**
     * enable content show(设置标题是否显示)
     */
    public T isContentShow(boolean isContentShow) {
        this.isContentShow = isContentShow;
        return (T) this;
    }

    /**
     * enable content show(设置提示view是否显示)
     */
    public T isToastShow(boolean isToastShow) {
        this.isToastShow = isToastShow;
        return (T) this;
    }

    /**
     * set content text(设置正文内容)
     */
    public T content(@StringRes int contentRes) {
        this.contentRes = contentRes;
        return (T) this;
    }

    /**
     * set content text(设置正文内容)
     */
    public T content(String content) {
        this.content = content;
        return (T) this;
    }

    /**
     * set content gravity(设置正文内容,显示位置)
     */
    public T contentGravity(int contentGravity) {
        this.contentGravity = contentGravity;
        return (T) this;
    }

    /**
     * set content textcolor(设置正文字体颜色)
     */
    public T contentTextColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
        return (T) this;
    }

    /**
     * set content textsize(设置正文字体大小,单位sp)
     */
    public T contentTextSize(float contentTextSize_SP) {
        this.contentTextSize_SP = contentTextSize_SP;
        return (T) this;
    }

    /**
     * set btn press color(设置按钮点击颜色)
     */
    public T btnPressColor(int btnPressColor) {
        this.btnPressColor = btnPressColor;
        return (T) this;
    }

    /**
     * set corner radius (设置圆角程度)
     */
    public T cornerRadius(float cornerRadius_DP) {
        this.cornerRadius_DP = cornerRadius_DP;
        return (T) this;
    }

    /**
     * set backgroud color(设置背景色)
     */
    public T bgColor(int bgColor) {
        this.bgColor = bgColor;
        return (T) this;
    }

    public T setRightButton(int resId, final View.OnClickListener listener) {
        this.btnRightId = resId;
        this.onBtnRightClickL = listener;
        return (T) this;
    }

    public T setRightButton(String text, final View.OnClickListener listener) {
        this.btnRightText = text;
        this.onBtnRightClickL = listener;
        return (T) this;
    }

    public T setMiddleButton(int resId, final View.OnClickListener listener) {
        this.btnMiddleId = resId;
        this.onBtnMiddleClickL = listener;
        return (T) this;
    }

    public T setMiddleButton(String text, final View.OnClickListener listener) {
        this.btnMiddleText = text;
        this.onBtnMiddleClickL = listener;
        return (T) this;
    }

    public T setLeftButton(int resId, final View.OnClickListener listener) {
        this.btnLeftId = resId;
        this.onBtnLeftClickL = listener;
        return (T) this;
    }

    public T setLeftButton(String text, final View.OnClickListener listener) {
        this.btnLeftText = text;
        this.onBtnLeftClickL = listener;
        return (T) this;
    }

    @Override public T showDialog() {
        return (T) super.showDialog();
    }
}
