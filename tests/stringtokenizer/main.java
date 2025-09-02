import java.util.StringTokenizer;

public class main {

	private static int test1() {
		System.out.println("================================ test1");
		String in_string = "Mary had a little lamb\nwhose fleece was white as snow";
		int in_ntok = 11;
		int errorCount = 0;
	
		StringTokenizer st = new StringTokenizer(in_string);
		if (st.countTokens() != in_ntok) {
			System.out.printf("test1: *** ERROR, expected countTokens()=%d, observed countTokens()=%d\n", in_ntok, st.countTokens());
			errorCount++;
		}
		 
		int ctTokens = 0;
		while (st.hasMoreTokens()) {
		 	ctTokens++;
		    System.out.println(st.nextToken());
		}
		if(ctTokens != in_ntok) {
			System.out.printf("test1: *** ERROR, expected ctTokens=%d, observed ctTokens=%d\n", in_ntok, ctTokens);
			errorCount++;
		}
		
		return errorCount;
	}
	
	private static int test2() {
		System.out.println("================================ test2");
		String in_string = "Mary;had;a;little;lamb;whose;fleece;was;white;as;snow";
		int in_ntok = 11;
		int errorCount = 0;
	
		StringTokenizer st = new StringTokenizer(in_string, ";");
		if (st.countTokens() != in_ntok) {
			System.out.printf("test2: *** ERROR, expected countTokens()=%d, observed countTokens()=%d\n", in_ntok, st.countTokens());
			errorCount++;
		}
		 
		int ctTokens = 0;
		while (st.hasMoreTokens()) {
		 	ctTokens++;
		    System.out.println(st.nextToken());
		}
		if (ctTokens != in_ntok) {
			System.out.printf("test2: *** ERROR, expected ctTokens=%d, observed ctTokens=%d\n", in_ntok, ctTokens);
			errorCount++;
		}
		
		return errorCount;
	}
	
	private static int test3() {
		System.out.println("================================ test3");
		String in_string = "Mary;had;a;little;lamb;whose;fleece;was;white;as;snow";
		int in_ntok = 21;
		int errorCount = 0;
	
		StringTokenizer st = new StringTokenizer(in_string, ";", true);
		if (st.countTokens() != in_ntok) {
			System.out.printf("test3: *** ERROR, expected countTokens()=%d, observed countTokens()=%d\n", in_ntok, st.countTokens());
			errorCount++;
		}
		 
		int ctTokens = 0;
		while (st.hasMoreTokens()) {
		 	ctTokens++;
		    System.out.println(st.nextToken());
		}
		if (ctTokens != in_ntok) {
			System.out.printf("test3: *** ERROR, expected ctTokens=%d, observed ctTokens=%d\n", in_ntok, ctTokens);
			errorCount++;
		}
		
		return errorCount;
	}
	
	private static int test4() {
		System.out.println("================================ test4");
		String in_string = "Mary;had;a;little;lamb;whose;fleece;was;white;as;snow";
		int in_ntok = 11;
		int errorCount = 0;
	
		StringTokenizer st = new StringTokenizer(in_string, ";", false);
		if (st.countTokens() != in_ntok) {
			System.out.printf("test4: *** ERROR, expected countTokens()=%d, observed countTokens()=%d\n", in_ntok, st.countTokens());
			errorCount++;
		}
		 
		int ctTokens = 0;
		while (st.hasMoreTokens()) {
		 	ctTokens++;
		    System.out.println(st.nextToken());
		}
		if (ctTokens != in_ntok) {
			System.out.printf("test4: *** ERROR, expected ctTokens=%d, observed ctTokens=%d\n", in_ntok, ctTokens);
			errorCount++;
		}
		
		return errorCount;
	}
	
	public static void main(String[] args) {
	
		int errorCount = 0;
		errorCount += test1();
		errorCount += test2();
		errorCount += test3();
		errorCount += test4();
		
		Checkers.theEnd(errorCount);
	
	}
}
