package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type View. This class simplifies switching
 * Views within the application.
 */
public class View {
    Stage stage;
    Parent scene;
    String title;

    /**
     * Instantiates a new View.
     *
     * @param actionEvent the action event
     * @param path        the path
     * @param title       the title
     * @throws IOException the io exception
     */
    public View(ActionEvent actionEvent, String path, String title) throws IOException {
        this.stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        this.scene = FXMLLoader.load(getClass().getResource(path));
        this.title = title;
    }

    /**
     * Change.
     */
    public void change() {
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
