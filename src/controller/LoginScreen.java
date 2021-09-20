package controller;

import data.DBCountries;
import data.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;

import java.net.URL;
import java.util.ResourceBundle;

// controllers need to implement the Initializable interface
public class LoginScreen implements Initializable {
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
    }

    public void usernameOnAction(ActionEvent actionEvent) {
        // Validates format of username upon typing
    }

    public void passwordOnAction(ActionEvent actionEvent) {
        // Validates format of password upon typing
    }

    /**
     * This is the Login button
     *
     * @param actionEvent
     */
    public void onLoginAction(ActionEvent actionEvent) {
        System.out.println("You clicked");

        // Calls usernameOnAction to see if field is correctly filled out
        // Calls passwordOnAction to see if field is correctly filled out

        DBUsers user = new DBUsers(username_field_id.getText(), password_field_id.getText());

        if(user.usernameExists()){
            // Set welcome message
            // Set short delay to allow for reading the welcome message
            the_label.setText("Username Good");
        } else {
            // Set alert
            // Highlight username field
            // Highlight password field
            the_label.setText("Username does not exist");
        }

        // If above is okay, makes a call to database upon clicking Login
        // Check to see if username exists
        // Check to see if password matches username
        // Switch to new screen
    }
}
