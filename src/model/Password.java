package model;

/**
 * A class to save username and password objects for validation.
 */
public class Password {
    private final String password;
    private final String username;

    public Password(String username, String password){
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
