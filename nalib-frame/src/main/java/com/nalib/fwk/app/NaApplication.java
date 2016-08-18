package com.nalib.fwk.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nalib.fwk.NaApi;
import com.nalib.fwk.utils.NaLog;

public class NaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        NaApi.init(this);
        NaApi.setIsDebug(true);
        NaApi.setLogLevel(NaLog.VERBOSE);
    }
}
