package Commons;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RawClient{
    final int port;
    final ChannelHandler handler;
    public RawClient() {
        this(13130);
    }

    public RawClient(int port) {
        this(port, new ChannelInboundHandlerAdapter());
    }

    public RawClient(ChannelHandler handler) {
        this(13130, handler);
    }

    public RawClient(int port, ChannelHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
