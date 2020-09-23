package com.sfb.clue;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sfb.baselib.R;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.ui.base.BaseListActivity;
import com.sfb.clue.adapter.ClueReportAdapter;
import com.sfb.clue.contract.ClueReportContract;
import com.sfb.clue.presenter.ClueReportPresenter;

import java.util.List;

/**
 * 线索举报界面
 */
@Route(path = ActivityRoute.CLUE_REPORT_PATH)
public class ClueReportActivity extends BaseListActivity<ClueReportContract.View, ClueReportContract.Presenter, ClueInfo> implements ClueReportContract.View {

    @Override
    public ClueReportContract.Presenter createPresenter() {
        return new ClueReportPresenter();
    }

    @Override
    public void afterCreated() {
        setExpandView(R.layout.activity_clue_report);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        setAdapter(new ClueReportAdapter(this));
    }

    @Override
    public void loadData(int pageNo, int pageSize) {
        mPresenter.getClueList(pageNo, pageSize);
    }

    @Override
    public void getClueListComplete(List<ClueInfo> list) {
        mPtrRecycleView.loadComplete(list);
    }

    @Override
    protected void onItemClick(BaseQuickAdapter adapter, int position) {
        super.onItemClick(adapter, position);
        mARouter.build(ActivityRoute.CLUE_DETAIL_PATH)
                .withInt(ClueDetailActivity.CLUE_ID_KEY, mAdapter.getItem(position).getId())
                .navigation();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.tv_commit) {
            mARouter.build(ActivityRoute.ADD_CLUE_PATH).navigation();
        }
    }

}
