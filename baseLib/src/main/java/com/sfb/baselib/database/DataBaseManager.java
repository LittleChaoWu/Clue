package com.sfb.baselib.database;

import android.content.Context;

import com.sfb.baselib.database.data.CollectRecordBeanDao;
import com.sfb.baselib.database.data.DaoMaster;
import com.sfb.baselib.database.data.DaoSession;
import com.sfb.baselib.database.data.PatrolInfoDao;
import com.sfb.baselib.database.data.UploadInfoDao;
import com.sfb.baselib.database.data.WifiBeanDao;


public class DataBaseManager {
    private DaoOpenHelper devOpenHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static DataBaseManager mDataBaseManager;

    private DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (mDataBaseManager == null) {
            synchronized (DataBaseManager.class) {
                if (mDataBaseManager == null) {
                    mDataBaseManager = new DataBaseManager();
                }
            }
        }
        return mDataBaseManager;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        devOpenHelper = new DaoOpenHelper(context.getApplicationContext(), "cuunar_guard.db");
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取 DaoMaster
     *
     * @return DaoMaster
     */
    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 获取 DaoSession
     *
     * @return DaoSession
     */
    private DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    /**
     * 获取 CollectRecordBeanDao
     *
     * @return CollectRecordBeanDao
     */
    public CollectRecordBeanDao getCollectRecordBeanDao() {
        if (daoSession == null) {
            daoSession = getDaoSession();
        }
        return daoSession.getCollectRecordBeanDao();
    }

    /**
     * 获取 UploadInfoDao
     *
     * @return UploadInfoDao
     */
    public UploadInfoDao getUploadInfoDao() {
        if (daoSession == null) {
            daoSession = getDaoSession();
        }
        return daoSession.getUploadInfoDao();
    }

    /**
     * 获取 PatrolInfoDao
     *
     * @return PatrolInfoDao
     */
    public PatrolInfoDao getPatrolInfoDao() {
        if (daoSession == null) {
            daoSession = getDaoSession();
        }
        return daoSession.getPatrolInfoDao();
    }

    /**
     * 获取 WifiBeanDao
     *
     * @return WifiBeanDao
     */
    public WifiBeanDao getWifiBeanDao() {
        if (daoSession == null) {
            daoSession = getDaoSession();
        }
        return daoSession.getWifiBeanDao();
    }

}
