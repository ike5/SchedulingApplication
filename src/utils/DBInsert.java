package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Using JDBC to insert into first table:
 *
 */
public class DBInsert {
    // You can use the null to select keys
    // Use getGeneratedKeys() from JDBC API
    // null = User_ID (key)
    // ? = User_Name
    // ? = Password
    public static String sql = "INSERT INTO users VALUES (null, ?, ?)";

    public static void insert() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter name: ");

        String name = keyboard.nextLine();

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, name);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
