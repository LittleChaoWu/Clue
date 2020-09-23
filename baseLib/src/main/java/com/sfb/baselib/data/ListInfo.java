package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class ListInfo<T> extends BaseBean {

    private int pageNo;//页码
    private int pageSize;//页大小
    private long totalNum;//数据总量
    private int totalPage;//总页数
    private List<T> results;//数据集合
    private List<T> data;//数据集合，部分接口返回在data中

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
