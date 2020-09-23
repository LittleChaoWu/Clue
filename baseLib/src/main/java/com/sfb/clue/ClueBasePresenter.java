package com.sfb.clue;

import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.clue.api.ClueApiService;

import javax.inject.Inject;

public class ClueBasePresenter<V extends BaseView> extends BasePresenter {

    @Inject
    public ClueApiService mClueApiService;

    public ClueBasePresenter() {
        ClueDagger.getDaggerComponent().inject((ClueBasePresenter<BaseView>) this);
    }
}
