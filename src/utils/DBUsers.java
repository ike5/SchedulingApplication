package utils;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {
    private User user;
    private String password;

    public DBUsers(String username, String password) {
        this.password = password;
        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = '" + username + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                user = new User(rs.getString("User_Name").trim(), rs.getString("Password").trim());
            } else {
                user = new User(null, null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public Boolean userExists(){
        return user.getUsername() != null;
    }

    public Boolean passwordMatches(){
        return user.getPassword().equals(this.password);
    }
}

