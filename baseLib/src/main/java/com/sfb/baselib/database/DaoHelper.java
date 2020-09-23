package com.sfb.baselib.database;

import android.content.Context;
import android.text.TextUtils;

import com.sfb.baselib.database.data.CollectRecordBeanDao;
import com.sfb.baselib.database.data.PatrolInfoDao;
import com.sfb.baselib.database.data.UploadInfoDao;
import com.sfb.baselib.database.data.WifiBeanDao;
import com.pref.GuardPreference_;
import com.sfb.baselib.data.AccountInfo;
import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.database.data.PatrolInfo;
import com.sfb.baselib.database.data.UploadInfo;
import com.sfb.baselib.database.data.WifiBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.sfb.baselib.database.data.PatrolInfo.PATROL_ONGOING;


public class DaoHelper {

    private DataBaseManager manager;
    private GuardPreference_ preference;

    private static DaoHelper daoHelper;

    private DaoHelper(Context context) {
        manager = DataBaseManager.getInstance();
        preference = GuardPreference_.getInstance(context);
    }

    public static DaoHelper getInstance(Context context) {
        if (daoHelper == null) {
            synchronized (DataBaseManager.class) {
                if (daoHelper == null) {
                    daoHelper = new DaoHelper(context);
                }
            }
        }
        return daoHelper;
    }

