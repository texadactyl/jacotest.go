// Hacked from https://www.geeksforgeeks.org/user-defined-custom-exception-in-java/?ref=rp

// A Class that represents user-defined exception
class MyException extends Exception {
    public MyException(String wstring)
    {
        // Call constructor of parent Exception
        super(wstring);
    }
}
 
// A Class that uses above MyException
public class main {

    public static void main(String args[])
    {
        try {
            // Throw an object of user defined exception
            throw new MyException("This is a user-defined exception!");
        }
        catch (MyException ex) {
            System.out.println("Caught a user-defined exception");
 
            // Print the message from MyException object
            System.out.println(ex.getMessage());
        }
        catch (Exception ex) {
            System.out.println("*** Caught an unexpected exception !!!");
 
            // Print the message from MyException object
            System.out.println(ex.getMessage());
        }
    }
}
