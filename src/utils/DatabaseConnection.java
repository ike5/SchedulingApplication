package utils;

import java.sql.Connection;

/**
 * The interface has static methods for security: any non-implementing
 * class cannot use a method with the same signature.
 */
public interface DatabaseConnection {
    public static Connection openConnection() {
        return null;
    }

    public static void closeConnection() {

    }

    public static Connection getConnection() {
        return null;
    }
}
