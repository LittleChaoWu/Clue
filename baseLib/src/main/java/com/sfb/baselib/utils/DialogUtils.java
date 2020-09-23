package com.sfb.baselib.utils;

import android.content.Context;


import com.sfb.baselib.data.ConstantInfo;
import com.sfb.baselib.widget.dialog.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class DialogUtils {

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param items    String[]
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, String[] items, ActionSheetDialog.OnItemClickListener listener) {
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(false);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param items    ArrayList<String>
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, ArrayList<String> items, ActionSheetDialog.OnItemClickListener listener) {
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(false);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param list     List<ConstantInfo>
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, List<ConstantInfo> list, ActionSheetDialog.OnItemClickListener listener) {
        ArrayList<String> items = new ArrayList<>();
        for (ConstantInfo info : list) {
            items.add(info.getCfgText());
        }
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(false);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param title    String
     * @param items    String[]
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, String title, String[] items, ActionSheetDialog.OnItemClickListener listener) {
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(true);
        dialog.title(title);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param title    String
     * @param items    ArrayList<String>
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, String title, ArrayList<String> items, ActionSheetDialog.OnItemClickListener listener) {
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(true);
        dialog.title(title);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

    /**
     * 打开对话框
     *
     * @param context  Context
     * @param title    String
     * @param list     List<ConstantInfo>
     * @param listener OnItemClickListener
     */
    public static void openDialog(Context context, String title, List<ConstantInfo> list, ActionSheetDialog.OnItemClickListener listener) {
        ArrayList<String> items = new ArrayList<>();
        for (ConstantInfo info : list) {
            items.add(info.getCfgText());
        }
        ActionSheetDialog dialog = new ActionSheetDialog(context);
        dialog.setDatas(items);
        dialog.isTitleShow(true);
        dialog.title(title);
        dialog.setOnItemClickListener(listener);
        dialog.show();
    }

}
