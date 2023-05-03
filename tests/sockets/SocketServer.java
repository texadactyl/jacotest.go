// Hacked from https://www.digitalocean.com/community/tutorials/java-socket-programming-server-client (unrealistic, IMO)

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketServer {

    private int MAX_READ = 256;
    private int MAX_WAIT_ACCEPT = 3; // seconds
    private int SLEEP_TIME = 1000; // milliseconds
    private String MY_NAME = "SocketServer";

    private ServerSocket server;
    private Socket socket;

    public void exec(int port) {
    
    	// Synced printing.
    	PrintingSynced ps = new PrintingSynced();

		try {

		    // Create a socket server.
		    server = new ServerSocket(port);
		    server.setSoTimeout(MAX_WAIT_ACCEPT);
		    String msg = String.format("%s", server.toString());
		    ps.printLabeledMsg(MY_NAME, msg);
		    ps.printLabeledMsg(MY_NAME, "Waiting for a client connection request .....");

		    // Loop until a connection request is accepted.
		    while (true) {
		        try {
		            socket = server.accept(); // wait up to MAX_WAIT_ACCEPT seconds
		            break; // received a connection request
		        } catch (SocketTimeoutException ex) {
		        	try {
		            	Thread.sleep(SLEEP_TIME);
		            }
					catch (InterruptedException ee) {
						ps.printStackTrace(ee);
						System.exit(1);
					}
		        }
		    }

		    // Connection accepted.
		    // Create input and output streams based on socket.
		    ps.printLabeledMsg(MY_NAME, "Connection accepted");
		    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
		    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

		    // Wait for request.
		    // When received, echo it back to client.
		    // If "exit" socketMessage, then break out of loop.
		    ps.printLabeledMsg(MY_NAME, "Waiting for client socketMessages .....");
		    int counter = 0;
		    while (true) {
		        String socketMessage = dataIn.readUTF();
		        msg = String.format("Message %d Received: %s", ++counter, socketMessage);
		        ps.printLabeledMsg(MY_NAME, msg);

		        // Echo socketMessage back to client
		        dataOut.writeUTF(socketMessage);
		        dataOut.flush();

		        // Exit loop?
		        if (socketMessage.equalsIgnoreCase("exit")) break;
		    }

		    // All done.
		    ps.printLabeledMsg(MY_NAME, "Exit request received");
		    dataIn.close();
		    dataOut.close();
		    socket.close();
		    server.close();
		    
        } catch (IOException ee) {
        	ps.printStackTrace(ee);
        	System.exit(1);
        }
		    
        ps.printLabeledMsg(MY_NAME, "Bye-bye");
    }

}
