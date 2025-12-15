public class main {
	public static void main(String[] args){
        System.out.println("I will catch a NullPointerException");
        try {
            throw new NullPointerException("Can you see my null pointer exception?");
        } catch (NullPointerException ex) {
            System.out.println("Caught NullPointerException");
            ex.printStackTrace();
            Checkers.theEnd(0);
        }
        throw new AssertionError("failed to catch Null Pointer Exception");
    }
}
