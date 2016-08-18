package com.nalib.fwk;

import android.content.Context;

import com.nalib.fwk.api.NaApiSub;

/**
 * Created by taotao on 16/7/14.
 */
public class NaApi {
    private static NaApiSub sApi;

    public static void init(Context context) {
        if (sApi == null) {
            synchronized (NaApi.class) {
                if (sApi == null) {
                    sApi = new NaApiSub();
                    sApi.init(context);
                }
            }
        }
    }

    public static void release() {
        if (sApi != null) {
            sApi.release();
        }
        sApi = null;
    }

    public static void setIsDebug(boolean debug) {
        if (sApi != null){
            sApi.setIsDebug(debug);
        }
    }

    public static void setLogLevel(int level) {
        if (sApi != null){
            sApi.setLogLevel(level);
        }
    }

    public static NaApiSub getApi() {
        return sApi;
    }
}
