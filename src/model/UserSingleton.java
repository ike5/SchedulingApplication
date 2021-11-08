package model;

import javafx.collections.ObservableList;

/**
 * This class is used for holding a list of all users.
 */
public final class UserSingleton {
    private ObservableList<User> userObservableList;

    private static final UserSingleton INSTANCE = new UserSingleton();

    private UserSingleton() {
    }

    public static UserSingleton getInstance() {
        return INSTANCE;
    }

    public void setUserObservableList(ObservableList<User> userObservableList) {
        this.userObservableList = userObservableList;
    }

    public ObservableList<User> getUserObservableList() {
        return this.userObservableList;
    }
}
