package com.example.zknetty.zkNetty.Mycodec;


import com.nicht.promote.fileservice.zkNetty.Beans.WatchBeans;
import com.nicht.promote.fileservice.zkNetty.Utils.ByteUtils;
import com.nicht.promote.fileservice.zkNetty.Utils.CodecParserUtils;
import com.nicht.promote.fileservice.zkNetty.Utils.WatchConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author Nicht
 * @description 文件解码器
 * @ 2021/3/31
 */
public class FileDecoder extends ByteToMessageDecoder{
    private Integer FileTotalSize =0;//文件总大小
    static WatchBeans watchBeans;
    static byte[] msg;
    static ThreadLocal<Integer> filePackNum =new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new Integer(-1);
        }
    };
    /*字节序 设备是小端  java window linux是大端 接受int  float  long 都转为数据需要将数据还原再解析 */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (watchBeans==null){watchBeans=new WatchBeans();}
        byteBuf.markReaderIndex();
        CodecParserUtils.ParserPublicData(byteBuf,watchBeans);
        FileTotalSize=Integer.parseInt(watchBeans.getDatalen())-12;

        //目前文件标识无法传递 暂时舍弃
        byteBuf.readBytes(8);
        msg=new byte[2];  //103
        byteBuf.readBytes(msg);
        String pack_num = Short.toString(ByteUtils.getShort(msg));
        if(filePackNum.get()==-1){  //若为-1说明为第一次上传 设置packnum
            filePackNum.set(Integer.parseInt(pack_num)-1);  //初始包序号为0
        }
        msg =new byte[2];
        byteBuf.readBytes(msg);
        String pack_ind =Short.toString(ByteUtils.getShort(msg));
        System.out.println("当前文件包num"+pack_ind);
        try {
            msg = new byte[Integer.parseInt(watchBeans.getDatalen())-12];//  文件数据长度为 datalen-12
            System.out.println("可读字节"+byteBuf.readableBytes());
            byteBuf.readBytes(msg);
            watchBeans.putBytesdata(msg);
            //cacheFile.put(msg);//缓存数据
            byteBuf.readByte();//舍弃结束符
        }catch (Exception  e){e.printStackTrace();}
        if(filePackNum.get()==0){
            filePackNum.set(-1); //上传完成恢复初始状态 如果包数为0说明全部到达
            watchBeans.setSecpro(WatchConstant.UploadSecProCode.UploadFileData);
            out.add(watchBeans);
        }
        else {
            filePackNum.set(filePackNum.get()-1);
            //回复07继续上传
            watchBeans.setSecpro(WatchConstant.UploadSecProCode.UploadFileDatResponse); //07回复
            out.add(watchBeans);
        }
    }

}

