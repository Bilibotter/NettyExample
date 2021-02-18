package SSL;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;

public class SSLServerHandler extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 加上static可以变成线程安全，但实现起来很不优雅
    final String GREET;
    public SSLServerHandler() throws Exception {
        GREET = "Welcome to "+ InetAddress.getLocalHost().getHostName() +
                " secure chat!";
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // get返回Pipeline中的SslHandler
        // handshakeFuture为TLS握手完成的Future
        Future<Channel> future = ctx.pipeline().get(SslHandler.class).handshakeFuture();
        future.addListener(new GenericFutureListener<Future<? super Channel>>() {
            @Override
            public void operationComplete(Future<? super Channel> future) throws Exception {
                ctx.writeAndFlush(GREET);
                channels.add(ctx.channel());
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        String prefix;
        // 实际上不生效，但是是对示例更优雅的实现
        s = s.equals("") ? "Bye bye!" : s;
        System.out.println(ctx.channel().remoteAddress()+" says "+s);
        for (Channel channel:channels) {
            prefix = channel == ctx.channel() ? "[you]":"["+channel.remoteAddress()+"]";
            channel.writeAndFlush(prefix+s);
        }
        if (s.equals("Bye bye!")) {
            ctx.close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel outgoing = ctx.channel();
        String message = "[SERVER]-"+outgoing.remoteAddress()+" leave.";
        System.out.println(message);
        channels.writeAndFlush(message);
    }
}
