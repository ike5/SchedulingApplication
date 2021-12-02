package model;

import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance of a Country object
 * in order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class CountryListSingleton {
    private ObservableList<Country> countryObservableList;

    private final static CountryListSingleton INSTANCE = new CountryListSingleton();

    private CountryListSingleton(){}

    /**
     * Get instance country list singleton.
     *
     * @return the country list singleton
     */
    public static CountryListSingleton getInstance(){
        return INSTANCE;
    }

    /**
     * Set country observable list.
     *
     * @param countryObservableList the country observable list
     */
    public void setCountryObservableList(ObservableList<Country> countryObservableList){
        this.countryObservableList = countryObservableList;
    }

    /**
     * Get country observable list observable list.
     *
     * @return the observable list
     */
    public ObservableList<Country> getCountryObservableList(){
        return this.countryObservableList;
    }
}
