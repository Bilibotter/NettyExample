package Https;

import Commons.LocalServer;
import Http.HttpServerInitializer;

public class HttpsServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new HttpsServerInitializer()).run();
    }
}
