package controller;

import javafx.scene.control.Alert;
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
    private Stage stage;
    private Parent scene;
    public Label username_id;
    public Label password_id;
    public TextField username_field_id;
    public TextField password_field_id;
    public Button login_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Test("Login screen initialized!");

        ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());
        language_zone_id.setText(rb.getString("zoneid"));
        welcome_message.setText(rb.getString("welcomemessage"));
        username_id.setText(rb.getString("usernameid"));
        password_id.setText(rb.getString("passwordid"));
        login_id.setText(rb.getString("loginbutton"));
        username_field_id.setText(rb.getString("usernamefieldid"));
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
        ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());
        DBUsers userLogin = new DBUsers(username_field_id.getText(), password_field_id.getText());

        if (userLogin.userExists()) {
            new Test("User exists");
            if (userLogin.passwordMatches()) {
                new Test();

                //FIXME Make event listeners on ENTER correctly call methods

                // Get event source from button
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                // Load resources from view directory
                scene = (Parent) FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrectpassword"));
                alert.setTitle(rb.getString("passwordalerttitle"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("incorrectusername"));
            alert.setTitle(rb.getString("usernamealerttitle"));
            alert.showAndWait();
        }
    }
}
