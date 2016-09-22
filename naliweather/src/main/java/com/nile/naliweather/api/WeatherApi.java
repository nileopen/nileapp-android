package com.nile.naliweather.api;

import com.nile.naliweather.api.bean.Item;

import java.util.List;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public interface WeatherApi extends Api {
    List<Item> getDataInfo(String cityName);

    void setCallback(WeatherApiCallback callback);
}
