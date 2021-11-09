package controller;

import data.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
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
        CustomerListSingleton.getInstance().setCustomerObservableList(DBCustomers.getAllCustomers());
        customer_combo.setItems(CustomerListSingleton.getInstance().getCustomerObservableList());

        ContactsListSingleton.getInstance().setContactObservableList(DBContacts.getAllContacts());
        contact_combo.setItems(ContactsListSingleton.getInstance().getContactObservableList());

        UserListSingleton.getInstance().setUserObservableList(DBUsers.getAllUsers());
        user_combo.setItems(UserListSingleton.getInstance().getUserObservableList());

        location_combo.setItems(LocationListSingleton.getInstance().getLocationObservableList());

        type_combo.setItems(TypeListSingleton.getInstance().getTypeObservableList());


        start_combo.setItems(PossibleTimes.localTimeList());
        end_combo.setItems(PossibleTimes.localTimeList());

        //FIXME - Location and Type combos don't pull info on update

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

            type_combo.setValue(AppointmentSingleton.getInstance().getAppointment().getAppointmentType());
            type_combo.setVisibleRowCount(5);

            appointment_id_textfield.setText(Integer.toString(AppointmentSingleton.getInstance().getAppointment().getAppointmentId()));
            title_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentTitle());
            description_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentDescription());

            LocalDate localStartDate = AppointmentSingleton.getInstance().getAppointment().getStart().toLocalDate();
            start_date_picker.setValue(localStartDate);

            LocalDate localEndDate = AppointmentSingleton.getInstance().getAppointment().getEnd().toLocalDate();
            end_date_picker.setValue(localEndDate);

            LocalTime localStartTime = AppointmentSingleton.getInstance().getAppointment().getStart().toLocalTime();
            start_combo.setValue(localStartTime);

            LocalTime localEndTime = AppointmentSingleton.getInstance().getAppointment().getEnd().toLocalTime();
            end_combo.setValue(localEndTime);
        }


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
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void clearButtonOnAction(ActionEvent actionEvent) {
        onClear(actionEvent);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (AppointmentSingleton.getInstance().getAppointment() == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Create new appointment?");
            alert.setTitle("New Appointment");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.insertAppointment(
                        title_textfield.getText(),
                        description_textfield.getText(),
                        (String) location_combo.getValue(),
                        (String) type_combo.getValue(),
                        LocalDateTime.of(start_date_picker.getValue(), (LocalTime) start_combo.getSelectionModel().getSelectedItem()),
                        LocalDateTime.of(end_date_picker.getValue(), (LocalTime) end_combo.getSelectionModel().getSelectedItem()),
                        ((Customer) customer_combo.getValue()),
                        ((User) user_combo.getValue()),
                        ((Contact) contact_combo.getValue())
                );

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage.setTitle(null);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save changes?");
            alert.setTitle("Update Appointment");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.updateAppointment(
                        AppointmentSingleton.getInstance().getAppointment().getAppointmentId(),
                        title_textfield.getText(),
                        description_textfield.getText(),
                        (String) location_combo.getValue(),
                        (String) type_combo.getValue(),
                        LocalDateTime.of(start_date_picker.getValue(), (LocalTime) start_combo.getSelectionModel().getSelectedItem()),
                        LocalDateTime.of(end_date_picker.getValue(), (LocalTime) end_combo.getSelectionModel().getSelectedItem()),
                        ((Customer) customer_combo.getValue()),
                        ((User) user_combo.getValue()),
                        ((Contact) contact_combo.getValue())
                );

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage.setTitle(null);
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
}
