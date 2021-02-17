package HeartBeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    private final static ByteBuf HEART_BEAT = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Heart Beat".getBytes(CharsetUtil.UTF_8))
    );
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = null;
            switch (event.state()) {
                case READER_IDLE:
                    type = "read idle";
                    break;
                case WRITER_IDLE:
                    type = "write idle";
                    break;
                case ALL_IDLE:
                    type = "all idle";
                    break;
            }
            ctx.writeAndFlush(HEART_BEAT.duplicate())
                    .addListeners(ChannelFutureListener.CLOSE_ON_FAILURE);
            System.out.println(ctx.channel().remoteAddress()+" idle type is "+type);
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
