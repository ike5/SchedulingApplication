package model;

/**
 * A class to save USERNAME and PASSWORD objects for validation.
 */
public class Credentials {
    private final String PASSWORD;
    private final String USERNAME;

    public Credentials(String username, String password) {
        this.PASSWORD = password;
        this.USERNAME = username;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getUsername() {
        return USERNAME;
    }
}