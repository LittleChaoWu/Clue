package com.sfb.baselib.components.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {

    private Gson gson;

    @Override
    public void init(Context context) {
        gson = new Gson();
    }

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(input, clazz);
    }
}
