public class main {

    public static void main(String args[]) throws NumberFormatException {

        System.out.println("I will catch a Number Format Exception");
        try {
            int ii = Integer.parseInt("ABC");
			throw new AssertionError("*** ERROR, Failed to catch Number Format Exception!");
        } catch (NumberFormatException ex) {
            System.out.println("Caught Number Format Exception");
        }
    }
}
