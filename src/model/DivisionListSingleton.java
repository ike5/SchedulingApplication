package model;

import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance of a Division object
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DivisionListSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * Sets division observable list.
     *
     * @param divisionObservableList the division observable list
     */
    public void setDivisionObservableList(ObservableList<Division> divisionObservableList) {
        this.divisionObservableList = divisionObservableList;
    }

    /**
     * Gets division observable list.
     *
     * @return the division observable list
     */
    public ObservableList<Division> getDivisionObservableList() {
        return this.divisionObservableList;
    }
}