    /**
     * 获取CollectRecordBean上传列表
     *
     * @param pageNo   页数
     * @param pageSize 页大小
     * @return List<CollectRecordBean>
     */
    public List<CollectRecordBean> queryCollectRecord(int pageNo, int pageSize) {
        List<CollectRecordBean> list = null;
        QueryBuilder<CollectRecordBean> queryBuilder = manager.getCollectRecordBeanDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(CollectRecordBeanDao.Properties.User.eq(accountInfo.getUsername()));
        }
        list = queryBuilder.where(CollectRecordBeanDao.Properties.Filepaths.isNotNull())
                .where(CollectRecordBeanDao.Properties.Filepaths.notEq(""))
                .where(CollectRecordBeanDao.Properties.CollectType.notIn(CollectRecordBean.REGISTER,
                        CollectRecordBean.REGISTER_AGAIN,
                        CollectRecordBean.RESET_INFO,
                        CollectRecordBean.AVATAR))
                .whereOr(CollectRecordBeanDao.Properties.RecordRole.eq(CollectRecordBean.RECORD_ROLE.TRANSMIT.ordinal()),
                        CollectRecordBeanDao.Properties.RecordRole.eq(CollectRecordBean.RECORD_ROLE.CACHE_TRANSMIT.ordinal()))
                .orderDesc(CollectRecordBeanDao.Properties.SaveTime)
                .offset((pageNo - 1) * pageSize).limit(pageSize)
                .list();
        return list;
    }

    /**
     * 获取CollectRecordBean缓存列表
     *
     * @param collectType 采集类型
     * @param pageNo      页数
     * @param pageSize    页大小
     * @return List<CollectRecordBean>
     */
    public List<CollectRecordBean> queryCollectRecord(int collectType, int pageNo, int pageSize) {
        List<CollectRecordBean> list = null;
        QueryBuilder<CollectRecordBean> queryBuilder = manager.getCollectRecordBeanDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(CollectRecordBeanDao.Properties.User.eq(accountInfo.getUsername()));
        }
        list = queryBuilder.where(CollectRecordBeanDao.Properties.CollectType.eq(collectType))
                .where(CollectRecordBeanDao.Properties.UploadReportState.notEq(CollectRecordBean.STATE_OF_SUBMIT_SUCCESS))
                .whereOr(CollectRecordBeanDao.Properties.RecordRole.eq(CollectRecordBean.RECORD_ROLE.CACHE.ordinal()),
                        CollectRecordBeanDao.Properties.RecordRole.eq(CollectRecordBean.RECORD_ROLE.CACHE_TRANSMIT.ordinal()))
                .orderDesc(CollectRecordBeanDao.Properties.SaveTime)
                .offset((pageNo - 1) * pageSize).limit(pageSize)
                .list();
        return list;
    }

    /**
     * 获取采集
     *
     * @param id long
     * @return CollectRecordBean
     */
    public CollectRecordBean getCollectRecord(Long id) {
        CollectRecordBean bean = null;
        if (id != null) {
            QueryBuilder<CollectRecordBean> queryBuilder = manager.getCollectRecordBeanDao().queryBuilder();
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                queryBuilder.where(CollectRecordBeanDao.Properties.User.eq(accountInfo.getUsername()));
            }
            bean = queryBuilder.where(CollectRecordBeanDao.Properties.Id.eq(id)).unique();
        }
        return bean;
    }

    /**
     * 获取采集
     *
     * @param filePaths String
     * @return CollectRecordBean
     */
    public CollectRecordBean getCollectRecord(String filePaths) {
        CollectRecordBean bean = null;
        if (!TextUtils.isEmpty(filePaths)) {
            QueryBuilder<CollectRecordBean> queryBuilder = manager.getCollectRecordBeanDao().queryBuilder();
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                queryBuilder.where(CollectRecordBeanDao.Properties.User.eq(accountInfo.getUsername()));
            }
            bean = queryBuilder.where(CollectRecordBeanDao.Properties.Filepaths.eq(filePaths)).unique();
        }
        return bean;
    }

    /**
     * 获取准备上传的记录
     *
     * @return CollectRecordBean
     */
    public CollectRecordBean getPrepareRecord() {
        CollectRecordBean bean = null;
        bean = manager.getCollectRecordBeanDao()
                .queryBuilder()
                .where(CollectRecordBeanDao.Properties.UploadReportState.eq(CollectRecordBean.STATE_OF_UPLOADING))
                .unique();
        if (bean == null) {
            QueryBuilder<CollectRecordBean> queryBuilder = manager.getCollectRecordBeanDao().queryBuilder();
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                queryBuilder.where(CollectRecordBeanDao.Properties.User.eq(accountInfo.getUsername()));
            }
            List<CollectRecordBean> list = queryBuilder
                    .where(CollectRecordBeanDao.Properties.UploadReportState.eq(CollectRecordBean.STATE_OF_WAITING))
                    .list();
            bean = (list != null && list.size() > 0) ? list.get(0) : null;
        }
        return bean;
    }

    /**
     * 存储采集记录
     *
     * @param bean CollectRecordBean
     */
    public void saveCollectRecord(CollectRecordBean bean) {
        if (bean != null) {
            String paths = bean.getFilepaths();
            CollectRecordBean tempBean = getCollectRecord(paths);
            //通过附件路径查找
            if (tempBean != null) {
                bean.setId(tempBean.getId());
                manager.getCollectRecordBeanDao().update(bean);
            }//通过id查找
            else if (getCollectRecord(bean.getId()) != null) {
                manager.getCollectRecordBeanDao().update(bean);
            } else {
                manager.getCollectRecordBeanDao().insert(bean);
            }
        }
    }

    /**
     * 删除采集记录
     *
     * @param bean CollectRecordBean
     */
    public void deleteCollectRecord(CollectRecordBean bean) {
        if (bean != null) {
            if (getCollectRecord(bean.getFilepaths()) != null) {
                if (bean.getUploadReportState() == CollectRecordBean.STATE_OF_UPLOADING) {
                    bean.setRecordRole(CollectRecordBean.RECORD_ROLE.TRANSMIT.ordinal());
                    manager.getCollectRecordBeanDao().update(bean);
                } else {
                    manager.getCollectRecordBeanDao().delete(bean);
                }
            } else {
                manager.getCollectRecordBeanDao().delete(bean);
            }
        }
    }

    /**
     * 修改采集记录的上传状态
     *
     * @param bean CollectRecordBean
     */
    public void updateCollectRecordStatus(CollectRecordBean bean) {
        if (bean != null) {
            CollectRecordBean recordBean = manager.getCollectRecordBeanDao()
                    .queryBuilder()
                    .where(CollectRecordBeanDao.Properties.UploadReportState.eq(CollectRecordBean.STATE_OF_UPLOADING))
                    .unique();
            if (recordBean != null) {
                //先暂停正在上传的项
                recordBean.setUploadReportState(CollectRecordBean.STATE_OF_PAUSE);
                manager.getCollectRecordBeanDao().update(recordBean);
            }
            manager.getCollectRecordBeanDao().update(bean);
        }
    }

    /**
     * 存储上传信息
     *
     * @param info UploadInfo
     */
    public void saveUploadInfo(UploadInfo info) {
        if (info != null) {
            if (getUploadInfoById(info.getRowKey()) != null) {
                manager.getUploadInfoDao().update(info);
            } else {
                manager.getUploadInfoDao().insert(info);
            }
        }
    }

    /**
     * 根据FileId获取UploadInfo
     *
     * @param fileId String
     * @return UploadInfo
     */
    public UploadInfo getUploadInfoById(String fileId) {
        UploadInfo info = null;
        if (!TextUtils.isEmpty(fileId)) {
            QueryBuilder<UploadInfo> queryBuilder = manager.getUploadInfoDao().queryBuilder();
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                queryBuilder.where(UploadInfoDao.Properties.User.eq(accountInfo.getUsername()));
            }
            info = queryBuilder.where(UploadInfoDao.Properties.RowKey.eq(fileId)).unique();
        }
        return info;
    }

    /**
     * 根据FileId获取UploadInfo
     *
     * @param path String
     * @return UploadInfo
     */
    public UploadInfo getUploadInfoByPath(String path) {
        UploadInfo info = null;
        if (!TextUtils.isEmpty(path)) {
            QueryBuilder<UploadInfo> queryBuilder = manager.getUploadInfoDao().queryBuilder();
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                queryBuilder.where(UploadInfoDao.Properties.User.eq(accountInfo.getUsername()));
            }
            info = queryBuilder.where(UploadInfoDao.Properties.FilePath.eq(path)).unique();
        }
        return info;
    }

    /**
     * 获取 PatrolInfo
     *
     * @return PatrolInfo
     */
    public PatrolInfo getPatrolInfo(int subTaskId) {
        PatrolInfo info = null;
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        }
        info = queryBuilder.where(PatrolInfoDao.Properties.TaskId.eq(subTaskId)).unique();
        return info;
    }

    /**
     * 添加 PatrolInfo
     *
     * @param info PatrolInfo
     * @return 是否添加成功
     */
    public boolean savePatrolInfo(PatrolInfo info) {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        } else {
            return false;
        }
        PatrolInfo patrolInfo = queryBuilder.where(PatrolInfoDao.Properties.TaskId.eq(info.getTaskId())).unique();
        if (patrolInfo == null) {
            manager.getPatrolInfoDao().insert(info);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改 PatrolInfo
     *
     * @param info PatrolInfo
     * @return 是否修改成功
     */
    public boolean updatePatrolInfo(PatrolInfo info) {
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        }
        PatrolInfo patrolInfo = queryBuilder.where(PatrolInfoDao.Properties.TaskId.eq(info.getId())).unique();
        if (patrolInfo != null) {
            manager.getPatrolInfoDao().update(info);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除 PatrolInfo
     *
     * @param subTaskId int
     * @return 是否删除成功
     */
    public boolean deletePatrolInfo(int subTaskId) {
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        }
        PatrolInfo patrolInfo = queryBuilder.where(PatrolInfoDao.Properties.TaskId.eq(subTaskId)).unique();
        if (patrolInfo != null) {
            manager.getPatrolInfoDao().delete(patrolInfo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除 PatrolInfo
     *
     * @param info PatrolInfo
     * @return 是否删除成功
     */
    public boolean deletePatrolInfo(PatrolInfo info) {
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        }
        PatrolInfo patrolInfo = queryBuilder.where(PatrolInfoDao.Properties.TaskId.eq(info.getId())).unique();
        if (patrolInfo != null) {
            manager.getPatrolInfoDao().delete(info);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否有正在执行的巡逻
     *
     * @return boolean
     */
    public boolean hasPatrolOnGoing() {
        QueryBuilder<PatrolInfo> queryBuilder = manager.getPatrolInfoDao().queryBuilder();
        AccountInfo accountInfo = preference.getAccountInfo();
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
            queryBuilder.where(PatrolInfoDao.Properties.Username.eq(accountInfo.getUsername()));
        }
        PatrolInfo patrolInfo = queryBuilder
                .where(PatrolInfoDao.Properties.Status.eq(PATROL_ONGOING))
                .unique();
        return patrolInfo != null;
    }

    /**
     * 保存wifi信息
     *
     * @param wifiBean WifiBean
     */
    public void saveWifi(WifiBean wifiBean) {
        if (wifiBean != null) {
            WifiBeanDao dao = manager.getWifiBeanDao();
            WifiBean temp = dao.queryBuilder().where(WifiBeanDao.Properties.Mac.eq(wifiBean.getMac())).unique();
            if (temp == null) {
                dao.save(wifiBean);
            } else {
                wifiBean.setId(temp.getId());
                dao.update(wifiBean);
            }
        }
    }


    /**
     * 获取wifi信息
     *
     * @return List<WifiBean>
     */
    public List<WifiBean> getWifiList() {
        UserInfo userInfo = preference.getUserInfo();
        String telephone = "";
        if (userInfo != null) {
            telephone = userInfo.getTelephone();
        }
        if (TextUtils.isEmpty(telephone)) {
            return null;
        }
        return manager.getWifiBeanDao().queryBuilder()
                .where(WifiBeanDao.Properties.Telephone.eq(telephone))
                .list();
    }

    /**
     * 删除wifi信息
     */
    public void deleteWifiList() {
        List<WifiBean> list = getWifiList();
        if (list != null && !list.isEmpty()) {
            for (WifiBean bean : list) {
                manager.getWifiBeanDao().delete(bean);
            }
        }
    }


}
