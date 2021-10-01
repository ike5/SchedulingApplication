package model;

/**
 * A class to save username and password objects for validation.
 */
public class User extends Person{
    private String password;
    private String username;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}