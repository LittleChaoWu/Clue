package com.sfb.baselib.builder;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sfb.baselib.data.AccountInfo;
import com.sfb.baselib.data.NoticeInfo;
import com.sfb.baselib.data.RegisterInfo;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.database.data.CollectRecordBean.*;
import com.sfb.baselib.database.data.UploadInfo;
import com.sfb.baselib.utils.CommonUtils;
import com.sfb.baselib.utils.DateUtils;
import com.sfb.baselib.utils.FileHelper;
import com.sfb.baselib.R;
import com.sfb.baselib.data.*;
import com.sfb.baselib.data.AvatarInfo;
import com.sfb.baselib.data.CarInfo;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.data.GasInfo;
import com.sfb.baselib.data.LegalCaseInfo;
import com.sfb.baselib.data.TaskInfo;
import com.sfb.baselib.data.TaskReportInfo;
import com.sfb.baselib.data.TenantInfo;
import com.pref.GuardPreference_;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sfb.baselib.database.data.CollectRecordBean.*;
import static com.sfb.baselib.database.data.CollectRecordBean.ADD_RENTAL_PERSON;
import static com.sfb.baselib.database.data.CollectRecordBean.AVATAR;
import static com.sfb.baselib.database.data.CollectRecordBean.CAR_COLLECT;
import static com.sfb.baselib.database.data.CollectRecordBean.CASE_INPUT;
import static com.sfb.baselib.database.data.CollectRecordBean.CLUE_ASSIGN;
import static com.sfb.baselib.database.data.CollectRecordBean.CLUE_COLLECT;
import static com.sfb.baselib.database.data.CollectRecordBean.CLUE_FEEDBACK;
import static com.sfb.baselib.database.data.CollectRecordBean.DISPOSE_CLUE;
import static com.sfb.baselib.database.data.CollectRecordBean.GAS_COLLECT;
import static com.sfb.baselib.database.data.CollectRecordBean.PERSONNEL_COLLECT;
import static com.sfb.baselib.database.data.CollectRecordBean.PUBLISH_NOTICE;
import static com.sfb.baselib.database.data.CollectRecordBean.PUBLISH_TASK;
import static com.sfb.baselib.database.data.CollectRecordBean.REGISTER;
import static com.sfb.baselib.database.data.CollectRecordBean.REGISTER_AGAIN;
import static com.sfb.baselib.database.data.CollectRecordBean.RESET_INFO;
import static com.sfb.baselib.database.data.CollectRecordBean.STATE_OF_WAITING;
import static com.sfb.baselib.database.data.CollectRecordBean.TASK_REPORT;

public class CollectRecordBuilder {

    protected Context context;
    protected GuardPreference_ preference;
    protected Gson gson;

    public CollectRecordBuilder(Context context) {
        this.context = context;
        this.preference = GuardPreference_.getInstance(context);
        this.gson = new Gson();
    }

