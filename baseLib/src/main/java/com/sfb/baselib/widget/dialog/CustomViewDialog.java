package com.sfb.baselib.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

/**
 * Dialog like Material Design Dialog,loading style
 */
public class CustomViewDialog extends BaseAlertDialog<CustomViewDialog> {

    Context context;
    View contentView;

    public interface DismissListener {
        void onDismiss();
    }

    public CustomViewDialog(Context context) {
        super(context);

        this.context = context;
        /** default value*/
        titleTextColor = Color.parseColor("#DE000000");
        titleTextSize_SP = 22f;
        contentTextColor = Color.parseColor("#8a000000");
        contentTextSize_SP = 18f;
        leftBtnTextColor = Color.parseColor("#383838");
        rightBtnTextColor = Color.parseColor("#468ED0");
        middleBtnTextColor = Color.parseColor("#00796B");
        /** default value*/
    }

    @Override public void dismiss() {
        super.dismiss();
        if (mListener != null) {
            mListener.onDismiss();
        }
    }

    DismissListener mListener;

    public void setOnDismissListener(DismissListener listener) {
        this.mListener = listener;
    }


    @Override public View onCreateView() {

        /** title */
        tv_title.setGravity(Gravity.CENTER_VERTICAL);
        tv_title.getPaint().setFakeBoldText(true);
        tv_title.setPadding(dp2px(20), dp2px(20), dp2px(20), dp2px(0));
        tv_title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll_container.addView(tv_title);

        LinearLayout loadingLayout = new LinearLayout(context);
        loadingLayout.setOrientation(LinearLayout.VERTICAL);

        /** content */
        tv_content.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(0));
        tv_content.setTextSize(16);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        tv_content.setLayoutParams(params);
        loadingLayout.addView(tv_content);
        ll_container.addView(loadingLayout);

        contentView.setPadding(dp2px(0), dp2px(0), dp2px(0), dp2px(10));
        ll_container.addView(contentView);

        /**btns*/
        ll_btns.setGravity(Gravity.RIGHT);
        ll_btns.addView(tv_btn_left);
        ll_btns.addView(tv_btn_middle);
        ll_btns.addView(tv_btn_right);
        tv_btn_left.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        tv_btn_right.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        tv_btn_middle.setPadding(dp2px(15), dp2px(8), dp2px(15), dp2px(8));
        ll_btns.setPadding(dp2px(20), dp2px(0), dp2px(10), dp2px(10));
        ll_container.addView(ll_btns);

        return ll_container;
    }

    @Override public void setTitle(CharSequence customTitle) {
        super.setTitle(title);
        title = (String) customTitle;
    }

    public CustomViewDialog setCustomView(@NonNull View view) {
        contentView = view;
        return this;
    }

    @Override public void setUiBeforShow() {
        super.setUiBeforShow();
        /**set background color and corner radius */
        float radius = dp2px(cornerRadius_DP);
        ll_container.setBackgroundDrawable(CornerUtils.cornerDrawable(bgColor, radius));
        tv_btn_left.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -2));
        tv_btn_right.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -2));
        tv_btn_middle.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -2));
    }
}
