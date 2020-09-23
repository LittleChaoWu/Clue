package com.sfb.clue.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.route.FragmentRoute;
import com.sfb.baselib.ui.base.BaseListFragment;
import com.sfb.baselib.R;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.clue.ClueDetailActivity;
import com.sfb.clue.adapter.ClueReportAdapter;
import com.sfb.clue.contract.ClueReportContract;
import com.sfb.clue.presenter.ClueReportPresenter;

import java.util.List;

/**
 * 线索举报的Fragment
 */
@Route(path = FragmentRoute.CLUE_FRAGMENT_PATH)
public class ClueFragment extends BaseListFragment<ClueReportContract.View, ClueReportContract.Presenter, ClueInfo> implements ClueReportContract.View {

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    public ClueReportContract.Presenter createPresenter() {
        return new ClueReportPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_clue_report, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_clue_report) {
            mARouter.build(ActivityRoute.ADD_CLUE_PATH).navigation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void afterViewCreated(View view) {
        setToolBarTitle(R.string.main_clue_report);
        setAdapter(new ClueReportAdapter(getContext()));
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

}
