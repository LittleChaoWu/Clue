package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

public class AddressInfo extends BaseBean {

    private double lat;
    private double lon;
    private String address;

    public AddressInfo(double lat, double lon, String address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
