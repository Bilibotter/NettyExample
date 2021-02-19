package Https;

import Commons.LocalServer;

public class HttpsServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new HttpsServerInitializer()).run();
    }
}
