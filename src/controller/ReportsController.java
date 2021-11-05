package controller;

import data.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
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

    private ObservableList<Map> mapObservableListTypesValues;
    private ObservableList<Map> mapObservableListMonthValues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapObservableListTypesValues = DBAppointment.getMapOfTypesAndValue();
        mapObservableListMonthValues = DBAppointment.getMapOfAppointmentsByMonth();

        // initialize data
        if (type_radio_button.isSelected()) {
            basic_column.setText("Type of Appointment");
            basic_column.setCellValueFactory(new MapValueFactory<>(TYPE_MAP_KEY));
            num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_MAP_KEY));
            customer_table_view.setItems(mapObservableListTypesValues);
        }
        customer_table_view.getColumns().setAll(basic_column, num_appointments_column);
    }

    public void userTabOnSelectionChanged(Event event) {
    }

    public void customerTabOnSelectionChanged(Event event) {
    }

    public void contactTabOnSelectionChanged(Event event) {
    }

    public void additionalTabOnSelectionChanged(Event event) {
    }

    public void typeRadioButtonOnAction(ActionEvent actionEvent) {
        basic_column.setText("Type of Appointment");
        basic_column.setCellValueFactory(new MapValueFactory<>(TYPE_MAP_KEY));
        num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_MAP_KEY));
        customer_table_view.setItems(mapObservableListTypesValues);
    }

    public void monthRadioButtonOnAction(ActionEvent actionEvent) {
        basic_column.setText("Month of Appointment");
        basic_column.setCellValueFactory(new MapValueFactory<>(MONTH_MAP_KEY));
        num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_BY_MONTH_MAP_KEY));
        customer_table_view.setItems(mapObservableListMonthValues);
    }
}
