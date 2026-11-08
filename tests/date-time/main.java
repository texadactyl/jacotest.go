import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Demonstrates the core API surface of java.time.LocalDateTime and
 * java.time.ZonedDateTime: creation, field access, arithmetic,
 * comparison, formatting/parsing, time zone conversion, and
 * duration/period calculations.
 *
 * Every result is verified with Checkers.checker(). The expected values
 * were taken from a HotSpot run (console.log). Values that depend on the
 * wall clock (now()) are verified by bracketing or by an independent
 * computation instead of a literal.
 */
public final class main {

    public static void main(String[] args) {
        int errorCount = 0;

        section("1. Creating LocalDateTime instances");
        errorCount += demoLocalDateTimeCreation();

        section("2. LocalDateTime field access");
        errorCount += demoLocalDateTimeFields();

        section("3. LocalDateTime arithmetic and adjusters");
        errorCount += demoLocalDateTimeArithmetic();

        section("4. LocalDateTime comparison");
        errorCount += demoLocalDateTimeComparison();

        section("5. Formatting and parsing LocalDateTime");
        errorCount += demoLocalDateTimeFormatting();

        section("6. Creating ZonedDateTime instances");
        errorCount += demoZonedDateTimeCreation();

        section("7. ZonedDateTime field access and zone info");
        errorCount += demoZonedDateTimeFields();

        section("8. Converting between LocalDateTime and ZonedDateTime");
        errorCount += demoConversion();

        section("9. withZoneSameInstant vs withZoneSameLocal");
        errorCount += demoZoneShifting();

        section("10. Daylight Saving Time transition");
        errorCount += demoDstTransition();

        section("11. Duration and Period calculations");
        errorCount += demoDurationAndPeriod();

        section("12. ChronoUnit.between for both types");
        errorCount += demoChronoUnitBetween();

        Checkers.theEnd(errorCount);
    }

    private static void section(String title) {
        System.out.println();
        System.out.printf("=== %s ===%n", title);
    }

    private static boolean isBetween(Instant lowInstant, Instant valueInstant, Instant highInstant) {
        return !valueInstant.isBefore(lowInstant) && !valueInstant.isAfter(highInstant);
    }

    // ---------------------------------------------------------------
    // LocalDateTime
    // ---------------------------------------------------------------

    private static int demoLocalDateTimeCreation() {
        int errorCount = 0;

        // now() cannot be compared with a literal; bracket it between two other readings.
        LocalDateTime beforeNow = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterNow = LocalDateTime.now();
        System.out.printf("now()                : %s%n", now);
        boolean nowInRange = !now.isBefore(beforeNow) && !now.isAfter(afterNow);
        errorCount += Checkers.checker("now() bracketed by two other readings", true, nowInRange);

        LocalDateTime explicit = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 15);
        errorCount += Checkers.checker("of(2026,JUNE,24,...)",
                "2026-06-24T14:30:15", explicit.toString());

        LocalDateTime fromDateAndTime =
                LocalDateTime.of(LocalDate.of(2030, 1, 1), LocalTime.of(9, 0));
        errorCount += Checkers.checker("of(LocalDate,LocalTime)",
                "2030-01-01T09:00", fromDateAndTime.toString());

        LocalDateTime parsed = LocalDateTime.parse("2026-12-25T08:15:30");
        errorCount += Checkers.checker("parse(ISO string)",
                "2026-12-25T08:15:30", parsed.toString());

