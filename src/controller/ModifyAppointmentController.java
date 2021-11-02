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
import javafx.util.converter.DateTimeStringConverter;
import model.*;
import test.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Formatter;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {


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
    public TextField appointment_id_textfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerSingleton.getInstance().setCustomerObservableList(DBCustomers.getAllCustomers());
        customer_combo.setItems(CustomerSingleton.getInstance().getCustomerObservableList());

        ContactsSingleton.getInstance().setContactObservableList(DBContacts.getAllContacts());
        contact_combo.setItems(ContactsSingleton.getInstance().getContactObservableList());

        UserSingleton.getInstance().setUserObservableList(DBUsers.getAllUsers());
        user_combo.setItems(UserSingleton.getInstance().getUserObservableList());

        ObservableList<Location> locationObservableList = FXCollections.observableArrayList(Location.values());
        location_combo.setItems(locationObservableList);

        ObservableList<Type> typeObservableList = FXCollections.observableArrayList(Type.values());
        type_combo.setItems(typeObservableList);

//        start_time_spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(PossibleTimes.localTimeList()));
//        end_time_spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(PossibleTimes.localTimeList()));


        DateTimeStringConverter dateTimeStringConverter = new DateTimeStringConverter(DateFormat.SHORT, DateFormat.SHORT);


        if(AppointmentSingleton.getInstance().getAppointment() != null){
            customer_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getCustomer());
            customer_combo.setVisibleRowCount(5);

            contact_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getContact());
            contact_combo.setVisibleRowCount(5);

            user_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getUser());
            user_combo.setVisibleRowCount(5);

            location_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getAppointmentLocation());
            location_combo.setVisibleRowCount(5);

            type_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getType());
            type_combo.setVisibleRowCount(5);

            appointment_id_textfield.setText(Integer.toString(AppointmentSingleton.getInstance().getAppointment().getAppointmentId()));
            title_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentTitle());
            description_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentDescription());

            start_date_picker.setValue(AppointmentSingleton.getInstance().getAppointment().getStart().toLocalDate());
            end_date_picker.setValue(AppointmentSingleton.getInstance().getAppointment().getEnd().toLocalDate());


        }


        //TODO
        // include modification.
        // - set listeners to the comboboxes

    }

    //FIXME - still can't figure out why when clicking clear it doesn't turn all the comboboxes to empty. Maybe I need
    // to use a listener or a callback?
    private void onClear(ActionEvent actionEvent){
        customer_combo.valueProperty().set(null);
        contact_combo.valueProperty().set(null);
        user_combo.valueProperty().set(null);
        location_combo.getSelectionModel().clearAndSelect(0);
        type_combo.getSelectionModel().clearAndSelect(0);
        appointment_id_textfield.clear();
        title_textfield.clear();
        description_textfield.clear();
        new Test("onClear() called");
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
        new Test("cancelButtonOnAction() called");
    }

    public void clearButtonOnAction(ActionEvent actionEvent) {
        onClear(actionEvent);
        new Test("clearbuttonOnAction() called");
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        new Test("saveButtonOnAction() called");
    }
}
