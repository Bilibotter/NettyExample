package SSL;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class SSLServerInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public SSLServerInitializer() throws Exception{
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));

        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new LengthFieldPrepender(2, 0));
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new SSLServerHandler());
    }
}
