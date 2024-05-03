// hacked from https://www.pracspedia.com/SPCC/2passjava.html

public class main {

	public static void main(String args[]) throws Exception {
		TwoPassAssembler.initializeTables();
		System.out.println("====== PASS 1 ======\n");
		TwoPassAssembler.pass1();
		System.out.println("\n====== PASS 2 ======\n");
		TwoPassAssembler.pass2();
	}
	
}
