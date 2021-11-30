package model;


/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private boolean isValidUsername;
    private boolean isValidPassword;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password, boolean isValidUsername, boolean isValidPassword) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isValidUsername = isValidUsername;
        this.isValidPassword = isValidPassword;
    }

    public boolean isValidUsername() {
        return isValidUsername;
    }

    public void setValidUsername(boolean validUsername) {
        isValidUsername = validUsername;
    }

    public boolean isValidPassword() {
        return isValidPassword;
    }

    public void setValidPassword(boolean validPassword) {
        isValidPassword = validPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }
}