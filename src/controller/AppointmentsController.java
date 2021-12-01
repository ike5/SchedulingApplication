package controller;

import data.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.AppointmentSingleton;
import model.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class displays appointments to the user by either month, week, or all time. New appointments can be added,
 * and existing appointments can be modified.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
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
    public Button delete_button;
    public Button update_button;

    /**
     * Initializes TableView with Appointment objects. This method also ensures that Delete and Update buttons
     * start off inactive until a table item is selected.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_view_id.setItems(DBAppointment.getAllAppointments());

        appointment_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        title_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        description_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        location_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentLocation"));
        contact_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("ContactName"));
        type_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        start_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        end_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        customer_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));
        user_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserId"));

        // TableView listener
        table_view_id.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Enable buttons if row selected
                delete_button.setDisable(false);
                update_button.setDisable(false);
                AppointmentSingleton.getInstance().setAppointment((Appointment) newSelection);
            }
        });

        // Disable buttons if no row selected
        delete_button.setDisable(table_view_id.getSelectionModel().isEmpty());
        update_button.setDisable(table_view_id.getSelectionModel().isEmpty());
    }

    /**
     * Switches user to the CustomersController screen.
     *
     * @param actionEvent Back Button pressed
     * @throws IOException
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, Main.resourceBundle.getString("customers_screen"), "Hello");
    }

    /**
     * Sets the TableView to display all appointments in month.
     *
     * @param actionEvent Month RadioButton
     */
    public void monthViewRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointmentsInMonth());
    }

    /**
     * Sets the TableView to display all appointments in week.
     *
     * @param actionEvent Week RadioButton
     */
    public void weekViewRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointmentsInWeek());
    }

    /**
     * Sets the TableView to display all available appointments.
     *
     * @param actionEvent All Appointments RadioButton
     */
    public void allAppointmentsRadioButtonOnAction(ActionEvent actionEvent) {
        table_view_id.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * Switches user to the ModifyAppointmentController screen. This
     * method sets the Appointment singleton to null before switching
     * views.
     *
     * @param actionEvent New Appointment Button pressed
     * @throws IOException
     */
    public void newAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        AppointmentSingleton.getInstance().setAppointment(null);

        switchView(actionEvent, Main.resourceBundle.getString("modify_appointment_screen"), "New Appointment");
    }

    /**
     * Switches user to the ModifyAppointmentController screen. This
     * method captures the Appointment object with the Appointment
     * singleton before switching views.
     *
     * @param actionEvent Update Appointment Button is pressed
     * @throws IOException
     */
    public void updateAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (table_view_id.getSelectionModel().selectedItemProperty() != null) {
            AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

            switchView(actionEvent, Main.resourceBundle.getString("modify_appointment_screen"), "Modify Appointment");
        } else {
            Messages.errorMessage("Please select an appointment", "Nothing selected");
        }
    }

    /**
     * Deletes a selected appointment.
     *
     * @param actionEvent Delete Appointment Button is pressed
     */
    public void deleteAppointmentButtonOnAction(ActionEvent actionEvent) {
        AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

        Optional<ButtonType> result = Messages.confirmationMessage("Are you sure?", "Delete appointment");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.deleteAppointment(AppointmentSingleton.getInstance().getAppointment().getAppointmentId());
        }

        // After deleting item, disable button until another item is selected.
        delete_button.setDisable(true);

        // Repopulate TableView with new list of appointments
        table_view_id.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * Helper method that simplifies the view switching process.
     *
     * @param actionEvent The ActionEvent from the parent method
     * @param path        The new View to switch to
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
}
