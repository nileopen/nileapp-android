package com.nalib.fwk.http;

import com.nalib.fwk.http.okhttp.NaOkHttp;

/**
 * Created by taotao on 16/8/31.
 */
public class NaHttpUtil {
    public final static String TYEP_OKHTTP = "okhttp";
    private static NaHttpUtil sInstance;
    private INaHttp mNaHttp;
    private String type = TYEP_OKHTTP;

    public NaHttpUtil() {
        if (mNaHttp == null) {
            if (type.equals(TYEP_OKHTTP)) {
                mNaHttp = new NaOkHttp();
            }
        }
    }

    public static void init() {
        if (sInstance == null) {
            sInstance = new NaHttpUtil();
        }
    }

    public static INaHttp getNaHttp() {
        if (sInstance == null) {
            sInstance = new NaHttpUtil();
        }
        return sInstance.getNaHttpSub();
    }

    public static void release() {
        if (sInstance != null) {
            sInstance.destroy();
            sInstance = null;
        }
    }

    private INaHttp getNaHttpSub() {
        return mNaHttp;
    }

    private void destroy() {
        mNaHttp = null;
    }
}
