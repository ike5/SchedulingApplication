package controller;

import data.DBContacts;
import data.DBCustomers;
import data.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {


    public Label appointment_id_label;
    public Label error_message_label;
    public Label error_messages_label2;
    public ComboBox type_combo;
    public ComboBox location_combo;
    public TextField title_textfield;
    public TextField description_textfield;
    public DatePicker start_date_picker;
    public ComboBox start_time_combo;
    public DatePicker end_date_picker;
    public ComboBox end_time_combo;
    public ComboBox customer_combo;
    public ComboBox contact_combo;
    public ComboBox user_combo;
    public Button cancel_button;
    public Button clear_button;
    public Button save_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment appointment = AppointmentSingleton.getInstance().getAppointment();

        appointment_id_label.setText(Integer.toString(appointment.getAppointmentId()));

        ObservableList<Customer> customerObservableList = DBCustomers.getAllCustomers();
        customer_combo.setItems(customerObservableList);
        customer_combo.setValue(appointment.getCustomer());

        ObservableList<Contact> contactObservableList = DBContacts.getAllContacts();
        contact_combo.setItems(contactObservableList);
        contact_combo.setValue(appointment.getContact());

//        ObservableList<User> userObservableList = DBUsers.getUser()


        
    }

}
