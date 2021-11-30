package model;

import javafx.collections.ObservableList;

/**
 * This class is used for holding an all listed customers in the database.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class CustomerListSingleton {
    private ObservableList<Customer> customerObservableList;

    private final static CustomerListSingleton INSTANCE = new CustomerListSingleton();

    private CustomerListSingleton() {}

    public static CustomerListSingleton getInstance(){
        return INSTANCE;
    }

    public void setCustomerObservableList(ObservableList<Customer> customerObservableList){
        this.customerObservableList = customerObservableList;
    }

    public ObservableList<Customer> getCustomerObservableList(){
        return this.customerObservableList;
    }
}
