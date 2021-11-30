package model;

import data.DBDivisions;
import javafx.scene.control.ComboBox;
import test.Test;
/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class DivisionCombo implements ComboInterface {

    @Override
    public void setComboBox(Customer customer, ComboBox division_combo_id) {
        // Allow for any possibility of divisions to be selected
        DivisionListSingleton.getInstance().setDivisionObservableList(DBDivisions.getAllFirstLevelDivisions());
        Object[] d = DivisionListSingleton.getInstance().getDivisionObservableList().toArray();
        for (int i = 0; i < d.length; i++) {
            new Test(((Division) d[i]).getDivisionId());
            if (((Division) d[i]).getDivisionId() == customer.getDivisionId()) {
                division_combo_id.getSelectionModel().select(i);
                new Test("Should print: " + ((Division) d[i]).getDivisionName());
            }
        }
    }

}
