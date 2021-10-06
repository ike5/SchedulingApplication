package utils;

import model.Customer;

import java.util.concurrent.atomic.AtomicReference;

public abstract class DBUpdate {
    public static Customer updateCustomer(Customer customer) {
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
