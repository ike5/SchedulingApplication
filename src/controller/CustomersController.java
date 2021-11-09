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
import main.Main;
import model.*;
import test.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public TextField customer_id_id;
    public TextField customer_name_id;
    public ComboBox<Country> country_combo_id;
    public ComboBox<Division> division_combo_id;
    public TextField address_id;
    public TextField postal_code_id;
    public TextField phone_number_id;
    public TableColumn<Customer, Integer> id_tablecolumn_id;
    public TableColumn<Customer, String> name_tablecolumn_id;
    public TableColumn<Customer, String> address_tablecolumn_id;
    public TableColumn<Customer, String> postal_code_tablecolumn_id;
    public TableColumn<Customer, String> phone_number_tablecolumn_id;
    public TableColumn<Customer, Country> country_tablecolumn_id;
    public TableColumn<Customer, Division> division_tablecolumn_id;
    public TableView table_view_id;
    public Button save_button;
    public Button clear_form_button;
    public Button delete_customer_button;
    public Button logout_button;
    private static boolean isValuesChanged;
    private static boolean isSaveButtonDisabled;
    private static boolean isClearFormButtonDisabled;
    private static boolean isDeleteCustomerButtonDisabled;
    private static boolean isCustomerNameFieldValid;
    private static boolean isAddressFieldValid;
    private static boolean isPostalCodeFieldValid;
    private static boolean isPhoneNumberFieldValid;
    ObservableList<Customer> customerObservableList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set focus options on buttons
        save_button.setDisable(isSaveButtonDisabled);
        clear_form_button.setDisable(isClearFormButtonDisabled);
        delete_customer_button.setDisable(isDeleteCustomerButtonDisabled);

        // Prevent users from changing touching customer id value
        customer_id_id.setDisable(true);

        // Populate table with Customers
        CustomerListSingleton.getInstance().setCustomerObservableList(DBCustomers.getAllCustomers());
        table_view_id.setItems(CustomerListSingleton.getInstance().getCustomerObservableList());

        // Bind table cell values to class getter methods
        // Tied to getter in the Customer class --> getDivisionId()
        id_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Id"));
        name_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        address_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        phone_number_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
        postal_code_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, String>("PostalCode"));
        division_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, Division>("Division")); // note 'd' is capitalized
        country_tablecolumn_id.setCellValueFactory(new PropertyValueFactory<Customer, Country>("Country"));

        // Initialize Country ComboBox
        CountryListSingleton.getInstance().setCountryObservableList(DBCountries.getAllCountries());
        country_combo_id.setItems(CountryListSingleton.getInstance().getCountryObservableList());

        // Initialize Province/State (Division) ComboBox
        DivisionListSingleton.getInstance().setDivisionObservableList(DBDivisions.getAllFirstLevelDivisions());
        division_combo_id.setItems(DivisionListSingleton.getInstance().getDivisionObservableList());

        // TableView listener
        table_view_id.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        customer_id_id.setText(String.valueOf(((Customer) newSelection).getId()));
                        customer_name_id.setText(((Customer) newSelection).getName());
                        address_id.setText(((Customer) newSelection).getAddress());
                        phone_number_id.setText(((Customer) newSelection).getPhone());
                        postal_code_id.setText(((Customer) newSelection).getPostalCode());
                        division_combo_id.setValue(((Customer) newSelection).getDivision());
                        country_combo_id.setValue(((Customer) newSelection).getCountry());

