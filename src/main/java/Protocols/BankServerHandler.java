package Protocols;

import io.netty.channel.*;

@ChannelHandler.Sharable
public class BankServerHandler extends SimpleChannelInboundHandler<BankPOJO.User> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BankPOJO.User user) throws Exception {
        System.out.println("Id: "+user.getId());
        System.out.println("Name: "+user.getName());
        System.out.println("Message: "+user.getMessage());
        BankPOJO.Bank bank = BankPOJO.Bank.newBuilder()
                .setBankName("ICBC")
                .setBankId(13)
                .setMoney(131313131313L)
                .build();
        ChannelFuture future = channelHandlerContext.writeAndFlush(bank);
        future.addListeners(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
