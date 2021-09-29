package model;

/**
 * This class is used to record state and allow the programmer to set whether
 * to use the local database or remote database.
 * <p>
 * When isUsingLocalDatabase is true, the local database should be used. When
 * isUsingLocalDatabase is false, the program should make a CONNECTION to a
 * remote database.
 */
public class DatabaseState {
    public static boolean isUsingLocalDatabase;

    /**
     * Set to true if using a local database. Set to false if using remote database provided
     * by WGU.
     *
     * @param isUsingLocalDatabase
     */
    public static void useLocalDatabase(boolean isUsingLocalDatabase) {
        DatabaseState.isUsingLocalDatabase = isUsingLocalDatabase;
    }
}
