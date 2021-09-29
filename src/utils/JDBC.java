package utils;

import model.DatabaseState;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR = ":mysql:";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver reference
    public static Connection CONNECTION;  // Connection Interface

    // Local login info
    private static final String LOCAL_LOCATION = "//localhost/";
    private static final String LOCAL_DATABASE_NAME = "client_schedule";
    private static final String LOCAL_JDBC_URL = PROTOCOL + VENDOR + LOCAL_LOCATION + LOCAL_DATABASE_NAME + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String LOCAL_USERNAME = "sqlUser"; // Username
    private static final String LOCAL_PASSWORD = "Passw0rd!"; // Password

    // Remote login info
    private static final String IP_ADDRESS = "//wgudb.ucertify.com/";
    private static final String DB_NAME = "WJ07T9J";
    private static final String JDBC_URL = PROTOCOL + VENDOR + IP_ADDRESS + DB_NAME;
    private static final String REMOTE_USERNAME = "U07T9J"; // Username on wgu server
    private static final String REMOTE_PASSWORD = "53689123420"; // Password

    public static Connection openConnection() {
        if (DatabaseState.isUsingLocalDatabase) {
            // Use local database where condition is true
            try {
                Class.forName(DRIVER); // Locate Driver
                CONNECTION = DriverManager.getConnection(LOCAL_JDBC_URL, LOCAL_USERNAME, LOCAL_PASSWORD);
                System.out.println("Connection successful using local database...");
            } catch (Exception e) {
                // Printing the stack trace is better for seeing errors in SQL
                e.printStackTrace();
            }
        } else {
            // Use remote database where condition is false
            try {
                Class.forName(DRIVER); // Locate Driver
                CONNECTION = DriverManager.getConnection(JDBC_URL, REMOTE_USERNAME, REMOTE_PASSWORD);
                System.out.println("Connection successful using remote database...");
            } catch (SQLException | ClassNotFoundException e) {
                // Printing the stack trace is better for seeing errors in SQL
                e.printStackTrace();
            }
        }
        return CONNECTION;
    }

    public static void closeConnection() {
        try {
            CONNECTION.close();
            System.out.println("Connection closed...");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}
