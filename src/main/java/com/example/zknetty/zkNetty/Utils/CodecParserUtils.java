package com.example.zknetty.zkNetty.Utils;

import cn.hutool.core.util.HexUtil;
import com.nicht.promote.fileservice.zkNetty.Beans.FileInfo;
import com.nicht.promote.fileservice.zkNetty.Beans.LocateMsg;
import com.nicht.promote.fileservice.zkNetty.Beans.Sms;
import com.nicht.promote.fileservice.zkNetty.Beans.WatchBeans;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * @Author Nicht
 * @description
 * @ 2021/4/2
 */
public class CodecParserUtils {
    public static void main(String[] args) {
        byte [] b  = new byte[8];
        b[0] = 0;
        b[1]=4;
        b[2]=2;
        b[3]=13;
        b[4]=27;
        b[5]=15;
        b[6]=0;
        b[7]=0;
        String   sb  =  Byte.toString(b[0]);
        System.out.println(sb);

    }
    public  static  String ParserTdateTime(byte[] msg) {
       StringBuilder   hex =  new StringBuilder(); 

        String year = Byte.toString(msg[0]);
        hex.append("20"+year).append("-");
        
        String month = Byte.toString(msg[1]);
        hex.append(month).append("-");
        
        
        String day = Byte.toString(msg[2]);
        hex.append(day).append(" ");
        
        
        String hour = Byte.toString(msg[3]);
        hex.append(hour).append(":");
        
        
        String min = Byte.toString(msg[4]);
        hex.append(min).append(":");
        
        
        String sec = Byte.toString(msg[5]);
        hex.append(sec).append(":");

        byte[] temp = new  byte[2];
        temp[0] = msg[6];
        temp[1] = msg[7];
        String msec = Short.toString(ByteUtils.getShort(temp));
        hex.append(msec+"0");
        return hex.toString();
    }
    /*解析前四个公共数据*/
    public  static  void ParserPublicData(ByteBuf byteBuf, WatchBeans watchBeans) throws UnsupportedEncodingException {
        StringBuilder  sb =new StringBuilder();
        byte [] msg  ;
        msg=new byte[5];
        byteBuf.readBytes(msg);
        String begin =  new String(msg);
        watchBeans.setBegin(ByteUtils.trimnull(begin));
        msg=  new byte[16];
        byteBuf.readBytes(msg);
        String imei=  new String(msg);
        watchBeans.setImei(ByteUtils.trimnull(imei));
        msg  =new byte[1];
        byteBuf.readBytes(msg);
        String mainprotro=  HexUtil.encodeHexStr(msg);
        watchBeans.setMainpro(ByteUtils.trimnull(mainprotro));
        msg  =new byte[1];
        byteBuf.readBytes(msg);
        String secondprotro = HexUtil.encodeHexStr(msg);
        watchBeans.setSecpro(ByteUtils.trimnull(secondprotro));
        msg  =new byte[2];
        byteBuf.readBytes(msg);
        String datalen = Short.toString(ByteUtils.getShort(msg));
        watchBeans.setDatalen(ByteUtils.trimnull(datalen));
    }
    public  static  void ParserFileRequest(ByteBuf byteBuf,WatchBeans watchBeans) throws  UnsupportedEncodingException{
        byte [] msg;
        FileInfo fileInfo  =new FileInfo();
        msg = new byte[1];
        byteBuf.readBytes(msg);
        String file_type = Byte.toString(msg[0]);
        fileInfo.setFile_type(file_type);
        msg = new byte[4];
        byteBuf.readBytes(msg);
        String file_size =Integer.toString(ByteUtils.byteArrayToInt(ByteUtils.reverseBytes(msg)));  //单位byte
        fileInfo.setFile_size(file_size);
        msg = new byte[2];
        byteBuf.readBytes(msg);
        Integer file_name_len = Byte.toUnsignedInt(msg[0]);
        fileInfo.setFile_name_len(Integer.toString(file_name_len));

        msg = new byte[file_name_len + 1];
        byteBuf.readBytes(msg);
        String file_name = ByteUtils.trimnull(new String(msg));
        fileInfo.setFile_name(file_name);

        msg = new byte[12];
        byteBuf.readBytes(msg);
        String pos_info = "";
        fileInfo.setPos_info(pos_info);

        msg = new byte[8];
        byteBuf.readBytes(msg);
        String time = CodecParserUtils.ParserTdateTime(msg);
        fileInfo.setTime(time);
        byteBuf.readByte();//舍弃结束符
        watchBeans.setFileInfo(fileInfo);
    }
    public  static  void ParserTextMsg(ByteBuf byteBuf,WatchBeans watchBeans) {
        byte[] msg;
        Sms sms=new Sms();
        try{
            msg = new byte[2];
            byteBuf.readBytes(msg);
            String textlen = Short.toString(ByteUtils.getShort(msg));
            sms.setText_len(textlen);
            msg = new byte[Integer.parseInt(textlen)+1];
            byteBuf.readBytes(msg);
            String  text = ByteUtils.trimnull(new String(msg));
            sms.setText(text.substring(0,text.length()-1));
            msg =new byte[4];
            byteBuf.readBytes(msg);
            float lon = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
            sms.setLon(Float.toString(lon));
            msg =new byte[4];
            byteBuf.readBytes(msg);
            float lat = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
            sms.setLat(Float.toString(lat));
            msg =new byte[4];
            byteBuf.readBytes(msg);
            float alt = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
            sms.setAlt(Float.toString(alt));
            msg =new byte[8];
            byteBuf.readBytes(msg);
            sms.setTime(CodecParserUtils.ParserTdateTime(msg));
            byteBuf.readByte();//舍弃结束符
            watchBeans.setSms(sms);
        }
        catch (Exception e){e.printStackTrace();}
    }
    public  static  void ParserLocateData(ByteBuf byteBuf,WatchBeans watchBeans){
        byte[] msg;
        LocateMsg locateMsg = new LocateMsg();
        msg = new byte[4];
        byteBuf.readBytes(msg);
        float lon = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
        locateMsg.setLon(Float.toString(lon));

        msg = new byte[4];
        byteBuf.readBytes(msg);
        float lat = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
        locateMsg.setLat(Float.toString(lat));

        msg = new byte[4];
        byteBuf.readBytes(msg);
        float alt = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
        locateMsg.setAlt(Float.toString(alt));

        msg = new byte[4];
        byteBuf.readBytes(msg);
        float speed = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
        locateMsg.setSpeed(Float.toString(speed));


        msg = new byte[8];
        byteBuf.readBytes(msg);
        locateMsg.setTime(CodecParserUtils.ParserTdateTime(msg));


        msg = new byte[4];
        byteBuf.readBytes(msg);  //暂时舍弃
        float mileage = ByteUtils.hexToFloat(HexUtil.encodeHexStr(ByteUtils.reverseBytes(msg)));
        locateMsg.setMileage("0");


        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] gps_status = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setGps_status(ByteUtils.BytesToString(gps_status));

