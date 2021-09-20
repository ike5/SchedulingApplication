package data;

import model.Password;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {
    private Password user;
    private String username;
    private String password;

    public DBUsers(String username, String password) {
        this.username = username;
        this.password = password;

        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + username + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new Password(rs.getString("User_Name"), rs.getString("Password"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Password getUser() {
        return user;
    }

    public Boolean usernameExists() {
        System.out.println("username provided: " + this.username);
        System.out.println("username from database: " + user.getUsername());
        System.out.println("usernameExists() boolean " + this.username == user.getUsername());
        return this.username == user.getUsername();
    }

    public Boolean passwordMatches() {
        System.out.println("password provided: " + this.password);
        System.out.println("password from database: " + user.getPassword());
        System.out.println("passwordMatches() boolean " + this.password == user.getPassword());
        return this.password == user.getPassword();
    }
}

