// Hacked from https://www.geeksforgeeks.org/user-defined-custom-exception-in-java/?ref=rp

// A Class that represents user-defined exception
class MyException extends Exception {
    public MyException(String wstring) {
        // Call constructor of parent Exception
        super(wstring);
    }
}

// A Class that uses above MyException
public class main {

    public static void main(String args[]) {
        System.out.println("Throw a user-defined exception");

        try {
            throw new MyException("Throwing a user-defined exception!");
        } catch (MyException ex) {
            System.out.println("Success - Caught a user-defined exception as expected");

            // Print the message from MyException object
            System.out.println(ex.getMessage());
            System.exit(0);
        } catch (Exception ex) {
            System.out.println("*** FAILED *** Caught an unexpected exception !!!");

            // Print the message from MyException object
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        System.out.println("*** FAILED *** Expected to catch a MyException !!!");
        System.out.println("************** No exception was thrown !!!");
        System.exit(1);
    }

}
