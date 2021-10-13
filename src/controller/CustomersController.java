package controller;

import data.DBDivisions;
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
import javafx.util.Callback;
import model.Country;
import model.Customer;
import model.Division;
import test.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public TextField customer_id_id;
    public TextField customer_name_id;
    public ComboBox<Country> country_combo_id;
    public ComboBox<Division> state_province_combo_id;
    public TextField address_id;
    public TextField postal_code_id;
    public TextField phone_number_id;
    public TableColumn<Customer, Integer> id_tablecolumn_id;
    public TableColumn<Customer, String> name_tablecolumn_id;
    public TableColumn<Customer, String> address_tablecolumn_id;
    public TableColumn<Customer, String> postal_code_tablecolumn_id;
    public TableColumn<Customer, String> phone_number_tablecolumn_id;
    public TableColumn<Customer, String> country_tablecolumn_id;
    public TableColumn<Customer, String> state_province_tablecolumn_id;
    public TableView table_view_id;
    ObservableList<Division> divisionObservableList;

    public void customerIdOnAction(ActionEvent actionEvent) {
    }

    public void customerNameOnAction(ActionEvent actionEvent) {
    }

    public void addressOnAction(ActionEvent actionEvent) {
    }

    public void postalCodeOnAction(ActionEvent actionEvent) {
    }

    public void phoneNumberOnAction(ActionEvent actionEvent) {
    }

    public void clearFormButtonOnAction(ActionEvent actionEvent) {
        country_combo_id.getSelectionModel().clearSelection();
        state_province_combo_id.getSelectionModel().clearSelection();

        customer_id_id.clear();
        customer_name_id.clear();
        address_id.clear();
        postal_code_id.clear();
        phone_number_id.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customers controller initialized!");

        // Make a Customer ObservableList to populate the table
        ObservableList<Customer> customerObservableList = DBCustomers.getAllCustomers();

        // Populate table with customers
        table_view_id.setItems(customerObservableList);
        // string is tied to getter in the Customer class--example: getDivisionId()
        id_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Id"));
        name_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        address_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        phone_number_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
        postal_code_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("PostalCode"));
        state_province_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("DivisionId")); // note 'd' is capitalized
        country_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("CountryName"));

        // Initialize Country ComboBox
        ObservableList<Country> countryObservableList = DBCountries.getAllCountries();
        country_combo_id.setItems(countryObservableList);

        // Initialize Province/State ComboBox
        divisionObservableList = DBDivisions.getAllFirstLevelDivisions();
        state_province_combo_id.setItems(divisionObservableList);
        state_province_combo_id.setVisibleRowCount(5);

        table_view_id.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            if (newSelection != null) {
                new Test("new: " + newSelection);
                customer_id_id.setText(String.valueOf(((Customer) newSelection).getId()));
                customer_name_id.setText(((Customer) newSelection).getName());
                address_id.setText(((Customer) newSelection).getAddress());
                phone_number_id.setText(((Customer) newSelection).getPhone());
                postal_code_id.setText(((Customer) newSelection).getPostalCode());
                country_combo_id.setValue(((Customer) newSelection).getCountry());
                state_province_combo_id.setValue(((Customer) newSelection).getDivision());
            }
        });

        //FIXME - remove callback text (ugly)
        // - when selection for Country is made, list only States/Provinces of that country
        // - Make name of selection dropdown change to either States OR Provinces

//        Callback<ListView<Country>, ListCell<Country>> factory = countryListView -> new ListCell<Country>(){
//            @Override
//            protected void updateItem(Country country, boolean empty) {
//                super.updateItem(country, empty);
//                setText(empty ? "" : ("" + country.getName()));
//            }
//        };
//        Callback<ListView<Country>, ListCell<Country>> factoryUsed = countryListView -> new ListCell<Country>(){
//            @Override
//            protected void updateItem(Country country, boolean empty) {
//                super.updateItem(country, empty);
//                setText(empty ? "" : ("" + country.getName()));
//            }
//        };
//        country_combo_id.setCellFactory(factory);
//        country_combo_id.setButtonCell(factoryUsed.call(null));

//        Callback<ListView<Country>, ListCell<Country>> factory = countryListView -> new ListCell<>() {
//            @Override
//            protected void updateItem(Country country, boolean b) {
//                super.updateItem(country, b);
//                divisionObservableList = DBDivisions.getDivisions(country_combo_id.getSelectionModel().getSelectedItem().getCountryId());
//                state_province_combo_id.setItems(divisionObservableList);
//            }
//        };
//        country_combo_id.setCellFactory(factory);



    }

    public void countryComboBoxOnAction(ActionEvent actionEvent) {
        // Automatically limits division list to only those states/provinces within the country selected
        divisionObservableList = DBDivisions.getDivisions(country_combo_id.getSelectionModel().getSelectedItem().getCountryId());
        state_province_combo_id.setItems(divisionObservableList);
    }

    public void divisionComboBoxOnAction(ActionEvent actionEvent) {
    }

    public void onPull(ActionEvent actionEvent) {
        Customer customer = (Customer) table_view_id.getSelectionModel().getSelectedItem();
        customer_id_id.setText(Integer.toString(customer.getId()));
        customer_name_id.setText(customer.getName());
        address_id.setText(customer.getAddress());
        postal_code_id.setText(customer.getPostalCode());
        phone_number_id.setText(customer.getPhone());
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

    }

    public void deleteCustomerButtonOnAction(ActionEvent actionEvent) {
    }

    public void newCustomerButtonOnAction(ActionEvent actionEvent) {
    }

    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Logout?");
        alert.setTitle("Confirm logout?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
            stage.setTitle(null);
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    private boolean validateNameString() {
        //FIXME (low) - Allow spaces

        String regexUsername = "^[0-z]+";
        return customer_name_id.getText().matches(regexUsername);
    }


}
