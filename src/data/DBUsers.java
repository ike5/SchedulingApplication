package data;

import javafx.collections.ObservableList;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class must be instantiated in order to use the methods. The reason behind this is that a static User object can
 * then be referred to throughout the lifecycle of the program.
 */
public class DBUsers {
    private final String providedPassword;
    private static User user;

    public DBUsers(String username, String password) {
        user = new User(username, password);
        this.providedPassword = password;
        validateUsernamePassword(); //Sets User object's isValidUsername and isValidPassword values
    }

    @Deprecated
    public User getUser(String username, String password) {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?";
        User user = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return password.equals(user.getPassword()) ? user : null;
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


    @Deprecated
    public ObservableList<User> getAllUsers() {
        return null;
    }

    public User getUser() {
        return user;
    }
}

