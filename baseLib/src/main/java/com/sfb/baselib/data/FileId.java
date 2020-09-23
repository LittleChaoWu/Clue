package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

public class FileId extends BaseBean {
    private String file_id;

    public FileId(String file_id) {
        this.file_id = file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_id() {

        return file_id;
    }
}
