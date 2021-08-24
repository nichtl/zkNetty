package com.example.zknetty.zkNetty.Server;
import com.nicht.promote.fileservice.zkNetty.OnSocketListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.log4j.Logger;

/**
 * @Author Nicht
 * @description
 * @Time 2021/4/15
 * @Link
 */
public class ZkOutBoundHandler extends ChannelOutboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ZkOutBoundHandler.class);

    private OnSocketListener listener;

    public ZkOutBoundHandler(OnSocketListener onSocketListener){
        this.listener = onSocketListener;
    }
    @Override
    public void write(final ChannelHandlerContext ctx, final Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        logger.info("write " + ctx.name() + ",msgType:" + (msg instanceof String));
        logger.info("write " + ctx.name() + ",msg:" + msg.toString());
    }


}
