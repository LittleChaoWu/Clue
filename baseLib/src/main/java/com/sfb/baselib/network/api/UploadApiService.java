package com.sfb.baselib.network.api;

import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.baselib.data.ClueIdInfo;
import com.sfb.baselib.data.FileResult;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface UploadApiService {

    /**
     * 创建文件
     */
    @FormUrlEncoded
    @POST("file/create_file_item")
    Flowable<BaseResponse<FileResult>> createFile(@FieldMap Map<String, Object> map);

    /**
     * 获取上传文件大小
     *
     * @param file_id String
     */
    @GET("file/get_temp_length")
    Flowable<BaseResponse<FileResult>> getUploadLength(@Query("file_id") String file_id);

    /**
     * 上传文件
     *
     * @param file_id   文件id
     * @param start_pos 上传节点
     */
    @Streaming
    @POST("file/upload_file_item")
    Flowable<BaseResponse> uploadFile(@Query("file_id") String file_id, @Query("start_pos") long start_pos, @Body RequestBody body);

    /**
     * 注册
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("regist")
    Flowable<BaseResponse> register(@FieldMap Map<String, Object> map);

    /**
     * 重新注册
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("regagain")
    Flowable<BaseResponse> registerAgain(@FieldMap Map<String, Object> map);

    /**
     * 信息补全/信息重置
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("reset_info")
    Flowable<BaseResponse> resetInfo(@FieldMap Map<String, Object> map);

    /**
     * 添加线索
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("create_clue")
    Flowable<BaseResponse<ClueIdInfo>> addClue(@FieldMap Map<String, Object> map, @Field("type") String type);

    /**
     * 汇报任务
     *
     * @param json String
     */
    @FormUrlEncoded
    @POST("complete_exec")
    Flowable<BaseResponse> commitTaskReport(@Field("json") String json);

    /**
     * 汇报任务
     *
     * @param json String
     */
    @FormUrlEncoded
    @POST("complete_exec")
    Flowable<BaseResponse> commitTaskReport1(@Field("json") String json);

    /**
     * 发布任务
     *
     * @param json String
     */
    @FormUrlEncoded
    @POST("create_task")
    Flowable<BaseResponse> publishTask(@Field("json") String json);

    /**
     * 添加人员
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("coll_person")
    Flowable<BaseResponse<Integer>> addPerson(@FieldMap Map<String, Object> map);

    /**
     * 发布通知公告
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("save_info_item")
    Flowable<BaseResponse> publishNotice(@FieldMap Map<String, Object> map);

    /**
     * 修改头像
     *
     * @param fileId String
     */
    @FormUrlEncoded
    @POST("update_logo")
    Flowable<BaseResponse> updateAvatar(@Field("logoid") String fileId);

    /**
     * 添加车辆
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("coll_car")
    Flowable<BaseResponse<Integer>> addCar(@FieldMap Map<String, Object> map);

    /**
     * 上传案例
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("save_cases_item")
    Flowable<BaseResponse> uploadLegalCase(@FieldMap Map<String, Object> map);

    /**
     * 线索处置
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("deal_clue_by_department")
    Flowable<BaseResponse> disposeClue(@FieldMap Map<String, Object> map);

    /**
     * 线索反馈
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("clue_feed_back")
    Flowable<BaseResponse> clueFeedback(@FieldMap Map<String, Object> map);

    /**
     * 线索分发
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("clue_assign")
    Flowable<BaseResponse> dispatchClue(@FieldMap Map<String, Object> map);

    /**
     * 添加承租人
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("create_hire_unit_tenantry")
    Flowable<BaseResponse> addTenant(@FieldMap Map<String, Object> map);

    /**
     * 散装汽油采集
     *
     * @param map 参数集合
     */
    @FormUrlEncoded
    @POST("coll_gas")
    Flowable<BaseResponse> collectGas(@FieldMap Map<String, Object> map);

}
