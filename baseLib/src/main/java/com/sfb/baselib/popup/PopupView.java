package com.sfb.baselib.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.sfb.baselib.R;
import com.sfb.baselib.data.ConstantInfo;
import com.sfb.baselib.utils.CommonUtils;

import java.util.List;

/**
 * PopupView
 */
public class PopupView {

    private LinearLayout mLayoutContent;
    private PopupWindow mPopupWindow;

    private Context context;
    private List<ConstantInfo> list;
    private PopupCallback popupCallback;

    public interface PopupCallback {
        void onDismiss();

        void onCallback(ConstantInfo info);
    }

    public PopupView(Context context, List<ConstantInfo> list, PopupCallback popupCallback) {
        this.context = context;
        this.list = list;
        this.popupCallback = popupCallback;
    }

    public void show(View view) {
        show(view, 0);
    }

    public void show(View view, int originIndex) {
        if (mPopupWindow == null) {
            View popupView = LayoutInflater.from(context).inflate(R.layout.popup_view, null);
            View mAlphaView = popupView.findViewById(R.id.alpha_view);
            mAlphaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            mLayoutContent = popupView.findViewById(R.id.layout_content);
            for (int i = 0; i < list.size(); i++) {
                ConstantInfo info = list.get(i);
                TextView textView = createItem(info);
                textView.setSelected(i == originIndex);
                mLayoutContent.addView(textView);
            }
            ScrollView scrollView = popupView.findViewById(R.id.mScrollView);
            scrollView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.getScreenHeight(view.getContext()) / 2));
            // 创建PopupWindow
            int[] position = new int[2];
            view.getLocationOnScreen(position);
            int height = CommonUtils.getScreenHeight(context) - position[1];
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, height, true);
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (popupCallback != null) {
                        popupCallback.onDismiss();
                    }
                }
            });
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(view);
        }
    }

    /**
     * 创建item
     *
     * @param info ConstantInfo
     * @return TextView
     */
    private TextView createItem(final ConstantInfo info) {
        TextView view = new TextView(context);
        int padding = CommonUtils.dp2px(context, 12);
        view.setPadding(padding, padding, padding, padding);
        view.setTextColor(context.getResources().getColorStateList(R.color.selector_blue_and_textcolor));
        view.setTextSize(14);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setText(info.getCfgText());
        view.setGravity(Gravity.CENTER);
        view.setBackgroundColor(Color.WHITE);
//        view.setBackgroundResource(R.drawable.shape_stroke_bottom_f0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupCallback != null) {
                    popupCallback.onCallback(info);
                }
                refreshData(info);
                dismiss();
            }
        });
        return view;
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 判断是否显示
     *
     * @return boolean
     */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public void refreshData(ConstantInfo info) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCfgValue().equals(info.getCfgValue())) {
                mLayoutContent.getChildAt(i).setSelected(true);
            } else {
                mLayoutContent.getChildAt(i).setSelected(false);
            }
        }
    }

}
