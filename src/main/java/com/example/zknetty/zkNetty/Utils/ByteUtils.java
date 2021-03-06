package com.example.zknetty.zkNetty.Utils;

import cn.hutool.core.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Nicht
 * @description
 * @ 2021/3/30
 */
public class ByteUtils {

        public static void main(String[] args) {
        try {


          /*   String syncpara = HexUtil.encodeHexStr("*LOLA") +"53"+ "09" + "0000"+HexUtil.encodeHexStr("#");
            System.out.println(syncpara);
            String  res  ="e7a6bbe5bc80e58cbae59f9f28e58d97e4baac29e88c83e59bb4e68aa5e8ada600";

            System.out.println( new String(ByteUtils.hexString2Bytes(res)));

            char[] charsend  =new char[1];
            charsend[0]='\0';
            byte[] dyd=new byte[1];
            dyd[0]='\0';
            System.out.println(HexUtil.encodeHexStr("\0", Charset.forName("utf-8")));
            String  re ="";
            byte[] bys =ByteUtils.image2byte("D://360Downloads//timg.jpg");
            System.out.println(ByteUtils.BytesToString(bys));
            byte[] b = new byte[2];
            b[0] = 8;
            b[1] = 0;
            System.out.println(HexUtil.encodeHexStr(b));
*//*          b[0] = 89;
            b[1] = 0;
            b[2] = 5;
            b[3] = 0;*//*
            b=IntToByteArray(381916);
            System.out.println(HexUtil.decodeHexStr("dcd30500"));
            System.out.println(Integer.parseUnsignedInt(ByteUtils.BytesToString(b).trim(),10));

            System.out.println(getFileTicket());

           //String res = HexUtil.encodeHexStr("*LOLAT") + "05" + "0800" +HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket()))+HexUtil.encodeHexStr("#");
            System.out.println(res);



            bys=hexString2Bytes("2a4c4f4c41540508003231303430383131333032343236393030303023");
            System.out.println("??????bytes  "+ bys);
            String hex="42ed8c4e";

            Float  value=ByteUtils.byteToFloat(str2ByteArray(hex),16);
            System.out.println(value);
            byte[] bs=ByteUtils.str2ByteArray("2a4c4f4c41540508003231303430373035313133303634303030303023");

            System.out.println(HexUtil.encodeHexStr("*LOLAT") + "05" + "0800" +HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket()))+HexUtil.encodeHexStr("#"));
            String res2 = HexUtil.encodeHexStr("*LOLAT") + "05" + "0800" +HexUtil.encodeHexStr(Long.toString(ByteUtils.getFileTicket()))+HexUtil.encodeHexStr("#");
            System.out.println(res);
            System.out.println(HexUtil.encodeHexStr("*LOLAT")+"11"+"0200"+HexUtil.encodeHexStr("OK#"));






            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(bys);
            DataInputStream dataInputStream =
                    new DataInputStream(byteArrayInputStream);
            float v = dataInputStream.readFloat();
            System.out.println("v"+v);
            String ks = "4f8ced42";

            float f = Float.intBitsToFloat(Integer.parseInt(ks, 16));
            System.out.println(f);

            String li = "20210406160928";
            System.out.println(HexUtil.encodeHexStr(li));
            System.out.println(decodeHex(li.toCharArray()));

            //String  s2  = "*LOLAS"+HexUtil.encodeHexStr(new String(msg))+"(???:15977775555#";
            // System.out.println(HexUtil.encodeHexStr(s2));

            List<String> data = stringSpilt("2a4c4f4c413836333939353135313334333031360054042e000095210500120032303231303430363134343335362e6a7067000000000000000000000000001504060e2c03000023", 2);
            System.out.println(getListStr(data.subList(0, 5)));

            //short  sd  =Short.parseShort();
            System.out.println("");
            System.out.println("int" + ByteUtils.byteArrayToInt(b));
            System.out.println();
            System.out.println(HexUtil.encodeHexStr("*LOLAT058") + HexUtil.encodeHexStr(Long.toString(getFileTicket())) + HexUtil.encodeHexStr("#"));

            String s = "42E3EA9B";
            int   INTS=   Integer.parseUnsignedInt(s.trim(),16);
            Float svalue = Float.intBitsToFloat(Integer.valueOf(s.trim(), 16));
            System.out.println("16??????????????????10??????=" + value);
            Float f2 = 20.59375f;
            System.out.println("10??????????????????16??????=" + Integer.toHexString(Float.floatToIntBits(f)));*/
        }catch (Exception e){e.printStackTrace();}

        }

