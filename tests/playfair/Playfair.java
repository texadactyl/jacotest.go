// hacked from https://github.com/Elena-Marin/PlayfairJava/blob/master/src/Playfair.java

import java.io.*;
import java.util.*;

public class Playfair {

	private static final String[][] key = new String[5][5];

    //Transform text into uppercase 
    // + remove all special characters and numbers
    // + replace J with I
    //Used for preparing the text to be encrypted and also for preparing the encryption key
    public String formatText(String textForEncryption){
    
        String transformedText = textForEncryption.toUpperCase();
        transformedText = transformedText.replaceAll("\\s+","Q");
        transformedText = transformedText.replaceAll("\\t","Q");
        transformedText = transformedText.replaceAll("\\n","W");
        transformedText = transformedText.replaceAll("[^A-Z]", "");
        transformedText = transformedText.replace("J", "I");
        
        return transformedText;
    }

    //Transform the text into pairs and create an array for easy handling
    //If there are groups of 2 characters with the same letter, replace the last one with "Y"
    //If the last group of characters is odd, then insert "Q" as last character
    public ArrayList<String> prepareText(String textFormatted){

        ArrayList<String> textArray = new ArrayList<>();

        int i = 0;
        while(i < textFormatted.length()){

            int j = i + 2;
            if(j > textFormatted.length())
                j = textFormatted.length();
            textArray.add(textFormatted.substring(i,j));
            i = j;
        }

        int j = 0;
        while(j < textArray.size()-1){

            if(String.valueOf(textArray.get(j).charAt(0)).equals(String.valueOf(textArray.get(j).charAt(1)))){
                textArray.set(j, textArray.get(j).charAt(0) + "Y");
            }
            j++;
        }

        int lastItem = textArray.size()-1;

        if((textArray.get(lastItem).length()) == 1) {
            textArray.set(lastItem, String.format("%sQ", textArray.get(lastItem)));
        }

        return textArray;
    }

    //Format the encryption key text
    //Remove the duplicates and insert the rest of the letters from alphabet
    public String prepareEncryptionKey(String encryptionKey){

        String inputEncryptionKey = formatText(encryptionKey);
        StringBuilder outputEncryptionKey = new StringBuilder();

        for(int i = 0; i < inputEncryptionKey.length(); i++){
            if(!outputEncryptionKey.toString().contains(String.valueOf(inputEncryptionKey.charAt(i)))) {
                outputEncryptionKey.append(inputEncryptionKey.charAt(i));
           }
        }

        return outputEncryptionKey.toString();
    }

    //Using the preparedEncryptionKey - prepare the encryption matrix
    public String[][] tableMatrix(String encryptionKeyPrepared){

        int matrixCount = 0;
        for(int m=0; m < 5; m++)
        {
            for(int n=0; n < 5; n++)
            {
                key[m][n] = String.valueOf(encryptionKeyPrepared.charAt(matrixCount++));
                System.out.print(" "); 
                System.out.print(key[m][n]);
            }
            System.out.println();
        }
        return key;
    }

    // Searches matrix for char index
    //It will return the row and the column of the character from the matrix
    public int[] findRowColumn (String character){
        int[] pair = {-1,-1}; // row,column

        out:
        for(int row = 0; row < 5; row++) // row
        {
            for(int column = 0; column < 5; column++) // column
            {
                if(key[row][column].equals(character))
                {
                    pair[0] = row;
                    pair[1] = column;
                    break out;
                }
            }
        }
        return pair;
    }

    //Encrypt/Decrypt the text prepared using the encryption key
    public ArrayList<String>  encryptDecrypt(String type, ArrayList<String> textPrepared){
        int[] firstChar;
        int[] secondChar;

        ArrayList<String> converted = new ArrayList<>(); //(ArrayList<String>) (textPrepared.clone());

        int mod = 0;

        if (type.equals("e")){ //"e" used for encryption
            mod = 1;
        }
        else if(type.equals("d")) { //"d" used for decryption
            mod = 4;
        }

        for (int ix = 0; ix < textPrepared.size(); ix++) {
            String s = textPrepared.get(ix);
            firstChar = findRowColumn(String.valueOf(s.charAt(0)));
            secondChar = findRowColumn(String.valueOf(s.charAt(1)));

            if (firstChar[0] == secondChar[0]) // same row
            {
                converted.add(key[firstChar[0]][(firstChar[1] + mod) % 5] + key[secondChar[0]][(secondChar[1] + mod) % 5]);

            } else if (firstChar[1] == secondChar[1]) // same column
            {
                converted.add(key[(firstChar[0] + mod) % 5][firstChar[1]] + key[(secondChar[0] + mod) % 5][secondChar[1]]);

            } else // box
            {
                converted.add(key[firstChar[0]][secondChar[1]] + key[secondChar[0]][firstChar[1]]);
            }
        }
    return converted;
    }

    //After decryption, replace the Y and Q characters back
    public ArrayList<String> prepareDecryptedText(ArrayList<String> decryptedText) {
        
        // ArrayList<String> decryptedTextPrepared = (ArrayList<String>) decryptedText.clone();
        ArrayList<String> decryptedTextPrepared = decryptedText;

        for (int i = 0; i < decryptedText.size(); i++) {
            if (String.valueOf(decryptedText.get(i).charAt(1)).equals("Y")) {
                decryptedTextPrepared.set(i, (String.valueOf(decryptedText.get(i).charAt(0)) + String.valueOf(decryptedText.get(i).charAt(0))));
            }
            if (String.valueOf(decryptedText.get(i).charAt(0)).equals("Q")) {
                decryptedTextPrepared.set(i, (" " + String.valueOf(decryptedText.get(i).charAt(1))));
            }
            if (String.valueOf(decryptedText.get(i).charAt(1)).equals("Q")) {
                decryptedTextPrepared.set(i, (String.valueOf(decryptedText.get(i).charAt(0))) + " ");
            }
        }

        int lastItem = decryptedText.size() - 1;

        if (String.valueOf(decryptedText.get(lastItem).charAt(1)).equals("Q")) {
            decryptedTextPrepared.set(lastItem, String.valueOf(decryptedText.get(lastItem).charAt(0)));

        }
        return decryptedTextPrepared;
    }

    //Covert an array list to String
    public String arraylistToString (ArrayList < String > textToConvert) {
        StringBuilder textConverted = new StringBuilder();
        for (String s : textToConvert) {
            textConverted.append(s);
        }
        return textConverted.toString();
    }

}

