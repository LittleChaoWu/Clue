package com.sfb.baselib.ui.map;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.sfb.baselib.R;
import com.sfb.baselib.popup.MapPopupView;
import com.sfb.baselib.route.ActivityRoute;
import com.sfb.baselib.ui.base.BaseActivity;
import com.sfb.baselib.ui.mvp.BasePresenter;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.PositionUtil;

@Route(path = ActivityRoute.MAP_PATH)
public class MapActivity extends BaseActivity {

    public static final String GPS_KEY = "gps";
    public static final String ADDRESS_KEY = "address";

    private final String MARKER_EXTRA_KEY = "address";

    private MapView mMapView;

    @Autowired
    public String gps;
    @Autowired
    public String address;

    private double latitude;
    private double longitude;

    private BaiduMap baiduMap;

    private MapPopupView popup;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mARouter.inject(this);
        //初始化数据
        initData();
        //初始化视图
        initView();
        //初始化地图
        initMap();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!TextUtils.isEmpty(gps)) {
            String[] loc = gps.split(",");
            longitude = Double.parseDouble(loc[0]);
            latitude = Double.parseDouble(loc[1]);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mMapView = findViewById(R.id.mMapView);
        popup = new MapPopupView(this);
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        baiduMap = mMapView.getMap();
        baiduMap.setTrafficEnabled(false);//设置是否显示交通
        baiduMap.setBuildingsEnabled(true);//设置显示建筑物
        UiSettings uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);//设置是否显示指南针
        mMapView.removeViewAt(1);// 不显示百度地图Logo
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        LatLng latLng = PositionUtil.GPS2baidu(latitude, longitude);
        //在地图上定位
//        CoordinateConverter
        locationInMap(latLng.latitude, latLng.longitude, address);
        //设置Marker点击事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //显示MapPopup
                showMapPopup(marker);
                //移动地图
                moveMap(marker.getPosition().latitude, marker.getPosition().longitude, true);
                return false;
            }
        });
    }

    /**
     * 显示MapPopup
     *
     * @param marker Marker
     */
    private void showMapPopup(Marker marker) {
        Bundle bundle = marker.getExtraInfo();
        String info = bundle.getString(MARKER_EXTRA_KEY);
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;
        popup.setText(info, false);
        LatLng latLng = new LatLng(latitude, longitude);
        //显示InfoWindow，-47是偏移量，使InfoWindow向上偏移，不会挡住marker
        InfoWindow infoWindow = new InfoWindow(popup, latLng, -CommonUtils.dp2px(this, 48));
        baiduMap.showInfoWindow(infoWindow);
    }

    /**
     * 在地图上定位
     */
    private void locationInMap(double latitude, double longitude, String address) {
        if (latitude != 0 || longitude != 0) {
            baiduMap.clear();
            //设置显示标记
            showMarker(latitude, longitude, address);
            //移动地图
            moveMap(latitude, longitude, false);
        }
    }

    private void showMarker(double latitude, double longitude, String address) {
        baiduMap.hideInfoWindow();
        final LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_marker));
        OverlayOptions overlayOptions = new MarkerOptions()
                .icon(bitmap)
                .position(latLng)
                .zIndex(9);
        Marker marker = (Marker) baiduMap.addOverlay(overlayOptions);
        Bundle bundle = new Bundle();
        bundle.putString(MARKER_EXTRA_KEY, address);
        marker.setExtraInfo(bundle);
    }

    /**
     * 移动地图
     */
    private void moveMap(final double latitude, final double longitude, final boolean hasAnim) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLng latLng = new LatLng(latitude, longitude);
                //设置地图显示当前位置
                MapStatus.Builder mMapStatusBuilder = new MapStatus.Builder();
                MapStatus mMapStatus = mMapStatusBuilder.zoom(17).target(latLng).build();
                MapStatusUpdate mMapUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                if (hasAnim) {
                    baiduMap.animateMapStatus(mMapUpdate);
                } else {
                    baiduMap.setMapStatus(mMapUpdate);
                }
            }
        }, hasAnim ? 300 : 0);
    }

}
