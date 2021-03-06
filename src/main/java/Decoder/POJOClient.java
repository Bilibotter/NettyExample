package Decoder;

import Time.TimeClientHandler;
import TimePOJO.POJOClientHandler;
import TimePOJO.POJODecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class POJOClient {
    final int port;
    final ChannelHandler handler;

    public POJOClient(int port, ChannelHandler handler) {
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
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2), handler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new POJOClient(13130, new TimeClientHandler()).run();
    }
}
