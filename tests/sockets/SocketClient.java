// Hacked from https://www.digitalocean.com/community/tutorials/java-socket-programming-server-client (unrealistic, IMO)
 
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.InetAddress;

public class SocketClient {

    private int MAX_WAIT_ACCEPT = 3; // seconds
    private int SLEEP_TIME = 300; // milliseconds
    private String MY_NAME = "SocketClient";
    private Socket socket;
    private boolean DEBUGGING = false;

    public void exec(int port) {
    
    	// Synced printing.
    	PrintingSynced ps = new PrintingSynced();

		try {
		
		    // Create a socket client.
			InetAddress host = InetAddress.getLocalHost();
			String hostName = host.getHostName();
		    while (true) {
		    	try {
					socket = new Socket(hostName, port);
					break;
				}
				catch (ConnectException ee) {
					ps.printLabeledMsg(MY_NAME, "ConnectException, retrying connection");
					continue; 
				}
		    }
		    String msg = String.format("Connected %s", socket.toString());
		    ps.printLabeledMsg(MY_NAME, msg);
		    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
		    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
		    String requestMessage, responseMessage;
		    
		    for(int counter = 1; counter < 6; counter++) {
		    
		    	// Write requestMessage to server.
		        if (counter == 5)
		        	requestMessage = "exit";
		        else
		    		requestMessage = String.format("counter = %d", counter);
		    	dataOut.writeUTF(requestMessage);
		    	dataOut.flush();
		    	if (DEBUGGING)
		    		ps.printLabeledMsg(MY_NAME, "DEBUG just sent a message to SocketServer");
		    	
		    	// Sleep a bit.
		    	try {
		        	Thread.sleep(SLEEP_TIME);
		        }
				catch (InterruptedException ee) {
					throw new AssertionError("*** ERROR, Unexpected InterruptedException while sleeping!");
				}
		    	
		    	// Get echoed response.
		    	responseMessage = dataIn.readUTF();
		    	if (DEBUGGING)
		    		ps.printLabeledMsg(MY_NAME, "DEBUG just received a message from SocketServer");
		    	msg = String.format("Message response %d Received: %s", counter, responseMessage);
		    	ps.printLabeledMsg(MY_NAME, msg);
		    	if (! responseMessage.equals(requestMessage)) {
		    		msg = String.format("*** %s: ERROR, request != response !", MY_NAME);
		    		throw new AssertionError(msg);
		    	}
		    }
		    
		    ps.printLabeledMsg(MY_NAME, "Each response matched the corresponding request");
		    dataIn.close();
		    dataOut.close();
		    socket.close();
        
        } 
        
		catch (UnknownHostException ee) {
			ps.printStackTrace(ee);
        	throw new AssertionError("*** ERROR, Unexpected UnknownHostException!");
		}

		catch (IOException ee) {
        	ps.printStackTrace(ee);
        	throw new AssertionError("*** ERROR, Unexpected IOException!");
        }
             
        ps.printLabeledMsg(MY_NAME, "Bye-bye");
    }

}
