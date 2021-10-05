package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.util.concurrent.atomic.AtomicReference;

public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers() {
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

    public static Customer editCustomer(Customer customer) {

        AtomicReference<Customer> C = null;
        String sql = "UPDATE customers SET" +
                " Customer_Name = " + customer.getName() +
                ", Address = " + customer.getAddress() +
                ", Postal_Code = " + customer.getPostal() +
                ", Phone = " + customer.getPhone() +
                ", Division_ID = " + customer.getDivisionID() +
                " WHERE Customer_ID = " + customer.getId();

        ProcessQuery.process(sql, resultSet -> {
            assert false;
            C.set(new Customer(
                    resultSet.getInt("Customer_ID"),
                    resultSet.getString("Customer_Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Postal_Code"),
                    resultSet.getString("Phone"),
                    resultSet.getInt("Division_ID")
            ));

        });
        return C.get();

    }
}
