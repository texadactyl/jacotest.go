import java.math.BigDecimal;
import java.math.RoundingMode;

public class main {

    public static void main(String[] args) {
    
        int errorCount = 0;
        boolean willFail;
        String label;

        // ==============================                
        // Tolerance is a double
        // ==============================       
                 
        errorCount += Checkers.withinTolerance("Case 1 Exact match",
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                0.01);

        errorCount += Checkers.withinTolerance("Case 2 Within 1 percent",
                new BigDecimal("100.00"),
                new BigDecimal("100.50"),
                0.01);

        label = "Case 3 *EXPECTED TO FAIL*; above 1 percent";
        errorCount += Checkers.checker(label, 1, Checkers.withinTolerance(label,
                                                new BigDecimal("100.00"),
                                                new BigDecimal("101.50"),
                                                0.01)
                                      );

        errorCount += Checkers.withinTolerance("Case 4 Boundary 1 percent",
                new BigDecimal("100.00"),
                new BigDecimal("101.00"),
                0.01);

        errorCount += Checkers.withinTolerance("Case 5 Small expected abs tolerance",
                new BigDecimal("0.00001"),
                new BigDecimal("0.00002"),
                0.001);

        label = "Case 6 *EXPECTED TO FAIL*; above 1 percent";
        errorCount += Checkers.checker(label, 1, Checkers.withinTolerance(label,
                                                    new BigDecimal("0.00001"),
                                                    new BigDecimal("0.01"),
                                                    0.001)
                                      );

        // ==============================                
        // Tolerance is also a BigDecimal
        // ==============================                

        errorCount += Checkers.withinTolerance("Case 7 Exact match",
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                0.01);

        errorCount += Checkers.withinTolerance("Case 8 Within 1 percent",
                new BigDecimal("100.00"),
                new BigDecimal("100.50"),
                0.01);

        label = "Case 9 *EXPECTED TO FAIL*; above 1 percent";
        errorCount += Checkers.checker(label, 1, Checkers.withinTolerance(label,
                                                new BigDecimal("100.00"),
                                                new BigDecimal("101.50"),
                                                new BigDecimal("0.01") )
                                      );

        errorCount += Checkers.withinTolerance("Case 10 Boundary 1 percent",
                new BigDecimal("100.00"),
                new BigDecimal("101.00"),
                0.01);

        errorCount += Checkers.withinTolerance("Case 11 Small expected abs tolerance",
                new BigDecimal("0.00001"),
                new BigDecimal("0.00002"),
                0.001);

        label = "Case 12 *EXPECTED TO FAIL*; above 1 percent";
        errorCount += Checkers.checker(label, 1, Checkers.withinTolerance(label,
                                                    new BigDecimal("0.00001"),
                                                    new BigDecimal("0.01"),
                                                    new BigDecimal("0.001") )
                                      );


        Checkers.theEnd(errorCount);
    }
}

