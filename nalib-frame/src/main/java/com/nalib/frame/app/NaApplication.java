package com.nalib.frame.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class NaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
