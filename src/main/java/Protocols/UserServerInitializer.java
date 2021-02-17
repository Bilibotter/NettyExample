package Protocols;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class UserServerInitializer extends ChannelInitializer<SocketChannel> {
    final ChannelHandler handler;
    final ProtocolInitializer initializer;
    public UserServerInitializer() {
        handler = new UserServerHandler();
        initializer = new ProtocolInitializer();
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        initializer.initChannel(socketChannel);
        socketChannel.pipeline().addLast(handler);
    }
}
