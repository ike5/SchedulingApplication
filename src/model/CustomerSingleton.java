package model;

import javafx.collections.ObservableList;

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
