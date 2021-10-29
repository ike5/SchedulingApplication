package model;

import javafx.collections.ObservableList;

public final class ContactsSingleton {
    private ObservableList<Contact> contactObservableList;

    private static final ContactsSingleton INSTANCE = new ContactsSingleton();

    private ContactsSingleton() {
    }

    public static ContactsSingleton getInstance() {
        return INSTANCE;
    }

    public void setContactObservableList(ObservableList<Contact> contactObservableList) {
        this.contactObservableList = contactObservableList;
    }

    public ObservableList<Contact> getContactObservableList() {
        return this.contactObservableList;
    }

}
