package Http;

import Commons.LocalServer;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new HttpServerInitializer()).run();
    }
}
