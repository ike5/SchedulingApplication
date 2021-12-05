package utils;

import com.mysql.cj.xdevapi.Table;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

public class Test {
    public static void main(String[] args) {
        TableColumn<Appointment, String> appointmentStringTableColumn = new TableColumn<>("AppointmentTitle");

    }
}

interface TypeFilter {
    boolean showType(ModelType modelType);
}


interface ModelType {}
