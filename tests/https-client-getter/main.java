import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import javax.net.ssl.SSLSession;
import java.security.cert.Certificate;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.net.ssl.SSLPeerUnverifiedException;

public class main {

    private static final String GetURLString = "https://www.netcraft.com/";
    private static final int Timeout = 20000;
    private static final int stCodeOk = 200;

    public static void main(String[] args) {

        HttpResponse<String> response;
        Certificate[] certs;

        // Send request and receive response.
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .connectTimeout(Duration.ofMillis(20000))
                    .build();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(GetURLString))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            String errMsg = String.format("*** ERROR, syncedGet: %s\n", ex.getMessage());
            throw new AssertionError(errMsg);
        } catch (InterruptedException ex) {
            String errMsg = String.format("*** ERROR, syncedGet: %s\n", ex.getMessage());
            throw new AssertionError(errMsg);
        }

        // Process response.
        int stCode = response.statusCode();
        if (stCode == stCodeOk) {
            System.out.println(response.headers());
            Optional<SSLSession> optSSLSession = response.sslSession();
            SSLSession sslSession = optSSLSession.get();
            System.out.printf("\n\nProtocol: %s\n\n", sslSession.getProtocol());
            try {
                certs = sslSession.getPeerCertificates();
            } catch (SSLPeerUnverifiedException ex) {
                String errMsg = String.format("*** ERROR, syncedGet: %s\n", ex.getMessage());
                throw new AssertionError(errMsg);
            }
            int counter = 0;
            for (Certificate cert : certs) {
                counter++;
                System.out.printf("\n+++++ Certificate #%d +++++\n\n", counter);
                System.out.println(cert.toString());
            }
        } else {
            String errMsg = String.format("*** ERROR, syncedGet: status code: %d\n", stCode);
            throw new AssertionError(errMsg);
        }

    } // public static void main(String[] args)

}
