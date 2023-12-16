// Hacked from https://www.digitalocean.com/community/tutorials/java-socket-programming-server-client (unrealistic, IMO)

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class MyClient {

    private String MY_NAME = "MyClient";
	private String URL_PREFIX = "http://localhost";
    private int stCodeOk = 200;

    public void exec(int port, SimpleHttpServer shs) {

        // Health check.
        sendReceive(port, "/", null);

        // Echo header in response.
        sendReceive(port, "/echoHeader", null);

        // Simulate parameters in the location window (GET).
        sendReceive(port, "/echoGet?fruit=apple&veggie=celery&drink=milk", null);

        // Simulate an HTML form (POST).
        Map<Object, Object> parmMap = new HashMap<>();
        parmMap.put("fruit", "banana");
        parmMap.put("veggie", "carrot");
        parmMap.put("drink", "water");
        sendReceive(port, "/echoPost", parmMap);

        shs.stopper();
    }

    private void sendReceive(int port, String path, Map<Object, Object> parmMap) {

        HttpRequest request;
        HttpResponse<String> response;

        // Synced printing.
        PrintingSynced ps = new PrintingSynced();

        // Send request and receive response.
        try {
            String target = String.format("%s:%d%s", URL_PREFIX, port, path);
            String msg = String.format("sendReceive path: %s", target);
            ps.printLabeledMsg(MY_NAME, msg);
            HttpClient client = HttpClient
                    .newBuilder()
                    .connectTimeout(Duration.ofMillis(20000))
                    .build();
            if (parmMap != null) {
                request = HttpRequest
                        .newBuilder()
                        .POST(buildFormDataFromMap(parmMap))
                        .uri(URI.create(target))
                        .build();
            } else {
                request = HttpRequest
                        .newBuilder()
                        .uri(URI.create(target))
                        .GET()
                        .build();
            }
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            String errMsg = String.format("*** ERROR, MyClient sendReceive: %s\n", ex.getMessage());
            throw new AssertionError(errMsg);
        } catch (InterruptedException ex) {
            String errMsg = String.format("*** ERROR, MyClient sendReceive: %s\n", ex.getMessage());
            throw new AssertionError(errMsg);
        }

        // Process response.
        int stCode = response.statusCode();
        if (stCode == stCodeOk) {
            ps.printLabeledMsg(MY_NAME, response.headers().toString());
            ps.printLabeledMsg(MY_NAME, response.body());
        } else {
            String errMsg = String.format("*** ERROR, MyClient sendReceive: status code: %d\n", stCode);
            throw new AssertionError(errMsg);
        }

    } // private void sendReceive(int port, String path)

    // Prepare form data from map for POST processing.
    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}
