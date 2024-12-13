/*

5G-AKA (Authentication and Key Agreement) -- thanks to ChatGPT.com

Standardization: Authentication framework in 5G networks.
Purpose: Secures initial attachment and subsequent handovers.
Key Features:
* Improved privacy and security over earlier AKA protocols.
* Uses home and serving network keys to authenticate devices.

*/

import java.util.Arrays;

public class main {

    private static final int KEY_SIZE = 256 / 8; // Key size in bytes (256 bits)
    private static final int RAND_SIZE = 128 / 8; // Random challenge size (128 bits)
    private static final int MAC_SIZE = 128 / 8; // MAC size (128 bits)

    // Simulate Home Network Authentication Vector Generation
    public static AuthenticationVector generateAuthenticationVector(byte[] key, byte[] rand) {
        if (key.length != KEY_SIZE || rand.length != RAND_SIZE) {
            throw new IllegalArgumentException("Invalid key or RAND size.");
        }

        byte[] mac = new byte[MAC_SIZE];
        byte[] xres = new byte[MAC_SIZE];
        byte[] autn = new byte[MAC_SIZE];

        // Generate MAC (Message Authentication Code)
        for (int i = 0; i < MAC_SIZE; i++) {
            mac[i] = (byte) (key[i % key.length] ^ rand[i % rand.length]);
        }

        // Generate XRES (Expected Response)
        for (int i = 0; i < MAC_SIZE; i++) {
            xres[i] = (byte) (mac[i] ^ 0xA5); // Example transformation
        }

        // Generate AUTN (Authentication Token)
        for (int i = 0; i < MAC_SIZE; i++) {
            autn[i] = (byte) (mac[i] ^ 0x5A); // Example transformation
        }

        return new AuthenticationVector(mac, xres, autn);
    }

    // Simulate UE Authentication
    public static boolean authenticate(AuthenticationVector authVector, byte[] rand, byte[] key) {
        byte[] mac = authVector.getMac();
        byte[] xres = authVector.getXres();

        // Generate MAC (Message Authentication Code)
        byte[] generatedMac = new byte[MAC_SIZE];
        for (int i = 0; i < MAC_SIZE; i++) {
            generatedMac[i] = (byte) (key[i % key.length] ^ rand[i % rand.length]);
        }

        // Compare MACs
        if (!Arrays.equals(mac, generatedMac)) {
            return false; // Authentication failed
        }

        // Generate UE's RES (Response)
        byte[] res = new byte[MAC_SIZE];
        for (int i = 0; i < MAC_SIZE; i++) {
            res[i] = (byte) (generatedMac[i] ^ 0xA5);
        }

        // Compare RES and XRES
        return Arrays.equals(res, xres);
    }

    public static void main(String[] args) {
        // Example key and rand would be replaced with secure random values in real life.
        byte[] key = new byte[KEY_SIZE];
        byte[] rand = new byte[RAND_SIZE];
        Random random = new Random();
        random.nextBytes(key);
        random.nextBytes(rand);

        System.out.printf("key: %s\n", Arrays.toString(key));
        System.out.printf("rand: %s\n", Arrays.toString(rand));

        // Generate Authentication Vector
        AuthenticationVector authVector = generateAuthenticationVector(key, rand);
        System.out.printf("mac: %s\n", Arrays.toString(authVector.getMac()));
        System.out.printf("xres: %s\n", Arrays.toString(authVector.getXres()));
        System.out.printf("autn: %s\n", Arrays.toString(authVector.getAutn()));

        // Authenticate UE
        boolean isAuthenticated = authenticate(authVector, rand, key);
        
        // How did we do?
        if (isAuthenticated) {
            System.out.println("Success!");
            System.exit(0);
        }
        throw new AssertionError("*** ERROR, authentication failed!");
    }
}

class AuthenticationVector {
    private final byte[] mac;
    private final byte[] xres;
    private final byte[] autn;

    public AuthenticationVector(byte[] mac, byte[] xres, byte[] autn) {
        this.mac = mac;
        this.xres = xres;
        this.autn = autn;
    }

    public byte[] getMac() {
        return mac;
    }

    public byte[] getXres() {
        return xres;
    }

    public byte[] getAutn() {
        return autn;
    }
}

class Random {

    void nextBytes(byte[] vector) {
        long longValue;
        for (int ix = 0; ix < vector.length; ++ix) {
            longValue = System.nanoTime();
            vector[ix] = (byte) (longValue & 0xFF);          
        }
    }
    
}

