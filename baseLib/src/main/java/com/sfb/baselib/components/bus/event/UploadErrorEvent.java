package com.sfb.baselib.components.bus.event;

public class UploadErrorEvent {
    private String paths;

    public UploadErrorEvent(String paths) {
        this.paths = paths;
    }

    public String getPaths() {
        return paths;
    }
}
