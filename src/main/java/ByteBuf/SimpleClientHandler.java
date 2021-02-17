package ByteBuf;

import Commons.LocalClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class SimpleClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf request = Unpooled.copiedBuffer("YHM".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(request);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Received: "+byteBuf.toString(CharsetUtil.UTF_8));
    }

    public static void main(String[] args) throws Exception {
        new LocalClient(new SimpleClientHandler()).run();
    }
}
