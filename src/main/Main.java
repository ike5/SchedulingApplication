package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import test.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static data.JDBC.closeConnection;
import static data.JDBC.openConnection;


/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class Main extends Application {
    public static ResourceBundle resourceBundle;
    public static User user; // Establish the User using the application

    @Override
    public void start(Stage stage) throws Exception {
        resourceBundle = ResourceBundle.getBundle("RBundle", Locale.getDefault());

        Parent root = FXMLLoader.load(getClass().getResource(resourceBundle.getString("login_screen")));
        stage.setTitle(resourceBundle.getString("set_title"));
        stage.setScene(new Scene(root, 400, 300)); // (width, height)
        stage.show();
    }

    public static void main(String[] args) {
//        Test.changeLocale();  // Uncomment this line to change default locale to French Canadian

        openConnection();
        launch(args);
        closeConnection();
    }
}



