package com.sfb.clue.contract;

import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.clue.ClueBasePresenter;
import com.sfb.clue.data.ClueTypeInfo;

import java.util.List;

public interface AddClueContract {

    interface View extends BaseView {
        /**
         * 获取线索类型回调
         *
         * @param list List<ClueTypeInfo>
         */
        void getClueTypesComplete(List<ClueTypeInfo> list);
    }

    abstract class Presenter extends ClueBasePresenter<View> {
        /**
         * 获取线索类型
         */
        public abstract void getClueTypes();
    }

}
