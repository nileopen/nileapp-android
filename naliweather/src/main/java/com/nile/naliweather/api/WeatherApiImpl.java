package com.nile.naliweather.api;

import com.nalib.fwk.exception.NaHttpException;
import com.nalib.fwk.http.INaCallBack;
import com.nalib.fwk.http.INaParams;
import com.nalib.fwk.http.INaRequest;
import com.nalib.fwk.http.NaHttpParams;
import com.nalib.fwk.http.NaHttpRequest;
import com.nalib.fwk.http.NaHttpType;
import com.nalib.fwk.http.NaHttpUtil;
import com.nalib.fwk.json.NaJsonUtil;
import com.nalib.fwk.utils.NaLog;
import com.nalib.fwk.utils.ResUtils;
import com.nile.app.naliweather.R;
import com.nile.naliweather.api.bean.Item;
import com.nile.naliweather.api.bean.Item1;
import com.nile.naliweather.api.bean.Item2;
import com.nile.naliweather.data.DataBean;
import com.nile.naliweather.data.Pm25Bean;
import com.nile.naliweather.data.RealtimeBean;
import com.nile.naliweather.data.WeatherDayBean;
import com.nile.naliweather.data.WeatherResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @actor:taotao
 * @DATE: 16/9/4
 */
public class WeatherApiImpl implements WeatherApi {

    //10分钟
    public static final long RESQUEST_INTERVAL = 600000L;
    private final static String Tag = "WeatherApi";
    private final static String ApiUrl = "http://op.juhe.cn/onebox/weather/query";
    private final static String ApiKey = "b2b6bd36fc846b24e10055acfc03792b";
    private static WeatherApi weatherApi;

//    private List<Item> dataInfo;

    private Map<String, List> dataInfoMap = new HashMap(10);

    private long lastRefreshTime = 0;

    private boolean isRefresh = false;

    private WeatherApiCallback callback;

    public static WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            weatherApi = new WeatherApiImpl();
        }
        return weatherApi;
    }

    @Override
    public List<Item> getDataInfo(String cityName) {
        requestDataInfo(cityName);
        return dataInfoMap.get(cityName);
    }

    @Override
    public void setCallback(WeatherApiCallback callback) {
        this.callback = callback;
    }

    private void requestDataInfo(String cityName) {
        //is requesting dataInfo
        if (isRefresh) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        List dataInfo = dataInfoMap.get(cityName);
        if (dataInfo != null && currentTime - lastRefreshTime < RESQUEST_INTERVAL) {
            return;
        }
        lastRefreshTime = currentTime;

        isRefresh = true;

        //send request
        try {
            INaRequest request = new WeatherRequest(cityName, new INaCallBack() {
                @Override
                public void onError(NaHttpException e, int id) {
                    NaLog.e(Tag, "id=" + id + ",err=" + e.getMessage());
                    onResult(Callback.Result_Error, e.getMessage());
                }

                @Override
                public void onResponse(final String response, int id) {
                    NaLog.e(Tag, "id=" + id + ",response=" + response);
                    int result = Callback.Result_Error;
                    String error = "";
                    try {
                        Object obj = NaJsonUtil.stringToBean(response, WeatherResponse.class);
                        final WeatherResponse weatherResponse = (WeatherResponse) obj;
                        error = weatherResponse.getError_code() + "";
                        if (weatherResponse.getError_code() == 0) {
                            parserWeatherData(weatherResponse.getResult().getData());
                        }
                    } catch (Exception e) {
                        error = e.getMessage();
                    }
                    onResult(result, error);
                }
            });

            NaHttpUtil.getNaHttp().setRequest(request);
        } catch (NaHttpException e) {
            onResult(Callback.Result_Error, e.getMessage());
        }

    }


    private void parserWeatherData(DataBean data) {
        RealtimeBean realtime = data.getRealtime();
        Item1 item1 = new Item1();
        String ce = ResUtils.getStringById(R.string.centigrade);
        String sprit = ResUtils.getStringById(R.string.sprit);

        item1.setCityName(realtime.getCity_name());
        item1.setTemperature(realtime.getWeather().getTemperature() + ce);
        item1.setWeather(realtime.getWeather().getInfo());

        Item1.Info info = new Item1.Info();
        info.setName(realtime.getWind().getDirect());
        info.setValue(realtime.getWind().getPower());
        item1.addInfo(info);
        info = new Item1.Info();

        info.setName(ResUtils.getStringById(R.string.item2_humidity));
        info.setValue(realtime.getWeather().getHumidity() + ResUtils.getStringById(R.string.percent));
        item1.addInfo(info);

        Pm25Bean pm25 = data.getPm25();
        info = new Item1.Info();
        info.setName(pm25.getPm25().getQuality());
        info.setValue(pm25.getPm25().getCurPm());
        item1.addInfo(info);


        List<Item> dataInfo = new ArrayList(10);
        dataInfo.add(item1);

        //today
        Item2 item2 = new Item2();
        item2.setWeather(realtime.getWeather().getInfo());
        item2.setImg(realtime.getWeather().getImg());
        item2.setDate(ResUtils.getStringById(R.string.today));
        List<WeatherDayBean> wdbList = data.getWeather();
        WeatherDayBean today = wdbList.get(0);
        item2.setTemperature(today.getInfo().getInfos().get(WeatherDayBean.INFO_NIGHT).getTemperature()
                + ce + " " + sprit + " "
                + today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getTemperature()
                + ce);
        dataInfo.add(item2);

        //tomorrow
        today = wdbList.get(1);
        item2 = new Item2();
        item2.setTemperature(today.getInfo().getInfos().get(WeatherDayBean.INFO_NIGHT).getTemperature()
                + ce + " " + sprit + " "
                + today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getTemperature()
                + ce);
        item2.setDate(ResUtils.getStringById(R.string.tomorrow));
        item2.setImg(today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getImg());
        item2.setWeather(today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getName());
        dataInfo.add(item2);

        //the day after tomrrow
        today = wdbList.get(2);
        item2 = new Item2();
        item2.setTemperature(today.getInfo().getInfos().get(WeatherDayBean.INFO_NIGHT).getTemperature()
                + ce + " " + sprit + " "
                + today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getTemperature()
                + ce);
        item2.setDate(ResUtils.getStringById(R.string.tomorrow));
        item2.setImg(today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getImg());
        item2.setWeather(today.getInfo().getInfos().get(WeatherDayBean.INFO_DAY).getName());
        dataInfo.add(item2);

        dataInfoMap.remove(realtime.getCity_name());
        dataInfoMap.put(realtime.getCity_name(), dataInfo);
    }

    public void onResult(int result, String error) {
        isRefresh = false;
        if (callback != null) {
            callback.onResult(result, error);
        }
    }

    @Override
    public void release() {
        if (dataInfoMap != null) {
            dataInfoMap.clear();
            dataInfoMap = null;
        }
    }

    public static class WeatherRequest extends NaHttpRequest {
        private String city;

        public WeatherRequest(String url, NaHttpType type, INaCallBack callback) {
            super(url, type, callback);
        }

        public WeatherRequest(String city, INaCallBack callback) {
            this(ApiUrl, NaHttpType.Get, callback);
            this.city = city;
            INaParams params = new NaHttpParams();
            try {
                params.addParam("cityname", new String(city.getBytes(), "utf-8"));
                params.addParam("key", ApiKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.setParams(params);
        }
    }
}
