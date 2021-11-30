package data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String location = "//localhost/";
    private static final String database_name = "client_schedule";
    private static final String jdbc_url = protocol + vendor + location + database_name + "?connectionTimeZone = SERVER";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection conn;

    /**
     * Helper method that locates database driver, and opens a connection with credentials.
     *
     * @return Returns a single instance of a Connection object that can be reused elsewhere throughout the
     * program.
     */
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
     * Helper method to call close() on the Connection object.
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
     * Helper method to retrieve the static Connection object.
     *
     * @return Returns the Connection object. It is advisable to call openConnection() first before getting the
     * Connection object.
     */
    public static Connection getConnection() {
        return conn;
    }
}