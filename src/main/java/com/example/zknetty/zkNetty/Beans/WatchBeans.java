package com.example.zknetty.zkNetty.Beans;


import com.nicht.promote.fileservice.zkNetty.Utils.WatchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Nicht
 * @description  承接所有信息转发
 * @ 2021/4/6
 */
public class WatchBeans {
    private  String    begin;
    private  String    end;
    private  String    imei;
    private  String    mainpro;
    private  String    secpro;
    private  String    datalen;
    //文件名称
    private  String    filename;
    private  List<byte[]>  bytesdata  =new ArrayList<>();
    //定位信息
    private  LocateMsg locateMsg;
    //短信信息
    private  Sms       sms;
    //文件信息
    private  FileInfo  fileInfo;
    private  Boolean   CommandSendSuccess =false;
    private  String    ReceiveTime;//指令接收时间
    private  String    SendTime;  //指令发送时间
    private  String    Str_Data;//储存文本数据
    /*取出数据并清空*/
    public  List<byte[]> getBytesdata() {
        List<byte[]>  tempdata  = WatchUtils.depCopy(bytesdata);
        this.bytesdata.clear();
        return  tempdata;
    }

    public  void putBytesdata(byte[]  databytes) {
        this.bytesdata.add(databytes);
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMainpro() {
        return mainpro;
    }

    public void setMainpro(String mainpro) {
        this.mainpro = mainpro;
    }

    public String getSecpro() {
        return secpro;
    }

    public void setSecpro(String secpro) {
        this.secpro = secpro;
    }

    public String getDatalen() {
        return datalen;
    }

    public void setDatalen(String datalen) {
        this.datalen = datalen;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocateMsg getLocateMsg() {
        return locateMsg;
    }

    public void setLocateMsg(LocateMsg locateMsg) {
        this.locateMsg = locateMsg;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Boolean getCommandSendSuccess() {
        return CommandSendSuccess;
    }

    public void setCommandSendSuccess(Boolean commandSendSuccess) {
        CommandSendSuccess = commandSendSuccess;
    }

    public String getReceiveTime() {
        return ReceiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        ReceiveTime = receiveTime;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getStr_Data() {
        return Str_Data;
    }


    public void setStr_Data(String str_Data) {
        Str_Data = str_Data;
    }

    public WatchBeans(){}


    @Override
    public String toString() {
        return "WatchBeans{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", imei='" + imei + '\'' +
                ", mainpro='" + mainpro + '\'' +
                ", secpro='" + secpro + '\'' +
                ", datalen='" + datalen + '\'' +
                ", filename='" + filename + '\'' +
                ", locateMsg=" + locateMsg +
                ", sms=" + sms +
                ", fileInfo=" + fileInfo +
                '}';
    }
}
