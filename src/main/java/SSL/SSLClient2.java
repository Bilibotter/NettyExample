package SSL;

import Chat.ChatClient;

public class SSLClient2 {
    public static void main(String[] args) throws Exception {
        new ChatClient(new SSLClientInitializer()).run();
    }
}
