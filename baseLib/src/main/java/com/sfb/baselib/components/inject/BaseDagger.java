package com.sfb.baselib.components.inject;

import android.app.Application;

import com.sfb.baselib.components.inject.component.BaseComponent;
import com.sfb.baselib.components.inject.component.DaggerBaseComponent;
import com.sfb.baselib.components.inject.model.BaseModule;


public class BaseDagger {

    private static BaseComponent baseComponent;

    public static void init(Application app) {
        baseComponent = DaggerBaseComponent.builder().baseModule(new BaseModule(app)).build();
    }

    public static BaseComponent getBaseDaggerComponent() {
        return baseComponent;
    }

}
