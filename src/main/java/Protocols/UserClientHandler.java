package Protocols;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UserClientHandler extends SimpleChannelInboundHandler<UserPOJO.User> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UserPOJO.User user) throws Exception {
        System.out.println("Id: "+user.getId());
        System.out.println("Name: "+user.getName());
        System.out.println("Message: "+user.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserPOJO.User user = UserPOJO.User.newBuilder()
                .setId(13)
                .setName("YHM")
                .setMessage("YHM hao nb!")
                .build();
        ctx.writeAndFlush(user);
    }
}
