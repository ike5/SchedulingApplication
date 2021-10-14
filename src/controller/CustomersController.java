package controller;

import data.DBDivisions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import data.DBCountries;
import data.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public Button save_button;
    public Button clear_form_button;
    public Button new_customer_button;
    public Button delete_customer_button;
    public Button logout_button;
    private static boolean isSaveButtonDisabled;
    private static boolean isClearFormButtonDisabled;
    private static boolean isNewCustomerButtonDisabled;
    private static boolean isDeleteCustomerButtonDisabled;
    private static boolean isLogoutButtonDisabled;
    private static boolean isCustomerNameFieldValid;
    private static boolean isCountryComboBoxValid;
    private static boolean isDivisionComboBoxValid;
    private static boolean isAddressFieldValid;
    private static boolean isPostalCodeFieldValid;
    private static boolean isPhoneNumberFieldValid;


    ObservableList<Division> divisionObservableList;
    ObservableList<Customer> customerObservableList;


    // Initialize the state of the buttons once when the program starts
    static {
        isSaveButtonDisabled = true;
        isClearFormButtonDisabled = true;
        isNewCustomerButtonDisabled = false;
        isDeleteCustomerButtonDisabled = true;
        isLogoutButtonDisabled = false;
    }

    @Deprecated
    public void customerNameOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void addressOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void postalCodeOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void phoneNumberOnAction(ActionEvent actionEvent) {
    }

    public void clearFormButtonOnAction(ActionEvent actionEvent) {
        // Clear 7 items
        country_combo_id.getSelectionModel().clearSelection();
        state_province_combo_id.getSelectionModel().clearSelection();
        customer_id_id.clear();
        customer_name_id.clear();
        address_id.clear();
        postal_code_id.clear();
        phone_number_id.clear();

        setAllFieldsValidity(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customers controller initialized!");

        // Set focus options on buttons
        save_button.setDisable(isSaveButtonDisabled);
        clear_form_button.setDisable(isClearFormButtonDisabled);
        delete_customer_button.setDisable(isDeleteCustomerButtonDisabled);
        new_customer_button.setDisable(isNewCustomerButtonDisabled);
        customer_id_id.setDisable(true); // Prevent users from changing touching customer id value

        // Make a Customer ObservableList to populate the table
        customerObservableList = DBCustomers.getAllCustomers();

        // Populate table with customers
        table_view_id.setItems(customerObservableList);
        // Tied to getter in the Customer class --> getDivisionId()
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

                // make all fields valid by default when populating
                setAllFieldsValidity(true);
            }
        });

        //FIXME - remove callback text (ugly)
        // - Make name of selection dropdown change to either States OR Provinces
        // - Make States/Provinces autofill to first selection when choosing the Country

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

    }

    public void countryComboBoxOnAction(ActionEvent actionEvent) {
        // Automatically limits division list to only those states/provinces within the country selected
        divisionObservableList = DBDivisions.getDivisions(country_combo_id.getSelectionModel().getSelectedItem().getCountryId());
        state_province_combo_id.setItems(divisionObservableList);
    }

    @Deprecated
    public void divisionComboBoxOnAction(ActionEvent actionEvent) {
        // Not sure what to do here.
    }

    @Deprecated
    public void onPull(ActionEvent actionEvent) {
        Customer customer = (Customer) table_view_id.getSelectionModel().getSelectedItem();
        customer_id_id.setText(Integer.toString(customer.getId()));
        customer_name_id.setText(customer.getName());
        address_id.setText(customer.getAddress());
        postal_code_id.setText(customer.getPostalCode());
        phone_number_id.setText(customer.getPhone());
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        //FIXME
        // - if row in TableView is selected update the row with information in TextField
        // - new customer button clears form an unselects rows in TableView (if changes were made to TextFields, prompt alert)
        // - save button updates selected table row
        // - save button creates new customer if no table row selected
        // - clear form unselects table rows and clears TextFields
        // - if logout button pressed and if table row was selected and if field was changed, prompt save


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save changes?");
        Optional<ButtonType> result = alert.showAndWait();
        Customer customer = null;
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Insert into database
            customer = DBCustomers.insertCustomer(
                    customer_name_id.getText().trim(),
                    address_id.getText().trim(),
                    postal_code_id.getText().trim(),
                    phone_number_id.getText().trim(),
                    state_province_combo_id.getSelectionModel().getSelectedItem().getDivisionId()
            );
            new Test(customer.toString());

            customerObservableList = DBCustomers.getAllCustomers();
            table_view_id.setItems(customerObservableList);
            table_view_id.refresh(); // not necessary?
        }

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

    private boolean validateTextField(TextField textField) {
//        String regexUsername = "^[0-z]+";
        String regexTextField = "^[^\\s].*"; // Can't start with a whitespace and matches 1 or more characters
        return textField.getText().matches(regexTextField);
    }

    private boolean validateCountryComboBox(ComboBox<Country> countryComboBox) {
        return countryComboBox.isPickOnBounds();
    }

    private boolean validateDivisionComboBox(ComboBox<Division> divisionComboBox) {
        return divisionComboBox.isPickOnBounds();
    }

    @Deprecated
    public void customerIdOnAction(ActionEvent actionEvent) {
    }

    public void customerNameOnKeyTyped(KeyEvent keyEvent) {
        isCustomerNameFieldValid = validateTextField((TextField) keyEvent.getSource());
        if (isCustomerNameFieldValid) {
            customer_name_id.setStyle("-fx-background-color: white");
            save_button.setDisable(isAllFieldsValid());
        } else {
            customer_name_id.setStyle("-fx-background-color: pink");
            save_button.setDisable(!isAllFieldsValid());
        }
    }

    public void addressOnKeyTyped(KeyEvent keyEvent) {
        isAddressFieldValid = validateTextField((TextField) keyEvent.getSource());
        if (isAddressFieldValid) {
            address_id.setStyle("-fx-background-color: white");
            save_button.setDisable(isAllFieldsValid());
        } else {
            address_id.setStyle("-fx-background-color: pink");
            save_button.setDisable(!isAllFieldsValid());
        }
    }

    public void postalCodeOnKeyTyped(KeyEvent keyEvent) {
        isPostalCodeFieldValid = validateTextField((TextField) keyEvent.getSource());
        if (isPostalCodeFieldValid) {
            postal_code_id.setStyle("-fx-background-color: white");
            save_button.setDisable(isAllFieldsValid());
        } else {
            postal_code_id.setStyle("-fx-background-color: pink");
            save_button.setDisable(!isAllFieldsValid());
        }
    }

    public void phoneNumberOnKeyTyped(KeyEvent keyEvent) {
        isPhoneNumberFieldValid = validateTextField((TextField) keyEvent.getSource());
        if (isPhoneNumberFieldValid) {
            phone_number_id.setStyle("-fx-background-color: white");
            save_button.setDisable(isAllFieldsValid());
        } else {
            phone_number_id.setStyle("-fx-background-color: pink");
            save_button.setDisable(!isAllFieldsValid());
        }
    }


    @Deprecated
    public void saveButtonOnKeyTyped(KeyEvent keyEvent) {
    }

    //FIXME
    // - assumes that when one is correct, all are correct. need to fix this bug
    private boolean isAllFieldsValid() {
        // short-circuit AND ok
        return (isCustomerNameFieldValid
                && isCountryComboBoxValid
                && isDivisionComboBoxValid
                && isAddressFieldValid
                && isPostalCodeFieldValid
                && isPhoneNumberFieldValid
        );
    }

    private boolean setAllFieldsValidity(boolean fieldsValid) {
        isCustomerNameFieldValid = fieldsValid;
        isCountryComboBoxValid = fieldsValid;
        isDivisionComboBoxValid = fieldsValid;
        isAddressFieldValid = fieldsValid;
        isPostalCodeFieldValid = fieldsValid;
        isPhoneNumberFieldValid = fieldsValid;
        return fieldsValid;
    }
}

//TODO
// - Add validation before sending data into database for collection

