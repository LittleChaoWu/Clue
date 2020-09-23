package com.sfb.clue;

import com.sfb.baselib.ui.mvp.BaseView;

import dagger.Component;

@Component(modules = ClueModule.class)
public interface ClueComponent {
    void inject(ClueBasePresenter<BaseView> baseViewClueBasePresenter);

    void inject(AddClueActivity addClueActivity);

    void inject(ClueDetailActivity clueDetailActivity);
}
