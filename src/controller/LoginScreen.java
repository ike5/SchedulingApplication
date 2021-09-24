package controller;

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
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    private Stage stage;
    private Parent scene;
    public Label the_label;
    public Label username_id;
    public Label password_id;
    public TextField username_field_id;
    public TextField password_field_id;
    public Button login_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Screen initialized!"); // called when FXMLLoader called
        the_label.setText("Hello");

        //FIXME
        // - Add welcome message
        // - Add logo
        // - Determine user location (ZoneID) and display it in a label
        // - Display login form in English or French based on computer language settings, translate text, labels, buttons, errors
    }

    @FXML
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        onLoginAction(actionEvent);
        //TODO Fix bug on clicking ENTER

        //FIXME
        // - Validate username utils (no spaces etc)
        // - Create alert message in red writing below
    }

    @FXML
    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        onLoginAction(actionEvent);
        //TODO Fix bug on clicking Enter

        //FIXME
        // - Validate password utils (no spaces)
        // - Create alert message in red writing below
    }


    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        DBUsers userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());

        if (userLogin.userExists()) {
            System.out.println("User Exists"); // TEST
            if (userLogin.passwordMatches()) {
                System.out.println("Password matches"); // TEST

                //FIXME Make event listeners on ENTER correctly call methods

                // Get event source from button
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                // Load resources from view directory
                scene = (Parent) FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                //TODO Add Alert box for incorrect password only
            }
        } else {
            System.out.println("No user by that name");

            //TODO Add Alert box for incorrect username and/or password
        }
    }
}
