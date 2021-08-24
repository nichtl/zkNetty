package com.example.zknetty.zkNetty.Beans;

/**
 * @Author Nicht
 * @description 短信
 * @Time 2021/4/12
 * @Link
 */
public class Sms {
    private  String text_len;
    private  String text;
    private  String lon;
    private  String lat;
    private  String alt;//高度
    private  String time;//发送时间

    public String getText_len() {
        return text_len;
    }

    public void setText_len(String text_len) {
        this.text_len = text_len;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "text_len='" + text_len + '\'' +
                ", text='" + text + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", alt='" + alt + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
