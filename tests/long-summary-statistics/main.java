import java.util.LongSummaryStatistics;

public class main {

    static int errorCount = 0;

    public static void main(String[] args) {

        // ---- 1. Build a LongSummaryStatistics manually and feed it with accept(long) ----
        long[] data = {11L, 22L, 33L, 44L, 55L};
        LongSummaryStatistics stats1 = new LongSummaryStatistics();
        for (long value : data) {
            stats1.accept(value);
        }

        printStats("Manually-accumulated stats1", stats1, new LongSummaryStatistics(5L, 11L, 55L, 165L));

        // ---- 2. A second accumulator, also fed with accept(long) ----
        LongSummaryStatistics stats2 = new LongSummaryStatistics();
        stats2.accept(5L);
        stats2.accept(15L);
        stats2.accept(25L);

        printStats("Manually-accumulated stats2", stats2, new LongSummaryStatistics(3L, 5L, 25L, 45L));

        // ---- 3. Combine two LongSummaryStatistics instances with combine(LongSummaryStatistics) ----
        LongSummaryStatistics combined = new LongSummaryStatistics();
        combined.combine(stats1);
        combined.combine(stats2);

        printStats("Combined stats (stats1 + stats2)", combined, new LongSummaryStatistics(8L, 5L, 55L, 210L));

        // ---- 4. toString() ----
        errorCount += Checkers.checker("toString() of stats1", "LongSummaryStatistics{count=5, sum=165, min=11, average=33.000000, max=55}", stats1.toString());
        
        // ---- Ciao ----
        Checkers.theEnd(errorCount);
    }

    private static void printStats(String label, LongSummaryStatistics observed, LongSummaryStatistics expected) {
        System.out.printf("---- %s ----%n", label);
        errorCount += Checkers.checker("getCount()", expected.getCount(), observed.getCount());
        errorCount += Checkers.checker("getMin()", expected.getMin(), observed.getMin());
        errorCount += Checkers.checker("getMax()", expected.getMax(), observed.getMax());
        errorCount += Checkers.checker("getSum()", expected.getSum(), observed.getSum());
        errorCount += Checkers.withinTolerance("getAverage()", expected.getAverage(), observed.getAverage());
    }
}
