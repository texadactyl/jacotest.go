// https://en.wikipedia.org/wiki/Playfair_cipher
// hacked from https://github.com/Elena-Marin/PlayfairJava/blob/master/src/Playfair.java

/***

The Playfair-Wheatstone algorithm was initially rejected by the British Foreign Office when it was developed because of its perceived complexity. 
Wheatstone offered to demonstrate that three out of four boys in a nearby school could learn to use it in 15 minutes, 
but the Under Secretary of the Foreign Office responded, "That is very possible, but you could never teach it to attachés.

***/

import java.io.*;
import java.util.*;

public class main {

	public static void main(String[] args) {
     
     	Playfair pf = new Playfair();

        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String clearText = "Mary had a little lamb whose fleece was white as snow!";
        String expectedClearText = "MARR HAD A LITTLE LAMB WHOSE FLEECE WAS WHITE AS SNOW ";
        String clearKey = "qazwsx123$%^";
        
        System.out.printf("Raw cleartext: \"%s\"\n", clearText);
        System.out.printf("Raw key: \"%s\"\n", clearKey);

        String clearKeyPrepared = pf.prepareEncryptionKey(clearKey + alphabet);
        System.out.printf("Prepared key: \"%s\"\n", clearKeyPrepared);

        String[][] keyMatrix = pf.tableMatrix(clearKeyPrepared);
        
        // Encryption --------------------------------------------------
        System.out.println("..... ENCRYPTION .....");

        String cleartextFormatted = pf.formatText(clearText);
        System.out.printf("Formatted cleartext: \"%s\"\n",  cleartextFormatted);

        ArrayList<String> cleartextPrepared = pf.prepareText(cleartextFormatted);
        System.out.printf("Prepared cleartext: \"%s\"\n",  cleartextPrepared);

        ArrayList<String> encryptedArrayList = pf.encryptDecrypt("e", cleartextPrepared);
        System.out.printf("Prepared ciphertext: \"%s\"\n",  encryptedArrayList);
        
        String encryptedText = pf.arraylistToString(encryptedArrayList);
        System.out.printf("Raw ciphertext: \"%s\"\n",  encryptedText);

        // Decryption --------------------------------------------------
        System.out.println("..... DECRYPTION .....");

        ArrayList<String> decryptedArrayList = pf.encryptDecrypt("d", encryptedArrayList);
        System.out.printf("Cleartext2 as an array list: \"%s\"\n",  decryptedArrayList);
        
        ArrayList<String> clearText2Prepared = pf.prepareDecryptedText(decryptedArrayList);
        System.out.printf("Prepared clearText2: \"%s\"\n",  clearText2Prepared);

        String clearText2 = pf.arraylistToString(pf.prepareDecryptedText(decryptedArrayList));
        System.out.printf("Raw cleartext 2: \"%s\"\n",  clearText2);
        System.out.printf("Expected cleartext 2: \"%s\"\n", expectedClearText);
        
        assert(clearText2.equals(expectedClearText));
        
        System.out.println("..... END .....");

    }
    
}
