package com.nile.naliweather.app;

import com.nalib.fwk.NaApi;
import com.nalib.fwk.app.NaApplication;
import com.nalib.fwk.http.NaHttpUtil;
import com.nalib.fwk.utils.NaLog;

/**
 * Created by taotao on 16/8/31.
 */
public class NaliWeatherApp extends NaApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NaApi.setIsDebug(true);
        NaApi.setLogLevel(NaLog.VERBOSE);
        NaHttpUtil.init();
    }
}
