package Time;

import Commons.RawServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        // 2208988800是1990年1月1日12:00:01
        time.writeInt((int)(System.currentTimeMillis()/1000L+2208988800L));
        final ChannelFuture future = ctx.writeAndFlush(time);
        // 由于Netty是异步的，因此要使用Listener回调保证调用顺序
        // listener会在future绑定的操作执行后调用
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert future == channelFuture;
                ctx.close();
            }
        });
        // 简洁版
        // future.addListeners(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
