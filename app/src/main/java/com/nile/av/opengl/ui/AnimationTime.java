package com.nile.av.opengl.ui;

import android.os.SystemClock;

/**
 * Created by taotao on 16/7/29.
 */
public class AnimationTime {
    private static volatile long sTime;

    public static void update() {
        sTime = SystemClock.uptimeMillis();
    }

    public static long get() {
        return sTime;
    }

    public static long startTime() {
        sTime = SystemClock.uptimeMillis();
        return sTime;
    }
}
