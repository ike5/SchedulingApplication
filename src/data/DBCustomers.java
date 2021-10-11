package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.ProcessQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Completed CRUD functions
public class DBCustomers {

    /**
     * Inserts a customer into the customers database table. All data must be provided in order for this method to
     * work properly.
     *
     * @param customerName The Customer_Name field
     * @param address      The Address field
     * @param postalCode   The Postal_Code field
     * @param phone        The Phone field
     * @param divisionId   The Division_ID foreign key constraint
     * @return Returns a new Customer object or null if entry was unsuccessful.
     */
    public Customer insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (" +
                "'" + customerName + "', " +
                "'" + address + "', " +
                "'" + postalCode + "', " +
                "'" + phone + "', " + divisionId + ")";
        Customer customer = null;
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ps.executeUpdate();
            ResultSet resultSet = getAllCustomersResultSet();
            while (resultSet.next()) {
                resultSet.last(); // go to last row of table for efficiency
                customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getInt("Division_ID")
                );
            }
            return customer; // only the last entry is the customer created
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    /**
     * Inserts a customer into the customers database table provided a Customer object.
     *
     * @param customer The Customer object to insert
     * @return Returns -1 if insert unsuccessful and a value >= 1 if successful.
     */
    public int insertCustomerByObject(Customer customer) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (" +
                "'" + customer.getName() + "', " +
                "'" + customer.getAddress() + "', " +
                "'" + customer.getPostal() + "', " +
                "'" + customer.getPhone() + "', " + customer.getDivisionID() + ")";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }

    /**
     * Returns the ResultSet object of all customers in the customer database table.
     *
     * @return ResultSet object or null if query unsuccessful or if table empty
     */
    public ResultSet getAllCustomersResultSet() {
        String sql = "SELECT * FROM customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
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
                        resultSet.getInt("Division_ID")
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
                        resultSet.getInt("Division_ID")
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

            return getCustomer(customer.getId()); // returns updated customer object from database
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; // if unsuccessful
    }

    /**
     * Deletes customer from database table provided a Customer object.
     *
     * @param customer Customer object
     * @return Returns -1 if unsuccessful and > 1 if successful
     */
    public int deleteCustomer(Customer customer) {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customer.getId();
        // May have trouble with ON CASCADE DELETE since there are tied foreign key values.
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }

    /**
     * Deletes customer from database table provided a customer ID integer.
     *
     * @param customerId An integer representing the customer ID
     * @return Returns -1 if unsuccessful and > 1 if successful
     */
    public int deleteCustomerById(int customerId) {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerId;
        // You may have trouble deleting without first cascading delete other items in the database related to the customer
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }


}
