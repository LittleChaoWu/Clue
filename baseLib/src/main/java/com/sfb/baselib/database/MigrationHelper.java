package com.sfb.baselib.database;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;


import com.sfb.baselib.database.data.DaoMaster;
import com.sfb.baselib.utils.LogUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigrationHelper {

    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper instance;

    static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    @SafeVarargs
    final void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        dropMigrateTable(db, daoClasses);
        DaoMaster.createAllTables(db, true);
        restoreData(db, daoClasses);
    }

    private void dropMigrateTable(Database db, Class<? extends AbstractDao<?, ?>>[] daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            String sql = "DROP TABLE IF EXISTS " + "\"" + tableName + "\"";
            db.execSQL(sql);
        }
    }

    @SafeVarargs
    private final void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);

            //判断当前表是否存在
            if (!tableExist(db, daoConfig.tablename)) {
                continue;
            }

            String divider = "";
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            ArrayList<String> properties = new ArrayList<>();

            StringBuilder createTableStringBuilder = new StringBuilder();

            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName);

                    String type = null;

                    try {
                        type = getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception exception) {
                        LogUtils.logException2File("create new db table occur exception : ", exception);
                    }

                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);

                    if (daoConfig.properties[j].primaryKey) {
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }

                    divider = ",";
                }
            }
            createTableStringBuilder.append(");");

            db.execSQL(createTableStringBuilder.toString());

            String insertTableStringBuilder =
                    "INSERT INTO " + tempTableName + " (" + TextUtils.join(",", properties) + ") SELECT " + TextUtils.join(",", properties) + " FROM "
                            + tableName + ";";

            db.execSQL(insertTableStringBuilder);
        }
    }

    @SafeVarargs
    private final void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);

            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");

            //判断当前表是否存在
            if (!tableExist(db, tableName)) {
                continue;
            } else if (!tableExist(db, tempTableName)) {
                continue;
            }

            List<String> properties = new ArrayList<>();
            List<String> tempProperties = new ArrayList<>();
            List<Property> propertyList = new ArrayList<>();

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                    tempProperties.add(columnName);
                } else {
                    propertyList.add(daoConfig.properties[j]);
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < propertyList.size(); i++) {
                properties.add(propertyList.get(i).columnName);
                String defaultValue = getDefaultValue(propertyList.get(i).type);
                tempProperties.add(defaultValue);
            }
            builder.append("INSERT INTO " + tableName + " (" + TextUtils.join(",", properties) + ")");
            builder.append(" SELECT " + TextUtils.join(",", tempProperties) + " FROM " + tempTableName);

            db.execSQL(builder.toString());
            db.execSQL("DROP TABLE " + tempTableName);
        }
    }

    private String getDefaultValue(Class<?> type) {
        if (type.equals(String.class)) {
            return null;
        } else if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class) || type.equals(int.class)) {
            return "0";
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return "0";//此处需谨慎，boolean值的默认值0-false 1-true，尼玛慎重！！！
        } else {
            return null;
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class) || type.equals(int.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return "BOOLEAN";
        }

        //        Crashlytics.logException(exception);
        throw new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columns;
    }

    /**
     * 判断数据库是否存在
     *
     * @param db
     * @param tableName
     * @return
     */
    private boolean tableExist(Database db, String tableName) {
        boolean isExist = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    isExist = true;
                }
            }
        } catch (Exception e) {
            isExist = false;
        }
        return isExist;
    }

}