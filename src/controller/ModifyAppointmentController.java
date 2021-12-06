package controller;

import data.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.*;
import utils.ChangeViewInterface;
import utils.ControllerViewChanger;

import java.io.IOException;
import java.net.URL;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used to modify existing appointments and create new appointments.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class ModifyAppointmentController implements Initializable, ChangeViewInterface {
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

    /**
     * Initializes ComboBoxes and DatePickers. If an Appointment is
     * being modified, the form TextFields are pre-populated with
     * info from the AppointmentSingleton. The LocalTime ComboBoxes
     * are set to ZonedDateTime here, representing the machine's
     * local time, but all appointments options in the ComboBoxes
     * can only be selected from EST.
     *
     * @param url
     * @param resourceBundle
     */
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

        // Set datepicker to today
        LocalDate localDate = LocalDate.from(ZonedDateTime.now());
        start_date_picker.setValue(localDate);

        // Restrict appointment hours
        LocalTime startTime = LocalTime.of(8, 0); // 8:00AM
        LocalTime endTime = LocalTime.of(21, 45); // 11:45, but last appointment is +15 min so, 12:00AM

        // Set ZoneId for office to EST
        ZoneId zoneIdEST = ZoneId.of("America/New_York");

        // Ensure ZonedDateTime is EST to begin with
        ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDate, startTime, zoneIdEST);
        ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDate, endTime, zoneIdEST);

        // Convert ZonedDateTime to SystemDefault
        ZonedDateTime zonedDateTimeStartEST = ZonedDateTime.ofInstant(zonedDateTimeStart.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEndEST = ZonedDateTime.ofInstant(zonedDateTimeEnd.toInstant(), ZoneId.systemDefault());

        // Get times for systemDefault
        LocalTime start = zonedDateTimeStartEST.toLocalTime();
        LocalTime end = zonedDateTimeEndEST.toLocalTime();

        // Populate the Start time ComboBox
        while (start.isBefore(end.plusSeconds(1))) {
            start_combo.getItems().add(start);
            start = start.plusMinutes(15);
        }

        // Take a user selection and set the End time ComboBox to begin no earlier than the Start time ComboBox
        start_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            // Find the time selected in start_combo
            for (Object startComboItems : start_combo.getItems()) {
                if (startComboItems.equals(newValue)) {

                    // Clear all end times
                    end_combo.getItems().clear();

                    // Populate all end times that start after selected start time
                    while (((LocalTime) startComboItems).isBefore(end.plusSeconds(1))) {
                        startComboItems = ((LocalTime) startComboItems).plusMinutes(15);
                        end_combo.getItems().add(startComboItems);
                    }
                    break;
                }
            }
        });


        // If coming to view from Updating appointments, populate fields and combo
        if (AppointmentSingleton.getInstance().getAppointment() != null) {
            populateComboBoxes(AppointmentSingleton.getInstance().getAppointment());

            setNumberOfVisibleRows(5);

            populateTextFields(AppointmentSingleton.getInstance().getAppointment());

            setDatePickerAndTimeCombos(AppointmentSingleton.getInstance().getAppointment());
        }
    }

    /**
     * Button to go back to the Appointments View.
     *
     * @param actionEvent Back Button pressed
     * @throws IOException
     */
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        changeView(view -> view.change(), new View(
                actionEvent, Main.resourceBundle.getString("appointments_screen"), "Appointments"
        ));
    }

    /**
     * Helper method to change views
     *
     * @param controllerViewChanger a ControllerViewChanger interface
     * @param view                  a View object
     */
    @Override
    public void changeView(ControllerViewChanger controllerViewChanger, View view) {
        controllerViewChanger.switchView(view);
    }

    /**
     * Button to clear all TextFields
     *
     * @param actionEvent Clear Button pressed
     */
    public void clearButtonOnAction(ActionEvent actionEvent) {
        onClear(actionEvent);
    }

    /**
     * Button to save a new or updated appointment.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        Appointment appointment = AppointmentSingleton.getInstance().getAppointment();

        if (isMissingValue()) {
            Message.confirmationMessage(String.valueOf(errorMessage()), "Missing value");
        } else if (isWeekend()) {
            Message.errorMessage("Cannot schedule outside of business hours", "Schedule Error");
        } else {
            if (appointment == null) { // If user clicked 'New Appointment' from AppointmentsController,
                if (isOverlapping(null)) {
                    Message.errorMessage("Appointment overlaps", "Schedule Error");
                } else {
                    Optional<ButtonType> result = Message.confirmationMessage("Create new appointment?", "Confirm");
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

                        changeView(view -> view.change(), new View(
                                actionEvent, Main.resourceBundle.getString("appointments_screen"), "Appointments"
                        ));
                    }
                }
            } else {
                if (isOverlapping(appointment)) {
                    Message.errorMessage("Appointment overlaps", "Schedule Error");
                } else {
                    Optional<ButtonType> result = Message.confirmationMessage("Save changes?", "Confirm");
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

                        changeView(view -> view.change(), new View(
                                actionEvent, Main.resourceBundle.getString("appointments_screen"), "Appointments"
                        ));
                    }
                }
            }
        }
    }

    /**
     * Helper method to pre-populate ComboBoxes when user is
     * updating an appointment.
     *
     * @param appointment The Appointment object being updated
     */
    private void populateComboBoxes(Appointment appointment) {
        for (Object customer : customer_combo.getItems()) {
            if (((Customer) customer).getId() == appointment.getCustomerId()) {
                customer_combo.setValue(customer);
            }
        }
        for (Object contact : contact_combo.getItems()) {
            if (((Contact) contact).getContactId() == appointment.getContactId()) {
                contact_combo.setValue(contact);
            }
        }
        for (Object user : user_combo.getItems()) {
            if (((User) user).getUserId() == appointment.getUserId()) {
                user_combo.setValue(user);
            }
        }
        for (Object location : location_combo.getItems()) {
            if (location.equals(appointment.getAppointmentLocation())) {
                location_combo.setValue(location);
            }
        }
        for (Object type : type_combo.getItems()) {
            if (type.equals(appointment.getAppointmentType())) {
                type_combo.setValue(type);
            }
        }
    }

    /**
     * Helper method to pre-populate TextFields when user is
     * updating an Appointment object.
     *
     * @param appointment The Appointment object being updated
     */
    private void populateTextFields(Appointment appointment) {
        // Populate TextFields
        appointment_id_textfield.setText(Integer.toString(appointment.getAppointmentId()));
        title_textfield.setText(appointment.getAppointmentTitle());
        description_textfield.setText(appointment.getAppointmentDescription());
    }

    /**
     * Sets number of visible rows on all ComboBoxes
     *
     * @param numberOfVisibleRows int value number of visible rows
     */
    private void setNumberOfVisibleRows(int numberOfVisibleRows) {
        // Set number of visible rows in ComboBoxes
        customer_combo.setVisibleRowCount(numberOfVisibleRows);
        contact_combo.setVisibleRowCount(numberOfVisibleRows);
        user_combo.setVisibleRowCount(numberOfVisibleRows);
        location_combo.setVisibleRowCount(numberOfVisibleRows);
        type_combo.setVisibleRowCount(numberOfVisibleRows);
    }

    /**
     * Pre-populates DatePicker, Start, and End ComboBoxes when
     * updating an Appointment.
     *
     * @param appointment Appointment object being updated
     */
    private void setDatePickerAndTimeCombos(Appointment appointment) {
        start_date_picker.setValue(appointment.getStart().toLocalDate());
        start_combo.setValue(appointment.getStart().toLocalTime());
        end_combo.setValue(appointment.getEnd().toLocalTime());
    }


    /**
     * Helper method that checks whether the appointment starts
     * on a Saturday or Sunday.
     *
     * @return
     */
    private boolean isWeekend() {
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(
                start_date_picker.getValue(),
                (LocalTime) start_combo.getSelectionModel().getSelectedItem(),
                ZoneId.systemDefault()
        );
        return zonedStartDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                zonedStartDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }


    /**
     * Helper method that checks whether the provided appointment
     * overlaps with any existing appointments with that same
     * customer. If there is no appointment ID associated with the
     * Appointment parameter value, then it is assumed that the
     * user is creating a new appointment.
     *
     * @param appointment Appointment object used to determine if
     *                    there is an appointment ID.
     * @return
     */
    private boolean isOverlapping(Appointment appointment) {
        boolean overlap = false;

        // Get all appointments with same customer
        int customerId = ((Customer) customer_combo.getSelectionModel().getSelectedItem()).getId();
        List<Appointment> appointmentList = DBAppointment.getAllAppointmentsByCustomerId(customerId);

        LocalTime startTime = ((LocalTime) start_combo.getSelectionModel().getSelectedItem());
        LocalTime endTime = ((LocalTime) end_combo.getSelectionModel().getSelectedItem());

        LocalDateTime startDateTime = LocalDateTime.of(start_date_picker.getValue(), startTime);
        LocalDateTime endDateTime = LocalDateTime.of(start_date_picker.getValue(), endTime);


        if (appointment != null) { // Existing appointment being modified
            int appointmentId = appointment.getAppointmentId();

            for (Appointment b : appointmentList) {
                // Need to check whether same appointment exists in database, then ignore it
                if (b.getAppointmentId() != appointmentId) {
                    boolean isStartInWindow = (startDateTime.isAfter(b.getStart()) || startDateTime.equals(b.getStart())) && startDateTime.isBefore(b.getEnd());
                    boolean isEndInWindow = endDateTime.isAfter(b.getStart()) && (endDateTime.isBefore(b.getEnd()) || endDateTime.isEqual(b.getEnd()));
                    boolean isStartEndOutsideWindow = (startDateTime.isBefore(b.getStart()) || startDateTime.isEqual(b.getStart())) && (endDateTime.isAfter(b.getEnd()) || endDateTime.isEqual(b.getEnd()));

                    if (isStartInWindow || isEndInWindow || isStartEndOutsideWindow) {
                        overlap = true;
                        break;
                    }
                }
            }
        } else { // User is creating a new appointment

            // No need to check whether same appointment exists in database
            for (Appointment b : appointmentList) {
                boolean isStartInWindow = (startDateTime.isAfter(b.getStart()) || startDateTime.equals(b.getStart())) && startDateTime.isBefore(b.getEnd());
                boolean isEndInWindow = endDateTime.isAfter(b.getStart()) && (endDateTime.isBefore(b.getEnd()) || endDateTime.isEqual(b.getEnd()));
                boolean isStartEndOutsideWindow = (startDateTime.isBefore(b.getStart()) || startDateTime.isEqual(b.getStart())) && (endDateTime.isAfter(b.getEnd()) || endDateTime.isEqual(b.getEnd()));

                if (isStartInWindow || isEndInWindow || isStartEndOutsideWindow) {
                    overlap = true;
                    break;
                }
            }
        }

        return overlap;
    }

    /**
     * Helper method to reset all TextFields and ComboBoxes
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
     * Helper method to change Views
     *
     * @param actionEvent The ActionEvent triggering the stage
     * @param path        A String path of the new View
     * @param title       The title of the new View
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
     * Validates all ComboBoxes and TextFields for missing values.
     *
     * @return returns true if any field is missing
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
     * Validates each field and provides an error message if that
     * field is missing a value.
     *
     * @return A StringBuilder error message
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
