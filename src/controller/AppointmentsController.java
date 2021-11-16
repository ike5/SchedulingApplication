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
import java.util.Optional;
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
    public Button delete_button;
    public Button update_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_view_id.setItems(DBAppointment.getAllAppointments());

        appointment_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        title_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        description_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        location_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentLocation"));
        contact_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("ContactName"));
        type_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        start_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        end_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        customer_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));
        user_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserId"));


        table_view_id.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Enable buttons if row selected
                delete_button.setDisable(false);
                update_button.setDisable(false);
                AppointmentSingleton.getInstance().setAppointment((Appointment) newSelection);
            }
        });

        // Disable buttons if no row selected
        delete_button.setDisable(table_view_id.getSelectionModel().isEmpty());
        update_button.setDisable(table_view_id.getSelectionModel().isEmpty());
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

    //FIXME When updating appointment, the fields are thought of as blank for some reason. Issus happens
    // if immediately try to save without changing anything.
    public void updateAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (table_view_id.getSelectionModel().selectedItemProperty() != null) {
            // Use a Singleton to act as an '@ObservableObject'
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

    public void deleteAppointmentButtonOnAction(ActionEvent actionEvent) {
        AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        alert.setTitle("Delete appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.deleteAppointment(AppointmentSingleton.getInstance().getAppointment().getAppointmentId());
        }
        delete_button.setDisable(true);
        table_view_id.setItems(DBAppointment.getAllAppointments());
    }
}
