package model;

import javafx.collections.ObservableList;

/**
 * This class is used to provide a single instance of a Contacts object in
 * order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class ContactsListSingleton {
    private ObservableList<Contact> contactObservableList;

    private static final ContactsListSingleton INSTANCE = new ContactsListSingleton();

    private ContactsListSingleton() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ContactsListSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * Sets contact observable list.
     *
     * @param contactObservableList the contact observable list
     */
    public void setContactObservableList(ObservableList<Contact> contactObservableList) {
        this.contactObservableList = contactObservableList;
    }

    /**
     * Gets contact observable list.
     *
     * @return the contact observable list
     */
    public ObservableList<Contact> getContactObservableList() {
        return this.contactObservableList;
    }

}
