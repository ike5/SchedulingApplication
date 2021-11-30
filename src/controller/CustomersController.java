package controller;

import data.DBAppointment;
import data.DBDivisions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
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

/**
 * This class displays all customers to the user. New customers can be added and existing customers can
 * be modified and saved to the database.
 */
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

    private static boolean isCustomerNameFieldValid;
    private static boolean isAddressFieldValid;
    private static boolean isPostalCodeFieldValid;
    private static boolean isPhoneNumberFieldValid;
    ObservableList<Customer> customerObservableList;

    /**
     * Initializes TableView with Customer objects. Disables the Save,
     * Clear, and Delete buttons. Initializes ComboBoxes. Adds a TableView
     * listener.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set focus options on buttons
        disableButtons(true, true, true);

        // Prevent users from changing touching customer id value
        customer_id_id.setDisable(true);

        // Populate table with Customers
        CustomerListSingleton.getInstance().setCustomerObservableList(DBCustomers.getAllCustomers());
        table_view_id.setItems(CustomerListSingleton.getInstance().getCustomerObservableList());

        // Bind table cell values to class getter methods
        // Tied to getter methods in the Customer class --> getDivisionId()
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
        // Use a Singleton in order to have only one copy of the observable list
        DivisionListSingleton.getInstance().setDivisionObservableList(DBDivisions.getAllFirstLevelDivisions());
        division_combo_id.setItems(DivisionListSingleton.getInstance().getDivisionObservableList());

        // TableView listener
        table_view_id.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        disableButtons(false, false, false);
                        customer_id_id.setText(String.valueOf(((Customer) newSelection).getId()));
                        customer_name_id.setText(((Customer) newSelection).getName());
                        address_id.setText(((Customer) newSelection).getAddress());
                        phone_number_id.setText(((Customer) newSelection).getPhone());
                        postal_code_id.setText(((Customer) newSelection).getPostalCode());

                        for (Country c : country_combo_id.getItems()) {
                            if (c.getCountryId() == ((Customer) newSelection).getCountryId()) {
                                country_combo_id.setValue(c);
                                break;
                            }
                        }

                        for (Division d : division_combo_id.getItems()) {
                            if (d.getDivisionId() == ((Customer) newSelection).getDivisionId()) {
                                division_combo_id.setValue(d);
                                break;
                            }
                        }
                    }
                }
        );

        country_combo_id.setOnAction(actionEvent -> {
            division_combo_id.setItems(DBDivisions.getDivisions(country_combo_id.getValue().getCountryId()));
        });
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    @FXML
    public void customerNameOnKeyTyped(KeyEvent keyEvent) {
        isCustomerNameFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    @FXML
    public void addressOnKeyTyped(KeyEvent keyEvent) {
        isAddressFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    @FXML
    public void postalCodeOnKeyTyped(KeyEvent keyEvent) {
        isPostalCodeFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    /**
     * Validates each key entry in the TextField.
     *
     * @param keyEvent A key event
     */
    @FXML
    public void phoneNumberOnKeyTyped(KeyEvent keyEvent) {
        isPhoneNumberFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    /**
     * Button to clear the form.
     *
     * @param actionEvent Clear Button pressed
     */
    @FXML
    public void clearFormButtonOnAction(ActionEvent actionEvent) {
        table_view_id.getSelectionModel().clearSelection();
        customer_id_id.clear();
        customer_name_id.clear();
        address_id.clear();
        postal_code_id.clear();
        phone_number_id.clear();
        disableButtons(true, true, true);
        invalidateAllTextFields();
    }

    /**
     * Button to delete a customer. Only active when a TableView item
     * is selected.
     *
     * @param actionEvent Delete Button pressed
     */
    @FXML
    public void deleteCustomerButtonOnAction(ActionEvent actionEvent) {
        if (!table_view_id.getSelectionModel().isEmpty()) {
            Optional<ButtonType> result = Messages
                    .confirmationMessage(
                            "Delete " + ((Customer) table_view_id.getSelectionModel().getSelectedItem()).getName() +
                                    "?", "Delete"
                    );
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBCustomers.deleteCustomerById(((Customer) table_view_id.getSelectionModel().getSelectedItem()).getId());
                table_view_id.getSelectionModel().clearSelection();

                // Repopulate the table
                customerObservableList = DBCustomers.getAllCustomers();
                table_view_id.setItems(customerObservableList);
            }
        } else {
            Messages.errorMessage("Nothing to delete!", "Select an item to delete");
        }
    }

    /**
     * Button to logout.
     *
     * @param actionEvent Logout Button pressed
     * @throws IOException
     */
    @FXML
    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = Messages.confirmationMessage("Logout?", "Confirm logout?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            logout(actionEvent);
        }
    }

    /**
     * Button to switch to the Appointments View.
     *
     * @param actionEvent Appointments Button pressed
     * @throws IOException
     */
    @FXML
    public void viewAppointmentsButtonOnAction(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, Main.resourceBundle.getString("appointments_screen"), "Appointments");
    }

    /**
     * Button to switch to the Reports View.
     *
     * @param actionEvent Reports Button pressed
     * @throws IOException
     */
    @FXML
    public void reportsButtonOnAction(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, Main.resourceBundle.getString("reports_screen"), "Reports");
    }

    /**
     * Button to save a new customer. If a customer id is provided
     * (a customer was selected and generates an id) then the save
     * button updates the existing customer values to those provided.
     *
     * @param actionEvent Save Button pressed
     */
    @FXML
    public void saveButtonOnAction(ActionEvent actionEvent) {
        boolean isEmptyTableView = table_view_id.getSelectionModel().isEmpty();
        boolean isMissingComboBoxValues = isMissingComboBoxValues();

        if (isEmptyTableView) {
            // Save new Customer
            if (isMissingComboBoxValues) {
                Messages.warningMessage("Select a Country and State/Province", "Missing selection");
            } else {
                DBCustomers.insertCustomer(
                        customer_name_id.getText().trim(),
                        address_id.getText().trim(),
                        postal_code_id.getText().trim(),
                        phone_number_id.getText().trim(),
                        division_combo_id.getSelectionModel().getSelectedItem().getDivisionId(),
                        Main.user
                );
                populateTableView();
            }
        } else {
            // Update selected Customer
            if (isMissingComboBoxValues) {
                Messages.warningMessage("Select a Country and State/Province", "Missing selection");
            } else {
                Optional<ButtonType> result = Messages.confirmationMessage("Save changes?", "Save or Discard");
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
                    populateTableView();
                    clearFormButtonOnAction(actionEvent);
                }
            }
        }
    }

    /**
     * Helper method to disable or enable buttons.
     *
     * @param isSaveButtonDisabled
     * @param isClearFormButtonDisabled
     * @param isDeleteCustomerButtonDisabled
     */
    private void disableButtons(boolean isSaveButtonDisabled, boolean isClearFormButtonDisabled, boolean isDeleteCustomerButtonDisabled) {
        save_button.setDisable(isSaveButtonDisabled);
        clear_form_button.setDisable(isClearFormButtonDisabled);
        delete_customer_button.setDisable(isDeleteCustomerButtonDisabled);
    }

    /**
     * Helper method to populate the TableView with all customers in
     * the database.
     */
    private void populateTableView() {
        customerObservableList = DBCustomers.getAllCustomers();
        table_view_id.setItems(customerObservableList);

        table_view_id.refresh();
    }

    /**
     * Helper method to logout.
     *
     * @param actionEvent
     * @throws IOException
     */
    private void logout(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, Main.resourceBundle.getString("login_screen"), "login");
    }

    /**
     * Helper to enable or disable Save, Clear, and Delete buttons.
     */
    private void disableButtonsLogic() {
        if (table_view_id.getSelectionModel().isEmpty()) {
            if (allTextFieldsValid()) {
                disableButtons(false, false, true);
            }
        } else { // if tableview is selected
            if (allTextFieldsValid()) {
                disableButtons(true, true, true);
            }
        }
    }

    /**
     * Helper method to invalidate all TextFields. Sets the boolean
     * values of each TextField to false.
     */
    private void invalidateAllTextFields() {
        boolean isValid = false;
        isCustomerNameFieldValid = isValid;
        isAddressFieldValid = isValid;
        isPostalCodeFieldValid = isValid;
        isPhoneNumberFieldValid = isValid;
    }

    /**
     * Helper method to switch Views.
     *
     * @param actionEvent The calling Button ActionEvent
     * @param path        The new View path
     * @param title       The title of the new View
     * @throws IOException
     */
    private void switchView(ActionEvent actionEvent, String path, String title) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Validates whether either Country or Division ComboBoxes have
     * been selected.
     *
     * @return Returns false if either ComboBox is unselected.
     */
    private boolean isMissingComboBoxValues() {
        return country_combo_id.getSelectionModel().isEmpty() ||
                division_combo_id.getSelectionModel().isEmpty();
    }

    /**
     * Validates text input in a TextField.
     *
     * @param textField The TextField to be validated
     * @return Returns false if text input starts with a whitespace
     */
    private boolean isValidTextField(TextField textField) {
        // Can't start with a whitespace and matches 1 or more characters
        String regex = "^[^\\s].*";
        return textField.getText().matches(regex);
    }

    /**
     * Validates all TextFields.
     *
     * @return Returns true if valid, false if one or more fields is
     * invalid.
     */
    private boolean allTextFieldsValid() {
        return isCustomerNameFieldValid &&
                isAddressFieldValid &&
                isPostalCodeFieldValid &&
                isPhoneNumberFieldValid;
    }
}

