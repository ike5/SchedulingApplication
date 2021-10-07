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
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("RBundle", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml")); // slash represents the src folder
        stage.setTitle(rb.getString("set_title"));
        stage.setScene(new Scene(root, 400, 300)); // (width, height)
        stage.show();

        //FIXME
        // - Set appropriate width and height for LoginScreen.fxml
    }

    public static void main(String[] args) {
//        Locale.setDefault(new Locale("fr", "CA")); // Test to set default to French

        openConnection();
        launch(args);
        closeConnection();
    }


}



