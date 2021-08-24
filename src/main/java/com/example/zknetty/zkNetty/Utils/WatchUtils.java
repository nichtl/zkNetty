package com.example.zknetty.zkNetty.Utils;

import cn.hutool.core.util.HexUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author Nicht
 * @description  腕表对接指令工具
 * @Time 2021/4/12
 * @Link
 */
public class WatchUtils {
    public static void main(String[] args) {
        System.out.println(HexUtil.decodeHexStr("e7a6bbe5bc80e58cbae59f9f28e58d97e4baac29e88c83e59bb4e68aa5e8ada600"));
        System.out.println(CreateSMSCommandStr("离开区域(南京)范围报警"));
        System.out.println( );
        byte[]  b   =  new byte[4];  b[0]=01;b[1]=02;b[2]=03;b[3]=05;
        byte[]  d   =  new byte[4];  b[0]=05;b[1]=04;b[2]=07;b[3]=06;
        List<byte[]> list= new ArrayList<>();
        list.add(b);list.add(d);
        List<byte[]> bg = WatchUtils.depCopy(list);
        System.out.println( list==bg ?"相同":"不同");
        String res = HexUtil.encodeHexStr("*LOLAT") + "07" + "0800" + HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket())) + HexUtil.encodeHexStr("#");
        System.out.println(res);
        String hs  =HexUtil.encodeHexStr(ByteUtils.reverseBytes(WatchUtils.shortToByteArray("8"))) ;
        System.out.println(hs);
    }

    /**
     * @Description 生成短信指令
     * @param data
     * @return 16进制字符
     */
    public static   String  CreateSMSCommandStr(String data){
       data = WatchUtils.CreateMissionData(data);
       StringBuilder  sb  = new StringBuilder();
       sb.append(HexUtil.encodeHexStr(WatchConstant.Begin))
      .append(WatchConstant.IssueMainPro)
      .append(WatchConstant.IssueSecProCode.TextMsg)
      .append(HexUtil.encodeHexStr(ByteUtils.reverseBytes(WatchUtils.shortToByteArray(Integer.toString(data.length()/2)))))
      .append(data)
      .append(HexUtil.encodeHexStr(WatchConstant.End));
     return   sb.toString();
    }

    /**
     * @Description  生成定位间隔指令
     * @param report_rate  上报频率  平台默认 3600秒名称
     * @param StartTime   每天开始上报时间，终端默认00:00:00
     * @param EndTime     每天结束上报时间，终端默认23:59:59
     * @return 16进制字符
     */
    public static String  CreateIntervalCommandStr(Integer report_rate,String StartTime,String EndTime){
        return  CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.LocateInterval, WatchUtils.CreateIntervalSettingData(report_rate,StartTime,EndTime));
    }

    /**
     * @Description  生成SOS联系人指令
     * @param Name
     * @param TelPhoneNumber
     * @return
     */
    public static String  CreateSosCommandStr(String Name,String TelPhoneNumber){
        return  CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.SetSosNum, WatchUtils.CreateSOsSettingData(Name, TelPhoneNumber));
    }

    /**
     * @Description 生成获取设备参数指令
     * @return 16进制字符
     */
    public static String CreateSyncDeviceSettingStr(){
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.SyncDeviceSetting,"");
    }

    /** @Description 生成允许呼入电话号码指令  最多设置20个不同号码 不同号码以逗号隔开，例如13011112222,13122223333,13344445555, 13577775555
     * @param TelPhoneNumber
     * @return
     */
    public static String CreateAllowCallTelStr(String TelPhoneNumber){
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.AllowCallNum, WatchUtils.CreateAllowCallSettingData(TelPhoneNumber));
    }

    /**
     * @Description   设置心率范围报警  格式为  Min_Heart_rate,Max_Heart_rate  如 40,150
     * @param Min_Heart_rate  最低心率
     * @param Max_Heart_rate  最高心率
     * @return 16 进制
     */
    public static String CreateHeartRateWarrning(String Min_Heart_rate,String Max_Heart_rate){
        String HexStr = Min_Heart_rate+","+Max_Heart_rate;
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.Heart_Rate_Warnning,HexUtil.encodeHexStr(HexStr,Charset.forName("utf-8")));
    }

    /**
     * @Description   设置通讯IP地址  格式为  ip,port  如 192.168.5.238,8080
     * @param Ip   ip
     * @param Port  port
     * @return 16 进制
     */
    public static String CreateIpPortCommandStr(String Ip,String Port){
        String HexStr = Ip+","+Port;
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.IpPort,HexUtil.encodeHexStr(HexStr,Charset.forName("utf-8")));
    }

    /**
     * @Description   下发报警提醒命令  格式为  Min_Val,Max_Val  如 10,120
     * @param Min_Val
     * @param Max_Val
     * @return 16 进制
     */
    public static String CreateWarrningNoticeCommandStr(String Min_Val,String Max_Val){
        String HexStr = Min_Val+","+Max_Val;
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.WarnningNotice,HexUtil.encodeHexStr(HexStr,Charset.forName("utf-8")));
    }

    /**
     * @Description   下发报警提醒时间段命令  格式为  StartTime-EndTime
     * @param StartTime 00:00:00
     * @param EndTime 23:59:59
     * @return 16 进制
     */
    public static String CreateWarnningPeriodCommandStr(String StartTime,String EndTime){
        String HexStr = StartTime+"-"+EndTime;
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.WarnningPeriod,HexUtil.encodeHexStr(HexStr,Charset.forName("utf-8")));
    }

    /**
     * @Description 生成回复出厂设置参数指令
     * @return 16进制字符
     */
    public static  String CreateResetSettingStr(){
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.ResetPara,"");
    }

    /**
     * @Description  生成自动录音指令  上传音频数据包
     * @param report_rate  录音时长 最多30
     * @param StartTime   每天开始上报时间，终端默认00:00:00
     * @param EndTime     每天结束上报时间，终端默认23:59:59
     * @return 16进制字符
     */
    public static   String  CreateAutoRecordingCommandStr(Integer report_rate,String StartTime,String EndTime){
        return  CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.Auto_Recording, WatchUtils.CreateAutoRecordingSettingData(report_rate,StartTime,EndTime));
    }

    /**
     * @Description  生成关机指令
     * @return 16进制字符
     */
    public static   String  CreateShutDownCommandStr(){
        return  CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.Shut_Down, WatchUtils.CreateShutDownSettingData(0,"",""));
    }

    /**
     * @Description  生成设备重启指令
     * @return 16进制字符
     */
    public static   String  CreateReStartCommandStr(){
        return  CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.Restart, WatchUtils.CreateShutDownSettingData(0,"",""));
    }

    /**
     * @Description   生成wifi连接指令  格式为  WIFINAME,WIFIPASSWORD  如 TPLINK_4092,12345678
     * @param WIFINAME
     * @param WIFIPASSWORD
     * @return 16 进制
     */
    public static String CreateWIFICommandStr(String WIFINAME,String WIFIPASSWORD){
        String HexStr = WIFINAME+","+WIFIPASSWORD;
        return  WatchUtils.CreateCommandStr(WatchConstant.IssueMainPro, WatchConstant.IssueSecProCode.Wifi,HexUtil.encodeHexStr(HexStr,Charset.forName("utf-8")));
    }


    /**
     * @Description 统一指令生成 data必须为16进制
     * @param MainPro    主指令
     * @param SecProCode 副指令
     * @param data   16进制数据需要自己处理
     * @return
     */
    public static   String  CreateCommandStr(String MainPro,String SecProCode,String data){
        StringBuilder  sb  = new StringBuilder();
        sb.append(HexUtil.encodeHexStr(WatchConstant.Begin))
                .append(MainPro)
                .append(SecProCode)
                .append(HexUtil.encodeHexStr(ByteUtils.reverseBytes(WatchUtils.shortToByteArray(Integer.toString(data.length()/2)))))
                .append(data)
                .append(HexUtil.encodeHexStr(WatchConstant.End));
        return   sb.toString();
    }

    /**@Descrption 将short转为byte数组*/
    private static byte [] shortToByteArray(String  s) {
        byte [] shortBuf = new byte [2];
        for(int i=0;i<2;i++) {
            int offset = (shortBuf.length - 1 -i)*8;
            shortBuf[i] = (byte )((Short.parseShort(s)>>>offset)&0xff);
        }
        return shortBuf;
    }

    /**
     * @Description    短信文本数据
     * @param content 平台下发文本信息
     * @return
     */
    private static String CreateMissionData(String content){
        content=content+"\0";
        content =HexUtil.encodeHexStr(content,Charset.forName("utf-8"));
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("0000000000000000")
                    .append(HexUtil.encodeHexStr(ByteUtils.reverseBytes(WatchUtils.shortToByteArray(Integer.toString(content.length()/2)))))
                    .append(content)
                    .append("000000000000000000000000")
                    .append(CreateTDateTime());
        }catch (Exception e){e.printStackTrace();}
        return  sb.toString();
    }

    /**
     * @Description   设置定位间隔数据   SettingData
     * @param report_rate  上报频率  平台默认 3600秒名称
     * @param StartTime   每天开始上报时间，终端默认00:00:00
     * @param EndTime     每天结束上报时间，终端默认23:59:59
     * @return 16进制字符
     */
    private static String CreateIntervalSettingData(Integer report_rate,String StartTime,String EndTime ){
        StringBuilder  sb = new StringBuilder();
        if(report_rate<3600) {return "平台默认频率";}
        sb.append(HexUtil.encodeHexStr(ByteUtils.IntToByteArray(report_rate)))
                .append(WatchUtils.CreateTTime(StartTime))
                .append(WatchUtils.CreateTTime(EndTime));
        return  sb.toString();
    }
    /**
     * @Description   设置SOS数据 SettingData  最多设置三个 多个使用逗号分隔 李:15977775555,刘:18955556666
     * @param Name    联系人姓名
     * @param TelPhoneNumber   电话号码
     * @return 16进制字符
     */
    private static String CreateSOsSettingData(String Name,String TelPhoneNumber ){
        StringBuilder  sb = new StringBuilder();
        sb.append(Name).append(":").append(TelPhoneNumber);
        return  HexUtil.encodeHexStr(sb.toString(),Charset.forName("utf-8"));
    }
    /**
     * @Description       设置自动录音数据最多30秒  SettingData  录音时长,最多30秒，录完后会自动上报音频数据包
     * @param report_rate  录音时长,最多30秒
     * @param StartTime   开始时间，终端默认00:00:00
     * @param EndTime     结束时间，终端默认23:59:59
     * @return 16进制字符
     */
    private static String CreateAutoRecordingSettingData(Integer report_rate,String StartTime,String EndTime ){
        StringBuilder  sb = new StringBuilder();
        sb.append(HexUtil.encodeHexStr(ByteUtils.IntToByteArray(report_rate)))
                .append(WatchUtils.CreateTTime(StartTime))
                .append(WatchUtils.CreateTTime(EndTime));
        return  sb.toString();
    }
    /**
     * @Description        设置关机
     * @param report_rate  0
     * @param StartTime   0
     * @param EndTime     0
     * @return 16进制字符
     */
    private static String CreateShutDownSettingData(Integer report_rate,String StartTime,String EndTime ){
        StringBuilder  sb = new StringBuilder();
        sb.append(HexUtil.encodeHexStr(ByteUtils.IntToByteArray(report_rate)))
                .append(WatchUtils.CreateTTime(StartTime))
                .append(WatchUtils.CreateTTime(EndTime));
        return  sb.toString();
    }
    /**
     * @Description 设置允许呼叫电话设置 多个逗号分隔 13011112222,
     * @param TelPhoneNumber
     * @return
     */
    private static String CreateAllowCallSettingData(String TelPhoneNumber){
    return   HexUtil.encodeHexStr(TelPhoneNumber,Charset.forName("utf-8"));
    }

    /**
     * @Description  生成TDateTime 16进制字符
     * @return   time格式 21 04 13 12 05 06 00 00
     */
    private static  String CreateTDateTime(){
        StringBuilder  sb = new StringBuilder();
        sb.append(HexUtil.encodeHexStr(getTimeByCalendar())).append("0000");
        return   sb.toString();
    }
    /**
     * @Description  16进制 TTime字符
     * @param time   格式  00:00:00
     * @return
     */
    private static  String CreateTTime(String  time){
        byte[] timeByte =new byte[3];
        String[]  times  =time.split(":");
        timeByte[0] = Byte.parseByte(times[0],10);
        timeByte[1] = Byte.parseByte(times[1],10);
        timeByte[2] = Byte.parseByte(times[2],10);
        /*毫秒默认00*/
        String HexTTime = HexUtil.encodeHexStr(timeByte)+"0000";
        return HexTTime ;
    }
    /**@Descrption 生成当前时分秒 10进制数组*/
    public  static byte[] getTimeByCalendar(){
        byte[] timeByte =new byte[6];
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute=cal.get(Calendar.MINUTE);//分
        int second=cal.get(Calendar.SECOND);//秒
        timeByte[0]=Byte.parseByte(Integer.toString(year).substring(2,4),10) ;
        timeByte[1]=Byte.parseByte(Integer.toString(month),10) ;
        timeByte[2]=Byte.parseByte(Integer.toString(day),10) ;
        timeByte[3]=Byte.parseByte(Integer.toString(hour),10) ;
        timeByte[4]=Byte.parseByte(Integer.toString(minute),10) ;
        timeByte[5]=Byte.parseByte(Integer.toString(second),10) ;
        return  timeByte;
    }

    /**
     * @Description 对集合进行深拷贝
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
