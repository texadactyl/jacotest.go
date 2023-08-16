// hacked from Unka Andoo

// Java Program to Get and Set
// Default Character encoding or Charset
 
// Importing input output classes
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
// Importing Charset class that defines charsets and
// translation between bytes and Unicode characters.
import java.nio.charset.Charset;
 
// Class 1
// Helper Class for character encoding
// originally: public class javaEncodingInfo

public class main {
 
    public static void rptStr(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    // Method
    // To
    public static String getCharacterEncoding()
    {
 
        // Creating an array of byte type chars and
        // passing random  alphabet as an argument.abstract
        // Say alphabet be 'w'
        byte[] byte_array = { 'w' };
 
        // Creating an object of InputStream
        InputStream instream
            = new ByteArrayInputStream(byte_array);
 
        // Now, opening new file input stream reader
        InputStreamReader streamreader
            = new InputStreamReader(instream);
        String defaultCharset = streamreader.getEncoding();
 
        // Returning default character encoding
        return defaultCharset;
    }
 
    // Main driver method
    public static void main(String args[])
        throws FileNotFoundException,
               UnsupportedEncodingException, IOException
    {
 
        // Method returns a string of character encoding
        // used by using System.getProperty()
        String defaultencoding
            = System.getProperty("file.encoding");
 
        rptStr("Default Charset via 'file.encoding' property", defaultencoding);
 
        // Getting character encoding by InputStreamReader
        rptStr( "Default Charset by InputStreamReader", getCharacterEncoding());
 
        // Getting character encoding by java.nio.charset
        rptStr("Default Charset via Charset.defaultCharset()", Charset.defaultCharset().toString());
    }
}
