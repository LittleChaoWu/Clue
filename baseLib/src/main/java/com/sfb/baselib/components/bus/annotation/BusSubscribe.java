package com.sfb.baselib.components.bus.annotation;


import com.sfb.baselib.components.bus.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusSubscribe {
    ThreadMode threadMode() default ThreadMode.MAIN;
}
