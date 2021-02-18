package HeartBeat;

import Commons.LocalClient;

public class HeartBeatClient {
    public static void main(String[] args) throws Exception {
        new LocalClient(new HeartBeatClientInitializer()).run();
    }
}
