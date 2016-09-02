package com.nalib.fwk.utils;

import android.content.Context;
import android.view.Surface;
import android.view.WindowManager;

import com.nalib.fwk.NaApi;

/**
 * @author taotao
 *         <p>
 *         Created by taotao on 16/8/31.
 */
public class OrientationUtils {
    public static int getDeviceOrientation() {
        int orientation = 0;
        WindowManager wm = (WindowManager) NaApi.getApi().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
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

    public static int getFrameOrientation(boolean isNotFace, int orientation) {
        int dr = getDeviceOrientation();
        if (isNotFace) {
            dr = 360 - dr;
        }
        return (orientation + dr) % 360;
    }
}
