package controller;

import data.DBAppointment;
import data.LoginTracker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import main.Main;
import model.Log;
import model.LogType;
import model.User;
import test.Test;
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
import java.util.List;
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
//        username_field_id.setText(Main.resourceBundle.getString("username_field"));
    }

    @FXML
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    @FXML
    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        textFieldLogin(actionEvent);
    }

    private void textFieldLogin(ActionEvent actionEvent) throws IOException {
        dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());
        Pair<String, String> usernamePasswordReceived = new Pair<>(username_field_id.getText(), password_field_id.getText());
        makeLogEntry(usernamePasswordReceived);

        ChangeScreen.changeScreen(
                actionEvent,
                dbUsers,
                usernamePasswordReceived,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((TextField) aEvent.getSource()).getScene().getWindow());
    }

    /**
     * This method is triggered when the Login Button is clicked.
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        //FIXME (med) - pressing ENTER when button is highlighted doesn't work
        dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());
        Pair<String, String> usernamePasswordReceived = new Pair<>(username_field_id.getText(), password_field_id.getText());
        makeLogEntry(usernamePasswordReceived);

        ChangeScreen.changeScreen(
                actionEvent,
                dbUsers,
                usernamePasswordReceived,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((Button) aEvent.getSource()).getScene().getWindow()
        );
    }

    void makeLogEntry(Pair<String, String> usernamePasswordReceived) {
        if (dbUsers.getUser().isValidUsername() && dbUsers.getUser().isValidPassword()) {
            LoginTracker.addToLog(
                    Path.of("src/login_activity.txt"),
                    LogType.SUCCESS,
                    "Username: " + usernamePasswordReceived.getKey() +
                            "\tPassword: " + usernamePasswordReceived.getValue() +
                            "\tLocalDateTime: " + LocalDateTime.now());
        } else {
            LoginTracker.addToLog(
                    Path.of("src/login_activity.txt"),
                    LogType.FAILURE,
                    "Username: " + usernamePasswordReceived.getKey() +
                            "\tPassword: " + usernamePasswordReceived.getValue() +
                            "\tLocalDateTime: " + LocalDateTime.now());
        }
    }

    /**
     * Validates a username by matching string values that begin with a number or letter, and contains
     * only numbers and letters.
     *
     * @return
     */
    private boolean validateUsernameString() {
        //FIXME (low) - Allow underscores

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

    public void onUsernameKeyTyped(KeyEvent keyEvent) {
        if (validateUsernameString()) {
            username_label_id.setVisible(false);
            username_field_id.setStyle("-fx-background-color: white");
        } else {
            username_label_id.setVisible(true);
            username_label_id.setText(Main.resourceBundle.getString("invalid_username_format"));
            username_field_id.setStyle("-fx-background-color: pink");
        }
    }

    //FIXME (low) - The red password error happens because of a TAB keystroke when entering the password TextField.
    public void onPasswordKeyTyped(KeyEvent keyEvent) {
        if (validatePasswordString()) {
            password_label_id.setVisible(false);
            password_field_id.setStyle("-fx-background-color: white");
        } else {
            password_label_id.setVisible(true);
            password_label_id.setText(Main.resourceBundle.getString("invalid_password_format"));
            password_field_id.setStyle("-fx-background-color: pink");
        }

    }

    public void onLoginKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            dbUsers = new DBUsers(username_field_id.getText(), password_field_id.getText());

            if (dbUsers.getUser().isValidUsername()) {
                if (dbUsers.getUser().isValidPassword()) {
                    Stage stage = (Stage) ((Button) keyEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    stage.setTitle("Welcome " + dbUsers.getUser().getUsername() + "!");
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
}
