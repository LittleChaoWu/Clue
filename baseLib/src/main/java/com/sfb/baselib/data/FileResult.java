package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class FileResult extends BaseBean {

    private String file_id;//文件id
    private long temp_length;//上传文件的大小

    public String getFileId() {
        return file_id;
    }

    public void setFileId(String file_id) {
        this.file_id = file_id;
    }

    public void setTempLength(long temp_length) {
        this.temp_length = temp_length;
    }

    public long getTempLength() {
        return temp_length;
    }
}
