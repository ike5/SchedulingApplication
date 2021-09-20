package data;

import model.Credentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {
    private Credentials user;
    private Credentials providedUserCredentials;

    public DBUsers(String username, String password) {
        providedUserCredentials = new Credentials(username, password);

        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + username + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new Credentials(rs.getString("User_Name").trim(), rs.getString("Password").trim());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Credentials getUser() {
        return user;
    }

    public Boolean usernameExists() {
        System.out.println("username provided: " + this.providedUserCredentials.getUsername());
        System.out.println("username from database: " + user.getUsername());
        System.out.println("usernameExists() boolean " + this.providedUserCredentials.getUsername().equals(user.getUsername()));
        return this.providedUserCredentials.getUsername().equals(user.getUsername());
    }

    public Boolean passwordMatches() {
        System.out.println("password provided: " + this.providedUserCredentials.getPassword());
        System.out.println("password from database: " + user.getPassword());
        System.out.println("passwordMatches() boolean " + this.providedUserCredentials.getPassword().equals(user.getPassword()));
        return this.providedUserCredentials.getPassword().equals(user.getPassword());
    }
}

