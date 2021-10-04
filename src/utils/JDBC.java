package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC implements DatabaseConnection {
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR = ":mysql:";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String LOCATION = "//localhost/";
    private static final String DATABASE_NAME = "client_schedule";
    private static final String JDBC_URL = PROTOCOL + VENDOR + LOCATION + DATABASE_NAME + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String USERNAME = "sqlUser"; // Username
    private static final String PASSWORD = "Passw0rd!"; // Password

    public static Connection CONNECTION;  // Connection Interface

    /**
     * This method creates the connection to the local MySQL server.
     *
     * @return Connection object
     */
    public static Connection openConnection() {
        try {
            Class.forName(DRIVER); // Locate Driver
            CONNECTION = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connection to database successful...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CONNECTION;
    }

    /**
     * Closes Connection.
     */
    public static void closeConnection() {
        try {
            CONNECTION.close();
            System.out.println("Connection closed...");
        } catch (Exception e) { System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Gets the Connection object.
     *
     * @return Connection object
     */
    public static Connection getConnection() {
        return CONNECTION;
    }
}
