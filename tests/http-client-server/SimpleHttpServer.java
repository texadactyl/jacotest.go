import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

	private String MY_NAME = "SimpleHttpServer";
	private HttpServer server = null;

	public void Start(int argPort) {
		PrintingSynced ps = new PrintingSynced();
		try {
			server = HttpServer.create(new InetSocketAddress(argPort), 0);
			String msg = String.format("server created to use port %d", argPort);
			ps.printLabeledMsg(MY_NAME, msg);
			server.createContext("/", new Handlers.RootHandler(argPort));
			server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
			server.createContext("/echoGet", new Handlers.EchoGetHandler());
			server.createContext("/echoPost", new Handlers.EchoPostHandler());
			server.setExecutor(null);
			server.start();
			ps.printLabeledMsg(MY_NAME, "HttpServer thread started");
		} catch (IOException ex) {
		    String errMsg = String.format("*** ERROR, HttpServer could not start, errMsg: %s", ex.getMessage());
		    ps.printLabeledMsg(MY_NAME, errMsg);
			ex.printStackTrace();
		}
	}

	public void stopper() {
		PrintingSynced ps = new PrintingSynced();
		server.stop(0);
		ps.printLabeledMsg(MY_NAME, "stopper: HttpServer thread stopped");
	}
}
