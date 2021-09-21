package controller;

import data.DBCountries;
import data.DBUsers;
import javafx.collections.ObservableList;
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
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// controllers need to implement the Initializable interface
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
    }

    @FXML
    public void usernameOnAction(ActionEvent actionEvent) throws IOException {
        onLoginAction(actionEvent);
    }

    @FXML
    public void passwordOnAction(ActionEvent actionEvent) throws IOException {
        onLoginAction(actionEvent);
    }


    @FXML
    public void onLoginAction(ActionEvent actionEvent) throws IOException {
        System.out.println("You clicked");

        // Calls usernameOnAction to see if field is correctly filled out
        // Calls passwordOnAction to see if field is correctly filled out

        DBUsers userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());

        if (userLogin.userExists()) {
            System.out.println("User Exists"); // TEST
            if (userLogin.passwordMatches()) {
                System.out.println("Password matches"); // TEST
                // Switch screens
                // Get event source from button
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                // Load resources from view directory
                scene = (Parent) FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else {
            // Alert user
            System.out.println("No user by that name");
        }
    }
}
