package TimePOJO;

import Commons.RawServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

public class CacheOverflow {
    static class OverflowPOJOServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            byte[] bytes = new byte[1025];
            byte content = 13;
            Arrays.fill(bytes, content);
            ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        new RawServer(new OverflowPOJOServerHandler()).run();
    }
}
