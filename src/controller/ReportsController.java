package controller;

import data.DBAppointment;
import data.DBContacts;
import data.DBCustomers;
import data.LoginTracker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class displays data in three tabs: Customer Appointments, Contact Schedule, and Additional Report Info.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class ReportsController implements Initializable {

    public TableView contact_table_view;

    public static final String NUMBER_OF_APPOINTMENTS_MAP_KEY = "E";
    public static final String NUMBER_OF_APPOINTMENTS_MAP_VALUE = "F";
    public static final String NUMBER_OF_CUSTOMERS_MAP_KEY = "G";
    public static final String NUMBER_OF_CUSTOMERS_MAP_VALUE = "H";
    public static final String NUMBER_OF_CONTACTS_MAP_KEY = "I";
    public static final String NUMBER_OF_CLIENTS_MAP_VALUE = "J";
    public static final String NUMBER_OF_LOGINS_MAP_KEY = "K";
    public static final String NUMBER_OF_LOGINS_MAP_VALUE = "L";

    public Tab customer_tab;
    public Tab contact_tab;
    public Tab additional_report_tab;
    public TableColumn contact_appointment_id_column;
    public TableColumn contact_title_column;
    public TableColumn contact_type_column;
    public TableColumn contact_description_column;
    public TableColumn contact_start_column;
    public TableColumn contact_end_column;
    public TableColumn contact_customer_id_column;
    public ListView contact_listview;
    public ComboBox month_combo;
    public ComboBox type_combo;
    public Label number_of_appointments_id;
    public ListView label_list;
    public ListView values_list;


    /**
     * This method initializes each tab, and sets a listener for the Month and Type ComboBoxes.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeContactsTab();
        initializeAdditionalReportsTab();
        initializeCustomersTab();

        // Customer Tab Month ComboBox Listener
        month_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            // If only month combo is selected, query database for COUNT of just month
            // If both type and month selected, query database for COUNT of month AND type
            if (type_combo.getSelectionModel().isEmpty()) {
                number_of_appointments_id.setText(String.valueOf(DBAppointment.getTotalNumberOfAppointmentsByMonth((Month) newValue)));
            } else {
                Integer num = DBAppointment.getNumberOfAppointmentsByMonthAndType((Month) newValue, (String) type_combo.getSelectionModel().getSelectedItem());
                number_of_appointments_id.setText(String.valueOf(num));
            }
        });

        // Customer Tab Type ComboBox Listener
        type_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            // If just type combo is selected, query database for COUNT of just type
            // If both type and month selected, query database for COUNT of month AND type
            if (month_combo.getSelectionModel().isEmpty()) {
                Integer num = DBAppointment.getNumberOfAppointmentsByType((String) newValue);
                number_of_appointments_id.setText(String.valueOf(num));
            } else {
                Integer num = DBAppointment.getNumberOfAppointmentsByMonthAndType((Month) month_combo.getSelectionModel().getSelectedItem(), (String) newValue);
                number_of_appointments_id.setText(String.valueOf(num));
            }
        });

        // Contact Tab Listener
        contact_listview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            contact_table_view.setItems(DBAppointment.getAppointmentListFromContact((Contact) newSelection));
        });
    }

    /**
     * Helper method to initialize the Additional Reports Info tab. Uses
     * a Map<String, Integer> to store the label-value pair, which offers
     * modularity for future versions of this application.
     */
    private void initializeAdditionalReportsTab() {
        Map<String, Integer> reportsValuesMap = new HashMap<>();
        Path path = Path.of("login_activity.txt");

        reportsValuesMap.put(NUMBER_OF_APPOINTMENTS_MAP_VALUE, DBAppointment.getTotalNumberOfAppointments());
        reportsValuesMap.put(NUMBER_OF_CUSTOMERS_MAP_VALUE, DBCustomers.getTotalNumberOfCustomers());
        reportsValuesMap.put(NUMBER_OF_CLIENTS_MAP_VALUE, DBContacts.getTotalNumberOfContacts());
        reportsValuesMap.put(NUMBER_OF_LOGINS_MAP_VALUE, LoginTracker.getNumberOfLogMessages(path));

        Map<String, String> reportsKeyMap = new HashMap<>();
        reportsKeyMap.put(NUMBER_OF_APPOINTMENTS_MAP_KEY, "Number of Appointments");
        reportsKeyMap.put(NUMBER_OF_CUSTOMERS_MAP_KEY, "Number of Customers");
        reportsKeyMap.put(NUMBER_OF_CONTACTS_MAP_KEY, "Number of Contacts");
        reportsKeyMap.put(NUMBER_OF_LOGINS_MAP_KEY, "Number of Logins");

        ObservableList<Integer> valuesList = FXCollections.observableArrayList(reportsValuesMap.values());
        ObservableList<String> keyList = FXCollections.observableArrayList(reportsKeyMap.values());

        label_list.setItems(keyList);
        values_list.setItems(valuesList);
    }

    /**
     * Helper method to initialize the Contacts tab. Uses a Map<String, Integer>
     * to store the label-value pair, which offers modularity for future versions
     * of this application.
     */
    private void initializeContactsTab() {
        contact_listview.setItems(DBContacts.getAllContacts());
        contact_appointment_id_column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        contact_title_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        contact_type_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        contact_description_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        contact_start_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        contact_end_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        contact_customer_id_column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));
    }

    /**
     * Helper method to initialize the Customers tab. Uses a Map<String, Integer>
     * to store the label-value pair, which offers modularity for future versions
     * of this application.
     */
    private void initializeCustomersTab() {
        ObservableList<Month> monthObservableList = FXCollections.observableArrayList(Month.values());
        ObservableList<String> logTypeObservableList = FXCollections.observableArrayList(TypeListSingleton.getInstance().getTypeObservableList());
        month_combo.setItems(monthObservableList);
        type_combo.setItems(logTypeObservableList);
    }

    /**
     * Button to go back to Customers View.
     *
     * @param actionEvent Back Button pressed
     * @throws IOException
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(Main.resourceBundle.getString("customers_screen")));
        stage.setTitle("Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
