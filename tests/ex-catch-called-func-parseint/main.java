public class main {
    private static void fn() {
        int ii = Integer.parseInt("rubbish");
        throw new AssertionError("Missed NumberFormatException");
    }

    public static void main(String[] args) {
        System.out.println("Catch an exception thrown in a called function");
        try {
            fn();
        } catch (NumberFormatException ex) {
            System.out.println("Caught NumberFormatException");
        }
   }
}
