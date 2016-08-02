package com.nile.av.opengl.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

import com.nalib.fwk.utils.NaLog;

/**
 * Created by taotao on 16/7/29.
 */
public class Utils {

    public static String Tag = "";
    public static boolean USE_888_PIXEL_FORMAT = false;

    static {
        USE_888_PIXEL_FORMAT = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
    }

    public static int getGLVersion(Context context) {
        int ret = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && context != null) {
            ActivityManager var1 = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (null != var1) {
                ConfigurationInfo var2 = var1.getDeviceConfigurationInfo();
                if (null != var2) {
                    return var2.reqGlEsVersion >= 0x20000 ? 2 : 1;
                }
                NaLog.d(Tag, "getDeviceConfigurationInfo Error");
            }

            NaLog.d(Tag, "getSystemService Error");

        }
        return ret;
    }
}
