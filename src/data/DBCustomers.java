package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import test.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {
        //FIXME - Use a helper method instead of duplicating the code to insert a customer
//        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (" +
//                "'" + customerName + "', " +
//                "'" + address + "', " +
//                "'" + postalCode + "', " +
//                "'" + phone + "', " + divisionId + ")";
        String title = "default title";
        String description = "default description of things";
        String location = "default location";
        String type = "default type";
        String created_by = "default created_by";
        String last_updated_by = "default last_updated_by";
        int user_id = 1; // test
        int contact_id = 3; // Li Lee

        Customer customer = null;
        String sql_cus = "INSERT INTO customers VALUES(Null, ?, ?, ?, ?, current_timestamp, ?, current_timestamp, ?, ?)";

        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql_cus, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, "Created by test user");
            ps.setString(6, "Last updated by test user");
            // 5 = created_by
            // 6 = last_updated_by
            ps.setInt(7, divisionId);

            ps.execute();
//            ResultSet resultSet = getAllCustomersResultSet();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int customerId = rs.getInt(1);

            // Take acquired ID from above and put it into the next insert
            String sql_app = "INSERT INTO appointments VALUES (null, ?, ?, ?, ?, current_timestamp, current_timestamp, current_timestamp, ?, current_timestamp, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_app);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, created_by);
            preparedStatement.setString(6, last_updated_by);
            preparedStatement.setInt(7, customerId);
            preparedStatement.setInt(8, user_id);
            preparedStatement.setInt(9, contact_id);

            preparedStatement.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertCustomer("Ike Maldonado", "555 Franklin Street", "94102", "415-777-8950", 40);
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
                "'" + customer.getPostalCode() + "', " +
                "'" + customer.getPhone() + "', " + customer.getDivisionId() + ")";
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
    public static ResultSet getAllCustomersResultSet() {
        String sql = "SELECT * FROM customers";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public static ResultSet getCustomerResultSet(int customerId) {
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
    public static ObservableList<Customer> getAllCustomers() {
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
                ", Postal_Code = " + customer.getPostalCode() +
                ", Phone = " + customer.getPhone() +
                ", Division_ID = " + customer.getDivisionId() +
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
    public static int deleteCustomerById(int customerId) {
        //FIXME - is deleting, but not based off customerid
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }
}
