package Https;

import Http.HttpServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class HttpsServerInitializer extends ChannelInitializer<SocketChannel> {
    private final static ChannelHandler handler = new HttpServerHandler();
    private final SslContext sslCtx;
    public HttpsServerInitializer() throws Exception {
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        sslCtx = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build();
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));

        pipeline.addLast(new HttpServerCodec());
        // 拆包与粘包
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(handler);
    }
}
