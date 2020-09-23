package com.sfb.baselib.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.sfb.baselib.R;
import com.sfb.baselib.utils.CommonUtils;

import java.util.List;

public class MenuPopupView {

    private LinearLayout mLayoutContent;
    private PopupWindow mPopupWindow;

    private Context context;
    private List<String> menus;
    private MenuPopupCallback menuPopupCallback;

    public interface MenuPopupCallback {
        void onCallback(String text, int position);
    }

    public MenuPopupView(Context context, List<String> menus, MenuPopupCallback menuPopupCallback) {
        this.context = context;
        this.menus = menus;
        this.menuPopupCallback = menuPopupCallback;
    }

    public void show(View view) {
        if (mPopupWindow == null) {
            View popupView = LayoutInflater.from(context).inflate(R.layout.popup_menu_view, null);
            View mAlphaView = popupView.findViewById(R.id.alpha_view);
            mAlphaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            mLayoutContent = popupView.findViewById(R.id.layout_content);
            for (int i = 0; i < menus.size(); i++) {
                TextView textView = createItem(menus.get(i), i, i != menus.size() - 1);
                mLayoutContent.addView(textView);
            }
            // 创建PopupWindow
            int[] position = new int[2];
            view.getLocationOnScreen(position);
            int height = CommonUtils.getScreenHeight(context) - position[1];
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, height, true);
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(view);
        }
    }

    /**
     * 创建item
     *
     * @param text           String
     * @param position       int
     * @param showBottomLine boolean
     * @return TextView
     */
    private TextView createItem(final String text, final int position, boolean showBottomLine) {
        TextView view = new TextView(context);
        int padding = CommonUtils.dp2px(context, 12);
        view.setPadding(padding, padding, padding, padding);
        view.setTextColor(context.getResources().getColorStateList(R.color.selector_blue_and_textcolor));
        view.setTextSize(14);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setText(text);
        view.setGravity(Gravity.CENTER);
        if (showBottomLine) {
            view.setBackgroundResource(R.drawable.shape_stroke_bottom);
        } else {
            view.setBackgroundResource(R.color.white);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuPopupCallback != null) {
                    menuPopupCallback.onCallback(text, position);
                }
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
}
