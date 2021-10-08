package controller;

import javafx.scene.input.KeyEvent;
import main.Main;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
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
    private DBUsers userLogin;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        language_zone_id.setText(Main.resourceBundle.getString("zone_id"));
        welcome_message.setText(Main.resourceBundle.getString("welcome_message"));
        username_id.setText(Main.resourceBundle.getString("username"));
        password_id.setText(Main.resourceBundle.getString("password"));
        login_id.setText(Main.resourceBundle.getString("login_button"));
        username_field_id.setText(Main.resourceBundle.getString("username_field"));
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
        userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());
        ChangeScreen.changeScreen(
                actionEvent,
                userLogin,
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

        userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());

        ChangeScreen.changeScreen(
                actionEvent,
                userLogin,
                FXMLLoader.load(getClass().getResource("/view/Customers.fxml")),
                aEvent -> (Stage) ((Button) aEvent.getSource()).getScene().getWindow()
        );
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
        String regexPassword = "[\\S]+";
        return password_field_id.getText().matches(regexPassword);
    }

    public void onUsernameKeyTyped(KeyEvent keyEvent) {
        if (validateUsernameString()) {
            username_label_id.setVisible(false);
        } else {
            username_label_id.setText(Main.resourceBundle.getString("invalid_username_format"));
            username_label_id.setVisible(true);
        }
    }

    public void onPasswordKeyTyped(KeyEvent keyEvent) {
        if (validatePasswordString()) {
            password_label_id.setVisible(false);
        } else {
            password_label_id.setText(Main.resourceBundle.getString("invalid_password_format"));
            password_label_id.setVisible(true);
        }

    }
}
