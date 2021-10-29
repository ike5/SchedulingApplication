package model;

import javafx.collections.ObservableList;

public final class CountrySingleton {
    private ObservableList<Country> countryObservableList;

    private final static CountrySingleton INSTANCE = new CountrySingleton();

    private CountrySingleton(){}

    public static CountrySingleton getInstance(){
        return INSTANCE;
    }

    public void setCountryObservableList(ObservableList<Country> countryObservableList){
        this.countryObservableList = countryObservableList;
    }

    public ObservableList<Country> getCountryObservableList(){
        return this.countryObservableList;
    }
}
