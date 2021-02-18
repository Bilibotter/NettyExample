package SSL;

import Commons.LocalServer;

public class SSLServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new SSLServerInitializer()).run();
    }
}
