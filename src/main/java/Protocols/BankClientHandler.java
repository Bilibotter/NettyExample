package Protocols;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class BankClientHandler extends SimpleChannelInboundHandler<BankPOJO.Bank> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BankPOJO.Bank bank) throws Exception {
        System.out.println("Bank name: "+bank.getBankName());
        System.out.println("User id: "+bank.getBankId());
        System.out.println("Money: "+bank.getMoney());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        BankPOJO.User user = BankPOJO.User.newBuilder()
                .setId(13)
                .setName("YHM")
                .setMessage("Big Boss")
                .build();
        ctx.channel().writeAndFlush(user);
    }

}
