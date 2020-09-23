package com.sfb.baselib.widget.pulltorefrsh;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wuwc on 15/7/23.
 * author wuwc
 * E-MAIL:1428943514@qq.com
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = SpacesItemDecoration.class.getSimpleName();
    private int space;
    private int myType;
    private boolean hasOut = true;//是否空出左右边框部分
    private boolean hasHead;//是否有头部
    private int left, right, top, bottom;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public void setMyType(RecyclerViewDiverType myType, boolean hasOut, boolean hasHead) {
        this.myType = myType.getType();
        this.hasOut = hasOut;
        this.hasHead = hasHead;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
//        L.i(TAG, "-----position=" + position);
        if (space > 0) {
            left = space;
            right = space;
            top = space;
            bottom = space;
        }
        if (position == 0) {
            return;
        }
        outRect.left = left;
        outRect.right = right;
        outRect.top = top;
        outRect.bottom = bottom;
        if (RecyclerViewDiverType.DOUBLE.getType() == this.myType) {
            if (position % 2 == 0) {
                if (hasHead) {
                    outRect.left = left / 2;
                    if (hasOut) {
                        outRect.right = 0;
                    }
                } else {
//                    outRect.right = right / 2;
                    if (hasOut) {
                        outRect.left = left / 2;
                        outRect.right = right / 2;
                    }
                }
            } else {
                if (hasHead) {
                    outRect.right = right / 2;
                    if (hasOut) {
                        outRect.left = 0;
                    }
                } else {
//                    outRect.left = left / 2;
                    if (hasOut) {
                        if (position % 3 == 0) {
                            outRect.left = left / 2;
                        } else {
                            outRect.right = right / 2;
                        }
                    }
                }
            }
            if (position <= 2) {
                outRect.top = top;
            }
            outRect.bottom = 0;
//                int column = position % spanCount; // item column
        } else {
            /*if (!hasOut) {
                outRect.left = 0;
                outRect.right = 0;
                outRect.bottom = 0;
            }*/
            outRect.top = 0;
        }
        // Add top margin only for the first item to avoid double space between items
        if (position == 0) {
            outRect.top = top;
        }
    }

    public enum RecyclerViewDiverType {
        DOUBLE(2), SINGLE(1);
        private int type;

        RecyclerViewDiverType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    public enum AdapterItemType {
        HEADER(0), ITEM(1);
        private int type;

        AdapterItemType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}