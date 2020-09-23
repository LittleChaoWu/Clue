package com.sfb.baselib.widget.time;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.listener.OnDismissListener;
import com.sfb.baselib.utils.CommonUtils;

import java.util.Calendar;
import java.util.Date;

public class DateOrTimeDialog {

    private Context context;
    private String title;
    private String cancel;
    private String submit;
    private boolean pickTime;
    private long currentTime;

    private TimePicker mTimePickerView;

    private OnSelectListener onSelectListener;

    public DateOrTimeDialog(Context context, boolean pickTime, OnSelectListener onSelectListener) {
        this.context = context;
        this.pickTime = pickTime;
        this.onSelectListener = onSelectListener;
    }

    public DateOrTimeDialog(Context context, String title, boolean pickTime, OnSelectListener onSelectListener) {
        this.context = context;
        this.title = title;
        this.pickTime = pickTime;
        this.onSelectListener = onSelectListener;
    }

    /**
     * 显示时间选择器
     */
    public void show() {
        if (context instanceof Activity) {
            CommonUtils.hideKeyBoard((Activity) context);
        }
        if (mTimePickerView == null) {
            PickerBuilder builder = new PickerBuilder(context, new OnTimePickListener() {

                @Override
                public void onTimeSelect(Date date, View v) {
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(date.getTime());
                    }
                }

                @Override
                public void onCancel() {
                    if (onSelectListener != null) {
                        onSelectListener.onCancel();
                    }
                }
            });
            if (!TextUtils.isEmpty(title)) {
                builder.setTitleText(title);
            }
            if (!TextUtils.isEmpty(submit)) {
                builder.setSubmitText(submit);
            }
            if (!TextUtils.isEmpty(cancel)) {
                builder.setCancelText(cancel);
            }
            if (currentTime != 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(currentTime);
                builder.setDate(calendar);
            }
            if (pickTime) {
                builder.setType(new boolean[]{true, true, true, true, true, false});
            }
            mTimePickerView = builder.setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .build();
            mTimePickerView.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if (onSelectListener != null) {
                        onSelectListener.onDismiss();
                    }
                }
            });
        }
        mTimePickerView.show();
    }

    public DateOrTimeDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public DateOrTimeDialog setTitle(int resId) {
        this.title = context.getString(resId);
        return this;
    }

    public DateOrTimeDialog setSubmit(String submit) {
        this.submit = submit;
        return this;
    }

    public DateOrTimeDialog setSubmit(int resId) {
        this.submit = context.getString(resId);
        return this;
    }

    public DateOrTimeDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public DateOrTimeDialog setCancel(int resId) {
        this.cancel = context.getString(resId);
        return this;
    }

    public DateOrTimeDialog setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelect(long time);

        void onCancel();

        void onDismiss();
    }
}
