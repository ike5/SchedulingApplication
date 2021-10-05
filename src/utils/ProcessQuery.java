package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Call static method process(), then pass in the while logic as a lambda expression.
 */
public class ProcessQuery {
    public static void process(String sql, FunctionalResultSetInterface o) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Set up logic inside while loop
                o.whileLogic(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Calls resultSet.next() only ONCE and is used to send logic into an if statement.
     *
     * @param sql
     * @param o
     */
    public static void processIf(String sql, FunctionalResultSetInterface o) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            // Send resultSet to logic for processing
            o.whileLogic(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
