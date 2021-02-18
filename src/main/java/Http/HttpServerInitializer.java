package Http;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

@ChannelHandler.Sharable
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    private final static ChannelHandler handler = new HttpServerHandler();
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        // 拆包与粘包
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(handler);
    }
}
