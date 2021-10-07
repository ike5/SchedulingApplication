package data;

import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import utils.ProcessQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Locale;

interface UserDAO{
    // CREATE, READ, UPDATE, DELETE
    public User addUser(); // create
    public ObservableList<User> getAllUsers(); // read
    public User getUser(int UserId); // read
    public User editUser(User user); // update
    public boolean deleteUser(User user); // delete
}

public class DBUsers extends User implements UserDAO{
    private final String providedPassword;

    public DBUsers(String username, String password) {
        super(username, password);
        this.providedPassword = getPassword();
        validateUsernamePassword();
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
                "SELECT User_Name, Password FROM users WHERE User_Name = '" + this.getUsername() + "'",
                resultSet -> {
                    if (resultSet.next()) {
                        this.setUsername(resultSet.getString("User_Name").trim());
                        this.setPassword(resultSet.getString("Password").trim());
                    } else {
                        this.setUsername(null);
                        this.setPassword(null);
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
        return this.getUsername() != null;
    }

    /**
     * Checks whether provided password matches password in database for the username.
     *
     * @return returns true if provided password matches database password.
     */
    public Boolean passwordMatches() {
        return this.getPassword().equals(providedPassword);
    }

    @Override
    public ZonedDateTime startDateTime() {
        return null;
    }

    @Override
    public ZonedDateTime endDateTime() {
        return null;
    }

    @Override
    public Locale createDateTime() {
        return null;
    }

    @Override
    public Locale lastUpdateDateTime() {
        return null;
    }

    @Override
    public String createdBy(String name) {
        return null;
    }

    @Override
    public String lastUpdatedBy(String name) {
        return null;
    }

    @Override
    public User addUser() {
        return null;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(int UserId) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}

