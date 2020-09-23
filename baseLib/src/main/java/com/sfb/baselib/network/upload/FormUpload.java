package com.sfb.baselib.network.upload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sfb.baselib.Constants;
import com.sfb.baselib.components.bus.Bus;
import com.sfb.baselib.components.bus.event.CollectRecordEvent;
import com.sfb.baselib.components.bus.event.UploadEvent;
import com.sfb.baselib.data.NoticeInfo;
import com.sfb.baselib.data.RegisterInfo;
import com.sfb.baselib.data.UserInfo;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.database.DaoHelper;
import com.sfb.baselib.database.DataBaseManager;
import com.sfb.baselib.database.data.CollectRecordBean;
import com.sfb.baselib.database.data.UploadInfo;
import com.sfb.baselib.network.NetworkHelper;
import com.sfb.baselib.network.subscriber.ResponseSubscriber;
import com.sfb.baselib.utils.RxUtils;
import com.sfb.baselib.utils.ToastUtils;
import com.sfb.baselib.R;
import com.sfb.baselib.components.inject.UploadDagger;
import com.sfb.baselib.data.AvatarInfo;
import com.sfb.baselib.data.CarInfo;
import com.sfb.baselib.data.ClueDispatchInfo;
import com.sfb.baselib.data.ClueDisposeInfo;
import com.sfb.baselib.data.ClueFeedbackInfo;
import com.sfb.baselib.data.ClueIdInfo;
import com.sfb.baselib.data.ClueInfo;
import com.sfb.baselib.data.FileId;
import com.sfb.baselib.data.GasInfo;
import com.sfb.baselib.data.LegalCaseInfo;
import com.sfb.baselib.data.PersonCollectInfo;
import com.sfb.baselib.data.TaskInfo;
import com.sfb.baselib.data.TaskReportContentInfo;
import com.sfb.baselib.data.TaskReportInfo;
import com.sfb.baselib.data.TenantInfo;
import com.sfb.baselib.network.api.UploadApiService;
import com.pref.GuardPreference_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@SuppressLint("CheckResult")
public class FormUpload {

    @Inject
    public Context context;
    @Inject
    public Gson gson;
    @Inject
    public Bus mBus;
    @Inject
    public DaoHelper mDaoHelper;
    @Inject
    public GuardPreference_ preference;
    @Inject
    public UploadApiService mApiService;

    private static FormUpload instance;

    private FormUpload() {
        UploadDagger.getDaggerComponent().inject(this);
    }

    public static FormUpload getInstance() {
        if (instance == null) {
            synchronized (DataBaseManager.class) {
                if (instance == null) {
                    instance = new FormUpload();
                }
            }
        }
        return instance;
    }

    /**
     * 重置ApiService
     * 原因：
     * 由于loginLib依赖uploadLib，loginLib重置ApiService的同时，uploadLib也需要
     * 而且UploadApiService是通过注解实例化的，因此，此处需要对UploadApiService重赋值
     */
    public void resetApiService() {
        mApiService = null;
        NetworkHelper helper = NetworkHelper.getInstance(context);
        mApiService = helper.getApi(UploadApiService.class);
    }

    /**
     * 上传表单
     *
     * @param bean CollectRecordBean
     */
    public void uploadForm(CollectRecordBean bean) {
        int collectType = bean.getCollectType();
        switch (collectType) {
            case CollectRecordBean.CAR_COLLECT:
                //采集车辆
                uploadCar(bean);
                break;
            case CollectRecordBean.CLUE_COLLECT:
                //添加线索
                uploadClue(bean);
                break;
            case CollectRecordBean.TASK_REPORT:
                //任务报告
                uploadTaskReport(bean);
                break;
            case CollectRecordBean.PUBLISH_NOTICE:
                //发布通知公告
                publishNotice(bean);
                break;
            case CollectRecordBean.PUBLISH_TASK:
                //发布任务
                publishTask(bean);
                break;
            case CollectRecordBean.PERSONNEL_COLLECT:
                //采集人员
                uploadPerson(bean);
                break;
            case CollectRecordBean.CASE_INPUT:
                //上传案例
                uploadLegalCase(bean);
                break;
            case CollectRecordBean.DISPOSE_CLUE:
                //线索处置
                uploadDisposeClue(bean);
                break;
            case CollectRecordBean.CLUE_FEEDBACK:
                //线索反馈
                uploadClueFeedback(bean);
                break;
            case CollectRecordBean.CLUE_ASSIGN:
                //线索分发
                uploadDispatchClue(bean);
                break;
            case CollectRecordBean.ADD_RENTAL_PERSON:
                //添加承租人
                uploadTenant(bean);
                break;
            case CollectRecordBean.REGISTER:
                //上传注册信息
                uploadRegister(bean);
                break;
            case CollectRecordBean.REGISTER_AGAIN:
                //上传重新注册信息
                uploadRegisterAgain(bean);
                break;
            case CollectRecordBean.RESET_INFO:
                //补全信息/重置信息
                resetInfo(bean);
                break;
            case CollectRecordBean.AVATAR:
                updateAvatar(bean);
                break;
            case CollectRecordBean.GAS_COLLECT:
                //散装汽油采集
                collectGas(bean);
                break;
            default:
                break;
        }
    }

