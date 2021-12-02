package model;

import javafx.collections.ObservableList;
/**
 * This class is used to provide a single instance of a Country object
 * in order to simplify passing data between controllers.
 * @author Ike Maldonado
 * @version 1.0
 */
public final class CountryListSingleton {
    private ObservableList<Country> countryObservableList;

    private final static CountryListSingleton INSTANCE = new CountryListSingleton();

    private CountryListSingleton(){}

    public static CountryListSingleton getInstance(){
        return INSTANCE;
    }

    public void setCountryObservableList(ObservableList<Country> countryObservableList){
        this.countryObservableList = countryObservableList;
    }

    public ObservableList<Country> getCountryObservableList(){
        return this.countryObservableList;
    }
}
