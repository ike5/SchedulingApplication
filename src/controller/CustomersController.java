package controller;

import data.DBAppointment;
import data.DBDivisions;
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

    public void customerNameOnKeyTyped(KeyEvent keyEvent) {
        isCustomerNameFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    public void addressOnKeyTyped(KeyEvent keyEvent) {
        isAddressFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    public void postalCodeOnKeyTyped(KeyEvent keyEvent) {
        isPostalCodeFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    public void phoneNumberOnKeyTyped(KeyEvent keyEvent) {
        isPhoneNumberFieldValid = isValidTextField((TextField) keyEvent.getSource());
        disableButtonsLogic();
    }

    /**
     * Button
     *
     * @param actionEvent
     */
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
     * Button
     *
     * @param actionEvent
     */
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
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = Messages.confirmationMessage("Logout?", "Confirm logout?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            logout(actionEvent);
        }
    }

    /**
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void viewAppointmentsButtonOnAction(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, "/view/Appointments.fxml", "Appointments");
    }

    /**
     * Button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void reportsButtonOnAction(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, "/view/Reports.fxml", "Reports");
    }

    /**
     * Button
     *
     * @param actionEvent
     */
    public void saveButtonOnAction(ActionEvent actionEvent) {
        boolean isEmptyTableView = table_view_id.getSelectionModel().isEmpty();
        boolean isMissingComboBoxValues = isMissingComboBoxValues();
        new Test("isEmptyTableView: " + isEmptyTableView);
        new Test("isMissingComboBoxValues: " + isMissingComboBoxValues);

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
     * Helper
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
     * Helper
     */
    private void populateTableView() {
        customerObservableList = DBCustomers.getAllCustomers();
        table_view_id.setItems(customerObservableList);
        table_view_id.refresh(); // not necessary?
    }

    /**
     * Helper
     *
     * @param actionEvent
     * @throws IOException
     */
    private void logout(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, "/view/LoginScreen.fxml", "login");
    }

    /**
     * Helper
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
     * Helper
     */
    private void invalidateAllTextFields() {
        boolean isValid = false;
        isCustomerNameFieldValid = isValid;
        isAddressFieldValid = isValid;
        isPostalCodeFieldValid = isValid;
        isPhoneNumberFieldValid = isValid;
    }

    /**
     * Helper
     *
     * @param actionEvent
     * @param path
     * @param title
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
     * Helper
     *
     * @param isFieldValid
     * @param textFieldId
     */
    private void textFieldValidationColor(boolean isFieldValid, TextField textFieldId) {
        if (isFieldValid) {
            new Test(isFieldValid);
            textFieldId.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            textFieldId.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
            textFieldId.setStyle("-fx-background-color: pink");
        }
    }

    /**
     * Validation
     *
     * @return
     */
    private boolean isMissingComboBoxValues() {
        return country_combo_id.getSelectionModel().isEmpty() ||
                division_combo_id.getSelectionModel().isEmpty();
    }

    /**
     * Validation
     *
     * @param textField
     * @return
     */
    private boolean isValidTextField(TextField textField) {
        // Can't start with a whitespace and matches 1 or more characters
        String regex = "^[^\\s].*";
        return textField.getText().matches(regex);
    }

    /**
     * Validation
     *
     * @return Returns true if valid, false if one or more fields is invalid.
     */
    private boolean allTextFieldsValid() {
        return isCustomerNameFieldValid &&
                isAddressFieldValid &&
                isPostalCodeFieldValid &&
                isPhoneNumberFieldValid;
    }
}

