package com.nile.uninstall;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * Created by taotao on 16/8/1.
 */
public class UninstallJni {
    private final static String Tag = "UninstallJni";
    private static boolean isRegister = false;

    static {
        try {
            System.loadLibrary("uninstall");
        } catch (Throwable e) {
            Log.e(Tag, "load libaray failed", e);
        }
    }

    private String mUrl = null;

    private native void uninstall(String packageName, int sdkInt);

    public void registerUninstall(Context context) {
        if (!isRegister) {
            String packageName = "/data/data/" + context.getApplicationContext().getPackageName();
            uninstall(packageName, Build.VERSION.SDK_INT);
            isRegister = true;
        }
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

//    // 由于targetSdkVersion低于17，只能通过反射获取
//    public String getUserSerial(Context context) {
//        Object userManager = context.getSystemService(Context.USER_SERVICE);
//        if (userManager == null) {
//            Log.e(Tag, "userManager not exsit !!!");
//            return null;
//        }
//
//        try {
//            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
//            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
//
//            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
//            long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
//            return String.valueOf(userSerial);
//        } catch (NoSuchMethodException e) {
//            Log.e(Tag, "", e);
//        } catch (IllegalArgumentException e) {
//            Log.e(Tag, "", e);
//        } catch (IllegalAccessException e) {
//            Log.e(Tag, "", e);
//        } catch (InvocationTargetException e) {
//            Log.e(Tag, "", e);
//        }
//
//        return null;
//    }
}
