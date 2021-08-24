package com.example.zknetty.zkNetty.Mycodec;


import com.nicht.promote.fileservice.zkNetty.Beans.WatchBeans;
import com.nicht.promote.fileservice.zkNetty.Utils.ByteUtils;
import com.nicht.promote.fileservice.zkNetty.Utils.CodecParserUtils;
import com.nicht.promote.fileservice.zkNetty.Utils.WatchConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author Nicht
 * @description 文本解码器
 * @ 2021/4/1
 * @tip  自定义解码时需要特别注意 bytebuf 引用的手动管理和释放 ByteToMessageDecoder
 */
public class TextDecoder extends ByteToMessageDecoder {
    public static final int BASE_LENGTH =  26;  //数据包最小长度
    public static final int MAX_LENGTH  = 4038;  //数据包最大长度
    static WatchBeans watchBeans;
    /**记录半包写位置*/
    private static Integer  HalfPackWriterIndex=0;
    static  byte[] msg;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        checkDataClash(byteBuf,HalfPackWriterIndex);
        if (watchBeans==null){watchBeans=new WatchBeans();}
        int beginReaderIndex = byteBuf.readerIndex();
        byteBuf.markReaderIndex();
        if (byteBuf.readableBytes() < BASE_LENGTH) {
            System.out.println("小于最小帧长度");
            byteBuf.clear();
            return;
        }
        if (byteBuf.readableBytes() > MAX_LENGTH) {
            System.out.println("数据包大于最大长度");
            byteBuf.clear();
            return;
        }
        msg = new byte[5];
        byteBuf.readBytes(msg);
        if (!WatchConstant.Begin.equals(ByteUtils.trimnull(new String(msg)))) {
                System.out.println("无效数据舍弃"+ByteUtils.trimnull(new String(msg)));
                return;
        }
        byteBuf.resetReaderIndex();
        byteBuf.markReaderIndex();
        CodecParserUtils.ParserPublicData(byteBuf,watchBeans);
        if(byteBuf.readableBytes() < Integer.parseInt(watchBeans.getDatalen())-26){
            System.out.println("数据包未完全到达等待数据包  当前可读字节"+byteBuf.readableBytes()+"总数据长度="+watchBeans.getDatalen());
            //还原读指针 让buffer先不释放数据  继续缓存下一个包
            internalBuffer().readerIndex(beginReaderIndex);
            HalfPackWriterIndex = byteBuf.writerIndex();
            return;
        }
        /*是文件内容字节退出  数据大于500说明是文件传入 */
        if(byteBuf.readableBytes() >500 || WatchConstant.UploadSecProCode.UploadFileData.equals(watchBeans.getSecpro())){
                byteBuf.resetReaderIndex();
                /*处理文件半包过程中插入定时上报数据*/
                System.out.println("转发文件解码器");
                HalfPackWriterIndex=0;
                byteBuf.retain(1);  //writeandflush后会被释放   retain  引用计数加一
                ctx.fireChannelRead(byteBuf);
        }
        /*终端上传类解析*/
        if(WatchConstant.UploadMainPro.equals(watchBeans.getMainpro())){
            CodecParserUtils.ParserUploadMSG(byteBuf,watchBeans);
            out.add(watchBeans);
        }
        /*平台下发类回复解析*/
        if(WatchConstant.IssueMainPro.equals(watchBeans.getMainpro())) {
            CodecParserUtils.ParserCommandReplyMSG(byteBuf,watchBeans);
            out.add(watchBeans);
        }
    }


    /**
     * @description 排除文件分包上传时,定时定位上报数据扰乱byte buff
     * @param byteBuf byteBuf
     * @param readerIndex half pack write index
     * @warnning   未测试
     */
    private  void checkDataClash(ByteBuf byteBuf, int readerIndex){
        if(readerIndex <=BASE_LENGTH || readerIndex >=MAX_LENGTH){
            return;
        }
        byteBuf.markReaderIndex();
        byte[] tempmsg = new byte[readerIndex];
        byteBuf.readBytes(tempmsg);
        WatchBeans watchBeans1  = new WatchBeans();
        try {
            CodecParserUtils.ParserPublicData(byteBuf,watchBeans1);
            if (WatchConstant.UploadSecProCode.UploadLocateData.equals(watchBeans1.getSecpro())){
                byteBuf.clear();
                byteBuf.writeBytes(tempmsg);
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }
}


