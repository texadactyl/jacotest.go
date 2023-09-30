import java.nio.ByteBuffer;  
import java.nio.CharBuffer;  
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

public class main {

    public static void printer(String label, Object value) {
        System.out.print(label);
        System.out.print(" : ");
        System.out.println(value);
    }

	private static void showCharset(Charset argCharset, String argString) throws CharacterCodingException {
        String name = argCharset.name();
        String desc = argCharset.toString();
        
        printer("\nCharacter set", name);
        printer("Description", desc);
		
	 	ByteBuffer bytebuffer1 = ByteBuffer.wrap(argString.getBytes());  
	 	bytebuffer1.rewind();
	 	int hash1 = bytebuffer1.hashCode();
	 	printer("hash1", hash1);
	 	byte[] bytearray1 = bytebuffer1.array();
	 	printer("byte array 1 length", bytearray1.length);
	 	printer("byte array 1 as String", Arrays.toString(bytearray1));

	 	// CharsetDecoder
	 	
		CharsetDecoder csde = argCharset.newDecoder();
		printer("Decoder Average number of characters per byte of input", csde.averageCharsPerByte());
		printer("Decoder Maximum number of characters per byte of input", csde.maxCharsPerByte());
		printer("Decoder Autodetecting?", csde.isAutoDetecting());

		assert csde.replacement().equals("\uFFFD") : "csde.replacement() != \uFFFD";
		
		CodingErrorAction cea1 = csde.malformedInputAction();
		argString = cea1.toString();
		printer("Decoder's current action for malformed-input errors", argString);

		// CharsetEncoder
		
		CharsetEncoder csen = argCharset.newEncoder();
		printer("Encoder Average number of bytes per character of input", csen.averageBytesPerChar());
		printer("Encoder Maximum number of bytes per character of input", csen.maxBytesPerChar());
		byte[] repl = "\uFFFE".getBytes();
		printer("Encoder Legal replacement value?", csen.isLegalReplacement(repl));

		assert csen.replacement().equals("\uFFFD") : "csen.replacement() != \uFFFD";
		
		cea1 = csde.malformedInputAction();
		argString = cea1.toString();
		printer("Encoder's current action for malformed-input errors", argString);
		
		// Use CharsetEncoder/Decoder
		
		bytebuffer1.rewind();
    	CharBuffer charbuffer = csde.decode(bytebuffer1);  
    	ByteBuffer bytebuffer2 = csen.encode(charbuffer);   
	 	bytebuffer2.rewind();
		int hash2 = bytebuffer2.hashCode();
		printer("hash2 after CharsetEncoder", hash2);
	 	byte[] bytearray2 = bytebuffer2.array();
	 	printer("byte array 2 length after CharsetEncoder", bytearray2.length);
	 	printer("byte array 2 as String", Arrays.toString(bytearray2));
		
	 	assert hash1 == hash2 :  "hash1 != hash2 after CharsetEncode";
	 	assert Arrays.equals(bytearray1, bytearray2) : "byte arrays not equal after CharsetEncode";
		
	}

	public static void main(String[] args) throws CharacterCodingException {
	
		String ss = "Mary had a little lamb whose fleece was white as snow.";  
		
		showCharset(Charset.defaultCharset(), ss);
		showCharset(Charset.forName("UTF-8"), ss);
		showCharset(Charset.forName("ISO-8859-1"), ss);
		showCharset(Charset.forName("US-ASCII"), ss);
		showCharset(Charset.forName("UTF-16BE"), ss);
		showCharset(Charset.forName("UTF-16LE"), ss);
		
    }
}
