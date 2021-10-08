package utils;

import data.DBUsers;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.CustomAlert;
import test.Test;
import java.util.Locale;
import java.util.ResourceBundle;


@UtilityInterfaces
public class ChangeScreen {
  public static void changeScreen(ActionEvent actionEvent, DBUsers userLogin, Parent scene, UtilityInterfaces.FunctionalChangeScreenInterface o) {
        ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());

        if (userLogin.userExists()) {
            new Test("User exists");
            if (userLogin.passwordMatches()) {
                new Test("Password matches");

//                Note the event source is either a Button or a TextField:
//                stage = (Stage) ((TextField) actionEvent.getSource()).getScene().getWindow();
//                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Stage stage = o.eventSource(actionEvent);
                stage.setTitle("Welcome " + userLogin.getUser().getUsername() + "!");
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                CustomAlert.makeAlert(Alert.AlertType.ERROR, "incorrect_password", "password_alert_title");
            }
        } else {
            CustomAlert.makeAlert(Alert.AlertType.ERROR, "incorrect_username", "username_alert_title");
        }
    }
}
