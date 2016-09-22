package com.nile.naliweather.api;

/**
 * @actor:taotao
 * @DATE: 16/9/4
 */
public interface Callback {
    int Result_Error = 0;
    int Result_Success = 1;

    void onResult(int ret, String error);
}
