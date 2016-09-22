package com.nile.naliweather.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeBean implements Serializable {
    public final static String TYPE_CHUANYI = "chuanyi";
    public final static String TYPE_GANMAO = "ganmao";
    public final static String TYPE_KONGTIAO = "kongtiao";
    public final static String TYPE_WURAN = "wuran";
    public final static String TYPE_XICHE = "xiche";
    public final static String TYPE_YUDONG = "yundong";
    public final static String TYPE_ZIWAIXIAN = "ziwaixian";
    private static final long serialVersionUID = -2448480186196501689L;
    private String date;
    private InfoBean info;

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

    public static class InfoBean {
        private List<String> chuanyi;
        private List<String> ganmao;
        private List<String> kongtiao;
        private List<String> wuran;
        private List<String> xiche;
        private List<String> yundong;
        private List<String> ziwaixian;

        private Map<String, LifeInfo> infos;

        public List<String> getChuanyi() {
            return chuanyi;
        }

        public void setChuanyi(List<String> chuanyi) {
            this.chuanyi = chuanyi;
        }

        public List<String> getGanmao() {
            return ganmao;
        }

        public void setGanmao(List<String> ganmao) {
            this.ganmao = ganmao;
        }

        public List<String> getKongtiao() {
            return kongtiao;
        }

        public void setKongtiao(List<String> kongtiao) {
            this.kongtiao = kongtiao;
        }

        public List<String> getWuran() {
            return wuran;
        }

        public void setWuran(List<String> wuran) {
            this.wuran = wuran;
        }

        public List<String> getXiche() {
            return xiche;
        }

        public void setXiche(List<String> xiche) {
            this.xiche = xiche;
        }

        public List<String> getYundong() {
            return yundong;
        }

        public void setYundong(List<String> yundong) {
            this.yundong = yundong;
        }

        public List<String> getZiwaixian() {
            return ziwaixian;
        }

        public void setZiwaixian(List<String> ziwaixian) {
            this.ziwaixian = ziwaixian;
        }

        public Map<String, LifeInfo> getLifeInfoList() {
            if (infos == null) {
                infos = new HashMap();
                addLifeInfo(TYPE_CHUANYI, getChuanyi());
                addLifeInfo(TYPE_GANMAO, getGanmao());
                addLifeInfo(TYPE_KONGTIAO, getKongtiao());
                addLifeInfo(TYPE_WURAN, getWuran());
                addLifeInfo(TYPE_XICHE, getXiche());
                addLifeInfo(TYPE_YUDONG, getYundong());
                addLifeInfo(TYPE_ZIWAIXIAN, getZiwaixian());
            }

            return infos;
        }

        private void addLifeInfo(String key, List<String> list) {
            if (key != null && list != null && list.size() > 1) {
                LifeInfo info = new LifeInfo();
                info.setTitle(list.get(0));
                info.setDes(list.get(1));
                infos.put(key, info);
            }
        }
    }

    public static class LifeInfo {
        private String title;
        private String des;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
