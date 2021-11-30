package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class must be instantiated in order to use the methods. The reason behind this is that a static User object can
 * then be referred to throughout the lifecycle of the program.
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userObservableList;
    }

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


    public static User getUser(int userId) {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_ID = ?";
        User user = null;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public User getUser() {
        return user;
    }
}

