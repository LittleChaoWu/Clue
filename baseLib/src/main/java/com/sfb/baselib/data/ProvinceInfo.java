package com.sfb.baselib.data;

import com.contrarywind.interfaces.IPickerViewData;
import com.sfb.baselib.data.base.BaseBean;

import java.util.ArrayList;

public class ProvinceInfo extends BaseBean implements IPickerViewData {

    private String name;

    private ArrayList<CityInfo> city;

    public String getName() {
        return name;
    }

    public ArrayList<CityInfo> getCity() {
        return city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
