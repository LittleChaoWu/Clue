package com.sfb.clue.contract;

import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.clue.ClueBasePresenter;

import java.util.List;

public interface ClueReportContract {

    interface View extends BaseView {
        /**
         * 获取线索举报列表回调
         *
         * @param list List<ClueInfo>
         */
        void getClueListComplete(List<ClueInfo> list);
    }

    abstract class Presenter extends ClueBasePresenter<View> {
        /**
         * 获取线索举报列表
         *
         * @param pageNo   页数
         * @param pageSize 页大小
         */
        public abstract void getClueList(int pageNo, int pageSize);
    }

}
