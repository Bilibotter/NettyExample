package SSL;

import Chat.ChatClient;
import Commons.LocalClient;

public class SSLClient {
    public static void main(String[] args) throws Exception {
        new ChatClient(new SSLClientInitializer()).run();
    }
}
