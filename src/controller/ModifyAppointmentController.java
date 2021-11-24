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

import java.time.*;
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

        // Set end time combobox to begin no earlier than the start time
        start_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            // Find the time selected in start_combo
            for (Object startComboItems : start_combo.getItems()) {
                if (startComboItems.equals(newValue)) {
                    while (((LocalTime) startComboItems).isBefore(end.plusSeconds(1))) {
                        startComboItems = ((LocalTime) startComboItems).plusMinutes(15);
                        end_combo.getItems().add(startComboItems);
                    }
                    break;
                }
            }
        });

        // Set datepicker to today
        start_date_picker.setValue(LocalDate.from(ZonedDateTime.now()));


        // If coming to view from Updating appointments, populate fields and combo
        if (AppointmentSingleton.getInstance().getAppointment() != null) {
            // Populate ComboBoxes
            for (Object customer : customer_combo.getItems()) {
                if (((Customer) customer).getId() == AppointmentSingleton.getInstance().getAppointment().getCustomerId()) {
                    customer_combo.setValue(customer);
                }
            }
            for (Object contact : contact_combo.getItems()) {
                if (((Contact) contact).getContactId() == AppointmentSingleton.getInstance().getAppointment().getContactId()) {
                    contact_combo.setValue(contact);
                }
            }
            for (Object user : user_combo.getItems()) {
                if (((User) user).getUserId() == AppointmentSingleton.getInstance().getAppointment().getUserId()) {
                    user_combo.setValue(user);
                }
            }
            for (Object location : location_combo.getItems()) {
                if (location.equals(AppointmentSingleton.getInstance().getAppointment().getAppointmentLocation())) {
                    location_combo.setValue(location);
                }
            }
            for (Object type : type_combo.getItems()) {
                if (type.equals(AppointmentSingleton.getInstance().getAppointment().getAppointmentType())) {
                    type_combo.setValue(type);
                }
            }

            // Set number of visible rows in ComboBoxes
            customer_combo.setVisibleRowCount(5);
            contact_combo.setVisibleRowCount(5);
            user_combo.setVisibleRowCount(5);
            location_combo.setVisibleRowCount(5);
            type_combo.setVisibleRowCount(5);

            // Populate TextFields
            appointment_id_textfield.setText(Integer.toString(AppointmentSingleton.getInstance().getAppointment().getAppointmentId()));
            title_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentTitle());
            description_textfield.setText(AppointmentSingleton.getInstance().getAppointment().getAppointmentDescription());

            LocalDate localStartDate = AppointmentSingleton.getInstance().getAppointment().getStart().toLocalDate();
            LocalTime localStartTime = AppointmentSingleton.getInstance().getAppointment().getStart().toLocalTime();
            LocalTime localEndTime = AppointmentSingleton.getInstance().getAppointment().getEnd().toLocalTime();

            //Todo - do I need to convert to zonedDateTime to use properly in EST?
            ZonedDateTime zonedStartDateTime = ZonedDateTime.of(localStartDate, localStartTime, ZoneId.systemDefault());
            zonedStartDateTime.withZoneSameInstant(ZoneId.systemDefault());

            start_date_picker.setValue(localStartDate);
            start_combo.setValue(localStartTime);
            end_combo.setValue(localEndTime);
        }
        //TODO Validate whether appointment times overlap, start earlier, or etc.
    }

    /**
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button
     *
     * @param actionEvent
     */
    public void clearButtonOnAction(ActionEvent actionEvent) {
        onClear(actionEvent);
    }

    /**
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (isMissingValue()) {
            Messages.confirmationMessage(String.valueOf(errorMessage()), "Missing value");
        } else {

            // If user clicked 'New Appointment' inside the AppointmentsController,
            // this option will be executed when Save is clicked.
            if (AppointmentSingleton.getInstance().getAppointment() == null) {
                Optional<ButtonType> result = Messages.confirmationMessage("Create new appointment?", "Confirm");
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

                    switchView(actionEvent, "/view/Appointments.fxml", "Appointments");
                }
            } else {
                Optional<ButtonType> result = Messages.confirmationMessage("Save changes?", "Confirm");
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

                    switchView(actionEvent, "/view/Appointments.fxml", "Appointments");
                }
            }
        }
    }

    /**
     * Helper
     *
     * @param actionEvent
     */
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

    /**
     * Helper
     *
     * @param actionEvent
     * @param path
     * @param title
     * @throws IOException
     */
    private void switchView(ActionEvent actionEvent, String path, String title) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Validation
     *
     * @return
     */
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

    /**
     * Validation
     *
     * @return
     */
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
