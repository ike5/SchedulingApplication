package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

public abstract class DBSelect {
    public static void select() {

    }

    public static ObservableList<Customer> selectAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        ProcessQuery.process("SELECT * FROM customers", resultSet -> {
            Customer customer = new Customer(
                    resultSet.getInt("Customer_ID"),
                    resultSet.getString("Customer_Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Postal_Code"),
                    resultSet.getString("Phone"),
                    resultSet.getInt("Division_ID")
            );
            customerList.add(customer);
        });
        return customerList;
    }
}
