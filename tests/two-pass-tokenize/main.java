import java.util.LinkedList;
import java.util.StringTokenizer;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;
        
        String strInArray[] = { "DS", "EQU", "START", "LTORG", "END" };
        String strOutArray[] = tokenizeOperands(strInArray);
		System.out.printf("tokenizeOperands :: s.length: %d\n\t", strOutArray.length);
	    for (int ix = 0; ix < strOutArray.length; ix++)
	        System.out.printf("[%s] ", strOutArray[ix]);
	    System.out.println();
	    
	    errorCount += Checkers.checker("compare lengths", strInArray.length, strOutArray.length);
	    for (int ix = 0; ix < strInArray.length; ix++)
	        errorCount += Checkers.checker("compare element", strInArray[ix], strOutArray[ix]);
	        
	    Checkers.theEnd(errorCount);

    }

	static String[] tokenizeOperands(String[] s) {
		LinkedList<String> llStr = new LinkedList<>();
		for(int j=0 ; j<s.length-1 ; j++) {
			llStr.add(s[j]);
		}
		System.out.println("before StringTokenizer st = ...");
		StringTokenizer st = new StringTokenizer(s[s.length-1], " ,", false);
		System.out.println("after StringTokenizer st = ...");
		if (st == null)
		    throw new AssertionError("Output of StringTokenizer st = ... is null");
		while(st.hasMoreTokens()) {
			llStr.add(st.nextToken());
		}
		s = llStr.toArray(new String[0]);
		return s;
	}

}

