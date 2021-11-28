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
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used to modify existing appointments as well as create new appointments.
 */
public class ModifyAppointmentController implements Initializable {
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

        // Set datepicker to today
        LocalDate localDate = LocalDate.from(ZonedDateTime.now());
        start_date_picker.setValue(localDate);

        // Restrict appointment hours
        LocalTime startTime = LocalTime.of(8, 0); // 8:00AM
        LocalTime endTime = LocalTime.of(21, 45); // 11:45, but last appointment is +15 min so, 12:00AM

        // Set ZoneId for office
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

    private boolean hasOverlappingAppointments(Appointment appointment) {
        // Compare appointment time with list of appointments by same Customer

        // Remember to not compare against this exact appointment ID in the database

        //Get a List of all appointment objects from the database belonging to that Customer
    }


    private void checkOverlap() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 11, 25, 10, 40);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 11, 25, 11, 10);
        LocalDateTime myDateTime = LocalDateTime.of(2021, 11, 25, 11, 40); // inbetween start and end?

        // Check overlap
        if (myDateTime.isAfter(startDateTime) && myDateTime.isBefore(endDateTime)) {
            System.out.println(myDateTime + " is between " + startDateTime + " and " + endDateTime);
        } else if (myDateTime.isEqual(startDateTime) || myDateTime.isEqual(endDateTime)) {
            System.out.println("Matches start or end time");
        } else {
            System.err.println("Your date and time does not overlap");
        }
    }

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

    private void populateTextFields(Appointment appointment) {
        // Populate TextFields
        appointment_id_textfield.setText(Integer.toString(appointment.getAppointmentId()));
        title_textfield.setText(appointment.getAppointmentTitle());
        description_textfield.setText(appointment.getAppointmentDescription());
    }

    private void setNumberOfVisibleRows(int numberOfVisibleRows) {
        // Set number of visible rows in ComboBoxes
        customer_combo.setVisibleRowCount(numberOfVisibleRows);
        contact_combo.setVisibleRowCount(numberOfVisibleRows);
        user_combo.setVisibleRowCount(numberOfVisibleRows);
        location_combo.setVisibleRowCount(numberOfVisibleRows);
        type_combo.setVisibleRowCount(numberOfVisibleRows);
    }

    /**
     * Called when updating an appointment.
     *
     * @param appointment
     */
    private void setDatePickerAndTimeCombos(Appointment appointment) {
        // ZoneId of office
        ZoneId zoneId_EST = ZoneId.of("America/New_York");

        // Get ZonedDateTime of Appointment Start Date & Time
        ZonedDateTime zonedDateTime_start = ZonedDateTime.of(appointment.getStart(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime_start_EST = ZonedDateTime.ofInstant(zonedDateTime_start.toInstant(), zoneId_EST);
        LocalDate localStartDate = zonedDateTime_start_EST.toLocalDate();
        LocalTime localStartTime = zonedDateTime_start_EST.toLocalTime();

        // Get ZonedDateTime of Appointment End Time
        ZonedDateTime zonedDateTime_end = ZonedDateTime.of(appointment.getEnd(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime_end_EST = ZonedDateTime.ofInstant(zonedDateTime_end.toInstant(), zoneId_EST);
        LocalTime localEndTime = zonedDateTime_end_EST.toLocalTime();


        start_date_picker.setValue(localStartDate);
        start_combo.setValue(localStartTime);
        end_combo.setValue(localEndTime);
    }


    //FIXME refactor to make modular

    /**
     * Checks to see whether a ZonedDateTime falls on either Saturday or Sunday.
     *
     * @return
     */
    private boolean isWeekend() {
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(start_date_picker.getValue(), (LocalTime) start_combo.getSelectionModel().getSelectedItem(), ZoneId.systemDefault());
        return zonedStartDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || zonedStartDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY);
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
        } else if (isWeekend()) {
            Messages.errorMessage("Cannot schedule outside of business hours", "Schedule Error");
        } else {
            // If user clicked 'New Appointment' from AppointmentsController,
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
        //FIXME use .getItem().clear() instead of null?
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