        LocalDateTime fromEpoch =
                LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC);
        errorCount += Checkers.checker("ofEpochSecond(0,UTC)",
                "1970-01-01T00:00", fromEpoch.toString());

        return errorCount;
    }

    private static int demoLocalDateTimeFields() {
        int errorCount = 0;
        LocalDateTime dt = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 15, 250_000_000);

        errorCount += Checkers.checker("dt", "2026-06-24T14:30:15.250", dt.toString());
        errorCount += Checkers.checker("getYear()", 2026, dt.getYear());
        errorCount += Checkers.checker("getMonth()", "JUNE", dt.getMonth().toString());
        errorCount += Checkers.checker("getMonthValue()", 6, dt.getMonthValue());
        errorCount += Checkers.checker("getDayOfMonth()", 24, dt.getDayOfMonth());
        errorCount += Checkers.checker("getDayOfWeek()", "WEDNESDAY", dt.getDayOfWeek().toString());
        errorCount += Checkers.checker("getDayOfYear()", 175, dt.getDayOfYear());
        errorCount += Checkers.checker("getHour()", 14, dt.getHour());
        errorCount += Checkers.checker("getMinute()", 30, dt.getMinute());
        errorCount += Checkers.checker("getSecond()", 15, dt.getSecond());
        errorCount += Checkers.checker("getNano()", 250000000, dt.getNano());
        errorCount += Checkers.checker("toLocalDate()", "2026-06-24", dt.toLocalDate().toString());
        errorCount += Checkers.checker("toLocalTime()", "14:30:15.250", dt.toLocalTime().toString());
        errorCount += Checkers.checker("isLeapYear()", false, dt.toLocalDate().isLeapYear());

        return errorCount;
    }

    private static int demoLocalDateTimeArithmetic() {
        int errorCount = 0;
        LocalDateTime base = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 0);

        errorCount += Checkers.checker("base", "2026-06-24T14:30", base.toString());
        errorCount += Checkers.checker("plusDays(10)", "2026-07-04T14:30", base.plusDays(10).toString());
        errorCount += Checkers.checker("minusWeeks(2)", "2026-06-10T14:30", base.minusWeeks(2).toString());
        errorCount += Checkers.checker("plusMonths(7)", "2027-01-24T14:30", base.plusMonths(7).toString());
        errorCount += Checkers.checker("plusHours(36)", "2026-06-26T02:30", base.plusHours(36).toString());
        errorCount += Checkers.checker("minusYears(1)", "2025-06-24T14:30", base.minusYears(1).toString());
        errorCount += Checkers.checker("withYear(2030)", "2030-06-24T14:30", base.withYear(2030).toString());
        errorCount += Checkers.checker("withHour(0)", "2026-06-24T00:30", base.withHour(0).toString());
        errorCount += Checkers.checker("truncatedTo(HOURS)", "2026-06-24T14:00",
                base.truncatedTo(ChronoUnit.HOURS).toString());

        LocalDateTime firstOfNextMonth =
                base.with(TemporalAdjusters.firstDayOfNextMonth());
        errorCount += Checkers.checker("firstDayOfNextMonth", "2026-07-01T14:30",
                firstOfNextMonth.toString());

        LocalDateTime nextMonday =
                base.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        errorCount += Checkers.checker("next(MONDAY)", "2026-06-29T14:30", nextMonday.toString());

        return errorCount;
    }

    private static int demoLocalDateTimeComparison() {
        int errorCount = 0;
        LocalDateTime earlyTime = LocalDateTime.of(2026, 6, 24, 9, 0);
        LocalDateTime lateTime = LocalDateTime.of(2026, 6, 24, 17, 0);

        errorCount += Checkers.checker("earlyTime", "2026-06-24T09:00", earlyTime.toString());
        errorCount += Checkers.checker("lateTime", "2026-06-24T17:00", lateTime.toString());
        errorCount += Checkers.checker("earlyTime.isBefore(lateTime)", true, earlyTime.isBefore(lateTime));
        errorCount += Checkers.checker("earlyTime.isAfter(lateTime)", false, earlyTime.isAfter(lateTime));
        errorCount += Checkers.checker("earlyTime.isEqual(earlyTime)", true, earlyTime.isEqual(earlyTime));
        errorCount += Checkers.checker("earlyTime.compareTo(lateTime)", -1, earlyTime.compareTo(lateTime));

        return errorCount;
    }

    private static int demoLocalDateTimeFormatting() {
        int errorCount = 0;
        LocalDateTime dt = LocalDateTime.of(2026, 6, 24, 14, 30, 15);

        errorCount += Checkers.checker("ISO_LOCAL_DATE_TIME", "2026-06-24T14:30:15",
                dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        DateTimeFormatter custom = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");
        errorCount += Checkers.checker("custom pattern", "Wed, 24 Jun 2026 14:30:15",
                dt.format(custom));

        DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        errorCount += Checkers.checker("date-only pattern", "2026/06/24", dt.format(dateOnly));

        LocalDateTime reparsed = LocalDateTime.parse("24-06-2026 14:30:15",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        errorCount += Checkers.checker("parsed with pattern", "2026-06-24T14:30:15",
                reparsed.toString());

        return errorCount;
    }

    // ---------------------------------------------------------------
    // ZonedDateTime
    // ---------------------------------------------------------------

    private static int demoZonedDateTimeCreation() {
        int errorCount = 0;

        // now() cannot be compared with a literal; bracket the instant between two readings.
        Instant beforeInstant = Instant.now();
        ZonedDateTime nowSystem = ZonedDateTime.now();
        ZonedDateTime nowTokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        Instant afterInstant = Instant.now();

        System.out.printf("now() [system zone]      : %s%n", nowSystem);
        errorCount += Checkers.checker("now() [system zone] instant bracketed", true,
                isBetween(beforeInstant, nowSystem.toInstant(), afterInstant));
        errorCount += Checkers.checker("now() [system zone] zone",
                ZoneId.systemDefault().toString(), nowSystem.getZone().toString());

        System.out.printf("now(Asia/Tokyo)          : %s%n", nowTokyo);
        errorCount += Checkers.checker("now(Asia/Tokyo) instant bracketed", true,
                isBetween(beforeInstant, nowTokyo.toInstant(), afterInstant));
        errorCount += Checkers.checker("now(Asia/Tokyo) zone", "Asia/Tokyo", nowTokyo.getZone().toString());
        errorCount += Checkers.checker("now(Asia/Tokyo) offset", "+09:00", nowTokyo.getOffset().toString());

        ZonedDateTime explicit = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("America/Chicago"));
        errorCount += Checkers.checker("of(...,America/Chicago)",
                "2026-06-24T14:30-05:00[America/Chicago]", explicit.toString());

        ZonedDateTime fromLocal = LocalDateTime.of(2026, 6, 24, 14, 30)
                .atZone(ZoneId.of("Europe/London"));
        errorCount += Checkers.checker("LocalDateTime.atZone(...)",
                "2026-06-24T14:30+01:00[Europe/London]", fromLocal.toString());

        ZonedDateTime parsed = ZonedDateTime.parse("2026-06-24T14:30:00-05:00[America/Chicago]");
        errorCount += Checkers.checker("parse(zoned ISO string)",
                "2026-06-24T14:30-05:00[America/Chicago]", parsed.toString());

        return errorCount;
    }

    private static int demoZonedDateTimeFields() {
        int errorCount = 0;
        ZonedDateTime zdt = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("Australia/Sydney"));

        errorCount += Checkers.checker("zdt", "2026-06-24T14:30+10:00[Australia/Sydney]", zdt.toString());
        errorCount += Checkers.checker("getZone()", "Australia/Sydney", zdt.getZone().toString());
        errorCount += Checkers.checker("getOffset()", "+10:00", zdt.getOffset().toString());
        errorCount += Checkers.checker("getDayOfWeek()", "WEDNESDAY", zdt.getDayOfWeek().toString());
        errorCount += Checkers.checker("toInstant()", "2026-06-24T04:30:00Z", zdt.toInstant().toString());
        errorCount += Checkers.checker("toEpochSecond()", 1782275400L, zdt.toEpochSecond());

        // The zone count and the sampled names depend on the tzdata version of the
        // JVM; the expected values below come from the HotSpot run in console.log.
        Set<String> zoneIdSet = new TreeSet<>(ZoneId.getAvailableZoneIds());
        errorCount += Checkers.checker("zoneIdSet size", 604, zoneIdSet.size());

        int[] sampleIndexes = {100, 200, 300, 400, 500, 600};
        String[] expectedZoneNames = {
            "America/Creston",
            "America/Sao_Paulo",
            "Asia/Phnom_Penh",
            "Etc/GMT+5",
            "Indian/Cocos",
            "Universal"
        };

        int zoneIx = 0;
        int sampleCount = 0;
        for (String zoneName : zoneIdSet) {
            for (int sampleIx = 0; sampleIx < sampleIndexes.length; sampleIx++) {
                if (zoneIx == sampleIndexes[sampleIx]) {
                    errorCount += Checkers.checker(String.format("zone[%d]", zoneIx),
                            expectedZoneNames[sampleIx], zoneName);
                    sampleCount++;
                }
            }
            zoneIx++;
        }
        if (sampleCount != sampleIndexes.length) {
            errorCount += Checkers.fail(String.format(
                    "zone list too short: checked %d of %d sample indexes (size %d)",
                    sampleCount, sampleIndexes.length, zoneIx));
        }
        
        return errorCount;
    }

    private static int demoConversion() {
        int errorCount = 0;
        LocalDateTime local = LocalDateTime.of(2026, 6, 24, 14, 30);
        errorCount += Checkers.checker("local", "2026-06-24T14:30", local.toString());

        ZonedDateTime asNewYork = local.atZone(ZoneId.of("America/New_York"));
        errorCount += Checkers.checker("local.atZone(America/New_York)",
                "2026-06-24T14:30-04:00[America/New_York]", asNewYork.toString());

        LocalDateTime backToLocal = asNewYork.toLocalDateTime();
        errorCount += Checkers.checker("asNewYork.toLocalDateTime()", "2026-06-24T14:30",
                backToLocal.toString());
        errorCount += Checkers.checker("local.equals(backToLocal)", true, local.equals(backToLocal));

        ZonedDateTime withOffset =
                ZonedDateTime.of(local, ZoneOffset.ofHours(-3));
        errorCount += Checkers.checker("of(local, ZoneOffset.-3)", "2026-06-24T14:30-03:00",
                withOffset.toString());

        return errorCount;
    }

    private static int demoZoneShifting() {
        int errorCount = 0;
        ZonedDateTime chicago = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("America/Chicago"));
        errorCount += Checkers.checker("chicago", "2026-06-24T14:30-05:00[America/Chicago]",
                chicago.toString());

        ZonedDateTime sameInstantInTokyo =
                chicago.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        errorCount += Checkers.checker("withZoneSameInstant(Tokyo)",
                "2026-06-25T04:30+09:00[Asia/Tokyo]", sameInstantInTokyo.toString());
        errorCount += Checkers.checker("instants equal?", true,
                chicago.toInstant().equals(sameInstantInTokyo.toInstant()));

        ZonedDateTime sameLocalInTokyo =
                chicago.withZoneSameLocal(ZoneId.of("Asia/Tokyo"));
        errorCount += Checkers.checker("withZoneSameLocal(Tokyo)",
                "2026-06-24T14:30+09:00[Asia/Tokyo]", sameLocalInTokyo.toString());
        errorCount += Checkers.checker("wall-clock times equal?", true,
                chicago.toLocalDateTime().equals(sameLocalInTokyo.toLocalDateTime()));

        return errorCount;
    }

    private static int demoDstTransition() {
        int errorCount = 0;

        // US Eastern time springs forward on the second Sunday of March.
        // At 2:00 AM local time the clocks jump to 3:00 AM, so 2:30 AM
        // does not exist on that date.
        ZoneId newYork = ZoneId.of("America/New_York");
        LocalDateTime nonExistent = LocalDateTime.of(2026, 3, 8, 2, 30);

        ZonedDateTime resolved = nonExistent.atZone(newYork);
        errorCount += Checkers.checker("nominal local time", "2026-03-08T02:30", nonExistent.toString());
        errorCount += Checkers.checker("resolved ZonedDateTime",
                "2026-03-08T03:30-04:00[America/New_York]", resolved.toString());

        // The offset in effect today depends on the date the test is run, so the
        // expected value is derived from ZonedDateTime.now(newYork) instead of a literal.
        String observedOffset = newYork.getRules().getOffset(LocalDateTime.now()).toString();
        String expectedOffset = ZonedDateTime.now(newYork).getOffset().toString();
        System.out.printf("zone rules offset(now) : %s%n", observedOffset);
        errorCount += Checkers.checker("zone rules offset(now)", expectedOffset, observedOffset);

        ZonedDateTime beforeFallBack = ZonedDateTime.of(
                2026, 11, 1, 0, 30, 0, 0, newYork);
        ZonedDateTime afterAddingTwoHours = beforeFallBack.plusHours(2);
        errorCount += Checkers.checker("before fall-back",
                "2026-11-01T00:30-04:00[America/New_York]", beforeFallBack.toString());
        errorCount += Checkers.checker("plusHours(2)",
                "2026-11-01T01:30-05:00[America/New_York]", afterAddingTwoHours.toString());

        return errorCount;
    }

    // ---------------------------------------------------------------
    // Duration / Period
    // ---------------------------------------------------------------

    private static int demoDurationAndPeriod() {
        int errorCount = 0;
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 6, 24, 17, 45);

        Duration duration = Duration.between(start, end);
        errorCount += Checkers.checker("start", "2026-01-01T09:00", start.toString());
        errorCount += Checkers.checker("end", "2026-06-24T17:45", end.toString());
        errorCount += Checkers.checker("Duration.between", "PT4184H45M", duration.toString());
        errorCount += Checkers.checker("duration.toDays()", 174L, duration.toDays());
        errorCount += Checkers.checker("duration.toHours()", 4184L, duration.toHours());

        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        errorCount += Checkers.checker("Period.between years", 0, period.getYears());
        errorCount += Checkers.checker("Period.between months", 5, period.getMonths());
        errorCount += Checkers.checker("Period.between days", 23, period.getDays());

        ZonedDateTime zStart = start.atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime zEnd = end.atZone(ZoneId.of("Asia/Tokyo"));
        Duration zonedDuration = Duration.between(zStart, zEnd);
        errorCount += Checkers.checker("zStart", "2026-01-01T09:00-06:00[America/Chicago]",
                zStart.toString());
        errorCount += Checkers.checker("zEnd", "2026-06-24T17:45+09:00[Asia/Tokyo]", zEnd.toString());
        errorCount += Checkers.checker("Duration.between(zoned)", "PT4169H45M", zonedDuration.toString());

        return errorCount;
    }

    private static int demoChronoUnitBetween() {
        int errorCount = 0;
        LocalDateTime startTime = LocalDateTime.of(2026, 6, 24, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 9, 1, 8, 0);

        errorCount += Checkers.checker("ChronoUnit.DAYS.between", 69L,
                ChronoUnit.DAYS.between(startTime, endTime));
        errorCount += Checkers.checker("ChronoUnit.MONTHS.between", 2L,
                ChronoUnit.MONTHS.between(startTime, endTime));
        errorCount += Checkers.checker("ChronoUnit.HOURS.between", 1656L,
                ChronoUnit.HOURS.between(startTime, endTime));

        ZonedDateTime zStartTime = startTime.atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime zEndTime = endTime.atZone(ZoneId.of("America/Chicago"));
        errorCount += Checkers.checker("zoned ChronoUnit.DAYS", 69L,
                ChronoUnit.DAYS.between(zStartTime, zEndTime));

        return errorCount;
    }
}
