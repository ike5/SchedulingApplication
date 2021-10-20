package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;
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


    /**
     * Inserts a customer into the customers database table provided a Customer object.
     * A customer entry can exist on its own, but must reference a first_level_division object
     * and a country object.
     *
     * @param customer The Customer object to insert
     * @return Returns -1 if insert unsuccessful and a value >= 1 if successful.
     */
    public int insertCustomer(Customer customer) {
        String sql = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, null, null, CURRENT_TIMESTAMP, null, ?)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, customer.getDivision().getDivisionId());

            ps.execute();

//            //first_level_divisions is READ ONLY, why do you need to make an entry?
//            // - Maybe validate that the first_level_division exists first?
//            String sql2 = "INSERT INTO first_level_divisions VALUES (?, ?, null, null, CURRENT_TIMESTAMP, null, ?)";
//            PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql2);
//            ps2.setInt(1, customer.getDivision().getDivisionId());
//            ps2.setString(2, customer.getDivision().getDivisionName());
//            ps2.setInt(3, customer.getDivision().getCountry().getCountryId());


        } catch (SQLException e) {
            e.printStackTrace();
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
//        String sql = "SELECT client_schedule.customers.Customer_ID,\n" +
//                "       client_schedule.customers.Customer_Name,\n" +
//                "       client_schedule.customers.Address,\n" +
//                "       client_schedule.customers.Postal_Code,\n" +
//                "       client_schedule.customers.Phone,\n" +
//                "       client_schedule.customers.Division_ID,\n" +
//                "       client_schedule.first_level_divisions.Division_ID,\n" +
//                "       client_schedule.first_level_divisions.Division,\n" +
//                "       client_schedule.countries.Country_ID,\n" +
//                "       client_schedule.countries.Country\n" +
//                "FROM client_schedule.customers,\n" +
//                "     client_schedule.first_level_divisions,\n" +
//                "     client_schedule.countries\n" +
//                "WHERE client_schedule.customers.Division_ID = client_schedule.first_level_divisions.Division_ID\n" +
//                "AND client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID;";
        String sql_customers = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";
        
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            PreparedStatement psCustomer = JDBC.getConnection().prepareStatement(sql_customers);
            ResultSet resultSetCustomers = psCustomer.executeQuery();

            while (resultSetCustomers.next()) {
                String sql_div = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?";
                PreparedStatement psDiv = JDBC.getConnection().prepareStatement(sql_div);
                psDiv.setInt(1, resultSetCustomers.getInt("Division_ID"));
                ResultSet resultSetDiv = psDiv.executeQuery();
                resultSetDiv.next();

                String sql_country = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
                PreparedStatement psCountry = JDBC.getConnection().prepareStatement(sql_country);
                psCountry.setInt(1, resultSetDiv.getInt("Country_ID"));
                ResultSet resultSetCountry = psCountry.executeQuery();
                resultSetCountry.next();
                //FIXME - The issue here is that when I want to get the Division column, the compiler says that
                // it doesn't exist. The above sql statement proves that it exists however, so I am not sure
                // where the error lies.
                Customer customer = new Customer(
                        resultSetCustomers.getInt(1),                // Customer_ID
                        resultSetCustomers.getString(2),              // Customer_Name
                        resultSetCustomers.getString(3),             // Address
                        resultSetCustomers.getString(4),             // Postal_Code
                        resultSetCustomers.getString(5),             // Phone
                        new Division(
                                resultSetDiv.getInt(1),        // first_level_divisions.Division_ID
                                resultSetDiv.getString(2),             // first_level_divisions.Division
                                //FIXME - a Division should take a Country object
                                resultSetCountry.getInt(1),               // Country_ID
                                resultSetCountry.getString(2)             // Country
                        )
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
    public static Customer getCustomer(int customerId) {
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
        Customer customer = null;
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

            //FIXME - fix return statement
//            return getCustomer(customer.getId()); // returns updated customer object from database
            return null;
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
