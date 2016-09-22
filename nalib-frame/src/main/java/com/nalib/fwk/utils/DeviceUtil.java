package com.nalib.fwk.utils;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by taotao on 16/7/15.
 */
public class DeviceUtil {

    public static boolean checkBuildVersion(int sdkInt) {
        return Build.VERSION.SDK_INT >= sdkInt;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

}
