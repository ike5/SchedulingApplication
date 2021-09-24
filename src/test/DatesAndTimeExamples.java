package test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DatesAndTimeExamples {
    public static void main(String[] args) {
    }

    private static void allMethods(){
        /*
        java.time Class
            Key Instance Creation Options

        LocalDate
            LocalDate.now();
            LocalDate.of(2017, 8, 21);
            LocalDate.parse("2017-08-21");

        LocalTime
            LocalTime.now();
            LocalTime.of(10, 19, 36);
            LocalTime.parse("10:19:36");

        LocalDateTime
            LocalDateTime.now();
            LocalDateTime.of(aDate, aTime);
            LocalDateTime.parse("2017-04-08T10:19:36");
            LocalDateTime.parse(aDateTime, aFormatter);
            LocalDateTime.parse("2017-08-21T10:19", aformatter);

        ZonedDateTime
            ZonedDateTime.now();
            ZonedDateTime.of(aDateTime, ZoneId.of(aZoneString));
            ZonedDateTime.parse("2017-04-08T10:19:36-05:00");

        OffsetDateTime
            OffsetDateTime.now();
            OffsetDateTime.of(aDateTime, ZoneOffset.of("-05:00"));
            OffsetDateTime.parse("2017-04-08T10:19:36-05:00");

        format.DateTimeFormatter
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(aLocale);

        Instant
            Instant.now();
            zonedDateTime.toInstant();
            aDateTime.toInstant(ZoneOffset.of("+5"));

        Duration
            Duration.between(aTime1, aTime2);
            Duration.ofMinutes(5);

        Period
            Period.between(aDate1, aDate2);
            Period.ofDays(3);

        util.Locale
            Locale.getDefault();
            new Locale(String language);
            new Locale(String language, String country);
         */
    }

    private static void usingLocales() {
        /*
        Classes:
            Locale
            Locale Constructors:
                Locale(String language) // Language in ISO 639 Language code
                Locale(String language, String country)
        Methods:
            getDisplayCountry()
            getDisplayLanguage()
            ofLocalizedDateTime()
            withLocale()
         */

        /*
        If you want to represent basic Italian in your application, all you need is the Language code.
        If, on the other hand, you want to represent the Italian used in Switzerland, you’d want to
        indicate that the country is Switzerland (yes, the Country code for Switzerland is "CH"),
        but that the language is Italian:
        */


        Locale myLocale = Locale.getDefault();
        System.out.println("My locale: " + myLocale);
        LocalDateTime aDateTime = LocalDateTime.of(2024, 4, 8, 13, 35, 56);
        System.out.println("The date and time: " +
                aDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(aDateTime, ZoneId.of("US/Pacific"));
        Locale locPT = new Locale("pt");                    // Portugal
        Locale locIN = new Locale("hi", "IN");      // India
        Locale locJA = new Locale("ja");                    // Japan

        Locale locDK = new Locale("da", "DK"); // Denmark
        Locale locIT = new Locale("it", "IT"); // Italy


        System.out.println("Italy (Long) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(Locale.ITALY)
        ));
        System.out.println("Italy (Short) " + aDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(locIT)
        ));
        System.out.println("Japan (Long) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                        .withLocale(Locale.JAPAN)
        ));
        System.out.println("Portugal (Long) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(locPT)
        ));
        System.out.println("India (Long) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(locIN)
        ));
        System.out.println("Denmark (Medium) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(locDK)
        ));
        System.out.println("China (Long) " + zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(Locale.CHINA)
        ));
        Locale locBR = new Locale("pt", "BR"); // Brazil
        Locale locRA = new Locale("ru", "RU"); // Russia

        System.out.println("Denmark, country: " + locDK.getDisplayCountry());
        System.out.println("Denmark, country, local: " + locDK.getDisplayCountry(locDK));
        System.out.println("Denmark, language: " + locDK.getDisplayLanguage());
        System.out.println("Denmark, language, local: " + locDK.getDisplayLanguage(locDK));
        System.out.println("Japan, country, local: " + locJA.getDisplayCountry(locJA));
        System.out.println("Japan, language, local: " + locJA.getDisplayLanguage(locJA));
        System.out.println("Russia, country, local: " + locRA.getDisplayCountry(locPT));
        System.out.println("Russia, language, local: " + locRA.getDisplayLanguage(locPT));

        /*
        Output:

        My locale: en_US
        The date and time: Apr 8, 2024, 1:35:56 PM
        Italy (Long) 8 aprile 2024 13:35:56 PDT
        Italy (Short) 08/04/24, 13:35
        Japan (Long) 2024年4月8日月曜日 13時35分56秒 アメリカ太平洋夏時間
        Portugal (Long) 8 de abril de 2024 13:35:56 PDT
        India (Long) 8 अप्रैल 2024 को 1:35:56 अपराह्न PDT
        Denmark (Medium) 8. apr. 2024 13.35.56
        China (Long) 2024年4月8日 PDT 下午1:35:56
        Denmark, country: Denmark
        Denmark, country, local: Danmark
        Denmark, language: Danish
        Denmark, language, local: dansk
        Brazil, country: Brazil
        Brazil, country, local: Brasil
        Brazil, language: Portuguese
        Brazil, language, local: português
        Italy, Danish language is: danese
         */
    }

    private static void formatWithDateTimeFormatter() {
        /*
        Classes:
            DateTimeFormatter
            ZonedDateTime
        Methods:
            ofPattern()
            format()
         */


        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));
        System.out.println("Totality date/time written for sister in Europe: ");
        System.out.println(totalityAustin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")));

        // Alternately specify format style and locale
        System.out.println("Totality date/time in UK Locale: ");
        System.out.println(
                totalityAustin.format(
                        DateTimeFormatter
                                .ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.UK)
                )
        );

        /*
        Output:

        Totality date/time written for sister in Europe:
        08/04/2024 01:35
        Totality date/time in UK Locale:
        08/04/2024, 13:35
         */

        /*
        if you want to do date formatting for a specific locale, you need to create your Locale object before your
        DateTimeFormatter object because you’ll need your Locale object as an argument to your DateTimeFormatter method;
         */
    }

    private static void findDayOfWeek() {
        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));

        // Another reminder 3 days before
        System.out.println("DateTime of 3 day reminder: " + totalityAustin.minus(Period.ofDays(3)));
        // What day of the week is that?
        System.out.println("Day of week for 3 day reminder: " + totalityAustin.minus(Period.ofDays(3)).getDayOfWeek());

        // Call sister in paris 2 hours later
        ZonedDateTime localParis = totalityAustin.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        System.out.println("Eclipse happens at " + localParis + " Paris time");
        System.out.println("Phone sister at 2 hours after totality: " + totalityAustin.plusHours(2) + ", " +
                localParis.plusHours(2) + " Paris time");

        // Compare two ZonedDateTimes (must be same type)
        System.out.println("Is the 2024 eclipse still in the future? " +
                ZonedDateTime.now().isBefore(totalityAustin));
        // See if leap year
        System.out.println("Is 2024 a leap year? " +
                totalityAustin.toLocalDate().isLeapYear());

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

        ZonedDateTime from an existing LocalDate and LocalTime, you need a ZoneId too
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
