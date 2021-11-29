package controller;

import data.DBAppointment;
import data.LoginTracker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import main.Main;
import model.LogType;
import model.Messages;
import data.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Utility;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label zone_id;
    public Label language_zone_id;
    public Label welcome_message;
    public Label username_label_id;
    public Label password_label_id;
    public Label username_id;
    public Label password_id;
    public TextField username_field_id;
    public TextField password_field_id;
    public Button login_id;
    private DBUsers dbUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        language_zone_id.setText(Main.resourceBundle.getString("zone_id"));
        welcome_message.setText(Main.resourceBundle.getString("welcome_message"));
        username_id.setText(Main.resourceBundle.getString("username"));
        password_id.setText(Main.resourceBundle.getString("password"));
        login_id.setText(Main.resourceBundle.getString("login_button"));
    }

    @FXML
    public void onUsernameKeyTyped(KeyEvent keyEvent) {
        stringValidation(validateUsernameString(), username_label_id, username_field_id, "invalid_username_format");
    }

    @FXML
    public void onPasswordKeyTyped(KeyEvent keyEvent) {
        stringValidation(validatePasswordString(), password_label_id, password_field_id, "invalid_password_format");

    }

    private void stringValidation(boolean isInvalid, Label label, TextField field, String invalidFormatMessage) {
        if (isInvalid) {
            label.setVisible(false);
            field.setStyle("-fx-background-color: white");
        } else {
            label.setVisible(true);
            label.setText(Main.resourceBundle.getString(invalidFormatMessage));
            field.setStyle("-fx-background-color: pink");
        }
    }

    /**
     * This method is triggered when the login button is CLICKED with a mouse.
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        Pair<String, String> usernamePasswordReceived = getUsernamePasswordReceived();

        changeScreen(
                actionEvent,
                dbUsers,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((Button) aEvent.getSource()).getScene().getWindow()
        );
    }

    private Pair<String, String> getUsernamePasswordReceived() {
        dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());
        Pair<String, String> usernamePasswordReceived = new Pair<>(username_field_id.getText(), password_field_id.getText());
        makeLogEntry(usernamePasswordReceived);
        return usernamePasswordReceived;
    }

    /**
     * Typing ENTER while on username TextField calls this method
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    /**
     * Typing ENTER while on password TextField triggers this method
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    /**
     * Helper
     *
     * @param actionEvent
     * @throws IOException
     */
    private void textFieldLogin(ActionEvent actionEvent) throws IOException {
        Pair<String, String> usernamePasswordReceived = getUsernamePasswordReceived();

        changeScreen(
                actionEvent,
                dbUsers,
                FXMLLoader.load(getClass().getResource(Main.resourceBundle.getString("customer_screen"))),
                aEvent -> (Stage) ((TextField) aEvent.getSource()).getScene().getWindow());
    }

    /**
     * Helper method that takes a String pair of username and password to be validated. Sends Enum values of either
     * SUCCESS or FAILURE to addToLog() method.
     *
     * @param usernamePasswordReceived A String pair of username and password
     */
    private void makeLogEntry(Pair<String, String> usernamePasswordReceived) {
        if (dbUsers.getUser().isValidUsername() && dbUsers.getUser().isValidPassword()) {
            addToLog(usernamePasswordReceived, LogType.SUCCESS);
        } else {
            addToLog(usernamePasswordReceived, LogType.FAILURE);
        }
    }

    /**
     * Helper method that takes a String pair and LogType and passes a value to the LoginTracker.addToLog method.
     *
     * @param usernamePasswordReceived A String pair of username and password
     * @param logTypeStatus An enum of either SUCCESS or FAILURE
     */
    private void addToLog(Pair<String, String> usernamePasswordReceived, LogType logTypeStatus) {
        LoginTracker.addToLog(
                Path.of(Main.resourceBundle.getString("log_path")),
                logTypeStatus,
                "Username: " + usernamePasswordReceived.getKey() +
                        "\tLocalDateTime: " + LocalDateTime.now());
    }

    /**
     * Validation
     * <p>
     * Validates a username by matching string values that begin with a number or letter, and contains
     * only numbers and letters.
     *
     * @return
     */
    private boolean validateUsernameString() {
        String regexUsername = "^[0-z]+";
        return username_field_id.getText().matches(regexUsername);
    }

    /**
     * Validation
     * <p>
     * Validates password by matching all string values except whitespaces
     *
     * @return Returns true if password field complies with regex
     */
    private boolean validatePasswordString() {
        String regexPassword = "^[\\S]+";
        return password_field_id.getText().matches(regexPassword);
    }

    /**
     * Note the event source is either a Button or a TextField:
     * stage = (Stage) ((TextField) actionEvent.getSource()).getScene().getWindow();
     * stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
     *
     * @param actionEvent
     * @param userLogin
     * @param scene
     * @param o
     */
    public static void changeScreen(ActionEvent actionEvent, DBUsers userLogin, Parent scene, Utility.FunctionalChangeScreenInterface o) {
        Main.resourceBundle = ResourceBundle.getBundle("RBundle", Locale.getDefault());

        // Check if username and password are valid
        // then switch views or alert
        if (userLogin.getUser().isValidUsername()) {
            if (userLogin.getUser().isValidPassword()) {
                Main.user = userLogin.getUser();
                Pair<Boolean, Pair<LocalDateTime, Integer>> upcomingAppointment = DBAppointment.checkUpcomingAppointments();
                if (upcomingAppointment != null) {
                    Messages.warningMessage(
                            "Upcoming appointment: " + upcomingAppointment.getValue().getValue() +
                                    "\nTime: " + ZonedDateTime.of(upcomingAppointment.getValue().getKey(), ZoneId.systemDefault()),
                            "Upcoming appointment"
                    );
                } else {
                    Messages.warningMessage("No upcoming appointments", "Upcoming appointment");
                }

                switchView(actionEvent, userLogin, scene, o);
            } else {
                Messages.errorMessage(Main.resourceBundle.getString("incorrect_password"), Main.resourceBundle.getString("password_alert_title"));
            }
        } else {
            Messages.errorMessage(Main.resourceBundle.getString("incorrect_username"), Main.resourceBundle.getString("username_alert_title"));
        }
    }

    /**
     * Helper
     *
     * @param actionEvent
     * @param userLogin
     * @param scene
     * @param o
     */
    private static void switchView(ActionEvent actionEvent, DBUsers userLogin, Parent scene, Utility.FunctionalChangeScreenInterface o) {
        Stage stage = o.eventSource(actionEvent);
        stage.setTitle("Welcome " + userLogin.getUser().getUsername() + "!");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

