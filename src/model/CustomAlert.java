package model;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

public class CustomAlert {
    public static ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());

    public static void makeAlert(Alert.AlertType alertType, String key_alert_message, String key_alert_title) {
        Alert alert = new Alert(alertType, rb.getString(key_alert_message));

        // FIXME
        // - fix logout alert which is a confirmation
        // - need to figure out how to close alert box when cancel is clicked without actually loggin out.
        switch (alertType) {
            case ERROR:
                alert.setTitle(rb.getString(key_alert_title));
                alert.showAndWait();
                break;
            case CONFIRMATION:
                alert.setTitle(rb.getString(key_alert_title));
                alert.close();
                break;
            case WARNING:
                alert.setTitle("Warning");
                break;
            default:
                alert.showAndWait();
                break;
        }
    }
}
