package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Call static method process(), then pass in the while logic as a lambda expression.
 */
@UtilityInterfaces
public class ProcessQuery {
    public static void process(String sql, UtilityInterfaces.FunctionalResultSetInterface o) {
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
    public static void processIf(String sql, UtilityInterfaces.FunctionalResultSetInterface o) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            // Send resultSet to logic for processing

            //FIXME - fix naming convention of whileLogic since it's not used with an while() loop here
            o.whileLogic(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
