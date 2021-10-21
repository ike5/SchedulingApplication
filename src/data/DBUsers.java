package data;

import javafx.collections.ObservableList;
import model.User;
import utils.ProcessQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//TODO
// - Create READ method

/**
 * This class must be instantiated in order to use the methods. The reason behind this is that a static User object can
 * then be referred to throughout the lifecycle of the program.
 */
public class DBUsers {
    private final String providedPassword;
    private static User user;

    public DBUsers(String username, String password) {
        this.user = new User(username, password);
        this.providedPassword = password;
        validateUsernamePassword(); //Sets User object's isValidUsername and isValidPassword values
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        DBUsers dbUsers = new DBUsers("test", "test");
        boolean u = dbUsers.getUser().isValidUsername();
        boolean p = dbUsers.getUser().isValidPassword();

        System.out.println("Username is valid: " + u);
        System.out.println("Password is valid: " + p);
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

    /**
     * Make call to database to get actual information. This method does not validate anything
     * but rather completes the call to the database so that userExists() and passwordMatches()
     * can check against the data.
     * <p>
     * The ResultStatement does not iterate more than once--so for security it expects an exact match.
     */
    private void validateUsernamePassword() {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet resultSet = ps.executeQuery();

            // if a ResultSet row exists, then the provided User_Name exists
            if (resultSet.next()) {
//                // Set fields of the User object
//                user.setUserId(resultSet.getInt("User_ID"));
//                user.setUsername(resultSet.getString("User_Name"));
//                user.setPassword(resultSet.getString("Password"));
//
//                // Set valid username flag to true
//                user.setValidUsername(true);
//
//                // Check password against provided password
//                if (user.getPassword().equals(providedPassword)) {
//                    user.setValidPassword(true);
//                }

                user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password"),
                        true,
                        resultSet.getString("Password").equals(providedPassword)
                );
            } else {
//                user.setUserId(-1);     //sentinel value showing invalid username
//                user.setUsername(null);
//                user.setPassword(null);
//
//                // Set valid username flag to false
//                // Set valid password flag to false by default
//                user.setValidUsername(false);
//                user.setValidPassword(false);

                user = new User(-1, null, null, false, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks whether provided username for the DBUsers object exists in database.
     *
     * @return returns true if ResultSet isn't null. If ResultSet isn't null, then the database
     * holds a username that matches the provided username.
     */
    @Deprecated
    public Boolean userExists() {
        return user.getUsername() != null;
    }

    @Deprecated
    public static boolean passwordMatches(User user) {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    /**
     * Checks whether provided password matches password in database for the username.
     *
     * @return returns true if provided password matches database password.
     */
    @Deprecated
    public Boolean passwordMatches() {
        return user.getPassword().equals(providedPassword);
    }


    @Deprecated
    public ObservableList<User> getAllUsers() {
        return null;
    }

    public User getUser() {
        return user;
    }

}