//                        ComboInterface divisionCombo = new DivisionCombo();
//                        ComboInterface countryCombo = new CountryCombo();
//                        divisionCombo.setComboBox((Customer) newSelection, division_combo_id);
//                        countryCombo.setComboBox((Customer) newSelection, country_combo_id);
                    }
                }
        );

        country_combo_id.setOnAction(actionEvent -> {
            if (division_combo_id.getValue() == null | country_combo_id.getValue() == null) {
                division_combo_id.setItems(DBDivisions.getDivisions(country_combo_id.getValue().getCountryId()));
            }
        });

    }

    public void clearFormButtonOnAction(ActionEvent actionEvent) {
        // Clear all fields and set ComboBoxes to first item
        table_view_id.getSelectionModel().clearSelection();
        customer_id_id.clear();
        customer_name_id.clear();
        address_id.clear();
        postal_code_id.clear();
        phone_number_id.clear();
        resetComboBoxes();
    }

    //FIXME - When resetting the combo boxes, this invalidates the logic used to set the country and division in the below methods.
    private void resetComboBoxes() {
    }

    private boolean isValuesChanged() {
        if (!table_view_id.getSelectionModel().isEmpty()) {
            isValuesChanged = !(((Customer) table_view_id.getSelectionModel().getSelectedItem()).getName().equals(customer_name_id.getText()) &
                    ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getAddress().equals(address_id.getText()) &
                    ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getPhone().equals(phone_number_id.getText()) &
                    ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getPostalCode().equals(postal_code_id.getText()) &
                    ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getCountry().equals(country_combo_id.getValue()) &
                    ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getDivision().equals(division_combo_id.getValue())
            );
            new Test("Name matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getName().equals(customer_name_id.getText()));
            new Test("Address matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getAddress().equals(address_id.getText()));
            new Test("Phone matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getPhone().equals(phone_number_id.getText()));
            new Test("Postal matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getPostalCode().equals(postal_code_id.getText()));
            new Test("Country matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getCountry().equals(country_combo_id.getValue()));
            new Test("Division matches: " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getDivision().equals(division_combo_id.getValue()));
        } else {
            isValuesChanged = false;
        }
        return isValuesChanged;
    }

    //FIXME
    // - after saving, repopulate the textfields from the database so that you can get the customer ID value
    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (table_view_id.getSelectionModel().isEmpty()) {// Make a new Customer
            if (isMissingTextFieldValues()) { // Alert user of missing fields
                Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid/Missing values provided");
                alert.showAndWait();
            } else { // Save new customer information
                DBCustomers.insertCustomer(
                        customer_name_id.getText().trim(),
                        address_id.getText().trim(),
                        postal_code_id.getText().trim(),
                        phone_number_id.getText().trim(),
                        division_combo_id.getSelectionModel().getSelectedItem().getDivisionId(),
                        Main.user
                );
                repopulateTaleView();
            }
        } else { // Update an existing Customer
            if (isMissingTextFieldValues()) { // Alert user of missing fields
                Alert alert = new Alert(Alert.AlertType.WARNING, "Missing TextField values");
                alert.showAndWait();
            } else if (isValuesChanged()) { // If any changes made to field, alert user before updating
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save changes?");
                alert.setTitle("Save or Discard");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    DBCustomers.updateCustomer(
                            new Customer(
                                    Integer.parseInt(customer_id_id.getText().trim()),
                                    customer_name_id.getText().trim(),
                                    address_id.getText().trim(),
                                    postal_code_id.getText().trim(),
                                    phone_number_id.getText().trim(),
                                    division_combo_id.getSelectionModel().getSelectedItem()
                            )
                    );
                    repopulateTaleView();
                }
            }
        }
    }

    public void repopulateTaleView() {
        customerObservableList = DBCustomers.getAllCustomers();
        table_view_id.setItems(customerObservableList);
        table_view_id.refresh(); // not necessary?
    }

    public void deleteCustomerButtonOnAction(ActionEvent actionEvent) {
        if (!table_view_id.getSelectionModel().isEmpty()) {
            DBCustomers.deleteCustomerById(((Customer) table_view_id.getSelectionModel().getSelectedItem()).getId());
            table_view_id.getSelectionModel().clearSelection();

            // Repopulate the table
            customerObservableList = DBCustomers.getAllCustomers();
            table_view_id.setItems(customerObservableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing to delete!");
            alert.setTitle("Select an item to delete!");
            alert.showAndWait();
        }
    }

    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        //FIXME
        // - if logout button pressed and table row was selected and if field was changed, prompt alert that something was changed
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

    private boolean isValidTextField(TextField textField) {
        String regex = "^[^\\s].*\\S"; // Can't start with a whitespace and matches 1 or more characters and can't end with a whitespace
        return textField.getText().matches(regex);
    }

    private boolean isMissingTextFieldValues() {
        return !(isValidTextField(customer_name_id) & isValidTextField(address_id) & isValidTextField(postal_code_id) & isValidTextField(phone_number_id));
    }

    public void customerNameOnKeyTyped(KeyEvent keyEvent) {
        isCustomerNameFieldValid = isValidTextField((TextField) keyEvent.getSource());
        if (isCustomerNameFieldValid) {
            customer_name_id.setStyle("-fx-background-color: white");
        } else {
            customer_name_id.setStyle("-fx-background-color: pink");
        }
    }

    public void addressOnKeyTyped(KeyEvent keyEvent) {
        isAddressFieldValid = isValidTextField((TextField) keyEvent.getSource());
        if (isAddressFieldValid) {
            address_id.setStyle("-fx-background-color: white");
        } else {
            address_id.setStyle("-fx-background-color: pink");
        }
    }

    public void postalCodeOnKeyTyped(KeyEvent keyEvent) {
        isPostalCodeFieldValid = isValidTextField((TextField) keyEvent.getSource());
        if (isPostalCodeFieldValid) {
            postal_code_id.setStyle("-fx-background-color: white");
        } else {
            postal_code_id.setStyle("-fx-background-color: pink");
        }
    }

    public void phoneNumberOnKeyTyped(KeyEvent keyEvent) {
        isPhoneNumberFieldValid = isValidTextField((TextField) keyEvent.getSource());
        if (isPhoneNumberFieldValid) {
            phone_number_id.setStyle("-fx-background-color: white");
        } else {
            phone_number_id.setStyle("-fx-background-color: pink");
        }
    }

    public void viewAppointmentsButtonOnAction(ActionEvent actionEvent) throws IOException {
        //TODO Alert user if any changes were made to Fields
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
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

    @Deprecated
    public void divisionComboBoxOnAction(ActionEvent actionEvent) {
        // Not sure what to do here.
    }

    @Deprecated
    public void customerIdOnAction(ActionEvent actionEvent) {
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

    public void reportsButtonOnAction(ActionEvent actionEvent) throws IOException {
        //TODO Alert user if any changes were made to Fields
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setTitle("Reports");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

