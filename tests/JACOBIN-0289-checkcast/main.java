public class main {

    public static void main(String[] args) {
    	System.out.println("CHECKCAST exercise .....");
		String pv = System.getProperty("java.home");
		if (pv.equals("foobar"))
			throw new AssertionError("checkcast error?");
    }
    
}
