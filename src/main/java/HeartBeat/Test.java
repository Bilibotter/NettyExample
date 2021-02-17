package HeartBeat;

import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        int readerIdleTime = 4;
        int writerIdleTime = 5;
        int allIdleTime = 7;
        new IdleStateHandler(4, 5, 7, TimeUnit.SECONDS);
    }
}
