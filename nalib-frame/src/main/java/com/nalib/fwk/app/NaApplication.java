package com.nalib.fwk.app;

import android.app.Application;

import com.nalib.fwk.NaApi;
import com.nalib.fwk.http.NaHttpUtil;

public class NaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NaApi.init(this);
        NaHttpUtil.init();
//        LeakCanary.install(this);
    }
}
