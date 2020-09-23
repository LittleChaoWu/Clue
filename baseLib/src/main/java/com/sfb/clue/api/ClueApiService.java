package com.sfb.clue.api;

import com.sfb.baselib.data.ListInfo;
import com.sfb.baselib.data.base.BaseResponse;
import com.sfb.clue.data.ClueDetailInfo;
import com.sfb.clue.data.ClueTypeInfo;
import com.sfb.baselib.data.ClueInfo;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClueApiService {

    /**
     * 获取线索举报列表
     *
     * @param pageNo   页数
     * @param pageSize 页大小
     */
    @GET("get_clue_list")
    Flowable<BaseResponse<ListInfo<ClueInfo>>> getClueList(@Query("page_no") int pageNo, @Query("page_size") int pageSize, @Query("type") String type);

    /**
     * 获取线索详情
     *
     * @param clueId 线索id
     */
    @GET("get_clue")
    Flowable<BaseResponse<ClueDetailInfo>> getClueDetail(@Query("clue_id") int clueId);

    /**
     * 获取线索类型
     */
    @GET("get_clue_types")
    Flowable<BaseResponse<List<ClueTypeInfo>>> getClueTypes();
}
