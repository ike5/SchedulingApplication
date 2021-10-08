package model;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

public class CustomAlert {
    public static ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());
    public static void makeAlert(Alert.AlertType alertType, String key_alert_message, String key_alert_title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString(key_alert_message));
        alert.setTitle(rb.getString(key_alert_title));
        alert.showAndWait();
    }
}
