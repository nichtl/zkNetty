package com.example.zknetty.zkNetty.Server;


import com.nicht.promote.fileservice.zkNetty.OnSocketListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;

/**
 * @Author Nicht
 * @description  服务端处理文本
 * @ 2021/1/5  SimpleChannelInboundHandler  ChannelInboundHandler 4.x
 */
@ChannelHandler.Sharable
public class ZKInboundHandle implements ChannelInboundHandler {   /*TextWebSocketFrame*/
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static Logger logger = Logger.getLogger(ZKInboundHandle.class);

    private OnSocketListener listener;
    public ZKInboundHandle(OnSocketListener onSocketListener){
        this.listener = onSocketListener;
    }
    @Override
    public  void handlerAdded(ChannelHandlerContext ctx){   //新加入handle 触发 时给现有的channl 发送通知
      /*  Channel incoming = ctx.channel();
        String res  =  "2a4c4f4c4153090000#";
        for(Channel channel : channels){
            if(channel ==incoming) {
                 channel.writeAndFlush("Server - " + incoming.remoteAddress() + "加入\n");
                 channel.writeAndFlush(res);
            }
        }*/
        channels.add(ctx.channel());
    }
    @Override
    public  void  handlerRemoved(ChannelHandlerContext ctx){
        Channel  incoming = ctx.channel();
            System.out.println("Client - " +incoming.remoteAddress()+"离开\n");
            ctx.close();
        channels.remove(ctx.channel());

    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public  void   channelActive(ChannelHandlerContext ctx){
        Channel incoming = ctx.channel();
        System.out.println("Client  ::"+incoming.remoteAddress()+"在线");
    }

    @Override
    public  void channelInactive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        System.out.println("Client" + incoming.remoteAddress()+"掉线");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Channel incoming = ctx.channel();  //当前channel 连接
            if (listener != null){
                listener.onReceive(ctx.channel(),msg);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public  void exceptionCaught (ChannelHandlerContext ctx ,Throwable cause){
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        ctx.close();
        cause.printStackTrace();
    }


}
