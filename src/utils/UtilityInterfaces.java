package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Locale;

public @interface UtilityInterfaces {
    @FunctionalInterface
    interface FunctionalAlertInterface {
        Alert makeAlert();
    }

    @FunctionalInterface
    interface FunctionalChangeScreenInterface {
        public Stage eventSource(ActionEvent actionEvent);
    }

    @FunctionalInterface
    interface FunctionalResultSetInterface {
        void whileLogic(ResultSet resultSet) throws SQLException;
    }

    interface AdjustTimeInterface extends TimeZoneInterface {
        Locale addTime();

        Locale subtractTime();
    }

    interface TimeZoneInterface {
        ZonedDateTime startDateTime();

        ZonedDateTime endDateTime();

        Locale createDateTime();

        Locale lastUpdateDateTime();
    }

    interface ChangeScreenInterface {
        void changeScreen();
    }

    interface PersonInterface extends TimeZoneInterface {
        String createdBy(String name);

        String lastUpdatedBy(String name);
    }

    interface ContactInterface extends PersonInterface {
        String setName(String name);
    }

    interface AppointmentsInterface extends PersonInterface, AdjustTimeInterface {
        boolean addAppointment();

        boolean updateAppointment();

        boolean deleteAppointment();
    }



    interface CustomerInterface extends PersonInterface {
    }

    interface UserInterface extends PersonInterface {
    }

    interface ValidateFieldsInterface extends FunctionalAlertInterface{
        boolean notAValidField();
    }

    interface ValidateUserInterface extends ValidateFieldsInterface{
        boolean incorrectUsername();
        boolean incorrectPassword();
    }

    interface ValidateAppointmentsInterface extends ValidateFieldsInterface{
        boolean apptOutsideBusinessHours();
        boolean apptOverlap();
    }

    interface UpcomingAppointmentsInterface extends FunctionalAlertInterface {
        boolean within15Minutes();
        boolean noAppointmentsAtTheMoment();
    }

}







