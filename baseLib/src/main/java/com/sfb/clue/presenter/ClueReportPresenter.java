package com.sfb.clue.presenter;

import com.pref.GuardPreference_;
import com.sfb.baselib.BaseApp;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.data.ListInfo;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.utils.RxUtils;
import com.sfb.clue.contract.ClueReportContract;

public class ClueReportPresenter extends ClueReportContract.Presenter {
    private GuardPreference_ preference = GuardPreference_.getInstance(BaseApp.getContext());

    @Override
    public void getClueList(int pageNo, int pageSize) {
        if (pageNo == 1) {
            mView.showLoading();
        }
        boolean isSdkLogin = preference.getIsSdkLogin();
        ResponseSubscriber subscriber = mClueApiService.getClueList(pageNo, pageSize, isSdkLogin ? "1" : "0")
                .compose(RxUtils.<BaseResponse<ListInfo<ClueInfo>>>io2main())
                .subscribeWith(new ResponseSubscriber<BaseResponse<ListInfo<ClueInfo>>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<ListInfo<ClueInfo>> response) {
                        if (isViewAttach()) {
                            mView.hideLoading();
                            if (response.getData() != null)
                                ((ClueReportContract.View) mView).getClueListComplete(response.getData().getResults());
                        }
                    }

                    @Override
                    public void onFail(String errorMessage, boolean isEmptyMessage) {
                        if (isViewAttach()) {
                            mView.hideLoading();
                            ((ClueReportContract.View) mView).getClueListComplete(null);
                        }
                    }
                });
        subscribers.add(subscriber);
    }

}
