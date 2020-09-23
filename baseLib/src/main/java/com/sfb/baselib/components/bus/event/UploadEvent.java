package com.sfb.baselib.components.bus.event;

public class UploadEvent {
    private String paths;

    public UploadEvent(String paths) {
        this.paths = paths;
    }

    public String getPaths() {
        return paths;
    }
}
