package controller;

import data.DBAppointment;
import data.DBDivisions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import test.Test;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
//        final ToggleGroup toggleGroup = new ToggleGroup();
//        all_appointments_radio_button.setToggleGroup(toggleGroup);
//        month_view_radio_button.setToggleGroup(toggleGroup);
//        week_view_radio_button.setToggleGroup(toggleGroup);
//        all_appointments_radio_button.setSelected(true);

        appointmentObservableList = DBAppointment.getAllAppointments();
        table_view_id.setItems(appointmentObservableList);

        appointment_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        title_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        description_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        location_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentLocation"));
        type_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        start_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        end_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        customer_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));
        user_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserId"));
        contact_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("ContactId"));


        // TableView listener
        table_view_id.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Appointment appointment = (Appointment) newSelection;
                AppointmentSingleton.getInstance().setAppointment(appointment);
                new Test("Created Appointment Singleton");
            }
        });

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
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setTitle("Hello");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void monthViewRadioButtonOnAction(ActionEvent actionEvent) {
        appointmentObservableList = DBAppointment.getAllAppointmentsInMonth();
        table_view_id.setItems(appointmentObservableList);
    }

    public void weekViewRadioButtonOnAction(ActionEvent actionEvent) {
        appointmentObservableList = DBAppointment.getAllAppointmentsInWeek();
        table_view_id.setItems(appointmentObservableList);
    }

    public void allAppointmentsRadioButtonOnAction(ActionEvent actionEvent) {
        appointmentObservableList = DBAppointment.getAllAppointments();
        table_view_id.setItems(appointmentObservableList);
    }


    public void newAppointmentButtonOnAction(ActionEvent actionEvent) {
    }

    public void updateAppointmentButtonOnAction(ActionEvent actionEvent) {
    }
}
