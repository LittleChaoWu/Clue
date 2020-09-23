package com.sfb.baselib.widget.banner.indicator;

import android.view.View;


import com.sfb.baselib.widget.banner.config.IndicatorConfig;
import com.sfb.baselib.widget.banner.listener.OnPageChangeListener;

import androidx.annotation.NonNull;

public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
