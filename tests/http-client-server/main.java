// hacked from https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.IOException;

public class main {

	static private String MY_NAME = "main";
    static private String CONTEXT = "/ghurkin";
    static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
    static public int port = -1;

    private static int getPort() {
        // Ref: https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
        for (int port = 3101; port < 3128; port++) {
            try {
                ServerSocket socket = new ServerSocket(port);
                socket.close();
                return port;
            } catch (IOException e) { }
        }
        throw new AssertionError("*** ERROR, No available TCP ports");
    }

    public static void main(String[] args) {
        String msg = "HTTP client/server tests";
        System.out.println(msg);
        PrintingSynced ps = new PrintingSynced();
        port = getPort();

        // Initialise the server and start it.
        SimpleHttpServer server = new SimpleHttpServer();
        server.Start(port);
        msg = String.format("HTTP Server thread started using port %d", port);
        ps.printLabeledMsg(MY_NAME, msg);

        // Initialise the client and start it.
        MyClient client = new MyClient();
        client.exec(port, server);

        // The client and server are finished.
        ps.printLabeledMsg(MY_NAME, "End");
    }
}
