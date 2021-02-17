package ByteBuf;

import Commons.LocalServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class SimpleServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Received: "+byteBuf.toString(CharsetUtil.UTF_8));
        ByteBuf head = Unpooled.copiedBuffer("Hello ".getBytes(CharsetUtil.UTF_8));
        ByteBuf body = Unpooled.copiedBuffer(byteBuf);
        ByteBuf response = Unpooled.wrappedBuffer(head, body);
        ChannelFuture future = channelHandlerContext.writeAndFlush(response);
        future.addListeners(ChannelFutureListener.CLOSE);
        System.out.println("Flush!");
    }

    public static void main(String[] args) throws Exception {
        new LocalServer(new SimpleServerHandler()).run();
    }
}
