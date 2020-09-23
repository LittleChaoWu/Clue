package com.sfb.baselib.components.bus;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public enum ThreadMode {

    /**
     * Main Thread(UI Thread)
     */
    MAIN,
    /**
     * New Thread
     */
    NEW,
    /**
     * Read/Write Thread
     */
    IO,
    /**
     * Computation thread. No io block.
     */
    COMPUTATION,
    /**
     * Running at current thread by sequence
     */
    TRAMPOLINE,
    /**
     * {@link Schedulers#SINGLE}
     */
    SINGLE;

    public static Scheduler getScheduler(ThreadMode threadMode) {
        Scheduler scheduler;
        switch (threadMode) {
            default:
            case MAIN:
                scheduler = AndroidSchedulers.mainThread();
                break;
            case NEW:
                scheduler = Schedulers.newThread();
                break;
            case IO:
                scheduler = Schedulers.io();
                break;
            case COMPUTATION:
                scheduler = Schedulers.computation();
                break;
            case TRAMPOLINE:
                scheduler = Schedulers.trampoline();
                break;
            case SINGLE:
                scheduler = Schedulers.single();
        }
        return scheduler;
    }

}
