package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Customer;
import model.Division;
import model.User;

import java.sql.*;

/**
 * This class provides CRUD functionality to the customers database table.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBCustomers {


    /**
     * Inserts a customer into the customers database table.
     * <p>
     * Dependencies:
     * <li>All divisionId data must be validated</li>
     * <li>{@link Main#user} must be instantiated</li>
     *
     * @param customerName The Customer_Name field
     * @param address      The Address field
     * @param postalCode   The Postal_Code field
     * @param phone        The Phone field
     * @param divisionId   The Division_ID foreign key constraint
     * @return Returns a new Customer object or null if entry was unsuccessful.
     */
    public static Customer insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId, User user) {
        Customer customer = null;

        String sql_cus = "INSERT INTO customers VALUES(Null, ?, ?, ?, ?, current_timestamp, ?, current_timestamp, ?, ?)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql_cus, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getUsername());
            ps.setInt(7, divisionId);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            rs.next();
            int customerIdKey = rs.getInt(1);

            customer = DBCustomers.getCustomer(customerIdKey);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Returns an ObservableList<Customer> object of all customers in the
     * customer database table.
     *
     * @return an ObservableList<Customer> object or null if no entries.
     */
    public static ObservableList<Customer> getAllCustomers() {
        String sql_customers = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            PreparedStatement psCustomer = JDBC.getConnection().prepareStatement(sql_customers);

            ResultSet resultSetCustomers = psCustomer.executeQuery();

            while (resultSetCustomers.next()) {
                String sql_div = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?";
                PreparedStatement ps_division = JDBC.getConnection().prepareStatement(sql_div);
                ps_division.setInt(1, resultSetCustomers.getInt("Division_ID"));

                ResultSet resultSetDiv = ps_division.executeQuery();

                resultSetDiv.next();

                String sql_country = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
                PreparedStatement ps_country = JDBC.getConnection().prepareStatement(sql_country);
                ps_country.setInt(1, resultSetDiv.getInt("Country_ID"));

                ResultSet resultSetCountry = ps_country.executeQuery();

                resultSetCountry.next();

                Customer customer = new Customer(
                        resultSetCustomers.getInt(1),                // Customer_ID
                        resultSetCustomers.getString(2),             // Customer_Name
                        resultSetCustomers.getString(3),             // Address
                        resultSetCustomers.getString(4),             // Postal_Code
                        resultSetCustomers.getString(5),             // Phone
                        new Division(
                                resultSetDiv.getInt(1),              // first_level_divisions.Division_ID
                                resultSetDiv.getString(2),           // first_level_divisions.Division
                                resultSetCountry.getInt(1),          // Country_ID
                                resultSetCountry.getString(2)        // Country
                        )
                );
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the total number of customers in customers database.
     *
     * @return An Integer representing the total number of customers
     */
    public static Integer getTotalNumberOfCustomers() {
        Integer counter = 0;

        for (Customer c : getAllCustomers()) {
            counter++;
        }
        return counter;
    }

    /**
     * Returns a Customer object provided a customer ID.
     *
     * @param customerId An integer representing the customer ID
     * @return Customer object or null if no such customer exists
     */
    public static Customer getCustomer(int customerId) {
        Customer customer = null;

        String sql = "SELECT client_schedule.customers.Customer_ID,\n" +
                "       client_schedule.customers.Customer_Name,\n" +
                "       client_schedule.customers.Address,\n" +
                "       client_schedule.customers.Postal_Code,\n" +
                "       client_schedule.customers.Phone,\n" +
                "       client_schedule.customers.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM client_schedule.customers,\n" +
                "     client_schedule.first_level_divisions,\n" +
                "     client_schedule.countries\n" +
                "WHERE client_schedule.customers.Division_ID = client_schedule.first_level_divisions.Division_ID\n" +
                "  AND client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID\n" +
                "  AND client_schedule.customers.Customer_ID = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        new Division(
                                resultSet.getInt("first_level_divisions.Division_ID"),
                                resultSet.getString("Division"),
                                resultSet.getInt("Country_ID"),
                                resultSet.getString("Country")
                        )
                );
            }
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
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
    public static Customer updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, Main.user.getUsername());
            ps.setInt(6, customer.getDivision().getDivisionId());
            ps.setInt(7, customer.getId());
            ps.executeUpdate();

            return DBCustomers.getCustomer(customer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // if unsuccessful
    }

    /**
     * Deletes customer from database table provided a customer ID integer.
     *
     * @param customerId An integer representing the customer ID
     * @return Returns -1 if unsuccessful and > 1 if successful
     */
    public static void deleteCustomerById(int customerId) {
        String sql_delete_appointments = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sql_delete_customer = "DELETE FROM customers WHERE Customer_ID = ?";

        try {
            // Update any existing appointments referencing the customer to make Customer_ID field null
            PreparedStatement ps_appointment = JDBC.getConnection().prepareStatement(sql_delete_appointments);
            ps_appointment.setInt(1, customerId);
            ps_appointment.executeUpdate();

            // Delete the customer
            PreparedStatement ps_customer = JDBC.getConnection().prepareStatement(sql_delete_customer);
            ps_customer.setInt(1, customerId);
            ps_customer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

