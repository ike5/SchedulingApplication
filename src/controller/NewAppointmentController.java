package controller;

import data.DBAppointment;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {

    public TableView appointment_table;
    public TableColumn appointment_label_column;
    public TableColumn appointment_adjustable_column;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<String> appointmentList = new ArrayList<>();
//        DBAppointment.getAppointment()
//        for(String s = )
//        appointment_table.setItems();
    }

}
