package com.nile.naliweather.api.bean;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public class Item2 extends Item {
    private String img;
    private String date;
    private String weather;
    private String temperature;

    public Item2() {
        this(ITEM_2);
    }

    public Item2(byte type) {
        super(type);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
