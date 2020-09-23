package com.sfb.clue.presenter;

import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.utils.RxUtils;
import com.sfb.clue.contract.AddClueContract;
import com.sfb.clue.data.ClueTypeInfo;

import java.util.List;

public class AddCluePresenter extends AddClueContract.Presenter {

    @Override
    public void getClueTypes() {
        mView.showLoading();
        ResponseSubscriber subscriber = mClueApiService.getClueTypes()
                .compose(RxUtils.<BaseResponse<List<ClueTypeInfo>>>io2main())
                .subscribeWith(new ResponseSubscriber<BaseResponse<List<ClueTypeInfo>>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<List<ClueTypeInfo>> response) {
                        if (isViewAttach()) {
                            mView.hideLoading();
                            ((AddClueContract.View) mView).getClueTypesComplete(response.getData());
                        }
                    }

                    @Override
                    public void onFail(String errorMessage, boolean isEmptyMessage) {
                        if (isViewAttach()) {
                            mView.hideLoading();
                            ((AddClueContract.View) mView).getClueTypesComplete(null);
                        }
                    }
                });
        subscribers.add(subscriber);
    }
}
