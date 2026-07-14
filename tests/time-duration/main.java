import java.time.Duration;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

public class main {

    public static void main(String[] args) {
        int errorCount = 0;

        // ===== Static factory methods =====

        Duration d2Days = Duration.ofDays(2);
        errorCount += Checkers.checker("ofDays(2) getSeconds", 172800L, d2Days.getSeconds());

        Duration d5Hours = Duration.ofHours(5);
        errorCount += Checkers.checker("ofHours(5) getSeconds", 18000L, d5Hours.getSeconds());

        Duration d90Min = Duration.ofMinutes(90);
        errorCount += Checkers.checker("ofMinutes(90) getSeconds", 5400L, d90Min.getSeconds());

        Duration d100Sec = Duration.ofSeconds(100);
        errorCount += Checkers.checker("ofSeconds(100) getSeconds", 100L, d100Sec.getSeconds());

        Duration d100_5Sec = Duration.ofSeconds(100, 500_000_000L);
        errorCount += Checkers.checker("ofSeconds(100, 500000000) getSeconds", 100L, d100_5Sec.getSeconds());
        errorCount += Checkers.checker("ofSeconds(100, 500000000) getNano", 500_000_000, d100_5Sec.getNano());

        // ofSeconds with negative nanoAdjustment that rolls over
        Duration dRollover = Duration.ofSeconds(5, -1_000_000_000L);
        errorCount += Checkers.checker("ofSeconds(5, -1e9) getSeconds", 4L, dRollover.getSeconds());
        errorCount += Checkers.checker("ofSeconds(5, -1e9) getNano", 0, dRollover.getNano());

        Duration d1500Millis = Duration.ofMillis(1500);
        errorCount += Checkers.checker("ofMillis(1500) getSeconds", 1L, d1500Millis.getSeconds());
        errorCount += Checkers.checker("ofMillis(1500) getNano", 500_000_000, d1500Millis.getNano());

        Duration dNegMillis = Duration.ofMillis(-1500);
        errorCount += Checkers.checker("ofMillis(-1500) getSeconds", -2L, dNegMillis.getSeconds());
        errorCount += Checkers.checker("ofMillis(-1500) getNano", 500_000_000, dNegMillis.getNano());

        Duration d1_5BNanos = Duration.ofNanos(1_500_000_000L);
        errorCount += Checkers.checker("ofNanos(1.5e9) getSeconds", 1L, d1_5BNanos.getSeconds());
        errorCount += Checkers.checker("ofNanos(1.5e9) getNano", 500_000_000, d1_5BNanos.getNano());

        errorCount += Checkers.checker("ZERO isZero", true, Duration.ZERO.isZero());
        errorCount += Checkers.checker("ZERO getSeconds", 0L, Duration.ZERO.getSeconds());

        // parse(CharSequence)
        Duration dParsed = Duration.parse("PT1H30M");
        errorCount += Checkers.checker("parse(PT1H30M) getSeconds", 5400L, dParsed.getSeconds());

        Duration dParsed2 = Duration.parse("PT-6H+3M");
        errorCount += Checkers.checker("parse(PT-6H+3M) getSeconds", -6 * 3600L + 3 * 60L, dParsed2.getSeconds());

        boolean parseThrew = false;
        try {
            Duration.parse("not-a-duration");
        } catch (DateTimeParseException e) {
            parseThrew = true;
        }
        errorCount += Checkers.checker("parse invalid string throws DateTimeParseException", true, parseThrew);

        // ===== isZero / isNegative / isPositive =====

        errorCount += Checkers.checker("ofSeconds(0) isZero", true, Duration.ofSeconds(0).isZero());
        errorCount += Checkers.checker("ofSeconds(10) isZero", false, Duration.ofSeconds(10).isZero());
        errorCount += Checkers.checker("ofSeconds(-10) isNegative", true, Duration.ofSeconds(-10).isNegative());
        errorCount += Checkers.checker("ofSeconds(10) isNegative", false, Duration.ofSeconds(10).isNegative());
        errorCount += Checkers.checker("ofSeconds(10) isPositive", true, Duration.ofSeconds(10).isPositive());
        errorCount += Checkers.checker("ofSeconds(-10) isPositive", false, Duration.ofSeconds(-10).isPositive());
        errorCount += Checkers.checker("ofSeconds(0) isPositive", false, Duration.ofSeconds(0).isPositive());

        // ===== plus family (Duration-based, no TemporalUnit) =====

        Duration base = Duration.ofSeconds(60);
        errorCount += Checkers.checker("plus(Duration) getSeconds", 75L, base.plus(Duration.ofSeconds(15)).getSeconds());
        errorCount += Checkers.checker("plusDays(1) getSeconds", 60L + 86400L, base.plusDays(1).getSeconds());
        errorCount += Checkers.checker("plusHours(1) getSeconds", 60L + 3600L, base.plusHours(1).getSeconds());
        errorCount += Checkers.checker("plusMinutes(1) getSeconds", 60L + 60L, base.plusMinutes(1).getSeconds());
        errorCount += Checkers.checker("plusSeconds(10) getSeconds", 70L, base.plusSeconds(10).getSeconds());
        errorCount += Checkers.checker("plusMillis(500) getNano", 500_000_000, base.plusMillis(500).getNano());
        errorCount += Checkers.checker("plusNanos(1000) getNano", 1000, base.plusNanos(1000).getNano());

        // ===== minus family =====

        Duration base2 = Duration.ofSeconds(120);
        errorCount += Checkers.checker("minus(Duration) getSeconds", 100L, base2.minus(Duration.ofSeconds(20)).getSeconds());
        errorCount += Checkers.checker("minusDays(0) minusHours(1) getSeconds", 120L - 3600L, base2.minusHours(1).getSeconds());
        errorCount += Checkers.checker("minusMinutes(1) getSeconds", 120L - 60L, base2.minusMinutes(1).getSeconds());
        errorCount += Checkers.checker("minusSeconds(20) getSeconds", 100L, base2.minusSeconds(20).getSeconds());

        Duration bigDur = Duration.ofDays(3);
        errorCount += Checkers.checker("minusDays(1) getSeconds", 2 * 86400L, bigDur.minusDays(1).getSeconds());

        Duration withMillis = Duration.ofMillis(1500);
        errorCount += Checkers.checker("minusMillis(500) getSeconds", 1L, withMillis.minusMillis(500).getSeconds());
        errorCount += Checkers.checker("minusMillis(500) getNano", 0, withMillis.minusMillis(500).getNano());

        Duration withNanos = Duration.ofNanos(2000);
        errorCount += Checkers.checker("minusNanos(1000) getNano", 1000, withNanos.minusNanos(1000).getNano());

        // ===== multipliedBy / dividedBy =====

        Duration d10 = Duration.ofSeconds(10);
        errorCount += Checkers.checker("multipliedBy(3) getSeconds", 30L, d10.multipliedBy(3).getSeconds());
        errorCount += Checkers.checker("dividedBy(2) getSeconds", 5L, d10.dividedBy(2).getSeconds());
        errorCount += Checkers.checker("dividedBy(Duration) ratio", 5L, Duration.ofSeconds(50).dividedBy(Duration.ofSeconds(10)));

        // ===== negated / abs =====

        errorCount += Checkers.checker("negated getSeconds", -10L, d10.negated().getSeconds());
        errorCount += Checkers.checker("negated().negated() equals original", true, d10.equals(d10.negated().negated()));
        errorCount += Checkers.checker("abs() on negative getSeconds", 10L, Duration.ofSeconds(-10).abs().getSeconds());
        errorCount += Checkers.checker("abs() on positive getSeconds", 10L, d10.abs().getSeconds());

        // ===== comparisons and equality =====

        errorCount += Checkers.checker("compareTo equal durations", 0, Duration.ofSeconds(10).compareTo(Duration.ofSeconds(10)));
        errorCount += Checkers.checker("compareTo smaller < larger", true, Duration.ofSeconds(5).compareTo(Duration.ofSeconds(10)) < 0);
        errorCount += Checkers.checker("compareTo larger > smaller", true, Duration.ofSeconds(10).compareTo(Duration.ofSeconds(5)) > 0);
        errorCount += Checkers.checker("equals same value", true, Duration.ofSeconds(42).equals(Duration.ofSeconds(42)));
        errorCount += Checkers.checker("equals different value", false, Duration.ofSeconds(42).equals(Duration.ofSeconds(43)));
        errorCount += Checkers.checker("hashCode consistent for equal durations", true,
                Duration.ofSeconds(42).hashCode() == Duration.ofSeconds(42).hashCode());

        // ===== unit conversions (whole-unit, no *Part) =====

        Duration dConv = Duration.ofSeconds(90061, 123_000_000L); // 1 day, 1 hour, 1 min, 1 sec, 123ms
        errorCount += Checkers.checker("toDays", 1L, dConv.toDays());
        errorCount += Checkers.checker("toHours", 25L, dConv.toHours());
        errorCount += Checkers.checker("toMinutes", 1501L, dConv.toMinutes());
        errorCount += Checkers.checker("toSeconds", 90061L, dConv.toSeconds());
        errorCount += Checkers.checker("toMillis", 90061123L, dConv.toMillis());
        errorCount += Checkers.checker("toNanos", 90061123000000L, dConv.toNanos());

        // ===== *Part conversions (Java 9+) =====

        errorCount += Checkers.checker("toDaysPart", 1L, dConv.toDaysPart());
        errorCount += Checkers.checker("toHoursPart", 1, dConv.toHoursPart());
        errorCount += Checkers.checker("toMinutesPart", 1, dConv.toMinutesPart());
        errorCount += Checkers.checker("toSecondsPart", 1, dConv.toSecondsPart());
        errorCount += Checkers.checker("toMillisPart", 123, dConv.toMillisPart());
        errorCount += Checkers.checker("toNanosPart", 123_000_000, dConv.toNanosPart());

        // ===== toString =====

        errorCount += Checkers.checker("toString PT1H30M", "PT1H30M", Duration.ofMinutes(90).toString());
        errorCount += Checkers.checker("toString ZERO", "PT0S", Duration.ZERO.toString());
        errorCount += Checkers.checker("toString negative", "PT-10S", Duration.ofSeconds(-10).toString());

        // ===== overflow handling =====

        boolean overflowThrew = false;
        try {
            Duration.ofSeconds(Long.MAX_VALUE).plusSeconds(1);
        } catch (ArithmeticException e) {
            overflowThrew = true;
        }
        errorCount += Checkers.checker("plusSeconds overflow throws ArithmeticException", true, overflowThrew);

        boolean divideByZeroThrew = false;
        try {
            d10.dividedBy(Duration.ZERO);
        } catch (ArithmeticException e) {
            divideByZeroThrew = true;
        }
        errorCount += Checkers.checker("dividedBy(ZERO) throws ArithmeticException", true, divideByZeroThrew);

        Checkers.theEnd(errorCount);
    }
}
