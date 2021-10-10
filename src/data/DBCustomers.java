package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.ProcessQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO
// - Add CRUD functionality

public class DBCustomers {

    public Customer insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (" +
                "'" + customerName + "', " +
                "'" + address + "', " +
                "'" + postalCode + "', " +
                "'" + phone + "', " + divisionId + ")";
        Customer customer = null;
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            int sentinelValue = ps.executeUpdate();
//            ResultSet resultSet = getCustomerResultSet(sentinelValue);
            ResultSet resultSet = getAllCustomersResultSet();
            while (resultSet.next()) {
                System.out.println(
                        "Customer_ID: " + resultSet.getInt(1) +
                                "\t Customer_Name: " + resultSet.getString(2) +
                                "\t Address: " + resultSet.getString(3)
                );
            }
            return customer;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        // Division IDs must be real since they are foreign key constraints
        Customer customer = new DBCustomers().insertCustomer("Test 1 Smithf", "23423 Avenue street", "98432", "432-123-6656", 42);
        System.out.println(customer.getName());
    }

    /**
     * Returns the ResultSet object of all customers in the customer database table.
     *
     * @return ResultSet object or null if query unsuccessful or if table empty
     */
    public ResultSet getAllCustomersResultSet() {
        String sql = "SELECT * FROM customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet getCustomerResultSet(int customerId) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + customerId;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * Returns an ObservableList<Customer> object of all customers in the customer database table.
     *
     * @return an ObservableList<Customer> object or null if no entries.
     */
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getAllCustomersResultSet(); // helper method
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getInt("Division_ID"),
                        resultSet.getInt("COUNTRY_ID")
                );
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a Customer object provided a customer ID.
     *
     * @param customerId An integer representing the customer ID
     * @return Customer object or null if no such customer exists
     */
    public Customer getCustomer(int customerId) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + customerId;
        Customer customer = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getInt("Division_ID"),
                        resultSet.getInt("COUNTRY_ID")
                );
            }
            return customer;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }


    /**
     * Updates a customer entry in the customers database table provided a Customer object.
     *
     * @param customer A Customer object
     * @return The updated Customer object reflecting what's in the database after the change or null if
     * the update was unsuccessful.
     */
    public Customer updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET" +
                " Customer_Name = " + customer.getName() +
                ", Address = " + customer.getAddress() +
                ", Postal_Code = " + customer.getPostal() +
                ", Phone = " + customer.getPhone() +
                ", Division_ID = " + customer.getDivisionID() +
                " WHERE Customer_ID = " + customer.getId();

        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            int sentinelValue = ps.executeUpdate();

            return getCustomer(sentinelValue);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean deleteCustomer(Customer customer) {
        return false;
    }


}
