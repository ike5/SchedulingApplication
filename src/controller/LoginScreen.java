package controller;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import utils.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    public Label zone_id;
    public Label language_zone_id;
    public Label welcome_message;
    public Label username_label_id;
    public Label password_label_id;
    private Stage stage;
    private Parent scene;
    public Label username_id;
    public Label password_id;
    public TextField username_field_id;
    public TextField password_field_id;
    public Button login_id;
    private DBUsers userLogin;

    ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());

    //TODO
    // - Refactor onLoginAction() and textFieldLogin() to eliminate duplicated code

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set Labels to default language
        language_zone_id.setText(rb.getString("zone_id"));
        welcome_message.setText(rb.getString("welcome_message"));
        username_id.setText(rb.getString("username"));
        password_id.setText(rb.getString("password"));
        login_id.setText(rb.getString("login_button"));
        username_field_id.setText(rb.getString("username_field"));
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

        if (userLogin.userExists()) {
            new Test("User exists");
            if (userLogin.passwordMatches()) {
                new Test("Password matches");

                // Get event source from TextField
                stage = (Stage) ((TextField) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setTitle("Welcome " + userLogin.getUser().getUsername() + "!");
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrect_password"));
                alert.setTitle(rb.getString("password_alert_title"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrect_username"));
            alert.setTitle(rb.getString("username_alert_title"));
            alert.showAndWait();
        }
    }


    /**
     * This is the Event from Button click.
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        DBUsers userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());

        if (userLogin.userExists()) {
            if (userLogin.passwordMatches()) {
                // Get event source from Button
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrect_password"));
                alert.setTitle(rb.getString("password_alert_title"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrect_username"));
            alert.setTitle(rb.getString("username_alert_title"));
            alert.showAndWait();
        }
    }

    /**
     * Validates a username by matching string values that begin with a number or letter, and contains
     * only numbers and letters.
     *
     * @return
     */
    private boolean validateUsernameString() {
        //FIXME
        // - Allow underscores

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
            username_label_id.setText(rb.getString("invalid_username_format"));
            username_label_id.setVisible(true);
        }
    }

    public void onPasswordKeyTyped(KeyEvent keyEvent) {
        if (validatePasswordString()) {
            password_label_id.setVisible(false);
        } else {
            password_label_id.setText(rb.getString("invalid_password_format"));
            password_label_id.setVisible(true);
        }

    }
}
