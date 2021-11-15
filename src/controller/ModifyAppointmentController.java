package controller;

import data.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used to modify existing appointments as well as create new appointments.
 */
public class ModifyAppointmentController implements Initializable {
    public Label error_message_label;
    public Label error_messages_label2;
    public ComboBox type_combo;
    public ComboBox location_combo;
    public TextField title_textfield;
    public TextField description_textfield;
    public DatePicker start_date_picker;
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
        // Initialize singleton default values
        CustomerListSingleton.getInstance().setCustomerObservableList(DBCustomers.getAllCustomers());
        ContactsListSingleton.getInstance().setContactObservableList(DBContacts.getAllContacts());
        UserListSingleton.getInstance().setUserObservableList(DBUsers.getAllUsers());

        // Set ComboBox to default values
        customer_combo.setItems(CustomerListSingleton.getInstance().getCustomerObservableList());
        contact_combo.setItems(ContactsListSingleton.getInstance().getContactObservableList());
        user_combo.setItems(UserListSingleton.getInstance().getUserObservableList());
        location_combo.setItems(LocationListSingleton.getInstance().getLocationObservableList());
        type_combo.setItems(TypeListSingleton.getInstance().getTypeObservableList());


        // Set start time combobox
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        while (start.isBefore(end.plusSeconds(1))) {
            start_combo.getItems().add(start);
            start = start.plusMinutes(15);
        }
        //FIXME - Fix End combobox allowing conflicting time with first option

        // set end time combobox conditional on start time
        start_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            for (Object o : start_combo.getItems()) {
                if (o.equals(newValue)) {
                    while (((LocalTime) o).isBefore(end.plusSeconds(1))) {
                        end_combo.getItems().add(o);
                        o = ((LocalTime) o).plusMinutes(15);
                    }
                    break;
                }
            }
        });

        // Set datepicker to today
        start_date_picker.setValue(LocalDate.from(ZonedDateTime.now()));


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
        if (isMissingValue()) {
            getAlert(String.valueOf(errorMessage()));
        } else {

            // If user clicked 'New Appointment' inside the AppointmentsController,
            // this option will be executed when Save is clicked.
            if (AppointmentSingleton.getInstance().getAppointment() == null) {
                Optional<ButtonType> result = getAlert("Create new appointment?");
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    DBAppointment.insertAppointment(
                            title_textfield.getText(),
                            description_textfield.getText(),
                            (String) location_combo.getValue(),
                            (String) type_combo.getValue(),
                            LocalDateTime.of(start_date_picker.getValue(), (LocalTime) start_combo.getSelectionModel().getSelectedItem()),
                            LocalDateTime.of(start_date_picker.getValue(), (LocalTime) end_combo.getSelectionModel().getSelectedItem()),
                            ((Customer) customer_combo.getValue()),
                            ((User) user_combo.getValue()),
                            ((Contact) contact_combo.getValue())
                    );

                    toAppointmentsView(actionEvent);
                }
            } else {
                Optional<ButtonType> result = getAlert("Save changes?");
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    DBAppointment.updateAppointment(
                            AppointmentSingleton.getInstance().getAppointment().getAppointmentId(),
                            title_textfield.getText(),
                            description_textfield.getText(),
                            (String) location_combo.getValue(),
                            (String) type_combo.getValue(),
                            LocalDateTime.of(start_date_picker.getValue(), (LocalTime) start_combo.getSelectionModel().getSelectedItem()),
                            LocalDateTime.of(start_date_picker.getValue(), (LocalTime) end_combo.getSelectionModel().getSelectedItem()),
                            ((Customer) customer_combo.getValue()),
                            ((User) user_combo.getValue()),
                            ((Contact) contact_combo.getValue())
                    );

                    toAppointmentsView(actionEvent);
                }
            }
        }
    }

    private void toAppointmentsView(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private Optional<ButtonType> getAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, s);
        alert.setTitle("Confirm");
        return alert.showAndWait();
    }

    private boolean isMissingValue() {
        boolean isEmpty = false;

        if (customer_combo.getSelectionModel().isEmpty() ||
                contact_combo.getSelectionModel().isEmpty() ||
                user_combo.getSelectionModel().isEmpty() ||
                location_combo.getSelectionModel().isEmpty() ||
                type_combo.getSelectionModel().isEmpty() ||
                title_textfield.getText().isBlank() ||
                description_textfield.getText().isBlank() ||
                start_combo.getSelectionModel().isEmpty() ||
                end_combo.getSelectionModel().isEmpty()) {
            isEmpty = true;
        }
        return isEmpty;
    }

    private StringBuilder errorMessage() {
        StringBuilder errorMessage = new StringBuilder();

        if (customer_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select a customer\n");
        } else if (contact_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select a contact\n");
        } else if (user_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select a user\n");
        } else if (location_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select a location\n");
        } else if (type_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select and appointment type\n");
        } else if (title_textfield.getText().isBlank()) {
            errorMessage.append("Set a title\n");
        } else if (description_textfield.getText().isBlank()) {
            errorMessage.append("Set a description\n");
        } else if (start_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select a start time\n");
        } else if (end_combo.getSelectionModel().isEmpty()) {
            errorMessage.append("Select an end time\n");
        } else {
            System.out.println("No errors");
        }
        return errorMessage;
    }
}
