package com.sfb.baselib.database.data;


import com.sfb.baselib.data.base.BaseBean;

/**
 * 采集的坐标点的数据结构，可参考百度地图的BDLocation
 */
public class PatrolPointInfo extends BaseBean {

    private double lat;
    private double lon;
    private String address;
    private boolean hasSpeed;
    private int locType;
    private float speed;
    private float radius;
    private boolean hasRadius;
    private long timeStamp;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHasSpeed(boolean hasSpeed) {
        this.hasSpeed = hasSpeed;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setHasRadius(boolean hasRadius) {
        this.hasRadius = hasRadius;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getLat() {

        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }

    public boolean isHasSpeed() {
        return hasSpeed;
    }

    public int getLocType() {
        return locType;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRadius() {
        return radius;
    }

    public boolean isHasRadius() {
        return hasRadius;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
