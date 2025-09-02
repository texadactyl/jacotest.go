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

    private static void printArrayList(String label, ArrayList<String> input) {
        System.out.printf("%s = [", label);
        int size = input.size();
        for (int ix = 0; ix < size; ++ix) {
            System.out.printf(" %s", input.get(ix));
        }
        System.out.println(" ]");
    }

	public static void main(String[] args) {
     
     	Playfair pf = new Playfair();

        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String clearText = "Mary had a little lamb whose fleece was white as snow!";
        String expectedClearText = "MARR HAD A LITTLE LAMB WHOSE FLEECE WAS WHITE AS SNOW ";
        String clearKey = "qazwsx123$%^";
        
        System.out.printf("Raw cleartext: \"%s\"\n", clearText);
        System.out.printf("Raw key: \"%s\"\n", clearKey);

		String wstr = String.format("%s%s", clearKey, alphabet);
        String clearKeyPrepared = pf.prepareEncryptionKey(wstr);
        System.out.printf("Prepared key: \"%s\"\n", clearKeyPrepared);

        String[][] keyMatrix = pf.tableMatrix(clearKeyPrepared);
        
        // Encryption --------------------------------------------------
        System.out.println("..... ENCRYPTION .....");

        String cleartextFormatted = pf.formatText(clearText);
        System.out.printf("Formatted cleartext: \"%s\"\n",  cleartextFormatted);

        ArrayList<String> cleartextPrepared = pf.prepareText(cleartextFormatted);
        //System.out.printf("Prepared cleartext: \"%s\"\n",  cleartextPrepared);
        printArrayList("Prepared cleartext", cleartextPrepared);

        ArrayList<String> encryptedArrayList = pf.encryptDecrypt("e", cleartextPrepared);
        //System.out.printf("Prepared ciphertext: \"%s\"\n",  encryptedArrayList);
        printArrayList("Prepared ciphertext", encryptedArrayList);
        
        String encryptedText = pf.arraylistToString(encryptedArrayList);
        System.out.printf("Raw ciphertext: \"%s\"\n", encryptedText);

        // Decryption --------------------------------------------------
        System.out.println("..... DECRYPTION .....");

        ArrayList<String> decryptedArrayList = pf.encryptDecrypt("d", encryptedArrayList);
        //System.out.printf("Cleartext2 as an array list: \"%s\"\n",  decryptedArrayList);
        printArrayList("Cleartext2 as an array list", decryptedArrayList);
        
        ArrayList<String> clearText2Prepared = pf.prepareDecryptedText(decryptedArrayList);
        //System.out.printf("Prepared clearText2: \"%s\"\n",  clearText2Prepared);
        printArrayList("Prepared clearText2", clearText2Prepared);

        String clearText2 = pf.arraylistToString(pf.prepareDecryptedText(decryptedArrayList));
        System.out.printf("Raw cleartext 2: \"%s\"\n",  clearText2);
        System.out.printf("Expected cleartext 2: \"%s\"\n", expectedClearText);
        
        assert(clearText2.equals(expectedClearText));
        int errorCount = Checkers.checker("clearText2.equals(expectedClearText", expectedClearText, clearText2);
        
        Checkers.theEnd(errorCount);

    }
    
}
