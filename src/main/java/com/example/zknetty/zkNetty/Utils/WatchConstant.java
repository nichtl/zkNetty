package com.example.zknetty.zkNetty.Utils;

import cn.hutool.core.util.HexUtil;

/**
 * @Author Nicht
 * @description
 * @ 2021/4/7
 */
public class WatchConstant {

    public static String Begin = "*LOLA";
    public static String FILE_UPLOAD_ACTION = "";
    public static String UploadMainPro ="54"; //上报主指令
    public static String IssueMainPro  ="53"; //下发类主指令
    public static String End = "#";


    /*终端上报类指令code*/
    public  interface  UploadSecProCode{
        String   SyncPara = "00"; //同步参数
        String   TextMsg  = "02"; // 终端上报文本消息
        String   TextMsgRes = "03"; // 上报文本消息应答
        String   UploadFileRequest ="04";//终端请求上传文件
        String   UploadFileResponse="05";//请求上传文件应答
        String   UploadFileData    ="06";//终端上传文件内容
        String   UploadFileDatResponse="07";//服务器下发上传文件内容应答
        String   UploadLocateData     ="11";//终端上报定位数据
    }

    /*中心下发类指令主指令code*/
    public  interface IssueSecProCode{  //中心下发指令 时间段
        String   ResetPara  = "00";//服务器重设终端参数
        String   TextMsg    = "01";//服务器下发文本消息
        String   TextMsgRes = "02";//终端文本消息应答
        String   LocateInterval ="03";//服务器设置定位间隔
        String   SmsText    ="04";//服务器下发要转发的短信内容
        String   SetSosNum     ="07";//服务器设置SOS号码
        String   SyncDeviceSetting ="09";//服务器获取设备参数
        String   AllowCallNum ="0A";//服务器下发设置允许呼入号码
        String   IpPort ="0C";//服务器下发更改服务器IP地址、端口号
        String   WarnningNotice="11";//服务器下发平台报警通知
        String   WarnningPeriod="12";//服务器下发语音报警提醒时间段
        String   Heart_Rate_Warnning="0B";// 心率报警范围
        String   Auto_Recording="18";//自动录音
        String   Shut_Down = "20";//关机
        String   Restart   = "21";//重启
        String   Wifi      = "22";//设置wifi
    }

    /*中心回复指令*/
    public  interface CenterReplyCommand{
        /**收到文件上传请求 回复下发文件标识*/
        String  FileRequestReply = HexUtil.encodeHexStr("*LOLAT") + UploadSecProCode.UploadFileResponse + "0800" + HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket())) + HexUtil.encodeHexStr("#");
        /*日常签到回复签到成功*/
        String  DailySignInReply =  HexUtil.encodeHexStr("*LOLAT") + UploadSecProCode.UploadLocateData + "0200" + HexUtil.encodeHexStr("OK#");
        /*回复文件接受成功*/
        String  UploadFileReply = HexUtil.encodeHexStr("*LOLAT") + UploadSecProCode.UploadFileDatResponse + "0800" + HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket())) + HexUtil.encodeHexStr("#");
    }


}
