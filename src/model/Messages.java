package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class Messages {
    public static void errorMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void warningMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setTitle(title);
        alert.show();
    }

    public static Optional<ButtonType> confirmationMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle(title);
        return alert.showAndWait();
    }
}
