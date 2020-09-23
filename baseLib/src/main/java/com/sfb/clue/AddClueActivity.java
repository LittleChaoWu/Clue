package com.sfb.clue;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.sfb.baselib.components.bus.annotation.BusSubscribe;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.components.bus.event.UploadErrorEvent;
import com.sfb.baselib.data.ThumbInfo;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.storage.StoragePathUtils;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.DateUtils;
import com.sfb.baselib.utils.DialogUtils;
import com.sfb.baselib.utils.LocationHelper;
import com.sfb.baselib.utils.MediaUtils;
import com.sfb.baselib.utils.PositionUtil;
import com.sfb.baselib.widget.dialog.ActionSheetDialog;
import com.sfb.baselib.widget.dialog.DialogMenuItem;
import com.sfb.baselib.widget.form.LinearView;
import com.sfb.baselib.widget.gridimage.GridImageView;
import com.sfb.baselib.widget.gridimage.ImageItemView;
import com.sfb.baselib.R;
import com.sfb.clue.contract.AddClueContract;
import com.sfb.clue.presenter.AddCluePresenter;
import com.sfb.clue.data.ClueTypeInfo;
import com.sfb.baselib.builder.CollectRecordBuilder;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.ui.base.BaseUploadActivity;
import com.sfb.baselib.ui.cache.CacheActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加线索（我要举报）界面
 */
@Route(path = ActivityRoute.ADD_CLUE_PATH)
public class AddClueActivity extends BaseUploadActivity<AddClueContract.View, AddClueContract.Presenter> implements AddClueContract.View {

    private final int TAKE_IMAGE_REQUEST_CODE = 0x110;
    private final int TAKE_VIDEO_REQUEST_CODE = 0x111;
    private final int TAKE_AUDIO_REQUEST_CODE = 0x112;

    public LinearLayout mLayoutContent;
    private LinearView mLinearBroadType;
    private LinearView mLinearType;
    private LinearView mLinearMiniType;
    private LinearView mLinearSubject;
    private LinearView mLinearDescription;
    private LinearView mLinearCollectAddress;
    private LinearView mLinearCollectAddressHide;
    private GridImageView mGridImageView;

    private LocationHelper mLocationHelper;

    private double latitude;
    private double longitude;
    private String address;

    //线索大类
    private String clueBroadType;
    private String clueBroadTypeText;
    //线索小类
    private String clueType;
    private String clueTypeText;
    //线索细项
    private String clueMiniType;
    private String clueMiniTypeText;

    private boolean hasType = false;//是否有线索小类
    private boolean hasMiniType = false;//是否有线索细项

    private List<ClueTypeInfo> clueBroadTypeList;//线索大类
    private List<ClueTypeInfo> clueTypeList;//线索小类
    private List<ClueTypeInfo> clueMiniTypeList;//线索细项

