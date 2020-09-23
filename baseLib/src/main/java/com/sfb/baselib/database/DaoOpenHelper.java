package com.sfb.baselib.database;

import android.content.Context;


import com.sfb.baselib.database.data.CollectRecordBeanDao;
import com.sfb.baselib.database.data.DaoMaster;
import com.sfb.baselib.database.data.PatrolInfoDao;
import com.sfb.baselib.database.data.WifiBeanDao;

import org.greenrobot.greendao.database.Database;

public class DaoOpenHelper extends DaoMaster.DevOpenHelper {

    public DaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < 800) {
            updateTo800(db);
        }
        if (oldVersion < 1000) {
            updateTo1000(db);
        }
        if (oldVersion < 4000) {
            updateTo4000(db);
        }
        if (oldVersion < 4900) {
            updateTo4900(db);
        }
        if (oldVersion < 5000) {
            updateTo5000(db);
        }
        if (oldVersion < 5200) {
            updateTo5200(db);
        }
        if (oldVersion < 5300) {
            updateTo5300(db);
        }
        if (oldVersion < 5700) {
            updateTo5700(db);
        }
        if (oldVersion < 5800) {
            updateTo5800(db);
        }
    }

    /**
     * 更新数据库版本到800
     * 更新内容：
     * 1.采集表新增attach_data字段
     *
     * @param db 数据库
     */
    private void updateTo800(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }


    /**
     * 更新数据库版本到1000
     * 更新内容：
     * 1.增加巡逻表
     *
     * @param db 数据库
     */
    private void updateTo1000(Database db) {
        MigrationHelper.getInstance().migrate(db, PatrolInfoDao.class);
    }

    /**
     * 更新数据库版本到4000
     * 更新内容：
     * 1.采集表新增task_data字段
     *
     * @param db 数据库
     */
    private void updateTo4000(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }

    /**
     * 更新数据库版本到4900
     * 更新内容：
     * 1.采集表新增task_id字段
     *
     * @param db 数据库
     */
    private void updateTo4900(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }

    /**
     * 更新数据库版本到5000
     * 更新内容：
     * 1.采集表新增record_role字段
     *
     * @param db 数据库
     */
    private void updateTo5000(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }

    /**
     * 更新数据库版本到5200
     * 更新内容：
     * 1.采集表新增is_cache字段
     *
     * @param db 数据库
     */
    private void updateTo5200(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }

    /**
     * 更新数据库版本到5300
     * 更新内容：
     * 1.采集表新增url字段
     *
     * @param db 数据库
     */
    private void updateTo5300(Database db) {
        MigrationHelper.getInstance().migrate(db, CollectRecordBeanDao.class);
    }

    /**
     * 更新数据库版本到5700
     * 更新内容：
     * 1.新增wifi表
     *
     * @param db 数据库
     */
    private void updateTo5700(Database db) {
        MigrationHelper.getInstance().migrate(db, WifiBeanDao.class);
    }

    /**
     * 更新数据库版本到5800
     * 更新内容：
     * 1.修改wifi表：删除user_id字段，添加telephone字段
     *
     * @param db 数据库
     */
    private void updateTo5800(Database db) {
        MigrationHelper.getInstance().migrate(db, WifiBeanDao.class);
    }

}
