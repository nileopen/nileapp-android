package com.nile.naliweather.data;

import java.io.Serializable;

public class RealtimeBean implements Serializable {
    private static final long serialVersionUID = -653056510018778467L;
    private String city_code;
    private String city_name;
    private String date;
    private String time;
    private int week;
    private String moon;
    private int dataUptime;
    /**
     * temperature : 30
     * humidity : 37
     * info : 晴
     * img : 0
     */

    private WeatherBean weather;
    /**
     * direct : 北风
     * power : 0级
     * offset : null
     * windspeed : null
     */

    private WindBean wind;

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public int getDataUptime() {
        return dataUptime;
    }

    public void setDataUptime(int dataUptime) {
        this.dataUptime = dataUptime;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public void setWeather(WeatherBean weather) {
        this.weather = weather;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public static class WeatherBean {
        private String temperature;
        private String humidity;
        private String info;
        private String img;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class WindBean {
        private String direct;
        private String power;
        private Object offset;
        private Object windspeed;

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

        public Object getOffset() {
            return offset;
        }

        public void setOffset(Object offset) {
            this.offset = offset;
        }

        public Object getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(Object windspeed) {
            this.windspeed = windspeed;
        }
    }
}