    @Override
    public AddClueContract.Presenter createPresenter() {
        return new AddCluePresenter();
    }

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
    public int getSubTaskId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cache, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_cache).setVisible(!isTask());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cache) {
            mARouter.build(ActivityRoute.CACHE_PATH)
                    .withInt(CacheActivity.COLLECT_TYPE_KEY, CollectRecordBean.CLUE_COLLECT)
                    .navigation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationHelper != null) {
            mLocationHelper.stopLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clue);
        ClueDagger.getDaggerComponent().inject(this);
        //初始化视图
        initView();
        //初始化定位
        initLocation();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mLayoutContent = findViewById(R.id.layout_content);
        mLinearBroadType = findViewById(R.id.linear_broad_type);
        mLinearType = findViewById(R.id.linear_type);
        mLinearMiniType = findViewById(R.id.linear_mini_type);
        mLinearSubject = findViewById(R.id.linear_subject);
        mLinearDescription = findViewById(R.id.linear_description);
        mLinearCollectAddress = findViewById(R.id.linear_collect_address);
        mLinearCollectAddressHide = findViewById(R.id.linear_collect_address_hide);
        mGridImageView = findViewById(R.id.mGridImageView);
        findViewById(R.id.tv_cache).setOnClickListener(this);
        findViewById(R.id.tv_upload).setOnClickListener(this);
        mLinearBroadType.setLinearClickListener(this);
        mLinearType.setLinearClickListener(this);
        mLinearMiniType.setLinearClickListener(this);
        mLinearCollectAddress.setLinearClickListener(this);
        mLinearCollectAddress.setContentViewEnable(false);
        mGridImageView.setOnAddItemClickListener(new ImageItemView.OnAddItemClickListener() {
            @Override
            public void onAddItemClick() {
                showMediaDialog();
            }
        });
        if (isTask()) {
            //更新菜单栏
            invalidateOptionsMenu();
            //隐藏缓存按钮
            findViewById(R.id.tv_cache).setVisibility(View.GONE);
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        this.latitude = Double.parseDouble(mPreference.getLatitude());
        longitude = Double.parseDouble(mPreference.getLongitude());
        address = mPreference.getAddress();
        mLocationHelper = new LocationHelper(this);
        mLocationHelper.setLocationCallback(new LocationHelper.LocationCallback() {
            @Override
            public void callback(BDLocation location, double latitude, double longitude, String address) {
                if (latitude != 0 || longitude != 0) {
                    if (!mLinearCollectAddress.isContentViewEnable()) {
                        //因为是手工输入的，避免间隔一段时间又自动获取到。当只有是点击了自动定位时才设置地址进去
                        mLinearCollectAddress.setText(address);
                    }
                    AddClueActivity.this.latitude = latitude;
                    AddClueActivity.this.longitude = longitude;
                    AddClueActivity.this.address = address;
                    mLinearCollectAddressHide.setText(address);
                } else {
                    mLinearCollectAddress.setText(getString(R.string.location_fail_tip));
                }
                mLinearCollectAddress.setContentViewEnable(true);
            }
        });
        mLocationHelper.startLocation();
    }

    @Override
    public void onLinearItemClick(int id) {
        if (id == R.id.linear_broad_type) {
            //显示线索类型对话框
            showClueBroadTypeDialog();
        } else if (id == R.id.linear_type) {
            //显示线索小类对话框
            showClueTypeDialog();
        } else if (id == R.id.linear_mini_type) {
            //显示线索细项对话框
            showClueMiniTypeDialog();
        }
    }

    @Override
    public void onFirstSuffixImageClick(int id) {
        if (id == R.id.linear_collect_address) {
            latitude = 0;
            longitude = 0;
            address = "";
            mLinearCollectAddress.setText(getString(R.string.wait_location_tip));
            //20200803需求修改：新增地址可编辑手动修改，考虑体验效果，额外增加点击重新定位时暂时不允许编辑，等待定位结束后再开放编辑
            mLinearCollectAddress.setContentViewEnable(false);
            mLocationHelper.startLocation();
        }
    }

    /**
     * 显示线索大类对话框
     */
    private void showClueBroadTypeDialog() {
        if (clueBroadTypeList != null) {
            ArrayList<String> items = new ArrayList<>();
            for (ClueTypeInfo info : clueBroadTypeList) {
                items.add(info.getValue());
            }
            DialogUtils.openDialog(this, items, new ActionSheetDialog.OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    ClueTypeInfo info = clueBroadTypeList.get(i);
                    clueBroadType = info.getKey();
                    clueBroadTypeText = info.getValue();
                    mLinearBroadType.setText(clueBroadTypeText);
                    mLinearType.setText("");
                    mLinearMiniType.setText("", true);
                    clueTypeList = info.getChildren();
                    clueType = "";
                    clueTypeText = "";
                    clueMiniType = "";
                    clueMiniTypeText = "";
                    hasMiniType = false;
                    if (clueTypeList == null || clueTypeList.isEmpty()) {
                        hasType = false;
                        mLinearType.setVisibility(View.GONE);
                    } else {
                        hasType = true;
                        mLinearType.setVisibility(View.VISIBLE);
                        //显示线索小类对话框
                        showClueTypeDialog();
                    }
                }
            });
        } else {
            mPresenter.getClueTypes();
        }
    }

    @Override
    public void getClueTypesComplete(List<ClueTypeInfo> list) {
        if (list != null && list.size() > 0) {
            clueBroadTypeList = list;
            //显示线索类型对话框
            showClueBroadTypeDialog();
        }
    }

    /**
     * 显示线索小类对话框
     */
    private void showClueTypeDialog() {
        if (TextUtils.isEmpty(clueBroadType)) {
            showToast(R.string.clue_broad_type_tip);
            return;
        }
        ArrayList<String> items = new ArrayList<>();
        for (ClueTypeInfo info : clueTypeList) {
            items.add(info.getValue());
        }
        DialogUtils.openDialog(this, items, new ActionSheetDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ClueTypeInfo info = clueTypeList.get(i);
                clueType = info.getKey();
                clueTypeText = info.getValue();
                mLinearType.setText(clueTypeText);
                mLinearMiniType.setText("");
                clueMiniType = "";
                clueMiniTypeText = "";
                clueMiniTypeList = info.getChildren();
                if (clueMiniTypeList == null || clueMiniTypeList.isEmpty()) {
                    hasMiniType = false;
                    mLinearMiniType.setVisibility(View.GONE);
                } else {
                    hasMiniType = true;
                    mLinearMiniType.setVisibility(View.VISIBLE);
                    //显示线索细项对话框
                    showClueMiniTypeDialog();
                }
            }
        });
    }

    /**
     * 显示线索细项对话框
     */
    private void showClueMiniTypeDialog() {
        if (TextUtils.isEmpty(clueBroadType)) {
            showToast(R.string.clue_broad_type_tip);
            return;
        }
        if (TextUtils.isEmpty(clueType)) {
            showToast(R.string.clue_type_tip);
            return;
        }
        ArrayList<String> items = new ArrayList<>();
        for (ClueTypeInfo info : clueMiniTypeList) {
            items.add(info.getValue());
        }
        DialogUtils.openDialog(this, items, new ActionSheetDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ClueTypeInfo info = clueMiniTypeList.get(i);
                clueMiniType = info.getKey();
                clueMiniTypeText = info.getValue();
                mLinearMiniType.setText(clueMiniTypeText);
            }
        });
    }

    /**
     * 显示媒体获取路径对话框
     */
    private void showMediaDialog() {
        ArrayList<DialogMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new DialogMenuItem(0, getString(R.string.scene_photo)));
        menuItems.add(new DialogMenuItem(1, getString(R.string.live_video)));
        menuItems.add(new DialogMenuItem(2, getString(R.string.live_audio)));
        final ActionSheetDialog dialog = new ActionSheetDialog(this);
        dialog.setMenuDatas(menuItems);
        dialog.setOnItemClickListener(new ActionSheetDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        String fileName = "add_clue_" + System.currentTimeMillis() + ".jpg";
                        String path = StoragePathUtils.getImagePath(fileName);
                        mARouter.build(ActivityRoute.IMAGE_TAKE_PATH)
                                .withString("filePath", path)
                                .navigation(AddClueActivity.this, TAKE_IMAGE_REQUEST_CODE);
                        break;
                    case 1:
                        if (requestCamera()) {
                            if (requestIO()) {
                                MediaUtils.takeVideo(AddClueActivity.this, TAKE_VIDEO_REQUEST_CODE);
                            }
                        }
                        break;
                    case 2:
                        if (requestAudio()) {
                            if (requestIO()) {
                                MediaUtils.takeAudio(AddClueActivity.this, TAKE_AUDIO_REQUEST_CODE);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void requestCameraCallback(boolean isSuccess) {
        if (isSuccess) {
            requestIO();
        } else {
            showPermissionRefusedDialog(R.string.camera_permission_request_message, false);
        }
    }

    @Override
    protected void requestAudioCallback(boolean isSuccess) {
        if (isSuccess) {
            requestIO();
        } else {
            showPermissionRefusedDialog(R.string.audio_permission_request_message, false);
        }
    }

    @Override
    protected void requestIOCallback(boolean isSuccess) {
        if (!isSuccess) {
            showPermissionRefusedDialog(R.string.io_permission_request_message, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case TAKE_IMAGE_REQUEST_CODE:
                    addThumbInfo(data.getData().getPath());
                    break;
                case TAKE_VIDEO_REQUEST_CODE:
                    saveVideoFile(data.getData());
                    break;
                case TAKE_AUDIO_REQUEST_CODE:
                    saveAudioFile(data.getData());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 保存视频文件
     *
     * @param uri Uri
     */
    private void saveVideoFile(Uri uri) {
        if (uri != null) {
            String path = null;
            if (uri.toString().startsWith("content://")) {
                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    }
                    cursor.close();
                }
            } else {
                path = uri.getPath();
            }
            if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                addThumbInfo(path);
            }
        }
    }

    private void saveAudioFile(Uri uri) {
        if (uri != null) {
            String path = null;
            if (uri.toString().startsWith("content://")) {
                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    }
                    cursor.close();
                }
            } else {
                path = uri.getPath();
            }
            if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                addThumbInfo(path);
            }
        }
    }

    /**
     * 添加数据额
     *
     * @param path String
     */
    private void addThumbInfo(String path) {
        ThumbInfo info = new ThumbInfo(path);
        mGridImageView.addData(info);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tv_cache) {//加入缓存
            addCache();
        } else if (id == R.id.tv_upload) {
            //线索对象
            ClueInfo info = checkEnable();
            //线索采集类对象
            CollectRecordBean bean = new CollectRecordBuilder(this).build(info);
            //上传检测
            uploadCheck(bean);
        }
    }

    /**
     * 检测是否可以提交
     *
     * @return ClueInfo
     */
    private ClueInfo checkEnable() {
        String subject = mLinearSubject.getText();
        String description = mLinearDescription.getText();
        if (TextUtils.isEmpty(clueBroadType)) {
            showToast(R.string.clue_broad_type_tip);
            return null;
        }
        if (hasType && TextUtils.isEmpty(clueType)) {
            showToast(R.string.clue_type_tip);
            return null;
        }
        if (hasMiniType && TextUtils.isEmpty(clueMiniType)) {
            showToast(R.string.clue_mini_type_tip);
            return null;
        }
//        if (TextUtils.isEmpty(subject)) {
//            showToast(R.string.subject_empty_tip);
//            return null;
//        }
        if (TextUtils.isEmpty(description)) {
            showToast(R.string.clue_description_empty_tip);
            return null;
        }
//        if (latitude == 0 || longitude == 0 || TextUtils.isEmpty(address)) {
//            showToast(R.string.clue_location_tip);
//            return null;
//        }
        ClueInfo info = new ClueInfo();
        //设置主题
        info.setTitle(subject);
        //设置线索描述
        info.setSummary(description);
        //设置地址
        info.setAreaName(mLinearCollectAddress.getText());
        //设置GPS
        LatLng latLng = PositionUtil.baidu2GPS(latitude, longitude);
        info.setGps(latLng.longitude + "," + latLng.latitude);
        //设置线索类型
        info.setClueBroadType(clueBroadType);
        //设置线索类型文本
        info.setClueBroadTypeName(clueBroadTypeText);
        //设置线索小类
        info.setClueType(clueType);
        //设置线索小类文本
        info.setClueTypeName(clueTypeText);
        //设置线索细项
        info.setClueMiniType(clueMiniType);
        //设置线索细项文本
        info.setClueMiniTypeName(clueMiniTypeText);
        //设置创建时间
        info.setCreatedTime(CommonUtils.getServerTime(this));
        //设置创建时间字符串
        info.setCreateTimeStr(DateUtils.formatTime(info.getCreatedTime()));
        //设置所属区编号
        info.setAreaCode(0);
        //设置文件信息
        info.setFilePaths(mGridImageView.getFilePaths());
        //设置来源
        info.setSourceType(isTask() ? 1 : 0);
        //设置子任务id
        info.setSubTaskId(getSubTaskId());
        //设置自动定位获取的地址
        info.setAddressHide(mLinearCollectAddressHide.getText());
        return info;
    }

    /**
     * 加入缓存
     */
    private void addCache() {
        ClueInfo info = checkEnable();
        if (info != null) {
            CollectRecordBean bean = new CollectRecordBuilder(this).build(info);
            if (bean != null) {
                String paths = bean.getFilepaths();
                if (mDaoHelper.getCollectRecord(paths) != null) {
                    showToast(R.string.collect_record_existed);
                } else {
                    bean.setRecordRole(CollectRecordBean.RECORD_ROLE.CACHE.ordinal());
                    mDaoHelper.saveCollectRecord(bean);
                    showToast(R.string.cache_success);
                    //重置UI
                    resetUI();
                }
            }
        }
    }

    /**
     * 重置UI
     */
    private void resetUI() {
        mCollectRecordBean = null;
        clueType = "";
        mLinearBroadType.setText("");
        mLinearType.setText("");
        mLinearMiniType.setText("");
        mLinearType.setVisibility(View.GONE);
        mLinearMiniType.setVisibility(View.GONE);
        mLinearSubject.setText("");
        mLinearDescription.setText("");
        mGridImageView.clearData();
    }

    @Override
    public void runInBackCallback() {
        resetUI();
    }

    /**
     * 记录上传成功回调
     *
     * @param event CollectRecordEvent
     */
    @BusSubscribe
    public void onEvent(CollectRecordEvent event) {
        Log.i("lbs log","记录上传成功回调 " + isUploadSuccess(event));
        if (isUploadSuccess(event)) {
            finish();
        }
    }

    @BusSubscribe
    public void onEvent(UploadErrorEvent event) {
        uploadError(event);
    }

}
