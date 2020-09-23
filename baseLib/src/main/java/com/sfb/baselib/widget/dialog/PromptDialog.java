package com.sfb.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sfb.baselib.R;

import androidx.annotation.NonNull;

public class PromptDialog extends Dialog {

    public static final int COMMON = 0;
    public static final int SINGLE = 1;
    public static final int CHECK = 2;
    public static final int CHECK_SINGLE = 3;

    protected TextView mTvTitle;
    protected TextView mTvMessage;
    protected TextView mTvCheck;
    protected TextView mTvNegative;
    protected TextView mTvPositive;

    private int mode = COMMON;

    public PromptDialog(@NonNull Context context) {
        this(context, COMMON);
    }

    public PromptDialog(@NonNull Context context, int mode) {
        super(context, R.style.PromptDialog);
        this.mode = mode;
        setCancelable(false);
        setContentView(R.layout.layout_promp_dialog);
        mTvTitle = findViewById(R.id.tv_title);
        mTvMessage = findViewById(R.id.tv_message);
        mTvCheck = findViewById(R.id.tv_check);
        mTvNegative = findViewById(R.id.tv_negative);
        mTvPositive = findViewById(R.id.tv_positive);
        if (mode == SINGLE) {
            mTvNegative.setVisibility(View.GONE);
        } else if (mode == CHECK) {
            mTvCheck.setVisibility(View.VISIBLE);
        } else if (mode == CHECK_SINGLE) {
            mTvNegative.setVisibility(View.GONE);
            mTvCheck.setVisibility(View.VISIBLE);
        }
        mTvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = !mTvCheck.isSelected();
                mTvCheck.setSelected(isCheck);
            }
        });
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
                    onPositiveClickListener.onClick();
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
    public PromptDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    /**
     * 设置消息
     *
     * @param message 消息
     * @return PromptDialog
     */
    public PromptDialog setMessage(String message) {
        mTvMessage.setText(message);
        return this;
    }

    /**
     * 设置检测框文本
     *
     * @param check_label String
     * @return PromptDialog
     */
    public PromptDialog setCheckLabel(String check_label) {
        mTvCheck.setText(check_label);
        return this;
    }

    /**
     * 检测框是否选中
     *
     * @return boolean
     */
    public boolean isCheck() {
        return mTvCheck != null && mTvCheck.isSelected();
    }

    /**
     * 设置negative的点击
     *
     * @param negative String
     * @param listener OnNegativeClickListener
     * @return PromptDialog
     */
    public PromptDialog setNegativeButton(String negative, OnNegativeClickListener listener) {
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
    public PromptDialog setNegativeButton(OnNegativeClickListener listener) {
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
    public PromptDialog setPositiveButton(String positive, OnPositiveClickListener listener) {
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
    public PromptDialog setPositiveButton(OnPositiveClickListener listener) {
        onPositiveClickListener = listener;
        return this;
    }

    /**
     * 设置消息对齐方式
     *
     * @param gravity 对齐方式
     */
    public PromptDialog messageAlign(int gravity) {
        mTvMessage.setGravity(gravity);
        return this;
    }

    private OnNegativeClickListener onNegativeClickListener;

    public interface OnNegativeClickListener {
        void onClick();
    }

    private OnPositiveClickListener onPositiveClickListener;

    public interface OnPositiveClickListener {
        void onClick();
    }
}

