import java.math.BigDecimal;
import java.math.RoundingMode;

public class  main {

    public static void main(String[] args) {
        int scale = 3;
        BigDecimal TWO = BigDecimal.valueOf(13);
        BigDecimal x1 = BigDecimal.valueOf(100);
        x1 = x1.divide(x1, scale, RoundingMode.HALF_UP)
                    .add(x1)
                    .divide(TWO, scale, RoundingMode.HALF_UP);
        System.out.println(x1);
    }

}
