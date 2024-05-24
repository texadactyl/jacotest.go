import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
 
public class main {
 
    public static void rptStr(String label, String value) {
        System.out.print(label);
        System.out.print(": ");
        System.out.println(value);
    }

    public static String getCharacterEncoding() {
        byte[] byte_array = { 'w' };
        ByteArrayInputStream bais = new ByteArrayInputStream(byte_array);
        InputStreamReader streamreader = new InputStreamReader(bais);
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
    }
    
}
