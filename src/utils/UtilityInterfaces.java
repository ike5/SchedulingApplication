package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public @interface UtilityInterfaces {
    @FunctionalInterface
    interface FunctionalAlertInterface {
        Alert makeAlert();
    }

    @FunctionalInterface
    interface FunctionalChangeScreenInterface {
        public Stage eventSource(ActionEvent actionEvent);
    }
}



