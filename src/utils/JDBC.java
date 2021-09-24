package utils;

import model.DatabaseState;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    public static Connection connection;  // Connection Interface

    // Local login info
    private static final String localLocation = "//localhost/";
    private static final String localDatabaseName = "client_schedule";
    private static final String localJdbcUrl = protocol + vendor + localLocation + localDatabaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String localUserName = "sqlUser"; // Username
    private static String localPassword = "Passw0rd!"; // Password

    // Remote login info
    private static final String ipAddress = "//wgudb.ucertify.com/";
    private static final String dbName = "WJ07T9J";
    private static final String jdbcURL = protocol + vendor + ipAddress + dbName;
    private static final String remoteUsername = "U07T9J"; // Username on wgu server
    private static final String remotePassword = "53689123420"; // Password

    public static Connection openConnection() {
        if (DatabaseState.isUsingLocalDatabase) {
            // Use local database where condition is true
            try {
                Class.forName(driver); // Locate Driver
                connection = DriverManager.getConnection(localJdbcUrl, localUserName, localPassword);
                System.out.println("Connection successful using local database...");
            } catch (Exception e) {
                // Printing the stack trace is better for seeing errors in SQL
                e.printStackTrace();
            }
        } else {
            // Use remote database where condition is false
            try {
                Class.forName(driver); // Locate Driver
                connection = DriverManager.getConnection(jdbcURL, remoteUsername, remotePassword);
                System.out.println("Connection successful using remote database...");
            } catch (SQLException | ClassNotFoundException e) {
                // Printing the stack trace is better for seeing errors in SQL
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed...");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
