package com.example.zknetty.zkNetty.Server;

import com.nicht.promote.fileservice.zkNetty.OnSocketListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author Nicht
 * @description
 * @ 2021/1/5
 */
public class SimpleChatServer {
    private int port ;
    private OnSocketListener onSocketListener;
    public  SimpleChatServer(int port){
        this.port = port;
    }
    public  void run() throws  Exception{
        EventLoopGroup  bossGroup =  new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())//
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, Boolean.valueOf(true))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535));
            System.out.println("netty服务端启动 port"+this.port);


            ChannelFuture f = b.bind(port).sync();


            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("SimpleChatServer 关闭了");
        }
    }


}
