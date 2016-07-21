package com.nalib.fwk.api;

import com.nalib.fwk.BuildConfig;
import com.nalib.fwk.utils.NaLog;

/**
 * Created by taotao on 16/7/14.
 */
public class NaApiSub {

    public void init() {
        setIsDebug(BuildConfig.DEBUG);
        setLogLevel(NaLog.DEFAULT);
    }

    public void release() {

    }

    public void setIsDebug(boolean debug) {
        NaLog.setIsDebug(debug);
    }

    public void setLogLevel(int level) {
        NaLog.setLogLevel(level);
    }
}
