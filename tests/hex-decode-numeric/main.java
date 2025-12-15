public class main {

	private static final int NLOOPS = 1000;
	
	public static void main(String[] args) {
	    int errorCount = 0;
	    
		for (int ii = 0; ii < NLOOPS; ii++ ) {
			int wint = Integer.decode("18");
			String ss1 = String.format("%d", wint);
			System.out.printf("Integer conversion: |%s|\n", ss1);
			
			Byte BB = Byte.decode("#12");
			byte bb = BB.byteValue();
			System.out.printf("byteValue: |%02x| (hex)\n", bb);
			String ss2 = BB.toString();
			System.out.printf("Byte conversion: |%s|\n", ss2);
			
			Checkers.checker("Survive the byte/integer conversions?", ss1, ss2);
			
			Checkers.theEnd(errorCount);
		}
	}
}
