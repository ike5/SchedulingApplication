package model;

import javafx.collections.ObservableList;

/**
 * This class is used for holding a list of all users.
 */
public final class UserListSingleton {
    private ObservableList<User> userObservableList;

    private static final UserListSingleton INSTANCE = new UserListSingleton();

    private UserListSingleton() {
    }

    public static UserListSingleton getInstance() {
        return INSTANCE;
    }

    public void setUserObservableList(ObservableList<User> userObservableList) {
        this.userObservableList = userObservableList;
    }

    public ObservableList<User> getUserObservableList() {
        return this.userObservableList;
    }
}
