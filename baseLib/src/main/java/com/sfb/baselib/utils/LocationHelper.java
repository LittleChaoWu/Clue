package com.sfb.baselib.utils;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.pref.GuardPreference_;

public class LocationHelper {

    //定位的最小精度（精度16位，此处取15位就好）
    private static final double MIN_ACCURACY = 0.000000000000001;

    private GuardPreference_ mPreference;
    private LocationClient mLocationClient;
    private PLocationListener locationListener = new PLocationListener();

    private int scanSpan = 0;//定位间隔时间，当scanSpan==0时，表示只定位一次

    public LocationHelper(Context context) {
        initLocation(context);
    }

    public LocationHelper(Context context, int scanSpan) {
        this.scanSpan = scanSpan;
        initLocation(context);
    }

    /**
     * 初始化定位
     *
     * @param context Context 推荐使用getApplicationContext()方法获取全进程有效的Context
     */
    private void initLocation(Context context) {
        mPreference = GuardPreference_.getInstance(context);
        //声明LocationClient类
        mLocationClient = new LocationClient(context);
        //注册监听函数
        mLocationClient.registerLocationListener(locationListener);
        //初始化LocationClientOption
        initLocationClientOption();
    }

    /**
     * 初始化LocationClientOption
     */
    private void initLocationClientOption() {
        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(scanSpan * 1000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        stopLocation();
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    public class PLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int errorCode = bdLocation.getLocType();
            if (errorCode == BDLocation.TypeGpsLocation || errorCode == BDLocation.TypeNetWorkLocation) {
                double latitude = bdLocation.getLatitude();    //获取纬度信息
                double longitude = bdLocation.getLongitude();    //获取经度信息
                String address = "";
                if (!TextUtils.isEmpty(bdLocation.getCity())) {
                    address = bdLocation.getCity();
                }
                if (!TextUtils.isEmpty(bdLocation.getDistrict())) {
                    address += bdLocation.getDistrict();
                }
                if (!TextUtils.isEmpty(bdLocation.getStreet())) {
                    address += bdLocation.getStreet();
                }
                if (!TextUtils.isEmpty(bdLocation.getStreetNumber())) {
                    address += bdLocation.getStreetNumber();
                }
                //若地址为空时，说明定位有问题，不保存
                if (TextUtils.isEmpty(address)) {
                    return;
                }
                //若经纬度数值小于最小精度，说明定位经纬度有问题，不保存
                if (latitude <= MIN_ACCURACY || longitude <= MIN_ACCURACY) {
                    locationCallback.callback(null, 0, 0, "");
                    return;
                }
                mPreference.putCity(bdLocation.getCity());
                mPreference.putLatitude(String.valueOf(latitude));
                mPreference.putLongitude(String.valueOf(longitude));
                mPreference.putAddress(address);
                mPreference.putRadius(bdLocation.getRadius());
                if (locationCallback != null) {
                    locationCallback.callback(bdLocation, latitude, longitude, address);
                }
            } else {
                if (locationCallback != null) {
                    locationCallback.callback(null, 0, 0, "");
                }
            }
        }
    }

    private LocationCallback locationCallback;

    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    public interface LocationCallback {
        /**
         * 定位回调
         *
         * @param location  BDLocation
         * @param latitude  维度
         * @param longitude 经度
         * @param address   地址
         */
        void callback(BDLocation location, double latitude, double longitude, String address);
    }
}
