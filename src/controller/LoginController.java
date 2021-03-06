package controller;

import data.DBAppointment;
import data.LoginTracker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import main.Main;
import model.LogType;
import model.Message;
import data.DBUsers;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;

/**
 * This class displays a login form and validates username and password credentials.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
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

    /**
     * Initializes Labels and TextFields of login form.
     *
     * @param url            The URL
     * @param resourceBundle The ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        language_zone_id.setText(String.valueOf(ZoneId.systemDefault()));
        welcome_message.setText(Main.resourceBundle.getString("welcome_message"));
        username_id.setText(Main.resourceBundle.getString("username"));
        password_id.setText(Main.resourceBundle.getString("password"));
        login_id.setText(Main.resourceBundle.getString("login_button"));
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    public void onUsernameKeyTyped(KeyEvent keyEvent) {
        stringValidation(
                validateUsernameString(),
                username_label_id,
                username_field_id,
                "invalid_username_format"
        );
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    public void onPasswordKeyTyped(KeyEvent keyEvent) {
        stringValidation(
                validatePasswordString(),
                password_label_id,
                password_field_id,
                "invalid_password_format"
        );
    }

    /**
     * Helper method to provide visual error message when username or password
     * is invalid.
     *
     * @param isInvalid            Is an invalid username or password
     * @param label                The label to be changed
     * @param field                The TextField to have its color changed
     * @param invalidFormatMessage The invalid message as a resource bundle
     */
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
     * A lambda expression is used in the last argument of the changeScreen()
     * method in order to use varying ActionEvents (in this case, a Button rather
     * than a TextView).
     *
     * @param actionEvent Login Button is Clicked
     * @throws IOException Exception
     */
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        getUsernamePasswordReceived(); // move inside changeScreen()

        changeView(
                actionEvent,
                dbUsers,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((Button) aEvent.getSource()).getScene().getWindow()
        );
    }

    /**
     * Typing ENTER while on username TextField calls this method. Logs
     * user in.
     *
     * @param actionEvent The ENTER key actionEvent
     * @throws IOException Exception
     */
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    /**
     * Typing ENTER while on password TextField calls this method. Logs
     * user in.
     *
     * @param actionEvent The ENTER key actionEvent
     * @throws IOException Exception
     */
    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    /**
     * Helper method to retrieve the username and password exactly as entered
     * in the login form. This username and password is then made in a log entry.
     *
     * @return Returns a username and password Pair<String, String> object
     */
    private Pair<String, String> getUsernamePasswordReceived() {
        dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());

        Pair<String, String> usernamePasswordReceived = new Pair<>(username_field_id.getText(), password_field_id.getText());

        makeLogEntry(usernamePasswordReceived);
        return usernamePasswordReceived;
    }

    /**
     * Helper method that logs user in. A lambda expression is used in the
     * last argument of the changeScreen() method in order to use varying
     * ActionEvents (in this case, a TextField rather than a
     * Button).
     *
     * @param actionEvent
     * @throws IOException
     */
    private void textFieldLogin(ActionEvent actionEvent) throws IOException {
        getUsernamePasswordReceived();

        changeView(
                actionEvent,
                dbUsers,
                FXMLLoader.load(getClass().getResource(Main.resourceBundle.getString("customers_screen"))),
                aEvent -> (Stage) ((TextField) aEvent.getSource()).getScene().getWindow());
    }

    /**
     * Helper method that takes a String pair of username and password to be
     * validated. Sends Enum values of either SUCCESS or FAILURE to addToLog()
     * method.
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
     * Helper method that takes a String pair and LogType and passes a value
     * to the LoginTracker.addToLog method.
     *
     * @param usernamePasswordReceived A String pair of username and password
     * @param logTypeStatus            An enum of either SUCCESS or FAILURE
     */
    private void addToLog(Pair<String, String> usernamePasswordReceived, LogType logTypeStatus) {
        LoginTracker.addToLog(
                Path.of(Main.resourceBundle.getString("log_path")),
                logTypeStatus,
                "Username: " + usernamePasswordReceived.getKey() +
                        "\tLocalDateTime: " + LocalDateTime.now());
    }

    /**
     * Validates a username by matching string values that begin with either:
     * 1) a number
     * 2) a letter
     * Allows any amount of (only) letters or numbers in the string.
     *
     * @return true if only contains numbers or letters, false if anything else
     */
    private boolean validateUsernameString() {
        String regexUsername = "^[0-z]+";
        return username_field_id.getText().matches(regexUsername);
    }

    /**
     * Validates password by matching all string values except whitespaces
     *
     * @return Returns true if password field complies with regex
     */
    private boolean validatePasswordString() {
        String regexPassword = "^[\\S]+";
        return password_field_id.getText().matches(regexPassword);
    }

    /**
     * Helper method that validates user login then switches views if successful.
     * Using a lambda expression in the last parameter, the functional interface
     * ChangeViewInterface allows the programmer to reuse the same code for passing
     * varying types of ActionEvents (such as a Button or a TextField) and to
     * switch views reusing the same code.
     *
     * @param actionEvent         Either a Button actionEvent or a TextField
     * @param userLogin           The currently logged-in user username and password
     * @param scene               The Scene object
     * @param changeViewInterface Lambda expression whose parameter is an ActionEvent
     *                            and whose expression is a Stage.
     */
    private static void changeView(ActionEvent actionEvent, DBUsers userLogin, Parent scene, Utility.ChangeViewInterface changeViewInterface) {
        if (userLogin.getUser().isValidUsername()) {
            if (userLogin.getUser().isValidPassword()) {

                checkUpcomingAppointment(userLogin); // Checks for upcoming appointments within the specified period

                switchView(actionEvent, userLogin, scene, changeViewInterface);

            } else {
                Message.errorMessage(
                        Main.resourceBundle.getString("incorrect_password"),
                        Main.resourceBundle.getString("password_alert_title")
                );
            }
        } else {
            Message.errorMessage(
                    Main.resourceBundle.getString("incorrect_username"),
                    Main.resourceBundle.getString("username_alert_title")
            );
        }
    }

    /**
     * Helper method that responds to the call to DBAppointment.checkUpcomingAppointments()
     * and provides a message to display to the user.
     *
     * @param userLogin DBUsers object sets static User variable if login successful
     */
    private static void checkUpcomingAppointment(DBUsers userLogin) {
        Main.user = userLogin.getUser(); // If login successful, set static User variable to currently logged-in User.

        Pair<Boolean, Pair<LocalDateTime, Integer>> upcomingAppointment = DBAppointment.checkUpcomingAppointments();

        if (upcomingAppointment != null) {
            Message.warningMessage(
                    "Upcoming appointment: " + upcomingAppointment.getValue().getValue() +
                            "\nTime: " + ZonedDateTime.of(upcomingAppointment.getValue().getKey(), ZoneId.systemDefault()),
                    "Upcoming appointment"
            );
        } else {
            Message.warningMessage("No upcoming appointments", "Upcoming appointment");
        }
    }

    /**
     * Helper method that switches views based on having variable event sources.
     * Sets the Stage using a lambda expression with an event source of either a Button
     * or TextField. The functional interface of the last parameter is ChangeScreenInterface.
     *
     * @param actionEvent         An event source
     * @param userLogin           A DBUsers object to get currently logged-in User's username to display
     * @param scene               Builds a new Scene to switch to
     * @param changeViewInterface A variable event source object whose parameter is an ActionEvent
     *                            and whose expression is a Stage.
     */
    private static void switchView(ActionEvent actionEvent, DBUsers userLogin, Parent scene, Utility.ChangeViewInterface changeViewInterface) {
        Stage stage = changeViewInterface.eventSource(actionEvent);
        stage.setTitle("Welcome " + userLogin.getUser().getUsername() + "!");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

