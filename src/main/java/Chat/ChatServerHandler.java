package Chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
        String message = "[SERVER]-"+incoming.remoteAddress()+" join.";
        System.out.println(message);
        channels.writeAndFlush(message);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel outgoing = ctx.channel();
        String message = "[SERVER]-"+outgoing.remoteAddress()+" leave.";
        System.out.println(message);
        channels.writeAndFlush(message);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel sender = channelHandlerContext.channel();
        for (Channel channel:channels) {
            String hint = channel == sender ? "[you]":"["+channel.remoteAddress()+"]";
            channel.writeAndFlush(hint+s);
        }
        System.out.println(sender.remoteAddress()+" says: "+s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel current = ctx.channel();
        System.out.println(current.remoteAddress()+" happens exception!");
        cause.printStackTrace();
        ctx.close();
    }
}
