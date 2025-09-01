import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
 
public class main {

    private static String DATA = "./data.bintext";
 
    public static void rptStr(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public static String getCharacterEncoding()  throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(DATA);
        InputStreamReader streamreader = new InputStreamReader(fis);
        String defaultCharset = streamreader.getEncoding();
        return defaultCharset;
    }
 
    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException, IOException { 
        String defaultencoding = System.getProperty("file.encoding");
        rptStr("Default Charset via 'file.encoding' property", defaultencoding);
        String str = getCharacterEncoding();
        rptStr("Default Charset by InputStreamReader", str);
        str = Charset.defaultCharset().toString();
        rptStr("Default Charset via Charset.defaultCharset()", str);
        
        Checkers.theEnd(0);
    }
    
}
