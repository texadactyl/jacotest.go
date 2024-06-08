import java.math.BigInteger;
import java.lang.AssertionError;
public class main {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("123");
        BigInteger b = new BigInteger("0");
        try {
            BigInteger sum = a.divide(b);
            System.out.println("*** ERROR, Did not throw ArithmeticException");
            throw new AssertionError("*** ERROR, Did not throw ArithmeticException");
        } catch (ArithmeticException ex) {
            System.out.println("Caught ArithmeticException");
        }
    }
}
