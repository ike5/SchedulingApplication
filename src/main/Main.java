package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.JDBC;

import static data.DBConnection.*;

//public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml")); // slash represents the src folder
//        stage.setTitle("Customers Screen");
//        stage.setScene(new Scene(root, 1200, 500)); // (width, height)
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        startConnection();
//        launch(args);
//        closeConnection();
//    }

public class Main {
    public static void main(String[] args) {
        JDBC.openConnection();
    }
}
//Test

// Use the below code if having font issues
// root.setStyle("-fx-font-family: 'Times New Roman';");

// Use the below code if having font issues with alerts
// Alert alert = new Alert(Alert.AlertType.WARNING);
// alert.getDialogPane().getScene().getRoot().setStyle("-fx-font-family: 'Times New Roman';");

