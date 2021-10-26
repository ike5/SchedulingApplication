package controller;

import data.DBContacts;
import data.DBCustomers;
import data.DBDivisions;
import data.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {


    public Label appointment_id_label;
    public Label error_message_label;
    public Label error_messages_label2;
    public ComboBox type_combo;
    public ComboBox location_combo;
    public TextField title_textfield;
    public TextField description_textfield;
    public DatePicker start_date_picker;
    public DatePicker end_date_picker;
    public ComboBox customer_combo;
    public ComboBox contact_combo;
    public ComboBox user_combo;
    public Button cancel_button;
    public Button clear_button;
    public Button save_button;
    public Spinner start_time_spinner;
    public Spinner end_time_spinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment appointment = AppointmentSingleton.getInstance().getAppointment();

        ObservableList<Customer> customerObservableList = DBCustomers.getAllCustomers();
        customer_combo.setItems(customerObservableList);

        ObservableList<Contact> contactObservableList = DBContacts.getAllContacts();
        contact_combo.setItems(contactObservableList);

        ObservableList<User> userObservableList = DBUsers.getAllUsers();
        user_combo.setItems(userObservableList);

        ObservableList<Location> locationObservableList = FXCollections.observableArrayList(Location.values());
        location_combo.setItems(locationObservableList);

        ObservableList<Type> typeObservableList = FXCollections.observableArrayList(Type.values());
        type_combo.setItems(typeObservableList);

        start_time_spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(PossibleTimes.localTimeList()));
        end_time_spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(PossibleTimes.localTimeList()));



        if(appointment != null){
            customer_combo.setValue(appointment.getCustomer());
            contact_combo.setValue(appointment.getContact());
            user_combo.setValue(appointment.getUser());
            location_combo.setValue(appointment.getAppointmentLocation());
            type_combo.setValue(appointment.getType());
            appointment_id_label.setText(Integer.toString(appointment.getAppointmentId()));
            title_textfield.setText(appointment.getAppointmentTitle());
            description_textfield.setText(appointment.getAppointmentDescription());
        }


        //TODO
        // include modification.
        // - set listeners to the comboboxes

    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void clearButtonOnAction(ActionEvent actionEvent) {
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
    }
}
