// Hacked from http://www.academicpub.org/PaperInfo.aspx?PaperID=14496 .

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

public class main {

    final static String PARMSPEC_ECGEN = "secp384r1";
    final static String SIGNATURE_ALGORITHM = "SHA384withECDSA";
    final static String PROVIDER = "SunEC";
    final static String KPG_ALGORITHM = "EC";

    public static void main(String[] args) throws Exception {

        System.out.println("Elliptic cryptography exercise");

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KPG_ALGORITHM, PROVIDER);

        ECGenParameterSpec ecsp = new ECGenParameterSpec(PARMSPEC_ECGEN);
        kpg.initialize(ecsp);

        KeyPair kp = kpg.genKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();
        System.out.print("Public key: ");
        System.out.println(pubKey.toString());
        System.out.print("Private key: ");
        System.out.println(privKey.toString());

        Signature ecdsa = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
        ecdsa.initSign(privKey);

        String text = "Mary had a little lamb";
        System.out.print("Original cleartext: ");
        System.out.println(text);

        // Sign original cleartext
        byte[] clearTextBytes = text.getBytes("UTF-8");
        ecdsa.update(clearTextBytes);
        byte[] baSignature = ecdsa.sign();

        // Verify signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
        signature.initVerify(pubKey);
        signature.update(clearTextBytes);
        boolean success = signature.verify(baSignature);

        // Test for success
        if (success) {
            System.out.println("Signature verified");
        } else {
            System.out.println("*** FAILED, Signature verification");
            System.exit(1);
        }
    }
}
