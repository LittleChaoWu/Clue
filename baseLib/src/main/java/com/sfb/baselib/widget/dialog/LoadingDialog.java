package com.sfb.baselib.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sfb.baselib.R;
import com.sfb.baselib.widget.wheel.ProgressWheel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Dialog like Material Design Dialog,loading style
 */
public class LoadingDialog extends BaseAlertDialog<LoadingDialog> {

    public static final int PROGRESS_BAR_STYLE_HORIZONTAL = 1;
    public static final int PROGRESS_BAR_STYLE_ENDLESS = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PROGRESS_BAR_STYLE_HORIZONTAL, PROGRESS_BAR_STYLE_ENDLESS})
    public @interface PROGRESS_BAR_STYLE{}

    private @PROGRESS_BAR_STYLE int progressBarStyle;

    Context context;
    int progress = 0;//进度,100为MAX

    public interface ProgressListener {
        void onProgress(int progress);
    }

    public interface DismissListener {
        void onDismiss();
    }

    public LoadingDialog(Context context, @PROGRESS_BAR_STYLE int progressBarStyle) {
        super(context);

        this.context = context;
        this.progressBarStyle = progressBarStyle;
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
        if (progressWheel != null) {
            if (progressWheel.isSpinning()) {
                progressWheel.stopSpinning();
            }
        }
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
        if (this.progressBarStyle == PROGRESS_BAR_STYLE_HORIZONTAL) {
            loadingLayout.setOrientation(LinearLayout.VERTICAL);

            /** content */
            tv_content.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(0));
            tv_content.setTextSize(16);
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            tv_content.setLayoutParams(params);
            loadingLayout.addView(tv_content);

            progressText.setText(progress + "/100");
            progressText.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(0));
            loadingLayout.addView(progressText);
            progressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.custom_progressbar_progress, null);
            progressBar.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
            loadingLayout.addView(progressBar);
            ll_container.addView(loadingLayout);

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

        } else {
            loadingLayout.setOrientation(LinearLayout.HORIZONTAL);
            ProgressWheel progressWheel = (ProgressWheel) LayoutInflater.from(context).inflate(R.layout.wheel_progress_style, null);
            progressWheel.setProgress(1.0f);
            progressWheel.setRimColor(context.getResources().getColor(R.color.progress_wheel_color));
            setCustomProgressBar(progressWheel);
            progressWheel.setPadding(dp2px(20), dp2px(20), dp2px(0), dp2px(20));
            loadingLayout.addView(progressWheel);

            /** content */
            tv_content.setPadding(dp2px(20), dp2px(30), dp2px(20), dp2px(20));
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            tv_content.setLayoutParams(params);

            loadingLayout.addView(tv_content);
            loadingLayout.setGravity(Gravity.CENTER);
            ll_container.addView(loadingLayout);
        }
        return ll_container;
    }

    @Override public void setTitle(CharSequence customTitle) {
        super.setTitle(title);
        title = (String) customTitle;
    }

    public void setProgressBar(ProgressBar bar) {
        progressBar = bar;
    }

    public void setCustomProgressBar(ProgressWheel bar) {
        progressWheel = bar;
        progressWheel.spin();
    }

    public void setProgress(int progress) {
        if (this.progressBarStyle != PROGRESS_BAR_STYLE_HORIZONTAL) {
            return;
        }
        this.progress = progress;
        updateProgress(progress);
    }

    private void updateProgress(int progress) {
        progressText.setText(progress + "/100");
        progressBar.setProgress(progress);
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
