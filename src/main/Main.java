package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import data.JDBC;
import model.DatabaseState;

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
        DatabaseState.setDatabaseState(false); // Use remote database by setting flag to false

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
//Test

// Use the below code if having font issues
// root.setStyle("-fx-font-family: 'Times New Roman';");

// Use the below code if having font issues with alerts
// Alert alert = new Alert(Alert.AlertType.WARNING);
// alert.getDialogPane().getScene().getRoot().setStyle("-fx-font-family: 'Times New Roman';");

