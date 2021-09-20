package model;

import java.util.Objects;

/**
 * A class to save username and password objects for validation.
 */
public class Credentials {
    private final String password;
    private final String username;

    public Credentials(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials credentials1 = (Credentials) o;
        return Objects.equals(password, credentials1.password) && Objects.equals(username, credentials1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, username);
    }
}
