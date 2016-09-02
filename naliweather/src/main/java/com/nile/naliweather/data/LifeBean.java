package com.nile.naliweather.data;

import java.io.Serializable;
import java.util.List;

public class LifeBean implements Serializable {
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
    }
}
