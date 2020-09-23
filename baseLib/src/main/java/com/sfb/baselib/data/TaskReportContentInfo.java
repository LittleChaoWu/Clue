package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;

import java.util.List;

public class TaskReportContentInfo extends BaseBean {
    private String content;
    //空串表示正常的汇报，
    //"clue"表示线索任务汇报，
    //"car-info-collection"车辆采集任务,
    //"house-info-collection"表示出租屋任务汇报
    private String type;
    private List<Integer> files;

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFiles(List<Integer> files) {
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public List<Integer> getFiles() {
        return files;
    }
}
