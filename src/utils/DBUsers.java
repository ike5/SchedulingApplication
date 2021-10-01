package utils;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers extends User {
    private String providedPassword;

    public DBUsers(String username, String password) {
        super(username, password);
        this.providedPassword = getPassword();
        validateUsernamePassword();
    }

    /**
     * Make call to database to get actual information. This method does not validate anything
     * but rather completes the call to the database so that userExists() and passwordMatches()
     * can check against the data.
     */
    private void validateUsernamePassword(){
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

    public Boolean userExists() {
        return this.getUsername() != null;
    }

    public Boolean passwordMatches() {
        return this.getPassword().equals(providedPassword);
    }
}

