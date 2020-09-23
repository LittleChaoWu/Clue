package com.sfb.baselib.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Activity生命周期管理器
 */
public class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {

    private List<Activity> mActivityList = Collections.synchronizedList(new LinkedList<Activity>());
    private List<Activity> mResumeActivityList = Collections.synchronizedList(new LinkedList<Activity>());
    private static ActivityLifecycleManager mInstance;

    private ActivityLifecycleManager() {
    }

    public static ActivityLifecycleManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityLifecycleManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityLifecycleManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mResumeActivityList.add(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mResumeActivityList.remove(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
    }

    /**
     * 按名称结束活动
     *
     * @param activitySimpleName 需要结束的活动名称
     */
    public void finishActivity(String activitySimpleName) {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        for (Activity activity1 : mActivityList) {
            if (activitySimpleName.equalsIgnoreCase(activity1.getClass().getSimpleName())) {
                activity1.finish();
                return;
            }
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return null;
        }
        return mActivityList.get(mActivityList.size() - 1);
    }

    /**
     * 遍历所有Activity并finish，最后结束进程
     */
    public void exit() {
        try {
            for (Activity activity : mActivityList) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * 遍历所有Activity并finish(不结束进程)
     */
    public void finishActivities() {
        try {
            for (Activity activity : mActivityList) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取app当前是否在前台工作
     */
    public boolean isAppForeGround() {
        return mResumeActivityList.size() != 0;
    }

    /**
     * 获取app当前是否在后台
     */
    public boolean isAppBackGround() {
        return mResumeActivityList.size() == 0;
    }
}
