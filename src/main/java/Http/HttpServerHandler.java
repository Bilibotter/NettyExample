package Http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.text.MessageFormat;

@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private final static String format = "<h1>Netty Server</h1><a>{0}</a>";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) {
        HttpRequest request = (HttpRequest) msg;
        String uri = request.uri();
        String body;
        switch (uri) {
            case "/":
                body = MessageFormat.format(format, "Welcome to YHM's website!");
                break;
            case "/hello":
                body = MessageFormat.format(format, "Hello world!");
                break;
            default:
                body = MessageFormat.format(format, uri+" isn't exist!");
                break;
        }
        System.out.println(body);
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.protocolVersion(), HttpResponseStatus.OK, Unpooled.wrappedBuffer(body.getBytes(CharsetUtil.UTF_8)));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}
