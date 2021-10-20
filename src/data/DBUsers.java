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
public class DBUsers {
    private final String providedPassword;
    User user;


    public DBUsers(String username, String password) {
        user = new User(username, password);
        this.providedPassword = password;
        validateUsernamePassword();
    }

    public static void main(String[] args) {
        System.out.println(
                getUser("admin", "admin")
        );
    }

    public static User getUser(String username, String password){
        String sql = "SELECT * FROM users WHERE User_Name = ?";
        User user = null;
        try{
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(user != null){
            if (password.equals(user.getPassword())){
                return user;
            }else {
                return null;
            }
        } else{
            return null;
        }
    }

    /**
     * Make call to database to get actual information. This method does not validate anything
     * but rather completes the call to the database so that userExists() and passwordMatches()
     * can check against the data.
     * <p>
     * The ResultStatement does not iterate more than once--so for security it expects an exact match.
     */
    private void validateUsernamePassword() {
        ProcessQuery.processIf(
                "SELECT User_Name, Password FROM users WHERE User_Name = '" + user.getUsername() + "'",
                resultSet -> {
                    if (resultSet.next()) {
                        user.setUsername(resultSet.getString("User_Name").trim());
                        user.setPassword(resultSet.getString("Password").trim());
                    } else {
                        user.setUsername(null);
                        user.setPassword(null);
                    }
                });
    }


    /**
     * Checks whether provided username for the DBUsers object exists in database.
     *
     * @return returns true if ResultSet isn't null. If ResultSet isn't null, then the database
     * holds a username that matches the provided username.
     */
    public Boolean userExists() {
        return user.getUsername() != null;
    }

    /**
     * Checks whether provided password matches password in database for the username.
     *
     * @return returns true if provided password matches database password.
     */
    public Boolean passwordMatches() {
        return user.getPassword().equals(providedPassword);
    }

    public User addUser() {
        return null;
    }

    public ObservableList<User> getAllUsers() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public User editUser(User user) {
        return null;
    }

    public boolean deleteUser(User user) {
        return false;
    }
}

