package com.example.zknetty.zkNetty.Beans;

/**
 * @Author Nicht
 * @description  定位信息
 * @Time 2021/4/12
 * @Link
 */
public class LocateMsg {
    private  String  lon; //bd_09
    private  String  lat;//bd_09
    private  String  alt;//高度
    private  String  speed;//速度
    private  String  time;//定位时间
    private  String  mileage;//里程（m），上报该定位时间里程表上的里程值，如无里程表填0.
    private  String  gps_status;//卫星状态：0-网络定位，1-定位成功
    private  String  wrist_id;//固定000000
    private  String  data_type;//0为普通定位数据 1为低电报警数据 2为关机报警数据 3为腕带剪断报警数据 6为心率异常报警数据 -1为签到数据
    private  String  battery;//电量
    private  String  cutoff_times;//剪断次数，固定为0
    private  String  cutoff_status;//剪断状态：0为闭合，1位剪断
    private  String  sparate_status;//固定为-1，即 0xFF
    private  String  heart_rate;//心率值，取值范围0-255(unsigned char类型)

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

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getGps_status() {
        return gps_status;
    }

    public void setGps_status(String gps_status) {
        this.gps_status = gps_status;
    }

    public String getWrist_id() {
        return wrist_id;
    }

    public void setWrist_id(String wrist_id) {
        this.wrist_id = wrist_id;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCutoff_times() {
        return cutoff_times;
    }

    public void setCutoff_times(String cutoff_times) {
        this.cutoff_times = cutoff_times;
    }

    public String getCutoff_status() {
        return cutoff_status;
    }

    public void setCutoff_status(String cutoff_status) {
        this.cutoff_status = cutoff_status;
    }

    public String getSparate_status() {
        return sparate_status;
    }

    public void setSparate_status(String sparate_status) {
        this.sparate_status = sparate_status;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    @Override
    public String toString() {
        return "LocateMsg{" +
                "lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", alt='" + alt + '\'' +
                ", speed='" + speed + '\'' +
                ", time='" + time + '\'' +
                ", mileage='" + mileage + '\'' +
                ", gps_status='" + gps_status + '\'' +
                ", wrist_id='" + wrist_id + '\'' +
                ", data_type='" + data_type + '\'' +
                ", battery='" + battery + '\'' +
                ", cutoff_times='" + cutoff_times + '\'' +
                ", cutoff_status='" + cutoff_status + '\'' +
                ", sparate_status='" + sparate_status + '\'' +
                ", heart_rate='" + heart_rate + '\'' +
                '}';
    }
}
