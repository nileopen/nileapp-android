package com.nile.naliweather.api.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public class Item1 extends Item {

    private String temperature;
    private String cityName;
    private String weather;

    private List<Info> infos;

    public Item1() {
        this(ITEM_1);
    }

    public Item1(byte type) {
        super(type);
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void addInfo(Info info) {
        if (info != null) {
            if (infos == null) {
                infos = new ArrayList();
            }
            infos.add(info);
        }
    }

    public static class Info {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
