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

        // Alert User if any upcoming appointments for themselves
//        Appointment appointment = DBAppointment.getAppointmentByUser();
//        if(appointment != null){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming Appointment");
//            alert.setTitle("Appointment ID: " + appointment.getAppointmentId() +
//                    "\nAppointment Title: " + appointment.getAppointmentTitle() +
//                    "\nDate: " + appointment.getStartString());
//            alert.showAndWait();
//        }
    }

    private void disableButtons(boolean isSaveButtonDisabled, boolean isClearFormButtonDisabled, boolean isDeleteCustomerButtonDisabled) {
        save_button.setDisable(isSaveButtonDisabled);
        clear_form_button.setDisable(isClearFormButtonDisabled);
        delete_customer_button.setDisable(isDeleteCustomerButtonDisabled);
    }

    /**
     * Clears all fields
     *
     * @param actionEvent
     */
    public void clearFormButtonOnAction(ActionEvent actionEvent) {
        disableButtons(true, true, true);
        table_view_id.getSelectionModel().clearSelection();
        customer_id_id.clear();
        customer_name_id.clear();
        address_id.clear();
        postal_code_id.clear();
        phone_number_id.clear();
    }


    public void saveButtonOnAction(ActionEvent actionEvent) {
        boolean isEmptyTableView = table_view_id.getSelectionModel().isEmpty();
        boolean isMissingComboBoxValues = isMissingComboBoxValues();
        new Test("isEmptyTableView: " + isEmptyTableView);
        new Test("isMissingComboBoxValues: " + isMissingComboBoxValues);

        if (isEmptyTableView) {
            // Save new Customer
            if (isMissingComboBoxValues) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select a Country and State/Province");
                alert.setTitle("Missing ComboBox");
                alert.show();
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
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select a Country and State/Province");
                alert.setTitle("Missing ComboBox");
                alert.show();
            } else {
                Optional<ButtonType> result = getAlertAndWait("Save changes?", "Save or Discard");
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

    private boolean isMissingComboBoxValues() {
        return country_combo_id.getSelectionModel().isEmpty() || division_combo_id.getSelectionModel().isEmpty();
    }

    /**
     * Helper method that repopulates the Customers TableView.
     */
    private void populateTableView() {
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
        //FIXME If logout button pressed and table row was selected and if field was changed, prompt alert that something was changed
        Optional<ButtonType> result = getAlertAndWait("Logout?", "Confirm logout?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            logout(actionEvent);
        }
    }

    private void logout(ActionEvent actionEvent) throws IOException {
        switchView(actionEvent, "/view/LoginScreen.fxml", "login");
    }

    private Optional<ButtonType> getAlertAndWait(String alertMessage, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
        alert.setTitle(title);
        return alert.showAndWait();
    }

    private boolean isValidTextField(TextField textField) {
        // Can't start with a whitespace and matches 1 or more characters and can't end with a whitespace
        String regex = "^[^\\s].*\\S";
        return textField.getText().matches(regex);
    }

    private void validateAllTextFields(){

    }

    public void customerNameOnKeyTyped(KeyEvent keyEvent) {
        isCustomerNameFieldValid = isValidTextField((TextField) keyEvent.getSource());
        textFieldValidationColor(isCustomerNameFieldValid, customer_name_id);
    }

    public void addressOnKeyTyped(KeyEvent keyEvent) {
        isAddressFieldValid = isValidTextField((TextField) keyEvent.getSource());
        textFieldValidationColor(isAddressFieldValid, address_id);
    }

    public void postalCodeOnKeyTyped(KeyEvent keyEvent) {
        isPostalCodeFieldValid = isValidTextField((TextField) keyEvent.getSource());
        textFieldValidationColor(isPostalCodeFieldValid, postal_code_id);
    }

    public void phoneNumberOnKeyTyped(KeyEvent keyEvent) {
        isPhoneNumberFieldValid = isValidTextField((TextField) keyEvent.getSource());
        textFieldValidationColor(isPhoneNumberFieldValid, phone_number_id);
    }

    private void textFieldValidationColor(boolean isCustomerNameFieldValid, TextField customer_name_id) {
        if (isCustomerNameFieldValid) {
            customer_name_id.setStyle("-fx-background-color: white");
        } else {
            customer_name_id.setStyle("-fx-background-color: pink");
        }
    }

    public void viewAppointmentsButtonOnAction(ActionEvent actionEvent) throws IOException {
        //TODO Alert user if any changes were made to Fields
        switchView(actionEvent, "/view/Appointments.fxml", "Appointments");
    }

    public void reportsButtonOnAction(ActionEvent actionEvent) throws IOException {
        //TODO Alert user if any changes were made to Fields
        switchView(actionEvent, "/view/Reports.fxml", "Reports");
    }

    private void switchView(ActionEvent actionEvent, String path, String title) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

