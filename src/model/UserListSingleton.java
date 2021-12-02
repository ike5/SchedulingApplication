package model;

import javafx.collections.ObservableList;

/**
 * This class is used for holding a list of all users.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class UserListSingleton {
    private ObservableList<User> userObservableList;

    private static final UserListSingleton INSTANCE = new UserListSingleton();

    private UserListSingleton() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserListSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * Sets user observable list.
     *
     * @param userObservableList the user observable list
     */
    public void setUserObservableList(ObservableList<User> userObservableList) {
        this.userObservableList = userObservableList;
    }

    /**
     * Gets user observable list.
     *
     * @return the user observable list
     */
    public ObservableList<User> getUserObservableList() {
        return this.userObservableList;
    }
}
