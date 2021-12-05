package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class View {
    Stage stage;
    Parent scene;
    String title;

    public View(ActionEvent actionEvent, String path, String title) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = FXMLLoader.load(getClass().getResource(path));
        this.title = title;
    }

    public void change(){
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