    /**
     * 构建车辆采集类型采集对象
     */
    public CollectRecordBean build(CarInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(CAR_COLLECT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setCarNum(info.getPlate_num());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getPlate_num());
            bean.setContent(info.getRemark());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建线索采集类型采集对象
     */
    public CollectRecordBean build(ClueInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(CLUE_COLLECT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject("线索举报 " + DateUtils.formatTime(CommonUtils.getServerTime(context)));
            bean.setContent(info.getSummary());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建线索反馈类型采集对象
     */
    public CollectRecordBean build(ClueFeedbackInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(CLUE_FEEDBACK);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(context.getString(R.string.clue_feedback_title));
            bean.setContent(info.getRemark());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建任务报告类型采集对象
     */
    public CollectRecordBean build(TaskReportInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(TASK_REPORT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilepaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilepaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilepaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getAddress());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建通知公告类型采集对象
     */
    public CollectRecordBean build(NoticeInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(PUBLISH_NOTICE);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getTitle());
            bean.setContent(!TextUtils.isEmpty(info.getSummary()) ? info.getSummary() : info.getWebUrl());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建发布任务类型采集对象
     */
    public CollectRecordBean build(TaskInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(PUBLISH_TASK);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getSubject());
            bean.setContent(info.getDescription());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建人员采集类型采集对象
     */
    public CollectRecordBean build(PersonCollectInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(PERSONNEL_COLLECT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getReal_name() + info.getCertificate_num());
            bean.setContent(info.getContent());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建案例采集对象
     */
    public CollectRecordBean build(LegalCaseInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(CASE_INPUT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getTitle());
            bean.setContent(info.getDescription());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建线索处置对象
     */
    public CollectRecordBean build(ClueDisposeInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(DISPOSE_CLUE);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(context.getString(R.string.clue_dispose_check));
            bean.setContent(info.getRemark());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建线索分配处置对象
     */
    public CollectRecordBean build(ClueDispatchInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(CLUE_ASSIGN);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePaths());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePaths()));
            List<UploadInfo> list = getUploadInfo(info.getFilePaths());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(context.getString(R.string.clue_dispose_dispatch));
            bean.setContent(info.getRemark());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建添加承租人类型采集对象
     */
    public CollectRecordBean build(TenantInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(ADD_RENTAL_PERSON);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(info.getTenantryPath())) {
                builder.append(info.getTenantryPath());
            }
            if (!TextUtils.isEmpty(info.getCardPath())) {
                builder.append(",").append(info.getCardPath());
            }
            String filePaths = builder.toString();
            bean.setFilepaths(filePaths);
            bean.setTotalSize(FileHelper.calculateTotalSize(filePaths));
            List<UploadInfo> list = getUploadInfo(filePaths);
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getReal_name() + ":" + info.getCertificate_num());
            bean.setContent(info.getNative_place());
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建注册类型采集对象
     */
    public CollectRecordBean build(RegisterInfo info, int from) {
        if (info != null) {
            CollectRecordBean bean = new CollectRecordBean();
            if (from == 1) {
                bean.setCollectType(REGISTER);
            } else if (from == 2) {
                bean.setCollectType(RESET_INFO);
            } else {
                bean.setCollectType(REGISTER_AGAIN);
            }
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getIdcards());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getIdcards()));
            List<UploadInfo> list = getUploadInfo(info.getIdcards());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setTotalIndex(list.size() - 1);
            bean.setAttachData(gson.toJson(info));
            AccountInfo accountInfo = preference.getAccountInfo();
            if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getUsername())) {
                bean.setUser(accountInfo.getUsername());
            }
            return bean;
        }
        return null;
    }

    /**
     * 构建头像上传的采集对象
     */
    public CollectRecordBean build(AvatarInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(AVATAR);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            bean.setFilepaths(info.getFilePath());
            bean.setTotalSize(FileHelper.calculateTotalSize(info.getFilePath()));
            List<UploadInfo> list = getUploadInfo(info.getFilePath());
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setTotalIndex(list.size() - 1);
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }

    /**
     * 构建汽油采集类型采集对象
     */
    public CollectRecordBean build(GasInfo info) {
        AccountInfo accountInfo = preference.getAccountInfo();
        if (info != null && accountInfo != null) {
            CollectRecordBean bean = new CollectRecordBean();
            bean.setCollectType(GAS_COLLECT);
            bean.setUploadReportState(STATE_OF_WAITING);
            bean.setRecordRole(RECORD_ROLE.NONE.ordinal());
            bean.setSaveTime(CommonUtils.getServerTime(context));
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(info.getUserFilePath())) {
                builder.append(info.getUserFilePath());
            }
            if (!TextUtils.isEmpty(info.getCardFilePath())) {
                builder.append(",").append(info.getCardFilePath());
            }
            if (!TextUtils.isEmpty(info.getLetterFilePath())) {
                builder.append(",").append(info.getLetterFilePath());
            }
            String filePaths = builder.toString();
            bean.setFilepaths(filePaths);
            bean.setTotalSize(FileHelper.calculateTotalSize(filePaths));
            List<UploadInfo> list = getUploadInfo(filePaths);
            bean.setFileUploadInfos(list.size() > 0 ? gson.toJson(list) : "");
            bean.setGps(preference.getLongitude() + "," + preference.getLatitude());
            bean.setGpsAddress(preference.getAddress());
            bean.setTotalIndex(list.size() - 1);
            bean.setSubject(info.getName() + ":" + info.getCard());
            bean.setContent(String.format(context.getString(R.string.gas_collect_summary_format), String.valueOf(info.getLitre())));
            bean.setUser(accountInfo.getUsername());
            bean.setAttachData(gson.toJson(info));
            return bean;
        }
        return null;
    }


    /**
     * 获取UploadInfo
     */
    protected List<UploadInfo> getUploadInfo(String filePaths) {
        try {
            List<UploadInfo> list = null;
            List<String> paths = Arrays.asList(filePaths.split(","));
            list = new ArrayList<>();
            for (String path : paths) {
                if (TextUtils.isEmpty(path)) {
                    continue;
                }
                UploadInfo fileInfo = new UploadInfo();
                String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
                File file = new File(path);
                fileInfo.setRowKey(fileName + file.lastModified());
                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(path);
                fileInfo.setTotalSize(file.length());
                list.add(fileInfo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
