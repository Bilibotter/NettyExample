package TimePOJO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

public class POJODecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readable = byteBuf.readableBytes();
        if (readable >=  4) {
            if (readable > MAX_FRAME_SIZE) {
                byteBuf.skipBytes(readable);
                throw new TooLongFrameException("Frame size is " +readable+", which bigger than max size "+MAX_FRAME_SIZE);
            }
            list.add(new UnixTime(byteBuf.readUnsignedInt()));
        }
    }
}
