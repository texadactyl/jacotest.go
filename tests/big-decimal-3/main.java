import java.math.BigDecimal;
import java.math.RoundingMode;

public class  main {

    public static void main(String[] args) {
        int errorCount = 0;
        int scale = 3;
        BigDecimal thirteen = BigDecimal.valueOf(13);
        BigDecimal startingValue = BigDecimal.valueOf(100);
        BigDecimal answer_1 = BigDecimal.valueOf(7.769);
        BigDecimal answer_2 = BigDecimal.valueOf(-7.615);
        BigDecimal answer_3 = BigDecimal.valueOf(-1287.000);
        BigDecimal answer_4 = BigDecimal.valueOf(753.885000);
        BigDecimal answer;
        
        answer = startingValue.divide(startingValue, scale, RoundingMode.HALF_UP)
                    .add(startingValue)
                    .divide(thirteen, scale, RoundingMode.HALF_UP);
        errorCount += Checkers.withinTolerance("divide-add-divide", answer_1, answer, 0.00001);
        
        
        answer = startingValue.divide(startingValue, scale, RoundingMode.HALF_UP)
                    .subtract(startingValue)
                    .divide(thirteen, scale, RoundingMode.HALF_UP);
        errorCount += Checkers.withinTolerance("divide-subtract-divide", answer_2, answer, 0.00001);
        
        answer = startingValue.divide(startingValue, scale, RoundingMode.HALF_UP)
                    .subtract(startingValue)
                    .multiply(thirteen);
        errorCount += Checkers.withinTolerance("divide-subtract-multiply", answer_3, answer, 0.00001);
        
        answer = startingValue.divide(startingValue, scale, RoundingMode.HALF_UP)
                    .subtract(startingValue)
                    .multiply(answer_2);
        errorCount += Checkers.withinTolerance("divide-subtract-multiply", answer_4, answer, 0.00001);
    }

}
