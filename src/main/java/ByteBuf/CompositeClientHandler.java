package ByteBuf;

import Commons.LocalClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class CompositeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf request = Unpooled.copiedBuffer("YHM".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Received: "+in.toString(CharsetUtil.UTF_8));
        in.release();
    }


    public static void main(String[] args) throws Exception {
        new LocalClient(new CompositeClientHandler()).run();
    }
}
