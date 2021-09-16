package model;

import javafx.collections.ObservableList;

public class Country {
    private int id;
    private String name;

    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Country(int id, DivisionId divisionId){
        this.id = id;
        this.name = divisionId.getCountryName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
