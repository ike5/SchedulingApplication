package controller;

import data.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentSingleton;

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
    public ObservableList<Appointment> appointmentObservableList;
    public ToggleGroup appointmentToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_view_id.setItems(DBAppointment.getAllAppointments());

        appointment_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        title_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        description_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        location_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentLocation"));
        type_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        start_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        end_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        customer_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));
        user_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserId"));
        contact_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("ContactId"));


        table_view_id.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                AppointmentSingleton.getInstance().setAppointment((Appointment) newSelection);
            }
        });
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setTitle("Hello");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void monthViewRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointmentsInMonth());
    }

    public void weekViewRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointmentsInWeek());
    }

    public void allAppointmentsRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointments());
    }


    public void newAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        AppointmentSingleton.getInstance().setAppointment(null);

        // No need to alert user, since it doesn't matter if anything is selected or not.
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        stage.setTitle("New Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void updateAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (table_view_id.getSelectionModel().selectedItemProperty() != null) {
            AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            stage.setTitle("Modify Appointment");
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment");
            alert.setTitle("Nothing selected");
            alert.showAndWait();
        }
    }
}
