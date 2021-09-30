package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionId = rs.getInt("Division_ID");

                Customer C = new Customer(
                        customerId,
                        customerName,
                        customerAddress,
                        customerPostalCode,
                        customerPhone,
                        customerDivisionId
                );

                customerList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    public static Customer editCustomer(Customer customer) {
        Customer C = null;

        try {
            String sql = "UPDATE customers SET" +
                    " Customer_Name = " + customer.getName() +
                    ", Address = " + customer.getAddress() +
                    ", Postal_Code = " + customer.getPostal() +
                    ", Phone = " + customer.getPhone() +
                    ", Division_ID = " + customer.getDivisionID() +
                    " WHERE Customer_ID = " + customer.getId();

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionId = rs.getInt("Division_ID");

                C = new Customer(
                        customerId,
                        customerName,
                        customerAddress,
                        customerPostalCode,
                        customerPhone,
                        customerDivisionId
                );
            }

            return C;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return C;
    }
}
