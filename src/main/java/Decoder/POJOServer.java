package Decoder;

import Time.TimeServerHandler;
import TimePOJO.POJOEncoder;
import TimePOJO.POJOServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

public class POJOServer {
    final int port;
    final ChannelHandler handler;
    public POJOServer() {
        this(13130);
    }

    public POJOServer(int port) {
        this(port, new ChannelInboundHandlerAdapter());
    }

    public POJOServer(ChannelHandler handler) {
        this(13130, handler);
    }

    public POJOServer(int port, ChannelHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(2, 0), handler);
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new POJOServer(new TimeServerHandler()).run();
    }
}
