package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class TypeSingleton {
    private ObservableList<String> typeObservableList;

    private final static TypeSingleton INSTANCE = new TypeSingleton();

    private TypeSingleton() {
        setTypeObservableList();
    }

    public static TypeSingleton getInstance() {
        return INSTANCE;
    }

    public void setTypeObservableList() {
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
