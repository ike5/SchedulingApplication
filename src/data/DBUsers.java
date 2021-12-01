package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides CRUD functionality to the users database table. While
 * creating a new user in the database is not necessarily permitted, a User
 * object will be created when this class is instantiated. This User class
 * is used throughout the program to represent the credentials of the person
 * using the application.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBUsers {
    private final String providedPassword;
    private static User user;

    public DBUsers(String username, String password) {
        user = new User(username, password);
        this.providedPassword = password;
        validateUsernamePassword(); //Sets User object's isValidUsername and isValidPassword values
    }

    /**
     * Returns an ObservableList of all users in the database table.
     *
     * @return ObservableList<User> object or null if none.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();

        String sql = "SELECT User_ID, User_Name, Password FROM users";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );
                userObservableList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userObservableList;
    }

    /**
     * Helper method that validates a user's username and password. This
     * method sets the {@link #user} variable.
     */
    private void validateUsernamePassword() {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, user.getUsername());

            ResultSet resultSet = ps.executeQuery();

            // if a ResultSet row exists, then the provided User_Name exists
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password"),
                        true,
                        resultSet.getString("Password").equals(providedPassword)
                );
            } else {
                user = new User(-1, null, null, false, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns a User object provided a user id.
     *
     * @param userId An int representing the user id
     * @return Returns a User object
     */
    public static User getUser(int userId) {
        User user = null;

        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );
                user.setValidUsername(true);
                user.setValidPassword(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUser() {
        return user;
    }
}

