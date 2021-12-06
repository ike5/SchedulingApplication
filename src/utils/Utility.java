package utils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * The interface Utility.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public @interface Utility {
    /**
     * The change screen interface. Uses an ActionEvent
     * to decide whether using a Button or a TextField to
     * change views.
     */
    @FunctionalInterface
    interface ChangeViewInterface {
        public Stage eventSource(ActionEvent actionEvent);
    }
}








