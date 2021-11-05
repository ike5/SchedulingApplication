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
    public TableColumn label_column;
    public ToggleGroup radioButtonToggleGroup;
    public RadioButton type_radio_button;
    public RadioButton month_radio_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Map, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new MapValueFactory<>("Type"));

        TableColumn<Map, Integer> numCustomers = new TableColumn<>("Number of Customers");
        numCustomers.setCellValueFactory(new MapValueFactory<>("Number of Customers"));

        customer_table_view.getColumns().add(typeColumn);
        customer_table_view.getColumns().add(numCustomers);

        ObservableList<Map<String, Integer>> mapObservableList = DBAppointment.getMapOfTypesAndValue();

        customer_table_view.getItems().addAll(mapObservableList);
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
