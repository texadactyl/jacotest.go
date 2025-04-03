public class main {

	final static int NLOOPS = 1000;

	private static boolean byteArraysEqual(byte[] ba1, byte[] ba2) {
		if (ba1.length - ba2.length != 0)
			return false;
		for (int ii = 0; ii < ba1.length; ii++) {
			if (ba1[ii] != ba2[ii])
				return false;
		}
		return true;
	}

	private static void testNewString() {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testNewString BEGIN");
		String beetlejuice;
		for (int ii = 0; ii < NLOOPS; ii++) {
			beetlejuice = new String();
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testNewString END");
	}

	private static void testGetBytes() {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytes BEGIN");
		String beetlejuice = "Beetlejuice";
		byte[] bb;
		byte[] bb2 = {66, 101, 101, 116, 108, 101, 106, 117, 105, 99, 101};
		
		for (int ii = 0; ii < NLOOPS; ii++) { 
			bb = beetlejuice.getBytes();
			assert(byteArraysEqual(bb, bb2));
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytes END");
	}
	
	private static void subbie() {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytesSubbie subbie BEGIN");
		String beetlejuice = "Beetlejuice";
		byte[] bb;
		byte[] bb2 = {66, 101, 101, 116, 108, 101, 106, 117, 105, 99, 101};
		
		for (int ii = 0; ii < 1; ii++) { 
			bb = beetlejuice.getBytes();
			assert(byteArraysEqual(bb, bb2));
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytesSubbie subbie End");
	}

	private static void testGetBytesSubbie() {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytesSubbie BEGIN");
		subbie();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testGetBytesSubbie END");
	}

	private static void testNewStringGivenBytes() {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testNewStringGivenBytes BEGIN");
		String beetlejuice;
		String beetlejuice2 = "Beetlejuice";
		byte[] bb = {66, 101, 101, 116, 108, 101, 106, 117, 105, 99, 101};
		
		for (int ii = 0; ii < NLOOPS; ii++) { 
			beetlejuice = new String(bb);
			assert beetlejuice.equals(beetlejuice2);
			beetlejuice = null;
		}

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ testNewStringGivenBytes END");
	}

	public static void main(String[] args) {
		testNewString();
		testGetBytesSubbie();
		testGetBytes();
		testNewStringGivenBytes();
	}
	
}
