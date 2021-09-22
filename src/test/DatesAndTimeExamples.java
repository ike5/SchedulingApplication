package test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DatesAndTimeExamples {
    public static void main(String[] args) {
        usingInstants();
    }

    private static void usingInstants() {
        /*
        Classes:
            Instant
            ISO_INSTANT (format for displaying a datetime)
        Methods:
            toInstant()
            Instant.now()
        */

        // Convert ZonedDateTime to Instant
        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));
        Instant totalityInstant1 = totalityAustin.toInstant();
        System.out.println("Austin's eclipse instant is: " + totalityInstant1);

        // Compute difference using an Instant
        Instant nowInstant = Instant.now();
        Instant totalityInstant = totalityAustin.toInstant();
        long minsBetween = ChronoUnit.MINUTES.between(nowInstant, totalityInstant);
        Duration durationBetweenInstants = Duration.ofMinutes(minsBetween);
        System.out.println("Minutes between " + minsBetween + ", is duration " + durationBetweenInstants);


        // You can always get the number of seconds as a long value from the Instant
        // In order to call toInstant() must supply ZoneOffset argument
        // The 'Z' below means displayed for GMT zone rather than U.S. Central (This is ISO_INSTANT format)

        /*
        Output:

        Austin's eclipse instant is: 2024-04-08T18:35:56Z
        Minutes between 1337459, is duration PT22290H59M
         */
    }

    /**
     * Durations using ChronoUnit and Duration.ChronoUnit
     */
    private static void usingDurations() {
        /*
        Classes:
            ChronoUnit
            java.time.temporal
        Methods:
            between(LocalTime, LocalTime) : long
            Duration.Durations
            Duration.ofMinutes()
        Enums:
            ChronoUnit.MINUTES.between()
            Duration.ChronoUnit
         */

        // Create two LocalTime objects representing start and time of totality
        // ECLIPSE BEGINS IN AUSTIN, TX
        LocalTime begins = LocalTime.of(12, 17, 32);    // 12:17:32
        // TOTALITY IN AUSTIN, TX
        LocalTime totality = LocalTime.of(13, 35, 56);   // 13:35:56
        System.out.println("Eclipse begins at " + begins + " and totality is at " + totality);

        // How many minutes between when the eclipse begins and totality?
        long betweenMinutes = ChronoUnit.MINUTES.between(begins, totality);
        System.out.println("Minutes between begin and totality: " + betweenMinutes);

        // Turn minutes into a Duration
        Duration betweenDuration = Duration.ofMinutes(betweenMinutes);
        System.out.println("Duration: " + betweenDuration);

        // Add minutes back to the LocalTime by adding a Duration object
        LocalTime totalityBegins = begins.plus(betweenDuration);
        System.out.println("Totality begins, computed: " + totalityBegins);


        /*
        Output:

        Eclipse begins at 12:17:32 and totality is at 13:35:56
        Minutes between begin and totality: 78
        Duration: PT1H18M
        Totality begins, computed: 13:35:32
         */
    }

    /**
     * Use Period class
     */
    private static void remindOneMonthBefore() {
        ZonedDateTime totalityAustin = ZonedDateTime.of(
                2024, 4, 13, 8, 13, 56, 0, ZoneId.of("US/Central")
        );
        System.out.println("Next total eclipse in the US, date/time in Austin, TX: " + totalityAustin);

        // Reminder for a month before
        Period period = Period.ofMonths(1);
        System.out.println("Period is " + period);

        ZonedDateTime reminder = totalityAustin.minus(period);
        System.out.println("DateTime of 1 month reminder: " + reminder);

        // Create LocalDateTime from ZonedDateTime
        // No time zone printed
        System.out.println("Local DateTime (Austin, TX) of reminder: " + reminder.toLocalDateTime());
        System.out.println("Zoned DateTime (Madras, OR) of reminder: " +
                reminder.withZoneSameInstant(ZoneId.of("US/Pacific")));

        /*
        Next total eclipse in the US, date/time in Austin, TX: 2024-04-13T08:13:56-05:00[US/Central]
        Period is P1M
        DateTime of 1 month reminder: 2024-03-13T08:13:56-05:00[US/Central]
        Local DateTime (Austin, TX) of reminder: 2024-03-13T08:13:56
        Zoned DateTime (Madras, OR) of reminder: 2024-03-13T06:13:56-07:00[US/Pacific] // 2 hours earlier than above
         */
    }

    private static void computeOneMonthBefore() {
        // Totality begins in Austin, TX in 2024 at 1:35pm and 56 seconds;
        // Specify year, month, dayOfMonth, hour, minute, second, nano, zone
        ZonedDateTime totalityAustin = ZonedDateTime.of(
                2024, 4, 13, 8, 13, 56, 0, ZoneId.of("US/Central")
        );
        System.out.println("Next total eclipse in the US, date/time in Autin, TX: " + totalityAustin);

        /*
        Next total eclipse in the US, date/time in Autin, TX: 2024-04-13T08:13:56-05:00[US/Central]
         */
    }

    /**
     * Use TemporalAdjusters (plural). Make new one from old.
     */
    private static void adjustDatetime() {
        String eclipseDateTime = "2017-08-31 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        ZonedDateTime zTotalityDateTime = ZonedDateTime.of(eclipseDay, ZoneId.of("US/Pacific")); // immutable

        ZonedDateTime followingThursdayDateTime = zTotalityDateTime.with(TemporalAdjusters.next(
                DayOfWeek.THURSDAY
        )); // adjust date time to next Thursday
        ZonedDateTime followingYearDateTime = zTotalityDateTime.with(TemporalAdjusters.firstDayOfYear());
        ZonedDateTime lastDayOfMonthDateTime = zTotalityDateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("Thursday following the totality date is: " + followingThursdayDateTime);
        System.out.println("First day of year following the totality is: " + followingYearDateTime);
        System.out.println("Last day of month following the totality is: " + lastDayOfMonthDateTime);

        /*
        Thursday following the totality date is: 2017-09-07T10:19-07:00[US/Pacific]
        First day of year following the totality is: 2017-01-01T10:19-08:00[US/Pacific]
        Last day of month following the totality is: 2017-08-31T10:19-07:00[US/Pacific]
         */
    }

    /**
     * Use ZoneRules.
     */
    private static void findIfDaylightSavings() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        ZonedDateTime zTotalityDateTime = ZonedDateTime.of(eclipseDay, ZoneId.of("US/Pacific")); // immutable

        //US Pacific is either GMT-7 (winter) or GMT-8 (summer daylight savings time)
        ZoneId pacific = ZoneId.of("US/Pacific");
        System.out.println("Is Daylight Savings in effect at time of totality: " +
                pacific.getRules().isDaylightSavings(zTotalityDateTime.toInstant()));

        /*
        Is Daylight Savings in effect at time of totality: true

        A ZoneRules object has a method isDaylightSavings(), which takes an Instant and
        determines whether that Instant is currently in daylight savings. Allows using the
        toInstant() method.

         */
    }

    private static void findNameIdsOfZones() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        List<String> zoneList = new ArrayList<>(zoneIds);
        Collections.sort(zoneList);
        for (String zoneId : zoneList) {
            if (zoneId.contains("US") || zoneId.contains("GB")) {
                System.out.println(zoneId);
            }
        }

        /*
        GB-Eire
        US/Alaska
        US/Aleutian
        US/Arizona
        US/Central
        US/East-Indiana
        US/Eastern
        US/Hawaii
        US/Indiana-Starke
        US/Michigan
        US/Mountain
        US/Pacific
        US/Samoa

        Notice: some zone ids are country, city, and other names
         */
    }

    private static void zonedDateTime() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter

        ZonedDateTime zTotalityDateTime = ZonedDateTime.of(eclipseDay, ZoneId.of("US/Pacific"));
        ZonedDateTime zTotalityDateTime2 = ZonedDateTime.of(eclipseDay, ZoneId.of("GMT-7")); //Same as above
        System.out.println("Date and time totality begins with time zone: " + zTotalityDateTime2);

        /*
        Date and time totality begins with time zone: 2017-08-21T10:19-07:00[US/Pacific]
        Date and time totality begins with time zone: 2017-08-21T10:19-07:00[GMT-07:00]
         */

        /*
        ZonedDateTimes are subject to ZoneRules when you adjust them.

        If you want to create a datetime with a zone offset from GMT that does not use the ZoneRules,
        then you can use an OffsetDateTime. An OffsetDateTime is a fixed datetime and offset that doesn’t
        change even if the ZoneRules change.
         */
    }

    private static void getDayOfWeek() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        System.out.println("What day of the week is eclipse? " + eclipseDay.getDayOfWeek());

        /*
        What day of the week is eclipse? MONDAY
         */
    }

    private static void addDays() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        System.out.println("Going home: " + eclipseDay.plusDays(3));

        /*
        Going home: 2017-08-24T10:19
         */
    }

    private static void convertToCentralTime() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        System.out.println("Mom time: " + eclipseDay.plusHours(2));

        /*
        Mom time: 2017-08-21T12:19
         */
    }

    private static void setFormatOfOutput() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter

        System.out.println("Eclipse day, formatted: " +
                eclipseDay.format(DateTimeFormatter.ofPattern("dd, mm, yy hh, mm")));
        /*
        Eclipse day, formatted: 21, 19, 17 10, 19
         */
    }

    /**
     * Create your own format
     */
    private static void setFormat() {
        String eclipseDateTime = "2017-08-21 10:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter
        System.out.println("Eclipse day: " + eclipseDay);
        /*
        Eclipse day: 2017-08-21T10:19

        Notice: Will insert 'T' between formatted time
         */
    }

    private static void setSpecificTime() {
        //Eclipse begins in Madras, OR
        LocalTime begins = LocalTime.of(9, 6, 43); //9:06:43
        //Totality starts in Madras, OR
        LocalTime totality = LocalTime.parse("10:19:36");   //10:19:36
        System.out.println("Eclipse begins at " + begins +
                " and totality is at " + totality);
        /*
        Eclipse begins at 09:06:43 and totality is at 10:19:36
         */
    }

    private static void setSpecificDate() {
        //The day of the eclipse in Madras, OR
        LocalDate eclipseDate1 = LocalDate.of(2017, 8, 21);
        LocalDate eclipseDate2 = LocalDate.parse("2017-08-21");
        System.out.println("Eclipse date: " + eclipseDate1 + ", " + eclipseDate2);
        /*
        Eclipse date: 2017-08-21, 2017-08-21

        Notice: Date is in same format. LocalDate represents date in ISO-8601 calendar system: YYYY-MM-DD
        Check out java.time.format.DateTimeFormatter
         */
    }

    private static void time() {
        LocalDate nowDate = LocalDate.now();    //No time zone
        LocalTime nowTime = LocalTime.now();
        LocalDateTime nowDateTime = LocalDateTime.of(nowDate, nowTime); // immutable
//        LocalDateTime nowDateTime = LocalDateTime.now(); //Does the same as above

        System.out.println(nowDate);
        System.out.println(nowTime);
        System.out.println(nowDateTime);
        /*
        2021-09-22T05:47:16.344791
        2021-09-22 = 9/22/21
        05:47 = 5:47AM
        16 = 16 seconds
        344791 = milliseconds (which are distinguished by a decimal point)

        Notice: Java places a 'T' between the date and time when converting the
        LocalDateTime to a string
         */
    }
}
