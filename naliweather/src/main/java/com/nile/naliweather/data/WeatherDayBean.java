package com.nile.naliweather.data;

import com.nalib.fwk.utils.NaLog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 16/9/1.
 */
public class WeatherDayBean implements Serializable {

    public static final String INFO_DRAW = "draw";
    public static final String INFO_DAY = "day";
    public static final String INFO_NIGHT = "night";
    private static final long serialVersionUID = 1217262543414429711L;
    private static final String Tag = "WeatherDayBean";
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
        private List<String> dawn;
        private List<String> day;
        private List<String> night;

        private Map<String, WeatherInfo> infos;

        public List<String> getDawn() {
            return dawn;
        }

        public void setDawn(List<String> dawn) {
            this.dawn = dawn;
        }

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

        public Map<String, WeatherInfo> getInfos() {
            if (infos == null) {
                infos = new HashMap(3);
                addWeatherInfo(INFO_DRAW, dawn);
                addWeatherInfo(INFO_DAY, day);
                addWeatherInfo(INFO_NIGHT, night);
            }
            return infos;
        }

        private void addWeatherInfo(String type, List<String> list) {
            if (type != null && list != null && list.size() > 5) {
                WeatherInfo info = new WeatherInfo();
                info.setImg(list.get(0));
                info.setName(list.get(1));
                try {
                    info.setTemperature(Integer.parseInt(list.get(2)));
                } catch (Exception e) {
                    NaLog.w(Tag, "addWeatherInfo setTemperature err=" + e.getMessage());
                }
                info.setDirect(list.get(3));
                info.setPower(list.get(4));
                info.setTime(list.get(5));
                infos.put(type, info);
            }
        }
    }

    /*
     *{
     * "img":"3",
     *"name":"阵雨",
     *"temperature":25,
     *"direct":"东北风",
     *"power":"微风",
     *"time":"18:12"
     * }
     * */
    public static class WeatherInfo {
        private String img;
        private String name;
        private int temperature;
        private String direct;
        private String power;
        private String time;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
