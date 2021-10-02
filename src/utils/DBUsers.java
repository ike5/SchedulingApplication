package utils;

import model.Query;
import model.RandomClassQuery;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers extends User {
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
     *
     * The ResultStatement does not iterate more than once--so for security it expects an exact match.
     */
    private void validateUsernamePassword() {
        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + this.getUsername() + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.setUsername(rs.getString("User_Name").trim());
                this.setPassword(rs.getString("Password").trim());
            } else {
                this.setUsername(null);
                this.setPassword(null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


//    // have a method in a class that implements an interface in the parameters
//    private void validateUsernamePassword(){
//        String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + this.getUsername() + "'";
//
//    }

    public static void main(String[] args) {
        Query queryClass = new Query();
        QueryMaker queryMaker = new RandomClassQuery("Select * from customers"); // interface says I have these methods
        ResultSet rs = queryClass.basicQuery(queryMaker);
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
}

