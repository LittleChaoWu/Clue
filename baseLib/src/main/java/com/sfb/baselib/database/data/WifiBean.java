package com.sfb.baselib.database.data;

import com.google.gson.annotations.Expose;
import com.sfb.baselib.data.base.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * WiFi表结构（府谷才会用到）
 */
@Entity(nameInDb = "wifi_table")
public class WifiBean extends BaseBean {

    @Id
    @Property(nameInDb = "_id")
    @Expose
    private Long id;
    @Property(nameInDb = "mac")
    @Expose
    private String mac;//mac地址
    @Property(nameInDb = "name")
    @Expose
    private String name;//wifi名
    @Property(nameInDb = "level")
    @Expose
    private int level;//信号强度
    @Property(nameInDb = "longitude")
    @Expose
    private String lon;//经度
    @Property(nameInDb = "latitude")
    @Expose
    private String lat;//纬度
    @Property(nameInDb = "address")
    @Expose
    private String collAddr;//位置
    @Property(nameInDb = "radius")
    @Expose
    private float radius;//定位精度
    @Property(nameInDb = "coordinate")
    @Expose
    private String coordinate;//坐标类型
    @Property(nameInDb = "create_time")
    @Expose
    private long createdTime;//采集时间
    @Property(nameInDb = "telephone")
    private String telephone;//采集人手机号

    @Generated(hash = 1817474442)
    public WifiBean(Long id, String mac, String name, int level, String lon,
            String lat, String collAddr, float radius, String coordinate,
            long createdTime, String telephone) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.level = level;
        this.lon = lon;
        this.lat = lat;
        this.collAddr = collAddr;
        this.radius = radius;
        this.coordinate = coordinate;
        this.createdTime = createdTime;
        this.telephone = telephone;
    }

    @Generated(hash = 648724433)
    public WifiBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCollAddr() {
        return this.collAddr;
    }

    public void setCollAddr(String collAddr) {
        this.collAddr = collAddr;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
