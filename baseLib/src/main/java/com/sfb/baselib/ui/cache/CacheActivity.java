package com.sfb.baselib.ui.cache;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.annotation.BusSubscribe;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.ui.base.BaseListActivity;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.ui.mvp.BaseView;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.DateUtils;
import com.sfb.baselib.utils.DialogUtils;
import com.sfb.baselib.widget.dialog.ActionSheetDialog;
import com.sfb.baselib.R;
import com.sfb.baselib.components.event.CacheSelectEvent;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.network.upload.UploadManager;
import com.sfb.baselib.ui.cache.adapter.CollectCacheAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * 缓存列表
 */
@Route(path = ActivityRoute.CACHE_PATH)
public class CacheActivity extends BaseListActivity<BaseView, BasePresenter, CollectRecordBean> {

    public static final String COLLECT_TYPE_KEY = "collectType";

    @Inject
    public Bus mBus;
    @Inject
    public DaoHelper mDaoHelper;

    @Autowired
    public int collectType;

    protected LinearLayout mLayoutOperate;
    protected TextView mTvDelete;
    protected TextView mTvUpload;
    private MenuItem menuItem;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_cache, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void afterCreated() {
        setExpandView(R.layout.activity_cache);
        UploadDagger.getDaggerComponent().inject(this);
        mBus.register(this);
        mARouter.inject(this);
        //初始化数据
        initData();
        //初始化视图
        initView();
        setAdapter(new CollectCacheAdapter(this));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        collectType = getIntent().getIntExtra("collectType", 0);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mLayoutOperate = findViewById(R.id.layout_operate);
        mTvDelete = findViewById(R.id.tv_delete);
        mTvUpload = findViewById(R.id.tv_upload);
        mTvDelete.setOnClickListener(this);
        mTvUpload.setOnClickListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mAdapter.getData().size() > 0) {
            menu.findItem(R.id.menu_record_cache).setVisible(true);
        } else {
            menu.findItem(R.id.menu_record_cache).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_record_cache) {
            menuItem = item;
            //更新全选菜单
            updateMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新全选菜单
     */
    private void updateMenu() {
        boolean isSelect = !((CollectCacheAdapter) mAdapter).isSelect();
        ((CollectCacheAdapter) mAdapter).setSelect(isSelect);
        menuItem.setTitle(isSelect ? getString(R.string.not_select_all) : getString(R.string.selected_all));
        mLayoutOperate.setVisibility(isSelect ? View.VISIBLE : View.GONE);
        int count = mAdapter.getData().size();
        mTvDelete.setText(String.format(getString(R.string.delete_format), count));
        mTvUpload.setText(String.format(getString(R.string.upload_format), count));
    }

    @Override
    public void loadData(int pageNo, int pageSize) {
        List<CollectRecordBean> list = mDaoHelper.queryCollectRecord(collectType, pageNo, pageSize);
        mPtrRecycleView.loadComplete(list);
    }

    @Override
    protected void onItemClick(BaseQuickAdapter adapter, int position) {
        super.onItemClick(adapter, position);
        if (!((CollectCacheAdapter) mAdapter).isSelect()) {
            switch (collectType) {
                case CollectRecordBean.CAR_COLLECT://车辆采集
                    mARouter.build(ActivityRoute.CAR_DETAIL_PATH)
                            .withParcelable("mCollectRecordBean", mAdapter.getItem(position))
                            .navigation();
                    break;
                case CollectRecordBean.CLUE_COLLECT://线索采集
                    mARouter.build(ActivityRoute.CLUE_DETAIL_PATH)
                            .withParcelable("mCollectRecordBean", mAdapter.getItem(position))
                            .navigation();
                    break;
                case CollectRecordBean.PUBLISH_TASK://发布任务
                    showClickTaskDialog(mAdapter.getItem(position));
                    break;
                case CollectRecordBean.PERSONNEL_COLLECT://人员采集
                    mARouter.build(ActivityRoute.PERSON_COLLECT_DETAIL_PATH)
                            .withParcelable("mCollectRecordBean", mAdapter.getItem(position))
                            .navigation();
                    break;
                case CollectRecordBean.CASE_INPUT://案例采集
                    mARouter.build(ActivityRoute.LEGAL_CASE_DETAIL_PATH)
                            .withParcelable("mCollectRecordBean", mAdapter.getItem(position))
                            .navigation();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 显示任务类型点击的对话框
     */
    private void showClickTaskDialog(final CollectRecordBean bean) {
        String[] items = {getString(R.string.publish), getString(R.string.delete)};
        DialogUtils.openDialog(this, items, new ActionSheetDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                switch (i) {
                    case 0:
                        mARouter.build(ActivityRoute.ADD_TASK_PATH)
                                .withInt("mType", 1)//mType对应AddTaskActivity.TYPE_KEY，1对应AddTaskActivity.TYPE_OF_CACHE
                                .withParcelable("mCacheBean", bean)//mCacheBean对应AddTaskActivity.CACHE_BEAN_KEY
                                .navigation();
                        break;
                    case 1:
                        mAdapter.remove(mAdapter.getData().indexOf(bean));
                        mDaoHelper.deleteCollectRecord(bean);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.tv_delete) {//删除选中的记录
            deleteSelected();
        } else if (i == R.id.tv_upload) {//上传选中的记录
            uploadSelected();
        }
    }

    /**
     * 删除选中的记录
     */
    private void deleteSelected() {
        List<CollectRecordBean> list = ((CollectCacheAdapter) mAdapter).getSelectList();
        for (int i = 0; i < list.size(); i++) {
            mAdapter.remove(mAdapter.getData().indexOf(list.get(i)));
            mDaoHelper.deleteCollectRecord(list.get(i));
        }
        //更新menu
        invalidateOptionsMenu();
        //更新全选菜单
        updateMenu();
    }

    /**
     * 上传选中的记录
     */
    private void uploadSelected() {
        List<CollectRecordBean> list = ((CollectCacheAdapter) mAdapter).getSelectList();
        //检测是否超时提交
        if (checkTimeOut(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            UploadManager.getInstance().notifyUpload(list.get(i));
        }
        //更新全选菜单
        updateMenu();
        showToast(String.format(getString(R.string.cache_upload_tip), list.size()));
    }

    /**
     * 检测是否超时提交
     *
     * @param list List<CollectRecordBean>
     * @return boolean
     */
    private boolean checkTimeOut(List<CollectRecordBean> list) {
        switch (collectType) {
            case CollectRecordBean.CAR_COLLECT://车辆采集
            case CollectRecordBean.PERSONNEL_COLLECT://人员采集
                for (CollectRecordBean bean : list) {
                    long saveTime = bean.getSaveTime();
                    long currentTime = CommonUtils.getServerTime(this);
                    boolean isSameDay = DateUtils.isSameDate(saveTime, currentTime);
                    if (!isSameDay) {
                        showToast(getString(R.string.collect_cache_time_not_today, bean.getSubject()));
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 缓存记录上传成功、删除本地缓存回调
     *
     * @param event CollectRecordEvent
     */
    @BusSubscribe
    public void onEvent(CollectRecordEvent event) {
        if (event != null && event.getCollectRecordBean() != null && event.isSuccess()) {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                if (mAdapter.getData().get(i).getId().equals(event.getCollectRecordBean().getId())) {
                    mAdapter.remove(i);
                    //更新menu
                    invalidateOptionsMenu();
                    break;
                }
            }
        }
    }

    /**
     * 缓存项是否选中的回调
     *
     * @param event CacheSelectEvent
     */
    @BusSubscribe
    public void onEvent(CacheSelectEvent event) {
        List<CollectRecordBean> list = ((CollectCacheAdapter) mAdapter).getSelectList();
        int count = list.size();
        mTvDelete.setText(String.format(getString(R.string.delete_format), count));
        mTvUpload.setText(String.format(getString(R.string.upload_format), count));
    }

}
