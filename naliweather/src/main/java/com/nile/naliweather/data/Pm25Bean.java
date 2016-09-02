package com.nile.naliweather.data;

import java.io.Serializable;

public class Pm25Bean implements Serializable {
    private static final long serialVersionUID = 1464156265451384142L;
    private String key;
    private int show_desc;
    /**
     * curPm : 79
     * pm25 : 36
     * pm10 : 73
     * level : 2
     * quality : 良
     * des : 可以接受的，除极少数对某种污染物特别敏感的人以外，对公众健康没有危害。
     */

    private Pm25 pm25;
    private String dateTime;
    private String cityName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getShow_desc() {
        return show_desc;
    }

    public void setShow_desc(int show_desc) {
        this.show_desc = show_desc;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static class Pm25 {
        private String curPm;
        private String pm25;
        private String pm10;
        private int level;
        private String quality;
        private String des;

        public String getCurPm() {
            return curPm;
        }

        public void setCurPm(String curPm) {
            this.curPm = curPm;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
