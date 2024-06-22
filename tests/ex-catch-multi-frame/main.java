import java.io.IOException;
public class main {

    private static void f_idiv_error() {
        int ii = 1;
        int jj = 0;
        int kk = ii / jj;
        throw new AssertionError("f_idiv_error: *** ERROR, Failed to throw an ArithmeticException");
    }
    
    private static void f_io_exception() throws IOException {
        throw new IOException("f_io_exception: *** ERROR, throw new IOException");
    }
    
    private static void f2() {
        f_idiv_error();
        throw new AssertionError("f2/f_idiv_error: *** ERROR, this should not appear");
    }
    
    private static void f1() {
        // Try-catch directly.
        try {
            f_idiv_error();
            throw new AssertionError("f1/f_idiv_error: *** ERROR, Failed to catch an ArithmeticException");
        } catch (ArithmeticException ex) {
            System.out.println("f1/f_idiv_error: Caught an ArithmeticException");
        }
        // Try-catch indirectly.
        try {
            f2();
            throw new AssertionError("f1/f2: *** ERROR, Failed to catch an ArithmeticException");
        } catch (ArithmeticException ex) {
            System.out.println("f1/f2: Caught an ArithmeticException");
        }
        // Try-catch another exception.
        try {
            f_io_exception();
            throw new AssertionError("f1/f_io_exception: *** ERROR, Failed to catch an IOException");
        } catch (IOException ex) {
            System.out.println("f1/f_io_exception: Caught an IOException");
        }
        // Let main catch.
        f2();
        
    }
    
    public static void main(String[] args) {
        try {
            f1();
            throw new AssertionError("main/f1: *** ERROR, Failed to catch an ArithmeticException");
        } catch (ArithmeticException ex) {
            System.out.println("main/f1: Caught an ArithmeticException");
        }
    }
}

