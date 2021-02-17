package Commons;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LocalClient {
    private final ChannelInitializer<SocketChannel> initializer;

    public LocalClient() {
        initializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

            }
        };
    }

    public LocalClient(ChannelHandler handler) {
        this(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(handler);
            }
        });
    }

    public LocalClient(ChannelInitializer<SocketChannel> initializer) {
        this.initializer = initializer;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(initializer);
            ChannelFuture future = bootstrap.connect("localhost", 13130).sync();
            process(future);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    public void process(ChannelFuture future) {}
}
