package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used for holding a list of all types.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class TypeListSingleton {
    private ObservableList<String> typeObservableList;

    private final static TypeListSingleton INSTANCE = new TypeListSingleton();

    private TypeListSingleton() {
        setTypeObservableList();
    }

    public static TypeListSingleton getInstance() {
        return INSTANCE;
    }

    private void setTypeObservableList() {
        typeObservableList = FXCollections.observableArrayList(
                "Debrief",
                "Urgent",
                "Same-Day",
                "Pre-Bookable",
                "Standard Consultation",
                "Long Consultation",
                "Follow-Up",
                "Consultation",
                "Training",
                "Phone Conference",
                "Advising"
        );
    }

    public ObservableList<String> getTypeObservableList() {
        return this.typeObservableList;
    }
}
