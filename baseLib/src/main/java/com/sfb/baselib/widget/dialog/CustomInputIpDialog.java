package com.sfb.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sfb.baselib.R;

import androidx.annotation.NonNull;

public class CustomInputIpDialog extends Dialog {

    protected TextView mTvTitle;
    protected TextView mEdContent;
    protected TextView mTvNegative;
    protected TextView mTvPositive;

    public CustomInputIpDialog(@NonNull Context context) {
        super(context, R.style.PromptDialog);
        setCancelable(false);
        setContentView(R.layout.layout_custom_input_ip_dialog);
        mTvTitle = findViewById(R.id.layout_custom_input_ip_dialog_tv_title);
        mEdContent = findViewById(R.id.layout_custom_input_ip_dialog_ed_content);
        mTvNegative = findViewById(R.id.layout_custom_input_ip_dialog_tv_negative);
        mTvPositive = findViewById(R.id.layout_custom_input_ip_dialog_tv_positive);
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNegativeClickListener != null) {
                    onNegativeClickListener.onClick();
                }
                dismiss();
            }
        });
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveClickListener != null) {
                    if (onPositiveClickListener.onClick(mEdContent.getText().toString().trim())) {
                        dismiss();
                    }
                    return;
                }
                dismiss();
            }
        });

    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return PromptDialog
     */
    public CustomInputIpDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    /**
     * 设置negative的点击
     *
     * @param negative String
     * @param listener OnNegativeClickListener
     * @return PromptDialog
     */
    public CustomInputIpDialog setNegativeButton(String negative, OnNegativeClickListener listener) {
        mTvNegative.setText(negative);
        onNegativeClickListener = listener;
        return this;
    }

    /**
     * 设置negative的点击
     *
     * @param listener OnNegativeClickListener
     * @return PromptDialog
     */
    public CustomInputIpDialog setNegativeButton(OnNegativeClickListener listener) {
        onNegativeClickListener = listener;
        return this;
    }

    /**
     * 设置positive的点击
     *
     * @param positive String
     * @param listener OnPositiveClickListener
     * @return PromptDialog
     */
    public CustomInputIpDialog setPositiveButton(String positive, OnPositiveClickListener listener) {
        mTvPositive.setText(positive);
        onPositiveClickListener = listener;
        return this;
    }

    /**
     * 设置positive的点击
     *
     * @param listener OnPositiveClickListener
     * @return PromptDialog
     */
    public CustomInputIpDialog setPositiveButton(OnPositiveClickListener listener) {
        onPositiveClickListener = listener;
        return this;
    }

    private OnNegativeClickListener onNegativeClickListener;

    public interface OnNegativeClickListener {
        void onClick();
    }

    private OnPositiveClickListener onPositiveClickListener;

    public interface OnPositiveClickListener {
        boolean onClick(String content);
    }
}

