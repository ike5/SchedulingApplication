package controller;

import data.DBCountries;
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
    }

    public void passwordOnAction(ActionEvent actionEvent) {
    }

    public void onLoginAction(ActionEvent actionEvent) {
        System.out.println("You clicked");
        the_label.setText("You clicked Login!");

        ObservableList<Country> countrieslist = DBCountries.getAllCountries();
        for(Country C : countrieslist){
            System.out.println("Country ID: " + C.getId() + "\tCountry Name: " + C.getName());
        }
    }
}
