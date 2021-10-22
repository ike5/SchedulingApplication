package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {


    public TableView table_view_id;
    public TableColumn appointment_id_tablecolumn;
    public TableColumn title_tablecolumn;
    public TableColumn description_tablecolumn;
    public TableColumn location_tablecolumn;
    public TableColumn contact_tablecolumn;
    public TableColumn type_tablecolumn;
    public TableColumn start_date_time_tablecolumn;
    public TableColumn end_date_time_tablecolumn;
    public TableColumn customer_id_tablecolumn;
    public TableColumn user_id_tablecolumn;
    public RadioButton month_view_radio_button;
    public RadioButton week_view_radio_button;
    public RadioButton all_appointments_radio_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ToggleGroup toggleGroup = new ToggleGroup();
        all_appointments_radio_button.setToggleGroup(toggleGroup);
        month_view_radio_button.setToggleGroup(toggleGroup);
        week_view_radio_button.setToggleGroup(toggleGroup);
        all_appointments_radio_button.setSelected(true);

        // Set listener for radio buttons
//        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//            @Override
//            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) {
//
//            }
//        });
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/NewAppointmentController.fxml"));
        stage.setTitle("Hello ");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void monthViewRadioButtonOnAction(ActionEvent actionEvent) {

    }

    public void weekViewRadioButtonOnAction(ActionEvent actionEvent) {
    }

    public void allAppointmentsRadioButtonOnAction(ActionEvent actionEvent) {

    }


}
