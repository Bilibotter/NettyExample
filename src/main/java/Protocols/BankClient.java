package Protocols;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankClient {
    final static ChannelInitializer<SocketChannel> initializer;
    static  {
        initializer = new BankClientInitializer();
    }
    public void run() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new BankClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost",13130).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    static class Test implements Runnable {
        private BankClient client;
        public Test() {
            client = new BankClient();
        }
        @Override
        public void run() {
            try {
                client.run();
            } catch (Exception ignore) {}
        }
    }

    public static void main(String[] args) throws Exception {
        int runtime = 200;
        int threadNum = 50;
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        for (int i=0; i<runtime; i++) {
            exec.submit(new Test());
        }
        exec.shutdown();
    }
}
