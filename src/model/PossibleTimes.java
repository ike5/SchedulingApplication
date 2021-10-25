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
                LocalTime.parse("12:00", dateTimeFormatter),
                LocalTime.parse("12:15", dateTimeFormatter),
                LocalTime.parse("12:30", dateTimeFormatter),
                LocalTime.parse("12:45", dateTimeFormatter),
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
                LocalTime.parse("11:45", dateTimeFormatter)

        );
        return localTimeObservableList;
    }
}
