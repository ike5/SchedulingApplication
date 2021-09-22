package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Eclipse {
    public static void main(String[] args) {
        getDayOfWeek();
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

    private static void time1() {
        LocalDate nowDate = LocalDate.now();    //No time zone
        LocalTime nowTime = LocalTime.now();
        LocalDateTime nowDateTime = LocalDateTime.of(nowDate, nowTime);
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
