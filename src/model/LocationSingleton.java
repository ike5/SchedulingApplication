package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class LocationSingleton {
    private ObservableList<String> locationObservableList;

    private final static LocationSingleton INSTANCE = new LocationSingleton();

    private LocationSingleton(){
        setLocationObservableList();
    }

    public static LocationSingleton getInstance(){ return INSTANCE; }

    public void setLocationObservableList(){
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

    public ObservableList<String> getLocationObservableList(){
        return this.locationObservableList;
    }


}
