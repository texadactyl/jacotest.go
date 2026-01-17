import java.math.BigDecimal;
import java.math.RoundingMode;
public class leibniz_bd {
    public static void main(String[] args) {
        long rounds = 10_000_000L;

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal minus1 = new BigDecimal(-1.0);
        BigDecimal flip = new BigDecimal(-1.0);
        BigDecimal four = new BigDecimal(4.0);
        BigDecimal divisor;
        
        for (long i = 1; i <= rounds; i++) {
            flip = flip.multiply(minus1);
            divisor = new BigDecimal(i + i - 1);
            sum = sum.add(flip.divide(divisor, 16, RoundingMode.HALF_UP));
        }

        System.out.println("Expected: 3.14159265358979323846");
        System.out.print("Observed: ");
        System.out.println(sum.multiply(four));
    }
}
