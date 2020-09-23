package com.sfb.clue.contract;

import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.clue.ClueBasePresenter;
import com.sfb.clue.data.ClueDetailInfo;

public interface ClueDetailContract {

    interface View extends BaseView {

        /**
         * 获取线索详情回调
         *
         * @param info ClueDetailInfo
         */
        void getClueDetailComplete(ClueDetailInfo info);

    }

    abstract class Presenter extends ClueBasePresenter<View> {
        /**
         * 获取线索详情
         *
         * @param clueId int
         */
        public abstract void getClueDetail(int clueId);
    }

}
