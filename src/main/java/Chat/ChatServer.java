package Chat;

import Commons.LocalServer;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        new LocalServer(new ChatServerInitializer()).run();
    }
}
