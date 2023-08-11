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

	private static void printLabeledValue(String label, Object value) {
		System.out.print(label);
		System.out.print(": [");
		System.out.print(value);
		System.out.println("]");
	}

     public static void main(String[] args) {
     
     	Playfair pf = new Playfair();

        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String clearText = "Mary had a little lamb whose fleece was white as snow!";
        String expectedClearText = "MARR HAD A LITTLE LAMB WHOSE FLEECE WAS WHITE AS SNOW ";
        String clearKey = "qazwsx123$%^";
        
        printLabeledValue("Raw cleartext", clearText);
        printLabeledValue("Raw key", clearKey);

        String clearKeyPrepared = pf.prepareEncryptionKey(clearKey + alphabet);
        printLabeledValue("Prepared key", clearKeyPrepared);

        String[][] keyMatrix = pf.tableMatrix(clearKeyPrepared);
        
        // Encryption --------------------------------------------------
        System.out.println("..... ENCRYPTION .....");

        String cleartextFormatted = pf.formatText(clearText);
        printLabeledValue("Formatted cleartext",  cleartextFormatted);

        ArrayList<String> cleartextPrepared = pf.prepareText(cleartextFormatted);
        printLabeledValue("Prepared cleartext",  cleartextPrepared);

        ArrayList<String> encryptedArrayList = pf.encryptDecrypt("e", cleartextPrepared);
        printLabeledValue("Prepared ciphertext",  encryptedArrayList);
        
        String encryptedText = pf.arraylistToString(encryptedArrayList);
        printLabeledValue("Raw ciphertext",  encryptedText);

        // Decryption --------------------------------------------------
        System.out.println("..... DECRYPTION .....");

        ArrayList<String> decryptedArrayList = pf.encryptDecrypt("d", encryptedArrayList);
        printLabeledValue("Cleartext2 as an array list",  decryptedArrayList);
        
        ArrayList<String> clearText2Prepared = pf.prepareDecryptedText(decryptedArrayList);
        printLabeledValue("Prepared clearText2",  clearText2Prepared);

        String clearText2 = pf.arraylistToString(pf.prepareDecryptedText(decryptedArrayList));
        printLabeledValue("Raw cleartext 2",  clearText2);
        printLabeledValue("Expected cleartext 2", expectedClearText);
        
        assert(clearText2.equals(expectedClearText));
        
        System.out.println("..... END .....");

    }
    
}
