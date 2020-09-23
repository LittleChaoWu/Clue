package com.sfb.baselib.components.bus;


import com.sfb.baselib.components.bus.annotation.BusSubscribe;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class Bus {

    //包含所有bus
    private ConcurrentHashMap<Object, BusProcessor<Object>> busMap = new ConcurrentHashMap<>();
    //事件key对应的Disposable集合
    private ConcurrentHashMap<Object, List<Disposable>> disposablesOfKey = new ConcurrentHashMap<>();
    //单个Context下包含的Disposable集合
    private ConcurrentHashMap<Object, List<ContextDisposable>> disposablesOfContext = new ConcurrentHashMap<>();

    private static Bus mBus;

    private Bus() {
    }

    public static Bus getInstance() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }

    /**
     * 注册事件
     *
     * @param object 上下文
     */
    public void register(Object object) {
        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getAnnotations() != null) {
                if (method.isAnnotationPresent(BusSubscribe.class)) {
                    BusSubscribe annotation = method.getAnnotation(BusSubscribe.class);
                    Scheduler threadMode = ThreadMode.getScheduler(annotation.threadMode());
                    Type[] types = method.getGenericParameterTypes();
                    String key = null;
                    if (types.length != 1) {
                        return;
                    } else {
                        String start = "class";
                        key = types[0].toString().substring(start.length()).trim();
                    }
                    //事件类型
                    if (!busMap.containsKey(key)) {
                        //事件处理器
                        FlowableProcessor<Object> processor = PublishProcessor.create().toSerialized();
                        BusProcessor busProcessor = new BusProcessor(processor);
                        busMap.put(key, busProcessor);
                    }
                    handleEvent(object, method, threadMode, key);
                }
            }
        }
    }

    /**
     * 处理事件
     *
     * @param object     上下文
     * @param method     响应函数
     * @param threadMode 线程模式
     * @param key        事件类型
     */
    private void handleEvent(final Object object, final Method method, Scheduler threadMode, String key) {
        final BusProcessor<Object> bus = busMap.get(key);
        if (bus == null) {
            return;
        }
        Disposable disposable = bus.getFlowableProcessor().observeOn(threadMode).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object obj) throws Exception {
                        method.invoke(object, bus.getEvent());
                    }
                });
        ContextDisposable contextDisposable = new ContextDisposable(key, disposable);
        collectDisposable(object, contextDisposable);
    }

    /**
     * 收集当前上下文注册的bus
     *
     * @param object            上下文
     * @param contextDisposable ContextDisposable
     */
    private void collectDisposable(Object object, ContextDisposable contextDisposable) {
        Class<?> cls = object.getClass();
        String tag = cls.getName();

        String key = contextDisposable.key;
        Disposable disposable = contextDisposable.disposable;

        //添加当前上下文下的Disposable
        if (disposablesOfContext.containsKey(tag)) {
            if (!disposablesOfContext.get(tag).contains(disposable)) {
                disposablesOfContext.get(tag).add(contextDisposable);
            }
        } else {
            List<ContextDisposable> contextDisposables = new ArrayList<>();
            contextDisposables.add(contextDisposable);
            disposablesOfContext.put(tag, contextDisposables);
        }

        //添加key对应的Disposable
        if (disposablesOfKey.containsKey(key)) {
            if (!disposablesOfKey.get(key).contains(disposable)) {
                disposablesOfKey.get(key).add(disposable);
            }
        } else {
            List<Disposable> disposables = new ArrayList<>();
            disposables.add(disposable);
            disposablesOfKey.put(key, disposables);
        }
    }

    /**
     * 注销时间
     *
     * @param object 上下文
     */
    public void unregister(Object object) {
        Class<?> cls = object.getClass();
        String tag = cls.getName();
        if (disposablesOfContext.containsKey(tag)) {
            // 清除上下文对应的Disposable
            List<ContextDisposable> contextDispList = disposablesOfContext.get(tag);
            for (int i = 0; i < contextDispList.size(); i++) {
                ContextDisposable contextDisposable = contextDispList.get(i);
                contextDisposable.disposable.dispose();

                //清除key对应的Disposable
                List<Disposable> disposableList = disposablesOfKey.get(contextDisposable.key);
                disposableList.remove(contextDisposable.disposable);
                if (disposableList.size() == 0) {
                    disposablesOfKey.remove(contextDisposable.key);
                    busMap.get(contextDisposable.key).getFlowableProcessor().onComplete();
                    busMap.remove(contextDisposable.key);
                }
            }
            contextDispList.clear();
            disposablesOfContext.remove(tag);
        }
    }

    /**
     * 发送事件
     *
     * @param event 事件
     */
    public void post(Object event) {
        //事件处理器
        BusProcessor<Object> bus = null;
        //事件类型
        String key = event.getClass().getName();
        if (busMap.containsKey(key)) {
            bus = busMap.get(key);
        } else {
            return;
        }
        bus.setEvent(event);
        bus.getFlowableProcessor().onNext(event);
    }
}
