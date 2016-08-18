package com.nalib.fwk.api;

import android.content.Context;
import android.view.Surface;
import android.view.WindowManager;

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

    public void setIsDebug(boolean debug) {
        NaLog.setIsDebug(debug);
    }

    public void setLogLevel(int level) {
        NaLog.setLogLevel(level);
    }

    public int getDeviceOrientation() {
        int orientation = 0;
        WindowManager wm = (WindowManager) mApplicationContext.getSystemService(Context.WINDOW_SERVICE);
        switch (wm.getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_90: {
                orientation = 90;
                break;
            }
            case Surface.ROTATION_180: {
                orientation = 180;
                break;
            }
            case Surface.ROTATION_270: {
                orientation = 270;
                break;
            }
            case Surface.ROTATION_0:
            default: {
                orientation = 0;
                break;
            }
        }
        return orientation;
    }

    public int getFrameOrientation(boolean isNotFace, int orientation) {
        int dr = getDeviceOrientation();
        if (isNotFace) {
            dr = 360 - dr;
        }
        return (orientation + dr) % 360;
    }
}
