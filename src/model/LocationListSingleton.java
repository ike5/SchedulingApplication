package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance a LocationList object
 * in order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class LocationListSingleton {
    private ObservableList<String> locationObservableList;

    private final static LocationListSingleton INSTANCE = new LocationListSingleton();

    private LocationListSingleton() {
        setLocationObservableList();
    }

    public static LocationListSingleton getInstance() {
        return INSTANCE;
    }

    private void setLocationObservableList() {
        locationObservableList = FXCollections.observableArrayList(
                "Main Office",
                "Online",
                "East Office",
                "West Office",
                "North Office",
                "South Office",
                "London Office",
                "France Office"
        );
    }

    public ObservableList<String> getLocationObservableList() {
        return this.locationObservableList;
    }


}
