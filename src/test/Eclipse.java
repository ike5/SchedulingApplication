package test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Eclipse {
    public static void main(String[] args) {
        adjustDatetime();
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
