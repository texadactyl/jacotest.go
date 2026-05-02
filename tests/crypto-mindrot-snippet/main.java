// boiled down from Jacotest case crypto-mindrot-bcrypto

import java.io.UnsupportedEncodingException;

public class main {
    private static final int BCRYPT_SALT_LEN = 16;
    
	// Table for Base64 encoding
	static private final char base64_code[] = {
		'.', '/', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9'
	};

	// Table for Base64 decoding
	static private final byte index_64[] = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 0, 1, 54, 55,
		56, 57, 58, 59, 60, 61, 62, 63, -1, -1,
		-1, -1, -1, -1, -1, 2, 3, 4, 5, 6,
		7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
		-1, -1, -1, -1, -1, -1, 28, 29, 30,
		31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
		51, 52, 53, -1, -1, -1, -1, -1
	};

    public static void main(String[] args) throws UnsupportedEncodingException {
        String real_salt = "DCq7YPn5Rq63x1Lad4cll.";
        System.out.print(HexDump.dumpBytes("main: real_salt", real_salt.getBytes("UTF-8"), real_salt.length(), HexDump.COLUMN_SIZE));
        byte saltb[] = decode_base64(real_salt, BCRYPT_SALT_LEN);
        System.out.print(HexDump.dumpBytes("main: saltb", saltb, saltb.length, HexDump.COLUMN_SIZE));
		String salthex = HexDump.bytesToHex(saltb, saltb.length); 
		int errorCount = Checkers.checker("Salt in hex", "144b3d691a7b4ecf39cf735c7fa7a79c", salthex);
		Checkers.theEnd(errorCount);
    }
    
	private static byte char64(char x) {
		if ((int)x < 0 || (int)x > index_64.length)
			return -1;
		return index_64[(int)x];
	}
    
	private static byte[] decode_base64(String s, int maxolen) throws IllegalArgumentException, UnsupportedEncodingException {
	
		StringBuffer rs = new StringBuffer();
		int off = 0, slen = s.length(), olen = 0;
		byte ret[];
		byte c1, c2, c3, c4, o;

		if (maxolen <= 0)
			throw new IllegalArgumentException ("Invalid maxolen");

        int ix = 0;
		while (off < slen - 1 && olen < maxolen) {
		    ix++;
			c1 = char64(s.charAt(off++));
			c2 = char64(s.charAt(off++));
			if (c1 == -1 || c2 == -1)
				break;
			o = (byte)(c1 << 2);
			o |= (byte)((c2 & 0x30) >> 4);
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c3 = char64(s.charAt(off++));
			if (c3 == -1)
				break;
			o = (byte)((c2 & 0x0f) << 4);
			o |= (byte)((c3 & 0x3c) >> 2);
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c4 = char64(s.charAt(off++));
			o = (byte)((c3 & 0x03) << 6);
			o |= c4;
			rs.append((char)o);
			++olen;
			String rsstr = rs.toString();
			String rshex = HexDump.bytesToHex(rsstr.getBytes("UTF-8"), rsstr.length()); 
			System.out.printf("decode_base64[%d]: rshex: %s\n", ix, rshex);
		}

		ret = new byte[olen];
		for (off = 0; off < olen; off++)
			ret[off] = (byte)rs.charAt(off);
		return ret;
	}
}

