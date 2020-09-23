package com.sfb.clue;

import android.app.Application;


/**
 * 线索举报模块的Dagger
 */
public class ClueDagger {

    private static ClueComponent clueComponent;

    public static void init(Application app) {
        clueComponent = DaggerClueComponent.builder().clueModule(new ClueModule(app)).build();
    }

    public static ClueComponent getDaggerComponent() {
        return clueComponent;
    }
}