        msg = new byte[6];
        byteBuf.readBytes(msg); //Wrist_id 固定为字符直接赋值000000
        locateMsg.setWrist_id("000000");

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] data_type = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setData_type(ByteUtils.BytesToString(data_type));

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] battery = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setBattery(ByteUtils.BytesToString(battery));

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] Cutoff_times = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setCutoff_times(ByteUtils.BytesToString(Cutoff_times));

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] Cutoff_status = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setCutoff_status(ByteUtils.BytesToString(Cutoff_status));

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] Sparate_status = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setSparate_status(ByteUtils.BytesToString(Sparate_status));

        msg = new byte[1];
        byteBuf.readBytes(msg);
        byte[] Heart_rate = HexUtil.decodeHex(HexUtil.encodeHex(msg));
        locateMsg.setHeart_rate(ByteUtils.BytesToString(Heart_rate));
        byteBuf.readByte();//舍弃结束符
        watchBeans.setLocateMsg(locateMsg);
    }
    /*终端上传解析*/
    public  static  void ParserUploadMSG(ByteBuf byteBuf,WatchBeans watchBeans){
       try {
           /*文件上传请求*/
           if (WatchConstant.UploadSecProCode.UploadFileRequest.equals(watchBeans.getSecpro())) {
               //将完整数据包交给handler处理
               CodecParserUtils.ParserFileRequest(byteBuf, watchBeans);
               System.out.println("请求上传通知可以上传");
           }
           /*短信*/
           else if (WatchConstant.UploadSecProCode.TextMsg.equals(watchBeans.getSecpro())) {
               CodecParserUtils.ParserTextMsg(byteBuf, watchBeans);
               //将完整数据包交给handler处理
               System.out.println("文本消息");
           }
           /*定位上报    此处float数据应该是大端序但终端上报的是小端序 需要将小端序转换为大端序后才能使用*/
           else if (WatchConstant.UploadSecProCode.UploadLocateData.equals(watchBeans.getSecpro())) {
               CodecParserUtils.ParserLocateData(byteBuf, watchBeans);
               //将完整数据包交给handler处理
               System.out.println("定位上报消息");
           }
       }catch (Exception e){e.printStackTrace();}
    }
    /*下发指令回复解析*/
    public  static  void ParserCommandReplyMSG(ByteBuf byteBuf,WatchBeans watchBeans){
        byte[] msg;
        Sms sms = new Sms();
        try {
            /*终端回复设备同步参数*/
            if (WatchConstant.IssueSecProCode.SyncDeviceSetting.equals(watchBeans.getSecpro())) {
                msg = new byte[Integer.parseInt(watchBeans.getDatalen())];
                byteBuf.readBytes(msg);
                watchBeans.setStr_Data(ByteUtils.trimnull(new String(msg)));
                watchBeans.setCommandSendSuccess(true);
                byteBuf.readByte();
            }
            /*终端接受短信回复*/
            else if (WatchConstant.UploadSecProCode.TextMsg.equals(watchBeans.getSecpro())) {
                msg = new byte[8];
                byteBuf.readBytes(msg);
                watchBeans.setSendTime(CodecParserUtils.ParserTdateTime(msg));
                if (!CodecParserUtils.isNullOrEmpty(watchBeans.getSendTime())) {
                    watchBeans.setCommandSendSuccess(true);  //短信发送成功
                }
            }
            /*其他命令统一格式回复*/
            else {
                msg = new byte[Integer.parseInt(watchBeans.getDatalen())];
                byteBuf.readBytes(msg);
                watchBeans.setCommandSendSuccess(true);
                watchBeans.setStr_Data(ByteUtils.trimnull(new String(msg)));
            }
        }catch (Exception e){e.printStackTrace();}
    }


    public static byte int2Byte(int i){
        byte r = (byte) i;
        return r;
    }


    public static Integer ByteToInt(Byte i){
         return   i.intValue();
    }

    public static boolean isNullOrEmpty(String param) {
        return null == param || param.length() == 0;
    }













}
