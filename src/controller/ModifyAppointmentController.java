package controller;

import data.*;
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
import test.Test;

import java.io.IOException;
import java.net.URL;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public TextField appointment_id_textfield;
    public ComboBox start_combo;
    public ComboBox end_combo;

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


        start_combo.setItems(PossibleTimes.localTimeList());
        end_combo.setItems(PossibleTimes.localTimeList());


        // If coming to view from Updating appointments, populate fields and combo
        if (AppointmentSingleton.getInstance().getAppointment() != null) {
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

            LocalTime localStartTime = AppointmentSingleton.getInstance().getAppointment().getStart().toLocalTime();
            start_combo.setValue(localStartTime);

            LocalTime localEndTime = AppointmentSingleton.getInstance().getAppointment().getEnd().toLocalTime();
            end_combo.setValue(localEndTime);
        }


        //TODO
        // include modification.
        // - set listeners to the comboboxes

    }

    //FIXME - still can't figure out why when clicking clear it doesn't turn all the comboboxes to empty. Maybe I need
    // to use a listener or a callback?
    private void onClear(ActionEvent actionEvent) {
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

    //AppointmentID, CustomerID, ContactID, UserID, StartDate, EndDate, Title, Description, Location, Type, StartTime, EndTime
    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (AppointmentSingleton.getInstance().getAppointment() == null) {
            DBAppointment.insertAppointment(
                    title_textfield.getText(),
                    description_textfield.getText(),
                    ((Location) location_combo.getValue()),
                    ((Type) type_combo.getValue()),
                    (LocalDateTime) start_combo.getValue(),
                    ((LocalDateTime) end_combo.getValue()),
                    ((Customer) customer_combo.getValue()),
                    ((User) user_combo.getValue()),
                    ((Contact) contact_combo.getValue())
            );
        } else {
            DBAppointment.updateAppointment();// add logic to update
        }
        new Test("saveButtonOnAction() called");
    }
}
