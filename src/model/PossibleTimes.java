package model;

import examples.DatesAndTimeExamples;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public enum PossibleTimes {
    POSSIBLE_TIMES;

    public static final ObservableList<LocalTime> localTimeList() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ObservableList<LocalTime> localTimeObservableList = FXCollections.observableArrayList();

        localTimeObservableList.addAll(
                LocalTime.parse("00:00", dateTimeFormatter),
                LocalTime.parse("00:15", dateTimeFormatter),
                LocalTime.parse("00:30", dateTimeFormatter),
                LocalTime.parse("00:45", dateTimeFormatter),
                LocalTime.parse("01:00", dateTimeFormatter),
                LocalTime.parse("01:15", dateTimeFormatter),
                LocalTime.parse("01:30", dateTimeFormatter),
                LocalTime.parse("01:45", dateTimeFormatter),
                LocalTime.parse("02:00", dateTimeFormatter),
                LocalTime.parse("02:15", dateTimeFormatter),
                LocalTime.parse("02:30", dateTimeFormatter),
                LocalTime.parse("02:45", dateTimeFormatter),
                LocalTime.parse("03:00", dateTimeFormatter),
                LocalTime.parse("03:15", dateTimeFormatter),
                LocalTime.parse("03:30", dateTimeFormatter),
                LocalTime.parse("03:45", dateTimeFormatter),
                LocalTime.parse("04:00", dateTimeFormatter),
                LocalTime.parse("04:15", dateTimeFormatter),
                LocalTime.parse("04:30", dateTimeFormatter),
                LocalTime.parse("04:45", dateTimeFormatter),
                LocalTime.parse("05:00", dateTimeFormatter),
                LocalTime.parse("05:15", dateTimeFormatter),
                LocalTime.parse("05:30", dateTimeFormatter),
                LocalTime.parse("05:45", dateTimeFormatter),
                LocalTime.parse("06:00", dateTimeFormatter),
                LocalTime.parse("06:15", dateTimeFormatter),
                LocalTime.parse("06:30", dateTimeFormatter),
                LocalTime.parse("06:45", dateTimeFormatter),
                LocalTime.parse("07:00", dateTimeFormatter),
                LocalTime.parse("07:15", dateTimeFormatter),
                LocalTime.parse("07:30", dateTimeFormatter),
                LocalTime.parse("07:45", dateTimeFormatter),
                LocalTime.parse("08:00", dateTimeFormatter),
                LocalTime.parse("08:15", dateTimeFormatter),
                LocalTime.parse("08:30", dateTimeFormatter),
                LocalTime.parse("08:45", dateTimeFormatter),
                LocalTime.parse("09:00", dateTimeFormatter),
                LocalTime.parse("09:15", dateTimeFormatter),
                LocalTime.parse("09:30", dateTimeFormatter),
                LocalTime.parse("09:45", dateTimeFormatter),
                LocalTime.parse("10:00", dateTimeFormatter),
                LocalTime.parse("10:15", dateTimeFormatter),
                LocalTime.parse("10:30", dateTimeFormatter),
                LocalTime.parse("10:45", dateTimeFormatter),
                LocalTime.parse("11:00", dateTimeFormatter),
                LocalTime.parse("11:15", dateTimeFormatter),
                LocalTime.parse("11:30", dateTimeFormatter),
                LocalTime.parse("11:45", dateTimeFormatter),
                LocalTime.parse("12:00", dateTimeFormatter),
                LocalTime.parse("12:15", dateTimeFormatter),
                LocalTime.parse("12:30", dateTimeFormatter),
                LocalTime.parse("12:45", dateTimeFormatter),
                LocalTime.parse("13:00", dateTimeFormatter),
                LocalTime.parse("13:15", dateTimeFormatter),
                LocalTime.parse("13:30", dateTimeFormatter),
                LocalTime.parse("13:45", dateTimeFormatter),
                LocalTime.parse("14:00", dateTimeFormatter),
                LocalTime.parse("14:15", dateTimeFormatter),
                LocalTime.parse("14:30", dateTimeFormatter),
                LocalTime.parse("14:45", dateTimeFormatter),
                LocalTime.parse("15:00", dateTimeFormatter),
                LocalTime.parse("15:15", dateTimeFormatter),
                LocalTime.parse("15:30", dateTimeFormatter),
                LocalTime.parse("15:45", dateTimeFormatter),
                LocalTime.parse("16:00", dateTimeFormatter),
                LocalTime.parse("16:15", dateTimeFormatter),
                LocalTime.parse("16:30", dateTimeFormatter),
                LocalTime.parse("16:45", dateTimeFormatter),
                LocalTime.parse("17:00", dateTimeFormatter),
                LocalTime.parse("17:15", dateTimeFormatter),
                LocalTime.parse("17:30", dateTimeFormatter),
                LocalTime.parse("17:45", dateTimeFormatter),
                LocalTime.parse("18:00", dateTimeFormatter),
                LocalTime.parse("18:15", dateTimeFormatter),
                LocalTime.parse("18:30", dateTimeFormatter),
                LocalTime.parse("18:45", dateTimeFormatter),
                LocalTime.parse("19:00", dateTimeFormatter),
                LocalTime.parse("19:15", dateTimeFormatter),
                LocalTime.parse("19:30", dateTimeFormatter),
                LocalTime.parse("19:45", dateTimeFormatter),
                LocalTime.parse("20:00", dateTimeFormatter),
                LocalTime.parse("20:15", dateTimeFormatter),
                LocalTime.parse("20:30", dateTimeFormatter),
                LocalTime.parse("20:45", dateTimeFormatter),
                LocalTime.parse("21:00", dateTimeFormatter),
                LocalTime.parse("21:15", dateTimeFormatter),
                LocalTime.parse("21:30", dateTimeFormatter),
                LocalTime.parse("21:45", dateTimeFormatter),
                LocalTime.parse("22:00", dateTimeFormatter),
                LocalTime.parse("22:15", dateTimeFormatter),
                LocalTime.parse("22:30", dateTimeFormatter),
                LocalTime.parse("22:45", dateTimeFormatter),
                LocalTime.parse("23:00", dateTimeFormatter),
                LocalTime.parse("23:15", dateTimeFormatter),
                LocalTime.parse("23:30", dateTimeFormatter),
                LocalTime.parse("23:45", dateTimeFormatter)
        );
        return localTimeObservableList;
    }
}
