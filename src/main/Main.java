package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.JDBC;
import model.DatabaseState;

import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml")); // slash represents the src folder
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 400, 400)); // (width, height)
        stage.show();

        //FIXME
        // - Set appropriate width and height for LoginScreen.fxml
        // - Set appropriate title
    }

    public static void main(String[] args) {
        DatabaseState.setDatabaseState(true); // Use remote database by setting flag to false
//        Locale.setDefault(new Locale("fr")); // Test to set default to French

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }


}



