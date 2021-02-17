package ByteBuf;

import Commons.LocalServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class CompositeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf body = (ByteBuf) msg;
        ByteBuf head = Unpooled.copiedBuffer("Hello ".getBytes(CharsetUtil.UTF_8));
        ByteBuf response = Unpooled.wrappedBuffer(head, body);
        System.out.println("Received: "+body.toString(CharsetUtil.UTF_8));
        ChannelFuture future = ctx.writeAndFlush(response);
        future.addListeners(ChannelFutureListener.CLOSE);
    }

    public static void main(String[] args) throws Exception {
        new LocalServer(new CompositeServerHandler()).run();
    }
}
