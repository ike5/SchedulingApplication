package model;

import javafx.collections.ObservableList;

public final class DivisionSingleton {
    private ObservableList<Division> divisionObservableList;

    private final static DivisionSingleton INSTANCE = new DivisionSingleton();

    private DivisionSingleton(){}

    public static DivisionSingleton getInstance(){
        return INSTANCE;
    }

    public void setDivisionObservableList(ObservableList<Division> divisionObservableList){
        this.divisionObservableList = divisionObservableList;
    }

    public ObservableList<Division> getDivisionObservableList(){
        return this.divisionObservableList;
    }
}
