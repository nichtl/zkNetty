package com.example.zknetty.zkNetty;


import com.nicht.promote.fileservice.zkNetty.Mycodec.FileDecoder;
import com.nicht.promote.fileservice.zkNetty.Mycodec.TextDecoder;
import com.nicht.promote.fileservice.zkNetty.Server.ZKInboundHandle;
import com.nicht.promote.fileservice.zkNetty.Server.ZkOutBoundHandler;
import com.nicht.promote.fileservice.zkNetty.Utils.ByteUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;


/**
 * @Author Nicht
 * @description
 * @Time 2021/4/15
 * @Link
 */
@jdk.nashorn.internal.runtime.logging.Logger
public class zkNettyManager implements SocketService {


    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(zkNettyManager.class);

    private int port = 8082;

    private static zkNettyManager zkNettyManager = new zkNettyManager();

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup(5);
    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    private OnSocketListener onSocketListener;

    public static zkNettyManager getInstance() {
        return zkNettyManager;
    }


    private zkNettyManager() {

    }

    /***
     * 启动
     */
    @Override
    public void start(int port) {
        this.port = port;
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new TextDecoder());//固定执行链顺序
                        ch.pipeline().addLast(new FileDecoder());
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
                        ch.pipeline().addLast(new ZkOutBoundHandler(onSocketListener));
                        ch.pipeline().addLast(new IdleStateHandler(400,0,0,TimeUnit.SECONDS), new ZKInboundHandle(onSocketListener));
                    }
                });
        serverBootstrap.bind(port).awaitUninterruptibly();
        if (onSocketListener != null){
            onSocketListener.onStart(true);
        }
        logger.info("Netty 启动成功，port:" + port);
    }

    /***
     * 重新启动
     */
    @Override
    public void reset(){
        shutDown();
        start(port);
    }

    @Override
    public void sendMessage(Object object, final Object message) {
        if (object != null && object instanceof Channel){
            Channel channel = (Channel) object;
             String receiveStr  = (String) message;
            if (channel.isActive() && channel.isOpen()){
                try {
                    ByteBuf buff = Unpooled.buffer(receiveStr.length()/2);
                    buff.writeBytes(ByteUtils.hexString2Bytes(receiveStr));
                    channel.writeAndFlush(buff).addListener((ChannelFutureListener) future -> {
                        if (onSocketListener != null){
                            onSocketListener.onSend(future.channel(),message,future.isSuccess());
                        }
                        logger.info("下发" + (future.isSuccess() ? "成功" : "失败") );
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean isOnline(Object channel) {
        if (channel instanceof Channel){
            Channel socketChannel = (Channel) channel;
            return socketChannel.isOpen() && socketChannel.isActive();
        }
        return false;
    }

    @Override
    public void setOnSocketListener(OnSocketListener listener) {
        this.onSocketListener = listener;
    }

    /***
     * 关闭
     */
    @Override
    public void shutDown(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.info("Netty 停止成功，port:" + port);
    }


}

