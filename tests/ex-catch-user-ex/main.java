// Hacked from https://www.geeksforgeeks.org/user-defined-custom-exception-in-java/?ref=rp

// A Class that represents user-defined exception
class MyException extends Exception {
    String msg = "rubbish!";
	private static final long serialVersionUID = 42l;
    public MyException(String arg) {
        // Call constructor of parent Exception
        msg = arg;
    }
    public String getMsg() {
        return msg;
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
            System.out.println(ex.getMsg());
            Checkers.theEnd(0);
        }
        
        System.out.println("*** ERROR *** Expected to catch a MyException !!!");
        System.out.println("************* No exception was thrown !!!");
        throw new AssertionError("failed to throw MyException");
    }

}
