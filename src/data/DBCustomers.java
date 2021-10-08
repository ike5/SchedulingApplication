package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.ProcessQuery;

public class DBCustomers extends JDBC {
    public Customer addCustomer() {
        return null;
    }

    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        // widen query SELECT *
        // select * from customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID;
        ProcessQuery.process("select * from customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID", resultSet -> {
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
        });
        return customerList;
    }

    public Customer getCustomer(int customerId) {
        return null;
    }



    // FIXME
    //
    public Customer editCustomer(Customer customer) {
//        AtomicReference<Customer> C = null;
        String sql = "UPDATE customers SET" +
                " Customer_Name = " + customer.getName() +
                ", Address = " + customer.getAddress() +
                ", Postal_Code = " + customer.getPostal() +
                ", Phone = " + customer.getPhone() +
                ", Division_ID = " + customer.getDivisionID() +
                " WHERE Customer_ID = " + customer.getId();

//        ProcessQuery.process(sql, resultSet -> {
//            assert false;
//            C.set(new Customer(
//                    resultSet.getInt("Customer_ID"),
//                    resultSet.getString("Customer_Name"),
//                    resultSet.getString("Address"),
//                    resultSet.getString("Postal_Code"),
//                    resultSet.getString("Phone"),
//                    resultSet.getInt("Division_ID")
//            ));
//
//        });
        return null;
    }

    public boolean deleteCustomer(Customer customer) {
        return false;
    }


}
