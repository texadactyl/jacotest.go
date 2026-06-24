import java.time.DayOfWeek;
import java.time.Duration;
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
import java.util.List;

/**
 * Demonstrates the core API surface of java.time.LocalDateTime and
 * java.time.ZonedDateTime: creation, field access, arithmetic,
 * comparison, formatting/parsing, time zone conversion, and
 * duration/period calculations.
 */
public final class main {

     public static void main(String[] args) {
        section("1. Creating LocalDateTime instances");
        demoLocalDateTimeCreation();

        section("2. LocalDateTime field access");
        demoLocalDateTimeFields();

        section("3. LocalDateTime arithmetic and adjusters");
        demoLocalDateTimeArithmetic();

        section("4. LocalDateTime comparison");
        demoLocalDateTimeComparison();

        section("5. Formatting and parsing LocalDateTime");
        demoLocalDateTimeFormatting();

        section("6. Creating ZonedDateTime instances");
        demoZonedDateTimeCreation();

        section("7. ZonedDateTime field access and zone info");
        demoZonedDateTimeFields();

        section("8. Converting between LocalDateTime and ZonedDateTime");
        demoConversion();

        section("9. withZoneSameInstant vs withZoneSameLocal");
        demoZoneShifting();

        section("10. Daylight Saving Time transition");
        demoDstTransition();

        section("11. Duration and Period calculations");
        demoDurationAndPeriod();

        section("12. ChronoUnit.between for both types");
        demoChronoUnitBetween();
    }

    private static void section(String title) {
        System.out.println();
        System.out.printf("=== %s ===%n", title);
    }

    // ---------------------------------------------------------------
    // LocalDateTime
    // ---------------------------------------------------------------

    private static void demoLocalDateTimeCreation() {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("now()                : %s%n", now);

        LocalDateTime explicit = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 15);
        System.out.printf("of(2026,JUNE,24,...)  : %s%n", explicit);

        LocalDateTime fromDateAndTime =
                LocalDateTime.of(LocalDate.of(2030, 1, 1), LocalTime.of(9, 0));
        System.out.printf("of(LocalDate,LocalTime): %s%n", fromDateAndTime);

        LocalDateTime parsed = LocalDateTime.parse("2026-12-25T08:15:30");
        System.out.printf("parse(ISO string)     : %s%n", parsed);