        public  static String CreateResStr(char secpro,char datalen,char data){
        char [] begin  =  {'*','L','O','L','A','T',secpro,datalen,data,'#'};
        return  HexUtil.encodeHexStr(new String(begin));
        }
        /**
        * ????????????16????????????
        */
        public static void send2client(Channel ctx, final String receiveStr) {
        try {
            // netty?????????ByteBuf??????
            ByteBuf buff = Unpooled.buffer(receiveStr.length()/2);
            // ????????????16??????
            buff.writeBytes(ByteUtils.hexString2Bytes(receiveStr));
            ctx.writeAndFlush(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }


        }
        // read a integer from little-endian bytes
        public static int readIntLe(byte[] bytes, int offset) {
        return (bytes[offset] & 0xff) | (bytes[offset + 1] & 0xff) << 8
                | (bytes[offset + 2] & 0xff) << 16 | (bytes[offset + 3] & 0xff) << 24;
        }

        /**?????????byte??????*/
        public static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
        }
        /**
        * @Title:hexString2Bytes
        * @Description:16??????????????????????????????
        * @param src  16???????????????
        * @return ????????????
        */
        public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
        }

        public static byte int2Byte ( int i){
            byte r = (byte) i;
            return r;
        }

        public static float byteToFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
        }

        public static byte[] str2ByteArray (String str){
            byte[] bytes = new byte[str.length() / 2];
            List<String> data = ByteUtils.stringSpilt(str, 2);
            for (int i = 0; i < data.size(); i++) {
                bytes[i] = Byte.parseByte(data.get(i));
            }
            return bytes;
        }

        /**???int????????????????????? */
        public static  byte[] IntToByteArray(int n){
        byte[] b = new byte[4];
        b[0] = (byte)(n & 0xff);
        b[1] = (byte)(n>>8 & 0xff);
        b[2] = (byte)(n>>16 &0xff);
        b[3] = (byte)(n>>24 &0xff);
        return b;
        }


        public int ByteArrayToInt(byte[] bAttr){
        int n=0;
        int leftmove;
        for(int i=0;i<4&&(i<bAttr.length);i++){
            leftmove = i*8;
            n += bAttr[i]<<leftmove;
        }
        return n;

        }

        public byte[] StringToByteArray(String str){
        byte[] temp=new byte[100];
        try {
            temp=str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
        }

        public String ByteArrayToString(byte[] bAttr,int maxLen){
        int index=0;
        while(index <bAttr.length&&index<maxLen){
            if(bAttr[index] == 0){
                break;
            }
            index++;
        }
        byte[] tmp = new byte[index];
        System.arraycopy(bAttr, 0, tmp, 0, index);
        String str=null;
        try {
            str = new String(tmp,"GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;

        }


        public static String getListStr (List < String > stringList) {
            StringBuilder sb = new StringBuilder();
            stringList.forEach(sb::append);
            return sb.toString();
        }

        /**
         * byte[]???int
         * @param
         * @return
         */
        public static List<String> stringSpilt (String s,int spiltNum){
            int startIndex = 0;
            int endIndex = spiltNum;
            int lenght = s.length();
            List<String> subs = new ArrayList<>();//??????????????????????????????
            boolean isEnd = true;

            while (isEnd) {

                if (endIndex >= lenght) {
                    endIndex = lenght;
                    isEnd = false;
                }
                subs.add(s.substring(startIndex, endIndex));
                startIndex = endIndex;
                endIndex = endIndex + spiltNum;
            }
            return subs;
        }
        public static int byteArrayToInt ( byte[] bytes){
            int value = 0;
            // ??????????????????
            for (int i = 0; i < 4; i++) {
                int shift = (4 - 1 - i) * 8;
                value += (bytes[i] & 0x000000FF) << shift;// ????????????
            }
            return value;
        }

        public static Integer ByteToInt (Byte i){
            return i.intValue();
        }
        /**@Description
         * byte?????????16????????????
         * */
        public static String byteToArray ( byte[] data){
            String result = "";
            for (int i = 0; i < data.length; i++) {
                result += Integer.toHexString((data[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3);
            }
            return result;
        }
        protected static int toDigit ( char ch, int index){
            int digit = Character.digit(ch, 16);
            if (digit == -1) {
                throw new RuntimeException("??????16???????????? " + ch
                        + " ????????? " + index);
            }
            return digit;
        }

        /*
         * @Description
         * 16???????????????byte??????
         * */
        public static byte[] decodeHex ( char[] data){
            int len = data.length;
            if ((len & 0x01) != 0) {
                throw new RuntimeException("???????????????");
            }
            byte[] out = new byte[len >> 1];
            for (int i = 0, j = 0; j < len; i++) {
                int f = toDigit(data[j], j) << 4;
                j++;
                f = f | toDigit(data[j], j);
                j++;
                out[i] = (byte) (f & 0xFF);
            }
            return out;
        }

        /**
         * ??? 4?????????16???????????????????????????32?????????????????????????????????
         * @param str 4?????? 16????????????
         * @return
         */
        /*Boolean?????????????????????*/
        public static float hexToFloat (String str){
            return Float.intBitsToFloat(new BigInteger(str, 16).intValue());
        }

        public void invertUsingCollectionsReverse(Object[] array) {
        List<Object> list = Arrays.asList(array);
        Collections.reverse(list);
        }
        public  static  byte[] reverseBytes(byte[]  bytes){
            byte[]  reserve =  new byte[bytes.length];
            for (int i = bytes.length-1; i >=0; i--) {
                 reserve[bytes.length-1-i] =bytes[i];
            }
            return  reserve;
        }

        /**
         * ???????????????32?????????????????????16??????
         * @param value
         * @return
         */
        public static String folatToHexString (Float value){
            return Integer.toHexString(Float.floatToIntBits(value));
        }

        public static float tenBitToFloat ( byte[] bytes){
            String encodehex = HexUtil.encodeHexStr(bytes);
            return hexToFloat(encodehex);
        }
        public static String BytesToString ( byte[] bytes){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(bytes[i]);
            }
            return sb.toString();
        }

        /**@Descrption????????????????????????*/
        public static Long getFileTicket () {
            Long ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
            return ltime;
        }


        public static short getShort ( byte[] b){//?????????
            return (short) (((b[1] << 8) | b[0] & 0xff));
        }

        public static void putShort ( byte b[], short s){
            b[1] = (byte) (s >> 8);
            b[0] = (byte) (s >> 0);
        }

        public static String toStringHex2 (String s){
            byte[] baKeyword = new byte[s.length() / 2];
            for (int i = 0; i < baKeyword.length; i++) {
                try {
                    baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                            i * 2, i * 2 + 2), 16));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                s = new String(baKeyword, "utf-8");// UTF-16le:Not
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return s;
        }

        /**
        * @param by
        * @return ???????????????????????????16???????????????
        */
        public static String receiveHexToString(byte[] by) {
        try {
            /*io.netty.buffer.WrappedByteBuf buf = (WrappedByteBuf)msg;
            ByteBufInputStream is = new ByteBufInputStream(buf);
            byte[] by = input2byte(is);*/
            String str = bytes2Str(by);
            str = str.toLowerCase();
            return str;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("???????????????????????????16?????????????????????");
        }
        return null;
        }

        /**
        *  Convert byte[] to hex string.?????????????????????byte?????????int
        * @param src byte[] data
        * @return hex string
        */
        public static String bytes2Str(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
        }
        /**byte???????????????**/
        public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) {
            System.out.println("data  is null"); return;}
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
        }
        //byte?????????16???????????????
        public static String byte2string(byte[] data){
        if(data==null||data.length<=1) return "0x";
        if(data.length>200000) return "0x";
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        //byte????????????????????????
        for(int k=0;k<data.length;k++){
            buf[k] = data[k]<0?(data[k]+256):(data[k]);
        }
        //??????????????????????????????
        for(int k=0;k<buf.length;k++){
            if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
            else sb.append(Integer.toHexString(buf[k]));
        }
        return "0x"+sb.toString().toUpperCase();
        }
        /**@Descrption ByteBuffer ??? byte[] */
        public static byte[] Byf2Bytes(ByteBuffer  byteBuffer){
        byte []   bytes  = new byte[byteBuffer.position()];
        if(byteBuffer.position()>0){
            byteBuffer.flip();
            byteBuffer.get(bytes);
            byteBuffer.clear();
        }
        return  bytes;
        }
        /**@Descrption list<byte[]> ???byte[] ???????????????????????????*/
        public static ByteBuffer   bytes2Bytebuf(List<byte[]>  bytes){
        ByteBuffer  buf   = ByteBuffer.allocate(2097152);//2m
            bytes.forEach(b ->{
             buf.put(b.clone());
            });
        return  buf;
        }
        /**
     * @Descrption ?????????????????????null???  \0 ?????????
     * @param string
     * @return
     * @throws UnsupportedEncodingException
     */
        public static String trimnull(String string) throws UnsupportedEncodingException {
        ArrayList<Byte> list = new ArrayList<Byte>();
        byte[] bytes = string.getBytes("UTF-8");
        for(int i=0;bytes!=null&&i<bytes.length;i++){
            if(0!=bytes[i]){
                list.add(bytes[i]);
            }
        }
        byte[] newbytes = new byte[list.size()];
        for(int i = 0 ; i<list.size();i++){
            newbytes[i]=(Byte) list.get(i);
        }
        String str = new String(newbytes,"UTF-8");
        return str;
    }
}
