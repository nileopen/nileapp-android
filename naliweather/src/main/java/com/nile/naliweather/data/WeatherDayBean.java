package com.nile.naliweather.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by taotao on 16/9/1.
 */
public class WeatherDayBean implements Serializable {

    private static final long serialVersionUID = 1217262543414429711L;
    /**
     * date : 2016-08-31
     * info : {"day":["0","晴","34","西南风","微风","05:29"],"night":["1","多云","25","西南风","微风","18:19"]}
     * week : 三
     * nongli : 七月廿九
     */

    private String date;
    private InfoBean info;
    private String week;
    private String nongli;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getNongli() {
        return nongli;
    }

    public void setNongli(String nongli) {
        this.nongli = nongli;
    }

    public static class InfoBean {
        private List<String> day;
        private List<String> night;

        public List<String> getDay() {
            return day;
        }

        public void setDay(List<String> day) {
            this.day = day;
        }

        public List<String> getNight() {
            return night;
        }

        public void setNight(List<String> night) {
            this.night = night;
        }
    }
}