        LocalDateTime fromEpoch =
                LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC);
        System.out.printf("ofEpochSecond(0,UTC)  : %s%n", fromEpoch);
    }

    private static void demoLocalDateTimeFields() {
        LocalDateTime dt = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 15, 250_000_000);

        System.out.printf("dt                : %s%n", dt);
        System.out.printf("getYear()         : %d%n", dt.getYear());
        System.out.printf("getMonth()        : %s%n", dt.getMonth());
        System.out.printf("getMonthValue()   : %d%n", dt.getMonthValue());
        System.out.printf("getDayOfMonth()   : %d%n", dt.getDayOfMonth());
        System.out.printf("getDayOfWeek()    : %s%n", dt.getDayOfWeek());
        System.out.printf("getDayOfYear()    : %d%n", dt.getDayOfYear());
        System.out.printf("getHour()         : %d%n", dt.getHour());
        System.out.printf("getMinute()       : %d%n", dt.getMinute());
        System.out.printf("getSecond()       : %d%n", dt.getSecond());
        System.out.printf("getNano()         : %d%n", dt.getNano());
        System.out.printf("toLocalDate()     : %s%n", dt.toLocalDate());
        System.out.printf("toLocalTime()     : %s%n", dt.toLocalTime());
        System.out.printf("isLeapYear()      : %b%n", dt.toLocalDate().isLeapYear());
    }

    private static void demoLocalDateTimeArithmetic() {
        LocalDateTime base = LocalDateTime.of(2026, Month.JUNE, 24, 14, 30, 0);
        System.out.printf("base              : %s%n", base);
        System.out.printf("plusDays(10)      : %s%n", base.plusDays(10));
        System.out.printf("minusWeeks(2)     : %s%n", base.minusWeeks(2));
        System.out.printf("plusMonths(7)     : %s%n", base.plusMonths(7));
        System.out.printf("plusHours(36)     : %s%n", base.plusHours(36));
        System.out.printf("minusYears(1)     : %s%n", base.minusYears(1));
        System.out.printf("withYear(2030)    : %s%n", base.withYear(2030));
        System.out.printf("withHour(0)       : %s%n", base.withHour(0));
        System.out.printf("truncatedTo(HOURS): %s%n", base.truncatedTo(ChronoUnit.HOURS));

        LocalDateTime firstOfNextMonth =
                base.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.printf("firstDayOfNextMonth: %s%n", firstOfNextMonth);

        LocalDateTime nextMonday =
                base.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.printf("next(MONDAY)      : %s%n", nextMonday);
    }

    private static void demoLocalDateTimeComparison() {
        LocalDateTime a = LocalDateTime.of(2026, 6, 24, 9, 0);
        LocalDateTime b = LocalDateTime.of(2026, 6, 24, 17, 0);

        System.out.printf("a                 : %s%n", a);
        System.out.printf("b                 : %s%n", b);
        System.out.printf("a.isBefore(b)     : %b%n", a.isBefore(b));
        System.out.printf("a.isAfter(b)      : %b%n", a.isAfter(b));
        System.out.printf("a.isEqual(a)      : %b%n", a.isEqual(a));
        System.out.printf("a.compareTo(b)    : %d%n", a.compareTo(b));

        List<LocalDateTime> times = List.of(b, a);
        LocalDateTime earliest = times.stream().min(LocalDateTime::compareTo).orElseThrow();
        System.out.printf("earliest of list  : %s%n", earliest);
    }

    private static void demoLocalDateTimeFormatting() {
        LocalDateTime dt = LocalDateTime.of(2026, 6, 24, 14, 30, 15);

        System.out.printf("ISO_LOCAL_DATE_TIME : %s%n",
                dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        DateTimeFormatter custom = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");
        System.out.printf("custom pattern      : %s%n", dt.format(custom));

        DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.printf("date-only pattern   : %s%n", dt.format(dateOnly));

        LocalDateTime reparsed = LocalDateTime.parse("24-06-2026 14:30:15",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        System.out.printf("parsed with pattern : %s%n", reparsed);
    }

    // ---------------------------------------------------------------
    // ZonedDateTime
    // ---------------------------------------------------------------

    private static void demoZonedDateTimeCreation() {
        ZonedDateTime nowSystem = ZonedDateTime.now();
        System.out.printf("now() [system zone]      : %s%n", nowSystem);

        ZonedDateTime nowTokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.printf("now(Asia/Tokyo)          : %s%n", nowTokyo);

        ZonedDateTime explicit = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("America/Chicago"));
        System.out.printf("of(...,America/Chicago)  : %s%n", explicit);

        ZonedDateTime fromLocal = LocalDateTime.of(2026, 6, 24, 14, 30)
                .atZone(ZoneId.of("Europe/London"));
        System.out.printf("LocalDateTime.atZone(...): %s%n", fromLocal);

        ZonedDateTime parsed = ZonedDateTime.parse("2026-06-24T14:30:00-05:00[America/Chicago]");
        System.out.printf("parse(zoned ISO string)  : %s%n", parsed);
    }

    private static void demoZonedDateTimeFields() {
        ZonedDateTime zdt = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("Australia/Sydney"));

        System.out.printf("zdt                : %s%n", zdt);
        System.out.printf("getZone()          : %s%n", zdt.getZone());
        System.out.printf("getOffset()        : %s%n", zdt.getOffset());
        System.out.printf("getDayOfWeek()     : %s%n", zdt.getDayOfWeek());
        System.out.printf("toInstant()        : %s%n", zdt.toInstant());
        System.out.printf("toEpochSecond()    : %d%n", zdt.toEpochSecond());

        System.out.println("A few available zone IDs:");
        ZoneId.getAvailableZoneIds().stream()
                .filter(id -> id.startsWith("America/") || id.startsWith("Asia/"))
                .sorted()
                .limit(5)
                .forEach(id -> System.out.printf("  %s%n", id));
    }

    private static void demoConversion() {
        LocalDateTime local = LocalDateTime.of(2026, 6, 24, 14, 30);
        System.out.printf("local                          : %s%n", local);

        ZonedDateTime asNewYork = local.atZone(ZoneId.of("America/New_York"));
        System.out.printf("local.atZone(America/New_York) : %s%n", asNewYork);

        LocalDateTime backToLocal = asNewYork.toLocalDateTime();
        System.out.printf("asNewYork.toLocalDateTime()    : %s%n", backToLocal);
        System.out.printf("local.equals(backToLocal)      : %b%n", local.equals(backToLocal));

        ZonedDateTime withOffset =
                ZonedDateTime.of(local, ZoneOffset.ofHours(-3));
        System.out.printf("of(local, ZoneOffset.-3)       : %s%n", withOffset);
    }

    private static void demoZoneShifting() {
        ZonedDateTime chicago = ZonedDateTime.of(
                2026, 6, 24, 14, 30, 0, 0, ZoneId.of("America/Chicago"));
        System.out.printf("chicago                       : %s%n", chicago);

        ZonedDateTime sameInstantInTokyo =
                chicago.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        System.out.printf("withZoneSameInstant(Tokyo)    : %s%n", sameInstantInTokyo);
        System.out.printf("instants equal?               : %b%n",
                chicago.toInstant().equals(sameInstantInTokyo.toInstant()));

        ZonedDateTime sameLocalInTokyo =
                chicago.withZoneSameLocal(ZoneId.of("Asia/Tokyo"));
        System.out.printf("withZoneSameLocal(Tokyo)      : %s%n", sameLocalInTokyo);
        System.out.printf("wall-clock times equal?       : %b%n",
                chicago.toLocalDateTime().equals(sameLocalInTokyo.toLocalDateTime()));
    }

    private static void demoDstTransition() {
        // US Eastern time springs forward on the second Sunday of March.
        // At 2:00 AM local time the clocks jump to 3:00 AM, so 2:30 AM
        // does not exist on that date.
        ZoneId newYork = ZoneId.of("America/New_York");
        LocalDateTime nonExistent = LocalDateTime.of(2026, 3, 8, 2, 30);

        ZonedDateTime resolved = nonExistent.atZone(newYork);
        System.out.printf("nominal local time     : %s%n", nonExistent);
        System.out.printf("resolved ZonedDateTime : %s%n", resolved);
        System.out.printf("zone rules offset(now) : %s%n",
                newYork.getRules().getOffset(LocalDateTime.now()));

        ZonedDateTime beforeFallBack = ZonedDateTime.of(
                2026, 11, 1, 0, 30, 0, 0, newYork);
        ZonedDateTime afterAddingTwoHours = beforeFallBack.plusHours(2);
        System.out.printf("before fall-back       : %s%n", beforeFallBack);
        System.out.printf("plusHours(2)           : %s%n", afterAddingTwoHours);
    }

    // ---------------------------------------------------------------
    // Duration / Period
    // ---------------------------------------------------------------

    private static void demoDurationAndPeriod() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 6, 24, 17, 45);

        Duration duration = Duration.between(start, end);
        System.out.printf("start                  : %s%n", start);
        System.out.printf("end                    : %s%n", end);
        System.out.printf("Duration.between       : %s%n", duration);
        System.out.printf("duration.toDays()      : %d%n", duration.toDays());
        System.out.printf("duration.toHours()     : %d%n", duration.toHours());

        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        System.out.printf("Period.between         : %d years, %d months, %d days%n",
                period.getYears(), period.getMonths(), period.getDays());

        ZonedDateTime zStart = start.atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime zEnd = end.atZone(ZoneId.of("Asia/Tokyo"));
        Duration zonedDuration = Duration.between(zStart, zEnd);
        System.out.printf("zStart                 : %s%n", zStart);
        System.out.printf("zEnd                   : %s%n", zEnd);
        System.out.printf("Duration.between(zoned): %s%n", zonedDuration);
    }

    private static void demoChronoUnitBetween() {
        LocalDateTime a = LocalDateTime.of(2026, 6, 24, 8, 0);
        LocalDateTime b = LocalDateTime.of(2026, 9, 1, 8, 0);

        System.out.printf("ChronoUnit.DAYS.between   : %d%n", ChronoUnit.DAYS.between(a, b));
        System.out.printf("ChronoUnit.MONTHS.between : %d%n", ChronoUnit.MONTHS.between(a, b));
        System.out.printf("ChronoUnit.HOURS.between  : %d%n", ChronoUnit.HOURS.between(a, b));

        ZonedDateTime za = a.atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime zb = b.atZone(ZoneId.of("America/Chicago"));
        System.out.printf("zoned ChronoUnit.DAYS     : %d%n", ChronoUnit.DAYS.between(za, zb));
    }
}