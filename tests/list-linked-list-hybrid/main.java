// Boiled down from Jacotest case two-pass.

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class main {

    // TODO: needs qualitative tests

	static List<Tuple> mot;

	public static void main(String args[]) throws Exception {
		mot = new LinkedList<>();
		String input = "BR      15h      2      RR";
		StringTokenizer st = new StringTokenizer(input, " ", false);
		// static List<Tuple> mot; <--- causes an unexpected INVOKEINTERFACE on the next Java source line (mot.add).
		// G-function: interface=java/util/List, meth=java/util/List(Ljava/lang/Object;)Z
		// java.lang.IllegalArgumentException: FQN: main.main([Ljava/lang/String;)V, linkedlistAddLastRetBool:
		// Expected 2 arguments, got 1 in thread: main, G-function: java/util/List.add(Ljava/lang/Object;)Z
		// There is no anomaly if: static LinkedList<Tuple> mot;
		mot.add(new Tuple(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken()));
		System.out.println(mot);
		Checkers.theEnd(0);
	}
	
}

class Tuple {
	String mnemonic, bin_opcode, type;
	int length;

	Tuple(String s1, String s2, String s3, String s4) {
		mnemonic = s1;
		bin_opcode = s2;
		length = Integer.parseInt(s3);
		type = s4;
	}
}

