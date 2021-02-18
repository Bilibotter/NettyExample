package SSL;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;

import java.io.File;

public class SSLBuild {
    public static void main(String[] args) throws Exception {
        File certChainFile=new File("./SSL/nginx.crt");
        File keyFile=new File("./SSL/pkcs8_rsa_private_key.pem");
        SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).clientAuth(ClientAuth.NONE)
                .sslProvider(SslProvider.OPENSSL).build();
    }
}
