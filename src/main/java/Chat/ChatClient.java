package Chat;

import Commons.LocalClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    private final ChannelInitializer<SocketChannel> initializer = new ChatClientInitializer();
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(initializer);
            Channel channel = bootstrap.connect("localhost", 13130).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String result;
            while ((result=in.readLine()).length() != 0) {
                //System.out.println(result.length());
                channel.writeAndFlush(result);
            }
            channel.close(channel.newPromise());
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new ChatClient().run();
    }
}
