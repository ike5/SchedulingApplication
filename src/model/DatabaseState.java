package model;

/**
 * This class is used to record state and allow the programmer to set whether
 * to use the local database or remote database.
 *
 * When isUsingLocalDatabase is true, the local database should be used. When
 * isUsingLocalDatabase is false, the program should make a connection to a
 * remote database.
 */
public class DatabaseState {
    public static boolean isUsingLocalDatabase;

    public static void setIsUsingLocalDatabase(boolean isUsingLocalDatabase) {
        DatabaseState.isUsingLocalDatabase = isUsingLocalDatabase;
    }
}
