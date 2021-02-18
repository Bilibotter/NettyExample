package SSL;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class SSLClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    public SSLClientInitializer() throws Exception {
        sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(sslCtx.newHandler(socketChannel.alloc(), "127.0.0.1", 13130));

        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new LengthFieldPrepender(2, 0));
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new SSLClientHandler());
    }
}
