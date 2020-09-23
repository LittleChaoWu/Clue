package com.sfb.baselib.components.event;

public class CacheSelectEvent {

    private boolean isSelect;

    public CacheSelectEvent(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
