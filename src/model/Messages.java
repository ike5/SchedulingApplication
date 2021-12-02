package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The type Messages.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Messages {
    /**
     * Error message.
     *
     * @param message the message
     * @param title   the title
     */
    public static void errorMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    /**
     * Warning message.
     *
     * @param message the message
     * @param title   the title
     */
    public static void warningMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setTitle(title);
        alert.show();
    }

    /**
     * Confirmation message optional.
     *
     * @param message the message
     * @param title   the title
     * @return the optional
     */
    public static Optional<ButtonType> confirmationMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle(title);
        return alert.showAndWait();
    }
}
