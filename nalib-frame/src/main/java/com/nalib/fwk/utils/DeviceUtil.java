package com.nalib.fwk.utils;

import android.os.Build;

/**
 * Created by taotao on 16/7/15.
 */
public class DeviceUtil {

    public static boolean checkBuildVersion(int sdkInt) {
        return Build.VERSION.SDK_INT >= sdkInt;
    }
}
