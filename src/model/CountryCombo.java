package model;

import data.DBCountries;
import javafx.scene.control.ComboBox;

public class CountryCombo implements ComboInterface {
    ComboBox<Country> country_combo_id = new ComboBox<>();

    @Override
    public void setComboBox(Customer customer, ComboBox country_combo_id) {
        CountrySingleton.getInstance().setCountryObservableList(DBCountries.getAllCountries());
        country_combo_id.setItems(CountrySingleton.getInstance().getCountryObservableList());
        country_combo_id.setValue(customer.getCountry());
    }
}
