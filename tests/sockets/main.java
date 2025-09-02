import java.net.ServerSocket;
import java.io.IOException;

public class main {

	static private String MY_NAME = "main";

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
        String msg = "Socket tests with a parent thread (main) and 2 child threads (server, client)";
        System.out.println(msg);

        PrintingSynced ps = new PrintingSynced();

        int port = getPort();
        MyThread mthServer = new MyThread(MyThread.TAG_SERVER, port);
        MyThread mthClient = new MyThread(MyThread.TAG_CLIENT, port);

        ps.printLabeledMsg(MY_NAME, "Server and client threads are runnable; will start them now");
        mthServer.start();
        mthClient.start();
 
        // Wait for threads to end
        ps.printLabeledMsg(MY_NAME, "Waiting for server and client threads to end");
        try {
            mthServer.join();
            ps.printLabeledMsg(MY_NAME, "server joined");
            mthClient.join();
            ps.printLabeledMsg(MY_NAME, "client joined");
        } catch (Exception ex) {
            ps.printLabeledMsg(MY_NAME, "Interrupted !!");
        }
        ps.printLabeledMsg(MY_NAME, "End");
        
        Checkers.theEnd(0);
    }
}

class MyThread extends Thread {
	public static String TAG_SERVER = "server";
	public static String TAG_CLIENT = "client";

    private Thread th;
    private String name;
    private int port;

    MyThread(String argName, int argPort) {
        name = argName;
        port = argPort;
    }

    public void run() {
    	PrintingSynced ps = new PrintingSynced();
        ps.printLabeledMsg(name, "run() called");
        if (name.equals(TAG_SERVER)) {
        	SocketServer ss = new SocketServer();
        	ss.exec(port);
        } else {
        	SocketClient sc = new SocketClient();
        	sc.exec(port);
        }
        ps.printLabeledMsg(name, "run() finished");
    }

    public void start() {
    	PrintingSynced ps = new PrintingSynced();
        ps.printLabeledMsg(name, "start() called");
        th = new Thread(this, name);
        th.start();
        ps.printLabeledMsg(name, "start() finished");
    }
}

