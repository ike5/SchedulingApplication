package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * @author Ike Maldonado
 * @version 1.0
 */
public @interface Utility {
    @FunctionalInterface
    interface FunctionalChangeScreenInterface {
        public Stage eventSource(ActionEvent actionEvent);
    }
}








