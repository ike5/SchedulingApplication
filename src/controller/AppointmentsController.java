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
import model.Message;
import model.View;
import utils.ChangeViewInterface;
import utils.ControllerViewChanger;

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
public class AppointmentsController implements Initializable, ChangeViewInterface {
    public TableView<Appointment> table_view_id;
    public TableColumn<Appointment, Integer> appointment_id_tablecolumn;
    public TableColumn<Appointment, String> title_tablecolumn;
    public TableColumn<Appointment, String> description_tablecolumn;
    public TableColumn<Appointment, String> location_tablecolumn;
    public TableColumn<Appointment, String> contact_tablecolumn;
    public TableColumn<Appointment, String> type_tablecolumn;
    public TableColumn<Appointment, String> start_date_time_tablecolumn;
    public TableColumn<Appointment, String> end_date_time_tablecolumn;
    public TableColumn<Appointment, Integer> customer_id_tablecolumn;
    public TableColumn<Appointment, Integer> user_id_tablecolumn;
    public RadioButton month_view_radio_button;
    public RadioButton week_view_radio_button;
    public RadioButton all_appointments_radio_button;
    public ToggleGroup appointmentToggleGroup;
    public Button delete_button;
    public Button update_button;

    /**
     * Initializes TableView with Appointment objects. This method
     * also ensures that Delete and Update buttons start off inactive
     * until a table item is selected.
     *
     * @param url            The URL
     * @param resourceBundle The ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_view_id.setItems(DBAppointment.getAllAppointments());

        appointment_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        title_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        description_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));
        location_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentLocation"));
        contact_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));
        type_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        start_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("StartString"));
        end_date_time_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("EndString"));
        customer_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        user_id_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));


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
     * Switches user to the CustomersController screen. The lambda expression
     * parameter takes in a View object and calls the change() method on
     * it.
     *
     * @param actionEvent Back Button pressed
     * @throws IOException Exception
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        changeView(view -> view.change(), new View(
                actionEvent, Main.resourceBundle.getString("customers_screen"), "Customers"
        ));
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
     * views. The lambda expression parameter takes in a View object
     * and calls the change() method on it.
     *
     * @param actionEvent New Appointment Button pressed
     * @throws IOException Exception
     */
    public void newAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        AppointmentSingleton.getInstance().setAppointment(null);

        changeView(view -> view.change(), new View(
                actionEvent, Main.resourceBundle.getString("modify_appointment_screen"), "New Appointment"
        ));
    }

    /**
     * Switches user to the ModifyAppointmentController screen. This
     * method captures the Appointment object with the Appointment
     * singleton before switching views. The lambda expression
     * parameter takes in a View object and calls the change() method on
     * it.
     *
     * @param actionEvent Update Appointment Button is pressed
     * @throws IOException Exception
     */
    public void updateAppointmentButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (table_view_id.getSelectionModel().selectedItemProperty() != null) {
            AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

            changeView(view -> view.change(), new View(
                    actionEvent, Main.resourceBundle.getString("modify_appointment_screen"), "Modify Appointment"
            ));
        } else {
            Message.errorMessage("Please select an appointment", "Nothing selected");
        }
    }

    /**
     * Deletes a selected appointment.
     *
     * @param actionEvent Delete Appointment Button is pressed
     */
    public void deleteAppointmentButtonOnAction(ActionEvent actionEvent) {
        AppointmentSingleton.getInstance().setAppointment((Appointment) table_view_id.getSelectionModel().getSelectedItem());

        int id = AppointmentSingleton.getInstance().getAppointment().getAppointmentId();
        String type = AppointmentSingleton.getInstance().getAppointment().getAppointmentType();

        Optional<ButtonType> result = Message.confirmationMessage("Are you sure?", "Delete appointment");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.deleteAppointment(id);

            Message.successMessage(
                    "Appointment ID: " + id + "\nAppointment Type: " + type,
                    "Appointment Deleted"
            );
        }



        // After deleting item, disable button until another item is selected.
        delete_button.setDisable(true);

        // Repopulate TableView with new list of appointments
        table_view_id.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * Helper method that simplifies the process of changing views. The first
     * parameter is a functional interface and allows the user to use a lambda
     * to call methods within the View object.
     *
     * @param controllerViewChanger a ControllerViewChanger interface
     * @param view                  a View object
     */
    @Override
    public void changeView(ControllerViewChanger controllerViewChanger, View view) {
        controllerViewChanger.switchView(view);
    }
}
