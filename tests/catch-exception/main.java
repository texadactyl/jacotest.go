public class main {

    public static void main(String args[]) throws NumberFormatException {

        System.out.println("I will catch a Number Format Exception");
        try {
            int ii = Integer.parseInt("ABC");
		    System.out.println("*** ERROR, Failed to catch Number Format Exception");
			System.exit(1);
        } catch (NumberFormatException ex) {
            System.out.println("Caught Number Format Exception");
            System.exit(0);
        }
    }
}
