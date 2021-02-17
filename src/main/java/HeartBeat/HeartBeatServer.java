package HeartBeat;

import Commons.LocalServer;

public class HeartBeatServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new HeartBeatChannelInitializer()).run();
    }
}
