// hacked from https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.IOException;

public class main {

    private static int getPort() {
        // Ref: https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
        for (int candidate = 3101; candidate < 3128; candidate++) {
            try {
                ServerSocket socket = new ServerSocket(candidate);
                socket.close();
                return candidate;
            } catch (IOException e) { }
        }
        throw new AssertionError("*** ERROR, No available TCP ports");
    }

    public static void main(String[] args) {
	    String MY_NAME = "main";
        String CONTEXT = "/ghurkin";
        int portnum = -1;
        int errorCount = 0;
        
        String msg = "HTTP client/server tests";
        System.out.println(msg);
        PrintingSynced ps = new PrintingSynced();
        portnum = getPort();

        // Initialise the server and start it.
        SimpleHttpServer server = new SimpleHttpServer();
        server.Start(portnum);
        msg = String.format("HTTP Server thread started using port %d", portnum);
        ps.printLabeledMsg(MY_NAME, msg);

        // Initialise the client and run it.
        MyClient client = new MyClient();
        errorCount += client.exec(portnum, server);

        // The client and server are finished.
        ps.printLabeledMsg(MY_NAME, "End");
        
        // Qualitative tests were performed in MyClient.java
        Checkers.theEnd(errorCount);
    }
}
