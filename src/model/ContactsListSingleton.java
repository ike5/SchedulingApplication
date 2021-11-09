package model;

import javafx.collections.ObservableList;

public final class ContactsListSingleton {
    private ObservableList<Contact> contactObservableList;

    private static final ContactsListSingleton INSTANCE = new ContactsListSingleton();

    private ContactsListSingleton() {
    }

    public static ContactsListSingleton getInstance() {
        return INSTANCE;
    }

    public void setContactObservableList(ObservableList<Contact> contactObservableList) {
        this.contactObservableList = contactObservableList;
    }

    public ObservableList<Contact> getContactObservableList() {
        return this.contactObservableList;
    }

}
