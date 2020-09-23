package com.sfb.baselib.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sfb.baselib.R;
import com.sfb.baselib.data.CityInfo;
import com.sfb.baselib.data.ProvinceInfo;
import com.sfb.baselib.utils.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AreaDialog {

    private Activity activity;
    private String title;

    private OptionsPickerView mOptionsPickerView;

    private ArrayList<ProvinceInfo> provinceList = new ArrayList<>();//省列表（第一级）
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();//该省的城市列表（第二级）
    private ArrayList<ArrayList<ArrayList<String>>> districtList = new ArrayList<>();//该省的所有地区列表（第三极）

    private OnSelectListener onSelectListener;

    public AreaDialog(Activity activity, String title, OnSelectListener onSelectListener) {
        this.activity = activity;
        this.title = title;
        this.onSelectListener = onSelectListener;
    }

    /**
     * 显示地区选择器
     */
    public void show() {
        CommonUtils.hideKeyBoard(activity);
        if (mOptionsPickerView == null) {
            String province = activity.getString(R.string.province);
            String city = activity.getString(R.string.city);
            String district = activity.getString(R.string.district);

            int provincePos = 0;
            int cityPos = 0;
            int districtPos = 0;

            Type listType = new TypeToken<List<ProvinceInfo>>() {
            }.getType();
            provinceList = new Gson().fromJson(readAssetJson(activity, "area.json"), listType);

            for (int i = 0; i < provinceList.size(); i++) {
                ProvinceInfo provinceInfo = provinceList.get(i);
                if (provinceInfo.getName().equals(province)) {
                    provincePos = i;
                }
                ArrayList<String> areaList2 = new ArrayList<>();
                ArrayList<ArrayList<String>> areaList3 = new ArrayList<>();
                for (int j = 0; j < provinceInfo.getCity().size(); j++) {
                    CityInfo cityInfo = provinceInfo.getCity().get(j);
                    if (cityInfo.getName().equals(city)) {
                        cityPos = j;
                        districtPos = cityInfo.getDistrict().indexOf(district);
                    }
                    areaList2.add(!TextUtils.isEmpty(cityInfo.getName()) ? cityInfo.getName() : "");
                    areaList3.add(cityInfo.getDistrict());
                }
                cityList.add(areaList2);
                districtList.add(areaList3);
            }
            mOptionsPickerView = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (onSelectListener != null) {
                        String province = provinceList.get(options1).getName();
                        String city = provinceList.get(options1).getCity().get(options2).getName();
                        String district = provinceList.get(options1).getCity().get(options2).getDistrict().get(options3);
                        onSelectListener.onSelect(province, city, district);
                    }
                }
            }).setTitleText(title)
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .setDecorView((ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content))
                    .build();
            mOptionsPickerView.setPicker(provinceList, cityList, districtList);
            mOptionsPickerView.setSelectOptions(provincePos, cityPos, districtPos);
        }
        mOptionsPickerView.show();
    }

    private String readAssetJson(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelect(String province, String city, String district);
    }

}
