import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

	private int port;
	private HttpServer server;
	private String MY_NAME = "SimpleHttpServer";

	public void Start(int port) {
		PrintingSynced ps = new PrintingSynced();
		try {
			this.port = port;
			server = HttpServer.create(new InetSocketAddress(port), 0);
			String msg = String.format("server created to use " + port);
			ps.printLabeledMsg(MY_NAME, msg);
			server.createContext("/", new Handlers.RootHandler());
			server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
			server.createContext("/echoGet", new Handlers.EchoGetHandler());
			server.createContext("/echoPost", new Handlers.EchoPostHandler());
			server.setExecutor(null);
			server.start();
			ps.printLabeledMsg(MY_NAME, "HttpServer thread started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopper() {
		PrintingSynced ps = new PrintingSynced();
		server.stop(0);
		ps.printLabeledMsg(MY_NAME, "stopper: HttpServer thread stopped");
	}
}
