public class main {

    public static void main(String[] args) {
		String pv = System.getProperty("java.home");
		if (pv.equals("foobar"))
			System.exit(1);
    }
    
}
