package model;

import javafx.collections.ObservableList;

/**
 * This class is used for holding an all listed customers in the database.
 */
public final class CustomerSingleton {
    private ObservableList<Customer> customerObservableList;

    private final static CustomerSingleton INSTANCE = new CustomerSingleton();

    private CustomerSingleton() {}

    public static CustomerSingleton getInstance(){
        return INSTANCE;
    }

    public void setCustomerObservableList(ObservableList<Customer> customerObservableList){
        this.customerObservableList = customerObservableList;
    }

    public ObservableList<Customer> getCustomerObservableList(){
        return this.customerObservableList;
    }
}
