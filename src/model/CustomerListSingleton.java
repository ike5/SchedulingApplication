package model;

import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance a Customer object
 * in order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class CustomerListSingleton {
    private ObservableList<Customer> customerObservableList;

    private final static CustomerListSingleton INSTANCE = new CustomerListSingleton();

    private CustomerListSingleton() {}

    /**
     * Get instance customer list singleton.
     *
     * @return the customer list singleton
     */
    public static CustomerListSingleton getInstance(){
        return INSTANCE;
    }

    /**
     * Set customer observable list.
     *
     * @param customerObservableList the customer observable list
     */
    public void setCustomerObservableList(ObservableList<Customer> customerObservableList){
        this.customerObservableList = customerObservableList;
    }

    /**
     * Get customer observable list observable list.
     *
     * @return the observable list
     */
    public ObservableList<Customer> getCustomerObservableList(){
        return this.customerObservableList;
    }
}
