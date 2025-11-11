import java.math.BigDecimal;
import java.math.RoundingMode;

public class  main {

    public static void main(String[] args) {
        int scale = 3;
        BigDecimal thirteen = BigDecimal.valueOf(13);
        BigDecimal startingValue = BigDecimal.valueOf(100);
        BigDecimal x7p769 = BigDecimal.valueOf(7.769);
        BigDecimal answer = startingValue.divide(startingValue, scale, RoundingMode.HALF_UP)
                    .add(startingValue)
                    .divide(thirteen, scale, RoundingMode.HALF_UP);
        System.out.println(answer);
        assert(answer.equals(x7p769));
        System.out.println("ok answer.equals(x7p769)!");       

        RoundingMode rm1 = RoundingMode.valueOf("DOWN");
        RoundingMode rm2 = RoundingMode.valueOf(1);
        RoundingMode rm3 = RoundingMode.valueOf("UP");
        
        assert(rm2.equals(rm1));
        assert(rm2.name().equals(rm1.name()));
        assert(!rm3.equals(rm1));
        assert(!rm3.name().equals(rm1.name()));
        System.out.println("ok 3 RoundingMode names and comparisons made sense!");       

        int irm1 = rm1.ordinal();
        int irm2 = rm2.ordinal();        
        assert(irm1 == irm2);
        System.out.println("ok ordinal conversions work!");       
        
        System.out.println("Here comes the list of RoundingMode ordinals and names:");
        RoundingMode[] rmArray = RoundingMode.values();
        int ord;
        for (int ix = 0; ix < rmArray.length; ix++) {
            ord = rmArray[ix].ordinal();
            System.out.printf("\t[%d] %s\n", ix, RoundingMode.valueOf(ord).name());
            assert(ix == ord);
        }
        
        Checkers.theEnd(0);
    }

}
