package HeartBeat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;


public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
    private final static String HEART_BEAT = "OK";
    private final static int MAX_BEAT_TIME = 3;
    private int beatTime = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("I am coming!");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            System.out.println("[Heart Beat Check]"+new Date());
            IdleStateEvent event = (IdleStateEvent) evt;
            if (++beatTime > MAX_BEAT_TIME) {
                return;
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HEART_BEAT);
            }
        }
    }
}
