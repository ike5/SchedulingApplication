package model;


/**
 * The type User.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private boolean isValidUsername;
    private boolean isValidPassword;


    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId   the user id
     * @param username the username
     * @param password the password
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId          the user id
     * @param username        the username
     * @param password        the password
     * @param isValidUsername the is valid username
     * @param isValidPassword the is valid password
     */
    public User(int userId, String username, String password, boolean isValidUsername, boolean isValidPassword) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isValidUsername = isValidUsername;
        this.isValidPassword = isValidPassword;
    }

    /**
     * Is valid username boolean.
     *
     * @return the boolean
     */
    public boolean isValidUsername() {
        return isValidUsername;
    }

    /**
     * Sets valid username.
     *
     * @param validUsername the valid username
     */
    public void setValidUsername(boolean validUsername) {
        isValidUsername = validUsername;
    }

    /**
     * Is valid password boolean.
     *
     * @return the boolean
     */
    public boolean isValidPassword() {
        return isValidPassword;
    }

    /**
     * Sets valid password.
     *
     * @param validPassword the valid password
     */
    public void setValidPassword(boolean validPassword) {
        isValidPassword = validPassword;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }
}