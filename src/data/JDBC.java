package data;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String location = "//localhost/";
    private static final String database_name = "client_schedule";
    private static final String jdbc_url = protocol + vendor + location + database_name + "?connectionTimeZone = SERVER";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    static Connection conn;

    public static Connection openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            conn = DriverManager.getConnection(jdbc_url, username, password);
            System.out.println("Connection to database successful...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Closes Connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed...");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Gets the Connection object.
     *
     * @return Connection object
     */
    public static Connection getConnection() {
        return conn;
    }
}