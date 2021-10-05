package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Call static method process(), then pass in the while logic as a lambda expression.
 */
public class ProcessQuery {
    public static void process(String sql, FunctionalResultSetInterface o){
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                // Use the method defined in the functional interface to set up the logic inside
                // the while loop.
                o.whileLogic(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
