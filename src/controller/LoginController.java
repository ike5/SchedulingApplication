package controller;

import data.LoginTracker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import main.Main;
import model.LogType;
import model.Messages;
import utils.ChangeScreen;
import data.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
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

    public void onUsernameKeyTyped(KeyEvent keyEvent) {
        stringValidation(validateUsernameString(), username_label_id, username_field_id, "invalid_username_format");
    }

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

    //FIXME (low) - The red password error happens because of a TAB keystroke when entering the password TextField.
    public void onLoginKeyPressed(KeyEvent keyEvent) throws IOException {
//        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
//            dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());
//
//            if (dbUsers.getUser().isValidUsername()) {
//                if (dbUsers.getUser().isValidPassword()) {
//                    Main.user = dbUsers.getUser();
//                    switchView(keyEvent, "/view/Customers.fxml", "Welcome " + dbUsers.getUser().getUsername() + "!");
//                } else {
//                    Messages.errorMessage(Main.resourceBundle.getString("incorrect_password"), Main.resourceBundle.getString("password_alert_title"));
//                }
//            } else {
//                Messages.errorMessage(Main.resourceBundle.getString("incorrect_username"), Main.resourceBundle.getString("username_alert_title"));
//            }
//        }
    }

    /**
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        //FIXME (med) - pressing ENTER when button is highlighted doesn't work
        Pair<String, String> usernamePasswordReceived = getUsernamePasswordReceived();

        ChangeScreen.changeScreen(
                actionEvent,
                dbUsers,
                usernamePasswordReceived,
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
     * Typing ENTER
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    /**
     * Typing ENTER
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

        ChangeScreen.changeScreen(
                actionEvent,
                dbUsers,
                usernamePasswordReceived,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((TextField) aEvent.getSource()).getScene().getWindow());
    }

    /**
     * Helper
     *
     * @param keyEvent
     * @param path
     * @param title
     * @throws IOException
     */
    private void switchView(KeyEvent keyEvent, String path, String title) throws IOException {
        Stage stage = (Stage) ((Button) keyEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Helper
     *
     * @param usernamePasswordReceived
     */
    void makeLogEntry(Pair<String, String> usernamePasswordReceived) {
        if (dbUsers.getUser().isValidUsername() && dbUsers.getUser().isValidPassword()) {
            addToLog(usernamePasswordReceived, LogType.SUCCESS);
        } else {
            addToLog(usernamePasswordReceived, LogType.FAILURE);
        }
    }

    /**
     * Helper
     * @param usernamePasswordReceived
     * @param logTypeStatus
     */
    private void addToLog(Pair<String, String> usernamePasswordReceived, LogType logTypeStatus) {
        LoginTracker.addToLog(
                Path.of("login_activity.txt"),
                logTypeStatus,
                "Username: " + usernamePasswordReceived.getKey() +
                        "\tPassword: " + usernamePasswordReceived.getValue() +
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

}
