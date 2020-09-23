package com.sfb.baselib.data.base;

public class BaseResponse<T> extends BaseBean {

    private boolean success;
    private T data;
    private String code;
    private String msg;
    private long response_time;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }

    public boolean isSuccess() {
        return success || code.equals("200");
    }

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public long getResponse_time() {
        return response_time;
    }
}
