package model;


/**
 * This class should not be instantiated but should be extended.
 */
public class User {
    private int userId;
    private String username;
    private String password;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public User() {
        this(null, null);
    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}