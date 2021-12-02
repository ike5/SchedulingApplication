package model;

import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance a Division object
 * in order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class DivisionListSingleton {
    private ObservableList<Division> divisionObservableList;

    private final static DivisionListSingleton INSTANCE = new DivisionListSingleton();

    private DivisionListSingleton() {
    }

    public static DivisionListSingleton getInstance() {
        return INSTANCE;
    }

    public void setDivisionObservableList(ObservableList<Division> divisionObservableList) {
        this.divisionObservableList = divisionObservableList;
    }

    public ObservableList<Division> getDivisionObservableList() {
        return this.divisionObservableList;
    }
}
