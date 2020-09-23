package com.sfb.clue.presenter;

import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.utils.RxUtils;
import com.sfb.baselib.R;
import com.sfb.clue.contract.AddClueContract;
import com.sfb.clue.contract.ClueDetailContract;
import com.sfb.clue.data.ClueDetailInfo;

public class ClueDetailPresenter extends ClueDetailContract.Presenter {
    @Override
    public void getClueDetail(int clueId) {
        if (isViewAttach()) {
            mView.showLoading();
        }
        ResponseSubscriber subscriber = mClueApiService.getClueDetail(clueId)
                .compose(RxUtils.<BaseResponse<ClueDetailInfo>>io2main())
                .subscribeWith(new ResponseSubscriber<BaseResponse<ClueDetailInfo>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<ClueDetailInfo> response) {
                        if (isViewAttach()) {
                            ((ClueDetailContract.View) mView).getClueDetailComplete(response.getData());
                        }
                    }

                    @Override
                    public void onFail(String errorMessage, boolean isEmptyMessage) {
                        if (isViewAttach()) {
                            ((ClueDetailContract.View) mView).getClueDetailComplete(null);
                            if (isEmptyMessage) {
                                mView.showToast(R.string.get_info_fail);
                            }
                        }
                    }
                });
        subscribers.add(subscriber);
    }
}
