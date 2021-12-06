package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.util.Locale;
import java.util.ResourceBundle;

import static data.JDBC.closeConnection;
import static data.JDBC.openConnection;


/**
 * This is the main class where the program launches. This class also opens
 * and closes the connection to the database.
 * <p>
 * Several dependencies throughout the lifecycle of the program are provided
 * as static variables: {@link #resourceBundle} and {@link #user}.
 *
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
        openConnection();
        launch(args);
        closeConnection();
    }
}



