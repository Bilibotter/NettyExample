package Time;

import Commons.RawServer;
import Discard.DiscardServer;

public class TimeServer {
    public static void main(String[] args) throws Exception {
        new RawServer(new TimeServerHandler()).run();
    }
}
