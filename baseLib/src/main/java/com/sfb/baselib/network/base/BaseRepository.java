package com.sfb.baselib.network.base;

public class BaseRepository {
    private static BaseRepository mInstance;

    HttpHelper mHttpHelper;

    private BaseRepository(){}

    public static BaseRepository get() {
        if (mInstance == null) {
            synchronized (BaseRepository.class) {
                if (mInstance == null) {
                    mInstance = new BaseRepository();
                }
            }
        }
        return mInstance;
    }
}