    /**
     * 采集车辆
     */
    private void uploadCar(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            CarInfo info = gson.fromJson(data, CarInfo.class);
            final int subTaskId = info.getSubTaskId();
            setCarPhotoIds(info, bean.getFilepaths());
            Map<String, Object> map = new HashMap<>();
            map.put("type", info.getType());
            map.put("car_file_id", info.getCarPhotoId());
            map.put("other_file_id_list", info.getFileIds());
            map.put("plate_num", info.getPlate_num());
            map.put("color", info.getColor());
            map.put("coll_address", info.getColl_address());
            map.put("remark", info.getRemark());
            map.put("person_list", info.getPersonListJson());
            map.put("source_type", String.valueOf(info.getSourceType()));
            map.put("created_time", String.valueOf(info.getCreated_time()));
            mApiService.addCar(map)
                    .compose(RxUtils.<BaseResponse<Integer>>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse<Integer>>() {
                        @Override
                        public void onSuccess(BaseResponse<Integer> response) {
                            updateUploadState(bean, true);
                            if (subTaskId > 0) {
                                //构建数据
                                int collectId = response.getData();
                                TaskReportInfo reportInfo = createTask(subTaskId, String.valueOf(collectId), Constants.CAR_COLLECT_TASK_REPORT);
                                CollectRecordBean taskReportBean = new CollectRecordBean();
                                taskReportBean.setAttachData(gson.toJson(reportInfo));
                                //上传
                                uploadTaskReport(taskReportBean);
                            } else {
                                ToastUtils.showShort(context, R.string.commit_success);
                            }
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 添加线索
     */
    private void uploadClue(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            ClueInfo info = gson.fromJson(data, ClueInfo.class);
            final int subTaskId = info.getSubTaskId();
            Map<String, Object> map = new HashMap<>();
            map.put("title", info.getTitle());
            map.put("area_code", info.getAreaCode());
            map.put("area_name", info.getAddressHide());
            map.put("gps", info.getGps());
            map.put("clue_broad_type", info.getClueBroadType());
            map.put("clue_type", info.getClueType());
            map.put("clue_mini_type", info.getClueMiniType());
            map.put("summary", info.getSummary());
            map.put("create_time", info.getCreatedTime());
            //20200803需求修改：上报线索表单数据需要上传地址参数
            map.put("clueAddress", info.getAreaName());
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("file_ids", fileIds);
            }
            boolean isSdkLogin = preference.getIsSdkLogin();
            mApiService.addClue(map, isSdkLogin ? "1" : "0")
                    .compose(RxUtils.<BaseResponse<ClueIdInfo>>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse<ClueIdInfo>>() {
                        @Override
                        public void onSuccess(BaseResponse<ClueIdInfo> response) {
                            updateUploadState(bean, true);
                            if (subTaskId > 0) {
                                //构建数据
                                int collectId = response.getData().getClue_id();
                                TaskReportInfo reportInfo = createTask(subTaskId, String.valueOf(collectId), Constants.CLUE_TASK_REPORT);
                                CollectRecordBean taskReportBean = new CollectRecordBean();
                                taskReportBean.setAttachData(gson.toJson(reportInfo));
                                //上传
                                uploadTaskReport(taskReportBean);
                            } else {
                                ToastUtils.showShort(context, R.string.commit_success);
                            }
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 任务汇报
     */
    private void uploadTaskReport(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            TaskReportInfo info = gson.fromJson(data, TaskReportInfo.class);
            info.getAchievement().setFiles(getFileIdList(info.getFilepaths()));
            String json = gson.toJson(info);
            if (info.isDirectSubmit()) {
                mApiService.commitTaskReport(json)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                if (bean.getId() != null) {
                                    updateUploadState(bean, true);
                                }
                                ToastUtils.showShort(context, R.string.commit_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                if (bean.getId() != null) {
                                    updateUploadState(bean, false);
                                }
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.commit_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            } else {
                mApiService.commitTaskReport1(json)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                ToastUtils.showShort(context, R.string.commit_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.commit_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 发布通知公告
     */
    private void publishNotice(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            NoticeInfo info = gson.fromJson(data, NoticeInfo.class);
            Map<String, Object> map = new HashMap<>();
            map.put("title", info.getTitle());
            map.put("infType", info.getInfType());
            map.put("infoRU", info.getReceiverUsers());
            map.put("sendTime", info.getSendTime());
            map.put("contentType", info.getContentType());
            if (!TextUtils.isEmpty(info.getSummary())) {
                map.put("summary", info.getSummary());
            }
            if (!TextUtils.isEmpty(info.getWebUrl())) {
                map.put("webUrl", info.getWebUrl());
            }
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("fileIds", fileIds);
            }
            mApiService.publishNotice(map)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.publish_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.publish_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 发布任务
     */
    private void publishTask(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            TaskInfo info = gson.fromJson(data, TaskInfo.class);
            //赋值文件id
            String fileIds = getFileIds(info.getFilePaths(), ",");
            info.setFileIdsStr(fileIds);

            //为了处理publishTime
            Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson1.toJson(info);

            mApiService.publishTask(json)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.publish_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.publish_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 采集人员
     */
    private void uploadPerson(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            PersonCollectInfo info = gson.fromJson(data, PersonCollectInfo.class);
            final int subTaskId = info.getSubTaskId();
            setPersonPhotoIds(info, bean.getFilepaths());
            Map<String, Object> map = new HashMap<>();
            map.put("certificate_type", info.getCertificate_type());
            map.put("certificate_file_id", info.getCardPhotoId());
            map.put("other_file_id_list", info.getFileIds());
            map.put("certificate_num", info.getCertificate_num());
            map.put("real_name", info.getReal_name());
            map.put("sex", info.getSex());
            map.put("native_place", info.getNative_place());
            map.put("telephone", info.getTelephone());
            map.put("resident", info.getResident());
            map.put("coll_address", info.getColl_address());
            map.put("remark", info.getRemark() == null ? "" : info.getRemark());
            map.put("goods_list", info.getGoodsListJson());
            map.put("dispose_result", info.getDisposeResultJson());
            map.put("source_type", String.valueOf(info.getSourceType()));
            map.put("created_time", String.valueOf(info.getCreated_time()));
            mApiService.addPerson(map)
                    .compose(RxUtils.<BaseResponse<Integer>>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse<Integer>>() {
                        @Override
                        public void onSuccess(BaseResponse<Integer> response) {
                            updateUploadState(bean, true);
                            if (subTaskId > 0) {
                                //构建数据
                                int collectId = response.getData();
                                TaskReportInfo reportInfo = createTask(subTaskId, String.valueOf(collectId), Constants.PEOPLE_COLLECT_TASK_REPORT);
                                CollectRecordBean taskReportBean = new CollectRecordBean();
                                taskReportBean.setAttachData(gson.toJson(reportInfo));
                                //上传
                                uploadTaskReport(taskReportBean);
                            } else {
                                ToastUtils.showShort(context, R.string.commit_success);
                            }
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 上传案例
     */
    private void uploadLegalCase(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            LegalCaseInfo info = gson.fromJson(data, LegalCaseInfo.class);
            Map<String, Object> map = new HashMap<>();
            map.put("title", info.getTitle());
            map.put("description", info.getDescription());
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("file_ids", fileIds);
            }
            mApiService.uploadLegalCase(map)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.commit_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 线索处置
     */
    private void uploadDisposeClue(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            ClueDisposeInfo info = gson.fromJson(data, ClueDisposeInfo.class);
            Map<String, Object> map = new HashMap<>();
            map.put("clue_id", info.getClueId());
            map.put("deal_type", info.getResult());
            map.put("clue_big_type", info.getClueBroadType());
            map.put("clue_small_type", info.getClueType());
            map.put("clue_mini_type", info.getClueChildType());
            map.put("remark", info.getRemark());
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("file_ids", fileIds);
            }
            mApiService.disposeClue(map)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.commit_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 线索反馈
     */
    private void uploadClueFeedback(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            ClueFeedbackInfo info = gson.fromJson(data, ClueFeedbackInfo.class);
            Map<String, Object> map = new HashMap<>();
            map.put("clue_id", info.getClueId());
            map.put("remark", info.getRemark());
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("file_ids", fileIds);
            }
            mApiService.clueFeedback(map)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.commit_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 线索分发
     */
    private void uploadDispatchClue(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            ClueDispatchInfo info = gson.fromJson(data, ClueDispatchInfo.class);
            Map<String, Object> map = new HashMap<>();
            map.put("clue_id", info.getClueId());
            map.put("org_codes", info.getOrgCodes());
            map.put("remark", info.getRemark());
            String fileIds = getFileIds(info.getFilePaths());
            if (!TextUtils.isEmpty(fileIds)) {
                map.put("file_ids", fileIds);
            }
            mApiService.dispatchClue(map)
                    .compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                            ToastUtils.showShort(context, R.string.commit_success);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 添加承租人
     */
    private void uploadTenant(final CollectRecordBean bean) {
        String data = bean.getAttachData();
        if (!TextUtils.isEmpty(data)) {
            TenantInfo info = gson.fromJson(data, TenantInfo.class);
            setAddTenantPhotoId(info, bean.getFilepaths());
            Map<String, Object> map = new HashMap<>();
            map.put("hire_unit_id", info.getHire_unit_id());
            map.put("certificate_type", info.getCertificate_type());
            map.put("certificate_num", info.getCertificate_num());
            map.put("real_name", info.getReal_name());
            map.put("sex", String.valueOf(info.getSex()));
            map.put("native_place", info.getNative_place());
            map.put("telephone", info.getTelephone());
            map.put("work_unit", info.getWork_unit());
            map.put("remark", info.getRemark());
            map.put("check_in_time", info.getCheck_in_time());
            map.put("childNum", String.valueOf(info.getChildNum()));
            map.put("tenantryFileId", info.getTenantryFileId());
            map.put("cardFileId", info.getCardFileId());
            mApiService.addTenant(map).compose(RxUtils.<BaseResponse>io2main())
                    .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            updateUploadState(bean, true);
                        }

                        @Override
                        public void onFail(String errorMessage, boolean isEmptyMessage) {
                            updateUploadState(bean, false);
                            if (isEmptyMessage) {
                                ToastUtils.showShort(context, R.string.commit_fail);
                            } else {
                                ToastUtils.showShort(context, errorMessage);
                            }
                        }
                    });
        }
    }

    /**
     * 上传注册信息
     */
    private void uploadRegister(final CollectRecordBean bean) {
        if (bean != null) {
            String data = bean.getAttachData();
            if (!TextUtils.isEmpty(data)) {
                final RegisterInfo info = gson.fromJson(data, RegisterInfo.class);
                //获取注册时候提交图片的id
                setRegisterPhotoId(info, bean.getFilepaths());
                //提交表单
                Map<String, Object> map = new HashMap<>();
                map.put("realName", info.getRealName());
                map.put("sex", info.getSex());
                map.put("card", info.getCard());
                if (!TextUtils.isEmpty(info.getBirthday())) {
                    map.put("birthday", info.getBirthday());
                }
                map.put("referrer", info.getReferrer());
                map.put("area", info.getArea());
                map.put("police", info.getPolice());
                map.put("phone", info.getTelephone());
                map.put("pwd", info.getPwd());
                map.put("code", info.getCode());
                map.put("userGroups", info.getUserGroups());
                map.put("authType", String.valueOf(info.getAuthType()));
                map.put("authResult", String.valueOf(info.getAuthResult()));
                if (!TextUtils.isEmpty(info.getNation())) {
                    map.put("nation", info.getNation());
                }
                if (!TextUtils.isEmpty(info.getResidencet())) {
                    map.put("residencet", info.getResidencet());
                }
                if (!TextUtils.isEmpty(info.getDegree())) {
                    map.put("degree", info.getDegree());
                }
                if (!TextUtils.isEmpty(info.getMajor())) {
                    map.put("major", info.getMajor());
                }
                if (!TextUtils.isEmpty(info.getLeague())) {
                    map.put("league", info.getLeague());
                }
                if (!TextUtils.isEmpty(info.getOccupation())) {
                    map.put("occupation", info.getOccupation());
                }
                if (!TextUtils.isEmpty(info.getWorkUnit())) {
                    map.put("workUnit", info.getWorkUnit());
                }
                if (!TextUtils.isEmpty(info.getSpecialty())) {
                    map.put("specialty", info.getSpecialty());
                }
                if (!TextUtils.isEmpty(info.getResume())) {
                    map.put("resume", info.getResume());
                }
                if (!TextUtils.isEmpty(info.getCardBack())) {
                    map.put("cardBack", info.getCardBack());
                }
                if (!TextUtils.isEmpty(info.getCardPhoto())) {
                    map.put("cardPhoto", info.getCardPhoto());
                }
                if (!TextUtils.isEmpty(info.getCardFront())) {
                    map.put("cardFront", info.getCardFront());
                }
                if (!TextUtils.isEmpty(info.getBusStation())) {
                    map.put("busStation", info.getBusStation());
                }
                if (!TextUtils.isEmpty(info.getBusRoute())) {
                    map.put("busRoute", info.getBusRoute());
                }
                if (!TextUtils.isEmpty(info.getTraffic())) {
                    map.put("traffic", info.getTraffic());
                }
                if (!TextUtils.isEmpty(info.getEmail())) {
                    map.put("email", info.getEmail());
                }
                if (!TextUtils.isEmpty(info.getEmergency())) {
                    map.put("emergency", info.getEmergency());
                }
                mApiService.register(map)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                ToastUtils.showShort(context, R.string.register_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.register_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传重新注册信息
     */
    private void uploadRegisterAgain(final CollectRecordBean bean) {
        if (bean != null) {
            String data = bean.getAttachData();
            if (!TextUtils.isEmpty(data)) {
                final RegisterInfo info = gson.fromJson(data, RegisterInfo.class);
                //获取注册时候提交图片的id
                setRegisterPhotoId(info, bean.getFilepaths());
                //提交表单
                Map<String, Object> map = new HashMap<>();
                map.put("realName", info.getRealName());
                map.put("sex", info.getSex());
                map.put("card", info.getCard());
                map.put("username", info.getUsername());
                if (!TextUtils.isEmpty(info.getBirthday())) {
                    map.put("birthday", info.getBirthday());
                }
                map.put("referrer", info.getReferrer());
                map.put("area", info.getArea());
                map.put("police", info.getPolice());
                map.put("phone", info.getTelephone());
                map.put("pwd", info.getPwd());
                map.put("code", info.getCode());
                map.put("userGroups", info.getUserGroups());
                map.put("authType", String.valueOf(info.getAuthType()));
                map.put("authResult", String.valueOf(info.getAuthResult()));
                if (!TextUtils.isEmpty(info.getNation())) {
                    map.put("nation", info.getNation());
                }
                if (!TextUtils.isEmpty(info.getResidencet())) {
                    map.put("residencet", info.getResidencet());
                }
                if (!TextUtils.isEmpty(info.getDegree())) {
                    map.put("degree", info.getDegree());
                }
                if (!TextUtils.isEmpty(info.getMajor())) {
                    map.put("major", info.getMajor());
                }
                if (!TextUtils.isEmpty(info.getLeague())) {
                    map.put("league", info.getLeague());
                }
                if (!TextUtils.isEmpty(info.getOccupation())) {
                    map.put("occupation", info.getOccupation());
                }
                if (!TextUtils.isEmpty(info.getWorkUnit())) {
                    map.put("workUnit", info.getWorkUnit());
                }
                if (!TextUtils.isEmpty(info.getSpecialty())) {
                    map.put("specialty", info.getSpecialty());
                }
                if (!TextUtils.isEmpty(info.getResume())) {
                    map.put("resume", info.getResume());
                }
                if (!TextUtils.isEmpty(info.getCardBack())) {
                    map.put("cardBack", info.getCardBack());
                }
                if (!TextUtils.isEmpty(info.getCardPhoto())) {
                    map.put("cardPhoto", info.getCardPhoto());
                }
                if (!TextUtils.isEmpty(info.getCardFront())) {
                    map.put("cardFront", info.getCardFront());
                }
                if (!TextUtils.isEmpty(info.getBusStation())) {
                    map.put("busStation", info.getBusStation());
                }
                if (!TextUtils.isEmpty(info.getBusRoute())) {
                    map.put("busRoute", info.getBusRoute());
                }
                if (!TextUtils.isEmpty(info.getTraffic())) {
                    map.put("traffic", info.getTraffic());
                }
                if (!TextUtils.isEmpty(info.getEmail())) {
                    map.put("email", info.getEmail());
                }
                if (!TextUtils.isEmpty(info.getEmergency())) {
                    map.put("emergency", info.getEmergency());
                }
                mApiService.registerAgain(map)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                ToastUtils.showShort(context, R.string.register_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.register_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 补全信息/重置信息
     */
    private void resetInfo(final CollectRecordBean bean) {
        if (bean != null) {
            String data = bean.getAttachData();
            if (!TextUtils.isEmpty(data)) {
                final RegisterInfo info = gson.fromJson(data, RegisterInfo.class);
                //获取注册时候提交图片的id
                setRegisterPhotoId(info, bean.getFilepaths());
                //提交表单
                Map<String, Object> map = new HashMap<>();
                map.put("realName", info.getRealName());
                map.put("sex", info.getSex());
                map.put("card", info.getCard());
                map.put("username", info.getUsername());
                if (!TextUtils.isEmpty(info.getBirthday())) {
                    map.put("birthday", info.getBirthday());
                }
                map.put("referrer", info.getReferrer());
                map.put("area", info.getArea());
                map.put("police", info.getPolice());
                map.put("phone", info.getTelephone());
                map.put("pwd", info.getPwd());
                map.put("userGroups", info.getUserGroups());
                map.put("authType", String.valueOf(info.getAuthType()));
                map.put("authResult", String.valueOf(info.getAuthResult()));
                if (!TextUtils.isEmpty(info.getNation())) {
                    map.put("nation", info.getNation());
                }
                if (!TextUtils.isEmpty(info.getResidencet())) {
                    map.put("residencet", info.getResidencet());
                }
                if (!TextUtils.isEmpty(info.getDegree())) {
                    map.put("degree", info.getDegree());
                }
                if (!TextUtils.isEmpty(info.getMajor())) {
                    map.put("major", info.getMajor());
                }
                if (!TextUtils.isEmpty(info.getLeague())) {
                    map.put("league", info.getLeague());
                }
                if (!TextUtils.isEmpty(info.getOccupation())) {
                    map.put("occupation", info.getOccupation());
                }
                if (!TextUtils.isEmpty(info.getWorkUnit())) {
                    map.put("workUnit", info.getWorkUnit());
                }
                if (!TextUtils.isEmpty(info.getSpecialty())) {
                    map.put("specialty", info.getSpecialty());
                }
                if (!TextUtils.isEmpty(info.getResume())) {
                    map.put("resume", info.getResume());
                }
                if (!TextUtils.isEmpty(info.getCardBack())) {
                    map.put("cardBack", info.getCardBack());
                }
                if (!TextUtils.isEmpty(info.getCardPhoto())) {
                    map.put("cardPhoto", info.getCardPhoto());
                }
                if (!TextUtils.isEmpty(info.getCardFront())) {
                    map.put("cardFront", info.getCardFront());
                }
                if (!TextUtils.isEmpty(info.getBusStation())) {
                    map.put("busStation", info.getBusStation());
                }
                if (!TextUtils.isEmpty(info.getBusRoute())) {
                    map.put("busRoute", info.getBusRoute());
                }
                if (!TextUtils.isEmpty(info.getTraffic())) {
                    map.put("traffic", info.getTraffic());
                }
                if (!TextUtils.isEmpty(info.getEmail())) {
                    map.put("email", info.getEmail());
                }
                if (!TextUtils.isEmpty(info.getEmergency())) {
                    map.put("emergency", info.getEmergency());
                }
                mApiService.resetInfo(map)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                ToastUtils.showShort(context, R.string.register_reset_info_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.register_reset_info_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 修改头像
     */
    private void updateAvatar(final CollectRecordBean bean) {
        if (bean != null) {
            String data = bean.getAttachData();
            if (!TextUtils.isEmpty(data)) {
                final AvatarInfo info = gson.fromJson(data, AvatarInfo.class);
                setAvatarId(info, bean.getFilepaths());
                mApiService.updateAvatar(info.getFileId())
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                UserInfo userInfo = GuardPreference_.getInstance(context).getUserInfo();
                                if (userInfo != null && !TextUtils.isEmpty(info.getFileId())) {
                                    userInfo.setLogo(Integer.parseInt(info.getFileId()));
                                    GuardPreference_.getInstance(context).putUserInfo(userInfo);
                                }
                                ToastUtils.showShort(context, R.string.upload_avatar_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.upload_avatar_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 汽油采集
     */
    private void collectGas(final CollectRecordBean bean) {
        if (bean != null) {
            String data = bean.getAttachData();
            if (!TextUtils.isEmpty(data)) {
                GasInfo info = gson.fromJson(data, GasInfo.class);
                setGasPhotoId(info, bean.getFilepaths());
                Map<String, Object> map = new HashMap<>();
                map.put("name", info.getName());
                map.put("card", info.getCard());
                map.put("sex", info.getSex());
                map.put("litre", info.getLitre());
                map.put("company", info.getCompany());
                map.put("userFileId", info.getUserFileId());
                map.put("cardFileId", info.getCardFileId());
                map.put("letterFileId", info.getLetterFileId());
                mApiService.collectGas(map)
                        .compose(RxUtils.<BaseResponse>io2main())
                        .subscribeWith(new ResponseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                updateUploadState(bean, true);
                                ToastUtils.showShort(context, R.string.commit_success);
                            }

                            @Override
                            public void onFail(String errorMessage, boolean isEmptyMessage) {
                                updateUploadState(bean, false);
                                if (isEmptyMessage) {
                                    ToastUtils.showShort(context, R.string.commit_fail);
                                } else {
                                    ToastUtils.showShort(context, errorMessage);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 获取FileIds
     *
     * @param filePaths String
     * @return String
     */
    private List<Integer> getFileIdList(String filePaths) {
        List<Integer> list = new ArrayList<>();
        if (!TextUtils.isEmpty(filePaths)) {
            List<String> paths = Arrays.asList(filePaths.split(","));
            for (int i = 0; i < paths.size(); i++) {
                UploadInfo info = mDaoHelper.getUploadInfoByPath(paths.get(i));
                if (info != null && !TextUtils.isEmpty(info.getRowKey())) {
                    list.add(Integer.valueOf(info.getRowKey()));
                }
            }
            return list;
        }
        return list;
    }

    /**
     * 获取FileIds
     *
     * @param filePaths String 文件路径
     * @param separator String 分隔符
     * @return String
     */
    private String getFileIds(String filePaths, String separator) {
        if (!TextUtils.isEmpty(filePaths)) {
            List<String> paths = Arrays.asList(filePaths.split(","));
            StringBuilder fileIds = new StringBuilder();
            for (int i = 0; i < paths.size(); i++) {
                UploadInfo info = mDaoHelper.getUploadInfoByPath(paths.get(i));
                if (info != null) {
                    if (i == paths.size() - 1) {
                        fileIds.append(info.getRowKey());
                    } else {
                        fileIds.append(info.getRowKey()).append(separator);
                    }
                }
            }
            return fileIds.toString();
        }
        return "";
    }

    /**
     * 获取FileIds
     *
     * @param filePaths String
     * @return String
     */
    private String getFileIds(String filePaths) {
        return getFileIds(filePaths, "|");
    }

    /**
     * 获取采集车辆的FileIds
     *
     * @param info      CarInfo
     * @param filePaths String
     */
    private void setCarPhotoIds(CarInfo info, String filePaths) {
        //获取上传文件的id
        List<String> paths = Arrays.asList(filePaths.split(","));
        List<FileId> fileIds = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                if (path.equals(info.getCarPhotoPath())) {
                    info.setCarPhotoId(uploadInfo.getRowKey());//车辆照片
                } else {
                    fileIds.add(new FileId(uploadInfo.getRowKey()));
                }
            }
        }
        String fileIdsJson = gson.toJson(fileIds, new TypeToken<ArrayList<FileId>>() {
        }.getType());
        info.setFileIds(fileIdsJson);
    }

    /**
     * 获取采集人员的FileIds
     *
     * @param info      CarInfo
     * @param filePaths String
     */
    private void setPersonPhotoIds(PersonCollectInfo info, String filePaths) {
        //获取上传文件的id
        List<String> paths = Arrays.asList(filePaths.split(","));
        List<FileId> fileIds = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                if (path.equals(info.getCardPhotoPath())) {
                    info.setCardPhotoId(uploadInfo.getRowKey());//证件照片
                } else {
                    fileIds.add(new FileId(uploadInfo.getRowKey()));
                }
            }
        }
        String fileIdsJson = gson.toJson(fileIds, new TypeToken<ArrayList<FileId>>() {
        }.getType());
        info.setFileIds(fileIdsJson);
    }


    /**
     * 获取注册时候提交图片的id
     *
     * @param info      RegisterInfo
     * @param filePaths String
     */
    private void setRegisterPhotoId(RegisterInfo info, String filePaths) {
        //获取上传文件的id
        List<String> paths = Arrays.asList(filePaths.split(","));
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                if (path.equals(info.getFrontIccardPath())) {
                    info.setCardFront(uploadInfo.getRowKey());//身份证正面照片
                } else if (path.equals(info.getBackIccardPath())) {
                    info.setCardBack(uploadInfo.getRowKey());//身份证反面照
                } else if (path.equals(info.getFaceIccardPath())) {
                    info.setCardPhoto(uploadInfo.getRowKey());//个人近照
                }
            }
        }
    }

    /**
     * 获取上传头像的id
     *
     * @param info      AvatarInfo
     * @param filePaths String
     */
    private void setAvatarId(AvatarInfo info, String filePaths) {
        List<String> paths = Arrays.asList(filePaths.split(","));
        for (String path : paths) {
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                info.setFileId(uploadInfo.getRowKey());
            }
        }
    }

    /**
     * 获取采集汽油提交图片的id
     *
     * @param info      RegisterInfo
     * @param filePaths , String
     */
    private void setGasPhotoId(GasInfo info, String filePaths) {
        //获取上传文件的id
        List<String> paths = Arrays.asList(filePaths.split(","));
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                if (path.equals(info.getUserFilePath())) {
                    info.setUserFileId(uploadInfo.getRowKey());//手持身份证照片
                } else if (path.equals(info.getCardFilePath())) {
                    info.setCardFileId(uploadInfo.getRowKey());//身份证照片
                } else if (path.equals(info.getLetterFilePath())) {
                    info.setLetterFileId(uploadInfo.getRowKey());//个人近照
                }
            }
        }
    }

    /**
     * 获取添加承租人提交图片的id
     *
     * @param info      RegisterInfo
     * @param filePaths , String
     */
    private void setAddTenantPhotoId(TenantInfo info, String filePaths) {
        //获取上传文件的id
        List<String> paths = Arrays.asList(filePaths.split(","));
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            UploadInfo uploadInfo = mDaoHelper.getUploadInfoByPath(path);
            if (uploadInfo != null) {
                if (path.equals(info.getTenantryPath())) {
                    info.setTenantryFileId(uploadInfo.getRowKey());//承租人照片路径
                } else if (path.equals(info.getCardPath())) {
                    info.setCardFileId(uploadInfo.getRowKey());//身份证照片
                }
            }
        }
    }

    /**
     * 创建采集任务相关的实体，在进行任务提交时使用
     *
     * @param subTaskId int
     * @param collectId String
     * @param taskType  String
     * @return
     */
    private TaskReportInfo createTask(int subTaskId, String collectId, String taskType) {
        //设置任务信息
        TaskReportInfo info = new TaskReportInfo();
        info.setSubTaskId(subTaskId);
        info.setLat(Double.parseDouble(preference.getLatitude()));
        info.setLon(Double.parseDouble(preference.getLongitude()));
        info.setAddress(preference.getAddress());
        TaskReportContentInfo contentInfo = new TaskReportContentInfo();
        contentInfo.setType(taskType);
        contentInfo.setContent(collectId);
        info.setAchievement(contentInfo);
        return info;
    }

    /**
     * 更改上传状态
     *
     * @param isSuccess boolean
     */
    private void updateUploadState(CollectRecordBean bean, boolean isSuccess) {
        final Long id = bean.getId();
        final String paths = bean.getFilepaths();
        int state = isSuccess ? CollectRecordBean.STATE_OF_SUBMIT_SUCCESS : CollectRecordBean.STATE_OF_SUBMIT_FAIL;
        if (!TextUtils.isEmpty(paths)) {
            bean = mDaoHelper.getCollectRecord(paths);
            bean.setUploadReportState(state);
            mDaoHelper.saveCollectRecord(bean);
        } else if (id != null && id != 0) {
            bean = mDaoHelper.getCollectRecord(id);
            bean.setUploadReportState(state);
            mDaoHelper.saveCollectRecord(bean);
        }
        notifyUI(bean, isSuccess);
    }

    /**
     * 通知上传列表UI更新
     *
     * @param bean      CollectRecordBean
     * @param isSuccess boolean
     */
    private void notifyUI(CollectRecordBean bean, boolean isSuccess) {
        if (!TextUtils.isEmpty(bean.getFilepaths())) {
            mBus.post(new UploadEvent(bean.getFilepaths()));
        }
        //发送上传表单结果通过
        mBus.post(new CollectRecordEvent(bean, isSuccess));
    }

}
