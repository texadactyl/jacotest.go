import java.math.BigDecimal;
import java.math.RoundingMode;

public class main {
    public static void main(String[] args) {

        final long t1 = System.currentTimeMillis();
        long rounds = 1_000_000L;

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

        final long t2 = System.currentTimeMillis();
        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.printf("Rounds: %d\n", rounds);
        System.out.printf("Elapsed time (seconds): %f.3\n", elapsedSeconds);
        System.out.println("Expected: 3.14159265358979323846");
        System.out.print("Observed: ");
        System.out.println(sum.multiply(four));
    }
}
