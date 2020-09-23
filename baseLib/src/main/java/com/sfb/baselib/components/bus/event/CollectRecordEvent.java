package com.sfb.baselib.components.bus.event;


import com.sfb.baselib.database.data.CollectRecordBean;

public class CollectRecordEvent {

    private CollectRecordBean collectRecordBean;
    private boolean isSuccess;

    public CollectRecordEvent(CollectRecordBean collectRecordBean) {
        this.collectRecordBean = collectRecordBean;
        this.isSuccess = true;
    }

    public CollectRecordEvent(CollectRecordBean collectRecordBean, boolean isSuccess) {
        this.collectRecordBean = collectRecordBean;
        this.isSuccess = isSuccess;
    }

    public CollectRecordBean getCollectRecordBean() {
        return collectRecordBean;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
