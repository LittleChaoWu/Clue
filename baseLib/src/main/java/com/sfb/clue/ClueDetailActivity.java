package com.sfb.clue;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.sfb.baselib.BuildConfig;
import com.sfb.baselib.Constants;
import com.sfb.baselib.R;
import com.sfb.baselib.components.bus.annotation.BusSubscribe;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.components.bus.event.UploadErrorEvent;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.data.FileModel;
import com.sfb.baselib.data.ThumbInfo;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.ui.base.BaseUploadActivity;
import com.sfb.baselib.ui.map.MapActivity;
import com.sfb.baselib.utils.DateUtils;
import com.sfb.baselib.widget.form.LinearView;
import com.sfb.baselib.widget.gridimage.GridImageView;
import com.sfb.clue.contract.ClueDetailContract;
import com.sfb.clue.data.ClueCheckInfo;
import com.sfb.clue.data.ClueDetailInfo;
import com.sfb.clue.presenter.ClueDetailPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sfb.baselib.data.ClueInfo.CLUE_STATUS_ALREADY_FEEDBACK;
import static com.sfb.baselib.data.ClueInfo.CLUE_STATUS_CHECKED_NO_CONFIRM;
import static com.sfb.baselib.data.ClueInfo.CLUE_STATUS_HANDLED;
import static com.sfb.baselib.data.ClueInfo.CLUE_STATUS_HANDLING;
import static com.sfb.baselib.data.ClueInfo.CLUE_STATUS_UN_PASSED;

/**
 * 线索详情界面
 */
@Route(path = ActivityRoute.CLUE_DETAIL_PATH)
public class ClueDetailActivity extends BaseUploadActivity<ClueDetailContract.View, ClueDetailContract.Presenter> implements ClueDetailContract.View {

    public static final String CLUE_ID_KEY = "clueId";

    //线索内容
    public LinearLayout mLayoutContent;
    private LinearView mLinearSubject;
    private LinearView mLinearBroadType;
    private LinearView mLinearType;
    private LinearView mLinearMiniType;
    private LinearView mLinearDescriber;
    private LinearView mLinearAddress;
    private LinearView mLinearTime;
    private GridImageView mGridImageView;
    private LinearLayout mLayoutMore;
    private TextView mTvFeedback;

    @Autowired
    public int clueId;

    private String gps;
    private String address;
    private boolean isCache;//是否来自缓存
    private ClueInfo clueInfo;


    /**
     * 此函数可用于子类重写
     *
     * @return boolean
     */
    public boolean isTask() {
        return false;
    }

    /**
     * 此函数可用于子类重写
     *
     * @return int
     */
    public int getCollectId() {
        return 0;
    }

    @Override
    public ClueDetailContract.Presenter createPresenter() {
        return new ClueDetailPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isCache) {
            mPresenter.getClueDetail(clueId);
        } else {
            updateUI(clueInfo);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_detail);
        ClueDagger.getDaggerComponent().inject(this);
        mARouter.inject(this);
        //初始化数据
        initData();
        //初始化视图
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (isTask()) {
            clueId = getCollectId();
        }
        mCollectRecordBean = getIntent().getParcelableExtra(COLLECT_RECORD_BEAN_KEY);
        isCache = mCollectRecordBean != null;
        if (isCache) {
            clueInfo = gson.fromJson(mCollectRecordBean.getAttachData(), ClueInfo.class);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //线索内容
        mLayoutContent = findViewById(R.id.layout_content);
        mLinearSubject = findViewById(R.id.linear_subject);
        mLinearBroadType = findViewById(R.id.linear_broad_type);
        mLinearType = findViewById(R.id.linear_type);
        mLinearMiniType = findViewById(R.id.linear_mini_type);
        mLinearDescriber = findViewById(R.id.linear_describe);
        mLinearAddress = findViewById(R.id.linear_address);
        mLinearTime = findViewById(R.id.linear_time);
        mGridImageView = findViewById(R.id.mGridImageView);
        mLayoutMore = findViewById(R.id.layout_more);
        mTvFeedback = findViewById(R.id.tv_feedback);
        mLinearAddress.setLinearClickListener(this);
        mTvFeedback.setOnClickListener(this);
        //删除、上传
        findViewById(R.id.tv_delete).setOnClickListener(this);
        findViewById(R.id.tv_upload).setOnClickListener(this);
    }

