package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static data.JDBC.closeConnection;
import static data.JDBC.openConnection;

public class Main extends Application {
    public static ResourceBundle resourceBundle;

    @Override
    public void start(Stage stage) throws Exception {
        resourceBundle = ResourceBundle.getBundle("RBundle", Locale.getDefault()); // initialize ResourceBundle for application

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml")); // slash represents the src folder
        stage.setTitle(resourceBundle.getString("set_title"));
        stage.setScene(new Scene(root, 400, 300)); // (width, height)
        stage.show();
    }

    public static void main(String[] args) {
//        Locale.setDefault(new Locale("fr", "CA")); // Test to set default to French

        openConnection();
        launch(args);
        closeConnection();
    }


}



