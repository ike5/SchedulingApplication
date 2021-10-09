package utils;

import data.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Call static method process(), then pass in the while logic as a lambda expression.
 */
@UtilityInterfaces
public class ProcessQuery {
    public static void processExecute(String sql, UtilityInterfaces.FunctionalResultSetInterface o) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                o.whileLogic(resultSet); // processes logic inside of while loop
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
    public static void processIf(String sql, UtilityInterfaces.FunctionalResultSetInterface o) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            //fixme - fix the naming convention for the lambda if you use it
            o.whileLogic(resultSet); // Send resultSet to logic for processing in an if-statement
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
