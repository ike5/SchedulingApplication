package utils;

import data.DBAppointment;
import data.DBUsers;
import data.LoginTracker;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Main;
import model.Log;
import model.LogType;
import model.User;
import test.Test;

import java.io.*;
import java.nio.file.Path;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


@UtilityInterfaces
public class ChangeScreen {
    public static void changeScreen(ActionEvent actionEvent,
                                    DBUsers userLogin,
                                    Pair<String, String> usernameAndPasswordReceived,
                                    Parent scene,
                                    UtilityInterfaces.FunctionalChangeScreenInterface o) {
        Main.resourceBundle = ResourceBundle.getBundle("RBundle", Locale.getDefault());
        Main.user = userLogin.getUser();

        try {
            // Deserialize log, add login info, then re-serialize it
            File file = new File("src/data/login.data");
            List<Log> logList = LoginTracker.deserializeLog(file);
            logList.add(
                    new Log(usernameAndPasswordReceived,
                            Timestamp.valueOf(LocalDateTime.now()),
                            (Main.user.isValidUsername() & Main.user.isValidPassword())
                    )
            );
            LoginTracker.serializeLog(logList, file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: " + e);
        } catch (IOException e) {
            System.err.println("IOException here: " + e);
        }

        // Check if username and password are valid, switch views or present appropriate alerts
        if (userLogin.getUser().isValidUsername()) {
            if (userLogin.getUser().isValidPassword()) {

                /*
                Note the event source is either a Button or a TextField:
                stage = (Stage) ((TextField) actionEvent.getSource()).getScene().getWindow();
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                 */

                Stage stage = o.eventSource(actionEvent);
                stage.setTitle("Welcome " + userLogin.getUser().getUsername() + "!");
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, Main.resourceBundle.getString("incorrect_password"));
                alert.setTitle(Main.resourceBundle.getString("password_alert_title"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, Main.resourceBundle.getString("incorrect_username"));
            alert.setTitle(Main.resourceBundle.getString("username_alert_title"));
            alert.showAndWait();
        }
    }
}
