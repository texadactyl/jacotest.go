import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HandshakeCompletedEvent;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import javax.net.ssl.SSLSocket;
import java.io.File;
import javax.net.ssl.SSLPeerUnverifiedException;

public class main {

	final static String PATHETIC_PASSWORD = "changeit";
	final static String TLS_URL = "github.com"; 
	final static int TLS_PORT = 443;
	final static int SLEEP_TIME = 2000; // milliseconds
	static boolean handshakeSemaphore = false;
	
	public static class MyHandshakeCompletedListener implements HandshakeCompletedListener {

		@Override
		public void handshakeCompleted(HandshakeCompletedEvent event) {
		
			Helpers hh = new Helpers();
		
			try {
				hh.printLabeledString("handshakeCompleted: peer", event.getPeerPrincipal().getName());
				hh.printLabeledString("handshakeCompleted: cipher suite", event.getCipherSuite());
				Certificate [] peerChain = event.getPeerCertificates();
				for (Certificate cert : peerChain) {
					X509Certificate xcert = (X509Certificate) cert;
					hh.printLabeledString("handshakeCompleted: issuer", xcert.getIssuerX500Principal().getName()); 
				}
				System.out.println("handshakeCompleted: end");
				handshakeSemaphore = true;
			} catch (SSLPeerUnverifiedException ee) {
				ee.printStackTrace();
				System.exit(1);
			}
		}

	}	

    public static void main(String [] args) throws Exception {
    
    	System.out.println("One-way TLS exercise (authenticate server only)");
    
    	SSLSocket sslSocket;

    	System.out.println("main: Make cryptographically strong random number generator");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        
        System.out.println("main: Set a secure random seed");
        secureRandom.setSeed(SecureRandom.getInstanceStrong().generateSeed(32));
        
        System.out.println("main: Set up a TLSv1.3 context");
        SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
        
        System.out.println("main: Get the built-in secure key store handle and initialise a trust manager with it using the default password");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
        KeyStore trustStore = KeyStore.getInstance(new File(System.getProperty("java.home"), "lib/security/cacerts"), PATHETIC_PASSWORD.toCharArray());
        trustManagerFactory.init(trustStore);
        
        System.out.println("main: Initialise the SSL/TLS context"); 
        sslContext.init(null, trustManagerFactory.getTrustManagers(), secureRandom);
        
        System.out.println("main: Set up a HandshakeCompletedListener for the SSL socket"); 
        System.out.println("main: Create  SSL socket by connecting to the URL destination"); 
        MyHandshakeCompletedListener hcl = new MyHandshakeCompletedListener();
        sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(TLS_URL, TLS_PORT);
        sslSocket.addHandshakeCompletedListener(hcl);
        
        System.out.println("main: Shake hands and wait for completion ....."); 
        sslSocket.startHandshake();
        
        // Wait for handshakeCompleted
        while (true) {
	    	try {
	        	Thread.sleep(SLEEP_TIME);
	        }
			catch (InterruptedException ee) {
				ee.printStackTrace();
				System.exit(1);
			}
			if (handshakeSemaphore) { break; }
        }
        
        System.out.println("main: end");
        sslSocket.close();
    }
}
