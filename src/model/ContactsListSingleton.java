package model;

import javafx.collections.ObservableList;

/**
 * @author Ike Maldonado
 * @version 1.0
 */
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
