package Protocols;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UserServerHandler extends SimpleChannelInboundHandler<Protocols.UserPOJO.User> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocols.UserPOJO.User user) throws Exception {
        System.out.println("Id: "+user.getId());
        System.out.println("Name: "+user.getName());
        System.out.println("Message: "+user.getMessage());
        Protocols.UserPOJO.User back = UserPOJO.User.newBuilder()
                .setId(13)
                .setName("YHM")
                .setMessage("Yi yang NB!")
                .build();
        ChannelFuture future = channelHandlerContext.writeAndFlush(back);
        future.addListeners(ChannelFutureListener.CLOSE);
    }
}
