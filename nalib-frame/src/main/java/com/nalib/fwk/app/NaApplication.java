package com.nalib.fwk.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nalib.fwk.NaApi;

public class NaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        NaApi.init();
    }
}
