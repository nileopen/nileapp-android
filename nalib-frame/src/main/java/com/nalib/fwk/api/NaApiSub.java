package com.nalib.fwk.api;

import android.content.Context;

import com.nalib.fwk.BuildConfig;
import com.nalib.fwk.utils.NaLog;

/**
 * Created by taotao on 16/7/14.
 */
public class NaApiSub {
    private Context mApplicationContext;

    public void init(Context context) {
        setIsDebug(BuildConfig.DEBUG);
        setLogLevel(NaLog.DEFAULT);
        mApplicationContext = context;
    }

    public void release() {

    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public void setIsDebug(boolean debug) {
        NaLog.setIsDebug(debug);
    }

    public void setLogLevel(int level) {
        NaLog.setLogLevel(level);
    }
}
