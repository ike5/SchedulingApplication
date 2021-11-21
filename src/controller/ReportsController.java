package controller;

import data.DBAppointment;
import data.DBContacts;
import data.LoginTracker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    public TableView user_table_view;
    public TableView customer_table_view;
    public TableView contact_table_view;
    public TableView additional_table_view;
    public ToggleGroup radioButtonToggleGroup;
    public RadioButton type_radio_button;
    public RadioButton month_radio_button;
    public TableColumn<Map, Integer> num_appointments_column;
    public TableColumn<Map, String> basic_column;

    public static final String TYPE_MAP_KEY = "A";
    public static final String NUM_APPOINTMENT_MAP_KEY = "B";
    public static final String MONTH_MAP_KEY = "C";
    public static final String NUM_APPOINTMENT_BY_MONTH_MAP_KEY = "D";
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

    private ObservableList<Map> mapObservableListTypesValues;
    private ObservableList<Map> mapObservableListMonthValues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize contacts tab data
        contact_listview.setItems(DBContacts.getAllContacts());
        contact_appointment_id_column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentId"));
        contact_title_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentTitle"));
        contact_type_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        contact_description_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentDescription"));
        contact_start_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartString"));
        contact_end_column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndString"));
        contact_customer_id_column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerId"));

        // Initialize customer tab data
//        mapObservableListTypesValues = DBAppointment.getMapOfTypesAndValue();
//        mapObservableListMonthValues = DBAppointment.getMapOfAppointmentsByMonth();
//        if (type_radio_button.isSelected()) {
//            basic_column.setText("Type of Appointment");
//            basic_column.setCellValueFactory(new MapValueFactory<>(TYPE_MAP_KEY));
//            num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_MAP_KEY));
//            customer_table_view.setItems(mapObservableListTypesValues);
//        }
//        customer_table_view.getColumns().setAll(basic_column, num_appointments_column);

        // Initialize Month, Type ComboBoxes in Customer tab
        ObservableList<java.time.Month> monthObservableList = FXCollections.observableArrayList(java.time.Month.values());
        ObservableList<String> logTypeObservableList = FXCollections.observableArrayList(TypeListSingleton.getInstance().getTypeObservableList());
        month_combo.setItems(monthObservableList);
        type_combo.setItems(logTypeObservableList);

        // Customer Tab Month ComboBox Listener
        month_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            // If only month combo is selected, ask database for COUNT of just month
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

        //TODO
        // Additional report: count Appointments, users, contacts, and number of logins
    }

    public void customerTabOnSelectionChanged(Event event) {
    }

    public void contactTabOnSelectionChanged(Event event) {
    }

//    public void additionalTabOnSelectionChanged(Event event) {
//    }

//    public void typeRadioButtonOnAction(ActionEvent actionEvent) {
//        basic_column.setText("Type of Appointment");
//        basic_column.setCellValueFactory(new MapValueFactory<>(TYPE_MAP_KEY));
//        num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_MAP_KEY));
//        customer_table_view.setItems(mapObservableListTypesValues);
//    }

//    public void monthRadioButtonOnAction(ActionEvent actionEvent) {
//        basic_column.setText("Month of Appointment");
//        basic_column.setCellValueFactory(new MapValueFactory<>(MONTH_MAP_KEY));
//        num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_BY_MONTH_MAP_KEY));
//        customer_table_view.setItems(mapObservableListMonthValues);
//    }

    public void additionalReportTabOnSelectionChanged(Event event) {
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setTitle("Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
