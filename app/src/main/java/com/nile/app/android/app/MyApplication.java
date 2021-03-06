package com.nile.app.android.app;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nalib.fwk.NaApi;
import com.nalib.fwk.app.NaApplication;
import com.nalib.fwk.utils.NaLog;

public class MyApplication extends NaApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        NaApi.setIsDebug(true);
        NaApi.setLogLevel(NaLog.VERBOSE);
    }
}
