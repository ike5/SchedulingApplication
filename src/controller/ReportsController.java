package controller;

import com.mysql.cj.xdevapi.Table;
import data.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public TableColumn<Map, String> month_column;
    public TableColumn<Map, Integer> total_appointments_column2;
    public TableColumn<Map, String> type_column;

    public static final String TYPE_MAP_KEY = "T";
    public static final String NUM_APPOINTMENT_MAP_KEY = "N";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Map> mapObservableList = DBAppointment.getMapOfTypesAndValue();

        type_column.setCellValueFactory(new MapValueFactory<>(TYPE_MAP_KEY));
        num_appointments_column.setCellValueFactory(new MapValueFactory<>(NUM_APPOINTMENT_MAP_KEY));

        customer_table_view.setItems(mapObservableList);
        customer_table_view.getColumns().setAll(type_column, num_appointments_column);
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
    }

    public void monthRadioButtonOnAction(ActionEvent actionEvent) {
    }
}