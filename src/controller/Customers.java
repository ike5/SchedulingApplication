package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import data.DBCountries;
import data.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Country;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Customers implements Initializable {
    public TextField customer_id_id;
    public TextField customer_name_id;
    public ComboBox<Country> country_combo_id;
    public ComboBox state_province_combo_id;
    public TextField address_id;
    public TextField postal_code_id;
    public TextField phone_number_id;
    public TableColumn id_tablecolumn_id;
    public TableColumn name_tablecolumn_id;
    public TableColumn address_tablecolumn_id;
    public TableColumn postal_code_tablecolumn_id;
    public TableColumn phone_number_tablecolumn_id;
    public TableColumn country_tablecolumn_id;
    public TableColumn state_province_tablecolumn_id;
    public TableView table_view_id;

    public void customerIdOnAction(ActionEvent actionEvent) {
    }

    public void customerNameOnAction(ActionEvent actionEvent) {
    }

    public void countryOnAction(ActionEvent actionEvent) {
    }

    public void stateProvinceOnAction(ActionEvent actionEvent) {
    }

    public void addressOnAction(ActionEvent actionEvent) {
    }

    public void postalCodeOnAction(ActionEvent actionEvent) {
    }

    public void phoneNumberOnAction(ActionEvent actionEvent) {
    }

    public void clearFormButtonOnAction(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customers controller initialized!");
        DBCustomers dbCustomers = new DBCustomers();
        ObservableList<Customer> customerObservableList = dbCustomers.getAllCustomers();

        table_view_id.setItems(customerObservableList);

        // string is tied to getter in the Country class--example: getDivisionId()
        id_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("name"));
        address_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("address"));
        postal_code_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("postal"));
        phone_number_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("phone"));
        state_province_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("divisionID")); // note 'd' is capitalized
        country_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<>("countryId"));

        DBCountries dbCountries = new DBCountries();
        ObservableList<Country> countryObservableList = dbCountries.getAllCountries();
        country_combo_id.setItems(countryObservableList);

        // Get value of combobox
        Country countryCombo = country_combo_id.getSelectionModel().getSelectedItem();
        country_combo_id.setValue(countryCombo);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        // worry about validation here
    }

    public void deleteCustomerButtonOnAction(ActionEvent actionEvent) {
    }

    public void newCustomerButtonOnAction(ActionEvent actionEvent) {
    }

    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        //TODO
        // - Alert to save or discard

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Logout?");
        alert.setTitle("Confirm logout?");
        alert.showAndWait();

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle(null);
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
