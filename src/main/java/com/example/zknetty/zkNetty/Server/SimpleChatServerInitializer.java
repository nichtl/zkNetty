package com.example.zknetty.zkNetty.Server;


import com.nicht.promote.fileservice.zkNetty.Mycodec.FileDecoder;
import com.nicht.promote.fileservice.zkNetty.Mycodec.TextDecoder;
import com.nicht.promote.fileservice.zkNetty.OnSocketListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * @Author Nicht
 * @description
 * @ 2021/1/5
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    private OnSocketListener onSocketListener;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new TextDecoder());//固定执行链顺序
        pipeline.addLast(new FileDecoder());
        pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
        //pipeline.addLast(new ZKInboundHandle(onSocketListener));
        System.out.println("SimplerClient   "+ch.remoteAddress()  +"连接上");
    }
}
