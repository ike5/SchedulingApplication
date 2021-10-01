package model;

/**
 * This class should not be instantiated but should be extended.
 */
public abstract class User extends Person{
    private String password;
    private String username;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public User(){
        this(null, null);
    };

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
}