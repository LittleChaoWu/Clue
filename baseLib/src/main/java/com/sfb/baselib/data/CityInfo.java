package com.sfb.baselib.data;


import com.sfb.baselib.data.base.BaseBean;

import java.util.ArrayList;

public class CityInfo extends BaseBean {

    private String name;

    private ArrayList<String> district;

    public String getName() {
        return name;
    }

    public ArrayList<String> getDistrict() {
        return district;
    }
}
