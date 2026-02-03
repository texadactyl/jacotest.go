import java.security.Provider;
import java.security.Security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class main {

    public static void main(String[] args) {
    
        int errorCount = 0;
    
        try {
            // Get the default provider (Jacobin Go runtime provider)
            Provider providers[] = Security.getProviders(); 
            Provider defaultProvider = providers[0];
            if (defaultProvider == null) {
                System.out.println("Default provider not found!");
                return;
            }

            System.out.printf("Provider name: %s\n", defaultProvider.getName());

            // Get the MessageDigest SHA-256 service from the provider
            Provider.Service sha256Service = defaultProvider.getService("MessageDigest", "SHA-256");
            System.out.printf("sha256Service(string): %s\n", sha256Service.toString());
            if (sha256Service == null) {
                System.out.println("Service not found!");
                return;
            }
            System.out.printf("Attribute 'blockSize': %s\n", sha256Service.getAttribute("blockSize"));
            String className = sha256Service.getClassName();
            System.out.printf("ClassName: %s\n", className);

            String type = sha256Service.getType();
            Checkers.checker("Type", "MessageDigest", type);
            String algorithm = sha256Service.getAlgorithm();
            Checkers.checker("Algorithm", "SHA-256", algorithm);
            String implementedIn = sha256Service.getAttribute("ImplementedIn");
            Checkers.checker("ImplementedIn", "Software", implementedIn);

            // Now, create a MessageDigest instance via the service
            MessageDigest md = MessageDigest.getInstance(sha256Service.getAlgorithm(), defaultProvider);

            // Hash some data
            byte[] data = "Mary had a little lamb whose fleece was white as snow!".getBytes();
            byte[] digest = md.digest(data);

            // Print digest as hex
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02x", b & 0xff));
            }
            Checkers.checker("SHA-256 digest", "dfc025fe1a8329c15ed6f362f49c0db009dc82d64363e21125b4f3773c145dc4", hex.toString());
            
        } catch (NoSuchAlgorithmException ex) {
            throw new AssertionError("*** ERROR, NoSuchAlgorithmException thrown");
        }
        
        Checkers.theEnd(errorCount);

    }
}

