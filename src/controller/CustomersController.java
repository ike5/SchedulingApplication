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
        onPull(null);
    }

    public void stateProvinceOnAction(ActionEvent actionEvent) {
        onPull(null);
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
        DBCustomers dbCustomers = new DBCustomers();
        ObservableList<Customer> customerObservableList = dbCustomers.getAllCustomers();

        // Populate table with customers
        table_view_id.setItems(customerObservableList);
        // string is tied to getter in the Customer class--example: getDivisionId()
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
        country_combo_id.getSelectionModel().selectFirst();

        Country country = country_combo_id.getSelectionModel().getSelectedItem();
//        ObservableList<Division> divisionObservableList = DBDivisions.getDivisions(country.getCountryId());
        ObservableList<Division> divisionObservableList = DBDivisions.getAllFirstLevelDivisions();
        state_province_combo_id.setVisibleRowCount(5);
        state_province_combo_id.setPromptText("Choose a country first...");
        state_province_combo_id.setItems(divisionObservableList);

        // Need callback of Country ComboBox in order to get the correct list of States/Provinces
//        Country countryCombo = country_combo_id.getSelectionModel().getSelectedItem();
//        country_combo_id.setValue(countryCombo);

        table_view_id.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            if(newSelection != null){
//                for (int i = 0; i < table_view_id.getItems().size(); i++){
                    Customer customer = (Customer) table_view_id.getSelectionModel().getSelectedItem();
                    customer_id_id.setText(String.valueOf(customer.getId()));
                    customer_name_id.setText(customer.getName());
                    address_id.setText(customer.getAddress());
                    postal_code_id.setText(customer.getPostal());
                    phone_number_id.setText(customer.getPhone());
                    country_combo_id.getSelectionModel().select(customer.getCountryId());
                    state_province_combo_id.getSelectionModel().select(customer.getDivisionID()); // only works for the united states
            }
        });

        //FIXME - remove callback text (ugly)
        Callback<ListView<Country>, ListCell<Country>> factory = countryListView -> new ListCell<Country>(){
            @Override
            protected void updateItem(Country country, boolean empty) {
                super.updateItem(country, empty);
                setText(empty ? "" : ("C : " + country.getName()));
            }
        };
        Callback<ListView<Country>, ListCell<Country>> factoryUsed = countryListView -> new ListCell<Country>(){
            @Override
            protected void updateItem(Country country, boolean empty) {
                super.updateItem(country, empty);
                setText(empty ? "" : ("Co: " + country.getName()));
            }
        };
        country_combo_id.setCellFactory(factory);
        country_combo_id.setButtonCell(factoryUsed.call(null));


    }

    public void onPull(ActionEvent actionEvent){
        Customer customer = (Customer) table_view_id.getSelectionModel().getSelectedItem();
        customer_id_id.setText(Integer.toString(customer.getId()));
        customer_name_id.setText(customer.getName());
        address_id.setText(customer.getAddress());
        postal_code_id.setText(customer.getPostal());
        phone_number_id.setText(customer.getPhone());
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        // worry about validation here
        onPull(actionEvent);
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