    @Override
    public void onSuffixTextClick(int id) {
        super.onSuffixTextClick(id);
        if (id == R.id.linear_address) {
            mARouter.build(ActivityRoute.MAP_PATH)
                    .withString(MapActivity.GPS_KEY, gps)
                    .withString(MapActivity.ADDRESS_KEY, address)
                    .navigation();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tv_feedback) {
            mARouter.build(ActivityRoute.CLUE_FEEDBACK_PATH).withInt(ClueFeedbackActivity.CLUE_ID_KEY, clueId).navigation();
        } else if (id == R.id.tv_delete) {
            //删除缓存记录
            deleteCache(mCollectRecordBean);
        } else if (id == R.id.tv_upload) {
            //上传检测
            uploadCheck(mCollectRecordBean);
        }
    }

    @Override
    public void runInBackCallback() {
        finish();
    }

    /**
     * 缓存记录上传成功回调
     *
     * @param event CollectRecordEvent
     */
    @BusSubscribe
    public void onEvent(CollectRecordEvent event) {
        if (isUploadSuccess(event)) {
            finish();
        }
    }

    @BusSubscribe
    public void onEvent(UploadErrorEvent event) {
        uploadError(event);
    }

    /**
     * 更新UI
     */
    private void updateUI(ClueInfo info) {
        if (info != null) {
            gps = info.getGps();
            address = info.getAreaName();
            mLinearSubject.setText(info.getTitle());
            mLinearBroadType.setText(info.getClueBroadTypeName());
            if (!TextUtils.isEmpty(clueInfo.getClueTypeName())) {
                mLinearType.setVisibility(View.VISIBLE);
                mLinearType.setText(clueInfo.getClueTypeName());
                if (!TextUtils.isEmpty(clueInfo.getClueMiniTypeName())) {
                    mLinearMiniType.setVisibility(View.VISIBLE);
                    mLinearMiniType.setText(clueInfo.getClueMiniTypeName());
                }
            }
            mLinearDescriber.setText(TextUtils.isEmpty(info.getSummary()) ? getString(R.string.summary_empty) : info.getSummary());
            if (TextUtils.isEmpty(info.getAreaName()) || TextUtils.isEmpty(info.getGps())) {
                mLinearAddress.setText(getString(R.string.address_empty));
                mLinearAddress.setSuffix("");
            } else {
                mLinearAddress.setText(info.getAreaName());
                mLinearAddress.setSuffix(getString(R.string.look_map));
            }
            mLinearTime.setText(info.getCreateTimeStr());
            if (!TextUtils.isEmpty(info.getFilePaths())) {
                List<ThumbInfo> list = new ArrayList<>();
                List<String> paths = Arrays.asList(info.getFilePaths().split(","));
                for (int i = 0; i < paths.size(); i++) {
                    String path = paths.get(i);
                    if (!TextUtils.isEmpty(path)) {
                        ThumbInfo thumbInfo = new ThumbInfo(path);
                        list.add(thumbInfo);
                    }
                }
                mGridImageView.clearData();
                mGridImageView.addData(list);
            } else {
                mGridImageView.setVisibility(View.GONE);
            }
            findViewById(R.id.layout_save).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 更新UI
     */
    private void updateUI(ClueDetailInfo info) {
        if (info == null) {
            return;
        }
        ClueInfo clueInfo = info.getClue();
        if (clueInfo == null) {
            return;
        }
        gps = clueInfo.getGps();
        address = clueInfo.getAreaName();
        mLinearSubject.setText(clueInfo.getTitle());
        mLinearSubject.setSuffix(clueInfo.getStatusInReport(this));
        mLinearBroadType.setText(clueInfo.getClueBroadTypeName());
        if (!TextUtils.isEmpty(clueInfo.getClueTypeName())) {
            mLinearType.setVisibility(View.VISIBLE);
            mLinearType.setText(clueInfo.getClueTypeName());
            if (!TextUtils.isEmpty(clueInfo.getClueMiniTypeName())) {
                mLinearMiniType.setVisibility(View.VISIBLE);
                mLinearMiniType.setText(clueInfo.getClueMiniTypeName());
            }
        }
        mLinearDescriber.setText(TextUtils.isEmpty(clueInfo.getSummary()) ? getString(R.string.summary_empty) : clueInfo.getSummary());
        if (TextUtils.isEmpty(clueInfo.getAreaName()) || TextUtils.isEmpty(clueInfo.getGps())) {
            mLinearAddress.setText(getString(R.string.address_empty));
            mLinearAddress.setSuffix("");
        } else {
            mLinearAddress.setText(clueInfo.getAreaName());
            mLinearAddress.setSuffix(getString(R.string.look_map));
        }
        mLinearTime.setText(clueInfo.getCreateTimeStr());
        List<FileModel> fileModels = info.getFile_model_list();
        if (fileModels != null && fileModels.size() > 0) {
            List<ThumbInfo> thumbList = new ArrayList<>();
            for (FileModel model : fileModels) {
                thumbList.add(new ThumbInfo(model));
            }
            mGridImageView.clearData();
            mGridImageView.addData(thumbList);
        } else {
            mGridImageView.setVisibility(View.GONE);
        }

        //以上是固定信息所以不用[add]的形式添加，以下是动态的数据，为了不必要的内存消耗，所以采用[add]的形式
        //处置结果
        mTvFeedback.setVisibility(View.GONE);
        mLayoutMore.removeAllViews();
        int clueStatus = clueInfo.getClueStatus();
        if (clueStatus == CLUE_STATUS_HANDLING || clueStatus == CLUE_STATUS_CHECKED_NO_CONFIRM) {
            mLayoutMore.addView(createLinearView(getString(R.string.clue_dispose_result_tip), getString(R.string.clue_dispose_result_title), getString(R.string.clue_disposing_tip)));
            mLayoutMore.addView(createLinearView("", getString(R.string.clue_result_time_title), DateUtils.formatTime(clueInfo.getUpdatedTime())));
        } else if (clueStatus == CLUE_STATUS_HANDLED || clueStatus == CLUE_STATUS_ALREADY_FEEDBACK) {
            long dealTime = clueInfo.getUpdatedTime();
            mLayoutMore.addView(createLinearView(getString(R.string.clue_dispose_result_tip), getString(R.string.clue_dispose_result_title), clueInfo.getResultNote()));
            if (info.getClueStatusList() != null && info.getClueStatusList().size() > 0) {
                for (ClueCheckInfo clueCheckInfo : info.getClueStatusList()) {
                    dealTime = clueCheckInfo.getDealTime();
                    if (!TextUtils.isEmpty(clueCheckInfo.getClueBigTypeName())) {
                        mLayoutMore.addView(createLinearView("", getString(R.string.clue_broad_type_title), clueCheckInfo.getClueBigTypeName()));
                    }
                    if (!TextUtils.isEmpty(clueCheckInfo.getClueSmallTypeName())) {
                        mLayoutMore.addView(createLinearView("", getString(R.string.clue_type_title), clueCheckInfo.getClueSmallTypeName()));
                    }
                    if (!TextUtils.isEmpty(clueCheckInfo.getClueMiniTypeName())) {
                        mLayoutMore.addView(createLinearView("", getString(R.string.clue_child_type_title), clueCheckInfo.getClueMiniTypeName()));
                    }
                    if (!TextUtils.isEmpty(clueCheckInfo.getRemark())) {
                        mLayoutMore.addView(createLinearView("", getString(R.string.clue_result_explain_title), clueCheckInfo.getRemark()));
                    }
                }
            }
            if (BuildConfig.HAS_SCORE) {
                if (mPreference.getRole() != Constants.ROLE_MANAGER) {
                    mLayoutMore.addView(createLinearView("", getString(R.string.clue_result_score_title), clueInfo.getClueScore() + "分"));
                }
            }
            mLayoutMore.addView(createLinearView("", getString(R.string.clue_result_time_title), DateUtils.formatTime(dealTime)));
        }

        //设置反馈按钮显示隐藏
        if (clueStatus == CLUE_STATUS_HANDLED || clueStatus == CLUE_STATUS_UN_PASSED) {
            mTvFeedback.setVisibility(View.VISIBLE);
        } else {
            mTvFeedback.setVisibility(View.GONE);
        }

        //处理反馈
        ClueCheckInfo feedbackInfo = info.getFeedBackClueStatus();
        if (feedbackInfo != null) {
            mLayoutMore.addView(createLinearView(getString(R.string.clue_feedback_title), getString(R.string.clue_feedback_title), feedbackInfo.getRemark()));
            mLayoutMore.addView(createLinearView("", getString(R.string.clue_feedback_time_title), DateUtils.formatTime(feedbackInfo.getDealTime())));
            if (info.getFeedBackFileModelList() != null && info.getFeedBackFileModelList().size() > 0) {
                GridImageView gridImageView = new GridImageView(this);
                gridImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                List<ThumbInfo> thumbList = new ArrayList<>();
                for (FileModel model : info.getFeedBackFileModelList()) {
                    thumbList.add(new ThumbInfo(model));
                }
                gridImageView.clearData();
                gridImageView.addData(thumbList);
                mLayoutMore.addView(gridImageView);
            }
        }
    }

    /**
     * 创建LinearView
     *
     * @param catalog 目录
     * @param title   标题
     * @param text    内容
     * @return LinearView
     */
    private LinearView createLinearView(String catalog, String title, String text) {
        LinearView view = new LinearView(this);
        view.setCatalog(catalog).setTitle(title).setText(text).setDetail(true).rebuildView();
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    @Override
    public void getClueDetailComplete(ClueDetailInfo info) {
        hideLoading();
        updateUI(info);
    }

}
