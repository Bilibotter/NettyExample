package Chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@ChannelHandler.Sharable
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    final static ChannelHandler handler = new ChatClientHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new LengthFieldPrepender(2, 0));
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(handler);
    }
}
