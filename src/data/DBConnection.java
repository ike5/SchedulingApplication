package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/";
    private static final String dbName = "WJ07T9J";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    // Driver and Connection interface reference
    private static final String MYSQL_JDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07T9J"; // Username
    private static final String password = "53689123420"; // Password

    public static Connection startConnection() {
        try {
            Class.forName(MYSQL_JDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection initiated...");
        } catch (SQLException e) {
            e.printStackTrace(); // better for SQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            System.out.println("Connection closed...");
            conn.close();
        } catch (Exception e) {
            // do nothing just in case a race happens where the connection closes early
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}