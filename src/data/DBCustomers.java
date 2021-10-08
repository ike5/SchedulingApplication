package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.ProcessQuery;

import java.util.concurrent.atomic.AtomicReference;

interface CustomerDAO{
    // CREATE, READ, UPDATE, DELETE
    public Customer addCustomer(); // create
    public ObservableList<Customer> getAllCustomers(); // read
    public Customer getCustomer(int customerId); // read
    public Customer editCustomer(Customer customer); // update
    public boolean deleteCustomer(Customer customer); // delete
}

public class DBCustomers extends JDBC implements CustomerDAO{
    @Override
    public Customer addCustomer() {
        return null;
    }

    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        // widen query SELECT *
        // select * from customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID;
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

    @Override
    public Customer getCustomer(int customerId) {
        return null;
    }



    @Override
    public Customer editCustomer(Customer customer) {
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

    @Override
    public boolean deleteCustomer(Customer customer) {
        return false;
    }


}