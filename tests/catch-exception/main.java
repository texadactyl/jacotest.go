public class main {

    public static void main(String args[]) throws NumberFormatException {

        System.out.println("I will catch a NumberFormatException");
        try {
            int ii = Integer.parseInt("ABC");
        } catch (NumberFormatException ex) {
            System.out.println("Caught NumberFormatException");
            System.exit(0);
        }
        System.out.println("*** ERROR, failed to catch NumberFormatException");
        System.exit(1);
    }
}
